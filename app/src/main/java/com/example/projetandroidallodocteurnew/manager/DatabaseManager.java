package com.example.projetandroidallodocteurnew.manager;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    private Context context;
    public RequestQueue queue;

    public DatabaseManager(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    //Cette méthode permet de récupérer les réponses de l'api
    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(JSONObject response);
    }

    public void createAccount(String prenom, String nom, String email, String mdp, String cmdp, String isMedecin, final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/createAccount.php";

        Map<String, String> params = new HashMap<>();
        params.put("prenom", prenom);
        params.put("nom", nom);
        params.put("email", email);
        params.put("mdp", mdp);
        params.put("cmdp", cmdp);
        params.put("isMedecin", isMedecin);
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void connectUser(String email, String mdp, final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/connectUser.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("mdp", mdp);
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void updateLastConnectionDate(String email, String dateDerniereConnexion) {
        String url = "https://helderalves.fr/api/actions/updateLastConnectionDate.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("dateDerniereConnexion", dateDerniereConnexion);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    public void getAllMedecin(final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/getAllMedecin.php";

        Map<String, String> params = new HashMap<>();
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void getOneMedecin(String email, final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/getOneMedecin.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void updateUtilisateur(String email, String sexe, String nom, String prenom, String dateNaissance, String adresse, String codePostal, String ville, String telephone, String imageProfil) {
        String url = "https://helderalves.fr/api/actions/updateUtilisateur.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("sexe", sexe);
        params.put("nom", nom);
        params.put("prenom", prenom);
        params.put("dateNaissance", dateNaissance);
        params.put("adresse", adresse);
        params.put("codePostal", codePostal);
        params.put("ville", ville);
        params.put("telephone", telephone);
        params.put("imageProfil", imageProfil);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    public void deleteAccount(String email) {
        String url = "https://helderalves.fr/api/actions/deleteAccount.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    public void actionsMedecinTraitant(String action, String emailPatient, String emailMedecin, final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/actionsMedecinTraitant.php";

        Map<String, String> params = new HashMap<>();
        params.put("action", action);
        params.put("emailPatient", emailPatient);
        params.put("emailMedecin", emailMedecin);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void addRendezVous(String dateDebut, String dateFin, String idMedecin, String idPatient) {
        String url = "https://helderalves.fr/api/actions/addRendezVous.php";

        Map<String, String> params = new HashMap<>();
        params.put("dateDebut", dateDebut);
        params.put("dateFin", dateFin);
        params.put("idMedecin", idMedecin);
        params.put("idPatient", idPatient);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    public void UpdateMedecinInfo(String email, String specialite, String adressePro, String codePostalPro, String villePro, String presentation, String tarifs, String moyensPaiement){
        String url = "https://helderalves.fr/api/actions/updateMedecinInfo.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("specialite", specialite);
        params.put("adressePro", adressePro);
        params.put("codePostalPro", codePostalPro);
        params.put("villePro", villePro);
        params.put("presentation", presentation);
        params.put("tarifs", tarifs);
        params.put("moyensPaiements", moyensPaiement);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void getOneUser(String email, final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/getOneUser.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void getAllDatesRDV(String email, String indicateur, final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/getAllDatesRDV.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("indicateur", indicateur);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void getAllHoraires(String date, String email, String indicateur, final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/getAllHoraires.php";

        Map<String, String> params = new HashMap<>();
        params.put("date", date);
        params.put("email", email);
        params.put("indicateur", indicateur);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void prendreRDV(String dateDebut, String dateFin, String emailMedecin, String emailPatient, final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/prendreRDV.php";

        Map<String, String> params = new HashMap<>();
        params.put("dateDebut", dateDebut);
        params.put("dateFin", dateFin);
        params.put("emailMedecin", emailMedecin);
        params.put("emailPatient", emailPatient);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void getRDVAVenir(String type, String email, final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/getRDVAVenir.php";

        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("email", email);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void getRDVPasse(String type, String email, final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/getRDVPasse.php";

        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("email", email);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void deleteDateRendezVous(String date, String email) {
        String url = "https://helderalves.fr/api/actions/deleteDateRendezVous.php";

        Map<String, String> params = new HashMap<>();
        params.put("date", date);
        params.put("email", email);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    public void deleteHoraireRendezVous(String dateDebut, String dateFin, String email, final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/deleteHoraireRendezVous.php";

        Map<String, String> params = new HashMap<>();
        params.put("dateDebut", dateDebut);
        params.put("dateFin", dateFin);
        params.put("email", email);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void getHoraireTravail(String email, final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/getHoraireTravail.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void addHoraireTravail(String idMedecin, String jour, String horaire, String duree) {
        String url = "https://helderalves.fr/api/actions/addHoraireTravail.php";

        Map<String, String> params = new HashMap<>();
        params.put("idMedecin", idMedecin);
        params.put("jour", jour);
        params.put("horaire", horaire);
        params.put("duree", duree);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void deleteHoraireTravail(String email, String jour) {
        String url = "https://helderalves.fr/api/actions/deleteHoraireTravail.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("jour", jour);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void deleteHoraireTravailLast(String email, String jour, final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/deleteHoraireTravail.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("jour", jour);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void deleteHoraireTravailAll(String email, final VolleyResponseListener listener) {
        String url = "https://helderalves.fr/api/actions/deleteHoraireTravailAll.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }
}
