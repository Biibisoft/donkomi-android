package com.pongo.biibisoft.donkomi_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListRecyclerAdapter extends RecyclerView.Adapter<ProductListRecyclerAdapter.ProductListItemViewHolder> {


  @NonNull
  @Override
  public ProductListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_display_card, parent,false);
    return new ProductListItemViewHolder(v) ;
  }

  @Override
  public void onBindViewHolder(@NonNull ProductListItemViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 10;
  }

  class ProductListItemViewHolder extends RecyclerView.ViewHolder {


    public ProductListItemViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
