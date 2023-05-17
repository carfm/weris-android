package sv.com.guindapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    private VideoView vvBgScreen;
    Intent menu;
    View placeholder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        placeholder = (View) findViewById(R.id.placeholder);
        vvBgScreen = findViewById(R.id.vv_bg_screen);
        ejecutarProceso();

    }

    public void ejecutarProceso() {
        new MiTareaAsincrona().execute();
    }

    private void tareaLarga() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
    }

    private class MiTareaAsincrona extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            /*vvBgScreen.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro));
            vvBgScreen.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    vvBgScreen.start();
                }
            });

            vvBgScreen.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    placeholder.setVisibility(View.GONE);
                }
            });
            //placeholder.setVisibility(View.GONE);
            tareaLarga();*/
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

    @Override
    public void onResume() {
        super.onResume();
        vvBgScreen.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        vvBgScreen.stopPlayback();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vvBgScreen.stopPlayback();
    }

    public void iniciarActivity() {

        menu = new Intent(getApplicationContext(), MainActivity.class);
        menu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(menu);
        finish();
    }
}
