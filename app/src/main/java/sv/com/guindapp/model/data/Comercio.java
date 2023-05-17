/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.guindapp.model.data;

import java.io.Serializable;
import java.math.BigDecimal;

import sv.com.guindapp.model.interfaces.IParametro;


/**
 *
 * @author carlo
 */

public class Comercio implements Serializable, IParametro {

    private static final long serialVersionUID = 1L;
    protected ComercioPK comercioPK;
    private String nombre;
    private String direccion;
    private String telefono;
    private String contacto;
    private BigDecimal longitud;
    private BigDecimal latitud;
    private Afiliado afiliado;
    private SubCategoria subCategoria;
    private TipoSociedad idTipoSociedad;
    private Integer logo;
    private Integer imagen;
    private String url;
    private String urlPerfil;
    private String acercaDe;
    private BigDecimal consumoMinimo;
    private Integer tiempoEspera;
    private Boolean abierto;

    public Comercio() {
    }

    public Comercio(ComercioPK comercioPK) {
        this.comercioPK = comercioPK;
    }

    public Comercio(int id, int idCompania, int idAfiliado) {
        this.comercioPK = new ComercioPK(id, idCompania, idAfiliado);
    }

    public ComercioPK getComercioPK() {
        return comercioPK;
    }

    public void setComercioPK(ComercioPK comercioPK) {
        this.comercioPK = comercioPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }


    public Afiliado getAfiliado() {
        return afiliado;
    }

    public void setAfiliado(Afiliado afiliado) {
        this.afiliado = afiliado;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public TipoSociedad getIdTipoSociedad() {
        return idTipoSociedad;
    }

    public void setIdTipoSociedad(TipoSociedad idTipoSociedad) {
        this.idTipoSociedad = idTipoSociedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comercioPK != null ? comercioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comercio)) {
            return false;
        }
        Comercio other = (Comercio) object;
        if ((this.comercioPK == null && other.comercioPK != null) || (this.comercioPK != null && !this.comercioPK.equals(other.comercioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.Comercio[ comercioPK=" + comercioPK + " ]";
    }

    public Integer getLogo() {
        return logo;
    }

    public void setLogo(Integer logo) {
        this.logo = logo;
    }

    public Integer getImagen() {
        return imagen;
    }

    public void setImagen(Integer imagen) {
        this.imagen = imagen;
    }

    public String getUrlPerfil() {
        return urlPerfil;
    }

    public void setUrlPerfil(String urlPerfil) {
        this.urlPerfil = urlPerfil;
    }

    public String getAcercaDe() {
        return acercaDe;
    }

    public void setAcercaDe(String acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BigDecimal getConsumoMinimo() {
        return consumoMinimo;
    }

    public void setConsumoMinimo(BigDecimal consumoMinimo) {
        this.consumoMinimo = consumoMinimo;
    }

    public Integer getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(Integer tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getAbierto() {
        return abierto;
    }

    public void setAbierto(Boolean abierto) {
        this.abierto = abierto;
    }
}
