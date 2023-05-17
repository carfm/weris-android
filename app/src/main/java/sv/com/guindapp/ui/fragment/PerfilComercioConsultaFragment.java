package sv.com.guindapp.ui.fragment;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jakewharton.rxbinding.view.RxView;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.functions.Action1;
import sv.com.guindapp.MainActivity;
import sv.com.guindapp.R;
import sv.com.guindapp.model.data.Comercio;
import sv.com.guindapp.model.data.Orden;
import sv.com.guindapp.model.interfaces.FragmentFunctions;

public class PerfilComercioConsultaFragment extends Fragment implements FragmentFunctions {

    private Orden orden;
    private Comercio comercio;
    CircleImageView logo;
    ImageView perfil;
    TextView nombreComercio;
    TextView subCategoria;
    TextView horario;
    TextView minimoConsumo;
    TextView calificacion;
    TextView acercaNosotros;
    TextView tiempoEspera;
    GoogleMap mMap;
    Button maps,waze;
    private LocationManager locationManager;
    Location location1;

    public PerfilComercioConsultaFragment() {
    }

    public PerfilComercioConsultaFragment(Orden orden) {
        this.orden = orden;
        this.comercio = orden.getComercio();
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            if (comercio.getLatitud() != null && comercio.getLongitud() != null) {
                System.out.println("Entro en ubicacion");
                LatLng sydney = new LatLng(comercio.getLatitud().doubleValue(), comercio.getLongitud().doubleValue());
                mMap.addMarker(new MarkerOptions()
                        .position(sydney)
                        .title(comercio.getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .draggable(true));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
                // Zoom in, animating the camera.
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }else{
                Toast.makeText(getContext(), "Comercio no tiene ubicacion", Toast.LENGTH_LONG).show();
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil_comercio_consulta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }*/

        /*try {
            Toast.makeText(getContext(), "Se obtiene su ubicacion", Toast.LENGTH_LONG).show();
            //locationManager = (LocationManager) getSystem  Service(Context.LOCATION_SERVICE);
            locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, locationListenerGPS);

        } catch (SecurityException e) {
            e.printStackTrace();
        }*/

        nombreComercio = view.findViewById(R.id.nombre_comercio);
        subCategoria = view.findViewById(R.id.sub_categoria);
        horario = view.findViewById(R.id.horario);
        minimoConsumo = view.findViewById(R.id.minimo_consumo);
        calificacion = view.findViewById(R.id.calificacion);
        acercaNosotros = view.findViewById(R.id.acerca_nosotros);
        tiempoEspera = view.findViewById(R.id.tiempo_espera);

        perfil = view.findViewById(R.id.img_producto);
        logo = view.findViewById(R.id.img_logo_comercio);

        nombreComercio.setText("¡Gracias por permitirnos entregarte\n" +
                "lo que pediste de "+comercio.getNombre()+"!");
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

        maps = view.findViewById(R.id.ir_maps);
        waze = view.findViewById(R.id.ir_waze);

        RxView.clicks(maps).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (comercio.getLatitud() != null && comercio.getLongitud() != null && location1 != null) {
                    String uri = "http://maps.google.com/maps?saddr=" + location1.getLatitude() +
                            "," + location1.getLongitude() + "&daddr=" + comercio.getLatitud().doubleValue()
                            + "," + comercio.getLongitud().doubleValue();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "Faltan ubicaciones para enviar", Toast.LENGTH_LONG).show();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

       /* RxView.clicks(waze).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (comercio.getLatitud() != null && comercio.getLongitud() != null && location1 != null) {
                    try {
                        String url = "waze://?ll=" + comercio.getLatitud().doubleValue() + "," + comercio.getLongitud().doubleValue() + "&navigate=yes";
                        System.out.println(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        Intent intent =
                                new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(getContext(), "Faltan ubicaciones para enviar", Toast.LENGTH_LONG).show();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });*/

    }

    @Override
    public void allowBackPressed() {
        ((MainActivity) getContext()).regresarOrden(orden);
    }

    //para obtener las ubicaciones latitud, latitud y altitud funcion con internet o sin internet
    //al usar internet mejora la presicion
    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            location1 = location;
            locationManager.removeUpdates(locationListenerGPS);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getContext(), "Favor habilitar GPS", Toast.LENGTH_SHORT).show();
        }
    };
}