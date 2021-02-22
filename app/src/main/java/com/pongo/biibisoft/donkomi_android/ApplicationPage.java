package com.pongo.biibisoft.donkomi_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ApplicationPage extends AppCompatActivity {

  ImageView rightIcon, backBtn;
  TextView pageName;
  Spinner dropdown;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_application_page);
    initialize();
  }

  public void initialize() {
    backBtn = findViewById(R.id.back_icon);
    backBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    rightIcon = findViewById(R.id.right_icon);
    rightIcon.setVisibility(View.GONE);
    pageName = findViewById(R.id.page_name);
    pageName.setText("Donkomi Application");
    dropdown = findViewById(R.id.earning_options_dropdown);
    ArrayAdapter<String> earningOptions = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Konstants.EARNING_OPTIONS);
    dropdown.setAdapter(earningOptions);

  }
}