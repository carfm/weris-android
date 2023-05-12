package sv.com.weris.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import sv.com.weris.R;
import sv.com.weris.model.data.Comercio;
import sv.com.weris.model.data.TipoProdComercio;
import sv.com.weris.model.interfaces.OnItemClickListener;


public class TipoProdComercioAdapter extends RecyclerView.Adapter<TipoProdComercioAdapter.MyViewHolder> {
    Context context;
    private List<TipoProdComercio> ofertasList;
    private final OnItemClickListener listener;
    Comercio comercio;

    public TipoProdComercioAdapter(List<TipoProdComercio> ofertasList, Context context, OnItemClickListener listener,Comercio comercio) {
        this.ofertasList = ofertasList;
        this.context = context;
        this.listener = listener;
        this.comercio = comercio;
    }

    public void updateData(List<TipoProdComercio> ofertasList) {
        this.ofertasList = ofertasList;
    }

    @NonNull
    @Override
    public TipoProdComercioAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_tipo_prod_list, viewGroup, false);
        return new TipoProdComercioAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TipoProdComercioAdapter.MyViewHolder holder, final int position) {
        TipoProdComercio item = ofertasList.get(position);
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        holder.nombre.setText(item.getDescripcion());
        holder.productoSliderAdapter = new ProductoSliderAdapter(item.getProductos(), context,this.listener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.cardSliderViewPager.setLayoutManager(mLayoutManager);
        holder.cardSliderViewPager.setAdapter(holder.productoSliderAdapter);
    }

    @Override
    public int getItemCount() {
        return ofertasList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;
        ProductoSliderAdapter productoSliderAdapter;
        RecyclerView cardSliderViewPager;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            cardSliderViewPager = itemView.findViewById(R.id.viewPager);

        }


    }

}
