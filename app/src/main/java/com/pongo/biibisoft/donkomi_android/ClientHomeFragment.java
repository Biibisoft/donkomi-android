package com.pongo.biibisoft.donkomi_android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class ClientHomeFragment extends Fragment {
  public static final String TAG = "CLIENT_HOME_FRAGMENT";
  RequestQueue httpHandler;
  InternetExplorer explorer;
  private Gson gson = new Gson();

  RecyclerView liveTripsRecycler;
  ProgressBar loadingSpinner;
  TextView informationBanner;
  RelativeLayout notFoundBox;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.client_home_fragment, container, false);
    initialize(v);
    return v;
  }

  public void initialize(View v) {
//    this.httpHandler = Volley.newRequestQueue(getContext());
    this.explorer = new InternetExplorer(getContext());
    notFoundBox = v.findViewById(R.id.not_found);
    informationBanner = v.findViewById(R.id.information);
    loadingSpinner = v.findViewById(R.id.raw_loading_spinner);
    liveTripsRecycler = v.findViewById(R.id.live_trips_recycler);
    LinearLayoutManager manager = new LinearLayoutManager(getContext());
    LiveTripsRecyclerAdapter adapter = new LiveTripsRecyclerAdapter();
    liveTripsRecycler.setLayoutManager(manager);
    liveTripsRecycler.setAdapter(adapter);
    liveTripsRecycler.hasFixedSize();
    runInitialRequest();
  }


  public void runInitialRequest() {
    explorer.run(DonkomiURLS.GET_ROUTINES, new Result() {
      @Override
      public void isOkay(JSONObject response) {
        try {
          RoutineTemplate[] templates = gson.fromJson(response.get("data").toString(), (Type) RoutineTemplate[].class);
          if( templates != null && templates.length != 0) {
            Log.d(TAG, "isOkay: " + templates[0].toString());
            loadingSpinner.setVisibility(View.GONE);
            liveTripsRecycler.setVisibility(View.VISIBLE);
          }else{
            loadingSpinner.setVisibility(View.GONE);
            notFoundBox.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "There are no routines yet, sorry!", Toast.LENGTH_SHORT).show();
          }
        } catch (JSONException e) {
          loadingSpinner.setVisibility(View.GONE);
          informationBanner.setVisibility(View.VISIBLE);
          e.printStackTrace();
        }
      }

      @Override
      public void error(String error) {
        Log.d(TAG, "error: " + error);
      }
    });
  }
}
