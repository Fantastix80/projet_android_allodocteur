package com.example.formulairebeau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RechercheMedecin extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private TextView tvDisplayError;
    private Spinner TypeRecherche;
    private EditText searchBar;
    private ListView listViewMedecin;
    private ArrayList<HashMap<String,String>> values = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String,String>> listeDeMedecins = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_medecin);

        tvDisplayError = findViewById(R.id.tvDisplayErrors);
        TypeRecherche = findViewById(R.id.spinnerTypeRecherche);
        searchBar = findViewById(R.id.search_bar_list_medecin);
        listViewMedecin = findViewById(R.id.ListViewMedecin);

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
                        listViewMedecin.setVisibility(View.VISIBLE);
                        tvDisplayError.setVisibility(View.GONE);
                        JSONArray medecins = response.getJSONArray("data");

                        for(int i = 0; i < medecins.length(); i++) {

                            JSONObject medecin = medecins.getJSONObject(i);
                            HashMap<String,String> m = new HashMap<String,String>();
                            //m.put("imageProfil", medecin.getString("imageProfil"));
                            m.put("email", medecin.getString("email"));
                            m.put("nomEtPrenom", medecin.getString("nom").toUpperCase() + " " + medecin.getString("prenom"));
                            m.put("specialite", medecin.getString("specialite"));
                            m.put("ville", medecin.getString("villePro"));
                            values.add(m);
                        }


                    } else {
                        listViewMedecin.setVisibility(View.GONE);
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
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int spinner_pos = TypeRecherche.getSelectedItemPosition();
                String[] values = getResources().getStringArray(R.array.liste_choix_recherche_valeur);
                int value = Integer.valueOf(values[spinner_pos]);
                listeDeMedecins = filterArrayListMedecins(charSequence.toString(), value);

                SimpleAdapter adapter = new SimpleAdapter(RechercheMedecin.this, listeDeMedecins, R.layout.format_list_item_medecin,
                        new String[]{"email", "nomEtPrenom", "specialite", "ville"},
                        new int[]{R.id.tvEmail, R.id.tvNomEtPrenom, R.id.tvSpecialite, R.id.tvVille});

                listViewMedecin.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //On crée un listener qui vient s'exécuter lorsque l'on clique sur un des médecins
        listViewMedecin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView emailMedecin = view.findViewById(R.id.tvEmail);
                Intent goToInfoMedecin = new Intent(RechercheMedecin.this, DetailMedecin.class);
                goToInfoMedecin.putExtra("email", emailMedecin.getText().toString());
                startActivity(goToInfoMedecin);
            }
        });

    }

    private ArrayList<HashMap<String,String>> filterArrayListMedecins(String textFilter, int typeFiltre) {
        ArrayList<HashMap<String,String>> arrayListMedecinsTemp = new ArrayList<HashMap<String, String>>();

        if(textFilter != null) {
            for(int i = 0; i < values.size(); i++) {
                switch (typeFiltre) {
                    case 0:
                        if(values.get(i).get("nomEtPrenom").toUpperCase().contains(textFilter.toUpperCase())) {
                            arrayListMedecinsTemp.add(values.get(i));
                        }
                        break;
                    case 1:
                        if(values.get(i).get("specialite").toUpperCase().contains(textFilter.toUpperCase())) {
                            arrayListMedecinsTemp.add(values.get(i));
                        }
                        break;
                    case 2:
                        if(values.get(i).get("ville").toUpperCase().contains(textFilter.toUpperCase())) {
                            arrayListMedecinsTemp.add(values.get(i));
                        }
                        break;
                }
            }
        } else {
            arrayListMedecinsTemp = values;
        }

        return arrayListMedecinsTemp;
    }
}