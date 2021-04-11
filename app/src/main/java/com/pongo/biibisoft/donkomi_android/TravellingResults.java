package com.pongo.biibisoft.donkomi_android;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TravellingResults {
  private Boolean status = true;
  private ArrayList<Object> data = new ArrayList<>() ;
  private final JSONObject fieldsAndData = new JSONObject();


  public Boolean isOkay(){
    return status;
  }
  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public ArrayList<Object> getData() {
    return data;
  }

  public JSONObject getDataWithValues(){
    return fieldsAndData;
  }

  public void addData(String field, Object value) throws JSONException {
    fieldsAndData.put(field, value);
  }

  public void setData(ArrayList<Object> data) {
    this.data = data;
  }
  public void addToData(Object o){
    data.add(o);
  }
}
