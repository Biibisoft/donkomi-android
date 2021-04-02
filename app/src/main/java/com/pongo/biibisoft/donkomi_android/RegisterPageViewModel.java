package com.pongo.biibisoft.donkomi_android;

import android.app.Application;
import android.content.Intent;
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
  public FirebaseAuth mAuth = FirebaseAuth.getInstance();
  String selectedGender = "OTHER";
  Organization selectedOrg;
  MagicBoxes dialogCreator;
  private final MutableLiveData<Boolean> loaderOn = new MutableLiveData<Boolean>(false);
  private final MutableLiveData<Boolean> isGoogle = new MutableLiveData<Boolean>(false);
  private final MutableLiveData<Boolean> flipBtns = new MutableLiveData<Boolean>(false);
  ;
  private final MutableLiveData<String> toastMsg = new MutableLiveData<String>("");
  private final MutableLiveData<String> message = new MutableLiveData<String>("Setup Your Profile On Donkomi");
  public final MyFirebaseGoogleRegistrationHelper fireAuthService = new MyFirebaseGoogleRegistrationHelper(getApplication().getApplicationContext());


  public RegisterPageViewModel(@NonNull Application application) {
    super(application);
  }

  public void registerNewUserWithEmailAndPassword(String email, String password, DonkomiInterfaces.Callback callback) {
    toggleLoader();
    fireAuthService.createUserWithEmailAndPassword(email, password, new MyFirebaseGoogleRegistrationHelper.Result() {
      @Override
      public void isOkay() {
        callback.next();
      }

      @Override
      public void error(String error) {
        toggleLoader();
        setToastMsg(error);
      }
    });
  }

  public void startGoogleRegistration(MyFirebaseGoogleRegistrationHelper.RelayCallback callback) {
    fireAuthService.startGoogleRegistration(new MyFirebaseGoogleRegistrationHelper.RelayCallback() {
      @Override
      public void next(Object anything) {
        callback.next(anything);
      }
    });
  }

  public void proceedAfterGoogleRegistration(DonkomiUser user) {
    toggleLoader();
    setUserObj(user);
    flipBtns();
    setMessage("Add The Remaining Information To Complete Your Profile");
  }


  public LiveData<String> getMessage() {
    return this.message;
  }

  public void setMessage(String msg) {
    this.message.setValue(msg);
  }

  public void flipBtns() {
    this.flipBtns.setValue(!this.flipBtns.getValue());
  }

  public LiveData<Boolean> shouldBtnsFlip() {
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

  public DonkomiUser getDonkomiUser(){
    return userObj.getValue();
  }

  public void createBackendDonkomiUser(DonkomiInterfaces.Callback callback) throws JSONException {
    DonkomiUser user = userObj.getValue();
    explorer.setData(user.parseIntoInternetData());
    explorer.run(DonkomiURLS.REGISTER_USER, new Result() {
      @Override
      public void isOkay(JSONObject response) throws JSONException {
        ResponseHandler handler = new ResponseHandler(response);
        if (handler.hasError()) {
          toggleLoader();
          setToastMsg(handler.getErrorMessage());
        } else callback.next();
      }

      @Override
      public void error(String error) {
        toggleLoader();
        setToastMsg(error);
      }
    });

  }


}
