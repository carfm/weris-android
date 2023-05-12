/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.weris.model.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author carlo
 */

public class EntregaAsignacion implements Serializable {

    private static final long serialVersionUID = 1L;
    protected EntregaAsignacionPK entregaAsignacionPK;
    private Integer estado;
    private Date fechaHoraAsignacion;
    private Date fechaHoraEntrega;
    private Date tiempoRecorrido;
    private BigInteger distanciaRecorrida;
    private BigDecimal latitudOrigen;
    private BigDecimal longitudOrigen;
    private BigDecimal latitudDestino;
    private BigDecimal longitudDestino;
    private BigDecimal comision;
    private BigDecimal valor;
    private Orden orden;
    private Trasportista trasportista;

    public EntregaAsignacion() {
    }

    public EntregaAsignacion(EntregaAsignacionPK entregaAsignacionPK) {
        this.entregaAsignacionPK = entregaAsignacionPK;
    }

    public EntregaAsignacion(int id, int idCompania, int idAfiliado, int idCaja, int idTransportista, int idOrden) {
        this.entregaAsignacionPK = new EntregaAsignacionPK(id, idCompania, idAfiliado, idCaja, idTransportista, idOrden);
    }

    public EntregaAsignacionPK getEntregaAsignacionPK() {
        return entregaAsignacionPK;
    }

    public void setEntregaAsignacionPK(EntregaAsignacionPK entregaAsignacionPK) {
        this.entregaAsignacionPK = entregaAsignacionPK;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFechaHoraAsignacion() {
        return fechaHoraAsignacion;
    }

    public void setFechaHoraAsignacion(Date fechaHoraAsignacion) {
        this.fechaHoraAsignacion = fechaHoraAsignacion;
    }

    public Date getFechaHoraEntrega() {
        return fechaHoraEntrega;
    }

    public void setFechaHoraEntrega(Date fechaHoraEntrega) {
        this.fechaHoraEntrega = fechaHoraEntrega;
    }

    public Date getTiempoRecorrido() {
        return tiempoRecorrido;
    }

    public void setTiempoRecorrido(Date tiempoRecorrido) {
        this.tiempoRecorrido = tiempoRecorrido;
    }

    public BigInteger getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    public void setDistanciaRecorrida(BigInteger distanciaRecorrida) {
        this.distanciaRecorrida = distanciaRecorrida;
    }

    public BigDecimal getLatitudOrigen() {
        return latitudOrigen;
    }

    public void setLatitudOrigen(BigDecimal latitudOrigen) {
        this.latitudOrigen = latitudOrigen;
    }

    public BigDecimal getLongitudOrigen() {
        return longitudOrigen;
    }

    public void setLongitudOrigen(BigDecimal longitudOrigen) {
        this.longitudOrigen = longitudOrigen;
    }

    public BigDecimal getLatitudDestino() {
        return latitudDestino;
    }

    public void setLatitudDestino(BigDecimal latitudDestino) {
        this.latitudDestino = latitudDestino;
    }

    public BigDecimal getLongitudDestino() {
        return longitudDestino;
    }

    public void setLongitudDestino(BigDecimal longitudDestino) {
        this.longitudDestino = longitudDestino;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public Trasportista getTrasportista() {
        return trasportista;
    }

    public void setTrasportista(Trasportista trasportista) {
        this.trasportista = trasportista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entregaAsignacionPK != null ? entregaAsignacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntregaAsignacion)) {
            return false;
        }
        EntregaAsignacion other = (EntregaAsignacion) object;
        if ((this.entregaAsignacionPK == null && other.entregaAsignacionPK != null) || (this.entregaAsignacionPK != null && !this.entregaAsignacionPK.equals(other.entregaAsignacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.EntregaAsignacion[ entregaAsignacionPK=" + entregaAsignacionPK + " ]";
    }
    
}
