package com.pongo.biibisoft.donkomi_android;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ClientAllPagesContainerViewModel extends AndroidViewModel {
  DonkomiUser authenticatedUser;
  public ClientAllPagesContainerViewModel(@NonNull Application application) {
    super(application);
  }

  public void handleTravellingContent(Intent data){
    authenticatedUser = (DonkomiUser) data.getParcelableExtra(Konstants.USER);
  }
}
