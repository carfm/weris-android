/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.guindapp.model.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author carlo
 */

public class TrakingOrden implements Serializable {

    private static final long serialVersionUID = 1L;
    protected TrakingOrdenPK trakingOrdenPK;
    private Date fechaHora;
    private String usuario;
    private BigDecimal longitud;
    private BigDecimal latitud;
    private Orden orden;
    private EstadoOrden idEstadoOrden;
    private String token;

    public TrakingOrden() {
    }

    public TrakingOrden(TrakingOrdenPK trakingOrdenPK) {
        this.trakingOrdenPK = trakingOrdenPK;
    }

    public TrakingOrden(int id, int idOrden, int idCompania, int idAfiliado) {
        this.trakingOrdenPK = new TrakingOrdenPK(id, idOrden, idCompania, idAfiliado);
    }

    public TrakingOrdenPK getTrakingOrdenPK() {
        return trakingOrdenPK;
    }

    public void setTrakingOrdenPK(TrakingOrdenPK trakingOrdenPK) {
        this.trakingOrdenPK = trakingOrdenPK;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public EstadoOrden getIdEstadoOrden() {
        return idEstadoOrden;
    }

    public void setIdEstadoOrden(EstadoOrden idEstadoOrden) {
        this.idEstadoOrden = idEstadoOrden;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trakingOrdenPK != null ? trakingOrdenPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrakingOrden)) {
            return false;
        }
        TrakingOrden other = (TrakingOrden) object;
        if ((this.trakingOrdenPK == null && other.trakingOrdenPK != null) || (this.trakingOrdenPK != null && !this.trakingOrdenPK.equals(other.trakingOrdenPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.TrakingOrden[ trakingOrdenPK=" + trakingOrdenPK + " ]";
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
