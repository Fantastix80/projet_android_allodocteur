package com.example.formulairebeau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailMedecin extends AppCompatActivity {

    private TextView tvEmail, tvNom, tvPrenom;
    private Button btnGoBack;
    private String email;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_medecin);

        tvEmail = findViewById(R.id.tvEmail);
        tvNom = findViewById(R.id.tvNom);
        tvPrenom = findViewById(R.id.tvPrenom);
        btnGoBack = findViewById(R.id.goBack);

        databaseManager = new DatabaseManager(DetailMedecin.this);

        //On crée un listener qui viendra exécuter du code lorsqu'on recevra la réponse de la bdd
        DatabaseManager.VolleyResponseListener listener = new DatabaseManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) {
                String success = null;
                try {
                    success = response.getString("success");

                    if (success.equals("true")) {

                        JSONArray medecin = response.getJSONArray("infoMedecin");
                        JSONObject infoMedecin = medecin.getJSONObject(0);
                        System.out.println(infoMedecin);

                        //Renseigner toutes les données nécessaires dans les TextView
                        tvEmail.setText(infoMedecin.getString("email"));
                        tvNom.setText(infoMedecin.getString("nom"));
                        tvPrenom.setText(infoMedecin.getString("prenom"));

                    } else {
                        //Afficher l'erreur dans une textview prévu à cet effet, où rediriger l'utilisateur
                        //vers une autre page et afficher un Toast.
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            email = extras.getString("email");
            databaseManager.getOneMedecin(email, listener);
        } else {
            System.out.println("Extras vide dans l'activité DetailMedecin, retour à l'activité précédente");
            Intent erreurExtras = new Intent(DetailMedecin.this, RechercheMedecin.class);
            startActivity(erreurExtras);
            finish();
        }


        //temp revenir en arrière (activity)
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(DetailMedecin.this, RechercheMedecin.class);
                startActivity(goBack);
                finish();
            }
        });
    }
}