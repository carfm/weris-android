package sv.com.guindapp.ui.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import sv.com.guindapp.DireccionesActivity;
import sv.com.guindapp.MainActivity;
import sv.com.guindapp.R;
import sv.com.guindapp.api.RetrofitClient;
import sv.com.guindapp.model.data.Cliente;
import sv.com.guindapp.model.data.DireccionCliente;
import sv.com.guindapp.model.data.DireccionClientePK;
import sv.com.guindapp.service.ServicesAPI;

/**
 * A simple {@link Fragment} subclass.
 */
public class GestionDireccionFragment extends Fragment {
    DireccionCliente direccionCliente;
    EditText direccion, casaApto, puntoReferencia, ciudad, departamento, telefono;
    Button guardar;
    String tipo;
    ImageView casa, trabajo, puntoEncuentro;
    LinearLayout casaLy, trabajoLy, puntoEncuentroLy;
    Spinner tipoUbicacion, departamentoSpinner, municipioSpinner;
    ArrayAdapter<String> adapterTipoUbicacion, adapterDepartamentoSpinner,
            adapterMunicipioSpinner;

    public GestionDireccionFragment() {
        // Required empty public constructor
    }

    public GestionDireccionFragment(DireccionCliente direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestion_direccion, container, false);
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
        telefono = view.findViewById(R.id.telefono);

        ArrayList<String> nameList = new ArrayList<>();

        nameList.add("Casa");
        nameList.add("Trabajo");
        nameList.add("Punto de Encuentro");
        adapterTipoUbicacion = new ArrayAdapter(getContext(), R.layout.spinner, nameList);
        //adapterTipoUbicacion.setDropDownViewResource(R.layout.spinner);
        tipoUbicacion.setAdapter(adapterTipoUbicacion);

        guardar = view.findViewById(R.id.guardar);

        if (direccionCliente.getDireccionClientePK() == null) {
            tipo = "C";
            tipoUbicacion.setSelection(0);
        } else {
            direccion.setText(direccionCliente.getDireccion());
            puntoReferencia.setText(direccionCliente.getPuntoReferencia());
            ciudad.setText(direccionCliente.getCiudad());
            casaApto.setText(direccionCliente.getNumeroCasa());
            telefono.setText(direccionCliente.getTelefono());
            tipo = direccionCliente.getTipo();
            switch (tipo) {
                case "T":
                    tipoUbicacion.setSelection(1);
                    break;
                case "C":
                    tipoUbicacion.setSelection(0);
                    break;
                case "P":
                    tipoUbicacion.setSelection(2);
                    break;
            }
        }

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


    public Integer guardar() {
        if (direccion.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Ingrese una dirección de envio", Toast.LENGTH_LONG).show();
            return null;
        }

        if (telefono.getText().toString().trim().isEmpty() || telefono.getText().toString().trim().length() < 8) {
            Toast.makeText(getContext(), "Ingrese un número de teléfono de contacto", Toast.LENGTH_LONG).show();
            return null;
        }


        direccionCliente.setDireccion(direccion.getText().toString());
        direccionCliente.setCiudad(ciudad.getText().toString());
        direccionCliente.setCliente(MainActivity.cliente);
        direccionCliente.setLatitud(direccionCliente.getLatitud());
        direccionCliente.setLongitud(direccionCliente.getLongitud());
        direccionCliente.setPuntoReferencia(puntoReferencia.getText().toString());
        direccionCliente.setTipo(tipoUbicacion.getSelectedItemPosition() == 0 ? "C" :
                tipoUbicacion.getSelectedItemPosition() == 1 ? "T" : "P");
        direccionCliente.setTelefono(telefono.getText().toString());
        direccionCliente.setNumeroCasa(casaApto.getText().toString());

        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call call;
        if (direccionCliente.getDireccionClientePK() == null) {
            direccionCliente.setDireccionClientePK(new DireccionClientePK());
            direccionCliente.getDireccionClientePK().setIdCliente(direccionCliente.getCliente().getClientePK().getId());
            direccionCliente.getDireccionClientePK().setIdCompania(direccionCliente.getCliente().getClientePK().getIdCompania());
            call = servicesAPI.crearDireccionCliente(direccionCliente);
        } else {
            //editar
            call = servicesAPI.editarDireccionCliente(direccionCliente.toString(), direccionCliente);
        }

        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {

                    System.out.println("Se envio: " + response);
                    Toast.makeText(getContext(), "Se creo la direccion correctamente", Toast.LENGTH_LONG).show();
                    MainActivity.cliente = direccionCliente.getCliente();
                    ((MainActivity) getContext()).irDirecciones();
                } else {
                    System.out.println(response.errorBody());
                    System.out.println("Hubo un error al guardar la direccion");
                    Toast.makeText(getContext(), "Hubo un error al crear la direccion", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getContext(), "Hubo un error al sincronizar la direccion", Toast.LENGTH_LONG).show();
            }
        });

        return 1;

    }


}
