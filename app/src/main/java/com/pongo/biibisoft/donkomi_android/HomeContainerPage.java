package com.pongo.biibisoft.donkomi_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeContainerPage extends AppCompatActivity {

  TextView pageName;
  ImageView backBtn;
  RecyclerView recyclerView;
  BottomNavigationView navigation;
  Fragment currentFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_container_page);
    initialize();
  }


  public void initialize() {
    navigation = findViewById(R.id.bottom_nav);
    navigation.setOnNavigationItemSelectedListener(navItemSelected);
//    recyclerView = findViewById(R.id.live_trips_recycler);
//    LinearLayoutManager manager = new LinearLayoutManager(this);
//    LiveTripsRecyclerAdapter adapter = new LiveTripsRecyclerAdapter();
//    recyclerView.setLayoutManager(manager);
//    recyclerView.setAdapter(adapter);
//    recyclerView.hasFixedSize();
    backBtn = findViewById(R.id.back_icon);
    backBtn.setVisibility(View.GONE);
    pageName = findViewById(R.id.page_name);
    pageName.setText("Donkomi");


  }

  private BottomNavigationView.OnNavigationItemSelectedListener navItemSelected =
      new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          currentFragment = null;
          switch (item.getItemId()) {
            case R.id.live_trips_menu_item:
              currentFragment = new ClientHomeFragment();
              break;
          }
          getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, currentFragment).commit();
          return true;
        }
      };
}

