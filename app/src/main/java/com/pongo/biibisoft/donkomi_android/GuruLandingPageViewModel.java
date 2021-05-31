package com.pongo.biibisoft.donkomi_android;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pongo.biibisoft.donkomi_android.ClientHomeFragment.TAG;

public class GuruLandingPageViewModel extends CommonViewModelItems {
  ArrayList<Vendor> vendors = new ArrayList<>();
  public GuruLandingPageViewModel(@NonNull Application application) {
    super(application);
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
        thisClass.vendors = vendors;
        Log.d(TAG, "getDataArray: "+ vendors.toString());
      }

      @Override
      public void error(String error) {

      }
    });
  }


}
