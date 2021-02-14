package com.pongo.biibisoft.donkomi_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderItemsRecycler extends RecyclerView.Adapter<OrderItemsRecycler.OrderItemsViewHolder> {


  @NonNull
  @Override
  public OrderItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_card, parent, false);

    return new OrderItemsViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull OrderItemsViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 8;
  }

  public class OrderItemsViewHolder extends RecyclerView.ViewHolder {

    public OrderItemsViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
