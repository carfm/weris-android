/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.guindapp.model.data;

import java.io.Serializable;

import sv.com.guindapp.model.interfaces.IParametro;


/**
 *
 * @author carlo
 */

public class FormaPago implements Serializable, IParametro {

    private static final long serialVersionUID = 1L;
    protected FormaPagoPK formaPagoPK;
    private String nombre;
    private String abreviacion;

    public FormaPago() {
    }

    public FormaPago(FormaPagoPK formaPagoPK) {
        this.formaPagoPK = formaPagoPK;
    }

    public FormaPago(int id, int idCompania) {
        this.formaPagoPK = new FormaPagoPK(id, idCompania);
    }

    public FormaPagoPK getFormaPagoPK() {
        return formaPagoPK;
    }

    public void setFormaPagoPK(FormaPagoPK formaPagoPK) {
        this.formaPagoPK = formaPagoPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formaPagoPK != null ? formaPagoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormaPago)) {
            return false;
        }
        FormaPago other = (FormaPago) object;
        if ((this.formaPagoPK == null && other.formaPagoPK != null) || (this.formaPagoPK != null && !this.formaPagoPK.equals(other.formaPagoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.FormaPago[ formaPagoPK=" + formaPagoPK + " ]";
    }
    
}
