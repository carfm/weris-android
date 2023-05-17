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

public class TipoProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    protected TipoProductoPK tipoProductoPK;
    private String descripcion;

    public TipoProducto() {
    }

    public TipoProducto(TipoProductoPK tipoProductoPK) {
        this.tipoProductoPK = tipoProductoPK;
    }

    public TipoProducto(int id, int idCompania) {
        this.tipoProductoPK = new TipoProductoPK(id, idCompania);
    }

    public TipoProductoPK getTipoProductoPK() {
        return tipoProductoPK;
    }

    public void setTipoProductoPK(TipoProductoPK tipoProductoPK) {
        this.tipoProductoPK = tipoProductoPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoProductoPK != null ? tipoProductoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoProducto)) {
            return false;
        }
        TipoProducto other = (TipoProducto) object;
        if ((this.tipoProductoPK == null && other.tipoProductoPK != null) || (this.tipoProductoPK != null && !this.tipoProductoPK.equals(other.tipoProductoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.TipoProducto[ tipoProductoPK=" + tipoProductoPK + " ]";
    }
    
}
