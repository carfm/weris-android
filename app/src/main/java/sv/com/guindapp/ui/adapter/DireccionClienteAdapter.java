package sv.com.guindapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sv.com.guindapp.R;
import sv.com.guindapp.model.data.DireccionCliente;
import sv.com.guindapp.model.interfaces.OnItemClickListener;


public class DireccionClienteAdapter extends RecyclerView.Adapter<DireccionClienteAdapter.MyViewHolder> {
    Context context;
    private List<DireccionCliente> ofertasList;
    private final OnItemClickListener listener;
    private final OnItemClickListener listener2;
    private boolean gestion;

    public DireccionClienteAdapter(List<DireccionCliente> ofertasList, Context context,
                                   OnItemClickListener listener, boolean gestion,
                                   OnItemClickListener listener2) {
        this.ofertasList = ofertasList;
        this.context = context;
        this.listener = listener;
        this.gestion = gestion;
        this.listener2 = listener2;
    }

    public void updateData(List<DireccionCliente> ofertasList) {
        this.ofertasList = ofertasList;
    }

    @NonNull
    @Override
    public DireccionClienteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_direccion, viewGroup, false);
        return new DireccionClienteAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DireccionClienteAdapter.MyViewHolder holder, final int position) {
        DireccionCliente item = ofertasList.get(position);
        String tipo = "";
        switch (item.getTipo()) {
            case "T":
                tipo = "Trabajo";
                break;
            case "C":
                tipo = "Casa";
                break;
            case "P":
                tipo = "Punto de Encuentro";
                break;
        }
        holder.tipo.setText(tipo);
        holder.direccion.setText(item.getDireccion());
        //holder.editar_item.setVisibility((gestion ? View.VISIBLE : View.GONE));
        holder.eliminar_item.setVisibility((gestion ? View.VISIBLE : View.GONE));
        if (gestion) {
            holder.eliminar_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onItemClick(item);
                }
            });
        }
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return ofertasList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tipo, direccion, editar_item;
        RelativeLayout eliminar_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tipo = itemView.findViewById(R.id.tipo);
            direccion = itemView.findViewById(R.id.direccion);
            editar_item = itemView.findViewById(R.id.editar_item);
            eliminar_item = itemView.findViewById(R.id.eliminar_item);
        }

        public void bind(final DireccionCliente item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
