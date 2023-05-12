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

public class MunicipioPK implements Serializable {

    private int id;
    private int idPais;
    private int idDepartamento;

    public MunicipioPK() {
    }

    public MunicipioPK(int id, int idPais, int idDepartamento) {
        this.id = id;
        this.idPais = idPais;
        this.idDepartamento = idDepartamento;
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

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) idPais;
        hash += (int) idDepartamento;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MunicipioPK)) {
            return false;
        }
        MunicipioPK other = (MunicipioPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.idPais != other.idPais) {
            return false;
        }
        if (this.idDepartamento != other.idDepartamento) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.MunicipioPK[ id=" + id + ", idPais=" + idPais + ", idDepartamento=" + idDepartamento + " ]";
    }
    
}
