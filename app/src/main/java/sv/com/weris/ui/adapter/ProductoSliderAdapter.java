package sv.com.weris.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.islamkhsh.CardSliderAdapter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import sv.com.weris.R;
import sv.com.weris.model.data.Producto;
import sv.com.weris.model.interfaces.OnItemClickListener;

public class ProductoSliderAdapter extends CardSliderAdapter<ProductoSliderAdapter.ProductoViewHolder> {


    private List<Producto> movies;
    Context context;
    private final OnItemClickListener listener;

    public ProductoSliderAdapter(List<Producto> movies, Context context, OnItemClickListener listener) {
        this.movies = movies;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false);
        return new ProductoViewHolder(view);
    }


    @Override
    public void bindVH(ProductoViewHolder holder, int i) {
        Producto item = movies.get(i);
        holder.producto.setText(item.getNombre());
        holder.descripcion.setText(item.getDescripcion());
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        holder.precio.setText("$ " + formatter.format(item.getPrecio().setScale(2, RoundingMode.HALF_UP)));
        Glide.with(context).load(item.getUrl()).into(holder.imageView);
        holder.bind(item, listener);
    }


    class ProductoViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView producto,descripcion;
        TextView precio;

        public ProductoViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.img_prod);
            producto = view.findViewById(R.id.producto);
            descripcion = view.findViewById(R.id.descripcion);
            precio = view.findViewById(R.id.precio);
        }

        public void bind(final Producto item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
