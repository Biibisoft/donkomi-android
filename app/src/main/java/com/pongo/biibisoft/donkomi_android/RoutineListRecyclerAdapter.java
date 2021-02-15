package com.pongo.biibisoft.donkomi_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class RoutineListRecyclerAdapter extends RecyclerView.Adapter<RoutineListRecyclerAdapter.RoutineListItemViewHolder> {


  @NonNull
  @Override
  public RoutineListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card, parent, false);

    return new RoutineListItemViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull RoutineListItemViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 6;
  }

  public class RoutineListItemViewHolder extends RecyclerView.ViewHolder {


    public RoutineListItemViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }

}
