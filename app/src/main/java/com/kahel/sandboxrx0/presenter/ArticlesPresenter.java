package com.kahel.sandboxrx0.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.kahel.sandboxrx0.model.ArticlesModel;
import com.kahel.sandboxrx0.parameters.ArticlesParameters;
import com.kahel.sandboxrx0.parameters.GlobalParameters;
import com.kahel.sandboxrx0.utils.VolleyQueue;
import com.kahel.sandboxrx0.view.Articles;

import org.json.JSONObject;

import static com.android.volley.Request.Method.GET;

/**
 * Created by Mark on 8/18/2015.
 */
public class ArticlesPresenter {

    Articles view;
    Context context;
    ArticlesParameters parameters;

    public ArticlesPresenter(Articles view, Context context, ArticlesParameters parameters) {
        this.view = view;
        this.context = context;
        this.parameters = parameters;
    }

    public void getArticles(){
        GlobalParameters globalParameters = new GlobalParameters();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GET, globalParameters.getBaseurl().concat(parameters.getArticles()),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArticlesModel articlesModel;
                        Gson gson = new Gson();
                        articlesModel = gson.fromJson(response.toString(), ArticlesModel.class);
                        view.displayArticles(articlesModel);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("Response", "Error");
            }
        });

        // Access the RequestQueue through your singleton class.
        VolleyQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }
}
