package sv.com.weris.service;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sv.com.weris.model.data.Categoria;
import sv.com.weris.model.data.Cliente;
import sv.com.weris.model.data.Comercio;
import sv.com.weris.model.data.DireccionCliente;
import sv.com.weris.model.data.FormaPago;
import sv.com.weris.model.data.MetodoPago;
import sv.com.weris.model.data.Orden;
import sv.com.weris.model.data.Pedido;
import sv.com.weris.model.data.ProdAdicionales;
import sv.com.weris.model.data.ProdAgregado;
import sv.com.weris.model.data.ProdOpciones;
import sv.com.weris.model.data.ProdSubCat;
import sv.com.weris.model.data.Producto;
import sv.com.weris.model.data.TipoProdComercio;
import sv.com.weris.model.data.TokenUsuario;
import sv.com.weris.model.data.TrakingOrden;
import sv.com.weris.model.entity.CardClient;

public interface ServicesAPI {

    @GET("com.guindapp.entidades.categoria")
    Call<List<Categoria>> obtenerCategorias();

    @GET("com.guindapp.entidades.comercio")
    Call<List<Comercio>> obtenerComercios();

    @GET("com.guindapp.entidades.comercio")
    Call<List<Comercio>> obtenerComercios(@Path("to") Integer from, @Path("from") Integer to);

    @GET("com.guindapp.entidades.producto")
    Call<List<Producto>> obtenerProductos();

    @GET("com.guindapp.entidades.formapago")
    Call<List<FormaPago>> obtenerFormasPago();

    @GET("com.guindapp.entidades.producto/productosPorComercio/{compania}/{idAfiliado}/{idComercio}")
    Call<List<Producto>> productosPorComercio(@Path("compania") Integer compania, @Path("idAfiliado") Integer idAfiliado, @Path("idComercio") Integer idComercio);

    @GET("com.guindapp.entidades.producto/tipo-prod-by-comercio/{compania}/{idAfiliado}/{idComercio}")
    Call<List<TipoProdComercio>> tipoProdByComercio(@Path("compania") Integer compania, @Path("idAfiliado") Integer idAfiliado, @Path("idComercio") Integer idComercio);

    @POST("com.guindapp.entidades.cliente/crearClienteFirebase")
    Call<Cliente> crearClienteFirebase(@Body Cliente cliente);

    @POST("com.guindapp.entidades.tokenusuario")
    Call <TokenUsuario>crearToken(@Body TokenUsuario tokenUsuario);

    @POST("com.guindapp.entidades.orden/crearOrden")
    Call<Orden> crearOrden(@Body Pedido pedido);

    @POST("com.guindapp.entidades.orden/cpt")
    Call<Orden> calculoPrecioTransporte(@Body Pedido pedido);

    @GET("com.guindapp.entidades.direccioncliente/direccionesPorCliente/{compania}/{idCliente}")
    Call<List<DireccionCliente>> direccionesPorCliente(@Path("compania") Integer compania, @Path("idCliente") Integer idCliente);

    @GET("com.guindapp.entidades.direccioncliente/findDireccionCliente/{compania}/{idCliente}/{id}")
    Call<DireccionCliente> findDireccionCliente(@Path("compania") Integer compania, @Path("idCliente") Integer idCliente, @Path("id") Integer id);

    @GET("com.guindapp.entidades.orden/ordenesPorCliente/{compania}/{idCliente}/{activa}")
    Call<List<Pedido>> ordenesPorCliente(@Path("compania") Integer compania, @Path("idCliente") Integer idCliente, @Path("activa") Integer activa);

    @POST("com.guindapp.entidades.direccioncliente")
    Call <DireccionCliente>crearDireccionCliente(@Body DireccionCliente direccionCliente);

    @POST("com.guindapp.entidades.orden/dtc")
    Call <CardClient>dtc(@Body CardClient cardClient);

    @POST("com.guindapp.entidades.orden/ctc")
    Call <CardClient>ctc(@Body CardClient cardClient);

    @PUT("com.guindapp.entidades.direccioncliente/{id}")
    Call<DireccionCliente> editarDireccionCliente(@Path("id") String id, @Body DireccionCliente direccionCliente);

    @GET("com.guindapp.entidades.trakingorden/buscarPorOrden/{compania}/{idAfiliado}/{idComercio}/{idOrden}")
    Call<List<TrakingOrden>> buscarPorOrden(@Path("compania") Integer compania, @Path("idAfiliado") Integer idAfiliado, @Path("idComercio") Integer idComercio, @Path("idOrden") Integer idOrden);

    @GET("com.guindapp.entidades.producto/prod-sub-cat/{compania}/{idAfiliado}/{idComercio}/{idProducto}")
    Call<List<ProdSubCat>> prodSubCatByProducto(@Path("compania") Integer compania, @Path("idAfiliado") Integer idAfiliado, @Path("idComercio") Integer idComercio, @Path("idProducto") Integer idProducto);


    @GET("com.guindapp.entidades.producto/prod-opciones/{compania}/{idAfiliado}/{idComercio}/{idProducto}")
    Call<List<ProdOpciones>> prodOpcionesByProducto(@Path("compania") Integer compania, @Path("idAfiliado") Integer idAfiliado, @Path("idComercio") Integer idComercio, @Path("idProducto") Integer idProducto);

    @GET("com.guindapp.entidades.producto/prod-adicionales/{compania}/{idAfiliado}/{idComercio}/{idProducto}")
    Call<List<ProdAdicionales>> prodAdicionalesByProducto(@Path("compania") Integer compania, @Path("idAfiliado") Integer idAfiliado, @Path("idComercio") Integer idComercio, @Path("idProducto") Integer idProducto);

    @GET("com.guindapp.entidades.producto/prod-agregado/{compania}/{idAfiliado}/{idComercio}/{idProducto}")
    Call<List<ProdAgregado>> prodAgregadoByProducto(@Path("compania") Integer compania, @Path("idAfiliado") Integer idAfiliado, @Path("idComercio") Integer idComercio, @Path("idProducto") Integer idProducto);

    @GET("com.guindapp.entidades.comercio/comercios-por-cat/{idCategoria}")
    Call<List<Comercio>> comerciosPorCategoria(@Path("idCategoria") Integer idCategoria);

    @GET("com.guindapp.entidades.cliente/listar-metodo-pago-cliente/{idCliente}/{idCompania}")
    Call<List<MetodoPago>> metodosPagoPorCliente(@Path("idCliente") Integer idCliente,@Path("idCompania") Integer idCompania);

    @POST("com.guindapp.entidades.cliente/crear-metodo-pago-cliente")
    Call <MetodoPago>crearMetodoPagoCliente(@Body MetodoPago metodoPago);

    @DELETE("com.guindapp.entidades.cliente/borrar-metodo-pago-cliente/{id}")
    Call <MetodoPago>borrarMetodoPagoCliente(@Path("id") Integer id);

    @GET("com.guindapp.entidades.comercio/comercios-por-busqueda")
    Call<List<Comercio>> comerciosSearch(@Query("id-categoria") Integer idCategoria,@Query("palabra") String palabra);
}
