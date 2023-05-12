/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.weris.model.data;

import java.io.Serializable;


public class TrakingOrdenPK implements Serializable {


    private int id;

    private int idOrden;

    private int idCompania;

    private int idAfiliado;

    public TrakingOrdenPK() {
    }

    public TrakingOrdenPK(int id, int idOrden, int idCompania, int idAfiliado) {
        this.id = id;
        this.idOrden = idOrden;
        this.idCompania = idCompania;
        this.idAfiliado = idAfiliado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) idOrden;
        hash += (int) idCompania;
        hash += (int) idAfiliado;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrakingOrdenPK)) {
            return false;
        }
        TrakingOrdenPK other = (TrakingOrdenPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.idOrden != other.idOrden) {
            return false;
        }
        if (this.idCompania != other.idCompania) {
            return false;
        }
        if (this.idAfiliado != other.idAfiliado) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.TrakingOrdenPK[ id=" + id + ", idOrden=" + idOrden + ", idCompania=" + idCompania + ", idAfiliado=" + idAfiliado + " ]";
    }
    
}
