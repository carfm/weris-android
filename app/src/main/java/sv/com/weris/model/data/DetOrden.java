/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.weris.model.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import sv.com.weris.model.interfaces.IParametro;


/**
 *
 * @author carlo
 */

public class DetOrden implements Serializable, IParametro {

    private static final long serialVersionUID = 1L;
    protected DetOrdenPK detOrdenPK;
    private String descripcion;
    private String observaciones;
    private Date fecha;
    private BigDecimal precioU;
    private BigDecimal cantidad;
    private BigDecimal descuento;
    private BigDecimal subtotal;
    private BigDecimal comision;
    private Orden orden;
    private Producto producto;
    private List<ProdSubCat> prodSubCatList;
    private List<ProdOpciones> prodOpcionesList;

    public DetOrden() {
    }

    public DetOrden(DetOrdenPK detOrdenPK) {
        this.detOrdenPK = detOrdenPK;
    }

    public DetOrden(int id, int idOrden, int idCompania, int idAfiliado, int idCaja) {
        this.detOrdenPK = new DetOrdenPK(id, idOrden, idCompania, idAfiliado, idCaja);
    }

    public DetOrdenPK getDetOrdenPK() {
        return detOrdenPK;
    }

    public void setDetOrdenPK(DetOrdenPK detOrdenPK) {
        this.detOrdenPK = detOrdenPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPrecioU() {
        return precioU;
    }

    public void setPrecioU(BigDecimal precioU) {
        this.precioU = precioU;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detOrdenPK != null ? detOrdenPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetOrden)) {
            return false;
        }
        DetOrden other = (DetOrden) object;
        if ((this.detOrdenPK == null && other.detOrdenPK != null) || (this.detOrdenPK != null && !this.detOrdenPK.equals(other.detOrdenPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.DetOrden[ detOrdenPK=" + detOrdenPK + " ]";
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public List<ProdSubCat> getProdSubCatList() {
        return prodSubCatList;
    }

    public void setProdSubCatList(List<ProdSubCat> prodSubCatList) {
        this.prodSubCatList = prodSubCatList;
    }

    public List<ProdOpciones> getProdOpcionesList() {
        return prodOpcionesList;
    }

    public void setProdOpcionesList(List<ProdOpciones> prodOpcionesList) {
        this.prodOpcionesList = prodOpcionesList;
    }
}
