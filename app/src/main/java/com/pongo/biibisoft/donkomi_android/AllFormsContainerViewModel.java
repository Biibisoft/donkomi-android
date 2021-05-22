package com.pongo.biibisoft.donkomi_android;

import android.app.Application;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AllFormsContainerViewModel extends CommonViewModelItems {

  MutableLiveData<byte[]> selectedImage = new MutableLiveData<>();
  ImageUploadHelper imageHelper;
  FirebaseStorage storage = FirebaseStorage.getInstance();
  StorageReference bucket = storage.getReference();

  public ImageUploadHelper getImageHelper() {
    return imageHelper;
  }

  public void setImageHelper(ImageUploadHelper imageHelper) {
    this.imageHelper = imageHelper;
  }

  public AllFormsContainerViewModel(@NonNull Application application) {
    super(application);
  }

  public MutableLiveData<byte[]> getSelectedImage() {
    return selectedImage;
  }

  public byte[] getRealSelectedImage() {
    return this.selectedImage.getValue();
  }

  public void setSelectedImage(MutableLiveData<byte[]> selectedImage) {
    this.selectedImage = selectedImage;
  }

  public void setSelectedImage(byte[] image) {
    this.selectedImage.setValue(image);
  }

  public void removeSelectedImage() {
    this.selectedImage.setValue(null);
  }

  public void createNewVendor(String name, String description) {
    if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
      setToastMsg("Please make sure all fields are filled...");
      return;
    }
    if (getRealSelectedImage() == null) {
      setToastMsg("Please select an image...");
      return;
    }
    imageHelper.uploadImageToFirebase(bucket.child(Vendor.VENDOR_BUCKET), name, "user_platform_id", getRealSelectedImage(), new ImageUploadHelper.CollectUploadedImageURI() {
      @Override
      public void getURI(Uri uri) {
        setToastMsg(uri.toString());
      }
    });


  }

}
