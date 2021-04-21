package com.pongo.biibisoft.donkomi_android;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class InternetExplorer {
  public static final String POST = "POST";
  public static final String GET = "GET";
  private final RequestQueue handler;
  private JSONObject data = null;
  private String method = InternetExplorer.POST;
  private Context context;
  private Boolean expectsArray  = true;
  Gson gson = new Gson();
  private Class<?> expectedDataType;


  public InternetExplorer(Context context) {
    this.context = context;
    this.handler = Volley.newRequestQueue(context);
  }


  public void setExpectedDataType(Class<?> expectedDataType) {
    this.expectedDataType = expectedDataType;
  }

  public Boolean isExpectingArray() {
    return expectsArray;
  }

  public void expectsArray(Boolean expectsArray) {
    this.expectsArray = expectsArray;
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

  public void runAndFindData(String URL, ResultWithData explorer) {
    JsonObjectRequest req = new JsonObjectRequest(this.getMethod(), URL, this.data, new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        ResponseHandler responseHandler = new ResponseHandler(response);
        try {
          if (responseHandler.hasError()) explorer.error(responseHandler.getErrorMessage());
          else{
            if(isExpectingArray()) {
              if (expectedDataType != null) explorer.getDataArray(transformToExpected(responseHandler.getData()));
              else explorer.getDataArray(responseHandler.getData());
            }
            else {
              if(expectedDataType != null) explorer.getData(transformToExpected(responseHandler.getDataObject()));
              explorer.getData(responseHandler.getDataObject());
            }
          }

        } catch (JSONException e) {
          explorer.error(e.getMessage());
          e.printStackTrace();
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

  public String endSlash(String url) {
    return url + "/";
  }

  public Object transformToExpected(JSONArray data){
    return gson.fromJson(data.toString(), (Type) expectedDataType);
  }
  public Object transformToExpected(JSONObject data){
    return gson.fromJson(data.toString(), (Type) expectedDataType);
  }
}

interface Result {
  void isOkay(JSONObject response) throws JSONException;

  void error(String error);
}

interface ResultWithData {
  void isOkay(JSONObject response) throws JSONException;

  /**
   * Used when backend is presenting a json object
   * @param data A JSON Object of any kind of data type
   */
  void getData(Object data);

  /**
   * Used when backend is presenting an array
   * @param data JSONArray of any kind of data type
   */
  void getDataArray(Object data);

  void error(String error);
}
