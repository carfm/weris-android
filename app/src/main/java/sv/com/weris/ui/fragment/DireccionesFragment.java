package sv.com.weris.ui.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.weris.MainActivity;
import sv.com.weris.MapsActivity;
import sv.com.weris.R;
import sv.com.weris.api.RetrofitClient;
import sv.com.weris.model.data.Cliente;
import sv.com.weris.model.data.DireccionCliente;
import sv.com.weris.model.interfaces.IParametro;
import sv.com.weris.model.interfaces.OnItemClickListener;
import sv.com.weris.service.ServicesAPI;
import sv.com.weris.ui.adapter.DireccionClienteAdapter;
import sv.com.weris.util.Util;

/**
 * A placeholder fragment containing a simple view.
 */
public class DireccionesFragment extends Fragment {

    private RecyclerView rvResultados;
    private DireccionClienteAdapter adapter;
    private List<DireccionCliente> direccionClienteList;
    FloatingActionButton fab;
    Button agregar_direccion,fijar;
    LinearLayout sin_direcciones;
    private Cliente cliente;


    public DireccionesFragment() {
    }

    public DireccionesFragment(Cliente cliente) {
        this.cliente = MainActivity.cliente;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_direcciones, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvResultados = view.findViewById(R.id.rv_direcciones);
        fab = view.findViewById(R.id.fab);
        sin_direcciones = view.findViewById(R.id.sin_direcciones);
        agregar_direccion = view.findViewById(R.id.agregar_direccion);
        fijar = view.findViewById(R.id.fijar);
        agregar_direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaDireccion(null);
            }
        });

        fijar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaDireccion(null);
            }
        });
        getActivity().findViewById(R.id.img_logo).setVisibility(View.GONE);
        TextView tx = getActivity().findViewById(R.id.textEmpresa);
        tx.setVisibility(View.VISIBLE);
        tx.setText("Mis direcciones");

        /*view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    System.out.println("Entro en back");
                    getActivity().onBackPressed();
                    return true;
                }
                return false;
            }
        });*/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
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
                    direccionClienteList = ofertas;
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
        if (direccionClienteList.size() > 0) {
            sin_direcciones.setVisibility(View.GONE);
            rvResultados.setVisibility(View.VISIBLE);
            agregar_direccion.setVisibility(View.VISIBLE);
        } else {
            sin_direcciones.setVisibility(View.VISIBLE);
            rvResultados.setVisibility(View.GONE);
            agregar_direccion.setVisibility(View.GONE);
        }

        adapter = new DireccionClienteAdapter(direccionClienteList, getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {
                gestionDireccion((DireccionCliente) item);
            }
        },true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        rvResultados.setLayoutManager(mLayoutManager);
        rvResultados.setAdapter(adapter);
    }

    public void gestionDireccion(DireccionCliente direccionCliente) {

       /* Intent intent = new Intent(getContext(), MapsActivity.class);
        Bundle b = new Bundle();
        b.putInt("idCompania", direccionCliente.getDireccionClientePK().getIdCompania());
        b.putInt("idCliente", direccionCliente.getDireccionClientePK().getIdCliente());
        b.putInt("idDireccionCliente", direccionCliente.getDireccionClientePK().getId());
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);*/


        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<DireccionCliente> call = servicesAPI.findDireccionCliente(direccionCliente.getDireccionClientePK().getIdCompania(),
                direccionCliente.getDireccionClientePK().getIdCliente(), direccionCliente.getDireccionClientePK().getId());
        call.enqueue(new Callback<DireccionCliente>() {
            @Override
            public void onResponse(Call<DireccionCliente> call, Response<DireccionCliente> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    DireccionCliente ofertas = response.body();
                    //ofertas.setLatitud(new BigDecimal(latitud));
                    //ofertas.setLongitud(new BigDecimal(longitud));
                    Fragment fragment = new GestionDireccionFragment(ofertas);
                    String nombre = "GestionDireccionFragment";
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment, nombre)
                            .commit();
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<DireccionCliente> call, Throwable t) {
//                esta.setText(t.getMessage());
                System.out.println(t.getMessage());
                //Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



        //finish();
    }

    public void nuevaDireccion(View view) {
        Util.activity(getContext(), new MapsActivity(), false);
        //finish();
    }

    public void obtenerDireccionCliente() {

    }


}
