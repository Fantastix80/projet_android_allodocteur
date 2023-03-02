package com.example.formulairebeau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class FormulaireConnexion extends AppCompatActivity {

    // On initialise les variables
    private TextView tvDontHaveAccount, tvDisplayErrors;
    private EditText emailEditText, mdpEditText;
    private String email, mdp;
    private Button btnSubmitConnexion;

    private DatabaseManager databaseManager;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_connexion);

        // On définie les variables avec les View
        tvDontHaveAccount = findViewById(R.id.tvDontHaveAccount);
        tvDisplayErrors = findViewById(R.id.tvDisplayErrors);
        emailEditText = findViewById(R.id.etEmailConnexion);
        mdpEditText = findViewById(R.id.etMdpConnexion);
        btnSubmitConnexion = findViewById(R.id.btnSubmitConnexion);

        databaseManager = new DatabaseManager(getApplicationContext());
        sessionManager = new SessionManager(this);

        // OnClickListener pour le bouton "Je n'ai pas encore de compte"
        tvDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(getApplicationContext(), FormulaireInscription.class);
                startActivity(myIntent);
            }
        });

        //on définie notre listener qui va s'activer lorsque l'api aura répondu à notre requête
        DatabaseManager.VolleyResponseListener listener = new DatabaseManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(FormulaireConnexion.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                String success = null;
                String error = "";
                String isMedecin;
                String email;

                try {
                    success = response.getString("success");

                    if (success.equals("true")) {
                        isMedecin = response.getString("isMedecin");
                        email = response.getString("email");
                        sessionManager.insertUser(email, isMedecin);

                        if (isMedecin.equals("1")) {
                            Intent accueilMedecin = new Intent(getApplicationContext(), AccueilMedecin.class);
                            startActivity(accueilMedecin);
                            finish();
                        } else {
                            Intent accueilPatient = new Intent(getApplicationContext(), AccueilPatient.class);
                            startActivity(accueilPatient);
                            finish();
                        }
                    } else {
                        error = response.getString("error");
                        tvDisplayErrors.setVisibility(View.VISIBLE);
                        tvDisplayErrors.setText(error);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        // OnClickListener pour le bouton "Se connecter"
        btnSubmitConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On récupère les donnéex entrées dans les champs texte
                email = emailEditText.getText().toString();
                mdp = mdpEditText.getText().toString();

                //On appelle la méthode connectUser pour qu'elle se charge de la requête à l'api
                //On lui donne aussi la variable "listener" qui nous permettra de récuérer la réponse de l'api
                databaseManager.connectUser(email, mdp, listener);
            }
        });

    }
}