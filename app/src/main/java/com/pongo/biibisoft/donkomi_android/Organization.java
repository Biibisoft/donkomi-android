package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

public class Organization  implements Parcelable {
  private int organizationID;
  private String name;
  private String country;

  public Organization(String name,String country, int ID){
    this.name = name;
    this.country = country;
    this.organizationID = ID;
  }

  public int getOrganizationID() {
    return organizationID;
  }

  public void setOrganizationID(int organizationID) {
    this.organizationID = organizationID;
  }

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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.organizationID);
    dest.writeString(this.name);
    dest.writeString(this.country);
  }

  public void readFromParcel(Parcel source) {
    this.organizationID = source.readInt();
    this.name = source.readString();
    this.country = source.readString();
  }

  protected Organization(Parcel in) {
    this.organizationID = in.readInt();
    this.name = in.readString();
    this.country = in.readString();
  }

  public static final Creator<Organization> CREATOR = new Creator<Organization>() {
    @Override
    public Organization createFromParcel(Parcel source) {
      return new Organization(source);
    }

    @Override
    public Organization[] newArray(int size) {
      return new Organization[size];
    }
  };
}
