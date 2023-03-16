package com.example.projetandroidallodocteurnew;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfilMedecin extends AppCompatActivity {

    private SessionManager sessionManager;
    private DatabaseManager databaseManager;
    private TextView presentationMed, prenomMed, nomMed, specialiteMed, adresseMed, villeMed, codePostalMed, prixMed;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_medecin);

        sessionManager = new SessionManager(this);
        databaseManager = new DatabaseManager(this);

        //Récuperer ID pour TextView
        presentationMed = findViewById(R.id.presentationMedecin);
        prenomMed = findViewById(R.id.PrenomMedecin);
        nomMed = findViewById(R.id.NomMedecin);
        specialiteMed = findViewById(R.id.specialiteMedecin);
        adresseMed = findViewById(R.id.AdresseMedecin);
        villeMed = findViewById(R.id.VilleMedecin);
        codePostalMed = findViewById(R.id.CodePostalMedecin);
        prixMed = findViewById(R.id.PrixMedecin);

        email = sessionManager.getEmail();

        DatabaseManager.VolleyResponseListener listener = new DatabaseManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(JSONObject response) {
                String success = null;
                try {
                    success = response.getString("success");

                    if (success.equals("true")) {

                        JSONArray medecin = response.getJSONArray("infoMedecin");
                        JSONObject infoMedecin = medecin.getJSONObject(0);

                        //Renseigner toutes les données nécessaires dans les TextView
                        //tvPrenomEtNom.setText(infoMedecin.getString("prenom") + " " + infoMedecin.getString("nom").toUpperCase());
                        if (infoMedecin.getString("presentation") == "" || infoMedecin.getString("presentation") == null) {
                            presentationMed.setText("Non renseignez");
                        }


                    } else {
                        //Afficher l'erreur dans une textview prévu à cet effet, où rediriger l'utilisateur
                        //vers une autre page et afficher un Toast.
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        //Appel API pour recupérer les infos du medecin.
        databaseManager.getOneMedecin(email, listener);

    }
}