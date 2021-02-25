package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

public class Vendor  implements Parcelable {
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  protected Vendor(Parcel in) {
    name = in.readString();
  }

  public static final Creator<Vendor> CREATOR = new Creator<Vendor>() {
    @Override
    public Vendor createFromParcel(Parcel in) {
      return new Vendor(in);
    }

    @Override
    public Vendor[] newArray(int size) {
      return new Vendor[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
  }
}
