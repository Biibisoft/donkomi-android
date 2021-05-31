package com.pongo.biibisoft.donkomi_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GuruLandingPage extends AppCompatActivity {


  ImageView backBtn;
  TextView pageName;
  GuruLandingPageViewModel pageHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_guru_landing_page);
    pageHandler = new ViewModelProvider(this).get(GuruLandingPageViewModel.class);

    initialize();
  }

  public void initialize() {
    pageHandler.getAllGuruVendors();
    pageName = findViewById(R.id.page_name);
    pageName.setText(R.string.Delivery_guru);
    backBtn = findViewById(R.id.back_icon);
    backBtn.setOnClickListener(goBack);
    BottomNavigationView bottomNav = findViewById(R.id.guru_bottom_nav);
    bottomNav.setOnNavigationItemSelectedListener(itemSelected);
    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new GuruRoutineFragment()).commit();
  }

  private View.OnClickListener goBack = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      finish();
    }
  };

  private BottomNavigationView.OnNavigationItemSelectedListener itemSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      Fragment page = null;

      if (item.getItemId() == R.id.routines_menu_item) {
        page = new GuruRoutineFragment();
      }
      if (item.getItemId() == R.id.orders_menu_item) {
        page = new GuruOrdersFragment();
      }
      if (item.getItemId() == R.id.management_menu_item) {
        page = new GuruManagementFragment(pageHandler);

      }
      if (item.getItemId() == R.id.guru_side_client_menu_item) {
        finish();
      }

      if (page != null) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, page).commit();
        return true;
      }

      return false;
    }
  };
}