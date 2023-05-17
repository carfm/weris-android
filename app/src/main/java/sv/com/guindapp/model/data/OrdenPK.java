/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.guindapp.model.data;

import java.io.Serializable;


public class OrdenPK implements Serializable {

    private int id;
    private int idComercio;
    private int idCompania;
    private int idAfiliado;

    public OrdenPK() {
    }

    public OrdenPK(int id, int idComercio, int idCompania, int idAfiliado) {
        this.id = id;
        this.idComercio = idComercio;
        this.idCompania = idCompania;
        this.idAfiliado = idAfiliado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
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
        hash += (int) idComercio;
        hash += (int) idCompania;
        hash += (int) idAfiliado;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenPK)) {
            return false;
        }
        OrdenPK other = (OrdenPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.idComercio != other.idComercio) {
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
        return "com.guindapp.entidades.OrdenPK[ id=" + id + ", idComercio=" + idComercio + ", idCompania=" + idCompania + ", idAfiliado=" + idAfiliado + " ]";
    }
    
}
