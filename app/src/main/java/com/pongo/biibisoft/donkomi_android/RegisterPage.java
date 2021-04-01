package com.pongo.biibisoft.donkomi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterPage extends AppCompatActivity {

  private static final String TAG = "RegisterPage";
  ImageView backBtn, rightIcon;
  TextView pageName;
  Button finishBtn, useGoogleBtn, completeBtn, cancelBtn;
  Activity _this;
  EditText email, phone, password, confirmPassword, firstName, lastName;
  Spinner org_dropdown, gender_dropdown;
  FirebaseUser fireUser;
  FirebaseAuth mAuth;
  private DonkomiUser userObj;
  String selectedGender = "OTHER";
  Organization selectedOrg;
  MagicBoxes dialogCreator;
  Dialog loadingDialog;
  private GoogleSignInClient mGoogleSignInClient;
  RegisterPageViewModel registrationHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register_page);
    registrationHandler = new ViewModelProvider(this).get(RegisterPageViewModel.class);
    mAuth = FirebaseAuth.getInstance();
    _this = this;
    setGoogleDialogUp();
    initialize();
    setObservers();
  }

  public void setObservers() {
    registrationHandler.getToastMsg().observe(this, new Observer<String>() {
      @Override
      public void onChanged(String s) {
        Toast.makeText(_this, s, Toast.LENGTH_SHORT).show();
      }
    });

    registrationHandler.shouldBtnsFlip().observe(this, new Observer<Boolean>() {
      @Override
      public void onChanged(Boolean flipped) {
        if (!flipped) {
          cancelBtn.setVisibility(View.GONE);
          completeBtn.setVisibility(View.GONE);
          finishBtn.setVisibility(View.VISIBLE);
          useGoogleBtn.setVisibility(View.VISIBLE);
        } else {
          cancelBtn.setVisibility(View.VISIBLE);
          completeBtn.setVisibility(View.VISIBLE);
          finishBtn.setVisibility(View.GONE);
          useGoogleBtn.setVisibility(View.GONE);
        }
      }
    });

    registrationHandler.userObj().observe(this, new Observer<DonkomiUser>() {
      @Override
      public void onChanged(DonkomiUser userObj) {
        firstName.setText(userObj.getFirstName());
        lastName.setText(userObj.getLastName());
        email.setText(userObj.getEmail());
        phone.setText(userObj.getPhone());
      }
    });

    registrationHandler.getLoaderState().observe(this, new Observer<Boolean>() {
      @Override
      public void onChanged(Boolean loading) {
        if(loading) loadingDialog.show();
        else loadingDialog.dismiss();
      }
    });


  }

  public void initialize() {
    initializeLoader();
    completeBtn = findViewById(R.id.complete_btn);
    cancelBtn = findViewById(R.id.cancel_btn);
    useGoogleBtn = findViewById(R.id.use_google_btn);
    useGoogleBtn.setOnClickListener(launchGoogleRegistration);
    completeBtn.setOnClickListener(completeGoogleRegistration);
    cancelBtn.setOnClickListener(cancelGoogleRegistration);
    email = findViewById(R.id.email);
    phone = findViewById(R.id.phone);
    password = findViewById(R.id.password);
    firstName = findViewById(R.id.first_name);
    lastName = findViewById(R.id.last_name);
    confirmPassword = findViewById(R.id.confirm_passwords);
    finishBtn = findViewById(R.id.finish_btn);
    finishBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        loadingDialog.show();
//        finishNormalRegistration();
//        Intent page = new Intent(_this, HomeContainerPage.class);
//        startActivity(page);
      }
    });
    gender_dropdown = findViewById(R.id.gender_dropdown);
    org_dropdown = findViewById(R.id.organistion_dropdown);
    ArrayAdapter<String> dropdownAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Konstants.ORGANISATIONS);
    ArrayAdapter<String> genderAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Konstants.GENDER);
    org_dropdown.setAdapter(dropdownAdapter);
    gender_dropdown.setAdapter(genderAdapter);
    gender_dropdown.setOnItemSelectedListener(onGenderSelected);
    org_dropdown.setOnItemSelectedListener(onOrgSelected);
    pageName = findViewById(R.id.page_name);
    pageName.setText(R.string.create_account_text);
    rightIcon = findViewById(R.id.right_icon);
    rightIcon.setVisibility(View.GONE);
    backBtn = findViewById(R.id.back_icon);
    backBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

  }

  private void initializeLoader() {
    dialogCreator = new MagicBoxes(this);
    View loadingView = LayoutInflater.from(_this).inflate(R.layout.simple_loading_dialog, null, false);
    TextView loadingText = loadingView.findViewById(R.id.loader_text);
    loadingText.setText(R.string.create_account_text_loading_bar);
    loadingDialog = dialogCreator.constructLoadingCustomDialog(loadingView);
    loadingDialog.setCanceledOnTouchOutside(false);
  }

  private final View.OnClickListener launchGoogleRegistration = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      loadingDialog.show();
      registerWithGoogle();
    }
  };

  public Boolean contentIsValid() {
    if (email.getText() == null || email.getText().toString().isEmpty()) {
      Toast.makeText(this, "Please input a valid email", Toast.LENGTH_SHORT).show();
      email.requestFocus();
      email.setError("Please input a valid email");
      return false;
    }
    if (firstName.getText() == null || firstName.getText().toString().isEmpty()) {
      Toast.makeText(this, "Please enter a valid first name", Toast.LENGTH_SHORT).show();
      firstName.requestFocus();
      firstName.setError("Please enter a valid first name");
      return false;

    }
    if (lastName.getText() == null || lastName.getText().toString().isEmpty()) {
      Toast.makeText(this, "Please enter a valid last name", Toast.LENGTH_SHORT).show();
      lastName.requestFocus();
      lastName.setError("Please enter a valid last name");
      return false;
    }

    if (phone.getText() == null || phone.getText().toString().isEmpty() || phone.getText().toString().length() < 8) {
      Toast.makeText(this, "Please input a valid phone number", Toast.LENGTH_SHORT).show();
      phone.requestFocus();
      phone.setError("Please input a valid phone number");
      return false;
    }

    if (password.getText() == null || password.getText().toString().isEmpty()) {
      Toast.makeText(this, "Please input a valid password", Toast.LENGTH_SHORT).show();
      password.requestFocus();
      password.setError("Please input a valid password");
      return false;
    }
    if (password.getText().toString().length() < 6) {
      Toast.makeText(this, "Your password must have 6 or more characters", Toast.LENGTH_SHORT).show();
      password.requestFocus();
      password.setError("Your password must have 6 or more characters");
      return false;
    }


    if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
      Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
      confirmPassword.requestFocus();
      confirmPassword.setError("Passwords do not match");
      return false;
    }

    return true;
  }

  // Create a donkomi object from the details given by the user
  public DonkomiUser createCombinedUserObject(String platformID, boolean isGoogle) {
    if (!isGoogle)
      userObj = new DonkomiUser(MyHelper.getTextFrom(email), MyHelper.getTextFrom(firstName), MyHelper.getTextFrom(lastName), platformID);
    else
      userObj = new DonkomiUser(fireUser.getEmail(), fireUser.getDisplayName(), null, fireUser.getUid());
    userObj.setOrganization(selectedOrg);
    userObj.setGender(selectedGender);
    return userObj;
  }

  // Create new user user with email and password in firebase
  public void finishNormalRegistration() {
    if (contentIsValid()) {
      mAuth.createUserWithEmailAndPassword(MyHelper.getTextFrom(email), MyHelper.getTextFrom(password))
          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()) {
//                Toast.makeText(RegisterPage.this, "Successfully Created Your Account", Toast.LENGTH_SHORT).show();
                fireUser = mAuth.getCurrentUser();
                userObj = createCombinedUserObject(fireUser.getUid(), false);
                createBackendDonkomiUser(userObj, new DonkomiInterfaces.Callback() {
                  @Override
                  public void next() {
                    transitionToHomePage();
                  }
                });
              } else {
                loadingDialog.dismiss();
                Log.w("RegPageEmail&PErr::", task.getException().getMessage());
                Toast.makeText(RegisterPage.this, "Oops, something happened! " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
              }
            }
          }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          Log.w("RegPageEmail&PErr::", "withEmailAndPasswordException:" + e.getMessage());
          Toast.makeText(RegisterPage.this, "Oops! " + e.getMessage(), Toast.LENGTH_SHORT).show();
          loadingDialog.dismiss();
        }
      });
    } else loadingDialog.dismiss();
  }

  private void goToProfileCompletionPage(DonkomiUser user) {
    Intent page = new Intent(this, ClientAllPagesContainer.class);
    page.putExtra(Konstants.FORM_FOR, Konstants.EDIT_PROFILE_FORM);
    page.putExtra(Konstants.USER, user);
    startActivity(page);
  }

  private void transitionToHomePage() {
    Intent homePage = new Intent(this, HomeContainerPage.class);
    homePage.putExtra(Konstants.FORM_FOR, Konstants.EDIT_PROFILE_FORM);
    startActivity(homePage);
  }

  // Google Registration Step 2
  private void setGoogleDialogUp() {
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build();
    //attach google sign in options to Google Dialog
    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
  }

  // Google Registration Step 1
  public void registerWithGoogle() {
    Intent withGoogle = mGoogleSignInClient.getSignInIntent();
    startActivityForResult(withGoogle, Konstants.GOOGLE_SIGN_UP_CODE);
  }

  // Google Registration Step 3
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == Konstants.GOOGLE_SIGN_UP_CODE) {
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      try {
        GoogleSignInAccount account = task.getResult(ApiException.class);
        firebaseAuthWithGoogle(account.getIdToken());
      } catch (Exception e) {
        e.printStackTrace();
        Log.d(TAG, "onActivityResult: GoogleError" + e.getLocalizedMessage());
        Toast.makeText(this, "Oops! Failed to sign up with google! " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
      }
    }
//    loadingDialog.dismiss();
  }

  // Google Registration Step 4
  private void firebaseAuthWithGoogle(String token) {
    AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              // Sign in success, update UI with the signed-in user's information
              fireUser = mAuth.getCurrentUser();
              userObj = createCombinedUserObject(fireUser.getUid(), true);
//              createBackendDonkomiUser(userObj, new DonkomiInterfaces.Callback() {
//                @Override
//                public void next() {
//                  goToProfileCompletionPage(userObj);
//                  finish();
//                }
//              });
            } else {
              // If sign in fails, display a message to the user.
              Log.w(TAG, "signUpWithCredential:failure", task.getException());
              registrationHandler.setToastMsg("Oops, couldn't sign you up with google!");
              registrationHandler.toggleLoader();
//              Toast.makeText(RegisterPage.this, "Oops, couldn't sign you up with google! " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
//              loadingDialog.dismiss();
            }

          }
        });
  }

  private void createBackendDonkomiUser(DonkomiUser user, DonkomiInterfaces.Callback callback) {
    try {
      registrationHandler.explorer().setData(user.parseIntoInternetData());
      registrationHandler.explorer().run(DonkomiURLS.REGISTER_USER, new Result() {
        @Override
        public void isOkay(JSONObject response) {
          ResponseHandler handler = new ResponseHandler(response);
          try {
            if (handler.hasError()) {
              Log.d(TAG, "isOkay: " + handler.getErrorMessage());
              Toast.makeText(_this, handler.getErrorMessage(), Toast.LENGTH_LONG).show();
              loadingDialog.dismiss();
            } else {
              callback.next();
//              finish();
            }
          } catch (JSONException e) {
            loadingDialog.dismiss();
            Toast.makeText(_this, "Sorry, something happened please retry!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
          }
        }

        @Override
        public void error(String error) {
          Toast.makeText(_this, error, Toast.LENGTH_SHORT).show();
          loadingDialog.dismiss();
        }
      });
    } catch (JSONException e) {
      e.printStackTrace();
      Toast.makeText(_this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
      loadingDialog.dismiss();
    }


  }


  private final AdapterView.OnItemSelectedListener onGenderSelected = new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      selectedGender = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
  };

  private final AdapterView.OnItemSelectedListener onOrgSelected = new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      // Initially hardcoded, but will be changed to get dynamic values when more organizations join
      String orgName = parent.getItemAtPosition(position).toString();
      selectedOrg = new Organization(orgName, Konstants.MAURITIUS, 1);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
  };

  private final View.OnClickListener cancelGoogleRegistration = new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
  };
  private final View.OnClickListener completeGoogleRegistration = new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
  };
}