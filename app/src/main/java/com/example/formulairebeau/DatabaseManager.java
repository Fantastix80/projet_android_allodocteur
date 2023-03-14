package com.example.formulairebeau;

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
}
