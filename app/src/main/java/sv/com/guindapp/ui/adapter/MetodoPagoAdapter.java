package sv.com.guindapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sv.com.guindapp.R;
import sv.com.guindapp.model.data.MetodoPago;
import sv.com.guindapp.model.interfaces.OnItemClickListener;


public class MetodoPagoAdapter extends RecyclerView.Adapter<MetodoPagoAdapter.MyViewHolder> {
    Context context;
    private List<MetodoPago> ofertasList;
    private final OnItemClickListener listener;
    private boolean mostrarEliminar;

    public MetodoPagoAdapter(List<MetodoPago> ofertasList, Context context, OnItemClickListener listener, boolean mostrarEliminar) {
        this.ofertasList = ofertasList;
        this.context = context;
        this.listener = listener;
        this.mostrarEliminar = mostrarEliminar;
    }

    public void updateData(List<MetodoPago> ofertasList) {
        this.ofertasList = ofertasList;
    }

    @NonNull
    @Override
    public MetodoPagoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_metodo_pago, viewGroup, false);
        return new MetodoPagoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MetodoPagoAdapter.MyViewHolder holder, final int position) {
        MetodoPago item = ofertasList.get(position);
        holder.eliminar_item.setVisibility((mostrarEliminar ? View.VISIBLE : View.GONE));
        String tipo = item.getNombre();
        holder.tipo.setText(tipo);
        holder.direccion.setText(item.getMascara());
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return ofertasList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tipo, direccion, eliminar_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tipo = itemView.findViewById(R.id.tipo);
            direccion = itemView.findViewById(R.id.direccion);
            eliminar_item = itemView.findViewById(R.id.eliminar_item);
        }

        public void bind(final MetodoPago item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
