package com.pongo.biibisoft.donkomi_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalShopsRecyclerAdapter extends RecyclerView.Adapter<HorizontalShopsRecyclerAdapter.HorizontalShopsViewHolder>{


  @NonNull
  @Override
  public HorizontalShopsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_only_image_card,parent, false);
    return new HorizontalShopsViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull HorizontalShopsViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 4;
  }

  public class  HorizontalShopsViewHolder extends RecyclerView.ViewHolder{
    public HorizontalShopsViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
