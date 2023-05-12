package sv.com.weris.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import sv.com.weris.MainActivity;
import sv.com.weris.R;
import sv.com.weris.model.data.Orden;
import sv.com.weris.model.data.TrakingOrden;
import sv.com.weris.model.interfaces.FragmentFunctions;
import sv.com.weris.api.RetrofitClient;
import sv.com.weris.service.ServicesAPI;

public class RutaOrdenFragment extends Fragment implements FragmentFunctions {

    private Orden orden;
    private GoogleMap gMap;
    private List<TrakingOrden> trakingOrdenList;
    private Polyline polyline1;
    private Button regresar;
    private Disposable d;
    private Boolean primeraVez;

    public RutaOrdenFragment() {
    }

    public RutaOrdenFragment(Orden orden) {
        this.orden = orden;
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            gMap = googleMap;
            primeraVez = true;
            obtenerPuntos();
            iniciarActualizar();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ruta_orden, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        regresar = view.findViewById(R.id.regresar);
        RxView.clicks(regresar).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                allowBackPressed();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public void llenarRuta() {
        // Add polylines to the map.
        // Polylines are useful to show a route or some other connection between points.
        List<LatLng> latLngs = new ArrayList<>();
        for (TrakingOrden trakingOrden : trakingOrdenList) {
            LatLng lng = new LatLng(trakingOrden.getLatitud().doubleValue(), trakingOrden.getLongitud().doubleValue());
            latLngs.add(lng);
        }

        if (!latLngs.isEmpty()) {
            gMap.clear();
            gMap.addMarker(new MarkerOptions()
                    .position(latLngs.get(0))
                    .title("Inicio")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    .draggable(true));

            polyline1 = gMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .addAll(latLngs)
                    .color(getContext().getResources().getColor(R.color.colorPrimary))
                    .width(10)
            );
            polyline1.setJointType(JointType.ROUND);

            gMap.addMarker(new MarkerOptions()
                    .position(latLngs.get(latLngs.size() - 1))
                    .title("Fin")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    .draggable(true));

            if (primeraVez) {
                //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(0), 15));
                // Zoom in, animating the camera.
                gMap.animateCamera(CameraUpdateFactory.zoomIn());
                // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                gMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                primeraVez = false;
            }
        }
    }

    public void obtenerPuntos() {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<List<TrakingOrden>> call = servicesAPI.buscarPorOrden(orden.getOrdenPK().getIdCompania(), orden.getOrdenPK().getIdAfiliado(), orden.getOrdenPK().getIdComercio(), orden.getOrdenPK().getId());
        call.enqueue(new Callback<List<TrakingOrden>>() {
            @Override
            public void onResponse(Call<List<TrakingOrden>> call, Response<List<TrakingOrden>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    List<TrakingOrden> ofertas = response.body();
                    trakingOrdenList = ofertas;
                    System.out.println("Tamaño: " + ofertas.size());
                    llenarRuta();

                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<TrakingOrden>> call, Throwable t) {
//                esta.setText(t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void iniciarActualizar() {
        d = Observable.interval(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> {
                    // update your ui here
                    System.out.println("Hola");
                    obtenerPuntos();
                    //d.dispose();
                }, e -> {
                    e.printStackTrace();
                });

    }


    @Override
    public void allowBackPressed() {
        if (d != null) {
            d.dispose();
        }
        ((MainActivity) getContext()).regresarOrden(orden);
    }
}