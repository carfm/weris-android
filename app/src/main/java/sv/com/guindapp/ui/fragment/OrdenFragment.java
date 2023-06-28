package sv.com.guindapp.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding.view.RxView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.functions.Action1;
import sv.com.guindapp.MainActivity;
import sv.com.guindapp.R;
import sv.com.guindapp.model.data.Orden;
import sv.com.guindapp.model.interfaces.FragmentFunctions;
import sv.com.guindapp.model.interfaces.IParametro;
import sv.com.guindapp.model.interfaces.OnItemClickListener;
import sv.com.guindapp.ui.adapter.DetOrdenProcesarAdapter;
import sv.com.guindapp.util.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdenFragment extends Fragment implements FragmentFunctions {

    private RecyclerView rvResultados;
    private DetOrdenProcesarAdapter adapter;
    private Button calificar, rutaPedido;
    private Orden orden;
    private TextView subtotal, cargo, otrosCargos, total, metodoPago, direccion, comercio;
    private TextView instrucciones, comercioInfo, transportistaInfo, estado, fechaHora, pedido, tiempo,
            text_orden_recibida, text_delivery, text_disfruta;
    LinearLayout comercioLy, direccionLy, transportistaLy;
    ImageView orden_recibida, delivery, disfruta;

    public OrdenFragment() {
        // Required empty public constructor
    }

    public OrdenFragment(Orden orden) {
        // Required empty public constructor
        this.orden = orden;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orden, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvResultados = view.findViewById(R.id.rv_det_orden);
        calificar = view.findViewById(R.id.calificar);
        rutaPedido = view.findViewById(R.id.ver_ruta_pedido);
        comercio = view.findViewById(R.id.comercio);
        subtotal = view.findViewById(R.id.subtotal);
        cargo = view.findViewById(R.id.cargo);
        otrosCargos = view.findViewById(R.id.otros_cargos);
        total = view.findViewById(R.id.total);
        instrucciones = view.findViewById(R.id.instrucciones);
        direccion = view.findViewById(R.id.direccion);
        metodoPago = view.findViewById(R.id.metodo_pago);
        comercioInfo = view.findViewById(R.id.comercio_info);
        transportistaInfo = view.findViewById(R.id.info_transportista);
        estado = view.findViewById(R.id.estado);
        pedido = view.findViewById(R.id.pedido);
        fechaHora = view.findViewById(R.id.fecha_hora);
        tiempo = view.findViewById(R.id.tiempo);

        orden_recibida = view.findViewById(R.id.orden_recibida);
        delivery = view.findViewById(R.id.delivery);
        disfruta = view.findViewById(R.id.disfruta);

        text_orden_recibida = view.findViewById(R.id.text_orden_recibida);
        text_delivery = view.findViewById(R.id.text_delivery);
        text_disfruta = view.findViewById(R.id.text_disfruta);

        direccionLy = view.findViewById(R.id.ly_direccion);
        comercioLy = view.findViewById(R.id.ly_comercio);
        transportistaLy = view.findViewById(R.id.ly_transportista);

        comercio.setText(orden.getComercio().getNombre());
        direccion.setText(orden.getDireccionCliente().getDireccion());
        metodoPago.setText(orden.getFormaPago().getNombre());
        comercioInfo.setText(orden.getComercio().getNombre());
        estado.setText(orden.getEstadoOrden().getNombre());
        pedido.setText("Orden  N° " + orden.getIdOrden());

        SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fechaHora.setText("Fecha/Hora: " + sp.format(orden.getFechaOrden()));

        long diff = (new Date()).getTime() - (orden.getFechaOrden()).getTime();
        long segundos = diff / 1000;
        long minutos = segundos / 60;
        tiempo.setText("Tiempo transcurrido: " + minutos + " min");

        if (orden.getTransportista() != null) {
            transportistaInfo.setText(orden.getTransportista().getNombres());
        } else {
            transportistaInfo.setText("Sin asignar");
        }


        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        subtotal.setText("$ " + formatter.format(orden.getSubtotal().setScale(2, RoundingMode.HALF_UP)));
        cargo.setText(orden.getComision() != null ? "$ " + formatter.format(orden.getComision().setScale(2, RoundingMode.HALF_UP)) : "$ 0.00");
        otrosCargos.setText("$ 0.00");
        total.setText("$ " + formatter.format(orden.getTotal().setScale(2, RoundingMode.HALF_UP)));

        adapter = new DetOrdenProcesarAdapter(orden.getDetOrdenList(), getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        rvResultados.setLayoutManager(mLayoutManager);
        rvResultados.setAdapter(adapter);

        RxView.clicks(rutaPedido).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ((MainActivity) getContext()).irRutaOden(orden);
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
                ((MainActivity) getContext()).irDireccionEntrega(orden);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        RxView.clicks(comercioLy).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ((MainActivity) getContext()).irPerfilComercio(orden);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        RxView.clicks(transportistaLy).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ((MainActivity) getContext()).irPerfilTransportista(orden);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        inicializarRecorrido();
    }

    @Override
    public void allowBackPressed() {
        ((MainActivity) getContext()).getOpciones().setVisibility(View.VISIBLE);
    }

    public void inicializarRecorrido() {

        Glide.with(getContext()).load(R.drawable.ic_bag_grey)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(orden_recibida);
        Glide.with(getContext()).load(R.drawable.ic_moto_grey)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(delivery);
        Glide.with(getContext()).load(R.drawable.ic_face_grey)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(disfruta);

        text_orden_recibida.setTextColor(getResources().getColor(R.color.colorTextGris));
        text_delivery.setTextColor(getResources().getColor(R.color.colorTextGris));
        text_disfruta.setTextColor(getResources().getColor(R.color.colorTextGris));

        if (orden.getEstadoOrden().getId() == AppConstants.ESTADO_ORDEN_EN_PREPARACION
                || orden.getEstadoOrden().getId() == AppConstants.ESTADO_ORDEN_ORDEN_LISTA
                || orden.getEstadoOrden().getId() == AppConstants.ESTADO_ORDEN_ENTREGADA_A_DRIVER) {
            Glide.with(getContext()).load(R.drawable.ic_bag_orange)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(orden_recibida);
            text_orden_recibida.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (orden.getEstadoOrden().getId() == AppConstants.ESTADO_ORDEN_EN_RUTA
                || orden.getEstadoOrden().getId() == AppConstants.ESTADO_ORDEN_LLEGO_PUNTO_ENTREGA) {
            Glide.with(getContext()).load(R.drawable.ic_moto_orange)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(delivery);
            text_delivery.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (orden.getEstadoOrden().getId() == AppConstants.ESTADO_ORDEN_ENTREGADA_A_CLIENTE) {
            Glide.with(getContext()).load(R.drawable.ic_face_orange)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(disfruta);
            text_disfruta.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }

}
