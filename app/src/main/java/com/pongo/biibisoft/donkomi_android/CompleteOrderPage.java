package com.pongo.biibisoft.donkomi_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class CompleteOrderPage extends AppCompatActivity {

  RecyclerView recyclerView ;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_complete_order_page);
    initialize();
  }


  public void initialize(){
    recyclerView = findViewById(R.id.order_items_recycler);
    LinearLayoutManager manager = new LinearLayoutManager(this);
    OrderItemsRecycler adapter = new OrderItemsRecycler();
    recyclerView.setLayoutManager(manager);
    recyclerView.setAdapter(adapter);

  }
}