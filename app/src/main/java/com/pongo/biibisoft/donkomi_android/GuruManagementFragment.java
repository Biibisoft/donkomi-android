package com.pongo.biibisoft.donkomi_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.GONE;

public class GuruManagementFragment extends Fragment {



  OneTabPage currentTab, vendorsTab, stocksTab, routinesTab;
  View vue;
  RelativeLayout vendorBtn, stockBtn, routineBtn;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.guru_management_layout, container, false);
    initialize(v);
    return v;
  }


  public void goToPage(OneTabPage newPage) {
    if (currentTab != null) {
      currentTab.getMainContainer().setVisibility(GONE);
      currentTab.getTabHeaderView().setBackgroundResource(R.drawable.plain_ripple_background);
      newPage.getMainContainer().setVisibility(View.VISIBLE);
      newPage.getTabHeaderView().setBackgroundResource(R.drawable.bottom_border_line);
      currentTab = newPage;
    }
  }

  public void initialize(View v) {
    vendorBtn = v.findViewById(R.id.create_new_vendor_btn);
    stockBtn = v.findViewById(R.id.create_new_stock_btn);
    routineBtn = v.findViewById(R.id.create_new_routine_btn);

    vendorBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent page = new Intent(getContext(), AllFormsContainerPage.class);
        page.putExtra(Konstants.FORM_FOR, Konstants.NEW_VENDOR);
        startActivity(page);
      }
    });

    stockBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent page = new Intent(getContext(), AllFormsContainerPage.class);
        page.putExtra(Konstants.FORM_FOR, Konstants.NEW_STOCK);
        startActivity(page);
      }
    });

    routineBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent page = new Intent(getContext(), AllFormsContainerPage.class);
        page.putExtra(Konstants.FORM_FOR, Konstants.NEW_ROUTINE);
        startActivity(page);
      }
    });

    // ----------------- VENDORS TAB SETUP  ---------------------
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
    TextView vendorHeader = v.findViewById(R.id.vendors_tab_header);
    LinearLayout vendorMainContainer = v.findViewById(R.id.vendors_tab_fragment);
    vendorsTab.setMainContainer(vendorMainContainer);
    vendorsTab.setTabHeaderView(vendorHeader);
    vendorsTab.getTabHeaderView().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        goToPage(vendorsTab);
      }
    });
    currentTab = vendorsTab;

//    ------------ STOCKS TAB SETUP ---------------
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
    TextView stocksHeader = v.findViewById(R.id.stocks_tab_header);
    LinearLayout stocksMainContainer = v.findViewById(R.id.stocks_tab_fragment);
    stocksTab.setMainContainer(stocksMainContainer);
    stocksTab.setTabHeaderView(stocksHeader);
    stocksTab.getTabHeaderView().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        goToPage(stocksTab);
      }
    });

//   --------------- ROUTINES TAB SETUP ------------------
    routinesTab = new OneTabPage("Vendors", R.id.routines_tab_header, R.layout.routines_tab_fragment, v, new OneTabPage.InitialSetup() {
      @Override
      public void createView(View v) {

      }
    });
    TextView routinesHeader = v.findViewById(R.id.routines_tab_header);
    LinearLayout routinesMainContainer = v.findViewById(R.id.routines_tab_fragment);
    routinesTab.setMainContainer(routinesMainContainer);
    routinesTab.setTabHeaderView(routinesHeader);
    routinesTab.getTabHeaderView().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        goToPage(routinesTab);
      }
    });
    this.vue = v;


  }


}
