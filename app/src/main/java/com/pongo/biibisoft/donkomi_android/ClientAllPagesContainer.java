package com.pongo.biibisoft.donkomi_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ClientAllPagesContainer extends AppCompatActivity {

  TextView pageName;
  ImageView backBtn, rightIcon, profilePicture;
  Activity _this;
  String CURRENT_PAGE = Konstants.EDIT_PROFILE_FORM;
  //  String FORM_FOR ;
  RelativeLayout editProfileForm;
  private ImageUploadHelper imageUploadHelper;
  private byte[] selectedImageBytes;
  private String selectedImageExt;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client_all_pages_container);
    _this = this;
    initialize();
  }


  public void generateForm() {
    CURRENT_PAGE = CURRENT_PAGE == null ? Konstants.EDIT_PROFILE_FORM : CURRENT_PAGE;
    if (CURRENT_PAGE == Konstants.EDIT_PROFILE_FORM) {
      editProfileForm.setVisibility(View.VISIBLE);
    }
  }

  public void initialize() {
    profilePicture = findViewById(R.id.profile_picture);
    imageUploadHelper = new ImageUploadHelper(this);
    editProfileForm = findViewById(R.id.edit_profile_form);
    CURRENT_PAGE = getIntent().getStringExtra(Konstants.FORM_FOR);
    pageName = findViewById(R.id.page_name);
    pageName.setText("Edit Your Profile");
    backBtn = findViewById(R.id.back_icon);
    rightIcon = findViewById(R.id.right_icon);
    rightIcon.setVisibility(View.INVISIBLE);
    backBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    profilePicture.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startImageChooser();
      }
    });
    generateForm();

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