package com.assetslookup;

import android.app.Application;

import com.assetslookup.data.db.AssetsDatabase;

public class AppController extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    AssetsDatabase.getAppDatabase(getApplicationContext());
  }
}

//Andre Teste
