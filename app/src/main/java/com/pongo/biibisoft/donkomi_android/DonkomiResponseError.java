package com.pongo.biibisoft.donkomi_android;

import org.json.JSONException;
import org.json.JSONObject;

public class DonkomiResponseError {
  private boolean status = false;
  private String message;


  public DonkomiResponseError(JSONObject error) throws JSONException {
    this.status = (boolean) error.get("status");
    this.message = error.getString("status");
  }

  public boolean isStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }
}
