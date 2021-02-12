package com.pongo.biibisoft.donkomi_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LiveTripsRecyclerAdapter extends RecyclerView.Adapter<LiveTripsRecyclerAdapter.LiveTripsViewHolder>{


  @NonNull
  @Override
  public LiveTripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.campaign_card,null, false);
    return new LiveTripsViewHolder(v);
  }

  @Override
  public int getItemCount() {
    return 6;
  }

  @Override
  public void onBindViewHolder(@NonNull LiveTripsViewHolder holder, int position) {

  }

  public class LiveTripsViewHolder extends RecyclerView.ViewHolder{
    public LiveTripsViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
