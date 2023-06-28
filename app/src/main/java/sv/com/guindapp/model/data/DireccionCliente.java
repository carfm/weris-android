/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.guindapp.model.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import sv.com.guindapp.model.interfaces.IParametro;

/**
 * @author carlo
 */

public class DireccionCliente implements Serializable, IParametro {

    private static final long serialVersionUID = 1L;
    protected DireccionClientePK direccionClientePK;
    private String puntoReferencia;
    private String direccion;
    private String telefono;
    private String numeroCasa;
    private Date fechaIngreso;
    private String ciudad;
    private String tipo;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private Municipio municipio;
    private Cliente cliente;

    public DireccionCliente() {
    }

    public DireccionCliente(DireccionClientePK direccionClientePK) {
        this.direccionClientePK = direccionClientePK;
    }

    public DireccionCliente(int id, int idCompania, int idCliente) {
        this.direccionClientePK = new DireccionClientePK(id, idCompania, idCliente);
    }

    public DireccionClientePK getDireccionClientePK() {
        return direccionClientePK;
    }

    public void setDireccionClientePK(DireccionClientePK direccionClientePK) {
        this.direccionClientePK = direccionClientePK;
    }


    public String getPuntoReferencia() {
        return puntoReferencia;
    }

    public void setPuntoReferencia(String puntoReferencia) {
        this.puntoReferencia = puntoReferencia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (direccionClientePK != null ? direccionClientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DireccionCliente)) {
            return false;
        }
        DireccionCliente other = (DireccionCliente) object;
        if ((this.direccionClientePK == null && other.direccionClientePK != null) || (this.direccionClientePK != null && !this.direccionClientePK.equals(other.direccionClientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "/;id=" + this.getDireccionClientePK().getId() + ";idCompania=" + this.getDireccionClientePK().getIdCompania() + ";idCliente=" + this.getDireccionClientePK().getIdCliente() + "";
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }
}
