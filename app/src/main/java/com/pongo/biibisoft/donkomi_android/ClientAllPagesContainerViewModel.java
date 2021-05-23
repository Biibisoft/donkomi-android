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
  MutableLiveData<String> currentPage = new MutableLiveData<>(Konstants.EDIT_PROFILE_FORM);
  MutableLiveData<String> message = new MutableLiveData<>("");
  InternetExplorer explorer = new InternetExplorer(getApplication().getApplicationContext());
  MutableLiveData<Boolean> loader = new MutableLiveData<>(false);

  public ClientAllPagesContainerViewModel(@NonNull Application application) {
    super(application);
  }
  public void setLoaderValue(Boolean bool) {
    loader.setValue(bool);
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

  public void saveEditChanges() {
    setLoaderValue(true);
    String[] fields = new String[]{DonkomiUser.FieldNames.FIRST_NAME, DonkomiUser.FieldNames.LAST_NAME, DonkomiUser.FieldNames.PHONE, DonkomiUser.FieldNames.GENDER};
    TravellingResults res = null;
    try {
      res = DonkomiUser.areTheSame(getAuthUser(), getEditedUser(), fields);
    } catch (JSONException e) {
      setMessage(e.getMessage());
      e.printStackTrace();
      setLoaderValue(false);
    }
    if (res.isOkay()) {
      setMessage("No Changes have been made!");
      setLoaderValue(false);
    }
    else{
      explorer.setData(res.getDataWithValues());
      explorer.run(explorer.endSlash(DonkomiURLS.UPDATE_USER + getAuthUser().getPlatformID()), new Result() {
        @Override
        public void isOkay(JSONObject response) throws JSONException {
          setMessage("User is successfully updated!");
          setLoaderValue(false);
        }

        @Override
        public void error(String error) {
          setLoaderValue(false);
          setMessage(error);
        }
      });

    }
  }

  interface AfterSavedChanges {
    void nothingChanged();

    void changesSaved();

//    void error(String error);
  }
}
