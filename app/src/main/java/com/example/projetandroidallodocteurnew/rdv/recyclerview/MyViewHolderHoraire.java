package com.example.projetandroidallodocteurnew.rdv.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.rdv.PaiementRDV;

public class MyViewHolderHoraire extends RecyclerView.ViewHolder {

    TextView tvHoraire, tvDateDebut, tvDateFin, tvEmail;

    public MyViewHolderHoraire(@NonNull View itemView, Context context) {
        super(itemView);

        tvHoraire = itemView.findViewById(R.id.tvHoraire);
        tvDateDebut = itemView.findViewById(R.id.tvDateDebut);
        tvDateFin = itemView.findViewById(R.id.tvDateFin);
        tvEmail = itemView.findViewById(R.id.tvEmail);

        //OnClickListener sur les horaires
        itemView.findViewById(R.id.tvHoraire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvDateDebut = itemView.findViewById(R.id.tvDateDebut);
                TextView tvDateFin = itemView.findViewById(R.id.tvDateFin);
                TextView tvEmail = itemView.findViewById(R.id.tvEmail);
                System.out.println("j'ai été cliqué: " + tvDateDebut.getText().toString() + " | " + tvDateFin.getText().toString() + " | " + tvEmail.getText().toString());

                //Redirigé vers une page de paiement
                Intent paiement = new Intent(context, PaiementRDV.class);
                paiement.putExtra("dateDebut", tvDateDebut.getText().toString());
                paiement.putExtra("dateFin", tvDateFin.getText().toString());
                paiement.putExtra("email", tvEmail.getText().toString());
                context.startActivity(paiement);
            }
        });

    }
}
