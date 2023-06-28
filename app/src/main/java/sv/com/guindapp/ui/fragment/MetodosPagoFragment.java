package sv.com.guindapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.guindapp.MainActivity;
import sv.com.guindapp.R;
import sv.com.guindapp.api.RetrofitClient;
import sv.com.guindapp.model.data.MetodoPago;
import sv.com.guindapp.model.entity.CardClient;
import sv.com.guindapp.model.interfaces.IParametro;
import sv.com.guindapp.model.interfaces.OnItemClickListener;
import sv.com.guindapp.service.ServicesAPI;
import sv.com.guindapp.ui.adapter.MetodoPagoAdapter;
import sv.com.guindapp.ui.adapter.TarjetaAdapter;


public class MetodosPagoFragment extends Fragment {

    private RecyclerView rvResultados;
    private TarjetaAdapter adapter;
    private List<CardClient> formaPagoList;
    FloatingActionButton fab;
    LinearLayout sin_metodos;
    Button agregar_metodo, fijar;
    private MetodoPagoAdapter metodoPagoAdapter;
    private List<MetodoPago> metodoPagoClienteList;

    public MetodosPagoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tarjeta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvResultados = view.findViewById(R.id.rv_formas_pago);
        fab = view.findViewById(R.id.fab);
        sin_metodos = view.findViewById(R.id.sin_metodos);
        agregar_metodo = view.findViewById(R.id.agregar_metodo);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaTarjeta();
            }
        });*/
        fijar = view.findViewById(R.id.fijar);
        fijar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoMetodo();
            }
        });

        agregar_metodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoMetodo();
            }
        });

        getActivity().findViewById(R.id.img_logo).setVisibility(View.GONE);
        TextView tx = getActivity().findViewById(R.id.textEmpresa);
        tx.setVisibility(View.VISIBLE);
        tx.setText("Métodos de pago");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        obtenerMetodosPagoCliente();
    }

    public void nuevoMetodo() {
        GestionMetodoPagoFragment fragment = new GestionMetodoPagoFragment(new MetodoPago());
        //nombre = "GestionDireccionFragment";
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "GestionMetodoPagoFragment")
                .commit();
    }

    /* public void obtenerMetodosPagoCliente() {
         FirebaseAuth mAuth;
         mAuth = FirebaseAuth.getInstance();
         formaPagoList = ObjectBox.get()
                 .boxFor(CardClient.class)
                 .query()
                 .equal(CardClient_.client, mAuth.getUid())
                 .build()
                 .find();
         llenarLista();
     }

     public void llenarLista() {
         if (formaPagoList.size() > 0) {
             sin_metodos.setVisibility(View.GONE);
             rvResultados.setVisibility(View.VISIBLE);
             agregar_metodo.setVisibility(View.VISIBLE);
         } else {
             sin_metodos.setVisibility(View.VISIBLE);
             rvResultados.setVisibility(View.GONE);
             agregar_metodo.setVisibility(View.GONE);
         }

         adapter = new TarjetaAdapter(formaPagoList, getContext(), new OnItemClickListener() {
             @Override
             public void onItemClick(IParametro item) {
                 //((MainActivity) getContext()).llenarFormaPago((FormaPago) item);
             }
         }, new TarjetaAdapter.OnPaymentCardEventListener() {
             @Override
             public void onCardDetailsSubmit(String month, String year, String cardNumber, String cvv, CardClient item) {
                 guardarTarjeta(month, year, cardNumber, cvv, item);
             }

             @Override
             public void onError(String error) {
                 Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
             }

             @Override
             public void onCancelClick(CardClient item, Integer posicion) {
                 eliminarTarjeta(item, posicion);

             }
         });
         RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
         rvResultados.setLayoutManager(mLayoutManager);
         rvResultados.setAdapter(adapter);
     }

     public void nuevaTarjeta() {
         CardClient cardClient = new CardClient();
         if (formaPagoList == null || formaPagoList.isEmpty()) {
             formaPagoList = new ArrayList<>();
             formaPagoList.add(cardClient);
             llenarLista();
         } else {
             Boolean actual = false;
             for (CardClient card : formaPagoList) {
                 if (card.getId() == 0L) {
                     actual = true;
                     break;
                 }
             }
             if (actual) {
                 Toast.makeText(getContext(), "Ya hay una tarjeta en proceso de ingreso", Toast.LENGTH_LONG).show();
             } else {
                 formaPagoList.add(cardClient);
                 llenarLista();
             }
         }
     }

     public void eliminarTarjeta(CardClient cardClient, Integer posicion) {
         formaPagoList.remove(cardClient);
         if (cardClient.getId() != 0L) {
             ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
             Call<CardClient> call = servicesAPI.dtc(cardClient);
             call.enqueue(new Callback<CardClient>() {
                 @Override
                 public void onResponse(Call<CardClient> call, Response<CardClient> response) {
                     if (response.isSuccessful()) {
                         System.out.println("Respuesta: " + response);
                         //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                         CardClient ofertas = response.body();
                         if (ofertas.getAutorizado() == 1) {
                             Box<CardClient> box = ObjectBox.get().boxFor(CardClient.class);
                             box.remove(ofertas);
                             System.out.println("Se elimino el objeto");
                             llenarLista();
                             Toast.makeText(getContext(), "Tarjeta Eliminada", Toast.LENGTH_LONG).show();
                         } else {
                             Toast.makeText(getContext(), "Tarjeta no autorizada/no se pudo eliminar", Toast.LENGTH_LONG).show();
                         }
                     } else {
                         System.out.println("Error: " + response.errorBody());
                     }
                 }

                 @Override
                 public void onFailure(Call<CardClient> call, Throwable t) {
 //                esta.setText(t.getMessage());
                     System.out.println(t.getMessage());
                     //Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
                     Toast.makeText(getContext(), "Hubo un error al almacenar y aprobar la tarjeta", Toast.LENGTH_LONG).show();
                 }
             });

         } else {
             llenarLista();
             Toast.makeText(getContext(), "Tarjeta Eliminada", Toast.LENGTH_LONG).show();
         }

     }

     public void guardarTarjeta(String month, String year, String cardNumber, String cvv, CardClient item) {
         System.out.println("Creando tarjeta en ObjectBox");
         FirebaseAuth mAuth;
         mAuth = FirebaseAuth.getInstance();
         item.setNombre("Carlos Fuentes");
         item.setRx(cardNumber.replace(" ", ""));//agregar solo los 4 ultimos digitos y demas en astericos despues de confirmada
         item.setRy(month);
         item.setRy1(year);
         item.setRz(cvv);
         if (item.getId() == 0L) {
             item.setTkn("pendiente");
             item.setAth("pendiente");
         }
         item.setAutorizado(0);
         item.setClient(mAuth.getUid());

         ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
         Call<CardClient> call = servicesAPI.ctc(item);
         System.out.println("Request: " + item.getRx() + " " + item.getRy() + " " + item.getRy1() + " " + item.getRz());
         call.enqueue(new Callback<CardClient>() {
             @Override
             public void onResponse(Call<CardClient> call, Response<CardClient> response) {
                 if (response.isSuccessful()) {
                     System.out.println("Respuesta: " + response);
                     //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                     CardClient ofertas = response.body();
                     if (ofertas.getAutorizado() == 1) {
                         Box<CardClient> box = ObjectBox.get().boxFor(CardClient.class);
                         box.put(ofertas);
                         System.out.println("Se creo el objeto");
                         obtenerDireccionesCliente();
                         Toast.makeText(getContext(), "Tarjeta Creada", Toast.LENGTH_LONG).show();
                     } else {
                         Toast.makeText(getContext(), "Tarjeta no autorizada", Toast.LENGTH_LONG).show();
                     }
                 } else {
                     System.out.println("Error: " + response.errorBody());
                 }
             }

             @Override
             public void onFailure(Call<CardClient> call, Throwable t) {
 //                esta.setText(t.getMessage());
                 System.out.println(t.getMessage());
                 //Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
                 Toast.makeText(getContext(), "Hubo un error al almacenar y aprobar la tarjeta", Toast.LENGTH_LONG).show();
             }
         });
     }
 */
    public void obtenerMetodosPagoCliente() {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<List<MetodoPago>> call = servicesAPI.metodosPagoPorCliente(MainActivity.cliente.getClientePK().getId(), MainActivity.cliente.getClientePK().getIdCompania());
        call.enqueue(new Callback<List<MetodoPago>>() {
            @Override
            public void onResponse(Call<List<MetodoPago>> call, Response<List<MetodoPago>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    List<MetodoPago> ofertas = response.body();
                    metodoPagoClienteList = ofertas;
                    System.out.println("Tamaño: " + ofertas.size());
                    llenarLista();

                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<MetodoPago>> call, Throwable t) {
//                esta.setText(t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void llenarLista() {
        if (metodoPagoClienteList.size() > 0) {
            sin_metodos.setVisibility(View.GONE);
            rvResultados.setVisibility(View.VISIBLE);
            agregar_metodo.setVisibility(View.VISIBLE);
        } else {
            sin_metodos.setVisibility(View.VISIBLE);
            rvResultados.setVisibility(View.GONE);
            agregar_metodo.setVisibility(View.GONE);
        }

        metodoPagoAdapter = new MetodoPagoAdapter(metodoPagoClienteList, getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {

                eliminarTarjeta((MetodoPago) item);
            }
        },true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        rvResultados.setLayoutManager(mLayoutManager);
        rvResultados.setAdapter(metodoPagoAdapter);
    }

    public void eliminarTarjeta(MetodoPago metodoPago) {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<MetodoPago> call = servicesAPI.borrarMetodoPagoCliente(metodoPago.getId());
        call.enqueue(new Callback<MetodoPago>() {
            @Override
            public void onResponse(Call<MetodoPago> call, Response<MetodoPago> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    MetodoPago ofertas = response.body();
                    ((MainActivity) getContext()).irFormasPagoTarjeta();
                    Toast.makeText(getContext(), "Tarjeta Eliminada", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<MetodoPago> call, Throwable t) {
                //                esta.setText(t.getMessage());
                System.out.println(t.getMessage());
                //Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Hubo un error al eliminar la tarjeta", Toast.LENGTH_LONG).show();
            }
        });

    }

}