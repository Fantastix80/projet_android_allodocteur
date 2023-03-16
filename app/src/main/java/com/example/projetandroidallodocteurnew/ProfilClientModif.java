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

    //Definition variable RadioGroup
    private RadioGroup sexeGroup;


    //Definition variable Button
    private ImageButton cancel;
    private Button modif;
    private RadioButton homme;
    private RadioButton femme;


    // Définition variable texte
    private EditText nvxPrenom;
    private EditText nvxNom;
    private EditText nvxDateNaissance;
    private EditText nvxAdresse;
    private EditText nvxVille;
    private EditText nvxCodePostal;
    private String SEXE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_client_modif);

        sessionManager = new SessionManager(this);

        //sessionManager variable
        String email = sessionManager.getEmail();
        String isMedecin = sessionManager.getIsMedecin();
        String dateCreation = sessionManager.getDateCreation();
        String dateDerniereCo = sessionManager.getDateDerniereConnexion();

        //ID RadioGroup
        sexeGroup = findViewById(R.id.SexeRadioGroup);


        //ID de tous les EditText
        nvxPrenom = findViewById(R.id.nouveauPrenom);
        nvxNom = findViewById(R.id.nouveauNom);
        nvxDateNaissance = findViewById(R.id.nouveauAnniversaire);
        nvxAdresse = findViewById(R.id.nouveauAdresse);
        nvxVille = findViewById(R.id.nouveauVille);
        nvxCodePostal = findViewById(R.id.nouveauCodePostal);


        //ID de tous les buttons
        cancel = findViewById(R.id.closeButton);
        modif = findViewById(R.id.Enregistrement);
        homme = findViewById(R.id.Masculin);
        femme = findViewById(R.id.Feminin);


        //Modif EditText
        nvxPrenom.setText(sessionManager.getPrenom());
        nvxNom.setText(sessionManager.getNom());
        nvxDateNaissance.setText(sessionManager.getDateNaissance());
        nvxAdresse.setText(sessionManager.getAdresse());
        nvxVille.setText(sessionManager.getVille());
        nvxCodePostal.setText(sessionManager.getCodePostal());


        //Indiquez ceux renseignez et ceux qu'il ne le sont pas
        if(sessionManager.getPrenom() == ""){
            nvxPrenom.setHint("Non renseignez");
        }
        if(sessionManager.getNom()== ""){
            nvxNom.setHint("Non renseignez");
        }
        if(sessionManager.getDateNaissance()==""){
            nvxDateNaissance.setHint("Non renseignez");
        }
        if(sessionManager.getAdresse()==""){
            nvxAdresse.setHint("Non renseignez");
        }
        if(sessionManager.getVille()==""){
            nvxVille.setHint("non renseignez");
        }
        if(sessionManager.getCodePostal()=="") {
            nvxCodePostal.setHint("Non renseignez");
        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retour = new Intent(ProfilClientModif.this, ProfilClient.class);
                startActivity(retour);
            }
        });

        if (SEXE == "M"){
            //homme.setChecked(true);
            sexeGroup.check(R.id.Masculin);
        } else if (SEXE == "F") {
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
                if(homme.isChecked()== true){
                    SEXE = "M";
                } else if (femme.isChecked()== true) {
                    SEXE = "F";
                }
                else {
                    SEXE = "";
                }
                System.out.println(SEXE);

                //Actualiser BDD

                sessionManager.logout();
                sessionManager.insertUser(email,SEXE,NOM,PRENOM,DATE_NAISSANCE,isMedecin, "",ADRESSE,CODE_POSTAL,VILLE, "", dateCreation,dateDerniereCo);

                Intent enregistrer = new Intent(ProfilClientModif.this,ProfilClient.class);
                startActivity(enregistrer);
                Toast.makeText(ProfilClientModif.this, "Enregistrement pris en compte", Toast.LENGTH_SHORT).show();
            }
        });
    }
}