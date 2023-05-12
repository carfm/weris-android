package sv.com.weris.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import sv.com.weris.R;
import sv.com.weris.model.data.ProdAgregado;
import sv.com.weris.model.data.Producto;
import sv.com.weris.model.interfaces.Listado;
import sv.com.weris.model.interfaces.OnItemClickListener;


public class ProdAgregadoAdapter extends RecyclerView.Adapter<ProdAgregadoAdapter.MyViewHolder> {
    Context context;
    private List<ProdAgregado> ofertasList;
    private final OnItemClickListener listener;
    Producto producto;

    public ProdAgregadoAdapter(List<ProdAgregado> ofertasList, Context context, OnItemClickListener listener, Producto producto) {
        this.ofertasList = ofertasList;
        this.context = context;
        this.listener = listener;
        this.producto = producto;
    }

    public void updateData(List<ProdAgregado> ofertasList) {
        this.ofertasList = ofertasList;
    }

    @NonNull
    @Override
    public ProdAgregadoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.seleccion_prod_listados, viewGroup, false);
        return new ProdAgregadoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProdAgregadoAdapter.MyViewHolder holder, final int position) {
        ProdAgregado item = ofertasList.get(position);
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        holder.nombre.setText(item.getNombre() + "\n" + (item.getPrecio() != null ?
                ("+ $" + formatter.format(item.getPrecio()
                .setScale(2, RoundingMode.HALF_UP))) : "")+" ("+item.getDescripcion()+")");
        holder.bind(item, listener);
        item.setCheckBox(holder.seleccion);
        holder.seleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(item);
            }
        });
        holder.seleccionRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(item);
            }
        });
        item.setRadioButton(holder.seleccionRadio);
    }

    @Override
    public int getItemCount() {
        return ofertasList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;
        CheckBox seleccion;
        RadioButton seleccionRadio;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            seleccion = itemView.findViewById(R.id.seleccion);
            seleccionRadio = itemView.findViewById(R.id.seleccion_radio);
            if (producto.getTipoSeleccionAgregados() == 1) {
                seleccionRadio.setVisibility(View.VISIBLE);
                seleccion.setVisibility(View.GONE);
            } else {
                seleccionRadio.setVisibility(View.GONE);
                seleccion.setVisibility(View.VISIBLE);
            }
        }

        public void bind(final Listado item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //listener.onItemClick(item);
                    if (seleccionRadio.getVisibility() == View.VISIBLE) {
                        if (seleccionRadio.isChecked()) {
                            seleccionRadio.setChecked(false);
                        } else {
                            seleccionRadio.setChecked(true);
                        }
                        listener.onItemClick(item);
                    }

                }
            });
        }
    }

}
