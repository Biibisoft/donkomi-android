package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RoutineTemplate implements Parcelable {
  private String title;
  private ArrayList<Vendor> vendors;
  private String estimatedTime;
  private int perOrderRate;
  private int baseCost;
  private Currency currency;
  private ArrayList<Tag> foodTags ;

  protected RoutineTemplate(Parcel in) {
    title = in.readString();
    vendors = in.createTypedArrayList(Vendor.CREATOR);
    estimatedTime = in.readString();
    perOrderRate = in.readInt();
    baseCost = in.readInt();
    currency = in.readParcelable(Currency.class.getClassLoader());
    foodTags = in.createTypedArrayList(Tag.CREATOR);
  }

  public static final Creator<RoutineTemplate> CREATOR = new Creator<RoutineTemplate>() {
    @Override
    public RoutineTemplate createFromParcel(Parcel in) {
      return new RoutineTemplate(in);
    }

    @Override
    public RoutineTemplate[] newArray(int size) {
      return new RoutineTemplate[size];
    }
  };

  public RoutineTemplate() {

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
    dest.writeString(title);
    dest.writeTypedList(vendors);
    dest.writeString(estimatedTime);
    dest.writeInt(perOrderRate);
    dest.writeInt(baseCost);
    dest.writeParcelable(currency, flags);
    dest.writeTypedList(foodTags);
  }
}
