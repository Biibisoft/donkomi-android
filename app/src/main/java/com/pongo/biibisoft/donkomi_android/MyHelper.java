package com.pongo.biibisoft.donkomi_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyHelper {

  public static void initializeSpinner(ArrayList<String> array, Spinner spinner, Context context){
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,array);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
  }
  public static void saveToSharedPreferences(Context context, Object customValToSave, String IDENTIFIER) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(Konstants.DONKOMI_STORAGE, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    Gson gson = new Gson();
    String jsonString = gson.toJson(customValToSave);
    editor.putString(IDENTIFIER, jsonString);
    editor.apply();

  }

  public static Object getFromSharedPreferences(Context context, String IDENTIFIER, Type type) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(Konstants.DONKOMI_STORAGE, Context.MODE_PRIVATE);
    String savedJsonString = sharedPreferences.getString(IDENTIFIER, null);
    Gson gson = new Gson();
    return gson.fromJson(savedJsonString, type);
  }

  public static void removeFromSharedPreference(Context context, String IDENTIFIER) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(Konstants.DONKOMI_STORAGE, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.remove(IDENTIFIER);
    editor.apply();
  }
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
