/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.weris.model.data;

import java.math.BigDecimal;

import sv.com.weris.model.interfaces.Listado;

public class ProdAgregado extends Listado {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Short estado;


    public ProdAgregado() {
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }


    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }


}
