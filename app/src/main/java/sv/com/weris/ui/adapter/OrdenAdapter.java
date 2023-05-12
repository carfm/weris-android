package sv.com.weris.ui.adapter;

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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import sv.com.weris.R;
import sv.com.weris.model.data.Pedido;
import sv.com.weris.model.interfaces.OnItemClickListener;


public class OrdenAdapter extends RecyclerView.Adapter<OrdenAdapter.MyViewHolder> {
    Context context;
    private List<Pedido> ofertasList;
    private final OnItemClickListener listener;

    public OrdenAdapter(List<Pedido> ofertasList, Context context, OnItemClickListener listener) {
        this.ofertasList = ofertasList;
        this.context = context;
        this.listener = listener;
    }

    public void updateData(List<Pedido> ofertasList) {
        this.ofertasList = ofertasList;
    }

    @NonNull
    @Override
    public OrdenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_orden, viewGroup, false);
        return new OrdenAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrdenAdapter.MyViewHolder holder, final int position) {


        Pedido item = ofertasList.get(position);
        holder.comercio.setText(item.getOrden().getComercio().getNombre());
        holder.descripcion.setText(item.getOrden().getComercio().getSubCategoria().getCategoria().getNombre());
        //Formato
        String total = item.getOrden().getTotal().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        holder.total.setText("Total: $ " + total);
        holder.fecha.setText("Fecha: " + (new SimpleDateFormat("dd/MM/yyyy").format(item.getOrden().getFechaOrden())));
        holder.estado.setText(item.getOrden().getEstadoOrden().getNombre());//Estado
        Glide.with(context).load(item.getOrden().getComercio().getUrl()).into(holder.img);

       /* if (position % 2 == 0){
            holder.l1.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.comercio.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            holder.descripcion.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            holder.fecha.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            holder.estado.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }else{
            holder.l1.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            holder.comercio.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.descripcion.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.fecha.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.estado.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }*/
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return ofertasList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView comercio, descripcion, fecha, estado, total;
        LinearLayout l1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            comercio = itemView.findViewById(R.id.comercio);
            descripcion = itemView.findViewById(R.id.descripcion);
            fecha = itemView.findViewById(R.id.fecha);
            estado = itemView.findViewById(R.id.estado);
            total = itemView.findViewById(R.id.total);
            l1 = itemView.findViewById(R.id.l1);
        }

        public void bind(final Pedido item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
