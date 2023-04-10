package com.example.projetandroidallodocteurnew.rdv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.accueil.AccueilMedecin;
import com.example.projetandroidallodocteurnew.accueil.AccueilPatient;
import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.manager.SessionManager;
import com.example.projetandroidallodocteurnew.rdv.recyclerview.Item;
import com.example.projetandroidallodocteurnew.rdv.recyclerview.MyAdapter;
import com.example.projetandroidallodocteurnew.rdv.recyclerview.PrendreRDV;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaiementRDV extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private SessionManager sessionManager;
    private Button btnReserverEtPayer, btnFlecheRetour;
    private String dateDebut, dateFin, emailMedecin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement_rdv);

        databaseManager = new DatabaseManager(PaiementRDV.this);
        sessionManager = new SessionManager(PaiementRDV.this);

        btnReserverEtPayer = findViewById(R.id.btnReserverEtPayer);
        btnFlecheRetour = findViewById(R.id.btnFlecheRetour);

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

                        Intent accueil = new Intent(PaiementRDV.this, AccueilPatient.class);
                        startActivity(accueil);
                        finish();

                    } else {
                        System.out.println(response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            dateDebut = extras.getString("dateDebut");
            dateFin = extras.getString("dateFin");
            emailMedecin = extras.getString("email");
        } else {
            System.out.println("Extras vide dans l'activité PrendreRDV, retour à l'activité précédente");
            Intent erreurExtras = new Intent(PaiementRDV.this, RechercheMedecin.class);
            startActivity(erreurExtras);
            finish();
        }

        btnReserverEtPayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Appel à l'API pour récupérer toutes les dates
                databaseManager.prendreRDV(dateDebut, dateFin, emailMedecin, sessionManager.getEmail(), listener);
            }
        });

        btnFlecheRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(PaiementRDV.this, PrendreRDV.class);
                goBack.putExtra("email", emailMedecin);
                startActivity(goBack);
                finish();
            }
        });
    }
}