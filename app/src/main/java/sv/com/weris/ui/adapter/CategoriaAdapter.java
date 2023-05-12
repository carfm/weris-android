package sv.com.weris.ui.adapter;

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

import sv.com.weris.R;
import sv.com.weris.model.data.Categoria;
import sv.com.weris.model.data.Comercio;
import sv.com.weris.model.interfaces.OnItemClickListener;


public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.MyViewHolder> {
    Context context;
    private List<Categoria> ofertasList;
    private final OnItemClickListener listener;

    public CategoriaAdapter(List<Categoria> ofertasList, Context context, OnItemClickListener listener) {
        this.ofertasList = ofertasList;
        this.context = context;
        this.listener = listener;
    }

    public void updateData(List<Categoria> ofertasList) {
        this.ofertasList = ofertasList;
    }

    @NonNull
    @Override
    public CategoriaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_categoria, viewGroup, false);
        return new CategoriaAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoriaAdapter.MyViewHolder holder, final int position) {
        Categoria item = ofertasList.get(position);
        holder.nombre.setText(item.getNombre());
        holder.categoria.setText(item.getDescripcion());
        if (item.getUrl() != null) {
            System.out.println("Imagen: "+ item.getUrl());
            Glide.with(context).load(item.getUrl()).into(holder.img);
            /*Glide.with(context).load(item.getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(holder.img);*/
            /*Picasso.get()
                    .load(item.getUrl())
                    .error(R.drawable.rounded_background_item)
                    .into(holder.img);*/
        }
        /*if (item.getUrlPerfil() != null) {
            System.out.println("Imagen: "+ item.getUrlPerfil());
            Glide.with(context).load(item.getUrlPerfil()).into(holder.img);
        }*

         */

        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return ofertasList.size();
    }


    public class MyViewHolder extends ComercioHolder {


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);
            img = itemView.findViewById(R.id.imagen);
            nombre = itemView.findViewById(R.id.nombre);
            categoria = itemView.findViewById(R.id.descripcion);
        }

        public void bind(final Comercio item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


    public static class ComercioHolder extends RecyclerView.ViewHolder {

        ImageView logo;
        ImageView img;
        TextView nombre, categoria;

        ComercioHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);
            img = itemView.findViewById(R.id.imagen);
            nombre = itemView.findViewById(R.id.nombre);
            categoria = itemView.findViewById(R.id.descripcion);
        }

        public void bind(final Categoria item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
