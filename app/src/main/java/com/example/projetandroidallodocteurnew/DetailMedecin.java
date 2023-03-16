package com.example.projetandroidallodocteurnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class DetailMedecin extends AppCompatActivity {

    private TextView tvPrenomEtNom, tvSpecialite, tvAdresse, tvCodePostalEtVille, tvPresentation, tvTarifs, tvMoyensDePaiement;
    private Button btnFlecheRetour, btnPrendreRDV;
    private String email;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_medecin);

        tvPrenomEtNom = findViewById(R.id.tvPrenomEtNom);
        tvSpecialite = findViewById(R.id.tvSpecialite);
        tvAdresse = findViewById(R.id.tvAdresse);
        tvCodePostalEtVille = findViewById(R.id.tvCodePostalEtVille);
        tvPresentation = findViewById(R.id.tvPresentation);
        tvTarifs = findViewById(R.id.tvTarifs);
        tvMoyensDePaiement = findViewById(R.id.tvMoyensDePaiement);
        btnFlecheRetour = findViewById(R.id.btnFlecheRetour);
        btnPrendreRDV = findViewById(R.id.btnPrendreRDV);

        databaseManager = new DatabaseManager(DetailMedecin.this);

        //On crée un listener qui viendra exécuter du code lorsqu'on recevra la réponse de la bdd
        DatabaseManager.VolleyResponseListener listener = new DatabaseManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) {
                String success = null;
                try {
                    success = response.getString("success");

                    if (success.equals("true")) {

                        JSONArray medecin = response.getJSONArray("infoMedecin");
                        JSONObject infoMedecin = medecin.getJSONObject(0);
                        System.out.println(infoMedecin);

                        //Renseigner toutes les données nécessaires dans les TextView
                        tvPrenomEtNom.setText(infoMedecin.getString("prenom") + " " + infoMedecin.getString("nom").toUpperCase());
                        tvSpecialite.setText(infoMedecin.getString("specialite"));
                        tvAdresse.setText(infoMedecin.getString("adressePro"));
                        tvCodePostalEtVille.setText(infoMedecin.getString("codePostalPro") + " " + infoMedecin.getString("villePro"));
                        tvPresentation.setText(infoMedecin.getString("presentation"));
                        tvTarifs.setText(infoMedecin.getString("tarifs"));
                        tvMoyensDePaiement.setText(infoMedecin.getString("moyensPaiements"));

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

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            email = extras.getString("email");
            databaseManager.getOneMedecin(email, listener);
        } else {
            System.out.println("Extras vide dans l'activité DetailMedecin, retour à l'activité précédente");
            Intent erreurExtras = new Intent(DetailMedecin.this, RechercheMedecin.class);
            startActivity(erreurExtras);
            finish();
        }


        btnFlecheRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(DetailMedecin.this, RechercheMedecin.class);
                startActivity(goBack);
                finish();
            }
        });
    }
}