package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

public class Role implements Parcelable {
  private String name = Konstants.STANDARD;
  private int accessLevel = 1;

  protected Role(Parcel in) {
    name = in.readString();
    accessLevel = in.readInt();
  }

  public static final Creator<Role> CREATOR = new Creator<Role>() {
    @Override
    public Role createFromParcel(Parcel in) {
      return new Role(in);
    }

    @Override
    public Role[] newArray(int size) {
      return new Role[size];
    }
  };

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAccessLevel() {
    return accessLevel;
  }

  public void setAccessLevel(int accessLevel) {
    this.accessLevel = accessLevel;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeInt(accessLevel);
  }
}
