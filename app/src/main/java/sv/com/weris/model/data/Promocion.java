/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.weris.model.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import sv.com.weris.model.interfaces.IParametro;

/**
 *
 * @author carlo
 */

public class Promocion implements Serializable, IParametro {

    private static final long serialVersionUID = 1L;
    protected PromocionPK promocionPK;
    private Integer idSucursal;
    private String nombre;
    private Date desde;
    private Date hasta;
    private BigDecimal precio;
    private BigDecimal porcentajeDescuento;
    private BigDecimal valorDescuento;
    private BigDecimal cantidadRebaja;
    private Integer estado;
    private String tipoPromocion;
    private Afiliado afiliado;
    private Comercio comercio;

    public Promocion() {
    }

    public Promocion(PromocionPK promocionPK) {
        this.promocionPK = promocionPK;
    }

    public Promocion(int id, int idCompania, int idAfiliado) {
        this.promocionPK = new PromocionPK(id, idCompania, idAfiliado);
    }

    public PromocionPK getPromocionPK() {
        return promocionPK;
    }

    public void setPromocionPK(PromocionPK promocionPK) {
        this.promocionPK = promocionPK;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public BigDecimal getValorDescuento() {
        return valorDescuento;
    }

    public void setValorDescuento(BigDecimal valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    public BigDecimal getCantidadRebaja() {
        return cantidadRebaja;
    }

    public void setCantidadRebaja(BigDecimal cantidadRebaja) {
        this.cantidadRebaja = cantidadRebaja;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getTipoPromocion() {
        return tipoPromocion;
    }

    public void setTipoPromocion(String tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
    }

    public Afiliado getAfiliado() {
        return afiliado;
    }

    public void setAfiliado(Afiliado afiliado) {
        this.afiliado = afiliado;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promocionPK != null ? promocionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promocion)) {
            return false;
        }
        Promocion other = (Promocion) object;
        if ((this.promocionPK == null && other.promocionPK != null) || (this.promocionPK != null && !this.promocionPK.equals(other.promocionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.Promocion[ promocionPK=" + promocionPK + " ]";
    }
    
}
