package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RoutineTemplate implements Parcelable {
  private String title;
  @SerializedName("involved_vendors")
  private ArrayList<Vendor> vendors;
  private String estimatedTime;
  private int perOrderRate;
  @SerializedName("base_cost")
  private int baseCost;
  private Currency currency;
  private ArrayList<Tag> foodTags;
  private String description;


  public RoutineTemplate() {

  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ArrayList<Vendor> getVendors() {
    return vendors;
  }

  public void setVendors(ArrayList<Vendor> vendors) {
    this.vendors = vendors;
  }

  public String getEstimatedTime() {
    return estimatedTime;
  }

  public void setEstimatedTime(String estimatedTime) {
    this.estimatedTime = estimatedTime;
  }

  public int getPerOrderRate() {
    return perOrderRate;
  }

  public void setPerOrderRate(int perOrderRate) {
    this.perOrderRate = perOrderRate;
  }

  public int getBaseCost() {
    return baseCost;
  }

  public void setBaseCost(int baseCost) {
    this.baseCost = baseCost;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public ArrayList<Tag> getFoodTags() {
    return foodTags;
  }

  public void setFoodTags(ArrayList<Tag> foodTags) {
    this.foodTags = foodTags;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.title);
    dest.writeTypedList(this.vendors);
    dest.writeString(this.estimatedTime);
    dest.writeInt(this.perOrderRate);
    dest.writeInt(this.baseCost);
    dest.writeParcelable(this.currency, flags);
    dest.writeTypedList(this.foodTags);
    dest.writeString(this.description);
  }

  public void readFromParcel(Parcel source) {
    this.title = source.readString();
    this.vendors = source.createTypedArrayList(Vendor.CREATOR);
    this.estimatedTime = source.readString();
    this.perOrderRate = source.readInt();
    this.baseCost = source.readInt();
    this.currency = source.readParcelable(Currency.class.getClassLoader());
    this.foodTags = source.createTypedArrayList(Tag.CREATOR);
    this.description = source.readString();
  }

  protected RoutineTemplate(Parcel in) {
    this.title = in.readString();
    this.vendors = in.createTypedArrayList(Vendor.CREATOR);
    this.estimatedTime = in.readString();
    this.perOrderRate = in.readInt();
    this.baseCost = in.readInt();
    this.currency = in.readParcelable(Currency.class.getClassLoader());
    this.foodTags = in.createTypedArrayList(Tag.CREATOR);
    this.description = in.readString();
  }

  @Override
  public String toString() {
    return "RoutineTemplate{" +
        "title='" + title + '\'' +
        ", vendors=" + vendors +
        ", estimatedTime='" + estimatedTime + '\'' +
        ", perOrderRate=" + perOrderRate +
        ", baseCost=" + baseCost +
        ", currency=" + currency +
        ", foodTags=" + foodTags +
        ", description='" + description + '\'' +
        '}';
  }

  public static final Creator<RoutineTemplate> CREATOR = new Creator<RoutineTemplate>() {
    @Override
    public RoutineTemplate createFromParcel(Parcel source) {
      return new RoutineTemplate(source);
    }

    @Override
    public RoutineTemplate[] newArray(int size) {
      return new RoutineTemplate[size];
    }
  };
}
