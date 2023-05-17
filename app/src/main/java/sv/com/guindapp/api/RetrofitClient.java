package sv.com.guindapp.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.com.guindapp.util.AppConstants;

import static sv.com.guindapp.api.UtilAPI.getGsonSync;


public class RetrofitClient {


    private static Retrofit retrofit = null;
    private static Retrofit retrofitAuth = null;
    public RetrofitClient() {
    }

    public static void clearAuthClient(){
        retrofitAuth = null;
    }

    public static Retrofit getClientAuth() {

        //todo borrar
        PreferencesHelper prefas = PreferencesHelper.getInstance();
        String accessTokena = prefas.getAccessToken();
        Log.d("ERRORAR", "Current" + accessTokena);

        if (retrofitAuth == null) {

            PreferencesHelper prefs = PreferencesHelper.getInstance();
            final String accessToken = prefs.getAccessToken();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            //httpClient.authenticator(new TokenAuthenticator());
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    //todo borrar
                    Log.d("ERRORAR", "Sended" + accessToken);

                    Request request = original.newBuilder()
                            .header("Authorization", accessToken)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClient.build();

            retrofitAuth = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL_API)
                    .addConverterFactory(GsonConverterFactory.create(getGsonSync()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitAuth;
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL_API)
                    .addConverterFactory(GsonConverterFactory.create(getGsonSync()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
