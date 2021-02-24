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

public class CompleteOrderPage extends AppCompatActivity {

  RecyclerView recyclerView;

  TextView pageName;
  private ImageView backBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_complete_order_page);
    initialize();
  }


  public void initialize() {
    pageName = findViewById(R.id.page_name);
    pageName.setText("Complete Your Order");
    backBtn = findViewById(R.id.back_icon);
    backBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    BottomNavigationView nav = findViewById(R.id.order_bottom_nav);
    nav.setOnNavigationItemSelectedListener(navItemSelected);
    ClientCartFragment page = new ClientCartFragment();
    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, page).commit();

  }

  private BottomNavigationView.OnNavigationItemSelectedListener navItemSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      Fragment page = null;
      if (item.getItemId() == R.id.cart_menu_item) {
        page = new ClientCartFragment();
      } else if (item.getItemId() == R.id.finished_menu_item) {
        page = new ClientFinishedOrdersFragment();
      } else if (item.getItemId() == R.id.history_menu_item) {
        page = new ClientOrderHistoryFragment();
      }
      if (page != null) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, page).commit();

        return true;
      }
      return false;
    }
  };
}