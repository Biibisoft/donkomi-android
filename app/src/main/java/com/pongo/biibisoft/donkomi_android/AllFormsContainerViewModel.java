package com.pongo.biibisoft.donkomi_android;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class AllFormsContainerViewModel extends CommonViewModelItems {

  MutableLiveData<byte[]> selectedImage = new MutableLiveData<>();
  ImageUploadHelper imageHelper;

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

  public void setSelectedImage(MutableLiveData<byte[]> selectedImage) {
    this.selectedImage = selectedImage;
  }

  public void setSelectedImage(byte[] image) {
    this.selectedImage.setValue(image);
  }

  public void createNewVendor(String name, String description) {
    if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
      setToastMsg("Please make sure all fields are filled...");
      return;
    }
//    imageHelper.uploadImageToFirebase();


  }

}
