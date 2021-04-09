package com.pongo.biibisoft.donkomi_android;

import java.util.ArrayList;

public class TravellingResults {
  private Boolean status = true;
  private ArrayList<Object> data = new ArrayList<>() ;


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

  public void setData(ArrayList<Object> data) {
    this.data = data;
  }
  public void addToData(Object o){
    data.add(o);
  }
}
