package com.pongo.biibisoft.donkomi_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragmentPage extends Fragment {


  RelativeLayout goToSettingsBtn, applyToDonkomiBtn, signOutBtn;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.settings_fragment_page, container, false);
    initialize(v);
    return v;
  }


  public void initialize(View v) {
    goToSettingsBtn = v.findViewById(R.id.go_to_profile_edits_btn);
    applyToDonkomiBtn = v.findViewById(R.id.apply_btn);
    signOutBtn = v.findViewById(R.id.sign_out_btn);

    goToSettingsBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent page = new Intent(getContext(), ClientAllPagesContainer.class);
//        page.putExtra(Konstants.FORM_FOR, Konstants.EDIT_PROFILE_FORM);
        startActivity(page);
      }
    });

    applyToDonkomiBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent page = new Intent(getContext(), ApplicationPage.class);
        startActivity(page);
      }
    });

    signOutBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });

  }


  public View.OnClickListener generateClickEvent(FunctionToFire fxnToFire) {
    return new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fxnToFire.fire();
      }
    };
  }


  interface FunctionToFire {
    void fire();
  }
}
