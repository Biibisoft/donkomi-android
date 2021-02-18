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

import static android.view.View.GONE;

public class GuruManagementFragment extends Fragment {


  OneTabPage currentTab, vendorsTab, stocksTab, routinesTab;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.guru_management_layout, container, false);
    initialize(v);
    return v;
  }


  public void goToPage(OneTabPage newPage) {
    if (currentTab != null) {
      currentTab.getMainContainerView().setVisibility(GONE);
      currentTab.getHeaderView().setBackgroundResource(R.drawable.plain_ripple_background);
      newPage.getMainContainerView().setVisibility(View.VISIBLE);
      newPage.getHeaderView().setBackgroundResource(R.drawable.bottom_border_line);
      currentTab = newPage;
    }
  }

  public void initialize(View v) {
    vendorsTab = new OneTabPage("Vendors", R.id.vendors_tab_header, R.layout.vendors_tab_fragment, v, new OneTabPage.InitialSetup() {
      @Override
      public void createView(View v) {
        RecyclerView vendorsRec = v.findViewById(R.id.guru_vendors_tab_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        CommonGuruTabsRecyclerAdapter adapter = new CommonGuruTabsRecyclerAdapter();
        vendorsRec.setAdapter(adapter);
        vendorsRec.setLayoutManager(manager);
      }
    });

    currentTab = vendorsTab;
    stocksTab = new OneTabPage("Vendors", R.id.stocks_tab_header, R.layout.stocks_tab_fragment, v, new OneTabPage.InitialSetup() {
      @Override
      public void createView(View v) {
        RecyclerView stocksRec = v.findViewById(R.id.guru_stock_tab_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        CommonGuruTabsRecyclerAdapter adapter = new CommonGuruTabsRecyclerAdapter();
        adapter.setWhichTab(TAB_CONSTANTS.STOCKS_TAB);
        stocksRec.setAdapter(adapter);
        stocksRec.setLayoutManager(manager);

      }
    });
    routinesTab = new OneTabPage("Vendors", R.id.routines_tab_header, R.layout.routines_tab_fragment, v, new OneTabPage.InitialSetup() {
      @Override
      public void createView(View v) {

      }
    });

    goToPage(stocksTab);


  }
}
