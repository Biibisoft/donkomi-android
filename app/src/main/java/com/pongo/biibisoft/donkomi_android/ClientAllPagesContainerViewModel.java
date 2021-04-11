package com.pongo.biibisoft.donkomi_android;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

// The view model for ClientAllPagesContainer.java
public class ClientAllPagesContainerViewModel extends AndroidViewModel {
  public static final String TAG = "CLIENT_ALL_PAGES";
  MutableLiveData<DonkomiUser> authenticatedUser = new MutableLiveData<>(new DonkomiUser());
  MutableLiveData<DonkomiUser> editedUser = new MutableLiveData<DonkomiUser>(new DonkomiUser());
  MagicBoxes dialogCreator = new MagicBoxes(getApplication().getApplicationContext());
  MutableLiveData<String> currentPage = new MutableLiveData<>(Konstants.EDIT_PROFILE_FORM);
  MutableLiveData<String> message = new MutableLiveData<>("");
  InternetExplorer explorer = new InternetExplorer(getApplication().getApplicationContext());

  public ClientAllPagesContainerViewModel(@NonNull Application application) {
    super(application);
  }

  public LiveData<DonkomiUser> authenticatedUser() {
    return authenticatedUser;
  }

  public LiveData<String> message (){
    return message;
  }

  public void setMessage(String message){
    this.message.setValue(message);
  }

  public void handleTravellingContent(Intent data) {
    authenticatedUser.setValue(data.getParcelableExtra(Konstants.USER));
    editedUser.setValue(new DonkomiUser((DonkomiUser) data.getParcelableExtra(Konstants.USER)));
  }

  public DonkomiUser getAuthUser() {
    return authenticatedUser.getValue();
  }

  public DonkomiUser getEditedUser() {
    return editedUser.getValue();
  }

  public void onEditTriggered(String type, String value) {

  }

  public LiveData<String> currentPage() {
    return currentPage;
  }

  public String getCurrentPageValue(){
    return currentPage.getValue();
  }

  public void saveEditChanges(AfterSavedChanges callback) {
    String[] fields = new String[]{DonkomiUser.FieldNames.FIRST_NAME, DonkomiUser.FieldNames.LAST_NAME, DonkomiUser.FieldNames.PHONE, DonkomiUser.FieldNames.GENDER};
    TravellingResults res = null;
    try {
      res = DonkomiUser.areTheSame(getAuthUser(), getEditedUser(), fields);
    } catch (JSONException e) {
      callback.error(e.getMessage());
      e.printStackTrace();
    }
    if (res.isOkay()) callback.nothingChanged();
    else{
      explorer.setData(res.getDataWithValues());

    }
  }

  interface AfterSavedChanges {
    void nothingChanged();

    void changesSaved();

    void error(String error);
  }
}
