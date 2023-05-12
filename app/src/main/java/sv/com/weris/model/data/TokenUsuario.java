/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.weris.model.data;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author carlo
 */

public class TokenUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String userFirebase;
    private String token;
    private String app;
    private String estado;
    private Date fechaHora;

    public TokenUsuario() {
    }

    public TokenUsuario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TokenUsuario)) {
            return false;
        }
        TokenUsuario other = (TokenUsuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.TokenUsuario[ id=" + id + " ]";
    }

    public String getUserFirebase() {
        return userFirebase;
    }

    public void setUserFirebase(String userFirebase) {
        this.userFirebase = userFirebase;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

}
