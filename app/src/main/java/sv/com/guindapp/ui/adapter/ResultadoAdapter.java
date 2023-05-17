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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import sv.com.guindapp.R;
import sv.com.guindapp.model.data.Comercio;
import sv.com.guindapp.model.interfaces.OnItemClickListener;


public class ResultadoAdapter extends RecyclerView.Adapter<ResultadoAdapter.MyViewHolder> {
    Context context;
    private ArrayList<Comercio> ofertasList;
    private final OnItemClickListener listener;

    public ResultadoAdapter(ArrayList<Comercio> ofertasList, Context context, OnItemClickListener listener) {
        this.ofertasList = ofertasList;
        this.context = context;
        this.listener = listener;
    }

    public void updateData(ArrayList<Comercio> ofertasList) {
        this.ofertasList = ofertasList;
    }

    @NonNull
    @Override
    public ResultadoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_resultado, viewGroup, false);
        return new ResultadoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResultadoAdapter.MyViewHolder holder, final int position) {
        Comercio item = ofertasList.get(position);
        holder.nombre.setText(item.getNombre());
        holder.categoria.setText(item.getSubCategoria().getNombre());
        if (item.getUrl() != null) {
            Glide.with(context).load(item.getUrl()).into(holder.logo);
        }
        if (item.getUrlPerfil() != null) {
            System.out.println("Imagen: "+ item.getUrlPerfil());
            Glide.with(context).load(item.getUrlPerfil()).into(holder.img);
        }

        /*GlideApp.with(holder.itemView.getContext())
                .load("http://3.211.243.157:8080/guindapp/img/GUINDAPP/HM-01")
                .listener(new RequestListener<Drawable>(){
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        e.printStackTrace();
                       // Log.e("onLoadFailed", ofertasList.get(position).getFoto());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        //Log.e("onResourceReady", ofertasList.get(position).getFoto());
                        return false;
                    }

                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img);
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
            img = itemView.findViewById(R.id.img);
            nombre = itemView.findViewById(R.id.nombre);
            categoria = itemView.findViewById(R.id.categoria);
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

        CircleImageView logo;
        ImageView img;
        TextView nombre, categoria;

        ComercioHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);
            img = itemView.findViewById(R.id.img);
            nombre = itemView.findViewById(R.id.nombre);
            categoria = itemView.findViewById(R.id.categoria);
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
}
