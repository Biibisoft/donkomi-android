package com.pongo.biibisoft.donkomi_android;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class InternetExplorer {
  public static final String POST = "POST";
  public static final String GET = "GET";
  private final RequestQueue handler;
  private JSONObject data = null;
  private String method = InternetExplorer.POST;
  private Context context;

  public InternetExplorer(Context context) {
    this.context = context;
    this.handler = Volley.newRequestQueue(context);
  }

  public void setData(JSONObject data) {
    this.data = data;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public int getMethod() {
    if (this.method.equals(InternetExplorer.POST)) return Request.Method.POST;
    return Request.Method.GET;
  }

  public void run(String URL, Result explorer) {
    JsonObjectRequest req = new JsonObjectRequest(this.getMethod(), URL, this.data, new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        try {
          explorer.isOkay(response);
        } catch (JSONException e) {
          e.printStackTrace();
          explorer.error(e.getMessage());
        }
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        if (error != null) explorer.error(error.getMessage());
        else explorer.error("Something happened! Request timeout, or unknown error while roaming");
      }
    });
    if (this.handler != null) handler.add(req);
    else {
      explorer.error("Request handler is not setup properly!");
    }
  }
}

interface Result {
  void isOkay(JSONObject response) throws JSONException;

  void error(String error);
}
