package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag implements Parcelable {
  protected Tag(Parcel in) {
    name = in.readString();
    type = in.readString();
  }

  public static final Creator<Tag> CREATOR = new Creator<Tag>() {
    @Override
    public Tag createFromParcel(Parcel in) {
      return new Tag(in);
    }

    @Override
    public Tag[] newArray(int size) {
      return new Tag[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(type);
  }

  class TAGTYPES {
    public static final String ROUTINE_TAGS = "ROUTINE_TAGS";
    public static final String VENDOR_TAGS = "VENDOR_TAGS";
  }

  private String name;
  private String type;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
