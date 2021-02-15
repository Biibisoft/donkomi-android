package com.pongo.biibisoft.donkomi_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GuruLandingPage extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_guru_landing_page);
    initialize();
  }


  public void initialize() {

    BottomNavigationView bottomNav = findViewById(R.id.guru_bottom_nav);
    bottomNav.setOnNavigationItemSelectedListener(itemSelected);
    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new GuruRoutineFragment()).commit();
  }

  private BottomNavigationView.OnNavigationItemSelectedListener itemSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      Fragment page = null;

      if (item.getItemId() == R.id.routines_menu_item) {
        page = new GuruRoutineFragment();
      }
      if (item.getItemId() == R.id.orders_menu_item){
        page = new GuruOrdersFragment();
      }

      if (page != null) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, page).commit();
        return true;
      }

      return false;
    }
  };
}