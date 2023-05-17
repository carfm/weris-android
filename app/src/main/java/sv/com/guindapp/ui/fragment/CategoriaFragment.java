package sv.com.guindapp.ui.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
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
import sv.com.guindapp.api.RetrofitClient;
import sv.com.guindapp.model.interfaces.IParametro;
import sv.com.guindapp.model.interfaces.OnItemClickListener;
import sv.com.guindapp.service.ServicesAPI;
import sv.com.guindapp.ui.adapter.CategoriaAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriaFragment extends Fragment {


    ImageView bebidas;
    ImageView comida;
    ImageView conveniencia;
    ImageView documentos;
    ImageView mandados;
    ImageView panaderias;
    ImageView salud;
    ImageView servicios;
    ImageView[] imageViewList;
    CategoriaAdapter adapter;
    OnItemClickListener listener;
    ArrayList<Categoria> itemList;
    private RecyclerView rvResultados;

    EditText buscar;

    public CategoriaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categoria, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bebidas = getActivity().findViewById(R.id.bebidas);
        comida = getActivity().findViewById(R.id.comida);
        conveniencia = getActivity().findViewById(R.id.conveniencia);
        documentos = getActivity().findViewById(R.id.documentos);
        mandados = getActivity().findViewById(R.id.mandados);
        panaderias = getActivity().findViewById(R.id.panaderias);
        salud = getActivity().findViewById(R.id.salud);
        servicios = getActivity().findViewById(R.id.servicios);
        buscar = getActivity().findViewById(R.id.buscar);
        imageViewList = new ImageView[8];
        imageViewList[0] = comida;
        imageViewList[1] = bebidas;
        imageViewList[2] = panaderias;
        imageViewList[3] = conveniencia;
        imageViewList[4] = salud;
        imageViewList[5] = documentos;
        imageViewList[6] = mandados;
        imageViewList[7] = servicios;
        rvResultados = getActivity().findViewById(R.id.rv_promociones);

       /* Glide.with(getContext()).load(R.drawable.cat_bebidas).into(bebidas);
        Glide.with(getContext()).load(R.drawable.cat_comida).into(comida);
        Glide.with(getContext()).load(R.drawable.cat_conveniencia).into(conveniencia);
        Glide.with(getContext()).load(R.drawable.cat_documentos).into(documentos);
        Glide.with(getContext()).load(R.drawable.cat_mandados).into(mandados);
        Glide.with(getContext()).load(R.drawable.cat_panaderias).into(panaderias);
        Glide.with(getContext()).load(R.drawable.cat_salud).into(salud);
        Glide.with(getContext()).load(R.drawable.cat_servicios).into(servicios);*/

        obtenerCategorias();
        buscar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!buscar.getText().toString().isEmpty()) {
                        ((MainActivity) getContext()).irBusqueda(null, buscar.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void obtenerCategorias() {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<List<Categoria>> call = servicesAPI.obtenerCategorias();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    List<Categoria> ofertas = response.body();
                    System.out.println("Tamaño: " + ofertas.size());
                    llenarListaCategorias(ofertas);
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
//                esta.setText(t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void asignarCategoria(final Categoria cat) {
        try {
            ImageView imageView = imageViewList[cat.getId() - 1];
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getContext()).irBusqueda(cat, null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void llenarListaCategorias(List<Categoria> lista) {
        listener = new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {
                Categoria cat = (Categoria) item;
                ((MainActivity) getContext()).irBusqueda(cat, null);
            }
        };
        adapter = new CategoriaAdapter(lista, getContext(), listener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvResultados.setLayoutManager(mLayoutManager);
        rvResultados.setAdapter(adapter);
    }


}
