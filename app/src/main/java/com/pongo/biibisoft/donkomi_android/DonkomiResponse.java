package com.pongo.biibisoft.donkomi_android;

import org.json.JSONArray;

public class DonkomiResponse {
  private JSONArray data;
  private boolean success = true;
  private DonkomiResponseError error;
  public DonkomiResponse(){}

  public JSONArray getData() {
    return data;
  }

  public void setData(JSONArray data) {
    this.data = data;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public DonkomiResponseError getError() {
    return error;
  }

  public void setError(DonkomiResponseError error) {
    this.error = error;
  }
}
