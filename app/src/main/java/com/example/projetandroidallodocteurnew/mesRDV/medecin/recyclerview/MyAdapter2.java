package com.example.projetandroidallodocteurnew.mesRDV.medecin.recyclerview;

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

public class MyAdapter2 extends RecyclerView.Adapter<MyViewHolder2> {

    DatabaseManager databaseManager;
    Context context;
    List<Item2> items;

    public MyAdapter2(Context context, List<Item2> items) {
        this.context = context;
        this.items = items;
        this.databaseManager = new DatabaseManager(context);
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder2(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_rdv_potentiel, parent, false), this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
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

                        List<ItemHoraire2> items = new ArrayList<ItemHoraire2>();

                        for (int i = 0; i < horaires.length(); i++) {
                            JSONObject horaire = horaires.getJSONObject(i);

                            items.add(new ItemHoraire2(horaire.getString("dateDebut"), horaire.getString("dateFin"), horaire.getString("horaire"), horaire.getString("occupe"), emailMedecin));
                        }

                        recyclerViewHoraire.setLayoutManager(new GridLayoutManager(context, 3));
                        recyclerViewHoraire.setHasFixedSize(true);
                        MyAdapterHoraire2 myAdapterHoraire2 = new MyAdapterHoraire2(context, items);
                        recyclerViewHoraire.setAdapter(myAdapterHoraire2);
                        myAdapterHoraire2.notifyDataSetChanged();

                    } else {
                        //Afficher l'erreur dans une textview prévu à cet effet, où rediriger l'utilisateur
                        //vers une autre page et afficher un Toast.
                        System.out.println(response);
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
        databaseManager.getAllHoraires(dateFormate, items.get(position).getEmail(), "potentiel", listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
