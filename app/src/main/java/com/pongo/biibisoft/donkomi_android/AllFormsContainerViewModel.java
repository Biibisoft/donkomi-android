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

import java.util.ArrayList;

import static com.pongo.biibisoft.donkomi_android.ClientHomeFragment.TAG;

public class AllFormsContainerViewModel extends CommonViewModelItems {

  MutableLiveData<byte[]> selectedImage = new MutableLiveData<>();
  ImageUploadHelper imageHelper;
  FirebaseStorage storage = FirebaseStorage.getInstance();
  StorageReference bucket = storage.getReference();
  AllFormsContainerViewModel thisViewModel = this;
  MutableLiveData<Vendor> selectedVendor = new MutableLiveData<>();
  MutableLiveData<ArrayList<Vendor>> vendors = new MutableLiveData<>();
  public ImageUploadHelper getImageHelper() {
    return imageHelper;
  }

  public MutableLiveData<Vendor> getSelectedVendor() {
    return selectedVendor;
  }

  public Vendor getSelectedVendorValue() {
    return selectedVendor.getValue();
  }


  public MutableLiveData<ArrayList<Vendor>> getVendors() {
    return vendors;
  }

  public void setVendors(ArrayList<Vendor> vendors){

    this.vendors.setValue(vendors);
  }

  public void setSelectedVendor(Vendor selectedVendor) {
    this.selectedVendor.setValue(selectedVendor);
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


  public String currentPage = "";

  public MutableLiveData<TaskCompletion> getCompletionState() {
    return isComplete;
  }

  public void setCompletionState(MutableLiveData<TaskCompletion> isComplete) {
    this.isComplete = isComplete;
  }

  public String getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(String currentPage) {
    this.currentPage = currentPage;
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
    imageHelper.uploadImageToFirebase(bucket.child(Vendor.VENDOR_BUCKET), name, this.authUser.getPlatformID(), getRealSelectedImage(), new ImageUploadHelper.CollectUploadedImageURI() {
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

  public void createNewStock(String name, String price, String description) {
    if (MyHelper.isEmpty(name) || MyHelper.isEmpty(price) || MyHelper.isEmpty(description)) {
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
    setLoaderValue(true);
    imageHelper.uploadImageToFirebase(bucket.child(Stock.STOCK_BUCKET), name, this.authUser.getPlatformID(), getRealSelectedImage(), new ImageUploadHelper.CollectUploadedImageURI() {
      @Override
      public void getURI(Uri uri) {
        Stock stock = new Stock(name, price, description, uri.toString());
        try {
          exp.setRequestData(stock.makeRequestData(), false);
          exp.runAndFindData(DonkomiURLS.CREATE_STOCK, new ResultWithData() {
            @Override
            public void isOkay(JSONObject response) throws JSONException {

            }

            @Override
            public void getData(Object data) {
              setLoaderValue(false);
              setCompletionState(new TaskCompletion(Stock.STOCK_TASK, true));
              Log.d(TAG, "getData: WE GOT HERE BRO");
            }

            @Override
            public void getDataArray(Object data) {

            }

            @Override
            public void error(String error) {
              setLoaderValue(false);
              setToastMsg(error);
            }
          });
        } catch (JSONException e) {
          setLoaderValue(false);
          e.printStackTrace();
          setToastMsg(e.getMessage());
        }
      }
    });




  }
}
