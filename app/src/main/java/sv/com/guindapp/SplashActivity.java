package sv.com.guindapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


public class SplashActivity extends Activity {

    Intent menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new MiTareaAsincrona().execute();
    }

    private void tareaLarga() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
    }

    private class MiTareaAsincrona extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            for (int i = 1; i <= 10; i++) {
                tareaLarga();
                publishProgress(i * 10);
                if (isCancelled())
                    break;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Boolean result) {
            iniciarActivity();

        }
    }

    public void iniciarActivity(){

        menu = new Intent(getApplicationContext(), MainActivity.class);
        menu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(menu);
        finish();
    }
}
