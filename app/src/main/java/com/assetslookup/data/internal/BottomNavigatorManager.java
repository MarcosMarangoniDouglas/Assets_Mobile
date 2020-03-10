package com.assetslookup.data.internal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.lang.reflect.Constructor;

public class BottomNavigatorManager {

  private FragmentManager fragmentManager;
  private int fragmentContainer;

  public BottomNavigatorManager(FragmentManager fragmentManager, int fragmentContainer) {
    this.fragmentManager = fragmentManager;
    this.fragmentContainer = fragmentContainer;
  }

  public void attach(Class<? extends Fragment> fragment) {
    try {
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      Constructor<? extends Fragment> constructor = fragment.getConstructor();
      Fragment newFragment = constructor.newInstance();
      Fragment searchFragment = fragmentManager.findFragmentByTag(fragment.getName());
      detachFragments(fragmentTransaction, searchFragment);
      if(searchFragment == null) {
        fragmentTransaction.add(fragmentContainer, newFragment, fragment.getName());
      } else {
        fragmentTransaction.attach(searchFragment);
      }
      fragmentTransaction.commit();
    } catch (Exception ex) {
      Log.d("EXCEPTION", ex.getMessage());
    }
  }

  public void attach(Class<? extends Fragment> fragment, Bundle bundle) {
    try {
      Constructor<? extends Fragment> constructor = fragment.getConstructor();
      Fragment newFragment = constructor.newInstance();
      Fragment searchFragment = fragmentManager.findFragmentByTag(fragment.getName());
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      detachFragments(fragmentTransaction, searchFragment);
      if(searchFragment == null) {
        fragmentTransaction.add(fragmentContainer, newFragment, fragment.getName());
        newFragment.setArguments(bundle);
      } else {
        fragmentTransaction.attach(searchFragment);
        searchFragment.setArguments(bundle);
      }
      fragmentTransaction.commit();
    } catch (Exception ex) {
      Log.d("EXCEPTION", ex.getMessage());
    }
  }

  public boolean goBack() {
    fragmentManager.popBackStack();
    return true;
  }

  private void detachFragments(FragmentTransaction fragmentTransaction, Fragment searchFragment) {
    for (Fragment fragment : fragmentManager.getFragments()) {
      if(!fragment.isDetached() && fragment != searchFragment) {
        fragmentTransaction.detach(fragment);
      }
    }
    fragmentTransaction.commit();
  }

}
