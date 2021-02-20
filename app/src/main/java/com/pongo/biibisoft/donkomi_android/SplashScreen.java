package com.pongo.biibisoft.donkomi_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {


  ImageView logo;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);
    initialize();
  }

  public void initialize(){
    logo = findViewById(R.id.donkomi_logo);
    logo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        goToNextPage();
      }
    });
  }

  public void goToNextPage(){
    Intent page = new Intent(this, LoginPage.class);
    startActivity(page);
  }
}