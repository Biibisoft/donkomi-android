package com.pongo.biibisoft.donkomi_android;

import android.widget.EditText;

public class MyHelper {

  public static String getTextFrom(EditText textbox) {
    if (textbox == null) return "";
    return textbox.getText().toString();
  }

  public static String getTextFrom(EditText textbox, Boolean _null) {
    if (textbox == null) return "";
    String s = textbox.getText().toString();
    if(_null) {
      return s.isEmpty() ?  null : s;
    }
    return s;
  }
}
