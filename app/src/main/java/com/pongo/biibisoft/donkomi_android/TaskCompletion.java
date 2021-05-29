package com.pongo.biibisoft.donkomi_android;

public class TaskCompletion {
  private String taskName ="" ;
  private Boolean isComplete = false;


  public TaskCompletion(){}
  public TaskCompletion(String taskName, Boolean isComplete){
    this.taskName = taskName;
    this.isComplete = isComplete;

  }
  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public Boolean getComplete() {
    return isComplete;
  }

  public Boolean isComplete(){
    return isComplete;
  }

  public void setComplete(Boolean complete) {
    isComplete = complete;
  }
}
