package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

public class Device implements Parcelable {
 private String name;
 private String token;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeString(this.token);
  }

  public void readFromParcel(Parcel source) {
    this.name = source.readString();
    this.token = source.readString();
  }

  public Device() {
  }

  protected Device(Parcel in) {
    this.name = in.readString();
    this.token = in.readString();
  }

  public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {
    @Override
    public Device createFromParcel(Parcel source) {
      return new Device(source);
    }

    @Override
    public Device[] newArray(int size) {
      return new Device[size];
    }
  };
}
