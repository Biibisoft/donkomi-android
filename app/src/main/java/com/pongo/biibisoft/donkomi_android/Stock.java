package com.pongo.biibisoft.donkomi_android;

import org.json.JSONException;
import org.json.JSONObject;

public class Stock extends ItemForSale {
  public static final String STOCK_BUCKET = "STOCK";
  public static final String STOCK_TASK = "STOCK_TASK";
  Vendor vendor ;
  public Stock (String name, String price, String description,String picture){
    super(name, Integer.parseInt(price),description, picture);
  }

  public Vendor getVendor() {
    return vendor;
  }

  public void setVendor(Vendor vendor) {
    this.vendor = vendor;
  }

  public Stock(){}

  public JSONObject makeRequestData() throws JSONException {
    JSONObject data = new JSONObject();
      data.put("name", this.getName());
      data.put("price", this.getPrice());
      data.put("description", this.getDescription());
      data.put("tags",this.getTags());
      data.put("picture",this.getPicture());
    return data;
  }
}
