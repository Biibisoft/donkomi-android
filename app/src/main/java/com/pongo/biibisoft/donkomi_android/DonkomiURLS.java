package com.pongo.biibisoft.donkomi_android;

public class DonkomiURLS {

//  public static final String HOST = "http://192.168.8.101:8000/";
//  public static final String HOST = "https://donkomi-dev-app.herokuapp.com/";
  public static final String HOST = "http://10.0.3.2:8000/";
  public static final String GET_USER = HOST+"api/donkomi/account/user/get/";
  public static final String GET_ROUTINES = HOST + "api/donkomi/routine/get.all/";

  public static final String REGISTER_USER = HOST + "api/donkomi/account/user/register/";

  public static final String UPDATE_USER = HOST+"api/donkomi/account/user/update/";
  public static final String  GET_ALL_ROLES = HOST + "api/donkomi/roles/get.all/";
  public static final String SEND_APPLICATION = HOST+ "api/donkomi/apply-for/role";
  public static final String DELETE_MY_APPLICATION = HOST +"api/donkomi/delete/application";


  public static final String CREATE_VENDOR =  HOST +"api/donkomi/vendor/create/";
  public static final String DELETE_VENDOR =  HOST +"api/donkomi/vendor/delete/";
  public static final String UPDATE_VENDOR =  HOST +"api/donkomi/update/vendor/";
  public static final String CREATE_STOCK =  HOST +"api/donkomi/stock/create/";
  public static final String DELETE_STOCK =  HOST +"api/donkomi/stock/delete/";
  public static final String UPDATE_STOCK =  HOST +"api/donkomi/update/stock/";



}
