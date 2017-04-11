package com.jgeng.photowall;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by jgeng on 3/10/17.
 */

public class MyApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
  }
}
