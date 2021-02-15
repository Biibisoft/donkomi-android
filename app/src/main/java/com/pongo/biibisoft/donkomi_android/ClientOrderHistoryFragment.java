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

public class ClientOrderHistoryFragment extends Fragment {


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.client_order_history_fragment, container, false);
    initialize(v);
    return v;
  }



  public void initialize(View v){
    RecyclerView recyclerView = v.findViewById(R.id.history_recycler);
    OrderListRecyclerAdapter adapter = new OrderListRecyclerAdapter();
    LinearLayoutManager manager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(manager);
    recyclerView.setAdapter(adapter);

  }

}
