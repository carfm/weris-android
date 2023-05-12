package sv.com.weris.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.weris.model.data.Cliente;
import sv.com.weris.model.data.TokenUsuario;
import sv.com.weris.api.RetrofitClient;
import sv.com.weris.service.ServicesAPI;

/**
 * Created by Carlos y Jose on 20/9/2016.
 */
public class Util {

    public static final double IVA = 0.19;

    public static final String ESTADO_PEDIDO_APP_1 = "Por transmitir";
    public static final String ESTADO_PEDIDO_APP_2 = "Transmitido";
    public static final String ESTADO_PEDIDO_APP_3 = "Terminado";
    public static final int EDITANDO = 1;
    public static final int CREANDO = 0;
    public static final int CONSULTANDO = 0;
    public static final String TRUE = "Si";
    public static final String FALSE = "No";
    public static final String formatNum = "%03d";
    public static final NumberFormat formatMoneda = NumberFormat.getInstance(new Locale("es", "CO"));


    public static void cerrarTeclado(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /****
     * Method for Setting the Height of the ListView dynamically.
     * *** Hack to fix the issue of not showing all the items of the ListView
     * *** when placed inside a ScrollView
     ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void activity(final Context context, Activity a, boolean primero) {
        try {
            Intent inte = new Intent(context, a.getClass());
            if (primero) {
                inte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            }
            context.startActivity(inte);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean esNumero(String cadena) {
        try {
            Long.valueOf(cadena);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static boolean checkConn(Context ctx) {
        ConnectivityManager conMgr = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;
        return true;
    }

    public static void iniciarCalculador(Context ctx) {
        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
        final PackageManager pm = ctx.getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        for (PackageInfo pi : packs) {
            if (pi.packageName.toString().toLowerCase().contains("calcul")) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("appName", pi.applicationInfo.loadLabel(pm));
                map.put("packageName", pi.packageName);
                items.add(map);
            }
        }
        if (items.size() >= 1) {
            String packageName = (String) items.get(0).get("packageName");
            Intent i = pm.getLaunchIntentForPackage(packageName);
            if (i != null)
                ctx.startActivity(i);
        } else {
            // Application not found
            System.out.println("no hay calculadora");
        }
    }

    public static String stringBoolean(boolean b) {
        if (b) {
            return TRUE;
        } else {
            return FALSE;
        }
    }


    public static Object fabricarObjeto(String nombre) {
        try {
            Object obj = Class.forName(nombre).newInstance();
            return obj;
        } catch (Exception ex) {
            //Hacer algo
            return null;
        }
    }

    public static void restartApp(Context c) {
        Intent i = c.getPackageManager()
                .getLaunchIntentForPackage(c.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(i);
    }


    public static long diferenciaFechas(Date fecha) {
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
        java.util.Date hoy = new Date(); //Fecha de hoy

        long diferencia = (hoy.getTime() - fecha.getTime()) / MILLSECS_PER_DAY;
        return Math.abs(diferencia);
    }

    public static long restarFechas(Date fecha) {
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
        java.util.Date hoy = new Date(); //Fecha de hoy

        long diferencia = (hoy.getTime() - fecha.getTime()) / MILLSECS_PER_DAY;
        return diferencia;
    }


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public interface ExecuteParam {
        void onExecute();
    }

    public static void simpleAlert(String messague, Context context) {
        simpleAlert(messague, null, null, context);
    }

    public static void simpleAlert(String title, String messague, final ExecuteParam onOkAlert, Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (title != null) {
            alertDialog.setTitle(title);
        }

        alertDialog.setMessage(messague);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (onOkAlert != null) {
                            onOkAlert.onExecute();
                        }
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void actualizarToken(String token){
        ServicesAPI servicesAPI = RetrofitClient.getClient().create(ServicesAPI.class);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TokenUsuario tokenUsuario = new TokenUsuario();
        tokenUsuario.setApp("CLIENTE_ANDROID");
        tokenUsuario.setToken(token);
        tokenUsuario.setUserFirebase(user.getUid());
        Call call = servicesAPI.crearToken(tokenUsuario);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    System.out.println("Se envio: " + response);
                    //Toast.makeText(getContext(), "todo ok", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
//                esta.setText(t.getMessage());
                System.out.println(t.getMessage());
                //Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
