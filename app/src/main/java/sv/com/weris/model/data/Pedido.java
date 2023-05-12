/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.weris.model.data;


import java.util.List;

import sv.com.weris.model.entity.CardClient;
import sv.com.weris.model.interfaces.IParametro;


public class Pedido implements IParametro {

    private Orden orden;
    private List<DetOrden> detOrdenList;
    private CardClient cc;

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public List<DetOrden> getDetOrdenList() {
        return detOrdenList;
    }

    public void setDetOrdenList(List<DetOrden> detOrdenList) {
        this.detOrdenList = detOrdenList;
    }

    public CardClient getCc() {
        return cc;
    }

    public void setCc(CardClient cc) {
        this.cc = cc;
    }
}
