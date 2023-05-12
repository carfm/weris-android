package sv.com.weris.ui.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;
import sv.com.weris.MainActivity;
import sv.com.weris.R;
import sv.com.weris.model.interfaces.FragmentFunctions;

/**
 * A simple {@link Fragment} subclass.
 */
public class MensajeConfirmaOrdenFragment extends Fragment implements FragmentFunctions {

    Button completar;

    public MensajeConfirmaOrdenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mensaje_confirma_orden, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        completar = view.findViewById(R.id.completar);
        RxView.clicks(completar).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ((MainActivity)getContext()).completarOrden();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void allowBackPressed() {
        ((MainActivity)getContext()).getOpciones().setVisibility(View.VISIBLE);
        ((MainActivity)getContext()).getBarra().setVisibility(View.VISIBLE);
    }


}
