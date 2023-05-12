package sv.com.weris.ui.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import sv.com.weris.MainActivity;
import sv.com.weris.R;
import sv.com.weris.api.RetrofitClient;
import sv.com.weris.model.data.Comercio;
import sv.com.weris.model.data.DetOrden;
import sv.com.weris.model.data.Orden;
import sv.com.weris.model.data.Producto;
import sv.com.weris.model.interfaces.FragmentFunctions;
import sv.com.weris.model.interfaces.IParametro;
import sv.com.weris.model.interfaces.OnItemClickListener;
import sv.com.weris.service.ServicesAPI;
import sv.com.weris.ui.adapter.DetOrdenResumenAdapter;
import sv.com.weris.ui.adapter.ProductoResumenAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumenOrdenFragment extends Fragment implements FragmentFunctions {

    private RecyclerView rvResultados;
    private DetOrdenResumenAdapter adapter;
    private LinearLayout agregarMas, cancelar;
    private List<DetOrden> detOrdenList;
    private Button procesarOrden;
    private Orden orden;
    private TextView subtotal;
    RecyclerView cardSliderViewPager;
    ArrayList<Producto> productoList;
    ProductoResumenAdapter productoSliderAdapter;

    public ResumenOrdenFragment() {
        // Required empty public constructor
    }

    public ResumenOrdenFragment(Orden orden) {
        // Required empty public constructor
        this.orden = orden;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resumen_orden, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardSliderViewPager = getActivity().findViewById(R.id.viewPager);
        rvResultados = view.findViewById(R.id.rv_det_orden);
        procesarOrden = view.findViewById(R.id.fijar);
        subtotal = view.findViewById(R.id.subtotal);
        agregarMas = view.findViewById(R.id.rl_agregar);
        cancelar = view.findViewById(R.id.cancelar);
        adapter = new DetOrdenResumenAdapter(orden.getDetOrdenList(), getContext(), new OnItemClickListener() {
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
                ((MainActivity) getContext()).verProcesarOrden();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        RxView.clicks(agregarMas).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                allowBackPressed();
                ((MainActivity) getContext()).agregarMas(orden.getComercio());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        RxView.clicks(cancelar).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ((MainActivity) getContext()).cancelarOrden();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        subtotal.setText("$ " + formatter.format(orden.getSubtotal().setScale(2, RoundingMode.HALF_UP)));
        obtenerProductos();
    }

    @Override
    public void allowBackPressed() {
        ((MainActivity) getContext()).getOpciones().setVisibility(View.VISIBLE);
    }

    public void obtenerProductos() {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Comercio comercio = orden.getComercio();
        Call<List<Producto>> call = servicesAPI.productosPorComercio(comercio.getComercioPK().getIdCompania(), comercio.getComercioPK().getIdAfiliado(), comercio.getComercioPK().getId());
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    List<Producto> ofertas = response.body();
                    productoList = new ArrayList<>();
                    System.out.println("Tamaño: " + ofertas.size());
                    for (Producto c : ofertas) {
                        productoList.add(c);
                    }
                    llenarProductos();
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
//                esta.setText(t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void llenarProductos() {
        productoSliderAdapter = new ProductoResumenAdapter(productoList, getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {
                ((MainActivity) getContext()).seleccionProducto(orden.getComercio(), (Producto) item);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        cardSliderViewPager.setLayoutManager(mLayoutManager);

        cardSliderViewPager.setAdapter(productoSliderAdapter);
    }

}
