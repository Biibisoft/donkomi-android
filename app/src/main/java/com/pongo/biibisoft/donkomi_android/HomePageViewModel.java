package com.pongo.biibisoft.donkomi_android;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class HomePageViewModel extends AndroidViewModel {
  DonkomiUser authenticatedUser;
  public HomePageViewModel(@NonNull Application application) {
    super(application);
  }

  public void handleTravellingContent(Intent data){
    authenticatedUser = (DonkomiUser) data.getParcelableExtra(Konstants.USER);
  }

  public void setAuthUser(DonkomiUser user){
    this.authenticatedUser = user;
  }



}
