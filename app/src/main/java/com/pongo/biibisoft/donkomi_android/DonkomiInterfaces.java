package com.pongo.biibisoft.donkomi_android;

import android.content.Intent;

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

  interface RelayUser{
    void send(DonkomiUser user);
  }
}
