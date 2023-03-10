package com.example.formulairebeau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

        //on définie notre listener qui va s'activer lorsque l'api aura répondu à notre requête
        DatabaseManager.VolleyResponseListener listenerMainActivity = new DatabaseManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }
            @Override
            public void onResponse(JSONObject response) {

            }
        };

        //On vient vérifier si l'utilisateur possède une session afin d'éviter qu'il ne se reconnecte
        sessionManager = new SessionManager(this);
        if (sessionManager.isLogged()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            //Faire un appel à l'API pour mettre à jour la date de dernière connexion
            Map<String, String> params = new HashMap<>();
            params.put("table", "utilisateur");
            params.put("email", sessionManager.getEmail());
            params.put("dateDerniereConnexion", formatter.format(date));

            databaseManager.updateUser(params, listenerMainActivity);

            sessionManager.modifyUser("dateDerniereConnexion", formatter.format(date));

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