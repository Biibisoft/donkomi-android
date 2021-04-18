package com.pongo.biibisoft.donkomi_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ApplicationPage extends AppCompatActivity {

  ImageView rightIcon, backBtn;
  TextView pageName;
  Spinner dropdown;
  MagicBoxes dialogCreator ;
  DonkomiUser authUser;
  InternetExplorer explorer;
  Dialog loader;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_application_page);
    authUser = getIntent().getParcelableExtra(Konstants.USER);
    initialize();
  }

  public void initialize() {
    dialogCreator = new MagicBoxes(this);
    explorer = new InternetExplorer(this);
    loader = dialogCreator.constructLoadingDialog("Sending...");
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
    pageName.setText(R.string.donkomi_application);
    dropdown = findViewById(R.id.earning_options_dropdown);
    ArrayAdapter<String> earningOptions = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Konstants.EARNING_OPTIONS);
    dropdown.setAdapter(earningOptions);
  }


  public void sendApplication(){

    try {
      explorer.setData(makeRequestData());
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public JSONObject makeRequestData() throws JSONException {
      JSONObject json = new JSONObject();
      json.put("user_id",authUser.getPlatformID());
      json.put("firstName", authUser.getFirstName());
      json.put("lastName",authUser.getLastName());
      json.put("role",12);
      return json;
  }
}