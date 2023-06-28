package sv.com.guindapp.ui.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.shawnlin.numberpicker.NumberPicker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import sv.com.guindapp.MainActivity;
import sv.com.guindapp.R;
import sv.com.guindapp.api.RetrofitClient;
import sv.com.guindapp.model.data.Comercio;
import sv.com.guindapp.model.data.ProdAdicionales;
import sv.com.guindapp.model.data.ProdAgregado;
import sv.com.guindapp.model.data.ProdOpciones;
import sv.com.guindapp.model.data.ProdSubCat;
import sv.com.guindapp.model.data.Producto;
import sv.com.guindapp.model.interfaces.FragmentFunctions;
import sv.com.guindapp.model.interfaces.IParametro;
import sv.com.guindapp.model.interfaces.Listado;
import sv.com.guindapp.model.interfaces.OnItemClickListener;
import sv.com.guindapp.service.ServicesAPI;
import sv.com.guindapp.ui.adapter.ProdAdicionalesAdapter;
import sv.com.guindapp.ui.adapter.ProdAgregadoAdapter;
import sv.com.guindapp.ui.adapter.ProdOpcionesAdapter;
import sv.com.guindapp.ui.adapter.ProdSubCatAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeleccionProductoFragment extends Fragment implements FragmentFunctions {

    ImageView imgLogo, imgProducto;
    TextView textEmpresa, nombre, precio, descripcion, seleccionUno, seleccionDos, seleccionTres, seleccionCuatro;
    NumberPicker cantidad;
    Button button;
    Producto producto;
    Comercio comercio;
    List<ProdSubCat> prodSubCatList, prodSubCatListSelected = new ArrayList<>();
    List<ProdOpciones> prodOpcionesList, prodOpcionesListSelected = new ArrayList<>();
    List<ProdAdicionales> prodAdicionalesList, prodAdicionalesListSelected = new ArrayList<>();
    List<ProdAgregado> prodAgregadoList, prodAgregadoListSelected = new ArrayList<>();
    RecyclerView prodSubCatRecyclerView, prodOpcionesRecyclerView, prodAdicionalesRecyclerView,
            prodAgregadoRecyclerView;
    ProdSubCatAdapter prodSubCatAdapter;
    ProdOpcionesAdapter prodOpcionesAdapter;
    ProdAdicionalesAdapter prodAdicionalesAdapter;
    ProdAgregadoAdapter prodAgregadoAdapter;
    DecimalFormat formatter = new DecimalFormat("#,##0.00");
    BigDecimal subTotal = new BigDecimal(0.0);
    EditText instrucciones;

    public SeleccionProductoFragment() {
        // Required empty public constructor
    }

    public SeleccionProductoFragment(Comercio comercio, Producto producto) {
        this.comercio = comercio;
        this.producto = producto;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seleccion_producto, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            imgLogo = getActivity().findViewById(R.id.img_logo);
            imgProducto = getActivity().findViewById(R.id.img_producto);
            textEmpresa = getActivity().findViewById(R.id.textEmpresa);
            nombre = getActivity().findViewById(R.id.nombre);
            descripcion = getActivity().findViewById(R.id.descripcion);
            precio = getActivity().findViewById(R.id.precio);
            button = getActivity().findViewById(R.id.ingresar);
            cantidad = getActivity().findViewById(R.id.cantidad);
            instrucciones = getActivity().findViewById(R.id.instrucciones);
            seleccionUno = getActivity().findViewById(R.id.seleccion_uno);
            seleccionDos = getActivity().findViewById(R.id.seleccion_dos);
            seleccionTres = getActivity().findViewById(R.id.seleccion_tres);
            seleccionCuatro = getActivity().findViewById(R.id.seleccion_cuatro);

            seleccionUno.setVisibility(View.GONE);
            seleccionDos.setVisibility(View.GONE);
            seleccionTres.setVisibility(View.GONE);
            seleccionCuatro.setVisibility(View.GONE);

        /*imgLogo.setVisibility(View.GONE);
        textEmpresa.setText(comercio.getNombre());
        textEmpresa.setVisibility(View.VISIBLE);*/

            nombre.setText(producto.getNombre());
            descripcion.setText(producto.getDescripcion());

            precio.setText("$ " + formatter.format(producto.getPrecio().setScale(2, RoundingMode.HALF_UP)));

            Glide.with(getContext()).load(producto.getUrl()).into(imgProducto);

            RxView.clicks(button).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    System.out.println("cantidad: " + cantidad.getValue());
                    BigDecimal cant = new BigDecimal(cantidad.getValue());
                    ((MainActivity) getContext()).agregarProducto(producto, comercio, cant, subTotal,
                            prodSubCatListSelected, prodOpcionesListSelected, prodAgregadoListSelected,
                            prodAdicionalesListSelected, instrucciones.getText().toString());
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
            //button.setText("Agregar 1 a la orden - " + "$ " + formatter.format(producto.getPrecio().setScale(2, RoundingMode.HALF_UP)));

            cantidad.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    actualizarPrecio();
                    //button.setText("Agregar " + newVal + " a la orden - " + "$ " + formatter.format(producto.getPrecio().multiply(new BigDecimal(newVal)).setScale(2, RoundingMode.HALF_UP)));
                    //Log.d("", String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
                }
            });
            prodSubCatRecyclerView = getActivity().findViewById(R.id.prod_sub_cat_recycler_view);
            prodOpcionesRecyclerView = getActivity().findViewById(R.id.prod_opciones_recycler_view);

            prodAdicionalesRecyclerView = getActivity().findViewById(R.id.prod_adicional_recycler_view);
            prodAgregadoRecyclerView = getActivity().findViewById(R.id.prod_agregados_recycler_view);
            obtenerProdSubCat();
            obtenerProdOpciones();
            obtenerProdAdicional();
            obtenerProdAgregado();

            subTotal = producto.getPrecio().multiply(new BigDecimal(cantidad.getValue()))
                    .setScale(2, RoundingMode.HALF_UP);
            button.setText("Agregar 1 a la orden - " + "$ " +
                    formatter.format(subTotal));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public void obtenerProdSubCat() {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<List<ProdSubCat>> call = servicesAPI.prodSubCatByProducto(comercio.getComercioPK().getIdCompania(), comercio.getComercioPK().getIdAfiliado(), comercio.getComercioPK().getId(), producto.getProductoPK().getId());
        call.enqueue(new Callback<List<ProdSubCat>>() {
            @Override
            public void onResponse(Call<List<ProdSubCat>> call, Response<List<ProdSubCat>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    prodSubCatList = response.body();
                    llenarProdSubCat();
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<ProdSubCat>> call, Throwable t) {
//                esta.setText(t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void llenarProdSubCat() {
        prodSubCatAdapter = new ProdSubCatAdapter(prodSubCatList, getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {
                // ((MainActivity) getContext()).seleccionProducto(comercio, (Producto) item);
                actualizarOtros((ProdSubCat) item, 1);
            }
        }, producto);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        prodSubCatRecyclerView.setLayoutManager(mLayoutManager);
        //cardSliderViewPager.setNestedScrollingEnabled(true);

        prodSubCatRecyclerView.setAdapter(prodSubCatAdapter);
        if (!prodSubCatList.isEmpty()) {
            seleccionUno.setVisibility(View.VISIBLE);
            seleccionUno.setText(textSeleccion(1));
        }
    }


    public void actualizarOtros(Listado elemento, Integer list) {
        switch (list) {
            case 1:
                if (producto.getTipoSeleccionSubCat() == 1) {
                    for (Listado p : prodSubCatList) {
                        if (!p.equals(elemento)) {
                            p.getRadioButton().setChecked(false);
                        }
                    }
                } else if (producto.getTipoSeleccionSubCat() == 3) {
                    if (producto.getMaximo() != null &&
                            (prodSubCatListSelected.size() + 1) > producto.getMaximo()) {
                        elemento.getCheckBox().setChecked(false);
                        Toast.makeText(getContext(), "No se pueden agregar más elementos", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case 2:
                if (producto.getTipoSeleccionOpciones() == 1) {
                    for (Listado p : prodOpcionesList) {
                        if (!p.equals(elemento)) {
                            p.getRadioButton().setChecked(false);
                        }
                    }
                } else if (producto.getTipoSeleccionOpciones() == 3) {
                    System.out.println("entro acaaa " + producto.getMaximoOpciones() + " - " + prodOpcionesListSelected.size());
                    if (producto.getMaximoOpciones() != null &&
                            (prodOpcionesListSelected.size() + 1) > producto.getMaximoOpciones()) {
                        elemento.getCheckBox().setChecked(false);
                        Toast.makeText(getContext(), "No se pueden agregar más elementos", Toast.LENGTH_LONG).show();

                    }
                }
                break;
            case 3:
                if (producto.getTipoSeleccionAgregados() == 1) {
                    for (Listado p : prodAgregadoList) {
                        if (!p.equals(elemento)) {
                            p.getRadioButton().setChecked(false);
                        }
                    }
                } else if (producto.getTipoSeleccionAgregados() == 3) {
                    if (producto.getMaximoAgregados() != null &&
                            (prodAgregadoListSelected.size() + 1) > producto.getMaximoAgregados()) {
                        elemento.getCheckBox().setChecked(false);
                        Toast.makeText(getContext(), "No se pueden agregar más elementos", Toast.LENGTH_LONG).show();

                    }
                }
                break;
            case 4:
                if (producto.getTipoSeleccionAdicionales() == 1) {
                    for (Listado p : prodAdicionalesList) {
                        if (!p.equals(elemento)) {
                            p.getRadioButton().setChecked(false);
                        }
                    }
                } else if (producto.getTipoSeleccionAdicionales() == 3) {
                    if (producto.getMaximoAdicionales() != null &&
                            (prodAdicionalesListSelected.size() + 1) > producto.getMaximoAdicionales()) {
                        elemento.getCheckBox().setChecked(false);
                        Toast.makeText(getContext(), "No se pueden agregar más elementos", Toast.LENGTH_LONG).show();

                    }
                }
                break;
        }


        actualizarPrecio();
    }


    public void actualizarPrecio() {
        double totAdd = 0.0;
        prodSubCatListSelected.clear();
        prodOpcionesListSelected.clear();
        prodAdicionalesListSelected.clear();
        prodAgregadoListSelected.clear();
        for (ProdSubCat p : prodSubCatList) {
            if (producto.getTipoSeleccionSubCat() == 1) {
                if (p.getRadioButton() != null && p.getRadioButton().isChecked()) {
                    if (p.getPrecio() != null) {
                        totAdd = totAdd + p.getPrecio().doubleValue();
                    }
                    prodSubCatListSelected.add(p);
                }
            } else {
                if (p.getCheckBox() != null && p.getCheckBox().isChecked()) {
                    if (p.getPrecio() != null) {
                        totAdd = totAdd + p.getPrecio().doubleValue();
                    }
                    prodSubCatListSelected.add(p);
                }
            }

        }
        for (ProdOpciones p : prodOpcionesList) {
            if (producto.getTipoSeleccionOpciones() == 1) {
                if (p.getRadioButton() != null && p.getRadioButton().isChecked()) {
                    if (p.getPrecio() != null) {
                        totAdd = totAdd + p.getPrecio().doubleValue();
                    }
                    prodOpcionesListSelected.add(p);
                }
            } else {
                if (p.getCheckBox() != null && p.getCheckBox().isChecked()) {
                    if (p.getPrecio() != null) {
                        totAdd = totAdd + p.getPrecio().doubleValue();
                    }
                    prodOpcionesListSelected.add(p);
                }
            }
        }
        //System.out.println("Seleccion y tamaño: " + prodOpcionesListSelected.size());


        for (ProdAgregado p : prodAgregadoList) {
            if (producto.getTipoSeleccionAdicionales() == 1) {
                if (p.getRadioButton() != null && p.getRadioButton().isChecked()) {
                    if (p.getPrecio() != null) {
                        totAdd = totAdd + p.getPrecio().doubleValue();
                    }
                    prodAgregadoListSelected.add(p);
                }
            } else {
                if (p.getCheckBox() != null && p.getCheckBox().isChecked()) {
                    if (p.getPrecio() != null) {
                        totAdd = totAdd + p.getPrecio().doubleValue();
                    }
                    prodAgregadoListSelected.add(p);
                }
            }

        }


        for (ProdAdicionales p : prodAdicionalesList) {
            if (producto.getTipoSeleccionAdicionales() == 1) {
                if (p.getRadioButton() != null && p.getRadioButton().isChecked()) {
                    if (p.getPrecio() != null) {
                        totAdd = totAdd + p.getPrecio().doubleValue();
                    }
                    prodAdicionalesListSelected.add(p);
                }
            } else {
                if (p.getCheckBox() != null && p.getCheckBox().isChecked()) {
                    if (p.getPrecio() != null) {
                        totAdd = totAdd + p.getPrecio().doubleValue();
                    }
                    prodAdicionalesListSelected.add(p);
                }
            }

        }
        System.out.println("addd " + totAdd);
        subTotal = (producto.getPrecio().multiply(new
                BigDecimal(cantidad.getValue())).add(new BigDecimal(totAdd * cantidad.getValue())))
                .setScale(2, RoundingMode.HALF_UP);
        button.setText("Agregar " + cantidad.getValue() + " a la orden - " + "$ " +
                formatter.format(subTotal));
    }


    @Override
    public void allowBackPressed() {
        imgLogo.setVisibility(View.VISIBLE);
        textEmpresa.setVisibility(View.GONE);
    }

    public void obtenerProdOpciones() {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<List<ProdOpciones>> call = servicesAPI.prodOpcionesByProducto(comercio.getComercioPK().getIdCompania(), comercio.getComercioPK().getIdAfiliado(), comercio.getComercioPK().getId(), producto.getProductoPK().getId());
        call.enqueue(new Callback<List<ProdOpciones>>() {
            @Override
            public void onResponse(Call<List<ProdOpciones>> call, Response<List<ProdOpciones>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    prodOpcionesList = response.body();
                    llenarProdOpciones();
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<ProdOpciones>> call, Throwable t) {
//                esta.setText(t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void llenarProdOpciones() {
        prodOpcionesAdapter = new ProdOpcionesAdapter(prodOpcionesList, getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {
                // ((MainActivity) getContext()).seleccionProducto(comercio, (Producto) item);
                //actualizarPrecio();
                actualizarOtros((ProdOpciones) item, 2);
            }
        }, producto);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        prodOpcionesRecyclerView.setLayoutManager(mLayoutManager);
        //cardSliderViewPager.setNestedScrollingEnabled(true);

        prodOpcionesRecyclerView.setAdapter(prodOpcionesAdapter);
        if (!prodOpcionesList.isEmpty()) {
            seleccionDos.setVisibility(View.VISIBLE);
            seleccionDos.setText(textSeleccion(2));
        }
    }

    public void obtenerProdAgregado() {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<List<ProdAgregado>> call = servicesAPI.prodAgregadoByProducto(comercio.getComercioPK().getIdCompania(), comercio.getComercioPK().getIdAfiliado(), comercio.getComercioPK().getId(), producto.getProductoPK().getId());
        call.enqueue(new Callback<List<ProdAgregado>>() {
            @Override
            public void onResponse(Call<List<ProdAgregado>> call, Response<List<ProdAgregado>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    prodAgregadoList = response.body();
                    llenarProdAgregado();
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<ProdAgregado>> call, Throwable t) {
//                esta.setText(t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void llenarProdAgregado() {
        prodAgregadoAdapter = new ProdAgregadoAdapter(prodAgregadoList, getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {
                // ((MainActivity) getContext()).seleccionProducto(comercio, (Producto) item);
                //actualizarPrecio();
                actualizarOtros((ProdAgregado) item, 3);
            }
        }, producto);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        prodAgregadoRecyclerView.setLayoutManager(mLayoutManager);
        //cardSliderViewPager.setNestedScrollingEnabled(true);

        prodAgregadoRecyclerView.setAdapter(prodAgregadoAdapter);

        if (!prodAgregadoList.isEmpty()) {
            seleccionTres.setVisibility(View.VISIBLE);
            seleccionTres.setText(textSeleccion(3));
        }
    }

    public void obtenerProdAdicional() {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<List<ProdAdicionales>> call = servicesAPI.prodAdicionalesByProducto(comercio.getComercioPK().getIdCompania(), comercio.getComercioPK().getIdAfiliado(), comercio.getComercioPK().getId(), producto.getProductoPK().getId());
        call.enqueue(new Callback<List<ProdAdicionales>>() {
            @Override
            public void onResponse(Call<List<ProdAdicionales>> call, Response<List<ProdAdicionales>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    prodAdicionalesList = response.body();
                    llenarProdAdicionales();
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<ProdAdicionales>> call, Throwable t) {
//                esta.setText(t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void llenarProdAdicionales() {
        prodAdicionalesAdapter = new ProdAdicionalesAdapter(prodAdicionalesList, getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {
                // ((MainActivity) getContext()).seleccionProducto(comercio, (Producto) item);
                //actualizarPrecio();
                actualizarOtros((ProdAdicionales) item, 4);
            }
        }, producto);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        prodAdicionalesRecyclerView.setLayoutManager(mLayoutManager);
        //cardSliderViewPager.setNestedScrollingEnabled(true);

        prodAdicionalesRecyclerView.setAdapter(prodAdicionalesAdapter);
        if (!prodAdicionalesList.isEmpty()) {
            seleccionCuatro.setVisibility(View.VISIBLE);
            seleccionCuatro.setText(textSeleccion(3));
        }
    }

    public String textSeleccion(Integer list) {
        String seleccione = "Seleccione";
        switch (list) {
            case 1:
                seleccione = decision(producto.getTipoSeleccionSubCat(), producto.getMaximo());
                break;
            case 2:
                seleccione = decision(producto.getTipoSeleccionOpciones(), producto.getMaximoOpciones());
                break;
            case 3://Agre
                seleccione = decision(producto.getTipoSeleccionAgregados(), producto.getMaximoAgregados());
                break;
            case 4://Adi
                seleccione = decision(producto.getTipoSeleccionAdicionales(), producto.getMaximoAdicionales());
                break;
        }
        return seleccione;
    }

    public String decision(Integer tipo, Integer maximo) {
        return tipo == 3 ? "Seleccione (Máximo " + maximo + ")" : "Seleccione";
    }

}
