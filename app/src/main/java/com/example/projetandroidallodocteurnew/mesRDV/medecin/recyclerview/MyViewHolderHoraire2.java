package com.example.projetandroidallodocteurnew.mesRDV.medecin.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.mesRDV.medecin.MesRDVMedecin;
import com.example.projetandroidallodocteurnew.rdv.PaiementRDV;

import org.json.JSONObject;

public class MyViewHolderHoraire2 extends RecyclerView.ViewHolder {

    private DatabaseManager databaseManager;
    TextView tvHoraire, tvDateDebut, tvDateFin, tvOccupe, tvEmail;
    CardView cardHoraire;

    public MyViewHolderHoraire2(@NonNull View itemView, Context context) {
        super(itemView);

        databaseManager = new DatabaseManager(context);

        tvHoraire = itemView.findViewById(R.id.tvHoraire);
        tvDateDebut = itemView.findViewById(R.id.tvDateDebut);
        tvDateFin = itemView.findViewById(R.id.tvDateFin);
        tvOccupe = itemView.findViewById(R.id.tvOccupe);
        tvEmail = itemView.findViewById(R.id.tvEmail);

        cardHoraire = itemView.findViewById(R.id.cardHoraire);

        DatabaseManager.VolleyResponseListener listener = new DatabaseManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(JSONObject response) {
                Intent refreshPage = new Intent(context, MesRDVMedecin.class);
                refreshPage.putExtra("fragment", "potentiel");
                context.startActivity(refreshPage);
            }
        };

        //OnClickListener sur les horaires
        itemView.findViewById(R.id.tvHoraire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvDateDebut = itemView.findViewById(R.id.tvDateDebut);
                TextView tvDateFin = itemView.findViewById(R.id.tvDateFin);
                TextView tvEmail = itemView.findViewById(R.id.tvEmail);
                System.out.println("j'ai été cliqué: " + tvDateDebut.getText().toString() + " | " + tvDateFin.getText().toString() + " | " + tvEmail.getText().toString());
            }
        });

        //OnClickListener sur la poubelle des heures
        itemView.findViewById(R.id.deleteHoraire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvDateDebut = itemView.findViewById(R.id.tvDateDebut);
                TextView tvDateFin = itemView.findViewById(R.id.tvDateFin);
                TextView tvEmail = itemView.findViewById(R.id.tvEmail);

                databaseManager.deleteHoraireRendezVous(tvDateDebut.getText().toString(), tvDateFin.getText().toString(), tvEmail.getText().toString(), listener);
            }
        });
    }
}
