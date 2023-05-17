package sv.com.guindapp;

import android.content.Intent;
import android.os.Bundle;

import com.jakewharton.rxbinding.view.RxView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import sv.com.guindapp.api.RetrofitClient;
import sv.com.guindapp.model.data.DireccionCliente;
import sv.com.guindapp.service.ServicesAPI;
import sv.com.guindapp.ui.fragment.DireccionesFragment;
import sv.com.guindapp.ui.fragment.GestionDireccionFragment;
import sv.com.guindapp.util.Util;

public class DireccionesActivity extends AppCompatActivity {


    Integer idCompania;
    Integer idCliente;
    Integer idDireccionCliente;
    Double latitud;
    Double longitud;
    private LinearLayout compras;
    private LinearLayout menu;
    private LinearLayout perfil;
    private LinearLayout favoritos;
    private LinearLayout descubrir;

    private RelativeLayout atras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direcciones);
        atras = findViewById(R.id.back);


        RxView.clicks(atras).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                onBackPressed();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        compras = findViewById(R.id.compras);
        menu = findViewById(R.id.home);
        perfil = findViewById(R.id.perfil);
        descubrir = findViewById(R.id.menu);
        favoritos = findViewById(R.id.favoritos);

        RxView.clicks(compras).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                //irCompras();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        Fragment fragment;
        String nombre;
        Bundle b = getIntent().getExtras();
        Integer edicion = 0;
        if (b != null) {
            latitud = b.getDouble("latitud");
            longitud = b.getDouble("longitud");
            edicion = b.getInt("edicion");
            if (edicion == 0) {
                DireccionCliente direccionCliente = new DireccionCliente();
                direccionCliente.setLongitud(new BigDecimal(longitud));
                direccionCliente.setLatitud(new BigDecimal(latitud));
                fragment = new GestionDireccionFragment(direccionCliente);
                nombre = "GestionDireccionFragment";
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment, nombre)
                        .commit();
            } else {
                idCompania = b.getInt("idCompania");
                idCliente = b.getInt("idCliente");
                idDireccionCliente = b.getInt("idDireccionCliente");
                //Viene para edicion y hay que buscar la direccion cliente
                obtenerDireccionCliente();
            }

        } else {
            fragment = new DireccionesFragment(MainActivity.cliente);
            nombre = "DireccionesFragment";
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment, nombre)
                    .commit();
        }

    }


    public void gestionDireccion(DireccionCliente direccionCliente) {

        Intent intent = new Intent(DireccionesActivity.this, MapsActivity.class);
        Bundle b = new Bundle();
        b.putInt("idCompania", direccionCliente.getDireccionClientePK().getIdCompania());
        b.putInt("idCliente", direccionCliente.getDireccionClientePK().getIdCliente());
        b.putInt("idDireccionCliente", direccionCliente.getDireccionClientePK().getId());
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        finish();

    }

    public void nuevaDireccion(View view) {
        Util.activity(this, new MapsActivity(), false);
        finish();
    }

    public void cargarDirecciones() {
        Fragment fragment = new DireccionesFragment(MainActivity.cliente);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "DireccionesFragment")
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void obtenerDireccionCliente() {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<DireccionCliente> call = servicesAPI.findDireccionCliente(idCompania, idCliente, idDireccionCliente);
        call.enqueue(new Callback<DireccionCliente>() {
            @Override
            public void onResponse(Call<DireccionCliente> call, Response<DireccionCliente> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    DireccionCliente ofertas = response.body();
                    ofertas.setLatitud(new BigDecimal(latitud));
                    ofertas.setLongitud(new BigDecimal(longitud));
                    Fragment fragment = new GestionDireccionFragment(ofertas);
                    String nombre = "GestionDireccionFragment";
                    FragmentManager fragmentManager = getSupportFragmentManager();
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

    }

}
