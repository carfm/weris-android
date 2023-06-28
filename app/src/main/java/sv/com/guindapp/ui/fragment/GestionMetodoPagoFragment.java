package sv.com.guindapp.ui.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import sv.com.guindapp.MainActivity;
import sv.com.guindapp.R;
import sv.com.guindapp.api.RetrofitClient;
import sv.com.guindapp.model.data.MetodoPago;
import sv.com.guindapp.service.ServicesAPI;

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

    private static final int CARD_NUMBER_TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
    private static final int CARD_NUMBER_TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
    private static final int CARD_NUMBER_DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
    private static final int CARD_NUMBER_DIVIDER_POSITION = CARD_NUMBER_DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
    private static final char CARD_NUMBER_DIVIDER = '-';

    private static final int CARD_DATE_TOTAL_SYMBOLS = 5; // size of pattern MM/YY
    private static final int CARD_DATE_TOTAL_DIGITS = 4; // max numbers of digits in pattern: MM + YY
    private static final int CARD_DATE_DIVIDER_MODULO = 3; // means divider position is every 3rd symbol beginning with 1
    private static final int CARD_DATE_DIVIDER_POSITION = CARD_DATE_DIVIDER_MODULO - 1; // means divider position is every 2nd symbol beginning with 0
    private static final char CARD_DATE_DIVIDER = '/';

    private static final int CARD_CVC_TOTAL_SYMBOLS = 3;

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


        departamento.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Remove all spacing char

                int pos = 0;
                while (true) {
                    if (pos >= s.length()) break;
                    if (space == s.charAt(pos) && (((pos + 1) % 5) != 0 || pos + 1 == s.length())) {
                        s.delete(pos, pos + 1);
                    } else {
                        pos++;
                    }
                }

                // Insert char where needed.
                pos = 4;
                while (true) {
                    if (pos >= s.length()) break;
                    final char c = s.charAt(pos);
                    // Only if its a digit where there should be a space we insert a space
                    if ("0123456789".indexOf(c) >= 0) {
                        s.insert(pos, "" + space);
                    }
                    pos += 5;
                }
            }
        });

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
        direccionCliente.setCodigo(casaApto.getText().toString());

        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<MetodoPago> call = servicesAPI.crearMetodoPagoCliente(direccionCliente);


        call.enqueue(new Callback<MetodoPago>() {
            @Override
            public void onResponse(Call<MetodoPago> call, Response<MetodoPago> response) {
                if (response.isSuccessful()) {
                    System.out.println("Se envio: " + response);

                    MetodoPago metodoPago = response.body();

                    if (metodoPago != null) {
                        Toast.makeText(getContext(), "Se creo el metodo de pago correctamente", Toast.LENGTH_LONG).show();
                        ((MainActivity) getContext()).irFormasPagoTarjeta();
                    } else {
                        System.out.println("Hubo un error al crear el método de pago");
                    }
                } else {
                    System.out.println(response.errorBody());
                    System.out.println("Hubo un error al guardar el metodo de pago");
                    Toast.makeText(getContext(), "Hubo un error al crear el metodo de pago", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MetodoPago> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getContext(), "Hubo un error al sincronizar el metodo de pago", Toast.LENGTH_LONG).show();
            }
        });


    }

    private boolean isInputCorrect(Editable s, int size, int dividerPosition, char divider) {
        boolean isCorrect = s.length() <= size;
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && (i + 1) % dividerPosition == 0) {
                isCorrect &= divider == s.charAt(i);
            } else {
                isCorrect &= Character.isDigit(s.charAt(i));
            }
        }
        return isCorrect;
    }

    private String concatString(char[] digits, int dividerPosition, char divider) {
        final StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                formatted.append(digits[i]);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                    formatted.append(divider);
                }
            }
        }

        return formatted.toString();
    }

    private char[] getDigitArray(final Editable s, final int size) {
        char[] digits = new char[size];
        int index = 0;
        for (int i = 0; i < s.length() && index < size; i++) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                digits[index] = current;
                index++;
            }
        }
        return digits;
    }


}
