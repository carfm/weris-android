package sv.com.guindapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import sv.com.guindapp.MainActivity;
import sv.com.guindapp.R;
import sv.com.guindapp.model.data.Comercio;
import sv.com.guindapp.model.data.Orden;
import sv.com.guindapp.model.data.Producto;
import sv.com.guindapp.model.data.TipoProdComercio;
import sv.com.guindapp.model.interfaces.IParametro;
import sv.com.guindapp.model.interfaces.OnItemClickListener;
import sv.com.guindapp.api.RetrofitClient;
import sv.com.guindapp.service.ServicesAPI;
import sv.com.guindapp.ui.adapter.TipoProdComercioAdapter;
import sv.com.guindapp.util.Cargador;

public class PerfilComercioFragment extends Fragment {

    RecyclerView cardSliderViewPager;
    TipoProdComercioAdapter productoSliderAdapter;
    RelativeLayout miOrden,barra;
    Button verOrden;
    private Comercio comercio;
    ArrayList<TipoProdComercio> productoList;
    CircleImageView logo;
    ImageView perfil;
    TextView nombreComercio;
    TextView subCategoria;
    TextView horario;
    TextView minimoConsumo;
    TextView calificacion;
    TextView acercaNosotros;
    TextView tiempoEspera;

    public PerfilComercioFragment() {
        // Required empty public constructor
    }

    public PerfilComercioFragment(Comercio comercio) {
        // Required empty public constructor
        this.comercio = comercio;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_comercio, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cardSliderViewPager = getActivity().findViewById(R.id.viewPager);
        miOrden = getActivity().findViewById(R.id.rl_ver);
        verOrden = getActivity().findViewById(R.id.resumen_orden);
        nombreComercio = getActivity().findViewById(R.id.nombre_comercio);
        subCategoria = getActivity().findViewById(R.id.sub_categoria);
        horario = getActivity().findViewById(R.id.horario);
        minimoConsumo = getActivity().findViewById(R.id.minimo_consumo);
        calificacion = getActivity().findViewById(R.id.calificacion);
        acercaNosotros = getActivity().findViewById(R.id.acerca_nosotros);
        tiempoEspera = getActivity().findViewById(R.id.horario);

        perfil = getActivity().findViewById(R.id.img_producto);
        logo = getActivity().findViewById(R.id.img_logo_comercio);

        barra = getActivity().findViewById(R.id.barra);

        //barra.setVisibility(View.GONE);

        nombreComercio.setText(comercio.getNombre());
        subCategoria.setText(comercio.getSubCategoria().getNombre());
        acercaNosotros.setText(comercio.getAcercaDe());
        minimoConsumo.setText(comercio.getConsumoMinimo() != null ? comercio.getConsumoMinimo().toString() : null);
        tiempoEspera.setText(comercio.getTiempoEspera() != null ? comercio.getTiempoEspera().toString() + " min" : null);

        if (comercio.getUrl() != null) {
            Glide.with(this).load(comercio.getUrl()).into(logo);
        }
        if (comercio.getUrlPerfil() != null) {
            Glide.with(this).load(comercio.getUrlPerfil()).into(perfil);
        }

        Orden o = ((MainActivity) getContext()).getOrden();
        System.out.println("Orden: " + o);
        if (o != null) {
            miOrden.setVisibility(View.VISIBLE);
            RxView.clicks(verOrden).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    ((MainActivity) getContext()).verResumenOrden();
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
            DecimalFormat formatter = new DecimalFormat("#,##0.00");
            verOrden.setText("Resumen de Orden - " + "$ " + formatter.format(o.getSubtotal().setScale(2, RoundingMode.HALF_UP)));
        } else {
            miOrden.setVisibility(View.GONE);
        }
        obtenerProductos();
    }

    public void obtenerProductos() {
        //Cargador.show((MainActivity) getContext());
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<List<TipoProdComercio>> call = servicesAPI.tipoProdByComercio(comercio.getComercioPK().getIdCompania(), comercio.getComercioPK().getIdAfiliado(), comercio.getComercioPK().getId());
        call.enqueue(new Callback<List<TipoProdComercio>>() {
            @Override
            public void onResponse(Call<List<TipoProdComercio>> call, Response<List<TipoProdComercio>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    List<TipoProdComercio> ofertas = response.body();
                    productoList = new ArrayList<>();
                    System.out.println("Tamaño: " + ofertas.size());
                    for (TipoProdComercio c : ofertas) {
                        productoList.add(c);
                    }
                    llenarTipoProdComercios();
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
                //Cargador.hide();
            }

            @Override
            public void onFailure(Call<List<TipoProdComercio>> call, Throwable t) {
//                esta.setText(t.getMessage());
                //Cargador.hide();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void llenarTipoProdComercios() {
        productoSliderAdapter = new TipoProdComercioAdapter(productoList, getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {
                ((MainActivity) getContext()).seleccionProducto(comercio, (Producto) item);
            }
        },comercio);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cardSliderViewPager.setLayoutManager(mLayoutManager);
        cardSliderViewPager.setAdapter(productoSliderAdapter);
    }

 /*   public void llenarProductos() {
        productoSliderAdapter = new ProductoSliderAdapter(productoList, getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {
                ((MainActivity) getContext()).seleccionProducto(comercio, (Producto) item);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cardSliderViewPager.setLayoutManager(mLayoutManager);
        cardSliderViewPager.setAdapter(productoSliderAdapter);
    }*/

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }
}
