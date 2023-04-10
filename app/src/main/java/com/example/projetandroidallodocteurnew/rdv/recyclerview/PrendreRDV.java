package com.example.projetandroidallodocteurnew.rdv.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.manager.SessionManager;
import com.example.projetandroidallodocteurnew.rdv.DetailMedecin;
import com.example.projetandroidallodocteurnew.rdv.PaiementRDV;
import com.example.projetandroidallodocteurnew.rdv.RechercheMedecin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrendreRDV extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private SessionManager sessionManager;
    private String emailMedecin;
    private Button btnFlecheRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prendre_rdv);

        databaseManager = new DatabaseManager(PrendreRDV.this);
        sessionManager = new SessionManager(PrendreRDV.this);

        btnFlecheRetour = findViewById(R.id.btnFlecheRetour);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        //On vient écouter la réponse de la BDD
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

                        JSONArray dates = response.getJSONArray("dates");

                        List<Item> items = new ArrayList<Item>();

                        for (int i = 0; i < dates.length(); i++) {
                            JSONObject date = dates.getJSONObject(i);

                            String dateTemp = date.getString("date");
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date dateUnformated = inputFormat.parse(dateTemp);
                            String dateFormate = outputFormat.format(dateUnformated);

                            items.add(new Item(dateFormate, emailMedecin));
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), items));

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

        //On récupère l'email du médecin avec lequel l'utilisateur souhaite prendre un RDV
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            emailMedecin = extras.getString("email");

            //Appel à l'API pour récupérer toutes les dates
            databaseManager.getAllDatesRDV(emailMedecin, "", listener);
        } else {
            System.out.println("Extras vide dans l'activité PrendreRDV, retour à l'activité précédente");
            Intent erreurExtras = new Intent(PrendreRDV.this, RechercheMedecin.class);
            startActivity(erreurExtras);
            finish();
        }

        btnFlecheRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(PrendreRDV.this, DetailMedecin.class);
                goBack.putExtra("email", emailMedecin);
                startActivity(goBack);
                finish();
            }
        });
    }
}