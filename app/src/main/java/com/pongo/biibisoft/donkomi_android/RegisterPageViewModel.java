package com.pongo.biibisoft.donkomi_android;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterPageViewModel extends ViewModel {
  private MutableLiveData<Integer> some = new MutableLiveData<Integer>(0);
  private MutableLiveData<String>  email, password,confirmPassword, firstName, lastName = new MutableLiveData<>("");
//  private Mutab
  public void setSome(MutableLiveData<Integer> some) {
    this.some = some;
  }

  public MutableLiveData<String> getEmail() {
    return email;
  }

  public void setEmail(MutableLiveData<String> email) {
    this.email = email;
  }

  public MutableLiveData<String> getPassword() {
    return password;
  }

  public void setPassword(MutableLiveData<String> password) {
    this.password = password;
  }

  public MutableLiveData<String> getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(MutableLiveData<String> confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public MutableLiveData<String> getFirstName() {
    return firstName;
  }

  public void setFirstName(MutableLiveData<String> firstName) {
    this.firstName = firstName;
  }

  public MutableLiveData<String> getLastName() {
    return lastName;
  }

  public void setLastName(MutableLiveData<String> lastName) {
    this.lastName = lastName;
  }

  public LiveData<Integer> getSome(){
    return some;
  }

  public void increase(){
    some.setValue(some.getValue()+1);
  }
}
