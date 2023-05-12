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

public class Trasportista implements Serializable {

    private static final long serialVersionUID = 1L;
    protected TrasportistaPK trasportistaPK;
    private String nombres;
    private String apellidos;
    private String numIdentidad;
    private String correo;
    private String telefono;
    private String celular;
    private String poseeEmpleo;
    private String tiempoCompleto;
    private Compania compania;
    private Municipio municipio;
    private TipoTransporte idTipoVehiculo;
    private TipoTransportista idTipoTransportista;

    public Trasportista() {
    }

    public Trasportista(TrasportistaPK trasportistaPK) {
        this.trasportistaPK = trasportistaPK;
    }

    public Trasportista(int id, int idCompania) {
        this.trasportistaPK = new TrasportistaPK(id, idCompania);
    }

    public TrasportistaPK getTrasportistaPK() {
        return trasportistaPK;
    }

    public void setTrasportistaPK(TrasportistaPK trasportistaPK) {
        this.trasportistaPK = trasportistaPK;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumIdentidad() {
        return numIdentidad;
    }

    public void setNumIdentidad(String numIdentidad) {
        this.numIdentidad = numIdentidad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getPoseeEmpleo() {
        return poseeEmpleo;
    }

    public void setPoseeEmpleo(String poseeEmpleo) {
        this.poseeEmpleo = poseeEmpleo;
    }

    public String getTiempoCompleto() {
        return tiempoCompleto;
    }

    public void setTiempoCompleto(String tiempoCompleto) {
        this.tiempoCompleto = tiempoCompleto;
    }

    public Compania getCompania() {
        return compania;
    }

    public void setCompania(Compania compania) {
        this.compania = compania;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }


    public TipoTransporte getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    public void setIdTipoVehiculo(TipoTransporte idTipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
    }

    public TipoTransportista getIdTipoTransportista() {
        return idTipoTransportista;
    }

    public void setIdTipoTransportista(TipoTransportista idTipoTransportista) {
        this.idTipoTransportista = idTipoTransportista;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trasportistaPK != null ? trasportistaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trasportista)) {
            return false;
        }
        Trasportista other = (Trasportista) object;
        if ((this.trasportistaPK == null && other.trasportistaPK != null) || (this.trasportistaPK != null && !this.trasportistaPK.equals(other.trasportistaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.Trasportista[ trasportistaPK=" + trasportistaPK + " ]";
    }
    
}
