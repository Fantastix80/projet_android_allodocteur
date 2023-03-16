package com.example.projetandroidallodocteurnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilClientModif extends AppCompatActivity {


    private SessionManager sessionManager;
    private DatabaseManager databaseManager;

    //Definition variable RadioGroup
    private RadioGroup sexeGroup;


    //Definition variable Button
    private ImageButton cancel;
    private Button modif;
    private RadioButton homme;
    private RadioButton femme;


    // Définition variable texte
    private EditText nvxPrenom, nvxNom, nvxDateNaissance, nvxAdresse, nvxVille, nvxCodePostal, nvxTelephone;
    private String SEXE, email, isMedecin, dateCreation, dateDerniereCo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_client_modif);

        sessionManager = new SessionManager(this);
        databaseManager = new DatabaseManager(this);

        //sessionManager variable
        SEXE = sessionManager.getSexe();
        email = sessionManager.getEmail();
        isMedecin = sessionManager.getIsMedecin();
        dateCreation = sessionManager.getDateCreation();
        dateDerniereCo = sessionManager.getDateDerniereConnexion();

        //ID RadioGroup
        sexeGroup = findViewById(R.id.SexeRadioGroup);

        //ID de tous les EditText
        nvxPrenom = findViewById(R.id.nouveauPrenom);
        nvxNom = findViewById(R.id.nouveauNom);
        nvxDateNaissance = findViewById(R.id.nouveauAnniversaire);
        nvxAdresse = findViewById(R.id.nouveauAdresse);
        nvxVille = findViewById(R.id.nouveauVille);
        nvxCodePostal = findViewById(R.id.nouveauCodePostal);
        nvxTelephone = findViewById(R.id.nouveauTelephone);

        //ID de tous les buttons
        cancel = findViewById(R.id.closeButton);
        modif = findViewById(R.id.Enregistrement);
        homme = findViewById(R.id.Masculin);
        femme = findViewById(R.id.Feminin);

        //Indiquez ceux renseignez et ceux qu'il ne le sont pas
        if(sessionManager.getPrenom().equals("") || sessionManager.getPrenom().equals(null) || sessionManager.getPrenom().equals("null")){
            nvxPrenom.setHint("Entrez votre prénom...");
        } else {
            nvxPrenom.setText(sessionManager.getPrenom());
        }

        if(sessionManager.getNom().equals("") || sessionManager.getNom().equals(null) || sessionManager.getNom().equals("null")){
            nvxNom.setHint("Entrez votre nom...");
        } else {
            nvxNom.setText(sessionManager.getNom());
        }

        if(sessionManager.getDateNaissance().equals("") || sessionManager.getDateNaissance().equals(null) || sessionManager.getDateNaissance().equals("null")){
            nvxDateNaissance.setHint("Entrez votre date de naissance...");
        } else {
            nvxDateNaissance.setText(sessionManager.getDateNaissance());
        }

        if(sessionManager.getAdresse().equals("") || sessionManager.getAdresse().equals(null) || sessionManager.getAdresse().equals("null")){
            nvxAdresse.setHint("Entrez votre adresse...");
        } else {
            nvxAdresse.setText(sessionManager.getAdresse());
        }

        if(sessionManager.getVille().equals("") || sessionManager.getVille().equals(null) || sessionManager.getVille().equals("null")){
            nvxVille.setHint("Entrez votre ville...");
        } else {
            nvxVille.setText(sessionManager.getVille());
        }

        if(sessionManager.getCodePostal().equals("") || sessionManager.getCodePostal().equals(null) || sessionManager.getCodePostal().equals("null")) {
            nvxCodePostal.setHint("Entrez votre code postal...");
        } else {
            nvxCodePostal.setText(sessionManager.getCodePostal());
        }

        if(sessionManager.getTelephone().equals("") || sessionManager.getTelephone().equals(null) || sessionManager.getTelephone().equals("null")) {
            nvxTelephone.setHint("Entrez votre téléphone...");
        } else {
            nvxTelephone.setText(sessionManager.getTelephone());
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retour = new Intent(ProfilClientModif.this, ProfilClient.class);
                startActivity(retour);
            }
        });

        if (SEXE.equals("M")){
            homme.setChecked(true);
        } else if (SEXE.equals("F")) {
            femme.setChecked(true);
        }else {
            homme.setChecked(false);
            femme.setChecked(false);
        }

        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Conversion de toute les variables en String
                String NOM = nvxNom.getText().toString();
                String PRENOM = nvxPrenom.getText().toString();
                String DATE_NAISSANCE  = nvxDateNaissance.getText().toString();
                String ADRESSE = nvxAdresse.getText().toString();
                String VILLE = nvxVille.getText().toString();
                String CODE_POSTAL = nvxCodePostal.getText().toString();
                String TELEPHONE = nvxTelephone.getText().toString();

                if(homme.isChecked() == true){
                    SEXE = "M";
                } else if (femme.isChecked() == true) {
                    SEXE = "F";
                } else {
                    SEXE = "";
                }

                //Actualiser la BDD
                databaseManager.updateUtilisateur(email,SEXE,NOM,PRENOM,DATE_NAISSANCE,ADRESSE,CODE_POSTAL,VILLE,TELEPHONE,"");
                
                //Actualiser la session
                sessionManager.logout();
                sessionManager.insertUser(email,SEXE,NOM,PRENOM,DATE_NAISSANCE,isMedecin, TELEPHONE,ADRESSE,CODE_POSTAL,VILLE, "", dateCreation,dateDerniereCo);

                Intent enregistrer = new Intent(ProfilClientModif.this,ProfilClient.class);
                startActivity(enregistrer);
                Toast.makeText(ProfilClientModif.this, "Enregistrement pris en compte", Toast.LENGTH_SHORT).show();
            }
        });
    }
}