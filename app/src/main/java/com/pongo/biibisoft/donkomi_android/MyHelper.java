package com.pongo.biibisoft.donkomi_android;

import android.widget.EditText;

public class MyHelper {

  public static String getTextFrom(EditText textbox) {
    if (textbox == null) return "";
    return textbox.getText().toString();
  }
}
