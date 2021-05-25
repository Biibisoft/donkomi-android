package com.pongo.biibisoft.donkomi_android;

import android.content.Context;

public class AuthenticatedUser {

  public static DonkomiUser getInstance(Context context){
    Object obj =  MyHelper.getFromSharedPreferences(context, Konstants.USER, DonkomiUser.class);
    if (obj == null) return null;
    return (DonkomiUser) obj;
  }

  public static void updateAuthenticatedUser(DonkomiUser user, Context context){
    MyHelper.saveToSharedPreferences(context, user, Konstants.USER);
  }

  public static void setInstance(DonkomiUser user, Context context){
    MyHelper.saveToSharedPreferences(context, user, Konstants.USER);
  }
}
