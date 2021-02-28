package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class DonkomiUser implements Parcelable {
  private String platformID; // UID that is given by firebase
  private String firstName;
  private String lastName;
  private String gender;
  private String email;
  private Organization organization;
  private String OrgID;
  private String orgIDType = Konstants.ROOM_NUMBER;
  private String profilePicture;
  private ArrayList<Device> deviceTokens;
  private ArrayList<Role> role;


  public DonkomiUser(String email, String firstName, String lastName, String platformID) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.platformID = platformID;
  }

  public DonkomiUser() {
  }


  public ArrayList<Device> getDeviceTokens() {
    return deviceTokens;
  }

  public void setDeviceTokens(ArrayList<Device> deviceTokens) {
    this.deviceTokens = deviceTokens;
  }

  public ArrayList<Role> getRole() {
    return role;
  }

  public void setRole(ArrayList<Role> role) {
    this.role = role;
  }

  public String getPlatformID() {
    return platformID;
  }

  public void setPlatformID(String platformID) {
    this.platformID = platformID;
  }

  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
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
    dest.writeString(this.OrgID);
    dest.writeString(this.orgIDType);
    dest.writeString(this.profilePicture);
  }

  public void readFromParcel(Parcel source) {
    this.firstName = source.readString();
    this.lastName = source.readString();
    this.gender = source.readString();
    this.email = source.readString();
    this.organization = source.readParcelable(Organization.class.getClassLoader());
    this.OrgID = source.readString();
    this.orgIDType = source.readString();
    this.profilePicture = source.readString();
  }

  protected DonkomiUser(Parcel in) {
    this.firstName = in.readString();
    this.lastName = in.readString();
    this.gender = in.readString();
    this.email = in.readString();
    this.organization = in.readParcelable(Organization.class.getClassLoader());
    this.OrgID = in.readString();
    this.orgIDType = in.readString();
    this.profilePicture = in.readString();
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

  @Override
  public String toString() {
    return "DonkomiUser{" +
        "platformID='" + platformID + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", gender='" + gender + '\'' +
        ", email='" + email + '\'' +
        ", organization=" + organization +
        ", OrgID='" + OrgID + '\'' +
        ", orgIDType='" + orgIDType + '\'' +
        ", profilePicture='" + profilePicture + '\'' +
        ", deviceTokens=" + deviceTokens +
        ", role=" + role +
        '}';
  }
}
