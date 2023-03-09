package com.example.formulairebeau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccueilPatient extends AppCompatActivity {

    private SessionManager sessionManager;
    private Button btnLogout;

    private TextView test;
    private BottomNavigationView navbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_patient);

        sessionManager = new SessionManager(this);

        btnLogout = findViewById(R.id.btnLogout);
        test = findViewById(R.id.id2);
        navbar = findViewById(R.id.bottomNavigationView);

        sessionManager.modifyUser("email", "test123");
        test.setText(sessionManager.getEmail());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        BottomNavigationView.OnNavigationItemSelectedListener



    }
}