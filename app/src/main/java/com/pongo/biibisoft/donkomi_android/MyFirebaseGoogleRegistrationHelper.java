package com.pongo.biibisoft.donkomi_android;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

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
import com.google.firebase.auth.GoogleAuthProvider;

public class MyFirebaseGoogleRegistrationHelper {
  Context context;
  private GoogleSignInClient mGoogleSignInClient;
  private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

  public MyFirebaseGoogleRegistrationHelper(Context context) {
    this.context = context;
  }

  //  ----- FIRST,  (this is the entry point)
  public void startGoogleRegistration(RelayCallback callback) {
    setGoogleDialogUp();
    Intent withGoogle = mGoogleSignInClient.getSignInIntent();
    callback.next(withGoogle);
  }

  // ----- SETUP
  public void setGoogleDialogUp() {
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build();
    //attach google sign in options to Google Dialog
    mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
  }

  //------- THIRD 2 ---- (put this in onActivity result of the active activity)
  public void onActivityResult(int requestCode, Intent data, ActivityResultsCallback callback) {
    if (requestCode == Konstants.GOOGLE_SIGN_UP_CODE) {
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      try {
        GoogleSignInAccount account = task.getResult(ApiException.class);
        callback.googleDialogAuthIsOkay(account.getIdToken());
//        firebaseAuthWithGoogle(account.getIdToken(), callback);
      } catch (Exception e) {
        e.printStackTrace();
        callback.error(e.getMessage());
      }
    }
  }

  //  ---------- FOURTH 3 ( should be called inside the onActivity result fxn ---
  public void firebaseAuthWithGoogle(String token, DonkomiInterfaces.Result callback) {
    AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            // Sign in success, update UI with the signed-in user's information
            if (task.isSuccessful()) callback.isOkay();
              // If sign in fails, display a message to the user.
            else task.getException().getLocalizedMessage(); callback.error("Oops, authentication with google failed, please try again!");
          }
        });
  }
//-------------------------------------------------- EMAIL AND PASSWORD ---------------------------------------

  public void createUserWithEmailAndPassword(String email, String password, Result callback) {
    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) callback.isOkay();
        else
          callback.error("Sorry, something happened, could not create your account. Please try again!");

      }
    });

  }

  public void signInWithEmailAndPassword(String email, String password, Result callback) {
    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) callback.isOkay();
        else
          callback.error("Sorry, something happened, could not sign you in. Please try again!");

      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        callback.error(e.getMessage());
        Log.d("FIREBASE_REG_HELPER", "onFailure: " + e.getMessage());
      }
    });
  }

  public void signOut(){
    mAuth.signOut();
  }


  interface Result {
    void isOkay();

    void error(String error);
  }

  @FunctionalInterface
  interface RelayCallback {
    void next(Object anything);
  }

  @FunctionalInterface
  interface RegistrationCallback {
    void next();
  }

  interface ActivityResultsCallback {
    void googleDialogAuthIsOkay(String idToken);

    void error(String error);
  }
}
