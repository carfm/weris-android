package sv.com.guindapp.ui.fragment;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jakewharton.rxbinding.view.RxView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import sv.com.guindapp.MainActivity;
import sv.com.guindapp.R;
import sv.com.guindapp.api.RetrofitClient;
import sv.com.guindapp.model.data.Cliente;
import sv.com.guindapp.model.data.DetOrden;
import sv.com.guindapp.model.data.Orden;
import sv.com.guindapp.model.data.Pedido;
import sv.com.guindapp.model.interfaces.FragmentFunctions;
import sv.com.guindapp.model.interfaces.IParametro;
import sv.com.guindapp.model.interfaces.OnItemClickListener;
import sv.com.guindapp.service.ServicesAPI;
import sv.com.guindapp.ui.adapter.DetOrdenProcesarAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProcesarOrdenFragment extends Fragment implements FragmentFunctions {

    private RecyclerView rvResultados;
    private DetOrdenProcesarAdapter adapter;
    private ArrayList<DetOrden> detOrdenList;
    private Button procesarOrden;
    private Orden orden;
    private TextView subtotal, cargo, otrosCargos, total, metodoPago, direccion, textEmpresa,
            werimovil,txtMetodoPago;
    private EditText instrucciones;
    LinearLayout metodoPagoLy, direccionLy;
    private Cliente cliente;
    Dialog customDialog;
    RadioButton efectivo,tarjeta;

    public ProcesarOrdenFragment() {
        // Required empty public constructor
    }

    public ProcesarOrdenFragment(Orden orden) {
        // Required empty public constructor
        this.orden = orden;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_procesar_orden, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvResultados = view.findViewById(R.id.rv_det_orden);
        procesarOrden = view.findViewById(R.id.procesar);
        subtotal = view.findViewById(R.id.subtotal);
        cargo = view.findViewById(R.id.cargo);
        otrosCargos = view.findViewById(R.id.otros_cargos);
        total = view.findViewById(R.id.total);
        instrucciones = view.findViewById(R.id.instrucciones);
        direccion = view.findViewById(R.id.direccion);
        metodoPago = view.findViewById(R.id.metodo_pago);

        direccionLy = view.findViewById(R.id.ly_direccion);
        metodoPagoLy = view.findViewById(R.id.ly_metodo_pago);
        textEmpresa = view.findViewById(R.id.textEmpresa);
        werimovil = view.findViewById(R.id.werimovil);
        txtMetodoPago = view.findViewById(R.id.txtMetodoPago);
        efectivo = view.findViewById(R.id.efectivo);
        tarjeta = view.findViewById(R.id.tarjeta);
        obtenerCliente();

    }

    public void llenarComponentes() {
        if (orden.getDireccionCliente() != null) {
            direccion.setText(orden.getDireccionCliente().getDireccion());
            orden.setDireccionCliente(orden.getDireccionCliente());
            calculoPrecioTransporte();
        } else {
            direccion.setText("Seleccionar direccion");
        }
        if(orden.getFormaPago() != null){
            if(orden.getFormaPago().getFormaPagoPK().getId() == 2){
                txtMetodoPago.setVisibility(View.VISIBLE);
                metodoPagoLy.setVisibility(View.VISIBLE);
                tarjeta.setChecked(true);
            }else{
                txtMetodoPago.setVisibility(View.GONE);
                metodoPagoLy.setVisibility(View.GONE);
                efectivo.setChecked(true);
            }
        }


        if (orden.getMetodoPagoSeleccionado() != null) {
            metodoPago.setText("Tarjeta " + orden.getMetodoPagoSeleccionado().getMascara());
        } else {
            metodoPago.setText("Seleccionar método de pago");
        }
        //textEmpresa.setText(orden.getComercio().getNombre());


        totales();

        adapter = new DetOrdenProcesarAdapter(orden.getDetOrdenList(), getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        rvResultados.setLayoutManager(mLayoutManager);
        rvResultados.setAdapter(adapter);

        RxView.clicks(procesarOrden).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                String ins = instrucciones.getText().toString();
                ((MainActivity) getContext()).procesarOrden((ins.isEmpty() ? null : ins), null, orden);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        RxView.clicks(metodoPagoLy).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ((MainActivity) getContext()).irFormasPago();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        RxView.clicks(direccionLy).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ((MainActivity) getContext()).irDireccionesDisponibles();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public void totales() {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        subtotal.setText("$ " + formatter.format(orden.getSubtotal().setScale(2, RoundingMode.HALF_UP)));
        cargo.setText("$ " + formatter.format(orden.getComision().setScale(2, RoundingMode.HALF_UP)));
        otrosCargos.setText("$ 0.00");
        total.setText("$ " + formatter.format(orden.getTotal().setScale(2, RoundingMode.HALF_UP)));
    }

    @Override
    public void allowBackPressed() {
        ((MainActivity) getContext()).getOpciones().setVisibility(View.VISIBLE);
    }



    public void obtenerCliente() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Cliente c = new Cliente();
        c.setNombres(user.getDisplayName());
        c.setCorreo(user.getEmail());
        c.setUidFirebase(user.getUid());
        c.setTelefono(user.getPhoneNumber());
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<Cliente> call = servicesAPI.crearClienteFirebase(c);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    Cliente ofertas = response.body();
                    cliente = ofertas;
                    System.out.println("Tamaño: " + ofertas);
                    orden.setCliente(cliente);
                    llenarComponentes();
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
//                esta.setText(t.getMessage());
                System.out.println(t.getMessage());
                //Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    /*public  void showDialog() {
        customDialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        //customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.colorUno)));
        customDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //deshabilitamos el título por defecto

        //customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.dialog_det_pedido);
        Button b = (Button) customDialog.findViewById(R.id.confirmar);
        Button r = (Button) customDialog.findViewById(R.id.cancelar);
        TextView top = (TextView) customDialog.findViewById(R.id.top);
        listaDialog = (ExpandableHeightListView) customDialog.findViewById(R.id.listViewDet);
        customDialog.show();
        Cargador.hide();
    }*/

    public String calculoPrecioTransporte() {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Pedido pedido = new Pedido();
        pedido.setOrden(orden);
        pedido.setDetOrdenList(orden.getDetOrdenList());
        System.out.println("Det: " + pedido.getDetOrdenList().size());
        Call<Orden> call = servicesAPI.calculoPrecioTransporte(pedido);
        call.enqueue(new Callback<Orden>() {
            @Override
            public void onResponse(Call<Orden> call, Response<Orden> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    Orden ofertas = response.body();
                    System.out.println("Tamaño: " + ofertas.getComision());
                    ofertas.setDetOrdenList(orden.getDetOrdenList());
                    // Toast.makeText(getContext(), "Hubo u" + ofertas.getComercioSucursalSeleccionado().getId(), Toast.LENGTH_LONG).show();

                    orden = ofertas;
                    orden.setOrdenPK(null);
                    if (orden.getComision() != null) {
                        orden.setTotal(new BigDecimal(orden.getSubtotal().doubleValue() + orden.getComision().doubleValue()));
                        totales();
                    }
                    if (orden.getTipoTransporteCalculado() != null
                            && orden.getTipoTransporteCalculado().getId() == 2) {
                        werimovil.setVisibility(View.VISIBLE);
                    } else {
                        werimovil.setVisibility(View.INVISIBLE);
                    }
                } else {
                    System.out.println("Error: " + response.errorBody());
                    Toast.makeText(getContext(), "Hubo un error al calcular la comision", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Orden> call, Throwable t) {
//                esta.setText(t.getMessage());
                System.out.println(t.getMessage());
                //Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Hubo un error al acceder al servidor", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }
}
