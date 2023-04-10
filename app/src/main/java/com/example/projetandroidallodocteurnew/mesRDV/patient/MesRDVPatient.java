package com.example.projetandroidallodocteurnew.mesRDV.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.accueil.AccueilMedecin;
import com.example.projetandroidallodocteurnew.accueil.AccueilPatient;
import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.manager.SessionManager;
import com.example.projetandroidallodocteurnew.mesPatients.MesPatients;
import com.example.projetandroidallodocteurnew.mesRDV.medecin.AVenirMedecin;
import com.example.projetandroidallodocteurnew.mesRDV.medecin.PasseMedecin;
import com.example.projetandroidallodocteurnew.mesRDV.medecin.PotentielMedecin;
import com.example.projetandroidallodocteurnew.mesRDV.medecin.VPAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

public class MesRDVPatient extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private SessionManager sessionManager;
    private TabLayout tlMesRDV;
    private ViewPager vpMesRDV;
    private Button btnFlecheRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_rdvpatient);

        databaseManager = new DatabaseManager(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        tlMesRDV = findViewById(R.id.tlMesRDV);
        vpMesRDV = findViewById(R.id.vpMesRDV);

        btnFlecheRetour = findViewById(R.id.btnFlecheRetour);

        tlMesRDV.setupWithViewPager(vpMesRDV);

        com.example.projetandroidallodocteurnew.mesRDV.medecin.VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new AVenirPatient(), "A VENIR");
        vpAdapter.addFragment(new PassePatient(), "PASSE(S)");
        vpMesRDV.setAdapter(vpAdapter);



        btnFlecheRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retour = new Intent(MesRDVPatient.this, AccueilPatient.class);
                startActivity(retour);
                finish();
            }
        });
    }
}