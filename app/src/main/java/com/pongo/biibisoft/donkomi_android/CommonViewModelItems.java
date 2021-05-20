package com.pongo.biibisoft.donkomi_android;

import android.app.Application;
import android.app.Dialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class CommonViewModelItems extends AndroidViewModel {
  MutableLiveData<Boolean> loader = new MutableLiveData<>(false);
  MutableLiveData<String> toastMsg = new MutableLiveData<>("");
  InternetExplorer explorer = new InternetExplorer(getApplication().getApplicationContext());
  MagicBoxes dialogCreator;
  Dialog loadingDialog;

  public CommonViewModelItems(@NonNull Application application) {
    super(application);
  }

  public Dialog getLoadingDialog() {
    return loadingDialog;
  }

  public void setLoadingDialog(Dialog loadingDialog) {
    this.loadingDialog = loadingDialog;
  }

  public MagicBoxes getDialogCreator() {
    return dialogCreator;
  }

  public void setDialogCreator(MagicBoxes dialogCreator) {
    this.dialogCreator = dialogCreator;
  }

  public InternetExplorer getExplorer() {
    return explorer;
  }

  public void setExplorer(InternetExplorer explorer) {
    this.explorer = explorer;
  }

  public MutableLiveData<Boolean> getLoader() {
    return loader;
  }

  public void setLoader(MutableLiveData<Boolean> loader) {
    this.loader = loader;
  }
  public void setLoaderValue(Boolean value){
    this.loader.setValue(value);
  }

  public MutableLiveData<String> getToastMsg() {
    return toastMsg;
  }

  public void setToastMsg(MutableLiveData<String> toastMsg) {
    this.toastMsg = toastMsg;
  }

  public void setToastMsg(String toastMsg) {
    this.toastMsg.setValue(toastMsg);
  }
}
