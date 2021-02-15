package com.pongo.biibisoft.donkomi_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GuruRoutineFragment extends Fragment {
  RecyclerView recyclerView;
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.guru_routine_fragment_layout, container, false);
    initialize(v);
    return v;
  }


  public void initialize(View v){
   recyclerView = v.findViewById(R.id.routine_recycler);
    RoutineListRecyclerAdapter adapter = new RoutineListRecyclerAdapter();
    LinearLayoutManager manager = new LinearLayoutManager(getContext());
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(manager);
  }

}
