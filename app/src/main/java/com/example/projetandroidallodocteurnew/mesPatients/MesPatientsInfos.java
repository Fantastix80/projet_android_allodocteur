package com.example.projetandroidallodocteurnew.mesPatients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.manager.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MesPatientsInfos extends AppCompatActivity {
    private DatabaseManager databaseManager;
    private SessionManager sessionManager;
    private AppCompatButton btnAcceptRelation, btnDenyRelation, btnDeletePatient;
    private Button btnFlecheRetour;
    private TextView tvSexe, tvPrenom, tvNom, tvDateNaissance, tvAdresse, tvCodePostal, tvVille, tvTelephone, tvEmail, tvDateDerniereCo, tvDateCreation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_patients_infos);

        databaseManager = new DatabaseManager(this);
        sessionManager = new SessionManager(this);

        btnAcceptRelation = findViewById(R.id.btnAcceptRelation);
        btnDenyRelation = findViewById(R.id.btnDenyRelation);
        btnDeletePatient = findViewById(R.id.btnDeletePatient);
        btnFlecheRetour = findViewById(R.id.btnFlecheRetour);

        tvSexe = findViewById(R.id.tvSexe);
        tvPrenom = findViewById(R.id.tvPrenom);
        tvNom = findViewById(R.id.tvNom);
        tvDateNaissance = findViewById(R.id.tvDateNaissance);
        tvAdresse = findViewById(R.id.tvAdresse);
        tvCodePostal = findViewById(R.id.tvCodePostal);
        tvVille = findViewById(R.id.tvVille);
        tvTelephone = findViewById(R.id.tvTelephone);
        tvEmail = findViewById(R.id.tvEmail);
        tvDateDerniereCo = findViewById(R.id.tvDateDerniereCo);
        tvDateCreation = findViewById(R.id.tvDateCreation);

        //Création d'un listener pour venir récupérer la réponse de l'API
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

                        JSONArray patient = response.getJSONArray("infoUser");
                        JSONObject infoPatient = patient.getJSONObject(0);

                        //Renseigner toutes les données nécessaires dans les TextView
                        if (!(infoPatient.getString("sexe").equals(null) || infoPatient.getString("sexe").equals("null"))) {
                            if (infoPatient.getString("sexe").toUpperCase().equals("M")) {
                                tvSexe.setText("Masculin");
                            } else if (infoPatient.getString("sexe").toUpperCase().equals("F")) {
                                tvSexe.setText("Féminin");
                            }
                        }
                        if (!(infoPatient.getString("prenom").equals(null) || infoPatient.getString("prenom").equals("null"))) {
                            tvPrenom.setText(infoPatient.getString("prenom").toString());
                        }
                        if (!(infoPatient.getString("nom").equals(null) || infoPatient.getString("nom").equals("null"))) {
                            tvNom.setText(infoPatient.getString("nom").toString());
                        }
                        if (!(infoPatient.getString("dateNaissance").equals(null) || infoPatient.getString("dateNaissance").equals("null"))) {
                            String dateNaissance = infoPatient.getString("dateNaissance").toString();
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date dateUnformated = inputFormat.parse(dateNaissance);
                            String dateFormate = outputFormat.format(dateUnformated);
                            tvDateNaissance.setText(dateFormate);
                        }
                        if (!(infoPatient.getString("adresse").equals(null) || infoPatient.getString("adresse").equals("null"))) {
                            tvAdresse.setText(infoPatient.getString("adresse").toString());
                        }
                        if (!(infoPatient.getString("codePostal").equals(null) || infoPatient.getString("codePostal").equals("null"))) {
                            tvCodePostal.setText(infoPatient.getString("codePostal").toString());
                        }
                        if (!(infoPatient.getString("ville").equals(null) || infoPatient.getString("ville").equals("null"))) {
                            tvVille.setText(infoPatient.getString("ville").toString());
                        }
                        if (!(infoPatient.getString("telephone").equals(null) || infoPatient.getString("telephone").equals("null"))) {
                            tvTelephone.setText(infoPatient.getString("telephone").toString());
                        }
                        if (!(infoPatient.getString("email").equals(null) || infoPatient.getString("email").equals("null"))) {
                            tvEmail.setText(infoPatient.getString("email").toString());
                        }
                        if (!(infoPatient.getString("dateDerniereConnexion").equals(null) || infoPatient.getString("dateDerniereConnexion").equals("null"))) {
                            String dateDerniereConnexion = infoPatient.getString("dateDerniereConnexion").toString();
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date dateUnformated = inputFormat.parse(dateDerniereConnexion);
                            String dateFormate = outputFormat.format(dateUnformated);
                            tvDateDerniereCo.setText(dateFormate);
                        }
                        if (!(infoPatient.getString("dateCreation").equals(null) || infoPatient.getString("dateCreation").equals("null"))) {
                            String dateCreation = infoPatient.getString("dateCreation").toString();
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date dateUnformated = inputFormat.parse(dateCreation);
                            String dateFormate = outputFormat.format(dateUnformated);
                            tvDateCreation.setText(dateFormate);
                        }

                    } else {
                        System.out.println(response.getString("error"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        //Récupérer les extras
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            //Appel à l'api
            String email = extras.getString("email");
            databaseManager.getOneUser(email, listener);

            //Afficher les boutons si relation pas accepté
            String isAccepted = extras.getString("isAccepted");
            if (isAccepted.equals("oui")) {
                btnAcceptRelation.setVisibility(View.GONE);
                btnDenyRelation.setVisibility(View.GONE);
            } else {
                btnAcceptRelation.setVisibility(View.VISIBLE);
                btnDenyRelation.setVisibility(View.VISIBLE);
            }

        } else {
            System.out.println("Extras vide dans l'activité MesPatientsInfos, retour à l'activité précédente");
            Intent erreurExtras = new Intent(MesPatientsInfos.this, MesPatients.class);
            startActivity(erreurExtras);
            finish();
        }

        DatabaseManager.VolleyResponseListener listenerVoid = new DatabaseManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        };

        btnAcceptRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseManager.actionsMedecinTraitant("updateRelation", extras.getString("email"), sessionManager.getEmail(), listenerVoid);
                Intent goToPatient1 = new Intent(MesPatientsInfos.this, MesPatients.class);
                startActivity(goToPatient1);
                finish();
            }
        });

        btnDenyRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseManager.actionsMedecinTraitant("delete", extras.getString("email"), "", listenerVoid);
                Intent goToPatient2 = new Intent(MesPatientsInfos.this, MesPatients.class);
                startActivity(goToPatient2);
                finish();
            }
        });

        btnDeletePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseManager.actionsMedecinTraitant("delete", extras.getString("email"), "", listenerVoid);
                Intent goToPatient3 = new Intent(MesPatientsInfos.this, MesPatients.class);
                startActivity(goToPatient3);
                finish();
            }
        });

        btnFlecheRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(MesPatientsInfos.this, MesPatients.class);
                startActivity(goBack);
                finish();
            }
        });
    }
}