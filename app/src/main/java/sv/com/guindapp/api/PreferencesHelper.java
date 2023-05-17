package sv.com.guindapp.api;

import android.content.Context;
import android.content.SharedPreferences;

import hirondelle.date4j.DateTime;
import sv.com.guindapp.util.App;

public class PreferencesHelper {

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_SYNC_USER = "PREF_KEY_SYNC_USER";
    private static final String PREF_KEY_SYNC_UBIGEO = "PREF_KEY_SYNC_UBIGEO";
    private static final String PREF_KEY_TOKEN_FCM = "PREF_KEY_TOKEN_FCM";
    private static final String PREF_KEY_NOTIFICATION_ID = "PREF_KEY_NOTIFICATION_ID";

    private final SharedPreferences mPrefs;

    public PreferencesHelper(Context context) {
        mPrefs = context.getSharedPreferences("prefs_app", Context.MODE_PRIVATE);
    }

    public DateTime getLastSyncUbigeo() {
        String dateTime = mPrefs.getString(PREF_KEY_SYNC_UBIGEO, "");
        return dateTime.isEmpty() ? null : new DateTime(dateTime);
    }

    public void setLastSyncUbigeo(DateTime dateTime) {
        mPrefs.edit().putString(PREF_KEY_SYNC_UBIGEO, dateTime.toString()).apply();
    }

    public int getCurrentUserLoggedInMode() {
        return mPrefs.getInt(PREF_KEY_USER_LOGGED_IN_MODE, LoggedMode.LOGGED_OUT);
    }

    public void setCurrentUserLoggedInMode(int mode) {
        mPrefs.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode).apply();
    }

    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    public void setTokenFCM(String tokenFCM) {
        mPrefs.edit().putString(PREF_KEY_TOKEN_FCM, tokenFCM).apply();
    }

    public int getNotificationId() {
        return mPrefs.getInt(PREF_KEY_NOTIFICATION_ID, 0);
    }

    public void setNotificationId(int notificationId) {
        mPrefs.edit().putInt(PREF_KEY_NOTIFICATION_ID, notificationId).apply();
    }

    public String getTokenFCM() {
        return mPrefs.getString(PREF_KEY_TOKEN_FCM, null);
    }

    public void setSyncUser(boolean syncUser) {
        mPrefs.edit().putBoolean(PREF_KEY_SYNC_USER, syncUser).apply();
    }

    public boolean getSyncUser() {
        return mPrefs.getBoolean(PREF_KEY_SYNC_USER, false);
    }

    /*public Usuario getUser() {
        String usuarioJSON = mPrefs.getString(PREF_KEY_USER, null);
        if(usuarioJSON == null){
            return null;
        }
        Gson gson = UtilAPI.getGsonSync();
        return gson.fromJson(usuarioJSON, Usuario.class);
    }

    public void setUser(Usuario usuario) {
        Gson gson = UtilAPI.getGsonSync();
        String usuarioJSON = gson.toJson(usuario);
        mPrefs.edit().putString(PREF_KEY_USER, usuarioJSON).apply();
    }*/

    private static PreferencesHelper preferencesHelper = null;

    public static PreferencesHelper getInstance(){
        if(preferencesHelper == null){
            preferencesHelper = new PreferencesHelper(App.getAppContext());
        }
        return preferencesHelper;
    }

}
