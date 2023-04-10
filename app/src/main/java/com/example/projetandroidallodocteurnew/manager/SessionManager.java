package com.example.projetandroidallodocteurnew.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private final static String PREFS_NAME = "app_prefs";
    private final static int PRIVATE_MODE = 0;
    private final static String IS_LOGGED = "isLogged";
    private final static String EMAIL = "email";
    private final static String SEXE = "sexe";
    private final static String NOM = "nom";
    private final static String PRENOM = "prenom";
    private final static String DATE_NAISSANCE = "dateNaissance";
    private final static String IS_MEDECIN = "isMedecin";
    private final static String TELEPHONE = "telephone";
    private final static String ADRESSE = "adresse";
    private final static String CODE_POSTAL = "codePostal";
    private final static String VILLE = "ville";
    private final static String IMAGE_PROFIL = "imageProfil";
    private final static String DATE_CREATION = "dateCreation";
    private final static String DATE_DERNIERE_CONNEXION = "dateDerniereConnexion";
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
    public String getSexe() {
        return prefs.getString(SEXE, "");
    }
    public String getNom() {
        return prefs.getString(NOM, "");
    }
    public String getPrenom() {
        return prefs.getString(PRENOM, "");
    }
    public String getDateNaissance() {
        return prefs.getString(DATE_NAISSANCE, "");
    }
    public String getTelephone() {
        return prefs.getString(TELEPHONE, "");
    }
    public String getAdresse() {
        return prefs.getString(ADRESSE, "");
    }
    public String getCodePostal() {
        return prefs.getString(CODE_POSTAL, "");
    }
    public String getVille() {
        return prefs.getString(VILLE, "");
    }
    public String getImageProfil() {
        return prefs.getString(IMAGE_PROFIL, "");
    }
    public String getDateCreation() {
        return prefs.getString(DATE_CREATION, "");
    }
    public String getDateDerniereConnexion() {
        return prefs.getString(DATE_DERNIERE_CONNEXION, "");
    }
    public void insertUser(String email, String sexe, String nom, String prenom, String dateNaissance, String isMedecin, String telephone, String adresse, String codePostal, String ville, String imageProfil, String dateCreation, String dateDerniereConnexion) {
        editor.putBoolean(IS_LOGGED, true);
        editor.putString(EMAIL, email);
        editor.putString(SEXE, sexe);
        editor.putString(NOM, nom);
        editor.putString(PRENOM, prenom);
        editor.putString(DATE_NAISSANCE, dateNaissance);
        editor.putString(IS_MEDECIN, isMedecin);
        editor.putString(TELEPHONE, telephone);
        editor.putString(ADRESSE, adresse);
        editor.putString(CODE_POSTAL, codePostal);
        editor.putString(VILLE, ville);
        editor.putString(IMAGE_PROFIL, imageProfil);
        editor.putString(DATE_CREATION, dateCreation);
        editor.putString(DATE_DERNIERE_CONNEXION, dateDerniereConnexion);
        editor.commit();
    }

    public void modifyUser(String nomVariable, String valeur) {
        switch (nomVariable) {
            case "email":
                editor.putString(EMAIL, valeur);
                editor.commit();
                break;
            case "sexe":
                editor.putString(SEXE, valeur);
                editor.commit();
                break;
            case "nom":
                editor.putString(NOM, valeur);
                editor.commit();
                break;
            case "prenom":
                editor.putString(PRENOM, valeur);
                editor.commit();
                break;
            case "dateNaissance":
                editor.putString(DATE_NAISSANCE, valeur);
                editor.commit();
                break;
            case "isMedecin":
                editor.putString(IS_MEDECIN, valeur);
                editor.commit();
                break;
            case "telephone":
                editor.putString(TELEPHONE, valeur);
                editor.commit();
                break;
            case "adresse":
                editor.putString(ADRESSE, valeur);
                editor.commit();
                break;
            case "codePostal":
                editor.putString(CODE_POSTAL, valeur);
                editor.commit();
                break;
            case "ville":
                editor.putString(VILLE, valeur);
                editor.commit();
                break;
            case "imageProfil":
                editor.putString(IMAGE_PROFIL, valeur);
                editor.commit();
                break;
            case "dateDerniereConnexion":
                editor.putString(DATE_DERNIERE_CONNEXION, valeur);
                editor.commit();
                break;
        }
    }
    public void logout() {
        editor.clear().commit();
    }
}
