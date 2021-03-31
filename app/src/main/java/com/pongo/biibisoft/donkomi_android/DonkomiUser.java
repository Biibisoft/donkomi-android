package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DonkomiUser implements Parcelable, DonkomiUserManager {
  @SerializedName("user_id")
  private String platformID; // UID that is given by firebase
  private String firstName;
  private String lastName;
  private String gender;
  private String email;
  private Organization organization = null;
  private String OrgID;
  private String orgIDType = Konstants.ROOM_NUMBER;
  private String profilePicture;
  private ArrayList<Device> deviceTokens;
  private ArrayList<Role> roles;
  private String phone = "23432342";

  public DonkomiUser(String email, String firstName, String lastName, String platformID) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.platformID = platformID;
  }



  public DonkomiUser() {
  }

  public ArrayList<Role> getRoles() {
    return roles;
  }

  public void setRoles(ArrayList<Role> roles) {
    this.roles = roles;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public ArrayList<Device> getDeviceTokens() {
    return deviceTokens;
  }

  public void setDeviceTokens(ArrayList<Device> deviceTokens) {
    this.deviceTokens = deviceTokens;
  }

  public ArrayList<Role> getRole() {
    return roles;
  }

  public void setRole(ArrayList<Role> role) {
    this.roles = role;
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
        ", role=" + roles +
        '}';
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.platformID);
    dest.writeString(this.firstName);
    dest.writeString(this.lastName);
    dest.writeString(this.gender);
    dest.writeString(this.email);
    dest.writeParcelable(this.organization, flags);
    dest.writeString(this.OrgID);
    dest.writeString(this.orgIDType);
    dest.writeString(this.profilePicture);
    dest.writeTypedList(this.deviceTokens);
    dest.writeTypedList(this.roles);
    dest.writeString(this.phone);
  }

  public void readFromParcel(Parcel source) {

    this.platformID = source.readString();
    this.firstName = source.readString();
    this.lastName = source.readString();
    this.gender = source.readString();
    this.email = source.readString();
    this.organization = source.readParcelable(Organization.class.getClassLoader());
    this.OrgID = source.readString();
    this.orgIDType = source.readString();
    this.profilePicture = source.readString();
    this.deviceTokens = source.createTypedArrayList(Device.CREATOR);
    this.roles = source.createTypedArrayList(Role.CREATOR);
    this.phone = source.readString();
  }

  protected DonkomiUser(Parcel in) {
    this.platformID = in.readString();
    this.firstName = in.readString();
    this.lastName = in.readString();
    this.gender = in.readString();
    this.email = in.readString();
    this.organization = in.readParcelable(Organization.class.getClassLoader());
    this.OrgID = in.readString();
    this.orgIDType = in.readString();
    this.profilePicture = in.readString();
    this.deviceTokens = in.createTypedArrayList(Device.CREATOR);
    this.roles = in.createTypedArrayList(Role.CREATOR);
    this.phone = in.readString();
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
  public JSONObject parseIntoInternetData() throws JSONException {
    JSONObject data = new JSONObject();
    data.put("firstName", this.firstName);
    data.put("lastName", this.lastName);
    data.put("user_id", this.platformID);
    data.put("phone", this.phone);
    data.put("email", this.email);
    data.put("organization_id", this.organization != null ? this.organization.getOrganizationID() : 1);
    return data;
  }
}
