package com.example.projetandroidallodocteurnew.accueil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetandroidallodocteurnew.mesRDV.patient.MesRDVPatient;
import com.example.projetandroidallodocteurnew.profil.ProfilClient;
import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.rdv.RechercheMedecin;
import com.example.projetandroidallodocteurnew.manager.SessionManager;

public class AccueilPatient extends AppCompatActivity {

    private SessionManager sessionManager;
    private Button btnPrendreRDV, btnMesRDV, btnProfil, btnDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_patient);

        sessionManager = new SessionManager(this);

        btnPrendreRDV = findViewById(R.id.btnPrendreRDV);
        btnMesRDV = findViewById(R.id.btnMesRDV);
        btnProfil = findViewById(R.id.btnProfil);
        btnDocument = findViewById(R.id.btnDocument);

        btnPrendreRDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent prendreRDV = new Intent(getApplicationContext(), RechercheMedecin.class);
                startActivity(prendreRDV);
                finish();
            }
        });

        btnMesRDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mesRDV = new Intent(getApplicationContext(), MesRDVPatient.class);
                startActivity(mesRDV);
                finish();
            }
        });

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profil = new Intent(getApplicationContext(), ProfilClient.class);
                startActivity(profil);
                finish();
            }
        });

    }
}