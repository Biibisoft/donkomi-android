package com.pongo.biibisoft.donkomi_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {


  FirebaseAuth mAuth = FirebaseAuth.getInstance();
  ImageView logo;
  InternetExplorer explorer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);
    checkThenRedirectIf();
    initialize();
  }


  public void checkThenRedirectIf() {
    FirebaseUser user = mAuth.getCurrentUser();
    explorer = new InternetExplorer(this);
    if (user != null) {
      explorer.setExpectedDataType(DonkomiUser.class);
      explorer.expectsArray(false);
      explorer.setMethod(InternetExplorer.GET);
      explorer.runAndFindData(explorer.endSlash(DonkomiURLS.GET_USER + user.getUid()), new ResultWithData() {
        @Override
        public void isOkay(JSONObject response) throws JSONException {

        }

        @Override
        public void getData(Object data) {
          DonkomiUser user = (DonkomiUser) data;
          Intent home = new Intent(SplashScreen.this, HomeContainerPage.class);
          home.putExtra(Konstants.USER, user);
          startActivity(home);
          finish();
        }

        @Override
        public void getDataArray(Object data) {

        }

        @Override
        public void error(String error) {

        }
      });

    } else {
      goToNextPage();
      finish();
    }
  }

  public void initialize() {
    logo = findViewById(R.id.donkomi_logo);
    logo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        goToNextPage();
      }
    });
  }

  public void goToNextPage() {
    Intent page = new Intent(this, LoginPage.class);
    startActivity(page);
  }
}