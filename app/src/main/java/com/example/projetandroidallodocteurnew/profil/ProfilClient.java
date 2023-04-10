package com.example.projetandroidallodocteurnew.profil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.manager.SessionManager;
import com.example.projetandroidallodocteurnew.accueil.AccueilMedecin;
import com.example.projetandroidallodocteurnew.accueil.AccueilPatient;
import com.example.projetandroidallodocteurnew.accueil.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;


public class ProfilClient extends AppCompatActivity {

    private SessionManager sessionManager;
    private DatabaseManager databaseManager;
    private ImageButton btnReglage;
    private Button btnAddMedecinTraitant, btnUpdateMedecinTraitant, btnDeleteMedecinTraitant, btnDisconnect, btnDeleteAccount, btnFlecheRetour;
    private TextView tvPasDeMedecinTraitant, tvNomEtPrenomMedecinTraitant, tvSexe, tvPrenom, tvNom, tvDateNaissance, tvAdresse, tvCodePostal, tvVille, tvTelephone, tvEmail;
    private LinearLayout layoutHaveMedecinTraitant;
    private CardView cardViewMedecinTraitant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_client);

        sessionManager = new SessionManager(this);
        databaseManager = new DatabaseManager(this);

        btnReglage = findViewById(R.id.btnReglage);
        btnAddMedecinTraitant = findViewById(R.id.btnAddMedecinTraitant);
        btnUpdateMedecinTraitant = findViewById(R.id.btnUpdateMedecinTraitant);
        btnDeleteMedecinTraitant = findViewById(R.id.btnDeleteMedecinTraitant);
        btnDisconnect = findViewById(R.id.btnDisconnect);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        btnFlecheRetour = findViewById(R.id.btnFlecheRetour);

        layoutHaveMedecinTraitant = findViewById(R.id.layoutHaveMedecinTraitant);

        cardViewMedecinTraitant = findViewById(R.id.cardViewMedecinTraitant);
        tvPasDeMedecinTraitant = findViewById(R.id.tvPasDeMedecinTraitant);
        tvNomEtPrenomMedecinTraitant = findViewById(R.id.tvNomEtPrenomMedecinTraitant);
        tvSexe = findViewById(R.id.tvSexe);
        tvPrenom = findViewById(R.id.tvPrenom);
        tvNom = findViewById(R.id.tvNom);
        tvDateNaissance = findViewById(R.id.tvDateNaissance);
        tvAdresse = findViewById(R.id.tvAdresse);
        tvCodePostal = findViewById(R.id.tvCodePostal);
        tvVille = findViewById(R.id.tvVille);
        tvTelephone = findViewById(R.id.tvTelephone);
        tvEmail = findViewById(R.id.tvEmail);

        //On vient enlenver la partie médecin traitant si l'utilisateur est un médecin
        if (sessionManager.getIsMedecin().equals("1")) {
            cardViewMedecinTraitant.setVisibility(View.GONE);
        } else {
            cardViewMedecinTraitant.setVisibility(View.VISIBLE);
        }

        //On vient récupérer la réponse de la bdd pour savoir si l'utilisateur possède un médecin traitant
        DatabaseManager.VolleyResponseListener listenerHaveMedecinTraitant = new DatabaseManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(JSONObject response) {
                String success;
                try {
                    success = response.getString("success");

                    if (success.equals("true")) {
                        JSONArray datas = response.getJSONArray("data");
                        if (datas.length() > 0) {
                            JSONObject data = datas.getJSONObject(0);

                            tvNomEtPrenomMedecinTraitant.setText(data.getString("nom").toUpperCase() + " " + data.getString("prenom"));

                            tvPasDeMedecinTraitant.setVisibility(View.GONE);
                            btnAddMedecinTraitant.setVisibility(View.GONE);
                            tvNomEtPrenomMedecinTraitant.setVisibility(View.VISIBLE);
                            layoutHaveMedecinTraitant.setVisibility(View.VISIBLE);
                        } else {
                            tvPasDeMedecinTraitant.setVisibility(View.VISIBLE);
                            btnAddMedecinTraitant.setVisibility(View.VISIBLE);
                            tvNomEtPrenomMedecinTraitant.setVisibility(View.GONE);
                            layoutHaveMedecinTraitant.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(ProfilClient.this, response.getString("error"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        //On effectue une requête à l'api pour savoir si l'utilisateur a un médecin traitant
        databaseManager.actionsMedecinTraitant("select", sessionManager.getEmail(), "", listenerHaveMedecinTraitant);

        //On vient actualiser les textview
        if (sessionManager.getSexe().equals("") || sessionManager.getSexe().equals(null) || sessionManager.getSexe().equals("null")){
            tvSexe.setText("");
        } else {
            if (sessionManager.getSexe().equals("M")) {
                tvSexe.setText("Masculin");
            } else {
                tvSexe.setText("Féminin");
            }
        }

        if (sessionManager.getPrenom().equals("") || sessionManager.getPrenom().equals(null) || sessionManager.getPrenom().equals("null")){
            tvPrenom.setText("");
        } else {
            tvPrenom.setText(sessionManager.getPrenom());
        }

        if (sessionManager.getNom().equals("") || sessionManager.getNom().equals(null) || sessionManager.getNom().equals("null")){
            tvNom.setText("");
        } else {
            tvNom.setText(sessionManager.getNom());
        }

        if (sessionManager.getDateNaissance().equals("") || sessionManager.getDateNaissance().equals(null) || sessionManager.getDateNaissance().equals("null")){
            tvDateNaissance.setText("");
        } else {
            tvDateNaissance.setText(sessionManager.getDateNaissance());
        }

        if (sessionManager.getAdresse().equals("") || sessionManager.getAdresse().equals(null) || sessionManager.getAdresse().equals("null")){
            tvAdresse.setText("");
        } else {
            tvAdresse.setText(sessionManager.getAdresse());
        }

        if (sessionManager.getCodePostal().equals("") || sessionManager.getCodePostal().equals(null) || sessionManager.getCodePostal().equals("null")){
            tvCodePostal.setText("");
        } else {
            tvCodePostal.setText(sessionManager.getCodePostal());
        }

        if (sessionManager.getVille().equals("") || sessionManager.getVille().equals(null) || sessionManager.getVille().equals("null")){
            tvVille.setText("");
        } else {
            tvVille.setText(sessionManager.getVille());
        }

        if (sessionManager.getTelephone().equals("") || sessionManager.getTelephone().equals(null) || sessionManager.getTelephone().equals("null")){
            tvTelephone.setText("");
        } else {
            tvTelephone.setText(sessionManager.getTelephone());
        }

        if (sessionManager.getEmail().equals("") || sessionManager.getEmail().equals(null) || sessionManager.getEmail().equals("null")){
            tvEmail.setText("");
        } else {
            tvEmail.setText(sessionManager.getEmail());
        }

        DatabaseManager.VolleyResponseListener listenerDeleteMedecinTraitant = new DatabaseManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {}
            @Override
            public void onResponse(JSONObject response) {
                String success;
                try {
                    success = response.getString("success");

                    Intent reloadProfilClient = new Intent(ProfilClient.this, ProfilClient.class);
                    startActivity(reloadProfilClient);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        btnReglage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reglage = new Intent(getApplicationContext(), ProfilClientModif.class);
                startActivity(reglage);
            }
        });

        btnAddMedecinTraitant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rechercheMedecinTraitant = new Intent(ProfilClient.this, RechercheMedecinTraitant.class);
                rechercheMedecinTraitant.putExtra("action", "add");
                startActivity(rechercheMedecinTraitant);
                finish();
            }
        });

        btnUpdateMedecinTraitant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rechercheMedecinTraitant = new Intent(ProfilClient.this, RechercheMedecinTraitant.class);
                rechercheMedecinTraitant.putExtra("action", "update");
                startActivity(rechercheMedecinTraitant);
                finish();
            }
        });

        btnDeleteMedecinTraitant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseManager.actionsMedecinTraitant("delete", sessionManager.getEmail(), "", listenerDeleteMedecinTraitant);
            }
        });

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Créer une pop up de vérification pour s'assurer que l'utilisateur veuilles bien supprimer
                //son compte.
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilClient.this);
                builder.setTitle("SUPPRESSION DE VOTRE COMPTE");
                builder.setMessage("Voulez-vous vraiment supprimer votre compte ?");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                        databaseManager.deleteAccount(sessionManager.getEmail());

                        sessionManager.logout();

                        Intent mainActivity = new Intent(ProfilClient.this, MainActivity.class);
                        startActivity(mainActivity);
                        finish();
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        btnFlecheRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.getIsMedecin().equals("1")) {
                    Intent goAccueilMedecin = new Intent(getApplicationContext(), AccueilMedecin.class);
                    startActivity(goAccueilMedecin);
                    finish();
                } else {
                    Intent goAccueilPatient = new Intent(getApplicationContext(), AccueilPatient.class);
                    startActivity(goAccueilPatient);
                    finish();
                }
            }
        });

    }
}