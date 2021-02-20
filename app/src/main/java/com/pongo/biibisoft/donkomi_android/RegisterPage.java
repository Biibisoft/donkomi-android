package com.pongo.biibisoft.donkomi_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterPage extends AppCompatActivity {

  ImageView backBtn, rightIcon;
  TextView pageName;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register_page);
    initialize();
  }


  public void initialize(){
    pageName = findViewById(R.id.page_name);
    pageName.setText("Create An Account");
    rightIcon  = findViewById(R.id.right_icon);
    rightIcon.setVisibility(View.GONE);
    backBtn  = findViewById(R.id.back_icon);
    backBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

  }
}