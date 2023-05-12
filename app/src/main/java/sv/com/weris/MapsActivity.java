package sv.com.weris;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.rxbinding.view.RxView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import sv.com.weris.api.RetrofitClient;
import sv.com.weris.model.data.DireccionCliente;
import sv.com.weris.service.ServicesAPI;
import sv.com.weris.util.Util;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    Location location;
    Context context;
    FloatingActionButton fab;
    Button fijar;
    Integer idCompania;
    Integer idCliente;
    Integer idDireccionCliente;
    DireccionCliente direccionCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        context = this;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        Bundle b = getIntent().getExtras();
        if (b != null) {
            idCompania = b.getInt("idCompania");
            idCliente = b.getInt("idCliente");
            idDireccionCliente = b.getInt("idDireccionCliente");
            //Viene para edicion y hay que buscar la direccion cliente
            obtenerDireccionCliente();

        } else {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            obtenerMapa();
        }

        findViewById(R.id.img_logo).setVisibility(View.GONE);
        TextView tx = findViewById(R.id.textEmpresa);
        tx.setVisibility(View.VISIBLE);
        tx.setText("Buscar dirección");
        RelativeLayout atras = findViewById(R.id.back);

        RxView.clicks(atras).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                onBackPressed();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                Location targetLocation = new Location("");//provider name is unnecessary
                targetLocation.setLatitude(latLng.latitude);//your coords of course
                targetLocation.setLongitude(latLng.longitude);
                location = targetLocation;
                LatLng sydney = new LatLng(latLng.latitude, latLng.longitude);
                mMap.addMarker(new MarkerOptions()
                        .position(sydney)
                        .title(latLng.latitude + " : " + latLng.longitude)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .draggable(true));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
                // Zoom in, animating the camera.
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }
        });
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerUbicacionActual();
            }
        });
        fijar = findViewById(R.id.fijar);
        RxView.clicks(fijar).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {


                finish();
                enviarGestion();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        if (direccionCliente == null) {
            obtenerUbicacionActual();
        } else {
            LatLng sydney = new LatLng(direccionCliente.getLatitud().doubleValue(), direccionCliente.getLongitud().doubleValue());
            mMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title("Ubicacion")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    .draggable(true));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
            // Zoom in, animating the camera.
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        }
    }

    public void enviarGestion() {


        Intent intent = new Intent(MapsActivity.this, MainActivity.class);
        Bundle b = new Bundle();
        b.putDouble("latitud", location.getLatitude()); //Your id
        b.putDouble("longitud", location.getLongitude());
        b.putInt("edicion", (direccionCliente == null ? 0 : 1));
        if (direccionCliente != null) {
            b.putInt("idCompania", idCompania);
            b.putInt("idCliente", idCliente);
            b.putInt("idDireccionCliente", idDireccionCliente);
        }

        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        finish();


        /*DireccionCliente direccionCliente = new DireccionCliente();
        direccionCliente.setLongitud(new BigDecimal(location.getLongitude()));
        direccionCliente.setLatitud(new BigDecimal(location.getLatitude()));
        GestionDireccionFragment fragment = new GestionDireccionFragment(direccionCliente);
        //nombre = "GestionDireccionFragment";
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "GestionDireccionFragment")
                .commit();*/
    }

    public void obtenerUbicacionActual() {
        Toast.makeText(this, "Obteniendo ubicacion actual", Toast.LENGTH_LONG).show();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //locationManager = ((LocationManager))getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, locationListenerGPS);
    }

    //para obtener las ubicaciones latitud, latitud y altitud funcion con internet o sin internet
    //al usar internet mejora la presicion
    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(Location l) {
            // double latitude=location.getLatitude();
            //double longitude=location.getLongitude();
            System.out.println("llego aca");
            location = l;
            locationManager.removeUpdates(locationListenerGPS);
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title("Ubicacion Actual")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    .draggable(true));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
            // Zoom in, animating the camera.
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(context, "Favor habilitar GPS", Toast.LENGTH_SHORT).show();
        }
    };

    public void obtenerDireccionCliente() {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<DireccionCliente> call = servicesAPI.findDireccionCliente(idCompania, idCliente, idDireccionCliente);
        call.enqueue(new Callback<DireccionCliente>() {
            @Override
            public void onResponse(Call<DireccionCliente> call, Response<DireccionCliente> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    DireccionCliente ofertas = response.body();
                    direccionCliente = ofertas;
                    location = new Location("");
                    location.setLatitude(direccionCliente.getLatitud().doubleValue());
                    location.setLongitude(direccionCliente.getLongitud().doubleValue());
                    System.out.println("Tamaño: " + ofertas);
                    obtenerMapa();
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<DireccionCliente> call, Throwable t) {
//                esta.setText(t.getMessage());
                System.out.println(t.getMessage());
                //Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void obtenerMapa() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        Util.activity(this, new DireccionesActivity(), false);
        super.onBackPressed();
    }
}
