package com.pongo.biibisoft.donkomi_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeContainerPage extends AppCompatActivity {

  TextView pageName;
  ImageView backBtn, rightIcon;
  RecyclerView recyclerView;
  BottomNavigationView navigation;
  Fragment currentFragment;
  Context thisActivity;
  HomePageViewModel homeHandler;
  ClientFragmentsViewModel tabHandler;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_container_page);
    thisActivity = this;
    homeHandler = new ViewModelProvider(this).get(HomePageViewModel.class);
    tabHandler = new ViewModelProvider(this).get(ClientFragmentsViewModel.class);
    tabHandler.handleTravellingContent(getIntent());
    homeHandler.handleTravellingContent(getIntent());
    initialize();
  }


  public void initialize() {
    rightIcon = findViewById(R.id.right_icon);
    rightIcon.setOnClickListener(goToCartPage);
    navigation = findViewById(R.id.bottom_nav);
    navigation.setOnNavigationItemSelectedListener(navItemSelected);
    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ClientHomeFragment()).commit();
    backBtn = findViewById(R.id.back_icon);
    backBtn.setVisibility(View.GONE);
    pageName = findViewById(R.id.page_name);
    pageName.setText("Donkomi");


  }

  private final View.OnClickListener goToCartPage = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      Intent page = new Intent(thisActivity, CompleteOrderPage.class);
      startActivity(page);
    }
  };
  private final BottomNavigationView.OnNavigationItemSelectedListener navItemSelected =
      new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          currentFragment = null;
          int menuID = item.getItemId();
          if (menuID == R.id.live_trips_menu_item) {
            currentFragment = new ClientHomeFragment();
          } else if (menuID == R.id.shops_menu_item) {
            currentFragment = new ClientShopsPageFragment();
          } else if (menuID == R.id.settings_menu_item) {
            currentFragment = new SettingsFragmentPage();
            ((SettingsFragmentPage) currentFragment).setContext(thisActivity);
            ((SettingsFragmentPage) currentFragment).setPageHandler(tabHandler);

          } else if (menuID == R.id.client_side_guru_management_menu_item) {
            Intent page = new Intent(thisActivity, GuruLandingPage.class);
            startActivity(page);
          }

          if (currentFragment != null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, currentFragment).commit();
            return true;
          }
          return false;
        }
      };
}

