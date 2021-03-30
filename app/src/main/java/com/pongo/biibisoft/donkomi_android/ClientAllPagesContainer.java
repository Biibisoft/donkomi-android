package com.pongo.biibisoft.donkomi_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ClientAllPagesContainer extends AppCompatActivity {

  private static final String TAG = "ALL_PAGES_CONTAINER";
  TextView pageName;
  ImageView backBtn, rightIcon, profilePicture;
  Activity _this;
  String CURRENT_PAGE = Konstants.EDIT_PROFILE_FORM;
  //  String FORM_FOR ;
  RelativeLayout editProfileForm;
  private ImageUploadHelper imageUploadHelper;
  private byte[] selectedImageBytes;
  private String selectedImageExt;
  DonkomiUser authUser;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client_all_pages_container);
    _this = this;
    initialize();
  }


  public void generateForm() {
    CURRENT_PAGE = CURRENT_PAGE == null ? Konstants.EDIT_PROFILE_FORM : CURRENT_PAGE;
    if (CURRENT_PAGE.equals(Konstants.EDIT_PROFILE_FORM)) {
      setupUniqueEditQualities();
      pageName.setText(R.string.edit_your_profile);
      editProfileForm.setVisibility(View.VISIBLE);
    }
  }

  public void initialize() {
    authUser = getIntent().getParcelableExtra(Konstants.USER);
    Log.d(TAG, "setupUniqueEditQualities: "+ authUser.toString());
    Spinner gender_dropdown = findViewById(R.id.gender_dropdown);
    ArrayAdapter<String> genderAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Konstants.GENDER);
    gender_dropdown.setAdapter(genderAdapter);
    CURRENT_PAGE = getIntent().getStringExtra(Konstants.FORM_FOR);
    pageName = findViewById(R.id.page_name);
    backBtn = findViewById(R.id.back_icon);
    rightIcon = findViewById(R.id.right_icon);
    rightIcon.setVisibility(View.INVISIBLE);
    backBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    generateForm();
  }

  public void setupUniqueEditQualities() {

    profilePicture = findViewById(R.id.profile_picture);
    editProfileForm = findViewById(R.id.edit_profile_form);
    imageUploadHelper = new ImageUploadHelper(this);
    Button saveChanges = findViewById(R.id.save_changes);
    saveChanges.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(_this, "Do some submission lol!", Toast.LENGTH_SHORT).show();
      }
    });
    profilePicture.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startImageChooser();
      }
    });
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    imageUploadHelper.collectCroppedImage(requestCode, resultCode, data, new ImageUploadHelper.CroppingImageCallback() {
      @Override
      public void getCroppedImage(Uri uri) {
        selectedImageExt = imageUploadHelper.getFileExtension(uri);
        imageUploadHelper.compressImageToBytes(uri, new ImageUploadHelper.CompressedImageToBytesCallback() {
          @Override
          public void getCompressedImage(byte[] compressedImage) {
            selectedImageBytes = compressedImage;
            Bitmap bitmap = BitmapFactory.decodeByteArray(compressedImage, 0, compressedImage.length);
            profilePicture.setImageBitmap(bitmap);

          }
        });

      }

      @Override
      public void getCroppingError(Exception e) {
        e.printStackTrace();
        Toast.makeText(_this, e.getMessage(), Toast.LENGTH_SHORT).show();

      }
    });
  }

  private void startImageChooser() {
    imageUploadHelper.openFileChooserWithCropper(this, 4, 3);
  }
}