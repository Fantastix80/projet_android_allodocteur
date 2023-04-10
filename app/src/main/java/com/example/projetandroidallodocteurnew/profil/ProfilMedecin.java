package com.example.projetandroidallodocteurnew.profil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.manager.SessionManager;
import com.example.projetandroidallodocteurnew.accueil.AccueilMedecin;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfilMedecin extends AppCompatActivity {

    private SessionManager sessionManager;
    private DatabaseManager databaseManager;
    private TextView presentationMed, prenomMed, nomMed, specialiteMed, adresseMed, villeMed, codePostalMed, prixMed, moyenPaiement, tvPasHoraire, tvHoraire, tvLundi, tvMardi, tvMercredi, tvJeudi, tvVendredi, tvSamedi, tvDimanche;
    private ImageButton btnSettings;
    private LinearLayout lundi, mardi, mercredi, jeudi , vendredi, samedi, dimanche, layoutDuree, layoutJourTravail, layoutHaveHoraires;
    private AppCompatButton retour, btnAddHoraires, btnUpdateHoraires, btnDeleteHoraires;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_medecin);

        sessionManager = new SessionManager(this);
        databaseManager = new DatabaseManager(this);

        //Récuperer ID pour TextView
        presentationMed = findViewById(R.id.tvPresentation);
        specialiteMed = findViewById(R.id.tvSpecialite);
        adresseMed = findViewById(R.id.tvAdresse);
        villeMed = findViewById(R.id.tvVille);
        codePostalMed = findViewById(R.id.tvCodePostal);
        prixMed = findViewById(R.id.tvPrix);
        moyenPaiement = findViewById(R.id.tvMoyensDePaiement);
        tvPasHoraire = findViewById(R.id.tvPasHoraire);
        tvHoraire = findViewById(R.id.tvHoraire);
        tvLundi = findViewById(R.id.tvLundi);
        tvMardi = findViewById(R.id.tvMardi);
        tvMercredi = findViewById(R.id.tvMercredi);
        tvJeudi = findViewById(R.id.tvJeudi);
        tvVendredi = findViewById(R.id.tvVendredi);
        tvSamedi = findViewById(R.id.tvSamedi);
        tvDimanche = findViewById(R.id.tvDimanche);


        //Récuperer ID pour Button
        btnSettings = findViewById(R.id.btnReglage);
        retour = findViewById(R.id.btnFlecheRetour);
        btnAddHoraires = findViewById(R.id.btnAddHoraires);
        btnUpdateHoraires = findViewById(R.id.btnUpdateHoraires);
        btnDeleteHoraires = findViewById(R.id.btnDeleteHoraires);

        lundi = findViewById(R.id.layoutLundi);
        mardi = findViewById(R.id.layoutMardi);
        mercredi = findViewById(R.id.layoutMercredi);
        jeudi = findViewById(R.id.layoutJeudi);
        vendredi = findViewById(R.id.layoutVendredi);
        samedi = findViewById(R.id.layoutSemedi);
        dimanche = findViewById(R.id.layoutDimanche);
        layoutDuree = findViewById(R.id.layoutDuree);
        layoutJourTravail = findViewById(R.id.layoutJourTravail);
        layoutHaveHoraires = findViewById(R.id.layoutHaveHoraires);

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
                        if (infoMedecin.getString("presentation").equals("") || infoMedecin.getString("presentation").equals(null)) {
                            presentationMed.setText("Non renseignez");
                        }else {
                            presentationMed.setText(infoMedecin.getString("presentation"));
                        }
                        if(infoMedecin.getString("specialite").equals("") || infoMedecin.getString("specialite").equals(null)) {
                            specialiteMed.setText("Renseignez votre specialité");
                        }else {
                            specialiteMed.setText(infoMedecin.getString("specialite"));
                        }
                        if(infoMedecin.getString("tarifs").equals("") || infoMedecin.getString("tarifs").equals(null)) {
                            prixMed.setText("Renseignez un prix/ consultation");
                        }else {
                            prixMed.setText(infoMedecin.getString("tarifs"));
                        }
                        if(infoMedecin.getString("moyensPaiements").equals("") || infoMedecin.getString("moyensPaiements").equals(null)) {
                            moyenPaiement.setText("Choisissez le moyen pour les clients");
                        }else {
                            moyenPaiement.setText(infoMedecin.getString("moyensPaiements"));
                        }
                        if(infoMedecin.getString("adressePro").equals("") || infoMedecin.getString("adressePro").equals(null)) {
                            adresseMed.setText("Renseignez l'adresse du cabinet");
                        }else {
                            adresseMed.setText(infoMedecin.getString("adressePro"));
                        }
                        if(infoMedecin.getString("codePostalPro").equals("") || infoMedecin.getString("codePostalPro").equals(null)) {
                            codePostalMed.setText("Renseignez le code postal");
                        }else {
                            codePostalMed.setText(infoMedecin.getString("codePostalPro"));
                        }
                        if(infoMedecin.getString("villePro").equals("") || infoMedecin.getString("villePro").equals(null)) {
                            villeMed.setText("Indiquez la ville");
                        }else {
                            villeMed.setText(infoMedecin.getString("villePro"));
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

        DatabaseManager.VolleyResponseListener listenerHoraireTravail = new DatabaseManager.VolleyResponseListener() {
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
                        JSONArray horaires = response.getJSONArray("horaireTravail");

                        if (horaires.length() != 0) {
                            btnAddHoraires.setVisibility(View.GONE);
                            tvPasHoraire.setVisibility(View.GONE);
                            layoutDuree.setVisibility(View.VISIBLE);
                            layoutJourTravail.setVisibility(View.VISIBLE);
                            layoutHaveHoraires.setVisibility(View.VISIBLE);

                            tvHoraire.setText(horaires.getJSONObject(0).getString("duree"));

                            for (int i = 0; i < horaires.length(); i++) {
                                JSONObject horaire = horaires.getJSONObject(i);

                                if (horaire.getString("jour").equals("lundi")){
                                    lundi.setVisibility(View.VISIBLE);
                                    tvLundi.setText(horaire.getString("horaire"));
                                }
                                if(horaire.getString("jour").equals("mardi")){
                                    mardi.setVisibility(View.VISIBLE);
                                    tvMardi.setText(horaire.getString("horaire"));
                                }
                                if(horaire.getString("jour").equals("mercredi")){
                                    mercredi.setVisibility(View.VISIBLE);
                                    tvMercredi.setText(horaire.getString("horaire"));
                                }
                                if(horaire.getString("jour").equals("jeudi")){
                                    jeudi.setVisibility(View.VISIBLE);
                                    tvJeudi.setText(horaire.getString("horaire"));
                                }
                                if(horaire.getString("jour").equals("vendredi")){
                                    vendredi.setVisibility(View.VISIBLE);
                                    tvVendredi.setText(horaire.getString("horaire"));
                                }
                                if(horaire.getString("jour").equals("samedi")){
                                    samedi.setVisibility(View.VISIBLE);
                                    tvSamedi.setText(horaire.getString("horaire"));
                                }
                                if(horaire.getString("jour").equals("dimanche")){
                                    dimanche.setVisibility(View.VISIBLE);
                                    tvDimanche.setText(horaire.getString("horaire"));
                                }
                            }
                        } else {
                            btnAddHoraires.setVisibility(View.VISIBLE);
                            tvPasHoraire.setVisibility(View.VISIBLE);
                            layoutDuree.setVisibility(View.GONE);
                            layoutJourTravail.setVisibility(View.GONE);
                            layoutHaveHoraires.setVisibility(View.GONE);
                        }

                    } else {
                        System.out.println(response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        DatabaseManager.VolleyResponseListener listenerDeleteHTAll = new DatabaseManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) {
                Intent goProfilMedecin = new Intent(ProfilMedecin.this, ProfilMedecin.class);
                startActivity(goProfilMedecin);
            }
        };

        //On fait appel à l'API pour récupérer les horaires de travail du médecin s'il en a
        databaseManager.getHoraireTravail(sessionManager.getEmail(), listenerHoraireTravail);

        //Appel API pour recupérer les infos du medecin.
        databaseManager.getOneMedecin(email, listener);


        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reglage = new Intent(ProfilMedecin.this,ProfilMedecinModif.class);
                startActivity(reglage);
                finish();
            }
        });

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(ProfilMedecin.this, AccueilMedecin.class);
                startActivity(goBack);
                finish();
            }
        });

        btnAddHoraires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goManagehoraire = new Intent(ProfilMedecin.this, ManageHorairesMedecin.class);
                startActivity(goManagehoraire);
                finish();
            }
        });

        btnUpdateHoraires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goUpdatehoraire = new Intent(ProfilMedecin.this, ManageHorairesMedecin.class);
                goUpdatehoraire.putExtra("modif", true);
                startActivity(goUpdatehoraire);
                finish();
            }
        });

        btnDeleteHoraires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseManager.deleteHoraireTravailAll(sessionManager.getEmail(), listenerDeleteHTAll);
            }
        });
    }
}