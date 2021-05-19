package com.pongo.biibisoft.donkomi_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AllFormsContainerPage extends AppCompatActivity {

  String FORM_FOR = Konstants.NEW_STOCK;
  TextView pageName;
  ImageView backBtn, rightIcon, selectedImage;
  RelativeLayout vendorForm, stockForm, routineForm;
  AllFormsContainerViewModel pageHandler;
  EditText vendorName, vendorDesc;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_all_forms_container_page);
    pageHandler = new ViewModelProvider(this).get(AllFormsContainerViewModel.class);
    pageHandler.setLoadingDialog(pageHandler.dialogCreator.setUpDonkomiLoader(this,"Loading"));
    setObservers();
    initialize();

  }

  public void setObservers() {
    pageHandler.getToastMsg().observe(this, new Observer<String>() {
      @Override
      public void onChanged(String msg) {
        if (msg != null && !msg.isEmpty())
          Toast.makeText(AllFormsContainerPage.this, msg, Toast.LENGTH_SHORT).show();
      }
    });

    pageHandler.getLoader().observe(this, new Observer<Boolean>() {
      @Override
      public void onChanged(Boolean show) {
        if ( show) pageHandler.loadingDialog.show();
        else  pageHandler.loadingDialog.dismiss();
      }
    });

  }

  public void initialize() {
    String _for = getIntent().getStringExtra(Konstants.FORM_FOR);
    FORM_FOR = _for != null ? _for : Konstants.NEW_ROUTINE;
    backBtn = findViewById(R.id.back_icon);
    backBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    pageName = findViewById(R.id.page_name);
    vendorForm = findViewById(R.id.create_new_vendor_form);
    routineForm = findViewById(R.id.create_new_routine_form);
    stockForm = findViewById(R.id.create_new_stock_form);
    setupVendorForm();
    setupStocksForm();
    setupRoutineForm();
    generateForm(FORM_FOR);

  }

  public void setupVendorForm(){
    vendorName = findViewById(R.id.vendor_name);
    vendorDesc = findViewById(R.id.vendor_description);
  }

  public void setupStocksForm() {
    Spinner ownersOfStockDropdown = findViewById(R.id.owner_of_stock_dropdown);
    ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Konstants.DUMMY_VENDORS);
    ownersOfStockDropdown.setAdapter(adapter);
    pageName.setText("Create New Stock");
  }

  public void setupRoutineForm() {
    Spinner ownersOfStockDropdown = findViewById(R.id.routine_form_vendors_dropdown);
    ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Konstants.DUMMY_VENDORS);
    ownersOfStockDropdown.setAdapter(adapter);
    pageName.setText("Create New Routine");

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