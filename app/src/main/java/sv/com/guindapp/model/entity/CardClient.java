package sv.com.guindapp.model.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import sv.com.guindapp.model.interfaces.IParametro;

@Entity
public class CardClient implements IParametro {
    @Id
    private long id;
    private String nombre;
    private String rx;//Numero Tarjeta
    private String ry; //mes Vencimiento
    private String ry1; //anio Vencimiento
    private String rz;//cvv
    private Integer autorizado;
    private String tkn;//token
    private String ath;//Numero de autorizacion serfinsa
    private Integer defl;//predeterminada
    private String client;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRx() {
        return rx;
    }

    public void setRx(String rx) {
        this.rx = rx;
    }

    public String getRy() {
        return ry;
    }

    public void setRy(String ry) {
        this.ry = ry;
    }

    public String getRz() {
        return rz;
    }

    public void setRz(String rz) {
        this.rz = rz;
    }

    public Integer getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Integer autorizado) {
        this.autorizado = autorizado;
    }

    public String getTkn() {
        return tkn;
    }

    public void setTkn(String tkn) {
        this.tkn = tkn;
    }

    public String getAth() {
        return ath;
    }

    public void setAth(String ath) {
        this.ath = ath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getDefl() {
        return defl;
    }

    public void setDefl(Integer defl) {
        this.defl = defl;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getRy1() {
        return ry1;
    }

    public void setRy1(String ry1) {
        this.ry1 = ry1;
    }
}
