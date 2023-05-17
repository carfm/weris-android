package sv.com.guindapp.ui.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;
import sv.com.guindapp.MainActivity;
import sv.com.guindapp.R;
import sv.com.guindapp.model.data.Cliente;

public class PerfilFragment extends Fragment {

    Button cerrarSesion;
    CircleImageView fotoPerfil;
    TextView nombre, correo;
    LinearLayout tusDirecciones;
    LinearLayout tusFormasDePago,acercaDeWeris,ayuda;
    Cliente cliente;

    public PerfilFragment() {
    }

    public PerfilFragment(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        cerrarSesion = v.findViewById(R.id.cerrar_sesion);
        tusDirecciones = v.findViewById(R.id.tus_direcciones);
        tusFormasDePago = v.findViewById(R.id.tus_formas_de_pago);
        acercaDeWeris = v.findViewById(R.id.acerca_weris);
        ayuda = v.findViewById(R.id.ayuda);
        fotoPerfil = v.findViewById(R.id.profile_image);
        nombre = v.findViewById(R.id.nombre);
        correo = v.findViewById(R.id.correo);
        //https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=2883549765028314&height=50&width=50&ext=1592945548&hash=AeTg729JU4bfJ2V0
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        if (cliente != null) {
            System.out.println("Direccion fot perfil: " + cliente.getNombres());
            nombre.setText(cliente.getNombres());
            correo.setText(cliente.getCorreo());
            Uri foto = mAuth.getCurrentUser().getPhotoUrl();
            if (foto != null) {
                Glide.with(this).load(foto.toString()).into(fotoPerfil);
                System.out.println("Entro a foto");
            } else {

            }
            cerrarSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getContext()).cerrarSesion();
                }
            });

            tusDirecciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getContext()).irDirecciones();
                }
            });

            tusFormasDePago.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getContext()).irFormasPagoTarjeta();
                }
            });

            acercaDeWeris.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getContext()).acercaDeWeris();
                }
            });

            ayuda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getContext()).ayuda();
                }
            });
        }
        super.onActivityCreated(savedInstanceState);
    }



}
