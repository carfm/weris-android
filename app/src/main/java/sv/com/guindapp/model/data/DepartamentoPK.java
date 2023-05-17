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
public class DepartamentoPK implements Serializable {

    private int id;
    private int idPais;

    public DepartamentoPK() {
    }

    public DepartamentoPK(int id, int idPais) {
        this.id = id;
        this.idPais = idPais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) idPais;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DepartamentoPK)) {
            return false;
        }
        DepartamentoPK other = (DepartamentoPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.idPais != other.idPais) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.DepartamentoPK[ id=" + id + ", idPais=" + idPais + " ]";
    }
    
}
