package com.assetslookup.ui.profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assetslookup.R;
import com.assetslookup.ui.shared.BaseChildNestedFragment;


public class ProfileFragment extends BaseChildNestedFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setTitle("PROFILE");
        Log.d("PROFILE","onViewCreated");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("PROFILE","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("PROFILE","onPause");
    }
}
