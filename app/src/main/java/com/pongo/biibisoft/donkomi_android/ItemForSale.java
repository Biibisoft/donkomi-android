package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemForSale implements Parcelable {
  private String name;
  private int price;
  private Currency currency;
  private int available ;

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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeInt(this.price);
    dest.writeParcelable(this.currency, flags);
    dest.writeInt(this.available);
  }

  public void readFromParcel(Parcel source) {
    this.name = source.readString();
    this.price = source.readInt();
    this.currency = source.readParcelable(Currency.class.getClassLoader());
    this.available = source.readInt();
  }

  public ItemForSale() {
  }

  protected ItemForSale(Parcel in) {
    this.name = in.readString();
    this.price = in.readInt();
    this.currency = in.readParcelable(Currency.class.getClassLoader());
    this.available = in.readInt();
  }

  public static final Parcelable.Creator<ItemForSale> CREATOR = new Parcelable.Creator<ItemForSale>() {
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
