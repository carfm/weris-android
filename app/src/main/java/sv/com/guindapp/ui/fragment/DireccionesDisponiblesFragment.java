package sv.com.guindapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.guindapp.MainActivity;
import sv.com.guindapp.R;
import sv.com.guindapp.api.RetrofitClient;
import sv.com.guindapp.model.data.DireccionCliente;
import sv.com.guindapp.model.interfaces.IParametro;
import sv.com.guindapp.model.interfaces.OnItemClickListener;
import sv.com.guindapp.service.ServicesAPI;
import sv.com.guindapp.ui.adapter.DireccionClienteAdapter;


public class DireccionesDisponiblesFragment extends Fragment {

    private RecyclerView rvResultados;
    private DireccionClienteAdapter adapter;
    private List<DireccionCliente> formaPagoList;

    public DireccionesDisponiblesFragment() {
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
        Call<List<DireccionCliente>> call = servicesAPI.direccionesPorCliente(MainActivity.cliente.getClientePK().getIdCompania(), MainActivity.cliente.getClientePK().getId());
        call.enqueue(new Callback<List<DireccionCliente>>() {
            @Override
            public void onResponse(Call<List<DireccionCliente>> call, Response<List<DireccionCliente>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    List<DireccionCliente> ofertas = response.body();
                    formaPagoList = ofertas;
                    System.out.println("Tamaño: " + ofertas.size());
                    llenarLista();
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<DireccionCliente>> call, Throwable t) {
//                esta.setText(t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void llenarLista() {
        adapter = new DireccionClienteAdapter(formaPagoList, getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {
                ((MainActivity) getContext()).llenarDireccionCliente((DireccionCliente) item);
            }
        }, false, null);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        rvResultados.setLayoutManager(mLayoutManager);
        rvResultados.setAdapter(adapter);
    }
}