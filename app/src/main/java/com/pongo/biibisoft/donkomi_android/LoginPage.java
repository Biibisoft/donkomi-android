package com.pongo.biibisoft.donkomi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPage extends AppCompatActivity {

  private static final String TAG = "LOGIN_PAGE";
  Activity _this;
  Button finishBtn, useGoogleBtn;
  MagicBoxes dialogCreator;
  EditText email, password;
  FirebaseAuth mAuth;
  Dialog loadingDialog;
  private GoogleSignInClient mGoogleSignInClient;
  private FirebaseUser fireUser;
  LoginViewModel loginHandler;
  TextView loadingText, signOutTestBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_page);
    _this = this;

    initialize();
    loginHandler = new ViewModelProvider(this).get(LoginViewModel.class);
    setObservers();
  }


  public void setObservers() {
    loginHandler.getLoaderState().observe(this, new Observer<Boolean>() {
      @Override
      public void onChanged(Boolean isShowing) {
        if (isShowing) loadingDialog.show();
        else loadingDialog.dismiss();
      }
    });

    loginHandler.toastMsg().observe(this, new Observer<String>() {
      @Override
      public void onChanged(String text) {
        if (text == null) {
          Toast.makeText(_this, "Unknown error", Toast.LENGTH_SHORT).show();
          return;
        }
        if (!text.isEmpty()) Toast.makeText(_this, text, Toast.LENGTH_SHORT).show();
      }
    });

  }

  public void initialize() {
    initializeLoader();
    signOutTestBtn = findViewById(R.id.sign_out_test_btn);
    signOutTestBtn.setOnClickListener(testSignOut);
    useGoogleBtn = findViewById(R.id.use_google_btn);
    useGoogleBtn.setOnClickListener(launchGoogleSignIn);
    email = findViewById(R.id.email);
    password = findViewById(R.id.password);
    finishBtn = findViewById(R.id.finish_btn);
    finishBtn.setOnClickListener(finishAndLogin);
    TextView createNewAccount = findViewById(R.id.new_account_label);
    createNewAccount.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent page = new Intent(LoginPage.this, RegisterPage.class);
        startActivity(page);
      }
    });

  }

  private final View.OnClickListener testSignOut = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      loginHandler.fireAuthService.signOut();
    }
  };

  private final View.OnClickListener launchGoogleSignIn = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      loginHandler.setLoaderValue(true);
      loginHandler.fireAuthService.startGoogleRegistration(new MyFirebaseGoogleRegistrationHelper.RelayCallback() {
        @Override
        public void next(Object anything) {
          startActivityForResult((Intent) anything, Konstants.GOOGLE_SIGN_UP_CODE);
        }
      });
    }
  };


  private void initializeLoader() {
    dialogCreator = new MagicBoxes(this);
    View loadingView = LayoutInflater.from(_this).inflate(R.layout.simple_loading_dialog, null, false);
    loadingText = loadingView.findViewById(R.id.loader_text);
    loadingText.setText(R.string.authenticating);
    loadingDialog = dialogCreator.constructLoadingCustomDialog(loadingView);
    loadingDialog.setCanceledOnTouchOutside(false);
  }

  private Boolean detailsAreValid() {
    String _email = MyHelper.getTextFrom(email);
    String _password = MyHelper.getTextFrom(password);
    if (_email == null || _email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(_email).matches()) {
      email.requestFocus();
      email.setError("Please provide a valid email!");
      return false;
    }
    if (_password == null || _password.isEmpty()) {
      email.requestFocus();
      email.setError("Please provide a valid password");
    }
    return true;
  }

  private final View.OnClickListener finishAndLogin = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      if (!detailsAreValid()) return;
      loginHandler.authenticateWithEmailAndPassword(MyHelper.getTextFrom(email), MyHelper.getTextFrom(password), new DonkomiInterfaces.RelayUser() {
        @Override
        public void send(DonkomiUser user) {
          transitionToHomePage(user);
        }
      });
    }
  };

  private void transitionToHomePage(DonkomiUser user) {
    Intent homePage = new Intent(this, HomeContainerPage.class);
    homePage.putExtra(Konstants.USER, user);
    startActivity(homePage);
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    loginHandler.fireAuthService.onActivityResult(requestCode, data, new MyFirebaseGoogleRegistrationHelper.ActivityResultsCallback() {
      @Override
      public void googleDialogAuthIsOkay(String idToken) {
        loginHandler.fireAuthService.firebaseAuthWithGoogle(idToken, new DonkomiInterfaces.Result() {
          @Override
          public void isOkay() {
            FirebaseUser fireUser = loginHandler.mAuth.getCurrentUser();
            loginHandler.fetchUserFromBackend(fireUser.getUid(), new DonkomiInterfaces.Result() {
              @Override
              public void isOkay() {
                transitionToHomePage(loginHandler.userObj);
              }

              @Override
              public void error(String error) {
                loginHandler.setToastMsg(error);
                loginHandler.setLoaderValue(false);
              }
            });
          }

          @Override
          public void error(String error) {
            loginHandler.setLoaderValue(false);
            loginHandler.setToastMsg(error);
          }
        });
      }

      @Override
      public void error(String error) {
        loginHandler.setLoaderValue(false);
        loginHandler.setToastMsg(error);
      }
    });

  }
}