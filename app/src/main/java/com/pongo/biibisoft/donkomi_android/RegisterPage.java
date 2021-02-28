package com.pongo.biibisoft.donkomi_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPage extends AppCompatActivity {

  ImageView backBtn, rightIcon;
  TextView pageName;
  Button finishBtn;
  Activity _this;
  EditText email, phone, password, confirmPassword, firstName, lastName;
  Spinner org_dropdown, gender_dropdown;
  FirebaseUser fireUser;
  FirebaseAuth mAuth;
  private DonkomiUser userObj;
  String selectedGender = "Other";
  Organization selectedOrg;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register_page);
    mAuth = FirebaseAuth.getInstance();
    _this = this;
    initialize();
  }


  public Boolean contentIsValid() {
    if (email.getText() == null || email.getText().toString().isEmpty()) {
      Toast.makeText(this, "Please input a valid email", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (firstName.getText() == null || firstName.getText().toString().isEmpty()) {
      Toast.makeText(this, "Please enter a valid first name", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (lastName.getText() == null || lastName.getText().toString().isEmpty()) {
      Toast.makeText(this, "Please enter a valid last name", Toast.LENGTH_SHORT).show();
      return false;
    }

    if (phone.getText() == null || phone.getText().toString().isEmpty()) {
      Toast.makeText(this, "Please input a valid phone number", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (phone.getText() == null || phone.getText().toString().isEmpty() || phone.getText().toString().length() < 8) {
      Toast.makeText(this, "Please input a valid phone number", Toast.LENGTH_SHORT).show();
      return false;
    }

    if (password.getText() == null || password.getText().toString().isEmpty()) {
      Toast.makeText(this, "Please input a valid password", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (password.getText().toString().length() < 6) {
      Toast.makeText(this, "Your password must have 6 or more characters", Toast.LENGTH_SHORT).show();
      return false;
    }


    if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
      Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
      return false;
    }

    return true;
  }

  // Create a donkomi object from the details given by the user
  public void createCombinedUserObject() {

//    userObj = new DonkomiUser(MyHelper.getTextFrom(email),MyHelper.getTextFrom())
  }

  // Create new user user with email and password in firebase
  public void finishNormalRegistration() {
    if (contentIsValid()) {
      mAuth.createUserWithEmailAndPassword(MyHelper.getTextFrom(email), MyHelper.getTextFrom(password))
          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()) {
                Toast.makeText(RegisterPage.this, "Successfully Created Your Account", Toast.LENGTH_SHORT).show();
                fireUser = mAuth.getCurrentUser();

              } else {
                Log.w("RegPageEmail&PErr::", task.getException().getMessage());
                Toast.makeText(RegisterPage.this, "Oops, something happened! " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
              }
            }
          }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          Log.w("RegPageEmail&PErr::", "withEmailAndPasswordException:" + e.getMessage());
          Toast.makeText(RegisterPage.this, "Oops! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
      });
    }
  }

  public void initialize() {
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
    pageName.setText("Create An Account");
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

  private AdapterView.OnItemSelectedListener onGenderSelected = new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      selectedGender = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
  };

  private AdapterView.OnItemSelectedListener onOrgSelected = new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      // Initially hardcoded, but will be changed to get dynamic values when more organizations join
      String orgName = parent.getItemAtPosition(position).toString();
      selectedOrg = new Organization(orgName, Konstants.MAURITIUS);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
  };


}