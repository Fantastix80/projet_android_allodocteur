package com.example.projetandroidallodocteurnew.mesRDV.medecin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.manager.SessionManager;
import com.example.projetandroidallodocteurnew.mesPatients.MesPatients;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AVenirMedecin extends Fragment {

    private DatabaseManager databaseManager;
    private SessionManager sessionManager;
    private ListView ListViewRDVAVenir;
    private ArrayList<HashMap<String,String>> listeRDVAVenir = new ArrayList<HashMap<String, String>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a_venir_medecin, container, false);

        databaseManager = new DatabaseManager(getContext());
        sessionManager = new SessionManager(getContext());

        ListViewRDVAVenir = view.findViewById(R.id.ListViewRDVAVenir);

        //On vient écouter les réponses de la bdd
        DatabaseManager.VolleyResponseListener listenerAVenir = new DatabaseManager.VolleyResponseListener() {
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
                        JSONArray rdvs = response.getJSONArray("RDVAVenir");

                        listeRDVAVenir.clear();

                        for(int i = 0; i < rdvs.length(); i++) {

                            JSONObject rdv = rdvs.getJSONObject(i);
                            HashMap<String,String> m = new HashMap<String,String>();
                            m.put("email", rdv.getString("email"));
                            m.put("dateDebut", rdv.getString("dateDebut"));
                            m.put("dateFin", rdv.getString("dateFin"));

                            //date formate
                            String dateTemp = rdv.getString("dateDebut");
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date dateUnformated = inputFormat.parse(dateTemp);
                            String dateFormate = outputFormat.format(dateUnformated);
                            //heure
                            String tempHeure = rdv.getString("dateDebut").substring(11, 16) + "-" + rdv.getString("dateFin").substring(11, 16);

                            m.put("dateHeureFormate", tempHeure + " " + dateFormate);
                            m.put("NomEtPrenom", rdv.getString("nom").toUpperCase() + " " + rdv.getString("prenom"));

                            listeRDVAVenir.add(m);
                        }

                        //Ajout des éléments dans la listview
                        SimpleAdapter adapter = new SimpleAdapter(getContext(), listeRDVAVenir, R.layout.format_list_item_rdv,
                                new String[]{"email", "dateDebut", "dateFin", "dateHeureFormate", "NomEtPrenom"},
                                new int[]{R.id.tvEmail, R.id.tvDateDebut, R.id.tvDateFin, R.id.tvDateHeure, R.id.tvNomEtPrenom});

                        ListViewRDVAVenir.setAdapter(adapter);

                    } else {
                        //Afficher une erreur dans une textview
                        System.out.println("réponse false");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        //On vient appeler les APIS
        databaseManager.getRDVAVenir("medecin", sessionManager.getEmail(), listenerAVenir);

        return view;
    }
}