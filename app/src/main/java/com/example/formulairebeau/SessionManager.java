package com.example.formulairebeau;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private final static String PREFS_NAME = "app_prefs";
    private final static int PRIVATE_MODE = 0;
    private final static String IS_LOGGED = "isLogged";
    private final static String EMAIL = "email";
    private final static String IS_MEDECIN = "isMedecin";
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public boolean isLogged() {
        return prefs.getBoolean(IS_LOGGED, false);
    }
    public String getEmail() {
        return prefs.getString(EMAIL, null);
    }
    public String getIsMedecin() {
        return prefs.getString(IS_MEDECIN, null);
    }
    public void insertUser(String email, String isMedecin) {
        editor.putBoolean(IS_LOGGED, true);
        editor.putString(EMAIL, email);
        editor.putString(IS_MEDECIN, isMedecin);
        editor.commit();
    }
    public void logout() {
        editor.clear().commit();
    }
}
