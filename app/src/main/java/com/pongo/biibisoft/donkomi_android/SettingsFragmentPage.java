package com.pongo.biibisoft.donkomi_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

import static com.pongo.biibisoft.donkomi_android.ClientHomeFragment.TAG;

public class SettingsFragmentPage extends Fragment {
  RelativeLayout goToProfileEdits, applyToDonkomiBtn, signOutBtn;
  private Context context;
  FirebaseAuth mAuth = FirebaseAuth.getInstance();
  ClientFragmentsViewModel pageHandler;
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.settings_fragment_page, container, false);
    initialize(v);
//    Log.d(TAG, "onCreateView: ALL_"+ pageHandler.authenticatedUser.toString());
    return v;
  }
  @Nullable
  @Override
  public Context getContext() {
    return context;
  }

  public void setPageHandler(ClientFragmentsViewModel pageHandler) {
    this.pageHandler = pageHandler;
  }

  public void setContext(Context context) {
    this.context = context;
  }

  public void initialize(View v) {
    goToProfileEdits = v.findViewById(R.id.go_to_profile_edits_btn);
    applyToDonkomiBtn = v.findViewById(R.id.apply_btn);
    signOutBtn = v.findViewById(R.id.sign_out_btn);
    goToProfileEdits.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent page = new Intent(context, ClientAllPagesContainer.class);
        page.putExtra(Konstants.USER,pageHandler.authenticatedUser);
        startActivityForResult(page,Konstants.PASS_USER_REQ_CODE);
      }
    });

    applyToDonkomiBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent page = new Intent(getContext(), ApplicationPage.class);
        page.putExtra(Konstants.USER,pageHandler.authenticatedUser);
        startActivity(page);
      }
    });

    signOutBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mAuth.signOut();
        Intent login = new Intent(context,LoginPage.class);
        startActivity(login);
        getActivity().finish();
      }
    });

  }
//  @Override
//  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//    Log.d(TAG, "onActivityResult: ALL_ here here settings");
//    if(requestCode == Konstants.PASS_USER_REQ_CODE) {
//      DonkomiUser user = data.getParcelableExtra(Konstants.USER);
//      Log.d(TAG, "onActivityResult: ALL_" +user.toString());
//    }
//  }



}
