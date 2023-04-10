package com.example.projetandroidallodocteurnew.rdv.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandroidallodocteurnew.R;

import java.util.List;

public class MyAdapterHoraire extends RecyclerView.Adapter<MyViewHolderHoraire> {

    Context context;
    List<ItemHoraire> items;

    public MyAdapterHoraire(Context context, List<ItemHoraire> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolderHoraire onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderHoraire(LayoutInflater.from(context).inflate(R.layout.item_horaire_recyclerview, parent, false), context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderHoraire holder, int position) {
        holder.tvHoraire.setText(items.get(position).getHoraire());
        holder.tvDateDebut.setText(items.get(position).getDateDebut());
        holder.tvDateFin.setText(items.get(position).getDateFin());
        holder.tvEmail.setText(items.get(position).getEmailMedecin());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
