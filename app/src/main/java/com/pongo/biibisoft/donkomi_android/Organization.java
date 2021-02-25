package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

public class Organization  implements Parcelable {
  private String name;
  private String country;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public static Creator<Organization> getCREATOR() {
    return CREATOR;
  }

  protected Organization(Parcel in) {
    name = in.readString();
    country = in.readString();
  }

  public static final Creator<Organization> CREATOR = new Creator<Organization>() {
    @Override
    public Organization createFromParcel(Parcel in) {
      return new Organization(in);
    }

    @Override
    public Organization[] newArray(int size) {
      return new Organization[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(country);
  }
}
