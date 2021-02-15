package com.pongo.biibisoft.donkomi_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GuruOrdersFragment extends Fragment {
  String[] shops = new String[]{"McDonalds", "Ricardos", "KFC"};

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.guru_orders_fragment, container, false);
    initialize(v);
    return v;
  }

  public void initialize(View v) {
    Spinner dropdown = v.findViewById(R.id.shops_spinner);
    ArrayAdapter<String> dropdownAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, shops);
    dropdown.setAdapter(dropdownAdapter);
    RecyclerView recyclerView = v.findViewById(R.id.guru_orders_recycler);
    LinearLayoutManager manager = new LinearLayoutManager(getContext());
    GuruOrdersRecyclerAdapter adapter = new GuruOrdersRecyclerAdapter();
    recyclerView.setLayoutManager(manager);
    recyclerView.setAdapter(adapter);
  }
}
