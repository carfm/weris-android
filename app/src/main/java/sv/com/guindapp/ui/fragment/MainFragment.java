package sv.com.guindapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.guindapp.MainActivity;
import sv.com.guindapp.R;
import sv.com.guindapp.model.data.Categoria;
import sv.com.guindapp.model.data.Comercio;
import sv.com.guindapp.model.interfaces.OnItemClickListener;
import sv.com.guindapp.api.RetrofitClient;
import sv.com.guindapp.service.ServicesAPI;
import sv.com.guindapp.ui.adapter.ResultadoAdapter;

public class MainFragment extends Fragment {

    private RecyclerView rvResultados;
    private RecyclerView rvCercaDeTi;
    private RecyclerView rvNuevos;
    private ResultadoAdapter adapter;
    private ResultadoAdapter adapter1;
    private ResultadoAdapter adapter2;
    private OnItemClickListener listener;
    private Categoria categoria;
    private ArrayList<Comercio> comercioList;
    private String palabra;

    EditText buscar;

    public MainFragment() {

    }

    public MainFragment(OnItemClickListener listener, Categoria categoria, String palabra) {
        this.listener = listener;
        this.categoria = categoria;
        this.palabra = palabra;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvResultados = getActivity().findViewById(R.id.rv_promociones);
        /*rvCercaDeTi = getActivity().findViewById(R.id.rv_cerca_ti);
        rvNuevos = getActivity().findViewById(R.id.rv_nuevos);*/
        //Cargador.show(getActivity());
        obtenerComercios();
        buscar = getActivity().findViewById(R.id.buscar);
        buscar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!buscar.getText().toString().isEmpty()) {
                        ((MainActivity) getContext()).irBusqueda(categoria
                                , buscar.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public OnItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


    public void obtenerComercios() {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<List<Comercio>> call = servicesAPI.comerciosSearch(categoria != null ? categoria.getId()
                : null, palabra);
        call.enqueue(new Callback<List<Comercio>>() {
            @Override
            public void onResponse(Call<List<Comercio>> call, Response<List<Comercio>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    List<Comercio> ofertas = response.body();
                    comercioList = new ArrayList<>();
                    System.out.println("Tamaño: " + ofertas.size());
                    for (Comercio c : ofertas) {
                        //asignarCategoria(c);
                        //c.setLogo(R.drawable.logo_taco_bell);
                        //c.setImagen(R.drawable.taco_bell);
                        comercioList.add(c);
                    }
                    llenarListaPromociones();
                    /*llenarListaCercaDeMi();
                    llenarListaNuevos();*/
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Comercio>> call, Throwable t) {
//                esta.setText(t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void llenarListaPromociones() {
        adapter = new ResultadoAdapter(comercioList, getContext(), listener);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);

        rvResultados.setLayoutManager(mLayoutManager);
        rvResultados.setAdapter(adapter);
    }

    public void llenarListaCercaDeMi() {
        adapter1 = new ResultadoAdapter(comercioList, getContext(), listener);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvCercaDeTi.setLayoutManager(mLayoutManager1);
        rvCercaDeTi.setAdapter(adapter1);
    }

    public void llenarListaNuevos() {
        adapter2 = new ResultadoAdapter(comercioList, getContext(), listener);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvNuevos.setLayoutManager(mLayoutManager2);
        rvNuevos.setAdapter(adapter2);
    }

}
