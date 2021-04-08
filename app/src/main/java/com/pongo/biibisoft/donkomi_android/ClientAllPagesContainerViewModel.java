package com.pongo.biibisoft.donkomi_android;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

// The view model for ClientAllPagesContainer.java
public class ClientAllPagesContainerViewModel extends AndroidViewModel {
  public static final String TAG = "CLIENT_ALL_PAGES";
  MutableLiveData<DonkomiUser> authenticatedUser = new MutableLiveData<>();
  public ClientAllPagesContainerViewModel(@NonNull Application application) {
    super(application);
  }

  public void handleTravellingContent(Intent data){
    authenticatedUser.setValue(data.getParcelableExtra(Konstants.USER));
    Log.d(TAG, "handleTravellingContent: "+authenticatedUser.getValue().toString());
  }
}
