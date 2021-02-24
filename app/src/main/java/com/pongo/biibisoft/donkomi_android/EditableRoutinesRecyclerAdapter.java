package com.pongo.biibisoft.donkomi_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EditableRoutinesRecyclerAdapter extends RecyclerView.Adapter<EditableRoutinesRecyclerAdapter.EditableRoutinesViewHolder> {


  @NonNull
  @Override
  public EditableRoutinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_horizontal_card,parent, false);
    return new EditableRoutinesViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull EditableRoutinesViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 6;
  }

  public class EditableRoutinesViewHolder extends  RecyclerView.ViewHolder{

    public EditableRoutinesViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
