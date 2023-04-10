package com.example.projetandroidallodocteurnew.mesRDV.medecin.recyclerview;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.mesRDV.medecin.MesRDVMedecin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyViewHolder2 extends RecyclerView.ViewHolder {

    DatabaseManager databaseManager;
    TextView tvDate, tvEmail;
    RecyclerView recyclerViewHoraire;
    TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private int heureNonFormate, minuteNonFormate;
    String dateFormate, horaireDebut, horaireFin;

    public MyViewHolder2(@NonNull View itemView, Context context) {
        super(itemView);

        databaseManager = new DatabaseManager(context);

        tvDate = itemView.findViewById(R.id.tvDate);
        tvEmail = itemView.findViewById(R.id.tvEmail);
        recyclerViewHoraire = itemView.findViewById(R.id.recyclerViewHoraire);

        //OnClickListener des boutons des items de la recycler view
        itemView.findViewById(R.id.voirHoraires).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView cacherHoraires = itemView.findViewById(R.id.cacherHoraires);
                ImageView voirHoraires = itemView.findViewById(R.id.voirHoraires);
                RelativeLayout timeContainer = itemView.findViewById(R.id.timeContainer);
                View viewBarre = itemView.findViewById(R.id.viewBarre);

                voirHoraires.setVisibility(View.GONE);
                cacherHoraires.setVisibility(View.VISIBLE);

                timeContainer.setVisibility(View.VISIBLE);
                viewBarre.setVisibility(View.VISIBLE);
            }
        });

        itemView.findViewById(R.id.cacherHoraires).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView cacherHoraires = itemView.findViewById(R.id.cacherHoraires);
                ImageView voirHoraires = itemView.findViewById(R.id.voirHoraires);
                RelativeLayout timeContainer = itemView.findViewById(R.id.timeContainer);
                View viewBarre = itemView.findViewById(R.id.viewBarre);

                voirHoraires.setVisibility(View.VISIBLE);
                cacherHoraires.setVisibility(View.GONE);

                timeContainer.setVisibility(View.GONE);
                viewBarre.setVisibility(View.GONE);
            }
        });

        itemView.findViewById(R.id.add_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateTemp = tvDate.getText().toString();
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateUnformated = null;
                try {
                    dateUnformated = inputFormat.parse(dateTemp);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                dateFormate = outputFormat.format(dateUnformated);

                //Listener pour récupérer la durée d'une séance
                DatabaseManager.VolleyResponseListener listenerDureeRDV = new DatabaseManager.VolleyResponseListener() {
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

                                JSONArray jours = response.getJSONArray("horaireTravail");
                                JSONObject jour = jours.getJSONObject(0);

                                int duree = Integer.parseInt(jour.getString("duree"));

                                int heureDebut = Integer.parseInt(horaireDebut.split(":")[0]);
                                int minuteDebut = Integer.parseInt(horaireDebut.split(":")[1]);

                                minuteDebut += duree;
                                if (minuteDebut >= 60) {
                                    heureDebut += 1;
                                    minuteDebut -= 60;
                                }

                                String heureFin = "0" + Integer.toString(heureDebut);
                                String minuteFin = "0" + Integer.toString(minuteDebut);
                                horaireFin = heureFin.substring(heureFin.length() - 2) + ":" + minuteFin.substring(minuteFin.length() - 2) + ":00";

                                String dateDebut = dateFormate + " " + horaireDebut;
                                String dateFin = dateFormate + " " + horaireFin;

                                databaseManager.addRendezVous(dateDebut, dateFin, tvEmail.getText().toString(), "");

                                Intent refreshPage = new Intent(context, MesRDVMedecin.class);
                                refreshPage.putExtra("fragment", "potentiel");
                                context.startActivity(refreshPage);

                            } else {
                                System.out.println(response);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                //Listener pour récupérer l'horaire entré par le médecin
                onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int heureSelectionne, int minuteSelectionne) {
                        heureNonFormate = heureSelectionne;
                        minuteNonFormate = minuteSelectionne;

                        String heureFormate = "0" + Integer.toString(heureNonFormate);
                        String minuteFormate = "0" + Integer.toString(minuteNonFormate);
                        String horaireFormate = heureFormate.substring(heureFormate.length() - 2) + ":" + minuteFormate.substring(minuteFormate.length() - 2) + ":00";

                        horaireDebut = horaireFormate;
                        databaseManager.getHoraireTravail(tvEmail.getText().toString(), listenerDureeRDV);
                    }
                };

                //Show pop-up pour entrer une horaire
                popUpTimePicker(context);
            }
        });

        itemView.findViewById(R.id.delete_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateTemp = tvDate.getText().toString();
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateUnformated = null;
                try {
                    dateUnformated = inputFormat.parse(dateTemp);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                String dateFormate = outputFormat.format(dateUnformated);

                databaseManager.deleteDateRendezVous(dateFormate, tvEmail.getText().toString());

                Intent refreshPage = new Intent(context, MesRDVMedecin.class);
                refreshPage.putExtra("fragment", "potentiel");
                context.startActivity(refreshPage);
            }
        });
    }

    public void popUpTimePicker(Context context) {

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, style, onTimeSetListener, heureNonFormate, minuteNonFormate, true);

        timePickerDialog.setTitle("Définissez l'heure de début du rendez-vous");
        timePickerDialog.show();
    }
}
