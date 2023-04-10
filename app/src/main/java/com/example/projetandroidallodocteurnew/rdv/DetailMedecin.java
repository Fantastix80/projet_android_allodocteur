package com.example.projetandroidallodocteurnew.rdv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.rdv.recyclerview.PrendreRDV;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailMedecin extends AppCompatActivity {

    private TextView tvPrenomEtNom, tvSpecialite, tvAdresse, tvCodePostalEtVille, tvPresentation, tvTarifs, tvMoyensDePaiement, tvDateDerniereCo, tvDateCreation;
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
        tvDateDerniereCo = findViewById(R.id.tvDateDerniereCo);
        tvDateCreation = findViewById(R.id.tvDateCreation);

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

                        //Renseigner toutes les données nécessaires dans les TextView
                        if (!(infoMedecin.getString("prenom").equals(null) || infoMedecin.getString("prenom").equals("null")) || !(infoMedecin.getString("nom").equals(null) || infoMedecin.getString("nom").equals("null"))) {
                            tvPrenomEtNom.setText(infoMedecin.getString("prenom") + " " + infoMedecin.getString("nom").toUpperCase());
                        }
                        if (!(infoMedecin.getString("specialite").equals(null) || infoMedecin.getString("specialite").equals("null"))) {
                            tvSpecialite.setText(infoMedecin.getString("specialite"));
                        }
                        if (!(infoMedecin.getString("adressePro").equals(null) || infoMedecin.getString("adressePro").equals("null"))) {
                            tvAdresse.setText(infoMedecin.getString("adressePro"));
                        }if (!(infoMedecin.getString("codePostalPro").equals(null) || infoMedecin.getString("codePostalPro").equals("null")) || !(infoMedecin.getString("villePro").equals(null) || infoMedecin.getString("villePro").equals("null"))) {
                            tvCodePostalEtVille.setText(infoMedecin.getString("codePostalPro") + " " + infoMedecin.getString("villePro"));
                        }if (!(infoMedecin.getString("presentation").equals(null) || infoMedecin.getString("presentation").equals("null"))) {
                            tvPresentation.setText(infoMedecin.getString("presentation"));
                        }if (!(infoMedecin.getString("tarifs").equals(null) || infoMedecin.getString("tarifs").equals("null"))) {
                            tvTarifs.setText(infoMedecin.getString("tarifs"));
                        }if (!(infoMedecin.getString("moyensPaiements").equals(null) || infoMedecin.getString("moyensPaiements").equals("null"))) {
                            tvMoyensDePaiement.setText(infoMedecin.getString("moyensPaiements"));
                        }
                        if (!(infoMedecin.getString("dateDerniereConnexion").equals(null) || infoMedecin.getString("dateDerniereConnexion").equals("null"))) {
                            String dateDerniereConnexion = infoMedecin.getString("dateDerniereConnexion").toString();
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date dateUnformated = inputFormat.parse(dateDerniereConnexion);
                            String dateFormate = outputFormat.format(dateUnformated);
                            tvDateDerniereCo.setText(dateFormate);
                        }
                        if (!(infoMedecin.getString("dateCreation").equals(null) || infoMedecin.getString("dateCreation").equals("null"))) {
                            String dateCreation = infoMedecin.getString("dateCreation").toString();
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date dateUnformated = inputFormat.parse(dateCreation);
                            String dateFormate = outputFormat.format(dateUnformated);
                            tvDateCreation.setText(dateFormate);
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

        btnPrendreRDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent prendreRDV = new Intent(DetailMedecin.this, PrendreRDV.class);
                prendreRDV.putExtra("email", extras.getString("email"));
                startActivity(prendreRDV);
                finish();
            }
        });

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