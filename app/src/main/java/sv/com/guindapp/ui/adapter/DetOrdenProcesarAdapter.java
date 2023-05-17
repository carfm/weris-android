package sv.com.guindapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import sv.com.guindapp.R;
import sv.com.guindapp.model.data.DetOrden;
import sv.com.guindapp.model.interfaces.OnItemClickListener;


public class DetOrdenProcesarAdapter extends RecyclerView.Adapter<DetOrdenProcesarAdapter.MyViewHolder> {
    Context context;
    private List<DetOrden> ofertasList;
    private final OnItemClickListener listener;

    public DetOrdenProcesarAdapter(List<DetOrden> ofertasList, Context context, OnItemClickListener listener) {
        this.ofertasList = ofertasList;
        this.context = context;
        this.listener = listener;
    }

    public void updateData(List<DetOrden> ofertasList) {
        this.ofertasList = ofertasList;
    }

    @NonNull
    @Override
    public DetOrdenProcesarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_det_orden_procesar, viewGroup, false);
        return new DetOrdenProcesarAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetOrdenProcesarAdapter.MyViewHolder holder, final int position) {
        DetOrden item = ofertasList.get(position);
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        holder.cantidad.setText(item.getCantidad().setScale(0,RoundingMode.HALF_UP).toString());
        holder.nombre.setText(item.getDescripcion());
        holder.precio.setText("$" + formatter.format(item.getSubtotal().setScale(2, RoundingMode.HALF_UP)));
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return ofertasList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cantidad, nombre, precio;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            /*img = itemView.findViewById(R.id.img);*/
            cantidad = itemView.findViewById(R.id.cantidad);
            nombre = itemView.findViewById(R.id.nombre);
            precio = itemView.findViewById(R.id.precio);
        }

        public void bind(final DetOrden item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
