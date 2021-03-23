package com.pongo.biibisoft.donkomi_android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import org.json.JSONObject;

public class ClientHomeFragment extends Fragment {
  public static final String TAG = "CLIENT_HOME_FRAGMENT";
  RequestQueue httpHandler;
  InternetExplorer explorer;
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.client_home_fragment,container, false);
    initialize(v);
    return v;
  }

  public void initialize(View v){
    this.httpHandler = Volley.newRequestQueue(getContext());
    this.explorer = new InternetExplorer(getContext());
    RecyclerView recyclerView = v.findViewById(R.id.live_trips_recycler);
    LinearLayoutManager manager = new LinearLayoutManager(getContext());
    LiveTripsRecyclerAdapter adapter = new LiveTripsRecyclerAdapter();
    recyclerView.setLayoutManager(manager);
    recyclerView.setAdapter(adapter);
    recyclerView.hasFixedSize();
    runInitialRequest();
  }


  public void runInitialRequest(){
    explorer.run(DonkomiURLS.GET_ROUTINES, new Result() {
      @Override
      public void isOkay(JSONObject response) {
        Log.d(TAG, "isOkay: "+response.toString());
      }

      @Override
      public void error(String error) {
        Log.d(TAG, "error: "+error);
      }
    });
  }
}
