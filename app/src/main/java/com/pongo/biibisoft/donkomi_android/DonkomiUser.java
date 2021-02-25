package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

public class DonkomiUser implements Parcelable {
  private String firstName;
  private String lastName;
  private String gender;
  private String email;
  private Organization organization;
  private String type = Konstants.STANDARD;
  private String OrgID;
  private String orgIDType = Konstants.ROOM_NUMBER;

  public DonkomiUser() {
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getGender() {
    return gender;
  }

  public String getOrgID() {
    return OrgID;
  }

  public void setOrgID(String orgID) {
    OrgID = orgID;
  }

  public String getOrgIDType() {
    return orgIDType;
  }

  public void setOrgIDType(String orgIDType) {
    this.orgIDType = orgIDType;
  }

  public static Creator<DonkomiUser> getCREATOR() {
    return CREATOR;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = organization;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.firstName);
    dest.writeString(this.lastName);
    dest.writeString(this.gender);
    dest.writeString(this.email);
    dest.writeParcelable(this.organization, flags);
    dest.writeString(this.type);
    dest.writeString(this.OrgID);
    dest.writeString(this.orgIDType);
  }

  public void readFromParcel(Parcel source) {
    this.firstName = source.readString();
    this.lastName = source.readString();
    this.gender = source.readString();
    this.email = source.readString();
    this.organization = source.readParcelable(Organization.class.getClassLoader());
    this.type = source.readString();
    this.OrgID = source.readString();
    this.orgIDType = source.readString();
  }

  protected DonkomiUser(Parcel in) {
    this.firstName = in.readString();
    this.lastName = in.readString();
    this.gender = in.readString();
    this.email = in.readString();
    this.organization = in.readParcelable(Organization.class.getClassLoader());
    this.type = in.readString();
    this.OrgID = in.readString();
    this.orgIDType = in.readString();
  }

  public static final Creator<DonkomiUser> CREATOR = new Creator<DonkomiUser>() {
    @Override
    public DonkomiUser createFromParcel(Parcel source) {
      return new DonkomiUser(source);
    }

    @Override
    public DonkomiUser[] newArray(int size) {
      return new DonkomiUser[size];
    }
  };
}
