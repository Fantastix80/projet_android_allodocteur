package com.example.projetandroidallodocteurnew.mesRDV.medecin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.manager.SessionManager;
import com.example.projetandroidallodocteurnew.mesRDV.medecin.recyclerview.Item2;
import com.example.projetandroidallodocteurnew.mesRDV.medecin.recyclerview.MyAdapter2;
import com.example.projetandroidallodocteurnew.profil.LogiqueHoraires;
import com.example.projetandroidallodocteurnew.rdv.DetailMedecin;
import com.example.projetandroidallodocteurnew.rdv.RechercheMedecin;
import com.example.projetandroidallodocteurnew.rdv.recyclerview.Item;
import com.example.projetandroidallodocteurnew.rdv.recyclerview.MyAdapter;
import com.example.projetandroidallodocteurnew.rdv.recyclerview.PrendreRDV;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PotentielMedecin extends Fragment {

    private DatabaseManager databaseManager;
    private SessionManager sessionManager;
    private LogiqueHoraires logiqueHoraires;
    private AppCompatButton addDateRDV;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private String dateAAjouter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_potentiel_medecin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseManager = new DatabaseManager(getActivity());
        sessionManager = new SessionManager(getActivity());

        logiqueHoraires = new LogiqueHoraires();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        addDateRDV = view.findViewById(R.id.addDateRDV);

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

                        List<Item2> items = new ArrayList<Item2>();

                        for (int i = 0; i < dates.length(); i++) {
                            JSONObject date = dates.getJSONObject(i);

                            String dateTemp = date.getString("date");
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date dateUnformated = inputFormat.parse(dateTemp);
                            String dateFormate = outputFormat.format(dateUnformated);

                            items.add(new Item2(dateFormate, sessionManager.getEmail()));
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        MyAdapter2 myAdapter2 = new MyAdapter2(getActivity(), items);
                        recyclerView.setAdapter(myAdapter2);
                        myAdapter2.notifyDataSetChanged();

                    } else {
                        //Afficher l'erreur dans une textview prévu à cet effet, où rediriger l'utilisateur
                        //vers une autre page et afficher un Toast.
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        //Appel à l'API pour récupérer toutes les dates
        databaseManager.getAllDatesRDV(sessionManager.getEmail(), "potentiel", listener);

        //On vient écouter la réponse de la bdd
        DatabaseManager.VolleyResponseListener listenerHoraireTravail = new DatabaseManager.VolleyResponseListener() {
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

                        //Récupérer le jour de la semaine correspondant à la date
                        LocalDate date = LocalDate.parse(dateAAjouter);
                        String jourDeLaSemaine = getJourDeLaSemaine(date);

                        JSONArray jours = response.getJSONArray("horaireTravail");
                        Boolean jourTrouve = false;
                        for (int i = 0; i < jours.length(); i++) {
                            JSONObject jour = jours.getJSONObject(i);
                            if (jour.getString("jour").equals(jourDeLaSemaine)) {
                                jourTrouve = true;

                                ArrayList<ArrayList> listeDatesDebutFin = logiqueHoraires.associeDateAvecPlagesHoraires(date, jour.getString("horaire"), jour.getInt("duree"));
                                ArrayList<String> listeDateDebut = listeDatesDebutFin.get(0);
                                ArrayList<String> listeDateFin = listeDatesDebutFin.get(1);

                                for (int y = 0; y < listeDateDebut.size(); y++) {
                                    databaseManager.addRendezVous(listeDateDebut.get(y), listeDateFin.get(y), sessionManager.getEmail(), "");

                                    Intent refreshPage = new Intent(getActivity(), MesRDVMedecin.class);
                                    refreshPage.putExtra("fragment", "potentiel");
                                    startActivity(refreshPage);
                                }
                            }
                        }

                        if (!jourTrouve){
                            Toast.makeText(getActivity(), "Veuillez remplir vos horaires dans votre profil médecin avant d'ajouter des dates de rendez-vous.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        System.out.println(response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int annee, int mois, int jour) {
                mois = mois + 1;
                String newMois = "0" + Integer.toString(mois);
                String newJour = "0" + Integer.toString(jour);
                dateAAjouter = annee + "-" + newMois.substring(newMois.length() - 2) + "-" + newJour.substring(newJour.length() - 2);

                databaseManager.getHoraireTravail(sessionManager.getEmail(), listenerHoraireTravail);
            }
        };

        addDateRDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpDatePicker(getActivity());
            }
        });
    }

    public void popUpDatePicker(Context context) {

        Calendar cal = Calendar.getInstance();
        int annee = cal.get(Calendar.YEAR);
        int mois = cal.get(Calendar.MONTH);
        int jour = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, style, onDateSetListener, annee, mois, jour);
        datePickerDialog.show();
    }

    public String getJourDeLaSemaine(LocalDate date) {
        String jour = null;
        switch (date.getDayOfWeek().getValue()) {
            case 1:
                jour = "lundi";
                break;
            case 2:
                jour = "mardi";
                break;
            case 3:
                jour = "mercredi";
                break;
            case 4:
                jour = "jeudi";
                break;
            case 5:
                jour = "vendredi";
                break;
            case 6:
                jour = "samedi";
                break;
            case 7:
                jour = "dimanche";
                break;
        }

        return jour;
    }
}