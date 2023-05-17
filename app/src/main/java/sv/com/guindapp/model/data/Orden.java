/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.guindapp.model.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import sv.com.guindapp.model.interfaces.IParametro;

/**
 *
 * @author carlo
 */

public class Orden implements Serializable, IParametro {

    private static final long serialVersionUID = 1L;
    protected OrdenPK ordenPK;
    private Integer tipoOrden;
    private Date fechaOrden;
    private String observaciones;
    private String token;
    private BigDecimal subtotal;
    private BigDecimal impuesto;
    private BigDecimal descuento;
    private BigDecimal total;
    private BigDecimal comision;
    private BigDecimal otrosCargos;
    private String referencia;
    private Afiliado afiliado;
    private Comercio comercio;
    private Cliente cliente;
    private EstadoOrden estadoOrden;
    private DireccionCliente direccionCliente;
    private FormaPago formaPago;
    private Transportista  transportista;
    private List<EntregaAsignacion> entregaAsignacionList;
    private List<DetOrden> detOrdenList;
    private String idOrden;
    private Integer metodoPago;
    private transient MetodoPago metodoPagoSeleccionado;

    private ComercioSucursalPK comercioSucursalSeleccionado;
    private TipoTransporte tipoTransporteCalculado;
    private BigDecimal kmBase;

    public Orden() {
    }

    public Orden(OrdenPK ordenPK) {
        this.ordenPK = ordenPK;
    }

    public Orden(int id, int idCaja, int idCompania, int idAfiliado) {
        this.ordenPK = new OrdenPK(id, idCaja, idCompania, idAfiliado);
    }

    public OrdenPK getOrdenPK() {
        return ordenPK;
    }

    public void setOrdenPK(OrdenPK ordenPK) {
        this.ordenPK = ordenPK;
    }

    public Integer getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(Integer tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Afiliado getAfiliado() {
        return afiliado;
    }

    public void setAfiliado(Afiliado afiliado) {
        this.afiliado = afiliado;
    }


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EstadoOrden getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(EstadoOrden estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    public List<EntregaAsignacion> getEntregaAsignacionList() {
        return entregaAsignacionList;
    }

    public void setEntregaAsignacionList(List<EntregaAsignacion> entregaAsignacionList) {
        this.entregaAsignacionList = entregaAsignacionList;
    }



    public void setDetOrdenList(List<DetOrden> detOrdenList) {
        this.detOrdenList = detOrdenList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordenPK != null ? ordenPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orden)) {
            return false;
        }
        Orden other = (Orden) object;
        if ((this.ordenPK == null && other.ordenPK != null) || (this.ordenPK != null && !this.ordenPK.equals(other.ordenPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guindapp.entidades.Orden[ ordenPK=" + ordenPK + " ]";
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }

    public List<DetOrden> getDetOrdenList() {
        return detOrdenList;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public BigDecimal getOtrosCargos() {
        return otrosCargos;
    }

    public void setOtrosCargos(BigDecimal otrosCargos) {
        this.otrosCargos = otrosCargos;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DireccionCliente getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(DireccionCliente direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Transportista getTransportista() {
        return transportista;
    }

    public void setTransportista(Transportista transportista) {
        this.transportista = transportista;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    public Integer getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(Integer metodoPago) {
        this.metodoPago = metodoPago;
    }

    public MetodoPago getMetodoPagoSeleccionado() {
        return metodoPagoSeleccionado;
    }

    public void setMetodoPagoSeleccionado(MetodoPago metodoPagoSeleccionado) {
        this.metodoPagoSeleccionado = metodoPagoSeleccionado;
    }

    public ComercioSucursalPK getComercioSucursalSeleccionado() {
        return comercioSucursalSeleccionado;
    }

    public void setComercioSucursalSeleccionado(ComercioSucursalPK comercioSucursalSeleccionado) {
        this.comercioSucursalSeleccionado = comercioSucursalSeleccionado;
    }

    public TipoTransporte getTipoTransporteCalculado() {
        return tipoTransporteCalculado;
    }

    public void setTipoTransporteCalculado(TipoTransporte tipoTransporteCalculado) {
        this.tipoTransporteCalculado = tipoTransporteCalculado;
    }

    public BigDecimal getKmBase() {
        return kmBase;
    }

    public void setKmBase(BigDecimal kmBase) {
        this.kmBase = kmBase;
    }
}
