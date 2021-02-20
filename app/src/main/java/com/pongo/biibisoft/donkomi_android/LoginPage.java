package com.pongo.biibisoft.donkomi_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

  Activity _this;
  Button finishBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_page);
    _this = this;
    initialize();
  }


  public void initialize() {
    finishBtn = findViewById(R.id.finish_btn);
    finishBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent page = new Intent(_this, HomeContainerPage.class);
        startActivity(page);
      }
    });
    TextView createNewAccount = findViewById(R.id.new_account_label);
    createNewAccount.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent page = new Intent(_this, RegisterPage.class);
        startActivity(page);
      }
    });
  }
}