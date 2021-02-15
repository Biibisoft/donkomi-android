package com.pongo.biibisoft.donkomi_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GuruOrdersRecyclerAdapter  extends RecyclerView.Adapter<GuruOrdersRecyclerAdapter.GuruOrdersViewHolder> {


  @NonNull
  @Override
  public GuruOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_card, parent, false);
    return new GuruOrdersViewHolder(v);

  }

  @Override
  public void onBindViewHolder(@NonNull GuruOrdersViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 7;
  }

  public class  GuruOrdersViewHolder extends RecyclerView.ViewHolder{
    public GuruOrdersViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }

}
