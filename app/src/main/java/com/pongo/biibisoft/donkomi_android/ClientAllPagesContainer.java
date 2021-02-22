package com.pongo.biibisoft.donkomi_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ClientAllPagesContainer extends AppCompatActivity {

  TextView pageName;
  ImageView backBtn, rightIcon;
  Activity _this;
  String CURRENT_PAGE = Konstants.EDIT_PROFILE_FORM;
  //  String FORM_FOR ;
  RelativeLayout editProfileForm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client_all_pages_container);
    _this = this;
    initialize();
  }


  public void generateForm() {
    CURRENT_PAGE = CURRENT_PAGE == null ? Konstants.EDIT_PROFILE_FORM : CURRENT_PAGE;
    if (CURRENT_PAGE == Konstants.EDIT_PROFILE_FORM) {
      editProfileForm.setVisibility(View.VISIBLE);
    }
  }

  public void initialize() {
    editProfileForm = findViewById(R.id.edit_profile_form);
    CURRENT_PAGE = getIntent().getStringExtra(Konstants.FORM_FOR);
    pageName = findViewById(R.id.page_name);
    pageName.setText("Edit Your Profile");
    backBtn = findViewById(R.id.back_icon);
    rightIcon = findViewById(R.id.right_icon);
    rightIcon.setVisibility(View.INVISIBLE);
    backBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    generateForm();

  }
}