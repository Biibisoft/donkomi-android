package com.pongo.biibisoft.donkomi_android;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class DonkomiOrder implements Parcelable {
  private int id;
  private String orderNumber;
  private ArrayList<ItemForSale> items;
  private Currency currency;
  private Vendor vendor;
  private String orderStatus = Konstants.COMPLETE_ORDER;
  private int total;

  public int getId() {
    return id;
  }


  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public ArrayList<ItemForSale> getItems() {
    return items;
  }

  public void setItems(ArrayList<ItemForSale> items) {
    this.items = items;
  }

  public Vendor getVendor() {
    return vendor;
  }

  public void setVendor(Vendor vendor) {
    this.vendor = vendor;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public DonkomiOrder() {
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.orderNumber);
    dest.writeTypedList(this.items);
    dest.writeParcelable(this.currency, flags);
    dest.writeParcelable(this.vendor, flags);
    dest.writeString(this.orderStatus);
    dest.writeInt(this.total);
  }

  public void readFromParcel(Parcel source) {

    this.id = source.readInt();
    this.orderNumber = source.readString();
    this.items = source.createTypedArrayList(ItemForSale.CREATOR);
    this.currency = source.readParcelable(Currency.class.getClassLoader());
    this.vendor = source.readParcelable(Vendor.class.getClassLoader());
    this.orderStatus = source.readString();
    this.total = source.readInt();
  }

  protected DonkomiOrder(Parcel in) {
    this.id = in.readInt();
    this.orderNumber = in.readString();
    this.items = in.createTypedArrayList(ItemForSale.CREATOR);
    this.currency = in.readParcelable(Currency.class.getClassLoader());
    this.vendor = in.readParcelable(Vendor.class.getClassLoader());
    this.orderStatus = in.readString();
    this.total = in.readInt();
  }

  public static final Creator<DonkomiOrder> CREATOR = new Creator<DonkomiOrder>() {
    @Override
    public DonkomiOrder createFromParcel(Parcel source) {
      return new DonkomiOrder(source);
    }

    @Override
    public DonkomiOrder[] newArray(int size) {
      return new DonkomiOrder[size];
    }
  };
}
