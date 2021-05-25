package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DonkomiUser implements Parcelable, DonkomiUserManager {
  private static DonkomiUser instance = null;

  public static DonkomiUser getInstance() {
    if (instance == null) instance = new DonkomiUser();
    return instance;
  }

  public static void setInstance(DonkomiUser user) {
    Log.d("ALL_FOR_DISPLAY", "setInstance: " + user.toString());
    instance = new DonkomiUser(user);
  }

  public static DonkomiUser getInstance(String email, String firstName, String lastName, String platformID) {
    if (instance == null) {
      instance = new DonkomiUser(email, firstName, lastName, platformID);
    }
    return instance;
  }

  @SerializedName("user_id")
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
  private ArrayList<Role> roles;
  private String phone = "23432342";

  private DonkomiUser(String email, String firstName, String lastName, String platformID) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.platformID = platformID;
  }

  private DonkomiUser(DonkomiUser user) {
    this.platformID = user.getPlatformID();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.gender = user.getGender();
    this.email = user.getEmail();
    this.organization = user.getOrganization() != null ? new Organization(user.getOrganization()) : null;
    this.OrgID = user.getOrgID();
    this.orgIDType = user.getOrgIDType();
    this.profilePicture = user.getProfilePicture();
    this.deviceTokens = user.getDeviceTokens();
    this.roles = user.getRoles();
    this.phone = user.getPhone();
  }


  private DonkomiUser() {
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

//  @Override
//  public Boolean isTheSameAs(Object compareObj) {
//    DonkomiUser user = (DonkomiUser) compareObj;
//    return firstName.equals(user.getFirstName()) && lastName.equals(user.getLastName()) && phone.equals(user.getPhone()) && gender.equals(user.getGender());
//  }

  public static TravellingResults areTheSame(DonkomiUser user, DonkomiUser otherPerson, String[] fields) throws JSONException {
    TravellingResults res = new TravellingResults();
    if (fields == null) fields = FieldNames.FIELDS;
    for (String fieldName : fields) {
      String one = (String) getValueWithFieldName(fieldName, user);
      String two = (String) getValueWithFieldName(fieldName, otherPerson);
      if (one == null && two == null) {
      } // both values are null so they are still the same
      else if (one != null && two != null) { // none of the values is null, go on and check equality
        if (!one.equals(two)) {
          res.setStatus(false);
          res.addData(fieldName, two);
        }
      } else {// one of the values is null so its they are not the same
        res.setStatus(false);
        res.addData(fieldName, two);
      }
    }
    return res;
  }


  public static Object getValueWithFieldName(String fieldName, Object obj) {
    DonkomiUser user = (DonkomiUser) obj;
    switch (fieldName) {
      case FieldNames.FIRST_NAME:
        return user.getFirstName();

      case FieldNames.LAST_NAME:
        return user.getLastName();

      case FieldNames.EMAIL:
        return user.getEmail();
      case FieldNames.GENDER:
        return user.getGender();

      case FieldNames.PHONE:
        return user.getPhone();
      case FieldNames.ORGANIZATION:
        return user.getOrganization();

      case FieldNames.PLATFORM_ID:
        return user.getPlatformID();

      case FieldNames.PROFILE_PICTURE:
        return user.getProfilePicture();
    }
    return null;
  }

  public static class FieldNames {
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PHONE = "phone";
    public static final String GENDER = "gender";
    public static final String ORGANIZATION = "organization";
    public static final String PLATFORM_ID = "platformID";
    public static final String PROFILE_PICTURE = "profilePicture";
    public static final String EMAIL = "EMAIL";
    public static final String[] FIELDS = new String[]{FIRST_NAME, LAST_NAME, PHONE, GENDER, ORGANIZATION, PLATFORM_ID, PROFILE_PICTURE, EMAIL};
  }
}


