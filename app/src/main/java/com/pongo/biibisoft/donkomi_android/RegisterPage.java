package com.pongo.biibisoft.donkomi_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class RegisterPage extends AppCompatActivity {

  ImageView backBtn, rightIcon;
  TextView pageName;
  Button finishBtn;
  Activity _this;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register_page);
    _this = this;
    initialize();
  }


  public void initialize(){
    finishBtn = findViewById(R.id.finish_btn);
    finishBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent page = new Intent(_this,HomeContainerPage.class);
        startActivity(page);
      }
    });
    Spinner org_dropdown = findViewById(R.id.organistion_dropdown);
    ArrayAdapter<String> dropdownAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,Konstants.ORGANISATIONS);
    org_dropdown.setAdapter(dropdownAdapter);
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