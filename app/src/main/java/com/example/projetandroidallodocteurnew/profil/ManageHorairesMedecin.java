package com.example.projetandroidallodocteurnew.profil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.manager.SessionManager;
import com.example.projetandroidallodocteurnew.mesRDV.medecin.MesRDVMedecin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class ManageHorairesMedecin extends AppCompatActivity {

    private LogiqueHoraires logiqueHoraires;
    private DatabaseManager databaseManager;
    private SessionManager sessionManager;
    private static CheckBox lundi, mardi, mercredi, jeudi, vendredi, samedi, dimanche;
    //private Boolean lundi, mardi, mercredi, jeudi, vendredi, samedi, dimanche;
    private EditText etDuree, etLundiMatinDebut, etLundiMatinFin, etLundiApremDebut, etLundiApremFin, etMardiMatinDebut, etMardiMatinFin, etMardiApremDebut, etMardiApremFin, etMercrediMatinDebut, etMercrediMatinFin, etMercrediApremDebut, etMercrediApremFin, etJeudiMatinDebut, etJeudiMatinFin, etJeudiApremDebut, etJeudiApremFin, etVendrediMatinDebut, etVendrediMatinFin, etVendrediApremDebut, etVendrediApremFin, etSamediMatinDebut, etSamediMatinFin, etSamediApremDebut, etSamediApremFin, etDimancheMatinDebut, etDimancheMatinFin, etDimancheApremDebut, etDimancheApremFin;
    private Button save;
    private ImageButton cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_horaires_medecin);

        logiqueHoraires = new LogiqueHoraires();
        databaseManager = new DatabaseManager(ManageHorairesMedecin.this);
        sessionManager = new SessionManager(ManageHorairesMedecin.this);

        //ID de tous les checkBox
        lundi = findViewById(R.id.cbLundi);
        mardi = findViewById(R.id.cbMardi);
        mercredi = findViewById(R.id.cbMercredi);
        jeudi = findViewById(R.id.cbJeudi);
        vendredi = findViewById(R.id.cbVendredi);
        samedi = findViewById(R.id.cbSamedi);
        dimanche = findViewById(R.id.cbDimanche);

        //ID de tous les EditText
        etDuree = findViewById(R.id.etDuree);

        etLundiMatinDebut = findViewById(R.id.etLundiMatinDebut);
        etLundiMatinFin = findViewById(R.id.etLundiMatinFin);
        etLundiApremDebut = findViewById(R.id.etLundiApremDebut);
        etLundiApremFin = findViewById(R.id.etLundiApremFin);

        etMardiMatinDebut = findViewById(R.id.etMardiMatinDebut);
        etMardiMatinFin = findViewById(R.id.etMardiMatinFin);
        etMardiApremDebut = findViewById(R.id.etMardiApremDebut);
        etMardiApremFin = findViewById(R.id.etMardiApremFin);

        etMercrediMatinDebut = findViewById(R.id.etMercrediMatinDebut);
        etMercrediMatinFin = findViewById(R.id.etMercrediMatinFin);
        etMercrediApremDebut = findViewById(R.id.etMercrediApremDebut);
        etMercrediApremFin = findViewById(R.id.etMercrediApremFin);

        etJeudiMatinDebut = findViewById(R.id.etJeudiMatinDebut);
        etJeudiMatinFin = findViewById(R.id.etJeudiMatinFin);
        etJeudiApremDebut = findViewById(R.id.etJeudiApremDebut);
        etJeudiApremFin = findViewById(R.id.etJeudiApremFin);

        etVendrediMatinDebut = findViewById(R.id.etVendrediMatinDebut);
        etVendrediMatinFin = findViewById(R.id.etVendrediMatinFin);
        etVendrediApremDebut = findViewById(R.id.etVendrediApremDebut);
        etVendrediApremFin = findViewById(R.id.etVendrediApremFin);

        etSamediMatinDebut = findViewById(R.id.etSamediMatinDebut);
        etSamediMatinFin = findViewById(R.id.etSamediMatinFin);
        etSamediApremDebut = findViewById(R.id.etSamediApremDebut);
        etSamediApremFin = findViewById(R.id.etSamediApremFin);

        etDimancheMatinDebut = findViewById(R.id.etDimancheMatinDebut);
        etDimancheMatinFin = findViewById(R.id.etDimancheMatinFin);
        etDimancheApremDebut = findViewById(R.id.etDimancheApremDebut);
        etDimancheApremFin = findViewById(R.id.etDimancheApremFin);

        //Récuperer ID tous Button
        save = findViewById(R.id.btnEnregistrer);
        cancel = findViewById(R.id.btnCancel);


        //Définir si le medecin travail les jours de la semaines
        lundi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lundi.isChecked() == true){
                    etLundiMatinDebut.setEnabled(true);
                    etLundiMatinFin.setEnabled(true);
                    etLundiApremDebut.setEnabled(true);
                    etLundiApremFin.setEnabled(true);
                }else {
                    etLundiMatinDebut.setEnabled(false);
                    etLundiMatinFin.setEnabled(false);
                    etLundiApremDebut.setEnabled(false);
                    etLundiApremFin.setEnabled(false);
                }
            }
        });

        mardi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mardi.isChecked() == true){
                    etMardiMatinDebut.setEnabled(true);
                    etMardiMatinFin.setEnabled(true);
                    etMardiApremDebut.setEnabled(true);
                    etMardiApremFin.setEnabled(true);
                }else {
                    etMardiMatinDebut.setEnabled(false);
                    etMardiMatinFin.setEnabled(false);
                    etMardiApremDebut.setEnabled(false);
                    etMardiApremFin.setEnabled(false);
                }
            }
        });

        mercredi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mercredi.isChecked() == true){
                    etMercrediMatinDebut.setEnabled(true);
                    etMercrediMatinFin.setEnabled(true);
                    etMercrediApremDebut.setEnabled(true);
                    etMercrediApremFin.setEnabled(true);
                }else {
                    etMercrediMatinDebut.setEnabled(false);
                    etMercrediMatinFin.setEnabled(false);
                    etMercrediApremDebut.setEnabled(false);
                    etMercrediApremFin.setEnabled(false);
                }
            }
        });

        jeudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jeudi.isChecked() == true){
                    etJeudiMatinDebut.setEnabled(true);
                    etJeudiMatinFin.setEnabled(true);
                    etJeudiApremDebut.setEnabled(true);
                    etJeudiApremFin.setEnabled(true);
                }else {
                    etJeudiMatinDebut.setEnabled(false);
                    etJeudiMatinFin.setEnabled(false);
                    etJeudiApremDebut.setEnabled(false);
                    etJeudiApremFin.setEnabled(false);
                }
            }
        });

        vendredi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vendredi.isChecked() == true){
                    etVendrediMatinDebut.setEnabled(true);
                    etVendrediMatinFin.setEnabled(true);
                    etVendrediApremDebut.setEnabled(true);
                    etVendrediApremFin.setEnabled(true);
                }else {
                    etVendrediMatinDebut.setEnabled(false);
                    etVendrediMatinFin.setEnabled(false);
                    etVendrediApremDebut.setEnabled(false);
                    etVendrediApremFin.setEnabled(false);
                }
            }
        });

        samedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(samedi.isChecked() == true){
                    etSamediMatinDebut.setEnabled(true);
                    etSamediMatinFin.setEnabled(true);
                    etSamediApremDebut.setEnabled(true);
                    etSamediApremFin.setEnabled(true);
                }else {
                    etSamediMatinDebut.setEnabled(false);
                    etSamediMatinFin.setEnabled(false);
                    etSamediApremDebut.setEnabled(false);
                    etSamediApremFin.setEnabled(false);
                }
            }
        });

        dimanche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dimanche.isChecked() == true){
                    etDimancheMatinDebut.setEnabled(true);
                    etDimancheMatinFin.setEnabled(true);
                    etDimancheApremDebut.setEnabled(true);
                    etDimancheApremFin.setEnabled(true);
                }else {
                    etDimancheMatinDebut.setEnabled(false);
                    etDimancheMatinFin.setEnabled(false);
                    etDimancheApremDebut.setEnabled(false);
                    etDimancheApremFin.setEnabled(false);
                }
            }
        });

        DatabaseManager.VolleyResponseListener listenerVoid = new DatabaseManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) {

            }
        };

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
                        JSONArray horaires = response.getJSONArray("horaireTravail");

                        etDuree.setText(horaires.getJSONObject(0).getString("duree"));

                        for (int i = 0; i < horaires.length(); i++) {
                            JSONObject horaire = horaires.getJSONObject(i);

                            if (horaire.get("jour").equals("lundi")) {
                                String horaireFormate = horaire.getString("horaire");
                                if (horaireFormate.contains("|")) {
                                    String horaireMatin = horaireFormate.split("[|]")[0];
                                    String horaireAprem = horaireFormate.split("[|]")[1];

                                    etLundiMatinDebut.setText(horaireMatin.split("-")[0]);
                                    etLundiMatinFin.setText(horaireMatin.split("-")[1]);
                                    etLundiApremDebut.setText(horaireAprem.split("-")[0]);
                                    etLundiApremFin.setText(horaireAprem.split("-")[1]);
                                } else {
                                    String horaireMatin = horaireFormate;

                                    etLundiMatinDebut.setText(horaireFormate.split("-")[0]);
                                    etLundiMatinFin.setText(horaireFormate.split("-")[1]);
                                }

                                lundi.setChecked(true);
                                etLundiMatinDebut.setEnabled(true);
                                etLundiMatinFin.setEnabled(true);
                                etLundiApremDebut.setEnabled(true);
                                etLundiApremFin.setEnabled(true);
                            } else if (horaire.get("jour").equals("mardi")) {
                                String horaireFormate = horaire.getString("horaire");
                                if (horaireFormate.contains("|")) {
                                    String horaireMatin = horaireFormate.split("[|]")[0];
                                    String horaireAprem = horaireFormate.split("[|]")[1];

                                    etMardiMatinDebut.setText(horaireMatin.split("-")[0]);
                                    etMardiMatinFin.setText(horaireMatin.split("-")[1]);
                                    etMardiApremDebut.setText(horaireAprem.split("-")[0]);
                                    etMardiApremFin.setText(horaireAprem.split("-")[1]);
                                } else {
                                    String horaireMatin = horaireFormate;

                                    etMardiMatinDebut.setText(horaireFormate.split("-")[0]);
                                    etMardiMatinFin.setText(horaireFormate.split("-")[1]);
                                }

                                mardi.setChecked(true);
                                etMardiMatinDebut.setEnabled(true);
                                etMardiMatinFin.setEnabled(true);
                                etMardiApremDebut.setEnabled(true);
                                etMardiApremFin.setEnabled(true);
                            } else if (horaire.get("jour").equals("mercredi")) {
                                String horaireFormate = horaire.getString("horaire");
                                if (horaireFormate.contains("|")) {
                                    String horaireMatin = horaireFormate.split("[|]")[0];
                                    String horaireAprem = horaireFormate.split("[|]")[1];

                                    etMercrediMatinDebut.setText(horaireMatin.split("-")[0]);
                                    etMercrediMatinFin.setText(horaireMatin.split("-")[1]);
                                    etMercrediApremDebut.setText(horaireAprem.split("-")[0]);
                                    etMercrediApremFin.setText(horaireAprem.split("-")[1]);
                                } else {
                                    String horaireMatin = horaireFormate;

                                    etMercrediMatinDebut.setText(horaireFormate.split("-")[0]);
                                    etMercrediMatinFin.setText(horaireFormate.split("-")[1]);
                                }

                                mercredi.setChecked(true);
                                etMercrediMatinDebut.setEnabled(true);
                                etMercrediMatinFin.setEnabled(true);
                                etMercrediApremDebut.setEnabled(true);
                                etMercrediApremFin.setEnabled(true);
                            } else if (horaire.get("jour").equals("jeudi")) {
                                String horaireFormate = horaire.getString("horaire");
                                if (horaireFormate.contains("|")) {
                                    String horaireMatin = horaireFormate.split("[|]")[0];
                                    String horaireAprem = horaireFormate.split("[|]")[1];

                                    etJeudiMatinDebut.setText(horaireMatin.split("-")[0]);
                                    etJeudiMatinFin.setText(horaireMatin.split("-")[1]);
                                    etJeudiApremDebut.setText(horaireAprem.split("-")[0]);
                                    etJeudiApremFin.setText(horaireAprem.split("-")[1]);
                                } else {
                                    String horaireMatin = horaireFormate;

                                    etJeudiMatinDebut.setText(horaireFormate.split("-")[0]);
                                    etJeudiMatinFin.setText(horaireFormate.split("-")[1]);
                                }

                                jeudi.setChecked(true);
                                etJeudiMatinDebut.setEnabled(true);
                                etJeudiMatinFin.setEnabled(true);
                                etJeudiApremDebut.setEnabled(true);
                                etJeudiApremFin.setEnabled(true);
                            } else if (horaire.get("jour").equals("vendredi")) {
                                String horaireFormate = horaire.getString("horaire");
                                if (horaireFormate.contains("|")) {
                                    String horaireMatin = horaireFormate.split("[|]")[0];
                                    String horaireAprem = horaireFormate.split("[|]")[1];

                                    etVendrediMatinDebut.setText(horaireMatin.split("-")[0]);
                                    etVendrediMatinFin.setText(horaireMatin.split("-")[1]);
                                    etVendrediApremDebut.setText(horaireAprem.split("-")[0]);
                                    etVendrediApremFin.setText(horaireAprem.split("-")[1]);
                                } else {
                                    String horaireMatin = horaireFormate;

                                    etVendrediMatinDebut.setText(horaireFormate.split("-")[0]);
                                    etVendrediMatinFin.setText(horaireFormate.split("-")[1]);
                                }

                                vendredi.setChecked(true);
                                etVendrediMatinDebut.setEnabled(true);
                                etVendrediMatinFin.setEnabled(true);
                                etVendrediApremDebut.setEnabled(true);
                                etVendrediApremFin.setEnabled(true);
                            } else if (horaire.get("jour").equals("samedi")) {
                                String horaireFormate = horaire.getString("horaire");
                                if (horaireFormate.contains("|")) {
                                    String horaireMatin = horaireFormate.split("[|]")[0];
                                    String horaireAprem = horaireFormate.split("[|]")[1];

                                    etSamediMatinDebut.setText(horaireMatin.split("-")[0]);
                                    etSamediMatinFin.setText(horaireMatin.split("-")[1]);
                                    etSamediApremDebut.setText(horaireAprem.split("-")[0]);
                                    etSamediApremFin.setText(horaireAprem.split("-")[1]);
                                } else {
                                    String horaireMatin = horaireFormate;

                                    etSamediMatinDebut.setText(horaireFormate.split("-")[0]);
                                    etSamediMatinFin.setText(horaireFormate.split("-")[1]);
                                }

                                samedi.setChecked(true);
                                etSamediMatinDebut.setEnabled(true);
                                etSamediMatinFin.setEnabled(true);
                                etSamediApremDebut.setEnabled(true);
                                etSamediApremFin.setEnabled(true);
                            } else if (horaire.get("jour").equals("dimanche")) {
                                String horaireFormate = horaire.getString("horaire");
                                if (horaireFormate.contains("|")) {
                                    String horaireMatin = horaireFormate.split("[|]")[0];
                                    String horaireAprem = horaireFormate.split("[|]")[1];

                                    etDimancheMatinDebut.setText(horaireMatin.split("-")[0]);
                                    etDimancheMatinFin.setText(horaireMatin.split("-")[1]);
                                    etDimancheApremDebut.setText(horaireAprem.split("-")[0]);
                                    etDimancheApremFin.setText(horaireAprem.split("-")[1]);
                                } else {
                                    String horaireMatin = horaireFormate;

                                    etDimancheMatinDebut.setText(horaireFormate.split("-")[0]);
                                    etDimancheMatinFin.setText(horaireFormate.split("-")[1]);
                                }

                                dimanche.setChecked(true);
                                etDimancheMatinDebut.setEnabled(true);
                                etDimancheMatinFin.setEnabled(true);
                                etDimancheApremDebut.setEnabled(true);
                                etDimancheApremFin.setEnabled(true);
                            }
                        }

                        databaseManager.deleteHoraireTravailAll(sessionManager.getEmail(), listenerVoid);

                    } else {
                        System.out.println(response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        DatabaseManager.VolleyResponseListener listenerDeleteHoraireTravail = new DatabaseManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);

                Intent profilMedecinReponseBDD = new Intent(ManageHorairesMedecin.this, ProfilMedecin.class);
                startActivity(profilMedecinReponseBDD);
                finish();
            }
        };

        databaseManager.getHoraireTravail(sessionManager.getEmail(), listenerHoraireTravail);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retour = new Intent(ManageHorairesMedecin.this, ProfilMedecin.class);
                startActivity(retour);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> joursASupprimer = new ArrayList<String>();

                if (lundi.isChecked()) {
                    String horaire = "";
                    String horaireMatinDebut = etLundiMatinDebut.getText().toString();
                    String horaireMatinFin = etLundiMatinFin.getText().toString();
                    String horaireApremDebut = etLundiApremDebut.getText().toString();
                    String horaireApremFin = etLundiApremFin.getText().toString();

                    //Travail de formatage à faire sur les données reçues afin de les mettre sous le format: 00:00
                    if (horaireApremDebut.length() == 0 && horaireApremFin.length() == 0 && horaireMatinDebut.length() != 0 && horaireMatinFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireMatinFin;
                    } else if (horaireMatinDebut.length() == 0 && horaireMatinFin.length() == 0 && horaireApremDebut.length() != 0 && horaireApremFin.length() != 0) {
                        horaire = horaireApremDebut + "-" + horaireApremFin;
                    } else if (horaireMatinDebut.length() != 0 && horaireMatinFin.length() != 0 && horaireApremDebut.length() != 0 && horaireApremFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireMatinFin + "|" + horaireApremDebut + "-" + horaireApremFin;
                    } else if (horaireMatinDebut.length() != 0 && horaireMatinFin.length() == 0 && horaireApremDebut.length() == 0 && horaireApremFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireApremFin;
                    }

                    if (!horaire.equals("") && !etDuree.getText().toString().equals("")) {
                        databaseManager.addHoraireTravail(sessionManager.getEmail(), "lundi", horaire, etDuree.getText().toString());

                        ArrayList<ArrayList> listeDatesDebutFin = logiqueHoraires.associeJourAvecPlagesHoraires("lundi", horaire, Integer.parseInt(etDuree.getText().toString()), 1);
                        ArrayList<String> listeDateDebut = listeDatesDebutFin.get(0);
                        ArrayList<String> listeDateFin = listeDatesDebutFin.get(1);

                        for (int i = 0; i < listeDateDebut.size(); i++) {
                            databaseManager.addRendezVous(listeDateDebut.get(i), listeDateFin.get(i), sessionManager.getEmail(), "");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Veuillez respecter un format précis: 09:00 pour les heures et soit remplir juste les horaires de la matinée, soit cella de l'après-midi, les dexu ou encore l'heure de début du matin et celle de fin de l'après-midi.", Toast.LENGTH_SHORT);
                    }
                } else {
                    joursASupprimer.add("lundi");
                }
                if (mardi.isChecked()) {
                    String horaire = "";
                    String horaireMatinDebut = etMardiMatinDebut.getText().toString();
                    String horaireMatinFin = etMardiMatinFin.getText().toString();
                    String horaireApremDebut = etMardiApremDebut.getText().toString();
                    String horaireApremFin = etMardiApremFin.getText().toString();

                    //Travail de formatage à faire sur les données reçues afin de les mettre sous le format: 00:00
                    if (horaireApremDebut.length() == 0 && horaireApremFin.length() == 0 && horaireMatinDebut.length() != 0 && horaireMatinFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireMatinFin;
                    } else if (horaireMatinDebut.length() == 0 && horaireMatinFin.length() == 0 && horaireApremDebut.length() != 0 && horaireApremFin.length() != 0) {
                        horaire = horaireApremDebut + "-" + horaireApremFin;
                    } else if (horaireMatinDebut.length() != 0 && horaireMatinFin.length() != 0 && horaireApremDebut.length() != 0 && horaireApremFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireMatinFin + "|" + horaireApremDebut + "-" + horaireApremFin;
                    } else if (horaireMatinDebut.length() != 0 && horaireMatinFin.length() == 0 && horaireApremDebut.length() == 0 && horaireApremFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireApremFin;
                    }

                    if (!horaire.equals("") && !etDuree.getText().toString().equals("")) {
                        databaseManager.addHoraireTravail(sessionManager.getEmail(), "mardi", horaire, etDuree.getText().toString());

                        ArrayList<ArrayList> listeDatesDebutFin = logiqueHoraires.associeJourAvecPlagesHoraires("mardi", horaire, Integer.parseInt(etDuree.getText().toString()), 1);
                        ArrayList<String> listeDateDebut = listeDatesDebutFin.get(0);
                        ArrayList<String> listeDateFin = listeDatesDebutFin.get(1);

                        for (int i = 0; i < listeDateDebut.size(); i++) {
                            databaseManager.addRendezVous(listeDateDebut.get(i), listeDateFin.get(i), sessionManager.getEmail(), "");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Veuillez respecter un format précis: 09:00 pour les heures et soit remplir juste les horaires de la matinée, soit cella de l'après-midi, les dexu ou encore l'heure de début du matin et celle de fin de l'après-midi.", Toast.LENGTH_SHORT);
                    }
                } else {
                    joursASupprimer.add("mardi");
                }
                if (mercredi.isChecked()) {
                    String horaire = "";
                    String horaireMatinDebut = etMercrediMatinDebut.getText().toString();
                    String horaireMatinFin = etMercrediMatinFin.getText().toString();
                    String horaireApremDebut = etMercrediApremDebut.getText().toString();
                    String horaireApremFin = etMercrediApremFin.getText().toString();

                    //Travail de formatage à faire sur les données reçues afin de les mettre sous le format: 00:00
                    if (horaireApremDebut.length() == 0 && horaireApremFin.length() == 0 && horaireMatinDebut.length() != 0 && horaireMatinFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireMatinFin;
                    } else if (horaireMatinDebut.length() == 0 && horaireMatinFin.length() == 0 && horaireApremDebut.length() != 0 && horaireApremFin.length() != 0) {
                        horaire = horaireApremDebut + "-" + horaireApremFin;
                    } else if (horaireMatinDebut.length() != 0 && horaireMatinFin.length() != 0 && horaireApremDebut.length() != 0 && horaireApremFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireMatinFin + "|" + horaireApremDebut + "-" + horaireApremFin;
                    } else if (horaireMatinDebut.length() != 0 && horaireMatinFin.length() == 0 && horaireApremDebut.length() == 0 && horaireApremFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireApremFin;
                    }

                    if (!horaire.equals("") && !etDuree.getText().toString().equals("")) {
                        databaseManager.addHoraireTravail(sessionManager.getEmail(), "mercredi", horaire, etDuree.getText().toString());

                        ArrayList<ArrayList> listeDatesDebutFin = logiqueHoraires.associeJourAvecPlagesHoraires("mercredi", horaire, Integer.parseInt(etDuree.getText().toString()), 1);
                        ArrayList<String> listeDateDebut = listeDatesDebutFin.get(0);
                        ArrayList<String> listeDateFin = listeDatesDebutFin.get(1);

                        for (int i = 0; i < listeDateDebut.size(); i++) {
                            databaseManager.addRendezVous(listeDateDebut.get(i), listeDateFin.get(i), sessionManager.getEmail(), "");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Veuillez respecter un format précis: 09:00 pour les heures et soit remplir juste les horaires de la matinée, soit cella de l'après-midi, les dexu ou encore l'heure de début du matin et celle de fin de l'après-midi.", Toast.LENGTH_SHORT);
                    }
                } else {
                    joursASupprimer.add("mercredi");
                }
                if (jeudi.isChecked()) {
                    String horaire = "";
                    String horaireMatinDebut = etJeudiMatinDebut.getText().toString();
                    String horaireMatinFin = etJeudiMatinFin.getText().toString();
                    String horaireApremDebut = etJeudiApremDebut.getText().toString();
                    String horaireApremFin = etJeudiApremFin.getText().toString();

                    //Travail de formatage à faire sur les données reçues afin de les mettre sous le format: 00:00
                    if (horaireApremDebut.length() == 0 && horaireApremFin.length() == 0 && horaireMatinDebut.length() != 0 && horaireMatinFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireMatinFin;
                    } else if (horaireMatinDebut.length() == 0 && horaireMatinFin.length() == 0 && horaireApremDebut.length() != 0 && horaireApremFin.length() != 0) {
                        horaire = horaireApremDebut + "-" + horaireApremFin;
                    } else if (horaireMatinDebut.length() != 0 && horaireMatinFin.length() != 0 && horaireApremDebut.length() != 0 && horaireApremFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireMatinFin + "|" + horaireApremDebut + "-" + horaireApremFin;
                    } else if (horaireMatinDebut.length() != 0 && horaireMatinFin.length() == 0 && horaireApremDebut.length() == 0 && horaireApremFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireApremFin;
                    }

                    if (!horaire.equals("") && !etDuree.getText().toString().equals("")) {
                        databaseManager.addHoraireTravail(sessionManager.getEmail(), "jeudi", horaire, etDuree.getText().toString());

                        ArrayList<ArrayList> listeDatesDebutFin = logiqueHoraires.associeJourAvecPlagesHoraires("jeudi", horaire, Integer.parseInt(etDuree.getText().toString()), 1);
                        ArrayList<String> listeDateDebut = listeDatesDebutFin.get(0);
                        ArrayList<String> listeDateFin = listeDatesDebutFin.get(1);

                        for (int i = 0; i < listeDateDebut.size(); i++) {
                            databaseManager.addRendezVous(listeDateDebut.get(i), listeDateFin.get(i), sessionManager.getEmail(), "");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Veuillez respecter un format précis: 09:00 pour les heures et soit remplir juste les horaires de la matinée, soit cella de l'après-midi, les dexu ou encore l'heure de début du matin et celle de fin de l'après-midi.", Toast.LENGTH_SHORT);
                    }
                } else {
                    joursASupprimer.add("jeudi");
                }
                if (vendredi.isChecked()) {
                    String horaire = "";
                    String horaireMatinDebut = etVendrediMatinDebut.getText().toString();
                    String horaireMatinFin = etVendrediMatinFin.getText().toString();
                    String horaireApremDebut = etVendrediApremDebut.getText().toString();
                    String horaireApremFin = etVendrediApremFin.getText().toString();

                    //Travail de formatage à faire sur les données reçues afin de les mettre sous le format: 00:00
                    if (horaireApremDebut.length() == 0 && horaireApremFin.length() == 0 && horaireMatinDebut.length() != 0 && horaireMatinFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireMatinFin;
                    } else if (horaireMatinDebut.length() == 0 && horaireMatinFin.length() == 0 && horaireApremDebut.length() != 0 && horaireApremFin.length() != 0) {
                        horaire = horaireApremDebut + "-" + horaireApremFin;
                    } else if (horaireMatinDebut.length() != 0 && horaireMatinFin.length() != 0 && horaireApremDebut.length() != 0 && horaireApremFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireMatinFin + "|" + horaireApremDebut + "-" + horaireApremFin;
                    } else if (horaireMatinDebut.length() != 0 && horaireMatinFin.length() == 0 && horaireApremDebut.length() == 0 && horaireApremFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireApremFin;
                    }

                    if (!horaire.equals("") && !etDuree.getText().toString().equals("")) {
                        databaseManager.addHoraireTravail(sessionManager.getEmail(), "vendredi", horaire, etDuree.getText().toString());

                        ArrayList<ArrayList> listeDatesDebutFin = logiqueHoraires.associeJourAvecPlagesHoraires("vendredi", horaire, Integer.parseInt(etDuree.getText().toString()), 1);
                        ArrayList<String> listeDateDebut = listeDatesDebutFin.get(0);
                        ArrayList<String> listeDateFin = listeDatesDebutFin.get(1);

                        for (int i = 0; i < listeDateDebut.size(); i++) {
                            databaseManager.addRendezVous(listeDateDebut.get(i), listeDateFin.get(i), sessionManager.getEmail(), "");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Veuillez respecter un format précis: 09:00 pour les heures et soit remplir juste les horaires de la matinée, soit cella de l'après-midi, les dexu ou encore l'heure de début du matin et celle de fin de l'après-midi.", Toast.LENGTH_SHORT);
                    }
                } else {
                    joursASupprimer.add("vendredi");
                }
                if (samedi.isChecked()) {
                    System.out.println("samedi ok");
                    String horaire = "";
                    String horaireMatinDebut = etSamediMatinDebut.getText().toString();
                    String horaireMatinFin = etSamediMatinFin.getText().toString();
                    String horaireApremDebut = etSamediApremDebut.getText().toString();
                    String horaireApremFin = etSamediApremFin.getText().toString();

                    //Travail de formatage à faire sur les données reçues afin de les mettre sous le format: 00:00
                    if (horaireApremDebut.length() == 0 && horaireApremFin.length() == 0 && horaireMatinDebut.length() != 0 && horaireMatinFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireMatinFin;
                    } else if (horaireMatinDebut.length() == 0 && horaireMatinFin.length() == 0 && horaireApremDebut.length() != 0 && horaireApremFin.length() != 0) {
                        horaire = horaireApremDebut + "-" + horaireApremFin;
                    } else if (horaireMatinDebut.length() != 0 && horaireMatinFin.length() != 0 && horaireApremDebut.length() != 0 && horaireApremFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireMatinFin + "|" + horaireApremDebut + "-" + horaireApremFin;
                    } else if (horaireMatinDebut.length() != 0 && horaireMatinFin.length() == 0 && horaireApremDebut.length() == 0 && horaireApremFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireApremFin;
                    }

                    if (!horaire.equals("") && !etDuree.getText().toString().equals("")) {
                        System.out.println("samedi bdd ok");
                        databaseManager.addHoraireTravail(sessionManager.getEmail(), "samedi", horaire, etDuree.getText().toString());

                        ArrayList<ArrayList> listeDatesDebutFin = logiqueHoraires.associeJourAvecPlagesHoraires("samedi", horaire, Integer.parseInt(etDuree.getText().toString()), 1);
                        ArrayList<String> listeDateDebut = listeDatesDebutFin.get(0);
                        ArrayList<String> listeDateFin = listeDatesDebutFin.get(1);

                        for (int i = 0; i < listeDateDebut.size(); i++) {
                            databaseManager.addRendezVous(listeDateDebut.get(i), listeDateFin.get(i), sessionManager.getEmail(), "");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Veuillez respecter un format précis: 09:00 pour les heures et soit remplir juste les horaires de la matinée, soit cella de l'après-midi, les dexu ou encore l'heure de début du matin et celle de fin de l'après-midi.", Toast.LENGTH_SHORT);
                    }
                } else {
                    joursASupprimer.add("samedi");
                }
                if (dimanche.isChecked()) {
                    String horaire = "";
                    String horaireMatinDebut = etDimancheMatinDebut.getText().toString();
                    String horaireMatinFin = etDimancheMatinFin.getText().toString();
                    String horaireApremDebut = etDimancheApremDebut.getText().toString();
                    String horaireApremFin = etDimancheApremFin.getText().toString();

                    //Travail de formatage à faire sur les données reçues afin de les mettre sous le format: 00:00
                    if (horaireApremDebut.length() == 0 && horaireApremFin.length() == 0 && horaireMatinDebut.length() != 0 && horaireMatinFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireMatinFin;
                    } else if (horaireMatinDebut.length() == 0 && horaireMatinFin.length() == 0 && horaireApremDebut.length() != 0 && horaireApremFin.length() != 0) {
                        horaire = horaireApremDebut + "-" + horaireApremFin;
                    } else if (horaireMatinDebut.length() != 0 && horaireMatinFin.length() != 0 && horaireApremDebut.length() != 0 && horaireApremFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireMatinFin + "|" + horaireApremDebut + "-" + horaireApremFin;
                    } else if (horaireMatinDebut.length() != 0 && horaireMatinFin.length() == 0 && horaireApremDebut.length() == 0 && horaireApremFin.length() != 0) {
                        horaire = horaireMatinDebut + "-" + horaireApremFin;
                    }

                    if (!horaire.equals("") && !etDuree.getText().toString().equals("")) {
                        databaseManager.addHoraireTravail(sessionManager.getEmail(), "dimanche", horaire, etDuree.getText().toString());

                        ArrayList<ArrayList> listeDatesDebutFin = logiqueHoraires.associeJourAvecPlagesHoraires("dimanche", horaire, Integer.parseInt(etDuree.getText().toString()), 1);
                        ArrayList<String> listeDateDebut = listeDatesDebutFin.get(0);
                        ArrayList<String> listeDateFin = listeDatesDebutFin.get(1);

                        for (int i = 0; i < listeDateDebut.size(); i++) {
                            databaseManager.addRendezVous(listeDateDebut.get(i), listeDateFin.get(i), sessionManager.getEmail(), "");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Veuillez respecter un format précis: 09:00 pour les heures et soit remplir juste les horaires de la matinée, soit cella de l'après-midi, les dexu ou encore l'heure de début du matin et celle de fin de l'après-midi.", Toast.LENGTH_SHORT);
                    }
                } else {
                    joursASupprimer.add("dimanche");
                }

                if (joursASupprimer.size() != 0) {
                    for (int i = 0; i < joursASupprimer.size(); i++) {
                        if (i+1 == joursASupprimer.size()) {
                            databaseManager.deleteHoraireTravailLast(sessionManager.getEmail(), joursASupprimer.get(i), listenerDeleteHoraireTravail);
                        } else {
                            databaseManager.deleteHoraireTravail(sessionManager.getEmail(), joursASupprimer.get(i));
                        }
                    }
                } else {
                    Intent profilMedecin = new Intent(ManageHorairesMedecin.this, ProfilMedecin.class);
                    startActivity(profilMedecin);
                    finish();
                }
            }
        });

    }
}