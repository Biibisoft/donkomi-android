package com.pongo.biibisoft.donkomi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_page);
    mAuth = FirebaseAuth.getInstance();
    _this = this;
    setGoogleDialogUp();
    initialize();
  }


  public void initialize() {
    initializeLoader();
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

  private final View.OnClickListener launchGoogleSignIn = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      loadingDialog.show();
      registerWithGoogle();
    }
  };


  private void initializeLoader() {
    dialogCreator = new MagicBoxes(this);
    View loadingView = LayoutInflater.from(_this).inflate(R.layout.simple_loading_dialog, null, false);
    TextView loadingText = loadingView.findViewById(R.id.loader_text);
    loadingText.setText("Checking Details...");
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
      loadingDialog.show();
      mAuth.signInWithEmailAndPassword(MyHelper.getTextFrom(email), MyHelper.getTextFrom(password))
          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()) {
                Toast.makeText(LoginPage.this, "Nice, good to go!", Toast.LENGTH_SHORT).show();
                fireUser = mAuth.getCurrentUser();
              } else {
                Toast.makeText(_this, "Oops!, something happened! Couldn't log you in!", Toast.LENGTH_SHORT).show();
              }
              loadingDialog.dismiss();
            }
          }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          Log.d("LOGINATTEMPTERROR:", e.getMessage());
          Toast.makeText(LoginPage.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
          loadingDialog.dismiss();
        }
      });

    }
  };

  private void transitionToHomePage() {
    Intent homePage = new Intent(this, HomeContainerPage.class);
    startActivity(homePage);
  }

  // Google Login Step 4
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == Konstants.GOOGLE_SIGN_IN_CODE) {
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      try {
        GoogleSignInAccount account = task.getResult(ApiException.class);
        firebaseAuthWithGoogle(account.getIdToken());
      } catch (Exception e) {
        e.printStackTrace();
        Log.d(TAG, "onActivityResult: GoogleError" + e.getLocalizedMessage());
        Toast.makeText(this, "Oops! Failed to sign up with google! " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        loadingDialog.dismiss();
      }
    }

  }

  // Google Login Step 2
  private void setGoogleDialogUp() {
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build();
    //attach google sign in options to Google Dialog
    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
  }

  // Google Login Step 1
  public void registerWithGoogle() {
    Intent withGoogle = mGoogleSignInClient.getSignInIntent();
    startActivityForResult(withGoogle, Konstants.GOOGLE_SIGN_IN_CODE);
  }


  // Google Login Step 4
  private void firebaseAuthWithGoogle(String token) {
    AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              // Sign in success, update UI with the signed-in user's information
              fireUser = mAuth.getCurrentUser();

            } else {
              // If sign in fails, display a message to the user.
              Log.w(TAG, "signUpWithCredential:failure", task.getException());
              Toast.makeText(LoginPage.this, "Oops, couldn't sign you up with google! " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
            loadingDialog.dismiss();
          }
        });
  }


}