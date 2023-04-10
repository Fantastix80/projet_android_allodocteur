package com.example.projetandroidallodocteurnew.mesPatients;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.accueil.AccueilMedecin;
import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.manager.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MesPatients extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private SessionManager sessionManager;
    private ListView ListViewPatientsNonAcceptes, ListViewPatientsAcceptes;
    private Button btnFlecheRetour;
    private TextView tvRelationEnAttente;
    private ArrayList<HashMap<String,String>> listeDePatientsNonAcceptes = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String,String>> listeDePatientsAcceptes = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_patients);

        databaseManager = new DatabaseManager(MesPatients.this);
        sessionManager = new SessionManager(MesPatients.this);

        btnFlecheRetour = findViewById(R.id.btnFlecheRetour);

        tvRelationEnAttente = findViewById(R.id.tvRelationEnAttente);

        ListViewPatientsNonAcceptes = findViewById(R.id.ListViewPatientsNonAcceptes);
        ListViewPatientsAcceptes = findViewById(R.id.ListViewPatientsAcceptes);


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
                        JSONArray patients = response.getJSONArray("data");

                        for(int i = 0; i < patients.length(); i++) {

                            JSONObject patient = patients.getJSONObject(i);
                            HashMap<String,String> m = new HashMap<String,String>();
                            m.put("email", patient.getString("email"));
                            m.put("nomEtPrenom", patient.getString("nom").toUpperCase() + " " + patient.getString("prenom"));

                            //Ajout de condition pour savoir si la relation est acceptée ou non
                            if (patient.getInt("isRelationAccepted") == 0) {
                                listeDePatientsNonAcceptes.add(m);
                            } else {
                                listeDePatientsAcceptes.add(m);
                            }
                        }

                        if (listeDePatientsNonAcceptes.size() > 0) {
                            tvRelationEnAttente.setVisibility(View.VISIBLE);
                            ListViewPatientsNonAcceptes.setVisibility(View.VISIBLE);
                        } else {
                            tvRelationEnAttente.setVisibility(View.GONE);
                            ListViewPatientsNonAcceptes.setVisibility(View.GONE);
                        }

                        //Ajout des éléments dans la listview (patients Non acceptés)
                        SimpleAdapter adapter1 = new SimpleAdapter(MesPatients.this, listeDePatientsNonAcceptes, R.layout.format_list_item_patient,
                                new String[]{"email", "nomEtPrenom"},
                                new int[]{R.id.tvEmail, R.id.tvNomEtPrenom});

                        ListViewPatientsNonAcceptes.setAdapter(adapter1);

                        //Ajout des éléments dans la listview (patients Non acceptés)
                        SimpleAdapter adapter2 = new SimpleAdapter(MesPatients.this, listeDePatientsAcceptes, R.layout.format_list_item_patient,
                                new String[]{"email", "nomEtPrenom"},
                                new int[]{R.id.tvEmail, R.id.tvNomEtPrenom});

                        ListViewPatientsAcceptes.setAdapter(adapter2);


                    } else {
                        //Afficher une erreur dans une textview
                        System.out.println("réponse false");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        databaseManager.actionsMedecinTraitant("selectPatients", "", sessionManager.getEmail(), listener);


        btnFlecheRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retour = new Intent(MesPatients.this, AccueilMedecin.class);
                startActivity(retour);
                finish();
            }
        });

        ListViewPatientsNonAcceptes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView emailPatient = view.findViewById(R.id.tvEmail);
                Intent goToInfoPatient = new Intent(MesPatients.this, MesPatientsInfos.class);
                goToInfoPatient.putExtra("email", emailPatient.getText().toString());
                goToInfoPatient.putExtra("isAccepted", "non");
                startActivity(goToInfoPatient);
                finish();
            }
        });

        ListViewPatientsAcceptes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView emailPatient = view.findViewById(R.id.tvEmail);
                Intent goToInfoPatient = new Intent(MesPatients.this, MesPatientsInfos.class);
                goToInfoPatient.putExtra("email", emailPatient.getText().toString());
                goToInfoPatient.putExtra("isAccepted", "oui");
                startActivity(goToInfoPatient);
                finish();
            }
        });

    }
}