package sv.com.weris.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import sv.com.weris.MainActivity;
import sv.com.weris.R;
import sv.com.weris.api.RetrofitClient;
import sv.com.weris.model.data.MetodoPago;
import sv.com.weris.service.ServicesAPI;

/**
 * A simple {@link Fragment} subclass.
 */
public class GestionMetodoPagoFragment extends Fragment {
    MetodoPago direccionCliente;
    EditText direccion, casaApto, puntoReferencia, ciudad, departamento;
    Button guardar;
    String tipo;
    ImageView casa, trabajo, puntoEncuentro;
    LinearLayout casaLy, trabajoLy, puntoEncuentroLy;
    Spinner tipoUbicacion, departamentoSpinner, municipioSpinner;
    ArrayAdapter<String> adapterTipoUbicacion, adapterDepartamentoSpinner,
    adapterMunicipioSpinner;

    public GestionMetodoPagoFragment() {
        // Required empty public constructor
    }

    public GestionMetodoPagoFragment(MetodoPago direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestion_metodos_pago, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        direccion = view.findViewById(R.id.direccion);
        casaApto = view.findViewById(R.id.casa_apto);
        puntoReferencia = view.findViewById(R.id.referencia);
        ciudad = view.findViewById(R.id.ciudad);
        departamento = view.findViewById(R.id.departamento);

        casa = view.findViewById(R.id.casa);
        trabajo = view.findViewById(R.id.trabajo);
        puntoEncuentro = view.findViewById(R.id.punto_encuentro);

        casaLy = view.findViewById(R.id.ly_casa);
        trabajoLy = view.findViewById(R.id.ly_trabajo);
        puntoEncuentroLy = view.findViewById(R.id.ly_punto_encuentro);

        tipoUbicacion = view.findViewById(R.id.tipo_ubicacion);

        ArrayList<String> nameList = new ArrayList<>();

        nameList.add("Casa");
        nameList.add("Trabajo");
        nameList.add("Punto de Encuentro");
        adapterTipoUbicacion = new ArrayAdapter(getContext(), R.layout.spinner, nameList);
        //adapterTipoUbicacion.setDropDownViewResource(R.layout.spinner);
        tipoUbicacion.setAdapter(adapterTipoUbicacion);

        guardar = view.findViewById(R.id.guardar);


        RxView.clicks(guardar).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                guardar();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        RxView.clicks(casaLy).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                System.out.println("1");
                Glide.with(getContext()).load(R.drawable.circle_selection).into(casa);
                Glide.with(getContext()).load(R.drawable.circle_no_selection).into(trabajo);
                Glide.with(getContext()).load(R.drawable.circle_no_selection).into(puntoEncuentro);
                tipo = "C";
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        RxView.clicks(trabajoLy).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                System.out.println("2");
                Glide.with(getContext()).load(R.drawable.circle_no_selection).into(casa);
                Glide.with(getContext()).load(R.drawable.circle_selection).into(trabajo);
                Glide.with(getContext()).load(R.drawable.circle_no_selection).into(puntoEncuentro);
                tipo = "T";
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        RxView.clicks(puntoEncuentroLy).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                System.out.println("3");
                Glide.with(getContext()).load(R.drawable.circle_no_selection).into(casa);
                Glide.with(getContext()).load(R.drawable.circle_no_selection).into(trabajo);
                Glide.with(getContext()).load(R.drawable.circle_selection).into(puntoEncuentro);
                tipo = "P";
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }


    public void guardar() {
        direccionCliente.setIdCliente(MainActivity.cliente.getClientePK().getId());
        direccionCliente.setIdCompania(MainActivity.cliente.getClientePK().getIdCompania());
        direccionCliente.setNombre(direccion.getText().toString());
        direccionCliente.setTarjeta(departamento.getText().toString());
        direccionCliente.setFechaVencimiento(ciudad.getText().toString());

        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call  call = servicesAPI.crearMetodoPagoCliente(direccionCliente);


        call.enqueue(new Callback<MetodoPago>() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    System.out.println("Se envio: " + response);
                    Toast.makeText(getContext(), "Se creo el metodo de pago correctamente", Toast.LENGTH_LONG).show();
                    ((MainActivity) getContext()).irFormasPagoTarjeta();
                } else {
                    System.out.println(response.errorBody());
                    System.out.println("Hubo un error al guardar el metodo de pago");
                    Toast.makeText(getContext(), "Hubo un error al crear el metodo de pago", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getContext(), "Hubo un error al sincronizar el metodo de pago", Toast.LENGTH_LONG).show();
            }
        });


    }


}
