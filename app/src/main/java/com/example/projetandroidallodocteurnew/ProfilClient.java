package com.example.projetandroidallodocteurnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ProfilClient extends AppCompatActivity {

    private SessionManager sessionManager;
    private ImageButton btnReglage;
    private Button btnAddMedecinTraitant, btnUpdateMedecinTraitant, btnDeleteMedecinTraitant, btnDisconnect;
    private TextView tvPasDeMedecinTraitant, tvNomEtPrenomMedecinTraitant, tvPrenom, tvNom, tvDateNaissance, tvAdresse, tvCodePostal, tvVille, tvTelephone, tvEmail;
    private LinearLayout layoutHaveMedecinTraitant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_client);

        sessionManager = new SessionManager(this);

        btnReglage = findViewById(R.id.btnReglage);
        btnAddMedecinTraitant = findViewById(R.id.btnAddMedecinTraitant);
        btnUpdateMedecinTraitant = findViewById(R.id.btnUpdateMedecinTraitant);
        btnDeleteMedecinTraitant = findViewById(R.id.btnDeleteMedecinTraitant);
        btnDisconnect = findViewById(R.id.btnDisconnect);

        tvPasDeMedecinTraitant = findViewById(R.id.tvPasDeMedecinTraitant);
        tvNomEtPrenomMedecinTraitant = findViewById(R.id.tvNomEtPrenomMedecinTraitant);
        tvPrenom = findViewById(R.id.tvPrenom);
        tvNom = findViewById(R.id.tvNom);
        tvDateNaissance = findViewById(R.id.tvDateNaissance);
        tvAdresse = findViewById(R.id.tvAdresse);
        tvCodePostal = findViewById(R.id.tvCodePostal);
        tvVille = findViewById(R.id.tvVille);
        tvTelephone = findViewById(R.id.tvTelephone);
        tvEmail = findViewById(R.id.tvEmail);

        //On vient actualiser les textview
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

        btnReglage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reglage = new Intent(getApplicationContext(), ProfilClientModif.class);
                startActivity(reglage);
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




    }

}