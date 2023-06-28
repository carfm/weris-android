package sv.com.guindapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.jakewharton.rxbinding.view.RxView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import sv.com.guindapp.model.data.Categoria;
import sv.com.guindapp.model.data.Cliente;
import sv.com.guindapp.model.data.Comercio;
import sv.com.guindapp.model.data.DetOrden;
import sv.com.guindapp.model.data.DireccionCliente;
import sv.com.guindapp.model.data.FormaPago;
import sv.com.guindapp.model.data.FormaPagoPK;
import sv.com.guindapp.model.data.MetodoPago;
import sv.com.guindapp.model.data.Orden;
import sv.com.guindapp.model.data.Pedido;
import sv.com.guindapp.model.data.ProdAdicionales;
import sv.com.guindapp.model.data.ProdAgregado;
import sv.com.guindapp.model.data.ProdOpciones;
import sv.com.guindapp.model.data.ProdSubCat;
import sv.com.guindapp.model.data.Producto;
import sv.com.guindapp.model.interfaces.FragmentFunctions;
import sv.com.guindapp.model.interfaces.IParametro;
import sv.com.guindapp.model.interfaces.OnItemClickListener;
import sv.com.guindapp.api.RetrofitClient;
import sv.com.guindapp.service.ServicesAPI;
import sv.com.guindapp.ui.fragment.CategoriaFragment;
import sv.com.guindapp.ui.fragment.DireccionEnvioFragment;
import sv.com.guindapp.ui.fragment.DireccionesDisponiblesFragment;
import sv.com.guindapp.ui.fragment.DireccionesFragment;
import sv.com.guindapp.ui.fragment.FormasPagoFragment;
import sv.com.guindapp.ui.fragment.GestionDireccionFragment;
import sv.com.guindapp.ui.fragment.MainFragment;
import sv.com.guindapp.ui.fragment.MensajeConfirmaOrdenFragment;
import sv.com.guindapp.ui.fragment.MisOrdenesFragment;
import sv.com.guindapp.ui.fragment.OrdenFragment;
import sv.com.guindapp.ui.fragment.PerfilComercioConsultaFragment;
import sv.com.guindapp.ui.fragment.PerfilComercioFragment;
import sv.com.guindapp.ui.fragment.PerfilFragment;
import sv.com.guindapp.ui.fragment.PerfilTransportistaFragment;
import sv.com.guindapp.ui.fragment.ProcesarOrdenFragment;
import sv.com.guindapp.ui.fragment.ResumenOrdenFragment;
import sv.com.guindapp.ui.fragment.RutaOrdenFragment;
import sv.com.guindapp.ui.fragment.SeleccionProductoFragment;
import sv.com.guindapp.ui.fragment.MetodosPagoFragment;
import sv.com.guindapp.util.Cargador;
import sv.com.guindapp.util.Util;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private LinearLayout compras;
    private LinearLayout menu;
    private LinearLayout perfil;
    private LinearLayout favoritos;
    private LinearLayout descubrir;
    private RelativeLayout atras, contact;
    private ImageView imgCompras;
    private ImageView imgMenu;
    private ImageView imgPerfil;
    private Orden orden;
    public static Cliente cliente;
    private Categoria categoria;
    private RelativeLayout opciones;
    private RelativeLayout barra;
    Context context;

    ImageView imgLogo;
    TextView textEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //FirebaseAuth.getInstance().signOut();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //System.out.println("Usuario Firebase: " + currentUser);
        if (currentUser == null) {
            Util.activity(this, new LoginActivity(), true);
        } else {

            if (savedInstanceState == null) {
                context = this;
                compras = findViewById(R.id.compras);
                menu = findViewById(R.id.home);
                perfil = findViewById(R.id.perfil);
                descubrir = findViewById(R.id.menu);
                favoritos = findViewById(R.id.favoritos);

                imgCompras = findViewById(R.id.img_compras);
                imgMenu = findViewById(R.id.img_menu);
                imgPerfil = findViewById(R.id.img_perfil);
                atras = findViewById(R.id.back);
                contact = findViewById(R.id.contact);
                imgLogo = findViewById(R.id.img_logo);
                textEmpresa = findViewById(R.id.textEmpresa);
                setOpciones(findViewById(R.id.ly_1));
                barra = findViewById(R.id.ly_2);
                /*compras.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        irCompras();
                    }
                });*/

                //System.out.println("token: "+FirebaseAuth.getInstance());
                RxView.clicks(compras).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        regresarMenuBar();
                        irCompras();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

                RxView.clicks(contact).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        irAyuda();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
                RxView.clicks(menu).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        regresarMenuBar();
                        irMenu();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

                RxView.clicks(perfil).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        regresarMenuBar();
                        irPerfil();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

                RxView.clicks(atras).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {

                        regresarMenuBar();
                        onBackPressed();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

                /*Fragment fragment = MainFragment.newInstance(new OnItemClickListener() {
                    @Override
                    public void onItemClick(IParametro item) {
                        irComercio((Comercio) item);
                    }
                });*/
                Bundle b = getIntent().getExtras();
                if (b != null) {
                    Double latitud = b.getDouble("latitud");
                    Double longitud = b.getDouble("longitud");
                    Integer edicion = b.getInt("edicion");
                    if (edicion == 0) {
                        DireccionCliente direccionCliente = new DireccionCliente();
                        direccionCliente.setLongitud(new BigDecimal(longitud));
                        direccionCliente.setLatitud(new BigDecimal(latitud));
                        Fragment fragment = new GestionDireccionFragment(direccionCliente);
                        String nombre = "GestionDireccionFragment";
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.container, fragment, nombre)
                                .commit();
                    } else {
                        Integer idCompania = b.getInt("idCompania");
                        Integer idCliente = b.getInt("idCliente");
                        Integer idDireccionCliente = b.getInt("idDireccionCliente");
                        //Viene para edicion y hay que buscar la direccion cliente
                        obtenerDireccionCliente(idCompania, idCliente, idDireccionCliente, latitud, longitud);
                    }
                } else {
                    Fragment fragment = new CategoriaFragment();
                    Bundle bundle = new Bundle();
                    fragment.setArguments(bundle);
                    //bundle.putString("nombre", nombre);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment, "CategoriaFragment").commit();
                    Cargador.show(this);
                    obtenerCliente();
                }
            }
        }
    }

    public void regresarMenuBar() {
        findViewById(R.id.img_logo).setVisibility(View.VISIBLE);
        TextView tx = findViewById(R.id.textEmpresa);
        tx.setVisibility(View.GONE);
        tx.setText("");
    }


    public void obtenerDireccionCliente(Integer idCompania, Integer idCliente, Integer idDireccionCliente,
                                        Double latitud, Double longitud) {
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Call<DireccionCliente> call = servicesAPI.findDireccionCliente(idCompania, idCliente, idDireccionCliente);
        call.enqueue(new Callback<DireccionCliente>() {
            @Override
            public void onResponse(Call<DireccionCliente> call, Response<DireccionCliente> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    DireccionCliente ofertas = response.body();
                    ofertas.setLatitud(new BigDecimal(latitud));
                    ofertas.setLongitud(new BigDecimal(longitud));
                    Fragment fragment = new GestionDireccionFragment(ofertas);
                    String nombre = "GestionDireccionFragment";
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment, nombre)
                            .commit();
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<DireccionCliente> call, Throwable t) {
//                esta.setText(t.getMessage());
                System.out.println(t.getMessage());
                //Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void obtenerCliente() {
        System.out.println("Entro a obtener cliente");
        if (getCliente() == null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Cliente c = new Cliente();
            c.setNombres(user.getDisplayName());
            c.setCorreo(user.getEmail());
            c.setUidFirebase(user.getUid());
            c.setTelefono(user.getPhoneNumber());
            ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
            Call<Cliente> call = servicesAPI.crearClienteFirebase(c);
            call.enqueue(new Callback<Cliente>() {
                @Override
                public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Respuesta: " + response);
                        //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                        Cliente ofertas = response.body();
                        setCliente(ofertas);
                        System.out.println("Tamaño: " + ofertas);
                    } else {
                        System.out.println("Error: " + response.errorBody());
                    }
                    Cargador.hide();
                }

                @Override
                public void onFailure(Call<Cliente> call, Throwable t) {
//                esta.setText(t.getMessage());
                    System.out.println(t.getMessage());
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    Cargador.hide();
                }
            });
        }
    }

    public void irCompras() {
        /*Glide.with(this).load(R.drawable.compras_amarillo).into(imgCompras);
        Glide.with(this).load(R.drawable.perfil_gris).into(imgPerfil);
        Glide.with(this).load(R.drawable.menu_gris).into(imgMenu);*/
        imgLogo.setVisibility(View.VISIBLE);
        textEmpresa.setVisibility(View.GONE);

        Fragment fragment = new MisOrdenesFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        //bundle.putString("nombre", nombre);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.popBackStack();

        fragmentManager.beginTransaction().replace(R.id.container, fragment, "MisOrdenesFragment")
                .addToBackStack(null)
                .commit();
    }

    public void irPerfil() {
        /*Glide.with(this).load(R.drawable.compras_gris).into(imgCompras);
        Glide.with(this).load(R.drawable.perfil_amarillo).into(imgPerfil);
        Glide.with(this).load(R.drawable.menu_gris).into(imgMenu);*/

        imgLogo.setVisibility(View.VISIBLE);
        textEmpresa.setVisibility(View.GONE);

        Fragment fragment = new PerfilFragment(cliente);
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        //bundle.putString("nombre", nombre);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.popBackStack();

        fragmentManager.beginTransaction().replace(R.id.container, fragment, "PerfilFragment")
                .addToBackStack("CategoriaFragment")

                .commit();
    }

    public void irMenu() {
        /*Glide.with(this).load(R.drawable.compras_gris).into(imgCompras);
        Glide.with(this).load(R.drawable.perfil_gris).into(imgPerfil);
        Glide.with(this).load(R.drawable.menu_amarillo).into(imgMenu);*/
        imgLogo.setVisibility(View.VISIBLE);
        textEmpresa.setVisibility(View.GONE);

        Fragment fragment = new CategoriaFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        //bundle.putString("nombre", nombre);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.popBackStack();

        fragmentManager.beginTransaction().replace(R.id.container, fragment, "MainFragment")
                .addToBackStack(null)
                .commit();
    }

    public void cerrarSesion() {
        System.out.println("Cerrando sesion.........");
        FirebaseAuth.getInstance().signOut();
        Util.activity(this, new LoginActivity(), true);
    }

    public void irComercio(Comercio comercio) {
        System.out.println("Llego hasta aca....");
        Fragment fragment = new PerfilComercioFragment(comercio);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "PerfilComercioFragment")
                .addToBackStack(null)
                .commit();
    }

    public void seleccionProducto(Comercio comercio, Producto producto) {
        System.out.println("Llego a producto");
        Fragment fragment = new SeleccionProductoFragment(comercio, producto);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "SeleccionProductoFragment")
                .addToBackStack("PerfilComercioFragment")
                .commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("SeleccionProductoFragment");
        if (fragment != null) {
            FragmentFunctions seleccionProductoFragment = (FragmentFunctions) fragment;
            seleccionProductoFragment.allowBackPressed();
            System.out.println("Entro en el fragmento");
        }

        fragment = getSupportFragmentManager().findFragmentByTag("ResumenOrdenFragment");
        if (fragment != null) {
            FragmentFunctions seleccionProductoFragment = (FragmentFunctions) fragment;
            seleccionProductoFragment.allowBackPressed();
            System.out.println("Entro en el fragmento");
        }

        fragment = getSupportFragmentManager().findFragmentByTag("ProcesarOrdenFragment");
        if (fragment != null) {
            FragmentFunctions seleccionProductoFragment = (FragmentFunctions) fragment;
            seleccionProductoFragment.allowBackPressed();
            System.out.println("Entro en el fragmento");
        }

        fragment = getSupportFragmentManager().findFragmentByTag("MensajeConfirmaOrdenFragment");
        if (fragment != null) {
            FragmentFunctions seleccionProductoFragment = (FragmentFunctions) fragment;
            seleccionProductoFragment.allowBackPressed();
            System.out.println("Entro en el fragmento");
        }
        //System.out.println("Entro en el back");
        super.onBackPressed();
    }

    public void irBusqueda(Categoria cat, String palabra) {
        categoria = cat;
        Fragment fragment = new MainFragment(new OnItemClickListener() {
            @Override
            public void onItemClick(IParametro item) {
                Comercio c = (Comercio) item;
                if (c.getAbierto()) {
                    orden = null;
                    irComercio(c);
                } else {
                    Toast.makeText(context, "Comercio Cerrado", Toast.LENGTH_LONG).show();
                }

            }
        }, cat, palabra);
        /*textEmpresa.setText(cat.getNombre());
        textEmpresa.setVisibility(View.VISIBLE);
        imgLogo.setVisibility(View.GONE);*/
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "MainFragment")
                .addToBackStack(null)
                .commit();
    }

    public void agregarProducto(Producto producto, Comercio comercio,
                                BigDecimal cantidad, BigDecimal subtotal, List<ProdSubCat> prodSubCatList,
                                List<ProdOpciones> prodOpcionesList,
                                List<ProdAgregado> prodAgregadoList,
                                List<ProdAdicionales> prodAdicionalesList, String instrucciones) {
        onBackPressed();
        getSupportFragmentManager().popBackStack();

        if (getOrden() == null) {
            orden = new Orden();
            orden.setFechaOrden(new Date());
            orden.setAfiliado(comercio.getAfiliado());
            orden.setDetOrdenList(new ArrayList<>());
            orden.setComercio(comercio);
            orden.setCliente(getCliente());
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w("TAG", "getInstanceId failed", task.getException());
                                return;
                            }

                            // Get new Instance ID token
                            String token = task.getResult().getToken();

                            // Log and toast
                            //String msg = getString(R.string.msg_token_fmt, token);
                            Log.d("TAG", "Token: " + token);
                            orden.setToken(token);
                            //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        DetOrden detOrden = new DetOrden();
        detOrden.setCantidad(cantidad);
        detOrden.setDescripcion(producto.getNombre());
        detOrden.setProducto(producto);
        detOrden.setFecha(new Date());
        detOrden.setPrecioU(producto.getPrecio());
        detOrden.setSubtotal(subtotal);
        detOrden.setProdOpcionesList(prodOpcionesList);
        detOrden.setProdSubCatList(prodSubCatList);
        detOrden.setProdAgregadoList(prodAgregadoList);
        detOrden.setProdAdicionalesList(prodAdicionalesList);
        detOrden.setInstrucciones(instrucciones);

        String extras = "";
        DecimalFormat formatter = new DecimalFormat("#,##0.00");

        for (ProdSubCat psc : detOrden.getProdSubCatList()) {
            extras = extras + psc.getNombre() + (psc.getPrecio() != null && psc.getPrecio().doubleValue() != 0 ?
                    "(+$" + formatter.format(psc.getPrecio()) + ")" : "") + ",";
        }

        for (ProdOpciones po : detOrden.getProdOpcionesList()) {
            extras = extras + po.getNombre() + (po.getPrecio() != null && po.getPrecio().doubleValue() != 0 ?
                    "(+$" + formatter.format(po.getPrecio()) + ")" : "") + ",";
        }

        for (ProdAdicionales pa : detOrden.getProdAdicionalesList()) {
            extras = extras + pa.getNombre() + (pa.getPrecio() != null && pa.getPrecio().doubleValue() != 0 ?
                    "(+$" + formatter.format(pa.getPrecio()) + ")" : "") + ",";
        }

        for (ProdAgregado pag : detOrden.getProdAgregadoList()) {
            extras = extras + pag.getNombre() + (pag.getPrecio() != null && pag.getPrecio().doubleValue() != 0 ?
                    "(+$" + formatter.format(pag.getPrecio()) + ")" : "") + ", ";
        }
        if (extras.length() > 1) {
            extras = extras.substring(0, extras.length() - 1);
        }
        detOrden.setObservaciones(extras);

        orden.getDetOrdenList().add(detOrden);
        orden.setSubtotal(obtenerTotal(orden));
        orden.setComision(new BigDecimal(0));
        orden.setTotal(orden.getSubtotal());
        irComercio(comercio);
    }

    public BigDecimal obtenerTotal(Orden orden) {
        double total = 0.0;
        for (DetOrden detOrden : orden.getDetOrdenList()) {
            total = total + detOrden.getSubtotal().doubleValue();
        }
        return new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
    }

    public void verResumenOrden() {
        //getOpciones().setVisibility(View.GONE);
        Fragment fragment = new ResumenOrdenFragment(orden);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "ResumenOrdenFragment")
                .addToBackStack(null)
                .commit();
    }

    public void verProcesarOrden() {
        //getOpciones().setVisibility(View.GONE);
        Fragment fragment = new ProcesarOrdenFragment(orden);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "ProcesarOrdenFragment")
                .addToBackStack(null)
                .commit();
    }

    public void agregarMas(Comercio comercio) {
        System.out.println("Llego hasta aca....");
        Fragment fragment = new PerfilComercioFragment(comercio);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "PerfilComercioFragment")
                .addToBackStack(null)
                .commit();
    }

    public void irFormasPago() {
        System.out.println("Llego hasta aca....");
        Fragment fragment = new FormasPagoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "FormasPagoFragment")
                .addToBackStack(null)
                .commit();
    }

    public void irDireccionesDisponibles() {
        System.out.println("Llego hasta aca....");
        Fragment fragment = new DireccionesDisponiblesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "DireccionesDisponiblesFragment")
                .addToBackStack(null)
                .commit();
    }


    public void procesarOrden(String instrucciones, DireccionCliente direccionCliente, Orden orden) {
        //Enviar orden
        this.orden = orden;
        this.orden.setObservaciones(instrucciones);
        crearOrden(this.orden);
    }

    public void irPerfilComercio(Orden orden) {
        Fragment fragment = new PerfilComercioConsultaFragment(orden);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "PerfilComercioConsultaFragment")
                .addToBackStack(null)
                .commit();
    }

    public void irPerfilTransportista(Orden orden) {
        Fragment fragment = new PerfilTransportistaFragment(orden);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "PerfilTransportistaFragment")
                .addToBackStack(null)
                .commit();
    }

    public void irDireccionEntrega(Orden orden) {
        Fragment fragment = new DireccionEnvioFragment(orden);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "DireccionEnvioFragment")
                .addToBackStack(null)
                .commit();
    }

    public void verOrden(Orden orden) {
        //getOpciones().setVisibility(View.GONE);
        Fragment fragment = new OrdenFragment(orden);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "OrdenFragment")
                .addToBackStack(null)
                .commit();
    }

    public void irRutaOden(Orden orden) {
        this.orden = orden;
        //getOpciones().setVisibility(View.GONE);
        Fragment fragment = new RutaOrdenFragment(orden);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "RutaOrdenFragment")
                .addToBackStack(null)
                .commit();
    }

    public void regresarOrden(Orden orden) {
        System.out.println("Ingreso a regresar orden");
        //getOpciones().setVisibility(View.GONE);
        Fragment fragment = new OrdenFragment(orden);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "OrdenFragment")
                .addToBackStack(null)
                .commit();
    }

    public void cancelarOrden() {

        Util.simpleAlert("Cancelar orden", "¿Esta seguro de cancelar la orden?", new Util.ExecuteParam() {
            @Override
            public void onExecute() {
                //Mensaje de cancelacion
                getBarra().setVisibility(View.VISIBLE);
                getOpciones().setVisibility(View.VISIBLE);
                Fragment fragment = new CategoriaFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.popBackStack();
                fragmentManager.beginTransaction().replace(R.id.container, fragment, "CategoriaFragment")
                        .commit();
                //eliminar orden
                orden = null;
            }
        }, this);

    }

    public void completarOrden() {
        getBarra().setVisibility(View.VISIBLE);
        getOpciones().setVisibility(View.VISIBLE);
        Fragment fragment = new MisOrdenesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "MisOrdenesFragment")
                .commit();
    }

    public String crearOrden(Orden o) {

        if (o.getDireccionCliente() == null) {
            Toast.makeText(context, "Ingrese una dirección de envio", Toast.LENGTH_LONG).show();
            return null;
        }

        if (o.getFormaPago() == null) {
            Toast.makeText(context, "Ingrese una forma de pago", Toast.LENGTH_LONG).show();
            return null;
        }

        if (o.getMetodoPago() == null && o.getFormaPago().getFormaPagoPK().getId() == 2) {
            Toast.makeText(context, "Ingrese un método de pago", Toast.LENGTH_LONG).show();
            return null;
        }

        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        Pedido pedido = new Pedido();

        Cargador.show(this);
        System.out.println("Cliente: " + getCliente());
        o.setCliente(getCliente());
        pedido.setOrden(o);
        pedido.setDetOrdenList(o.getDetOrdenList());
        System.out.println("Det: " + pedido.getDetOrdenList().size());
        Call<Orden> call = servicesAPI.crearOrden(pedido);
        call.enqueue(new Callback<Orden>() {
            @Override
            public void onResponse(Call<Orden> call, Response<Orden> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                    Orden ofertas = response.body();
                    System.out.println("Tamaño: " + ofertas);
                    if (ofertas.getOrdenPK() != null) {
                        //getBarra().setVisibility(View.GONE);
                        //getOpciones().setVisibility(View.GONE);
                        Fragment fragment = new MensajeConfirmaOrdenFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.popBackStack();
                        fragmentManager.beginTransaction().replace(R.id.container, fragment, "MensajeConfirmaOrdenFragment")
                                .commit();
                        orden = null;
                    } else {
                        Toast.makeText(context, "Hubo un problema con tu método de pago", Toast.LENGTH_LONG).show();
                    }
                } else {
                    System.out.println("Error: " + response.errorBody());
                    Toast.makeText(context, "Hubo un error al sincronizar la orden", Toast.LENGTH_LONG).show();
                }
                try {
                    Cargador.hide();
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Orden> call, Throwable t) {
//                esta.setText(t.getMessage());
                System.out.println(t.getMessage());
                //Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(context, "Hubo un error al sincronizar la orden", Toast.LENGTH_LONG).show();
                try {
                    Cargador.hide();
                } catch (Exception e) {

                }
            }
        });
        return null;

    }

    public void irDirecciones() {
        //Util.activity(this, new DireccionesActivity(), false);

        Fragment fragment = new DireccionesFragment(MainActivity.cliente);


        //nombre = "DireccionesFragment";
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "DireccionesFragment")
                .commit();
    }

    public void irGestionDireccion() {
        //direccionCliente
        GestionDireccionFragment fragment = new GestionDireccionFragment();
        //nombre = "GestionDireccionFragment";
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "GestionDireccionFragment")
                .commit();
    }

    public void irFormasPagoTarjeta() {
        Fragment fragment = new MetodosPagoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "TarjetaFragment")
                .addToBackStack(null)
                .commit();
    }

    public void ayuda() {
        irAyuda();
    }

    public void acercaDeWeris() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://weris.app/"));
        startActivity(browserIntent);
    }

    public void nuevaDireccion() {
        Util.activity(this, new MapsActivity(), false);
        finish();
    }

    public void llenarFormaPago(MetodoPago formaPago) {
        //getOpciones().setVisibility(View.GONE);
        orden.setMetodoPago(formaPago.getId());
        orden.setMetodoPagoSeleccionado(formaPago);
        FormaPago f = new FormaPago();
        f.setFormaPagoPK(new FormaPagoPK(2, 1));
        orden.setFormaPago(f);;
        Fragment fragment = new ProcesarOrdenFragment(orden);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "ProcesarOrdenFragment")
                //.addToBackStack(null)
                .commit();
    }

    public void llenarDireccionCliente(DireccionCliente formaPago) {
        //getOpciones().setVisibility(View.GONE);
        orden.setDireccionCliente(formaPago);
        Fragment fragment = new ProcesarOrdenFragment(orden);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "ProcesarOrdenFragment")
                //.addToBackStack(null)
                .commit();
    }

    /*public void actualizarMetodoPago(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which RadioButton was clicked
        TextView txtMetodoPago = findViewById(R.id.txtMetodoPago);
        LinearLayout metodoPagoLy = findViewById(R.id.ly_metodo_pago);
        switch (view.getId()) {
            case R.id.efectivo:
                if (checked) {
                    // Do your coding
                    txtMetodoPago.setVisibility(View.GONE);
                    metodoPagoLy.setVisibility(View.GONE);
                    FormaPago f = new FormaPago();
                    f.setFormaPagoPK(new FormaPagoPK(1, 1));
                    this.orden.setFormaPago(f);
                    this.orden.setMetodoPagoSeleccionado(null);
                    this.orden.setMetodoPago(null);
                    System.out.println("Entro en efectivo select");
                } else {
                    txtMetodoPago.setVisibility(View.VISIBLE);
                    metodoPagoLy.setVisibility(View.VISIBLE);
                    FormaPago f = new FormaPago();
                    f.setFormaPagoPK(new FormaPagoPK(2, 1));
                    this.orden.setFormaPago(f);
                    System.out.println("Entro en efectivo no select");
                }
                break;
            // Perform your logic
            case R.id.tarjeta:
                if (checked) {
                    // Do your coding
                    txtMetodoPago.setVisibility(View.VISIBLE);
                    metodoPagoLy.setVisibility(View.VISIBLE);
                    FormaPago f = new FormaPago();
                    f.setFormaPagoPK(new FormaPagoPK(2, 1));
                    this.orden.setFormaPago(f);
                } else {
                    txtMetodoPago.setVisibility(View.GONE);
                    metodoPagoLy.setVisibility(View.GONE);
                    FormaPago f = new FormaPago();
                    f.setFormaPagoPK(new FormaPagoPK(1, 1));
                    this.orden.setFormaPago(f);
                    this.orden.setMetodoPagoSeleccionado(null);
                    this.orden.setMetodoPago(null);
                }
                break;
        }
    }*/

    public void irAyuda() {
        String url = "https://api.whatsapp.com/send?phone=50378400091";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    public void nuevaDireccion(View view) {
        Util.activity(this, new MapsActivity(), false);
        //finish();
    }


    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public RelativeLayout getOpciones() {
        return opciones;
    }

    public void setOpciones(RelativeLayout opciones) {
        this.opciones = opciones;
    }

    public RelativeLayout getBarra() {
        return barra;
    }

    public void setBarra(RelativeLayout barra) {
        this.barra = barra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
