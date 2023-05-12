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

public class Afiliado implements Serializable {

    private static final long serialVersionUID = 1L;
    protected AfiliadoPK afiliadoPK;
    private String nombre;
    private String contacto;
    private String telefono;
    private Compania compania;

    public Afiliado() {
    }

    public Afiliado(AfiliadoPK afiliadoPK) {
        this.afiliadoPK = afiliadoPK;
    }

    public Afiliado(int id, int idCompania) {
        this.afiliadoPK = new AfiliadoPK(id, idCompania);
    }

    public AfiliadoPK getAfiliadoPK() {
        return afiliadoPK;
    }

    public void setAfiliadoPK(AfiliadoPK afiliadoPK) {
        this.afiliadoPK = afiliadoPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }



    public Compania getCompania() {
        return compania;
    }

    public void setCompania(Compania compania) {
        this.compania = compania;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (afiliadoPK != null ? afiliadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Afiliado)) {
            return false;
        }
        Afiliado other = (Afiliado) object;
        if ((this.afiliadoPK == null && other.afiliadoPK != null) || (this.afiliadoPK != null && !this.afiliadoPK.equals(other.afiliadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.Afiliado[ afiliadoPK=" + afiliadoPK + " ]";
    }
    
}
