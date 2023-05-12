/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.weris.model.data;

import java.io.Serializable;

/**
 *
 * @author carlo
 */
public class SubCategoriaPK implements Serializable {

    private int id;
    private int idCategoria;

    public SubCategoriaPK() {
    }

    public SubCategoriaPK(int id, int idCategoria) {
        this.id = id;
        this.idCategoria = idCategoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) idCategoria;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubCategoriaPK)) {
            return false;
        }
        SubCategoriaPK other = (SubCategoriaPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.idCategoria != other.idCategoria) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.SubCategoriaPK[ id=" + id + ", idCategoria=" + idCategoria + " ]";
    }
    
}
