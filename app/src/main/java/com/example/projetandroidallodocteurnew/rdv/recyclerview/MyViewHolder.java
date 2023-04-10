package com.example.projetandroidallodocteurnew.rdv.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.manager.DatabaseManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyViewHolder extends RecyclerView.ViewHolder {

    DatabaseManager databaseManager;
    TextView tvDate, tvEmail;
    RecyclerView recyclerViewHoraire;

    public MyViewHolder(@NonNull View itemView, Context context) {
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

    }
}
