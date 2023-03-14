package com.example.formulairebeau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class FormulaireInscription extends AppCompatActivity {

    // On initialise les variables
    private TextView tvAlreadyHaveAccount, tvDisplayErrors;
    private Button btnSubmitInscription;
    private EditText prenomEditText, nomEditText, emailEditText, mdpEditText, cmdpEditText;
    private Spinner isMedecinSpinner;
    private String prenom, nom, email, mdp, cmdp, isMedecin;

    private DatabaseManager databaseManager;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_inscription);

        // On définie les variables avec les View
        tvAlreadyHaveAccount = findViewById(R.id.tvAlreadyHaveAccount);
        tvDisplayErrors = findViewById(R.id.tvDisplayErrors);
        btnSubmitInscription = findViewById(R.id.btnSubmitInscription);
        prenomEditText = findViewById(R.id.etPrenomInscription);
        nomEditText = findViewById(R.id.etNomInscription);
        emailEditText = findViewById(R.id.etEmailInscription);
        mdpEditText = findViewById(R.id.etMdpInscription);
        cmdpEditText = findViewById(R.id.etCmdpInscription);
        isMedecinSpinner = findViewById(R.id.spinnerIsMedecin);


        databaseManager = new DatabaseManager(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        // OnClickListener pour le bouton "J'ai déjà un compte"
        tvAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), FormulaireConnexion.class);
                startActivity(myIntent);
            }
        });

        //on définie notre listener qui va s'activer lorsque l'api aura répondu à notre requête
        DatabaseManager.VolleyResponseListener listener = new DatabaseManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(FormulaireInscription.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                String success = null;
                String error = "";
                String isMedecin;
                String email;
                String nom;
                String prenom;
                String dateCreation;
                String dateDerniereConnexion;

                try {
                    success = response.getString("success");

                    if (success.equals("true")) {
                        isMedecin = response.getString("isMedecin");
                        email = response.getString("email");
                        nom = response.getString("nom");
                        prenom = response.getString("prenom");
                        dateCreation = response.getString("dateCreation");
                        dateDerniereConnexion = response.getString("dateDerniereConnexion");
                        sessionManager.insertUser(email, "", nom, prenom, "", isMedecin, "", "", "", dateCreation, dateDerniereConnexion);

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

        // OnClickListener pour le bouton "S'inscrire"
        btnSubmitInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On récupère les donnéex entrées dans les champs texte
                prenom = prenomEditText.getText().toString();
                nom = nomEditText.getText().toString();
                email = emailEditText.getText().toString();
                mdp = mdpEditText.getText().toString();
                cmdp = cmdpEditText.getText().toString();

                //On récupère la valeur associé au choix de l'utilisateur (0 ou 1)
                int spinner_pos = isMedecinSpinner.getSelectedItemPosition();
                String[] values = getResources().getStringArray(R.array.array_name_values);
                int value = Integer.valueOf(values[spinner_pos]);
                isMedecin = Integer.toString(value);

                //On appelle la méthode createAccount pour qu'elle se charge de la requête à l'api
                //On lui donne aussi la variable "listener" qui nous permettra de récuérer la réponse de l'api
                databaseManager.createAccount(prenom, nom, email, mdp, cmdp, isMedecin, listener);
            }
        });
    }
}