package com.example.projetandroidallodocteurnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // On initialise les variables
    private Button btnInscription, btnConnexion;
    private SessionManager sessionManager;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On définie les variables avec les View
        btnInscription = findViewById(R.id.btnInscription);
        btnConnexion = findViewById(R.id.btnConnexion);
        databaseManager = new DatabaseManager(getApplicationContext());

        //On vient vérifier si l'utilisateur possède une session afin d'éviter qu'il ne se reconnecte
        sessionManager = new SessionManager(this);
        if (sessionManager.isLogged()) {
            //On génère la date et l'heure actuelle (date et heure du système)
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            //On vient appeler cette méthode qui va ensuite faire un appel à l'API afin de modifier la date de dernière connexion
            databaseManager.updateLastConnectionDate(sessionManager.getEmail(), formatter.format(date));

            //On vient actualiser la session on y mettant la nouvelle date de dernière connexion
            sessionManager.modifyUser("dateDerniereConnexion", formatter.format(date));

            //Cette condition permet de renvoyer l'utilisateur sur l'accueil médecin ou patient selon se qu'il est
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