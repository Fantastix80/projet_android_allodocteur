package com.example.projetandroidallodocteurnew.mesRDV.medecin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.projetandroidallodocteurnew.R;
import com.example.projetandroidallodocteurnew.accueil.AccueilMedecin;
import com.example.projetandroidallodocteurnew.manager.DatabaseManager;
import com.example.projetandroidallodocteurnew.manager.SessionManager;
import com.example.projetandroidallodocteurnew.mesPatients.MesPatients;
import com.example.projetandroidallodocteurnew.mesPatients.MesPatientsInfos;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

public class MesRDVMedecin extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private SessionManager sessionManager;
    private TabLayout tlMesRDV;
    private ViewPager vpMesRDV;
    private Button btnFlecheRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_rdvmedecin);

        databaseManager = new DatabaseManager(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        tlMesRDV = findViewById(R.id.tlMesRDV);
        vpMesRDV = findViewById(R.id.vpMesRDV);

        btnFlecheRetour = findViewById(R.id.btnFlecheRetour);

        tlMesRDV.setupWithViewPager(vpMesRDV);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new AVenirMedecin(), "A VENIR");
        vpAdapter.addFragment(new PasseMedecin(), "PASSE(S)");
        vpAdapter.addFragment(new PotentielMedecin(), "POTENTIEL");
        vpMesRDV.setAdapter(vpAdapter);

        //Récupérer les extras
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String fragment = extras.getString("fragment");
            if (fragment.equals("potentiel")) {

                vpMesRDV.setCurrentItem(2);
            }

        }


        btnFlecheRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retour = new Intent(MesRDVMedecin.this, AccueilMedecin.class);
                startActivity(retour);
                finish();
            }
        });
    }
}