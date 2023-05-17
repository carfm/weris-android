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
import sv.com.guindapp.model.data.Cliente;
import sv.com.guindapp.model.data.Pedido;
import sv.com.guindapp.model.data.Orden;
import sv.com.guindapp.model.interfaces.IParametro;
import sv.com.guindapp.model.interfaces.OnItemClickListener;
import sv.com.guindapp.service.ServicesAPI;
import sv.com.guindapp.ui.adapter.OrdenAdapter;

// Instances of this class are fragments representing a single
// object in our collection.
public class ConsultaHistoricoOrdenFragment extends Fragment {

    private RecyclerView rvResultados;
    private OrdenAdapter adapter;
    private List<Pedido> ordenList;
    private Cliente cliente;

    public static final String ARG_OBJECT = "object";

    public ConsultaHistoricoOrdenFragment() {
    }

    public ConsultaHistoricoOrdenFragment(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consulta_historico_orden, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvResultados = view.findViewById(R.id.rv_ordenes);
        obtenerOrdenes();
    }

    public void obtenerOrdenes() {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<List<Pedido>> call = servicesAPI.ordenesPorCliente(cliente.getClientePK().getIdCompania(), cliente.getClientePK().getId(), 0);
        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    List<Pedido> ofertas = response.body();
                    ordenList = ofertas;
                    System.out.println("Tamaño: " + ofertas.size());
                    llenarLista();

                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
//                esta.setText(t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void llenarLista() {
        adapter = new OrdenAdapter(ordenList, getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {
                Pedido pedido = (Pedido) item;
                Orden orden = pedido.getOrden();
                orden.setDetOrdenList(pedido.getDetOrdenList());
                ((MainActivity) getContext()).verOrden(orden);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        rvResultados.setLayoutManager(mLayoutManager);
        rvResultados.setAdapter(adapter);
    }


}
