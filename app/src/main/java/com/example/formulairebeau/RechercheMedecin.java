package com.example.formulairebeau;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RechercheMedecin extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private TextView tvDisplayError;
    private Spinner TypeRecherche;
    private EditText searchBar;
    private ListView ListViewMedecin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_medecin);

        tvDisplayError = findViewById(R.id.tvDisplayErrors);
        TypeRecherche = findViewById(R.id.spinnerTypeRecherche);
        searchBar = findViewById(R.id.search_bar_list_medecin);
        ListViewMedecin = findViewById(R.id.ListViewMedecin);

        databaseManager = new DatabaseManager(getApplicationContext());

        //On crée un listener qui va venir récupérer la réponse de l'API
        DatabaseManager.VolleyResponseListener listenerGetAllMedecin = new DatabaseManager.VolleyResponseListener() {
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
                        ListViewMedecin.setVisibility(View.VISIBLE);
                        tvDisplayError.setVisibility(View.GONE);
                        System.out.println(response);
                        JSONObject data = response.getJSONObject("data");
                        System.out.println(data);
                        data.keys().forEachRemaining(key -> {
                            Object value = null;
                            try {
                                value = data.get(key);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println(value);
                        });

                    } else {
                        ListViewMedecin.setVisibility(View.GONE);
                        tvDisplayError.setVisibility(View.VISIBLE);
                        tvDisplayError.setText(response.getString("error"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        //Appel à l'API pour récupérer la liste des médecins
        databaseManager.getAllMedecin(listenerGetAllMedecin);

        //Récupérer la barre de recherche et filtrer les médecins en fonction de ce qui est écrit
    }
}