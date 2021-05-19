package com.pongo.biibisoft.donkomi_android;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
  Role selectedRole;

  Button applyBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_application_page);
    authUser = getIntent().getParcelableExtra(Konstants.USER);
    initialize();
  }

  public void initialize() {
    rawLoaderBox = findViewById(R.id.raw_loader_box); //progress circle
    applyBtn = findViewById(R.id.submit_app);
    applyBtn.setOnClickListener(apply);
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
    if (authUser != null) {
      firstName.setText(authUser.getFirstName());
      lastName.setText(authUser.getLastName());
    }
    loadRolesAndFill();
  }


  private final AdapterView.OnItemSelectedListener selectRoleItemListener = new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      selectedRole = roles[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
  };

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
        dropdown.setOnItemSelectedListener(selectRoleItemListener);
        rawLoaderBox.setVisibility(View.GONE);

      }

      @Override
      public void error(String error) {
        rawLoaderBox.setVisibility(View.GONE);
        Toast.makeText(ApplicationPage.this, error, Toast.LENGTH_LONG).show();

      }
    });

  }


  private final View.OnClickListener apply = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      rawLoaderBox.setVisibility(View.VISIBLE);
      sendApplication();
    }
  };

  public void sendApplication() {
    try {
      explorer.setData(makeRequestData());
      explorer.setMethod(InternetExplorer.POST);
      explorer.run(explorer.endSlash(DonkomiURLS.SEND_APPLICATION), new Result() {
        @Override
        public void isOkay(JSONObject response) throws JSONException {
          ResponseHandler handler = new ResponseHandler(response);
          if (handler.hasError())
            Toast.makeText(ApplicationPage.this, "Error here : " + handler.getErrorMessage(), Toast.LENGTH_SHORT).show();
          else
            Toast.makeText(ApplicationPage.this, "Application was submitted", Toast.LENGTH_LONG).show();
          rawLoaderBox.setVisibility(View.GONE);
        }

        @Override
        public void error(String error) {
          Toast.makeText(ApplicationPage.this, "Error : " + error, Toast.LENGTH_SHORT).show();
          rawLoaderBox.setVisibility(View.GONE);
        }
      });
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public JSONObject makeRequestData() throws JSONException {
    JSONObject json = new JSONObject();
    json.put("user_id", authUser.getPlatformID());
    if (selectedRole != null) json.put("role_level", selectedRole.getAccessLevel());
    Log.d(TAG, "makeRequestData: "+ json.toString());
    return json;
  }
}