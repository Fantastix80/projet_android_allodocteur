package com.example.projetandroidallodocteurnew.mesRDV.medecin.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandroidallodocteurnew.R;

import java.util.List;

public class MyAdapterHoraire2 extends RecyclerView.Adapter<MyViewHolderHoraire2> {

    Context context;
    List<ItemHoraire2> items;

    public MyAdapterHoraire2(Context context, List<ItemHoraire2> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolderHoraire2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderHoraire2(LayoutInflater.from(context).inflate(R.layout.item_horaire_recyclerview_rdv_potentiel, parent, false), context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderHoraire2 holder, int position) {
        holder.tvHoraire.setText(items.get(position).getHoraire());
        holder.tvDateDebut.setText(items.get(position).getDateDebut());
        holder.tvDateFin.setText(items.get(position).getDateFin());
        holder.tvOccupe.setText(items.get(position).getOccupe());
        holder.tvEmail.setText(items.get(position).getEmailMedecin());

        if (items.get(position).getOccupe().equals("1")) {
            holder.cardHoraire.setBackgroundColor(Color.parseColor("#DC3545"));
            holder.tvHoraire.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
