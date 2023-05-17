package sv.com.guindapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import sv.com.guindapp.R;
import sv.com.guindapp.model.data.FormaPago;
import sv.com.guindapp.model.interfaces.OnItemClickListener;


public class FormaPagoAdapter extends RecyclerView.Adapter<FormaPagoAdapter.MyViewHolder> {
    Context context;
    private List<FormaPago> ofertasList;
    private final OnItemClickListener listener;

    public FormaPagoAdapter(List<FormaPago> ofertasList, Context context, OnItemClickListener listener) {
        this.ofertasList = ofertasList;
        this.context = context;
        this.listener = listener;
    }

    public void updateData(List<FormaPago> ofertasList) {
        this.ofertasList = ofertasList;
    }

    @NonNull
    @Override
    public FormaPagoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_forma_pago, viewGroup, false);
        return new FormaPagoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FormaPagoAdapter.MyViewHolder holder, final int position) {
        FormaPago item = ofertasList.get(position);
        holder.direccion.setText(item.getNombre());
        switch (item.getFormaPagoPK().getId()){
            case 1:
                Glide.with(context).load(R.drawable.efectivo).into(holder.img);
                break;
            case 2:
                Glide.with(context).load(R.drawable.tarjeta).into(holder.img);
                break;
        }
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return ofertasList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tipo, direccion;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            direccion = itemView.findViewById(R.id.direccion);
        }

        public void bind(final FormaPago item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
