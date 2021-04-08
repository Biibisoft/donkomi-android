package com.pongo.biibisoft.donkomi_android;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ClientFragmentsViewModel  extends AndroidViewModel {
  public static final String TAG = "CLIENT_FRAGMENTS_VIEW_MODEL";
  DonkomiUser authenticatedUser;
  public ClientFragmentsViewModel(@NonNull Application application) {
    super(application);
  }


  public void handleTravellingContent(Intent data){
    authenticatedUser = (DonkomiUser) data.getParcelableExtra(Konstants.USER);
    Log.d(TAG, "handleTravellingContent: "+authenticatedUser.toString());
  }
}
