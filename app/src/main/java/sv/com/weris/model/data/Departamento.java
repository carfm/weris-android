/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.weris.model.data;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author carlo
 */

public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;
    protected DepartamentoPK departamentoPK;
    private String nombre;
    private List<Municipio> municipioList;
    private Pais pais;

    public Departamento() {
    }

    public Departamento(DepartamentoPK departamentoPK) {
        this.departamentoPK = departamentoPK;
    }

    public Departamento(int id, int idPais) {
        this.departamentoPK = new DepartamentoPK(id, idPais);
    }

    public DepartamentoPK getDepartamentoPK() {
        return departamentoPK;
    }

    public void setDepartamentoPK(DepartamentoPK departamentoPK) {
        this.departamentoPK = departamentoPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Municipio> getMunicipioList() {
        return municipioList;
    }

    public void setMunicipioList(List<Municipio> municipioList) {
        this.municipioList = municipioList;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (departamentoPK != null ? departamentoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.departamentoPK == null && other.departamentoPK != null) || (this.departamentoPK != null && !this.departamentoPK.equals(other.departamentoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.Departamento[ departamentoPK=" + departamentoPK + " ]";
    }
    
}
