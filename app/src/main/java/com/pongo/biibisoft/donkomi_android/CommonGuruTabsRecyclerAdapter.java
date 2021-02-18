package com.pongo.biibisoft.donkomi_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommonGuruTabsRecyclerAdapter extends RecyclerView.Adapter<CommonGuruTabsRecyclerAdapter.CommonRecyclerItemsViewHolder> {

  String whichTab = TAB_CONSTANTS.VENDOR_TAB;

  public void setWhichTab(String whichTab) {
    this.whichTab = whichTab;
  }

  @NonNull
  @Override
  public CommonRecyclerItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
   View v;

   if( whichTab.equals(TAB_CONSTANTS.VENDOR_TAB)){
     v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_card, parent, false);
     return new CommonRecyclerItemsViewHolder(v);
   }

    if( whichTab.equals(TAB_CONSTANTS.STOCKS_TAB)){
      v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_display_card, parent, false);
      return new CommonRecyclerItemsViewHolder(v);
    }

    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull CommonRecyclerItemsViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 7;
  }

  public class CommonRecyclerItemsViewHolder extends RecyclerView.ViewHolder {
    public CommonRecyclerItemsViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
