package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Vendor  implements Parcelable {
  private String name;
  private String picture;
  private String description;

  public String getPicture() {
    return picture;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public JSONObject makeRequestData() throws JSONException {
    JSONObject obj = new JSONObject();
    obj.put("name",name);
    obj.put("description", description);
    obj.put("image", picture);
    return obj;
  };
  public void setPicture(String picture) {
    this.picture = picture;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeString(this.picture);
  }

  public void readFromParcel(Parcel source) {
    this.name = source.readString();
    this.picture = source.readString();
  }


  public Vendor(String name, String description, String picture){
    this.name = name;
    this.description = description;
    this.picture = picture;
  }
  public Vendor() {
  }

  protected Vendor(Parcel in) {
    this.name = in.readString();
    this.picture = in.readString();
  }

  public static final Creator<Vendor> CREATOR = new Creator<Vendor>() {
    @Override
    public Vendor createFromParcel(Parcel source) {
      return new Vendor(source);
    }

    @Override
    public Vendor[] newArray(int size) {
      return new Vendor[size];
    }
  };
}
