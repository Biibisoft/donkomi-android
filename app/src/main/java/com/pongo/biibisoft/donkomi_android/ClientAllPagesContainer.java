package com.pongo.biibisoft.donkomi_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
  RelativeLayout editProfileForm;
  private ImageUploadHelper imageUploadHelper;
  private byte[] selectedImageBytes;
  private String selectedImageExt;

  EditText firstName, lastName, phone;
  MagicBoxes dialogCreator;
  ClientAllPagesContainerViewModel pageHandler;
  private String selectedGender;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client_all_pages_container);
    pageHandler = new ViewModelProvider(this).get(ClientAllPagesContainerViewModel.class);
    pageHandler.handleTravellingContent(getIntent());
    _this = this;
    initialize();
    setObservers();
  }


  public void generateForm() {
    CURRENT_PAGE = CURRENT_PAGE == null ? Konstants.EDIT_PROFILE_FORM : CURRENT_PAGE;
    if (CURRENT_PAGE.equals(Konstants.EDIT_PROFILE_FORM)) {
      setupUniqueEditQualities();
      pageName.setText(R.string.edit_your_profile);
      editProfileForm.setVisibility(View.VISIBLE);
    }
  }

  public void setObservers(){
    pageHandler.currentPage().observe(this, new Observer<String>() {
      @Override
      public void onChanged(String page) {
        switch (page) {
          case Konstants.EDIT_PROFILE_FORM:
            setupUniqueEditQualities();
            pageName.setText(R.string.edit_your_profile);
            editProfileForm.setVisibility(View.VISIBLE);
        }

      }
    });
    pageHandler.authenticatedUser().observe(this, new Observer<DonkomiUser>() {
      @Override
      public void onChanged(DonkomiUser donkomiUser) {
        prefillWithContent(donkomiUser);
      }
    });
  }


  public void initialize() {
    dialogCreator = new MagicBoxes(this);
//    authUser = getIntent().getParcelableExtra(Konstants.USER);
//    if(authUser == null) {
//      dialogCreator.constructSimpleOneActionDialog("Sign In", "You have not signed in yet", "", new OneAction() {
//        @Override
//        public void callback() {
//          Intent login = new Intent(_this, LoginPage.class);
//          startActivity(login);
//          finish();
//        }
//      });
//    }
    Spinner gender_dropdown = findViewById(R.id.gender_dropdown);
    ArrayAdapter<String> genderAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Konstants.GENDER);
    gender_dropdown.setAdapter(genderAdapter);
    gender_dropdown.setOnItemSelectedListener(genderSelected);
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

  }

  public void setupUniqueEditQualities() {
    firstName = findViewById(R.id.first_name);
    lastName = findViewById(R.id.last_name);
    phone = findViewById(R.id.edit_mobile_number);
    profilePicture = findViewById(R.id.profile_picture);
    editProfileForm = findViewById(R.id.edit_profile_form);
    imageUploadHelper = new ImageUploadHelper(this);
    Button saveChanges = findViewById(R.id.save_changes);
    saveChanges.setOnClickListener(saveEditChanges);
    profilePicture.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startImageChooser();
      }
    });
  }

  public View.OnClickListener saveEditChanges = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      pageHandler.getEditedUser().setFirstName(MyHelper.getTextFrom(firstName));
      pageHandler.getEditedUser().setLastName(MyHelper.getTextFrom(lastName));
      pageHandler.getEditedUser().setPhone(MyHelper.getTextFrom(phone));
      pageHandler.getEditedUser().setGender(selectedGender);
      pageHandler.saveEditChanges(new ClientAllPagesContainerViewModel.AfterSavedChanges() {
        @Override
        public void nothingChanged() {
          Toast.makeText(_this, "Nothing has changed bro", Toast.LENGTH_SHORT).show();
          Log.d(TAG, "nothingChanged: "+pageHandler.getEditedUser().toString());
          Log.d(TAG, "nothingChanged: "+pageHandler.getAuthUser().toString());
        }

        @Override
        public void changesSaved() {
          Toast.makeText(_this, "Something has happened bro!", Toast.LENGTH_SHORT).show();
          Log.d(TAG, "changesSaved: "+pageHandler.getEditedUser().toString());
          Log.d(TAG, "changesSaved: "+pageHandler.getAuthUser().toString());
        }

        @Override
        public void error() {

        }
      });
    }
  };

  private final AdapterView.OnItemSelectedListener genderSelected = new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      selectedGender = Konstants.GENDER[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
  };


  public void prefillWithContent(DonkomiUser authUser){
    firstName.setText(authUser.getFirstName());
    lastName.setText(authUser.getLastName());
    phone.setText(authUser.getPhone());
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