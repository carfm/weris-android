/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.weris.model.data;

import java.io.Serializable;



public class AfiliadoPK implements Serializable {


    private int id;
    private int idCompania;

    public AfiliadoPK() {
    }

    public AfiliadoPK(int id, int idCompania) {
        this.id = id;
        this.idCompania = idCompania;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) idCompania;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfiliadoPK)) {
            return false;
        }
        AfiliadoPK other = (AfiliadoPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.idCompania != other.idCompania) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.AfiliadoPK[ id=" + id + ", idCompania=" + idCompania + " ]";
    }
    
}
