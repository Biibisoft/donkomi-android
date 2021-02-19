package com.pongo.biibisoft.donkomi_android;

import android.util.Log;
import android.view.View;

public class OneTabPage {
  String tabTitle = "Default Tab";
  int tabHeaderID = 0;
  int tabLayoutID = 0;
  View mainContainer, view, tabHeaderView;


  public OneTabPage(String tabTitle, int tabHeaderID, int tabLayoutID, View v, InitialSetup setup) {
    this.tabTitle = tabTitle;
    this.tabHeaderID = tabHeaderID;
    this.tabLayoutID = tabLayoutID;
    this.view = v;
    setup.createView(v);
  }

  public OneTabPage() {
  }

  public View getMainContainer() {
    return mainContainer;
  }

  public View getTabHeaderView() {
    return tabHeaderView;
  }

  public void setTabHeaderView(View tabHeaderView) {
    this.tabHeaderView = tabHeaderView;
  }

  public void setMainContainer(View mainContainer) {
    this.mainContainer = mainContainer;
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
//    public void setContainerView(View v);
  }
}

