package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ItemForSale implements Parcelable {
  private String name;
  private int price;
  private Currency currency;
  private String description;
  private int available ;
  private ArrayList<Tag> tags;
  private String picture;

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public ArrayList<Tag> getTags() {
    return tags;
  }

  public void setTags(ArrayList<Tag> tags) {
    this.tags = tags;
  }

  public ItemForSale(String name, int price, String description,String picture){
    this.name = name;
    this.price = price;
    this.description = description;
    this.picture = picture;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public int getAvailable() {
    return available;
  }

  public void setAvailable(int available) {
    this.available = available;
  }



  public ItemForSale() {
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeInt(this.price);
    dest.writeParcelable(this.currency, flags);
    dest.writeString(this.description);
    dest.writeInt(this.available);
    dest.writeTypedList(this.tags);
  }

  public void readFromParcel(Parcel source) {
    this.name = source.readString();
    this.price = source.readInt();
    this.currency = source.readParcelable(Currency.class.getClassLoader());
    this.description = source.readString();
    this.available = source.readInt();
    this.tags = source.createTypedArrayList(Tag.CREATOR);
  }

  protected ItemForSale(Parcel in) {
    this.name = in.readString();
    this.price = in.readInt();
    this.currency = in.readParcelable(Currency.class.getClassLoader());
    this.description = in.readString();
    this.available = in.readInt();
    this.tags = in.createTypedArrayList(Tag.CREATOR);
  }

  public static final Creator<ItemForSale> CREATOR = new Creator<ItemForSale>() {
    @Override
    public ItemForSale createFromParcel(Parcel source) {
      return new ItemForSale(source);
    }

    @Override
    public ItemForSale[] newArray(int size) {
      return new ItemForSale[size];
    }
  };
}
