/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.guindapp.model.data;

import java.io.Serializable;


/**
 *
 * @author carlo
 */

public class EntregaAsignacionPK implements Serializable {

    private int id;
    private int idCompania;
    private int idAfiliado;
    private int idCaja;
    private int idTransportista;
    private int idOrden;

    public EntregaAsignacionPK() {
    }

    public EntregaAsignacionPK(int id, int idCompania, int idAfiliado, int idCaja, int idTransportista, int idOrden) {
        this.id = id;
        this.idCompania = idCompania;
        this.idAfiliado = idAfiliado;
        this.idCaja = idCaja;
        this.idTransportista = idTransportista;
        this.idOrden = idOrden;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCompania() {
        return idCompania;
    }

    public void setIdCompania(int idCompania) {
        this.idCompania = idCompania;
    }

    public int getIdAfiliado() {
        return idAfiliado;
    }

    public void setIdAfiliado(int idAfiliado) {
        this.idAfiliado = idAfiliado;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public int getIdTransportista() {
        return idTransportista;
    }

    public void setIdTransportista(int idTransportista) {
        this.idTransportista = idTransportista;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) idCompania;
        hash += (int) idAfiliado;
        hash += (int) idCaja;
        hash += (int) idTransportista;
        hash += (int) idOrden;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntregaAsignacionPK)) {
            return false;
        }
        EntregaAsignacionPK other = (EntregaAsignacionPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.idCompania != other.idCompania) {
            return false;
        }
        if (this.idAfiliado != other.idAfiliado) {
            return false;
        }
        if (this.idCaja != other.idCaja) {
            return false;
        }
        if (this.idTransportista != other.idTransportista) {
            return false;
        }
        if (this.idOrden != other.idOrden) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.EntregaAsignacionPK[ id=" + id + ", idCompania=" + idCompania + ", idAfiliado=" + idAfiliado + ", idCaja=" + idCaja + ", idTransportista=" + idTransportista + ", idOrden=" + idOrden + " ]";
    }
    
}
