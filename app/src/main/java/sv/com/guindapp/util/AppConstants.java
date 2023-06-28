package sv.com.guindapp.util;

public class AppConstants {
    public static final String DATE_FORMAT_CLIENT = "DD/MM/YYYY";
    public static final String DATETIME_FORMAT_CLIENT = "DD/MM/YYYY hh12:mm a";

    public static final String DATETIME_FORMAT_TAG = "yyyy-MM-DD hh:mm:ss";

    public static final String DATETIME_FORMAT_API = "DD/MM/yyyy hh:mm:ss";// "YYYY-MM-DD hh:mm:ss";
    public static final String DATE_FORMAT_API = "DD/MM/YYYY"; //"YYYY-MM-DD";

    //http://3.211.243.157:8080/guindapp/webresources/
    public static final String BASE_URL = "https://ecobilling.agiltechnology.com/";

    public static final String BASE_URL_API = BASE_URL + "weris/webresources/";

    public static final Integer ESTADO_ORDEN_GENERADA = 1;
    public static final Integer ESTADO_ORDEN_EN_PREPARACION = 2;
    public static final Integer ESTADO_ORDEN_ORDEN_LISTA = 3;
    public static final Integer ESTADO_ORDEN_ENTREGADA_A_DRIVER = 4;
    public static final Integer ESTADO_ORDEN_EN_RUTA = 5;
    public static final Integer ESTADO_ORDEN_ENTREGADA_A_CLIENTE = 6;
    public static final Integer ESTADO_ORDEN_LLEGO_POR_LA_ORDEN = 9;
    public static final Integer ESTADO_ORDEN_CANCELADA = 7;
    public static final Integer ESTADO_ORDEN_POR_ASIGNAR = 11;
    public static final Integer ESTADO_ORDEN_LLEGO_PUNTO_ENTREGA = 10;


}
