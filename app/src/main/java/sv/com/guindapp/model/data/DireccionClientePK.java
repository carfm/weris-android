/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.guindapp.model.data;

import java.io.Serializable;

public class DireccionClientePK implements Serializable {

    private int id;
    private int idCompania;
    private int idCliente;

    public DireccionClientePK() {
    }

    public DireccionClientePK(int id, int idCompania, int idCliente) {
        this.id = id;
        this.idCompania = idCompania;
        this.idCliente = idCliente;
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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) idCompania;
        hash += (int) idCliente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DireccionClientePK)) {
            return false;
        }
        DireccionClientePK other = (DireccionClientePK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.idCompania != other.idCompania) {
            return false;
        }
        if (this.idCliente != other.idCliente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.DireccionClientePK[ id=" + id + ", idCompania=" + idCompania + ", idCliente=" + idCliente + " ]";
    }
    
}
