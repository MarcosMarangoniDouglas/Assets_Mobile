package com.assetslookup.data.internal;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.List;

public class FragmentManagerHelper {

    private FragmentManager fragmentManager;
    private int fragmentContainer;

    public FragmentManagerHelper(FragmentManager fragmentManager, int fragmentContainer) {
        this.fragmentManager = fragmentManager;
        this.fragmentContainer = fragmentContainer;
    }

    public void attachIfNotExist(Class<? extends Fragment> newClass) {
        try {
            Fragment searchFragment = fragmentManager.findFragmentByTag(newClass.getName());
            if(searchFragment == null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Constructor<? extends Fragment> constructor = newClass.getConstructor();
                Fragment fragment = constructor.newInstance();
                fragmentTransaction.add(fragmentContainer, fragment, newClass.getName());
                fragmentTransaction.commit();
            }
        } catch (Exception ex) {
            Log.d("EXCEPTION", ex.getMessage());
        }
    }

    public void attach(Class<? extends Fragment> newClass) {
        try {
            Fragment searchFragment = fragmentManager.findFragmentByTag(newClass.getName());
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            hideFragments(fragmentTransaction, searchFragment);
            if(searchFragment == null) {
                Constructor<? extends Fragment> constructor = newClass.getConstructor();
                Fragment fragment = constructor.newInstance();
                fragmentTransaction.add(fragmentContainer, fragment, newClass.getName());
            } else {
                fragmentTransaction.show(searchFragment);
            }
            fragmentTransaction.commit();
        } catch (Exception ex) {
            Log.d("EXCEPTION", ex.getMessage());
        }
    }

    public void attach(Class<? extends Fragment> newClass, Bundle bundle) {
        try {
            Fragment searchFragment = fragmentManager.findFragmentByTag(newClass.getName());
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            hideFragments(fragmentTransaction, searchFragment);
            if(searchFragment == null) {
                Constructor<? extends Fragment> constructor = newClass.getConstructor();
                Fragment fragment = constructor.newInstance();
                fragmentTransaction.add(fragmentContainer, fragment, newClass.getName());
                fragment.setArguments(bundle);
            } else {
                fragmentTransaction.show(searchFragment);
            }
            fragmentTransaction.commit();
        } catch (Exception ex) {
            Log.d("EXCEPTION", ex.getMessage());
        }
    }

    public boolean goBack() {
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments.size() <= 1) return false;
        Fragment lastFragment = fragments.get(fragments.size() - 1);
        Fragment penultimateFragment = fragments.get(fragments.size() - 2);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(lastFragment);
        fragmentTransaction.detach(penultimateFragment);
        fragmentTransaction.attach(penultimateFragment);
        fragmentTransaction.show(penultimateFragment);
        fragmentTransaction.commit();
        return true;
    }

    public void sendMessage(Class<? extends Fragment> fragment, Message message) {
        Fragment tmpFragment = fragmentManager.findFragmentByTag(fragment.getName());
        if(tmpFragment != null) {
            if(tmpFragment instanceof IFragmentInteraction) {
                ((IFragmentInteraction)tmpFragment).sendMessage(message);
            }
        }
    }

    private void hideFragments(FragmentTransaction fragmentTransaction, Fragment searchFragment) {
        for (Fragment fragment : fragmentManager.getFragments()) {
            if(fragment.getUserVisibleHint() && fragment != searchFragment) {
                fragmentTransaction.hide(fragment);
            }
        }
    }

}
