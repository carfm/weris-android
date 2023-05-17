package sv.com.guindapp.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

/**
 * Created by Carlos y Jose on 1/10/2016.
 */

public class Cargador {

    protected static ProgressDialog progressDialog;
    protected static String dialogMessage = "Cargando....";


    public static void show(Activity activity) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("");

        progressDialog.setMessage(dialogMessage);

        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // cancel AsyncTask

                    }
                });

    }

    public static void hide() {
        progressDialog.dismiss();

    }

}