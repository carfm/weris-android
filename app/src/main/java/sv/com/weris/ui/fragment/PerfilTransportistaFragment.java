package sv.com.weris.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import sv.com.weris.MainActivity;
import sv.com.weris.R;
import sv.com.weris.model.data.Orden;
import sv.com.weris.model.data.Transportista;
import sv.com.weris.model.interfaces.FragmentFunctions;


public class PerfilTransportistaFragment extends Fragment implements FragmentFunctions {

    private Orden orden;
    ImageView fotoPerfil;
    TextView nombre, correo,telefono;
    Transportista transportista;
    ImageView imgTransporte;

    public PerfilTransportistaFragment() {
        // Required empty public constructor
    }

    public PerfilTransportistaFragment(Orden orden) {
        this.orden = orden;
        this.transportista = orden.getTransportista();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil_transportista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fotoPerfil = view.findViewById(R.id.profile_image);
        nombre = view.findViewById(R.id.nombre);
        correo = view.findViewById(R.id.correo);
        telefono = view.findViewById(R.id.telefono);
        imgTransporte = view.findViewById(R.id.img_transporte);

        nombre.setText(transportista.getNombres());
        correo.setText("info@weris.app ");
        telefono.setText(transportista.getCelular());

        //Glide.with(getContext()).load(transportista.getUrlPerfil()).into(fotoPerfil);

        //Glide.with(getContext()).load(transportista.getUrlVehiculo()).into(imgTransporte);
    }

    @Override
    public void allowBackPressed() {
        ((MainActivity) getContext()).regresarOrden(orden);
    }
}