package com.pongo.biibisoft.donkomi_android;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterPageViewModel extends AndroidViewModel {
  private MutableLiveData<String> email, password, confirmPassword, firstName, lastName = new MutableLiveData<>("");
  private MutableLiveData<DonkomiUser> userObj = new MutableLiveData<DonkomiUser>();
  private InternetExplorer explorer = new InternetExplorer(getApplication().getApplicationContext());
  private FirebaseUser fireUser;
  private FirebaseAuth mAuth = FirebaseAuth.getInstance();
  String selectedGender = "OTHER";
  Organization selectedOrg;
  MagicBoxes dialogCreator;
  private MutableLiveData<Boolean> loaderOn, isGoogle, flipBtns = new MutableLiveData<Boolean>(false);
  private MutableLiveData<String> toastMsg = new MutableLiveData<String>("");
//  private MutableLiveData<Boolean> isGoogle = new MutableLiveData<Boolean>(false);


  public RegisterPageViewModel(@NonNull Application application) {
    super(application);
  }

  public void flipBtns(){
    this.flipBtns.setValue(!this.flipBtns.getValue());
  }
  public LiveData<Boolean> shouldBtnsFlip(){
    return this.flipBtns;
  }
  public void setToastMsg(String msg) {
    this.toastMsg.setValue(msg);
  }

  public Boolean isGoogleRegistration() {
    return isGoogle.getValue();
  }

  public LiveData<String> getToastMsg() {
    return this.toastMsg;
  }

  public void toggleLoader() {
    loaderOn.setValue(!this.loaderOn.getValue());
  }

  public LiveData<Boolean> getLoaderState() {
    return this.loaderOn;
  }

  public InternetExplorer explorer() {
    return this.explorer;
  }

  public void setUserObj(DonkomiUser user) {
    this.userObj.setValue(user);
  }

  public LiveData<DonkomiUser> userObj() {
    return userObj;
  }

  public void createBackendDonkomiUser(DonkomiUser user, DonkomiInterfaces.Callback callback) throws JSONException {
    explorer.setData(user.parseIntoInternetData());
    explorer.run(DonkomiURLS.REGISTER_USER, new Result() {
      @Override
      public void isOkay(JSONObject response) throws JSONException {
        ResponseHandler handler = new ResponseHandler(response);
        if (handler.hasError()) {
          toggleLoader();
          setToastMsg(handler.getErrorMessage());
        } else {
          if (isGoogleRegistration()) setUserObj(user); flipBtns();
          callback.next();
        }
      }

      @Override
      public void error(String error) {
        toggleLoader();
        setToastMsg(error);
      }
    });

  }


}
