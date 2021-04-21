package com.pongo.biibisoft.donkomi_android;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ApplicationPage extends AppCompatActivity {

  ImageView rightIcon, backBtn;
  TextView pageName;
  Spinner dropdown;
  MagicBoxes dialogCreator;
  DonkomiUser authUser;
  InternetExplorer explorer;
  EditText firstName, lastName;
  Dialog loader;
  Gson gson = new Gson();
  RelativeLayout rawLoaderBox;
  public static final String TAG = "ALL_APPLICATION_PAGE";
  Role[] roles;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_application_page);
    authUser = getIntent().getParcelableExtra(Konstants.USER);
    initialize();
  }

  public void initialize() {
    rawLoaderBox = findViewById(R.id.raw_loader_box);
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
    firstName = findViewById(R.id.first_name);
    lastName = findViewById(R.id.last_name);
    pageName = findViewById(R.id.page_name);
    pageName.setText(R.string.donkomi_application);
    dropdown = findViewById(R.id.role_to_apply_for);
//    ArrayAdapter<String> earningOptions = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Konstants.EARNING_OPTIONS);
//    dropdown.setAdapter(earningOptions);
    if (authUser != null) {
      firstName.setText(authUser.getFirstName());
      lastName.setText(authUser.getLastName());
    }
    loadRolesAndFill();
  }

  public void loadRolesAndFill() {
    explorer.setMethod(InternetExplorer.GET);
    explorer.setExpectedDataType(Role[].class);
    explorer.runAndFindData(DonkomiURLS.GET_ALL_ROLES, new ResultWithData() {
      @Override
      public void isOkay(JSONObject response) throws JSONException {

      }

      @Override
      public void getData(Object data) {

      }

      @Override
      public void getDataArray(Object data) {
        roles = (Role[]) data;
        ArrayAdapter<Role> roleAdapter = new ArrayAdapter<Role>(ApplicationPage.this, android.R.layout.simple_list_item_1, roles);
        dropdown.setAdapter(roleAdapter);
        rawLoaderBox.setVisibility(View.GONE);

      }

      @Override
      public void error(String error) {
        rawLoaderBox.setVisibility(View.GONE);
        Toast.makeText(ApplicationPage.this, error , Toast.LENGTH_LONG).show();

      }
    });

  }



  public void sendApplication() {
    try {
      explorer.setData(makeRequestData());
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public JSONObject makeRequestData() throws JSONException {
    JSONObject json = new JSONObject();
    json.put("user_id", authUser.getPlatformID());
    json.put("role_level", 2);
    return json;
  }
}