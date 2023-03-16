package com.example.projetandroidallodocteurnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ProfilClient extends AppCompatActivity {

    private SessionManager sessionManager;
    private ImageButton buttonSettings;
    private Button buttonDisconnect;

    // Definition variable text;
    private TextView nomModif;
    private TextView prenomModif;
    private TextView dateAnnivModif;
    private TextView adresseModif;
    private TextView mailModif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_client);

        sessionManager = new SessionManager(this);


        //ID de tous les bouttons
        buttonDisconnect = findViewById(R.id.btnDisconnect);
        buttonSettings = findViewById(R.id.reglage);


        // ID de tous les TextView
        nomModif = findViewById(R.id.NomDef);
        prenomModif = findViewById(R.id.PrenomDef);
        dateAnnivModif = findViewById(R.id.DateNaissanceDef);
        adresseModif = findViewById(R.id.AdresseDef);
        mailModif = findViewById(R.id.MailDef);

        // Modifier le textView
        nomModif.setText(sessionManager.getNom());
        prenomModif.setText(sessionManager.getPrenom());
        dateAnnivModif.setText(sessionManager.getDateNaissance());
        adresseModif.setText(sessionManager.getAdresse());
        mailModif.setText(sessionManager.getEmail());



        if (sessionManager.getAdresse() == ""){
            adresseModif.setText("Non renseignez");
        }

        if(sessionManager.getDateNaissance() == ""){
            dateAnnivModif.setText("Non renseignez");
        }

        if(sessionManager.getEmail() == ""){
            mailModif.setText("Non renseignez");
        }

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reglage = new Intent(getApplicationContext(), ProfilClientModif.class);
                startActivity(reglage);
            }
        });


        buttonDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }

}