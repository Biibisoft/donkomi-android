package com.pongo.biibisoft.donkomi_android;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

// The view model for ClientAllPagesContainer.java
public class ClientAllPagesContainerViewModel extends AndroidViewModel {
  public static final String TAG = "CLIENT_ALL_PAGES";
  MutableLiveData<DonkomiUser> authenticatedUser = new MutableLiveData<>(new DonkomiUser());
  MagicBoxes dialogCreator = new MagicBoxes(getApplication().getApplicationContext());
  MutableLiveData<String> currentPage = new MutableLiveData<>(Konstants.EDIT_PROFILE_FORM);
  public ClientAllPagesContainerViewModel(@NonNull Application application) {
    super(application);
  }



  public LiveData<DonkomiUser> authenticatedUser(){
    return authenticatedUser;
  }
  public void handleTravellingContent(Intent data){
    authenticatedUser.setValue(data.getParcelableExtra(Konstants.USER));
  }

  public LiveData<String> currentPage(){
    return currentPage;
  }
  public void saveChanges(){

  }
}
