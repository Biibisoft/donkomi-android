package com.pongo.biibisoft.donkomi_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllFormsContainerPage extends AppCompatActivity {

  private static final String TAG = "ALL_FORMS_CONTAINER";
  String FORM_FOR = Konstants.NEW_STOCK;
  TextView pageName, removeImageBtn, removeStockImage;
  ImageView backBtn, rightIcon, vendorImage, stockImage;
  RelativeLayout vendorForm, stockForm, routineForm;
  AllFormsContainerViewModel pageHandler;
  EditText vendorName, vendorDesc, stockName, stockDesc, stockPrice;
  Button createVendorBtn, createStockBtn;
  ImageUploadHelper imageHelper;
  AllFormsContainerPage _this = this;
  Spinner ownersOfStockDropdown;

  byte[] selectedImage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_all_forms_container_page);
    pageHandler = new ViewModelProvider(this).get(AllFormsContainerViewModel.class);
    MagicBoxes dialogCreator = new MagicBoxes(this);
    pageHandler.setDialogCreator(dialogCreator);
    pageHandler.setLoadingDialog(pageHandler.dialogCreator.setUpDonkomiLoader("Loading..."));
    setObservers();
    initialize();

  }

  public void setObservers() {
    pageHandler.getToastMsg().observe(this, new Observer<String>() {
      @Override
      public void onChanged(String msg) {
        if (msg != null && !msg.isEmpty()) {
          Toast.makeText(AllFormsContainerPage.this, msg, Toast.LENGTH_SHORT).show();
          Log.d(TAG, "onChanged:--> " + msg);
        }
      }
    });

    pageHandler.getLoader().observe(this, new Observer<Boolean>() {
      @Override
      public void onChanged(Boolean show) {
        if (show) pageHandler.loadingDialog.show();
        else pageHandler.loadingDialog.dismiss();
      }
    });

    pageHandler.getSelectedImage().observe(this, new Observer<byte[]>() {
      @Override
      public void onChanged(byte[] bytes) {
        if (bytes != null) {
          if (FORM_FOR.equals(Konstants.NEW_VENDOR))
            removeOrSetImage(vendorImage, removeImageBtn, bytes, true);
          else if (FORM_FOR.equals(Konstants.NEW_STOCK))
            removeOrSetImage(stockImage, removeStockImage, bytes, true);
        } else {
          if (FORM_FOR.equals(Konstants.NEW_VENDOR))
            removeOrSetImage(vendorImage, removeImageBtn, null, false);
          else if (FORM_FOR.equals(Konstants.NEW_STOCK))
            removeOrSetImage(stockImage, removeStockImage, null, false);
        }
      }
    });

    pageHandler.getCompletionState().observe(this, new Observer<TaskCompletion>() {
      @Override
      public void onChanged(TaskCompletion taskCompletion) {
        if (taskCompletion.getTaskName().equals(Vendor.VENDOR_TASK) && taskCompletion.isComplete())
          clearFields(Vendor.VENDOR_TASK);
        else if (taskCompletion.getTaskName().equals(Stock.STOCK_TASK) && taskCompletion.isComplete())
          clearFields(Stock.STOCK_TASK);
      }
    });


    pageHandler.getVendors().observe(this, new Observer<ArrayList<Vendor>>() {
      @Override
      public void onChanged(ArrayList<Vendor> vendors) {
        Log.d(TAG, "onChanged: "+vendors.toString());
        MyHelper.initializeSpinner(Vendor.toStringArray(vendors), _this.ownersOfStockDropdown, _this);
      }
    });

  }

  private void removeOrSetImage(ImageView imageView, TextView removeBtn, byte[] image, Boolean setImage) {
    if (setImage) {
      Bitmap img = ImageUploadHelper.changeBytesToBitmap(image);
      removeBtn.setVisibility(View.VISIBLE);
      imageView.setImageBitmap(img);
    } else {
      removeBtn.setVisibility(View.GONE);
      imageView.setImageResource(R.drawable.mcdonalds_building);
    }
  }

  private void clearFields(String taskName) {
    if (taskName.equals(Vendor.VENDOR_TASK)) {
      vendorName.getText().clear();
      vendorDesc.getText().clear();
      pageHandler.removeSelectedImage();
    } else if (taskName.equals(Stock.STOCK_TASK)) {
      stockName.getText().clear();
      stockDesc.getText().clear();
      stockPrice.getText().clear();
      pageHandler.removeSelectedImage();
    }
  }

  public void initialize() {
    imageHelper = new ImageUploadHelper(this);
    pageHandler.setImageHelper(imageHelper);
    String _for = getIntent().getStringExtra(Konstants.FORM_FOR);
    FORM_FOR = _for != null ? _for : Konstants.NEW_ROUTINE;
    pageHandler.setCurrentPage(FORM_FOR);
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
    generateForm(FORM_FOR);
  }

  private final View.OnClickListener createNewVendor = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      pageHandler.setLoaderValue(true);
      pageHandler.createNewVendor(MyHelper.getTextFrom(vendorName), MyHelper.getTextFrom(vendorDesc));
    }
  };

  private final View.OnClickListener chooseImage = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      imageHelper.openFileChooserWithCropper(AllFormsContainerPage.this, 4, 3);
    }
  };


  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    imageHelper.collectCroppedImage(requestCode, resultCode, data, new ImageUploadHelper.CroppingImageCallback() {
      @Override
      public void getCroppedImage(Uri uri) {
        imageHelper.compressImageToBytes(uri, new ImageUploadHelper.CompressedImageToBytesCallback() {
          @Override
          public void getCompressedImage(byte[] compressedImage) {
            selectedImage = compressedImage;
            pageHandler.setSelectedImage(compressedImage);

          }
        });
      }

      @Override
      public void getCroppingError(Exception e) {
        pageHandler.setToastMsg(e.getMessage());
      }
    });
  }

  public void setupVendorForm() {
    vendorName = findViewById(R.id.vendor_name);
    vendorDesc = findViewById(R.id.vendor_description);
    createVendorBtn = findViewById(R.id.create_new_vendor_btn);
    createVendorBtn.setOnClickListener(createNewVendor);
    vendorImage = findViewById(R.id.vendor_image);
    vendorImage.setOnClickListener(chooseImage);
    removeImageBtn = findViewById(R.id.remove_image); //used by all forms
    removeImageBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        selectedImage = null;
        pageHandler.removeSelectedImage();
      }
    });
  }

  private final AdapterView.OnItemSelectedListener selectOwnerFromDropdown = new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
  };

  public void setupStocksForm() {
    ownersOfStockDropdown = findViewById(R.id.owner_of_stock_dropdown);
    ArrayList<Vendor> vendors = getIntent().getParcelableArrayListExtra(Konstants.VENDORS);
    if ( vendors != null) Log.d(TAG, "setupStocksForm: In the stocks from bro"+ vendors.toString());
    else Log.d(TAG, "setupStocksForm:In stocks setup! Vendors is null bro!");
    pageHandler.setVendors(vendors);

//    MyHelper.initializeSpinner(Konstants.DUMMY_VENDORS, ownersOfStockDropdown, this);
    ownersOfStockDropdown.setOnItemSelectedListener(selectOwnerFromDropdown);
    pageName.setText(R.string.new_stock_text);
    stockName = findViewById(R.id.stock_name);
    stockPrice = findViewById(R.id.stock_price);
    stockDesc = findViewById(R.id.stock_description);
    createStockBtn = findViewById(R.id.create_new_stock_btn);
    stockImage = findViewById(R.id.stock_image);
    stockImage.setOnClickListener(chooseImage);
    removeStockImage = findViewById(R.id.remove_stock_image);
    removeStockImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        selectedImage = null;
        pageHandler.removeSelectedImage();
      }
    });
    createStockBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pageHandler.createNewStock(MyHelper.getTextFrom(stockName), MyHelper.getTextFrom(stockPrice), MyHelper.getTextFrom(stockDesc));
      }
    });
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
        setupRoutineForm();
        routineForm.setVisibility(View.VISIBLE);
        break;

      case Konstants.NEW_VENDOR:
        setupVendorForm();
        vendorForm.setVisibility(View.VISIBLE);
        break;
      case Konstants.NEW_STOCK:
        setupStocksForm();
        stockForm.setVisibility(View.VISIBLE);

        break;
      default:
        break;
    }
  }
}