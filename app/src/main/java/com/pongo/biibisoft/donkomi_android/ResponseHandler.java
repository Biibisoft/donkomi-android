package com.pongo.biibisoft.donkomi_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseHandler {
  private JSONObject response;
//  private JSONObject error;

  public ResponseHandler() {
  }


  public ResponseHandler(JSONObject response) {
    this.response = response;
  }

  public boolean isSuccessful() throws JSONException {
    return (boolean) this.response.get("success");
  }

  public JSONObject getError() throws JSONException {
    return  (JSONObject) this.response.get("error");
  }

  public boolean hasError() throws JSONException {
    JSONObject error = this.getError();
    return (Boolean) error.get("status");
  }

  public String getErrorMessage() throws JSONException {
    JSONObject error = this.getError();
    return (String) error.get("message");
  }

  public JSONArray[] getData() throws JSONException {
    return (JSONArray[]) response.get("data");
  }
  public JSONObject getDataObject() throws JSONException {
    return (JSONObject) response.get("data");
  }
}

