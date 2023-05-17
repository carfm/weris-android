package sv.com.guindapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import sv.com.guindapp.R;
import sv.com.guindapp.model.data.DetOrden;
import sv.com.guindapp.model.interfaces.OnItemClickListener;


public class DetOrdenResumenAdapter extends RecyclerView.Adapter<DetOrdenResumenAdapter.MyViewHolder> {
    Context context;
    private List<DetOrden> ofertasList;
    private final OnItemClickListener listener;

    public DetOrdenResumenAdapter(List<DetOrden> ofertasList, Context context, OnItemClickListener listener) {
        this.ofertasList = ofertasList;
        this.context = context;
        this.listener = listener;
    }

    public void updateData(List<DetOrden> ofertasList) {
        this.ofertasList = ofertasList;
    }

    @NonNull
    @Override
    public DetOrdenResumenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_det_orden, viewGroup, false);
        return new DetOrdenResumenAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetOrdenResumenAdapter.MyViewHolder holder, final int position) {
        DetOrden item = ofertasList.get(position);
        holder.nombre.setText(item.getProducto().getNombre());
        holder.descripcion.setText(item.getProducto().getDescripcion());
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        holder.subtotal.setText("$ " + formatter.format(item.getSubtotal().setScale(2, RoundingMode.HALF_UP)));
        Glide.with(context).load(item.getProducto().getUrl()).into(holder.img);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return ofertasList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView nombre, subtotal, descripcion;
        LinearLayout l1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            subtotal = itemView.findViewById(R.id.subtotal);
            descripcion = itemView.findViewById(R.id.descripcion);
            img = itemView.findViewById(R.id.img);
            /*img = itemView.findViewById(R.id.img);
            comercio = itemView.findViewById(R.id.comercio);
            descripcion = itemView.findViewById(R.id.descripcion);
            fecha = itemView.findViewById(R.id.fecha);
            estado = itemView.findViewById(R.id.estado);
            l1 = itemView.findViewById(R.id.l1);*/
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
