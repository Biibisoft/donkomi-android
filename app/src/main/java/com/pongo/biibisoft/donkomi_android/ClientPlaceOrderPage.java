package com.pongo.biibisoft.donkomi_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ClientPlaceOrderPage extends AppCompatActivity {

  RecyclerView recyclerView, productsRecyclerView;
  ImageView backBtn;
  TextView pageName;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client_place_order_page);
    initialize();
  }

  public void initialize(){
    pageName = findViewById(R.id.page_name);
    pageName.setText("Place Your Order ");
    backBtn = findViewById(R.id.back_icon);
    backBtn.setOnClickListener(goBack);
    recyclerView = findViewById(R.id.shops_recycler);
    HorizontalShopsRecyclerAdapter adapter = new HorizontalShopsRecyclerAdapter();
    LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
    recyclerView.setLayoutManager(manager);
    recyclerView.setAdapter(adapter);
    productsRecyclerView = findViewById(R.id.product_list_recycler);
    ProductListRecyclerAdapter productsAdapter = new ProductListRecyclerAdapter();
    LinearLayoutManager productsManager = new LinearLayoutManager(this);
    productsRecyclerView.setLayoutManager(productsManager);
    productsRecyclerView.setAdapter(productsAdapter);



  }

  private View.OnClickListener goBack = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      finish();
    }
  };
}