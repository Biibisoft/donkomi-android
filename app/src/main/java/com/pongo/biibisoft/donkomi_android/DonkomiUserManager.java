package com.pongo.biibisoft.donkomi_android;

import org.json.JSONException;
import org.json.JSONObject;

interface DonkomiUserManager {
  public JSONObject parseIntoInternetData() throws JSONException;
}
