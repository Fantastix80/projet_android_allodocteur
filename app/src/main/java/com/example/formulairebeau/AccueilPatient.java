package com.example.formulairebeau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccueilPatient extends AppCompatActivity {

    private SessionManager sessionManager;
    private Button btnLogout;
    private Button btnRechercheMedecin;
    private Button btnProfilClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_patient);

        sessionManager = new SessionManager(this);

        btnLogout = findViewById(R.id.btnLogout);
        btnRechercheMedecin = findViewById(R.id.btnRechercheMedecin);
        btnProfilClient = findViewById(R.id.btnProfilClient);

        System.out.println("Email: " + sessionManager.getEmail());
        System.out.println("Sexe: " + sessionManager.getSexe());
        System.out.println("Nom: " + sessionManager.getNom());
        System.out.println("Prenom: " + sessionManager.getPrenom());
        System.out.println("DateNaissance: " + sessionManager.getDateNaissance());
        System.out.println("IsMedecin: " + sessionManager.getIsMedecin());
        System.out.println("Adresse: " + sessionManager.getAdresse());
        System.out.println("CodePostal: " + sessionManager.getCodePostal());
        System.out.println("Ville: " + sessionManager.getVille());
        System.out.println("DateCreation: " + sessionManager.getDateCreation());
        System.out.println("DateDerniereConnexion: " + sessionManager.getDateDerniereConnexion());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRechercheMedecin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RechercheMedecin.class);
                startActivity(intent);
                finish();
            }
        });

        btnProfilClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}