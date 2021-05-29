package com.pongo.biibisoft.donkomi_android;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import static com.pongo.biibisoft.donkomi_android.ClientHomeFragment.TAG;

public class AllFormsContainerViewModel extends CommonViewModelItems {

  MutableLiveData<byte[]> selectedImage = new MutableLiveData<>();
  ImageUploadHelper imageHelper;
  FirebaseStorage storage = FirebaseStorage.getInstance();
  StorageReference bucket = storage.getReference();
  AllFormsContainerViewModel thisViewModel = this;

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

  public MutableLiveData<TaskCompletion> isComplete = new MutableLiveData<>(new TaskCompletion());


  public MutableLiveData<TaskCompletion> getCompletionState() {
    return isComplete;
  }

  public void setCompletionState(MutableLiveData<TaskCompletion> isComplete) {
    this.isComplete = isComplete;
  }

  public void setCompletionState(TaskCompletion task) {
    this.isComplete.setValue(task);
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
      setLoaderValue(false);
      return;
    }
    if (getRealSelectedImage() == null) {
      setToastMsg("Please select an image...");
      setLoaderValue(false);
      return;
    }
    InternetExplorer exp = this.explorer;
    imageHelper.uploadImageToFirebase(bucket.child(Vendor.VENDOR_BUCKET), name, "user_platform_id", getRealSelectedImage(), new ImageUploadHelper.CollectUploadedImageURI() {
      @Override
      public void getURI(Uri uri) {
        String url = uri.toString();
        Vendor vendor = new Vendor(name, description, url);
        try {
          exp.authenticate(thisViewModel.authUser);
          exp.setRequestData(vendor.makeRequestData(), false);
          exp.runAndFindData(DonkomiURLS.CREATE_VENDOR, new ResultWithData() {
            @Override
            public void isOkay(JSONObject response) throws JSONException {

            }

            @Override
            public void getData(Object data) {
              setCompletionState(new TaskCompletion(Vendor.VENDOR_TASK, true));
              Log.d(TAG, "getData: WE GOT HERE BRO");
            }

            @Override
            public void getDataArray(Object data) {

            }

            @Override
            public void error(String error) {
              setToastMsg(error);
            }
          });
        } catch (JSONException e) {
          e.printStackTrace();
          setToastMsg(e.getLocalizedMessage());
        }
        setLoaderValue(false);
      }
    });


  }

}
