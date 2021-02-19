package com.pongo.biibisoft.donkomi_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AllFormsContainerPage extends AppCompatActivity {

  String FORM_FOR = Konstants.NEW_VENDOR;
  TextView pageName;
  ImageView backBtn, rightIcon;
  LinearLayout  routineForm, stockForm;
  RelativeLayout vendorForm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_all_forms_container_page);
    initialize();
  }


  public void initialize() {
    String _for = getIntent().getStringExtra(Konstants.FORM_FOR) ;
    FORM_FOR = _for != null ? _for : Konstants.NEW_VENDOR;
    backBtn = findViewById(R.id.back_icon);
    pageName = findViewById(R.id.page_name);
    vendorForm = findViewById(R.id.create_new_vendor_form);
    routineForm = findViewById(R.id.create_new_routine_form);
    stockForm = findViewById(R.id.create_new_stock_form);
    generateForm(FORM_FOR);

  }

  public void generateForm(String forWho) {
    switch (forWho) {
      case Konstants.NEW_ROUTINE:
        routineForm.setVisibility(View.VISIBLE);
        break;

      case Konstants.NEW_VENDOR:
        vendorForm.setVisibility(View.VISIBLE);
        break;
      case Konstants.NEW_STOCK:
        stockForm.setVisibility(View.VISIBLE);
        break;
      default:
        break;
    }
  }
}