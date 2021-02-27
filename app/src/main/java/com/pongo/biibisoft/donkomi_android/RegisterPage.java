package com.pongo.biibisoft.donkomi_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity {

  ImageView backBtn, rightIcon;
  TextView pageName;
  Button finishBtn;
  Activity _this;
  EditText email, phone, password, confirmPassword;
  FirebaseAuth mAuth;

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

  public void finishNormalRegistration() {
    if (contentIsValid()) {

      Toast.makeText(this, "YOu have input great content", Toast.LENGTH_SHORT).show();
    }
  }

  public void initialize() {
    email = findViewById(R.id.email);
    phone = findViewById(R.id.phone);
    password = findViewById(R.id.password);
    confirmPassword = findViewById(R.id.confirm_passwords);
    finishBtn = findViewById(R.id.finish_btn);
    finishBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finishNormalRegistration();
//        Intent page = new Intent(_this, HomeContainerPage.class);
//        startActivity(page);
      }
    });
    Spinner org_dropdown = findViewById(R.id.organistion_dropdown);
    ArrayAdapter<String> dropdownAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Konstants.ORGANISATIONS);
    org_dropdown.setAdapter(dropdownAdapter);
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
}