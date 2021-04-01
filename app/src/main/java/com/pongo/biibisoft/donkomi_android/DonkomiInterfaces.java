package com.pongo.biibisoft.donkomi_android;

public class DonkomiInterfaces {
  interface Callback {
    void next();
  }

  interface Result {
    void isOkay();

    void error(String error);
  }

  interface Relay {
    void run();
  }
}
