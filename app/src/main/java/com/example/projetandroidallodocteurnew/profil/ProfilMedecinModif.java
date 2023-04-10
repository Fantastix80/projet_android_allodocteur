package com.example.projetandroidallodocteurnew.profil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.manager.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfilMedecinModif extends AppCompatActivity {

    private String email;
    private DatabaseManager databaseManager;
    private SessionManager sessionManager;
    private JSONObject infoMedecin;
    private TextInputEditText presentation, specialite, prix, moyenPaiement, adresse, ville, codePostal;
    private Button save;
    private ImageButton cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_medecin_modif);

        sessionManager = new SessionManager(this);
        databaseManager = new DatabaseManager(this);

        //Récuperer ID tous TextInputEditText
        presentation = findViewById(R.id.editPresentation);
        specialite = findViewById(R.id.editSpe);
        prix = findViewById(R.id.editPrix);
        moyenPaiement = findViewById(R.id.editMoyenPaiement);
        adresse = findViewById(R.id.editAdresse);
        ville = findViewById(R.id.editVille);
        codePostal = findViewById(R.id.editCodePostal);

        //Récuperer ID tous Button
        save = findViewById(R.id.btnEnregistrer);
        cancel = findViewById(R.id.btnCancel);

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
                        infoMedecin = medecin.getJSONObject(0);
                        System.out.println(response);

                        //Renseigner toutes les données nécessaires dans les TextView
                        //tvPrenomEtNom.setText(infoMedecin.getString("prenom") + " " + infoMedecin.getString("nom").toUpperCase());
                        if (infoMedecin.getString("presentation").equals("") || infoMedecin.getString("presentation").equals(null)) {
                            presentation.setText("Ajoutez une presentation");
                        } else {
                            presentation.setText(infoMedecin.getString("presentation"));
                        }
                        if (infoMedecin.getString("tarifs").equals("") || infoMedecin.getString("tarifs").equals(null)) {
                            prix.setText("Ajouter le prix de la consultation");
                        } else {
                            prix.setText(infoMedecin.getString("tarifs"));
                        }
                        if (infoMedecin.getString("specialite").equals("") || infoMedecin.getString("specialite").equals(null)) {
                            specialite.setText("Indiquer la specialite de votre cabinet");
                        } else {
                            specialite.setText(infoMedecin.getString("specialite"));
                        }
                        if (infoMedecin.getString("moyensPaiements").equals("") || infoMedecin.getString("moyensPaiements").equals(null)) {
                            moyenPaiement.setText("Ajoutez un moyen de paiement");
                        } else {
                            moyenPaiement.setText(infoMedecin.getString("moyensPaiements"));
                        }
                        if (infoMedecin.getString("adressePro").equals("") || infoMedecin.getString("adressePro").equals(null)) {
                            adresse.setText("Indiquez l'adresse de vôtre cabinet");
                        } else {
                            adresse.setText(infoMedecin.getString("adressePro"));
                        }
                        if (infoMedecin.getString("villePro").equals("") || infoMedecin.getString("villePro").equals(null)) {
                            ville.setText("Indiquer la ville");
                        } else {
                            ville.setText(infoMedecin.getString("villePro"));
                        }
                        if (infoMedecin.getString("codePostalPro").equals("") || infoMedecin.getString("codePostalPro").equals(null)) {
                            codePostal.setText("Indiquez un code postal");
                        } else {
                            codePostal.setText(infoMedecin.getString("codePostalPro"));
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


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retour = new Intent(ProfilMedecinModif.this, ProfilMedecin.class);
                startActivity(retour);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Definition variable
                String PRESENTATION = presentation.getText().toString();
                String SPECIALITE = specialite.getText().toString();
                String PRIX = prix.getText().toString();
                String MOYEN_PAIEMENT = moyenPaiement.getText().toString();
                String ADRESSE = adresse.getText().toString();
                String VILLE = ville.getText().toString();
                String CODE_POSTAL = codePostal.getText().toString();

                //Actualiser BDD
                databaseManager.UpdateMedecinInfo(email, SPECIALITE, ADRESSE, CODE_POSTAL, VILLE, PRESENTATION, PRIX, MOYEN_PAIEMENT);

                Intent modifier = new Intent(ProfilMedecinModif.this, ProfilMedecin.class);
                startActivity(modifier);
            }
        });
    }
}