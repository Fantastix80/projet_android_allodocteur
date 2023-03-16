package com.example.projetandroidallodocteurnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AccueilMedecin extends AppCompatActivity {

    private SessionManager sessionManager;
    private Button btnMesPatients, btnMesRDV, btnProfilPerso, btnProfilPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_medecin);

        sessionManager = new SessionManager(this);

        btnMesPatients = findViewById(R.id.btnMesPatients);
        btnMesRDV = findViewById(R.id.btnMesRDV);
        btnProfilPerso = findViewById(R.id.btnProfilPerso);
        btnProfilPro = findViewById(R.id.btnProfilPro);

        btnProfilPerso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profilPerso = new Intent(getApplicationContext(), ProfilClient.class);
                startActivity(profilPerso);
                finish();
            }
        });

        btnProfilPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profil = new Intent(getApplicationContext(), ProfilMedecin.class);
                startActivity(profil);
                finish();
            }
        });

    }
}