package com.pongo.biibisoft.donkomi_android;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class LoginViewModel extends AndroidViewModel {
  FirebaseAuth mAuth = FirebaseAuth.getInstance();
  MutableLiveData<Boolean> loader = new MutableLiveData<>(false);
  MutableLiveData<String> toastMsg = new MutableLiveData<>("");
  MyFirebaseGoogleRegistrationHelper fireAuthService = new MyFirebaseGoogleRegistrationHelper(getApplication().getApplicationContext());
  InternetExplorer explorer = new InternetExplorer(getApplication().getApplicationContext());
  FirebaseUser fireUser;
  Gson gson = new Gson();
  DonkomiUser userObj;

  public LoginViewModel(@NonNull Application application) {
    super(application);
  }

  public void setToastMsg(String msg) {
    this.toastMsg.setValue(msg);
  }

  public LiveData<String> toastMsg() {
    return this.toastMsg;
  }

  public void toggleLoader() {
    loader.setValue(!this.loader.getValue());
  }

  public void setLoaderValue(Boolean bool) {
    loader.setValue(bool);
  }

  public LiveData<Boolean> getLoaderState() {
    return this.loader;
  }

  public void authenticateWithEmailAndPassword(String email, String password, DonkomiInterfaces.RelayUser callback) {
    setLoaderValue(true);
    fireAuthService.signInWithEmailAndPassword(email, password, new MyFirebaseGoogleRegistrationHelper.Result() {
      @Override
      public void isOkay() {
        fireUser = mAuth.getCurrentUser();
        fetchUserFromBackend(fireUser.getUid(), new DonkomiInterfaces.Result() {
          @Override
          public void isOkay() {
            callback.send(userObj);
          }

          @Override
          public void error(String error) {
            setToastMsg(error);
            setLoaderValue(false);
          }
        });

      }

      @Override
      public void error(String error) {
        setToastMsg(error);
        setLoaderValue(false);
      }
    });

  }

  public void fetchUserFromBackend(String uid, DonkomiInterfaces.Result callback) {
    explorer.setMethod(InternetExplorer.GET);
    explorer.runAndFindData(DonkomiURLS.GET_USER + explorer.endSlash(uid), new ResultWithData() {
      @Override
      public void isOkay(JSONObject response) throws JSONException {

      }

      @Override
      public void getData(Object data) {
        JSONObject obj = (JSONObject) data;
        userObj = gson.fromJson(obj.toString(), (Type) DonkomiUser.class);
        callback.isOkay();
      }

      @Override
      public void error(String error) {
        callback.error(error);
      }
    });
  }


}
