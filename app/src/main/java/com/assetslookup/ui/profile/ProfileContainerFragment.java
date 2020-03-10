package com.assetslookup.ui.profile;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.assetslookup.data.internal.IFragmentInteraction;
import com.assetslookup.ui.assets.AssetsListFragment;
import com.assetslookup.ui.shared.BaseParentNestedFragment;

public class ProfileContainerFragment extends BaseParentNestedFragment implements IFragmentInteraction
    {

  public ProfileContainerFragment() { }

        @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentManagerHelper.attach(ProfileFragment.class);
    }

        @Override
        public void sendMessage(Message message) {

    }
}
