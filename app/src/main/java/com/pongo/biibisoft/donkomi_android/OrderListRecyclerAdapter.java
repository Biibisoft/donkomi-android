package com.pongo.biibisoft.donkomi_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderListRecyclerAdapter extends RecyclerView.Adapter<OrderListRecyclerAdapter.OrderListViewHolder> {


  @NonNull
  @Override
  public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_card, parent, false);
    return new OrderListViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull OrderListViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 8;
  }

  public class OrderListViewHolder extends RecyclerView.ViewHolder{

    public OrderListViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
