package com.example.formulairebeau;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // On initialise les variables
    private Button btnInscription, btnConnexion;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On définie les variables avec les View
        btnInscription = findViewById(R.id.btnInscription);
        btnConnexion = findViewById(R.id.btnConnexion);

        //On vient vérifier si l'utilisateur possède une session afin d'éviter qu'il ne se reconnecte
        sessionManager = new SessionManager(this);
        if (sessionManager.isLogged()) {
            if (Objects.equals(sessionManager.getIsMedecin(), "1")) {
                Intent accueilMedecin = new Intent(this, AccueilMedecin.class);
                startActivity(accueilMedecin);
                finish();
            } else {
                Intent accueilPatient = new Intent(this, AccueilPatient.class);
                startActivity(accueilPatient);
                finish();
            }
        }

        // OnClickListener pour les boutons "S'inscrire" et "Se connecter"
        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(getApplicationContext(), FormulaireInscription.class);
                startActivity(myIntent);
            }
        });

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent2 = new Intent(getApplicationContext(), FormulaireConnexion.class);
                startActivity(myIntent2);

            }
        });

    }

}