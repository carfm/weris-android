package sv.com.weris.ui.fragment;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;
import sv.com.weris.MainActivity;
import sv.com.weris.R;
import sv.com.weris.model.data.Orden;
import sv.com.weris.model.interfaces.FragmentFunctions;

public class DireccionEnvioFragment extends Fragment implements FragmentFunctions {

    private Orden orden;
    private Button regresar;
    private GoogleMap mMap;
    Button maps,waze;
    private LocationManager locationManager;
    Location location1;

    public DireccionEnvioFragment() {
    }

    public DireccionEnvioFragment(Orden orden) {
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
            mMap = googleMap;
            LatLng sydney = new LatLng(orden.getDireccionCliente().getLatitud().doubleValue(), orden.getDireccionCliente().getLongitud().doubleValue());
            mMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title(orden.getCliente().getNombres())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    .draggable(true));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
            // Zoom in, animating the camera.
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_direccion_envio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        try {
            Toast.makeText(getContext(), "Se obtiene su ubicacion", Toast.LENGTH_LONG).show();
            //locationManager = (LocationManager) getSystem  Service(Context.LOCATION_SERVICE);
            locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, locationListenerGPS);

        } catch (SecurityException e) {
            e.printStackTrace();
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

        maps = view.findViewById(R.id.ir_maps);
        waze = view.findViewById(R.id.ir_waze);

        RxView.clicks(maps).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (orden.getDireccionCliente().getLatitud() != null && orden.getDireccionCliente().getLongitud() != null && location1 != null) {
                    String uri = "http://maps.google.com/maps?saddr=" + location1.getLatitude() +
                            "," + location1.getLongitude() + "&daddr=" + orden.getDireccionCliente().getLatitud().doubleValue()
                            + "," + orden.getDireccionCliente().getLongitud().doubleValue();
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

        RxView.clicks(waze).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (orden.getDireccionCliente().getLatitud() != null && orden.getDireccionCliente().getLongitud() != null && location1 != null) {
                    try {
                        String url = "waze://?ll=" + orden.getDireccionCliente().getLatitud().doubleValue() + "," + orden.getDireccionCliente().getLongitud().doubleValue() + "&navigate=yes";
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
        });

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

    @Override
    public void allowBackPressed() {
        ((MainActivity) getContext()).regresarOrden(orden);
    }
}