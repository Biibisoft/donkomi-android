package com.pongo.biibisoft.donkomi_android;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pongo.biibisoft.donkomi_android.ClientHomeFragment.TAG;

public class GuruLandingPageViewModel extends CommonViewModelItems {
  MutableLiveData<ArrayList<Vendor>> vendors = new MutableLiveData<>();
  public GuruLandingPageViewModel(@NonNull Application application) {
    super(application);
  }

  public MutableLiveData<ArrayList<Vendor>> getVendors() {
    return vendors;
  }

  public ArrayList<Vendor> getRealVendors(){
    return this.vendors.getValue();
  }

  public void setVendors(MutableLiveData<ArrayList<Vendor>> vendors) {
    this.vendors = vendors;
  }

  public void setVendors(ArrayList<Vendor> vendors){
    this.vendors.setValue(vendors);
  }

  public void getAllGuruVendors() {
    GuruLandingPageViewModel thisClass = this;
    explorer.setExpectedDataType(ArrayList.class);
    explorer.runAndFindData(DonkomiURLS.GET_USER_VENDORS, new ResultWithData() {
      @Override
      public void isOkay(JSONObject response) throws JSONException {

      }

      @Override
      public void getData(Object data) {

      }

      @Override
      public void getDataArray(Object data) {
        ArrayList<Vendor> vendors = (ArrayList<Vendor>) data;
        thisClass.setVendors(vendors);
//        Log.d(TAG, "getDataArray: "+ vendors.toString());
      }

      @Override
      public void error(String error) {

      }
    });
  }


}
