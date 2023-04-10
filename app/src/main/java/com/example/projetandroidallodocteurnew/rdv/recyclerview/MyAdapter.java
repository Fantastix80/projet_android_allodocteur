package com.example.projetandroidallodocteurnew.rdv.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    DatabaseManager databaseManager;
    Context context;
    List<Item> items;

    public MyAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
        this.databaseManager = new DatabaseManager(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false), this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvDate.setText(items.get(position).getDate());
        holder.tvEmail.setText(items.get(position).getEmail());
        RecyclerView recyclerViewHoraire = holder.recyclerViewHoraire;

        String emailMedecin = items.get(position).getEmail();

        //Mise en place d'un listener pour venir écouter la réponse de la BDD
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

                        JSONArray horaires = response.getJSONArray("horaires");

                        List<ItemHoraire> items = new ArrayList<ItemHoraire>();

                        for (int i = 0; i < horaires.length(); i++) {
                            JSONObject horaire = horaires.getJSONObject(i);

                            items.add(new ItemHoraire(horaire.getString("dateDebut"), horaire.getString("dateFin"), horaire.getString("horaire"), emailMedecin));
                        }

                        recyclerViewHoraire.setLayoutManager(new GridLayoutManager(context, 3));
                        recyclerViewHoraire.setHasFixedSize(true);
                        recyclerViewHoraire.setAdapter(new MyAdapterHoraire(context, items));

                    } else {
                        //Afficher l'erreur dans une textview prévu à cet effet, où rediriger l'utilisateur
                        //vers une autre page et afficher un Toast.
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        String dateTemp = items.get(position).getDate();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateUnformated = null;
        try {
            dateUnformated = inputFormat.parse(dateTemp);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String dateFormate = outputFormat.format(dateUnformated);

        //Appel à l'API pour récupérer les horaires
        databaseManager.getAllHoraires(dateFormate, items.get(position).getEmail(), "", listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
