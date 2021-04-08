package com.pongo.biibisoft.donkomi_android;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class SettingsFragmentViewModel  extends AndroidViewModel {
  DonkomiUser authenticatedUser;
  public SettingsFragmentViewModel(@NonNull Application application) {
    super(application);
  }

  public void setAuthenticatedUser(DonkomiUser authenticatedUser) {
    this.authenticatedUser = authenticatedUser;
  }
}
