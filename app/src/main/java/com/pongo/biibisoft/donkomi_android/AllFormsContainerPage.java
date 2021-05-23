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
import android.view.LayoutInflater;
import android.view.View;
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

public class AllFormsContainerPage extends AppCompatActivity {

  String FORM_FOR = Konstants.NEW_STOCK;
  TextView pageName, removeImageBtn;
  ImageView backBtn, rightIcon, vendorImage;
  RelativeLayout vendorForm, stockForm, routineForm;
  AllFormsContainerViewModel pageHandler;
  EditText vendorName, vendorDesc;
  Button createVendorBtn;
  ImageUploadHelper imageHelper;
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
        if (msg != null && !msg.isEmpty())
          Toast.makeText(AllFormsContainerPage.this, msg, Toast.LENGTH_SHORT).show();
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
          Bitmap img = ImageUploadHelper.changeBytesToBitmap(bytes);
          removeImageBtn.setVisibility(View.VISIBLE);
          vendorImage.setImageBitmap(img);
        } else removeImageBtn.setVisibility(View.GONE);
      }
    });

  }

  public void initialize() {
    imageHelper = new ImageUploadHelper(this);
    pageHandler.setImageHelper(imageHelper);
    String _for = getIntent().getStringExtra(Konstants.FORM_FOR);
    FORM_FOR = _for != null ? _for : Konstants.NEW_ROUTINE;
    backBtn = findViewById(R.id.back_icon);
    removeImageBtn = findViewById(R.id.remove_image);
    removeImageBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        selectedImage = null;
        pageHandler.removeSelectedImage();
        vendorImage.setImageResource(R.drawable.mcdonalds_building);
      }
    });
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