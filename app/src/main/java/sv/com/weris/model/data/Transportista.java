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

public class Transportista implements Serializable {

    private static final long serialVersionUID = 1L;
    protected TransportistaPK transportistaPK;
    private String nombres;
    private String apellidos;
    private String numIdentidad;
    private String correo;
    private String telefono;
    private String celular;
    private String poseeEmpleo;
    private String tiempoCompleto;
    private String uid_firebase;
    private String pathPerfil;
    private String urlPerfil;
    private String pathVehiculo;
    private String urlVehiculo;
    private Compania compania;
    private Municipio municipio;
    private TipoTransporte idTipoVehiculo;
    private TipoTransportista idTipoTransportista;

    public Transportista() {
    }

    public Transportista(TransportistaPK transportistaPK) {
        this.transportistaPK = transportistaPK;
    }

    public Transportista(int id, int idCompania) {
        this.transportistaPK = new TransportistaPK(id, idCompania);
    }

    public TransportistaPK getTransportistaPK() {
        return transportistaPK;
    }

    public void setTransportistaPK(TransportistaPK transportistaPK) {
        this.transportistaPK = transportistaPK;
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

    public String getUid_firebase() {
        return uid_firebase;
    }

    public void setUid_firebase(String uid_firebase) {
        this.uid_firebase = uid_firebase;
    }

    public String getPathPerfil() {
        return pathPerfil;
    }

    public void setPathPerfil(String pathPerfil) {
        this.pathPerfil = pathPerfil;
    }

    public String getUrlPerfil() {
        return urlPerfil;
    }

    public void setUrlPerfil(String urlPerfil) {
        this.urlPerfil = urlPerfil;
    }

    public String getPathVehiculo() {
        return pathVehiculo;
    }

    public void setPathVehiculo(String pathVehiculo) {
        this.pathVehiculo = pathVehiculo;
    }

    public String getUrlVehiculo() {
        return urlVehiculo;
    }

    public void setUrlVehiculo(String urlVehiculo) {
        this.urlVehiculo = urlVehiculo;
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
        hash += (transportistaPK != null ? transportistaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transportista)) {
            return false;
        }
        Transportista other = (Transportista) object;
        if ((this.transportistaPK == null && other.transportistaPK != null) || (this.transportistaPK != null && !this.transportistaPK.equals(other.transportistaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.Transportista[ transportistaPK=" + transportistaPK + " ]";
    }
    
}
