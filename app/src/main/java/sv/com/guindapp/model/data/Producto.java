/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.guindapp.model.data;

import java.io.Serializable;
import java.math.BigDecimal;

import sv.com.guindapp.model.interfaces.IParametro;


/**
 *
 * @author carlo
 */

public class Producto  implements Serializable, IParametro {

    private static final long serialVersionUID = 1L;
    protected ProductoPK productoPK;
    private String nombre;
    private String url;
    private String descripcion;
    private BigDecimal precio;
    private String codigoInterno;
    private String referencia;
    private Integer estado;
    private Afiliado afiliado;
    private TipoProducto tipoProducto;
    private UnidadMedida idUnidad;

    private Integer tipoSeleccionSubCat;
    private Integer tipoSeleccionOpciones;
    private Integer tipoSeleccionAgregados;
    private Integer tipoSeleccionAdicionales;

    private Integer maximo;
    private Integer maximoOpciones;
    private Integer maximoAgregados;
    private Integer maximoAdicionales;

    public Producto() {
    }

    public Producto(ProductoPK productoPK) {
        this.productoPK = productoPK;
    }

    public Producto(int id, int idCompania, int idAfiliado) {
        this.productoPK = new ProductoPK(id, idCompania, idAfiliado);
    }

    public ProductoPK getProductoPK() {
        return productoPK;
    }

    public void setProductoPK(ProductoPK productoPK) {
        this.productoPK = productoPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Afiliado getAfiliado() {
        return afiliado;
    }

    public void setAfiliado(Afiliado afiliado) {
        this.afiliado = afiliado;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public UnidadMedida getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(UnidadMedida idUnidad) {
        this.idUnidad = idUnidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoPK != null ? productoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.productoPK == null && other.productoPK != null) || (this.productoPK != null && !this.productoPK.equals(other.productoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.Producto[ productoPK=" + productoPK + " ]";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTipoSeleccionSubCat() {
        return tipoSeleccionSubCat;
    }

    public void setTipoSeleccionSubCat(Integer tipoSeleccionSubCat) {
        this.tipoSeleccionSubCat = tipoSeleccionSubCat;
    }

    public Integer getTipoSeleccionOpciones() {
        return tipoSeleccionOpciones;
    }

    public void setTipoSeleccionOpciones(Integer tipoSeleccionOpciones) {
        this.tipoSeleccionOpciones = tipoSeleccionOpciones;
    }

    public Integer getTipoSeleccionAgregados() {
        return tipoSeleccionAgregados;
    }

    public void setTipoSeleccionAgregados(Integer tipoSeleccionAgregados) {
        this.tipoSeleccionAgregados = tipoSeleccionAgregados;
    }

    public Integer getTipoSeleccionAdicionales() {
        return tipoSeleccionAdicionales;
    }

    public void setTipoSeleccionAdicionales(Integer tipoSeleccionAdicionales) {
        this.tipoSeleccionAdicionales = tipoSeleccionAdicionales;
    }

    public Integer getMaximo() {
        return maximo;
    }

    public void setMaximo(Integer maximo) {
        this.maximo = maximo;
    }

    public Integer getMaximoOpciones() {
        return maximoOpciones;
    }

    public void setMaximoOpciones(Integer maximoOpciones) {
        this.maximoOpciones = maximoOpciones;
    }

    public Integer getMaximoAgregados() {
        return maximoAgregados;
    }

    public void setMaximoAgregados(Integer maximoAgregados) {
        this.maximoAgregados = maximoAgregados;
    }

    public Integer getMaximoAdicionales() {
        return maximoAdicionales;
    }

    public void setMaximoAdicionales(Integer maximoAdicionales) {
        this.maximoAdicionales = maximoAdicionales;
    }
}
