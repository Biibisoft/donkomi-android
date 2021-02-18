package com.pongo.biibisoft.donkomi_android;

import android.view.View;

public class OneTabPage {
  String tabTitle = "Default Tab";
  int tabHeaderID = 0;
  int tabLayoutID = 0;
  View view;

  public OneTabPage(String tabTitle, int tabHeaderID, int tabLayoutID, View v, InitialSetup setup) {
    this.tabTitle = tabTitle;
    this.tabHeaderID = tabHeaderID;
    this.tabLayoutID = tabLayoutID;
    this.view = v;
    setup.createView(v);
  }

  public View getHeaderView(){
    return view.findViewById(this.tabHeaderID);
  }
  public View getMainContainerView(){
    return view.findViewById(this.tabLayoutID);
  }

  public OneTabPage() {
  }

  public String getTabTitle() {
    return tabTitle;
  }

  public void setTabTitle(String tabTitle) {
    this.tabTitle = tabTitle;
  }

  public int getTabHeaderID() {
    return tabHeaderID;
  }

  public void setTabHeaderID(int tabHeaderID) {
    this.tabHeaderID = tabHeaderID;
  }

  public int getTabLayoutID() {
    return tabLayoutID;
  }

  public void setTabLayoutID(int tabLayoutID) {
    this.tabLayoutID = tabLayoutID;
  }


  interface InitialSetup {
    public void createView(View v);
  }
}

