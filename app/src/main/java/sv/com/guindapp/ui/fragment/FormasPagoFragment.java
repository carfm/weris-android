package sv.com.guindapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.guindapp.MainActivity;
import sv.com.guindapp.R;
import sv.com.guindapp.api.RetrofitClient;
import sv.com.guindapp.model.data.MetodoPago;
import sv.com.guindapp.model.interfaces.IParametro;
import sv.com.guindapp.model.interfaces.OnItemClickListener;
import sv.com.guindapp.service.ServicesAPI;
import sv.com.guindapp.ui.adapter.MetodoPagoAdapter;


public class FormasPagoFragment extends Fragment {

    private RecyclerView rvResultados;
    private MetodoPagoAdapter adapter;
    private List<MetodoPago> formaPagoList;

    public FormasPagoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_formas_pago, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvResultados = view.findViewById(R.id.rv_formas_pago);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        obtenerDireccionesCliente();
    }

    public void obtenerDireccionesCliente() {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<List<MetodoPago>> call = servicesAPI.metodosPagoPorCliente(MainActivity.cliente.getClientePK().getId(),MainActivity.cliente.getClientePK().getIdCompania());
        call.enqueue(new Callback<List<MetodoPago>>() {
            @Override
            public void onResponse(Call<List<MetodoPago>> call, Response<List<MetodoPago>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    List<MetodoPago> ofertas = response.body();
                    formaPagoList = ofertas;
                    System.out.println("Tamaño: " + ofertas.size());
                    llenarLista();
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<MetodoPago>> call, Throwable t) {
//                esta.setText(t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void llenarLista() {
        adapter = new MetodoPagoAdapter(formaPagoList, getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {
                ((MainActivity) getContext()).llenarFormaPago((MetodoPago) item);
            }
        },false);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        rvResultados.setLayoutManager(mLayoutManager);
        rvResultados.setAdapter(adapter);
    }
}