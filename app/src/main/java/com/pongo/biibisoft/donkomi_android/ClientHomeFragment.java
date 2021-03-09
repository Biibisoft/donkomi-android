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

  RequestQueue httpHandler;
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.client_home_fragment,container, false);
    initialize(v);
    return v;
  }

  public void initialize(View v){
    this.httpHandler = Volley.newRequestQueue(getContext());
    RecyclerView recyclerView = v.findViewById(R.id.live_trips_recycler);
    LinearLayoutManager manager = new LinearLayoutManager(getContext());
    LiveTripsRecyclerAdapter adapter = new LiveTripsRecyclerAdapter();
    recyclerView.setLayoutManager(manager);
    recyclerView.setAdapter(adapter);
    recyclerView.hasFixedSize();
    runInitialRequest();
  }


  public void runInitialRequest(){
    JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, DonkomiURLS.GET_USER + "2", null, new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        Log.d("RESPONSE-HERE:::",response.toString());
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Log.d("RESPONSE_ERROR:", error.getLocalizedMessage());
      }
    });
    httpHandler.add(req);
  }
}
