/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.weris.model.data;

import java.io.Serializable;

import sv.com.weris.model.interfaces.IParametro;


/**
 *
 * @author carlo
 */

public class SubCategoria implements Serializable, IParametro {

    private static final long serialVersionUID = 1L;
    protected SubCategoriaPK subCategoriaPK;
    private String nombre;
    private Categoria categoria;

    public SubCategoria() {
    }

    public SubCategoria(SubCategoriaPK subCategoriaPK) {
        this.subCategoriaPK = subCategoriaPK;
    }

    public SubCategoria(int id, int idCategoria) {
        this.subCategoriaPK = new SubCategoriaPK(id, idCategoria);
    }

    public SubCategoriaPK getSubCategoriaPK() {
        return subCategoriaPK;
    }

    public void setSubCategoriaPK(SubCategoriaPK subCategoriaPK) {
        this.subCategoriaPK = subCategoriaPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subCategoriaPK != null ? subCategoriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubCategoria)) {
            return false;
        }
        SubCategoria other = (SubCategoria) object;
        if ((this.subCategoriaPK == null && other.subCategoriaPK != null) || (this.subCategoriaPK != null && !this.subCategoriaPK.equals(other.subCategoriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.SubCategoria[ subCategoriaPK=" + subCategoriaPK + " ]";
    }
    
}
