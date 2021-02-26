package com.pongo.biibisoft.donkomi_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

  Activity _this;
  Button finishBtn;
  MagicBoxes dialogCreator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_page);
    _this = this;
    initialize();
  }


  public void initialize() {
    dialogCreator = new MagicBoxes(this);
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
        View loadingView = LayoutInflater.from(_this).inflate(R.layout.simple_loading_dialog, null, false);
        dialogCreator.constructLoadingCustomDialog(loadingView).show();
//        Intent page = new Intent(_this, RegisterPage.class);
//        startActivity(page);

//        View loadingView = LayoutInflater.from(_this).inflate(R.layout.simple_loading_dialog, null, false);
//
//        dialogCreator.constructCustomDialog("Loading", loadingView, new MagicBoxCallables() {
//          @Override
//          public void negativeBtnCallable() {
//
//          }
//
//          @Override
//          public void positiveBtnCallable() {
//
//          }
//        }).show();
      }
    });
  }
}