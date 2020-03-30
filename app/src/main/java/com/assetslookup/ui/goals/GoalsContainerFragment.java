package com.assetslookup.ui.goals;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.assetslookup.ui.shared.BaseParentNestedFragment;
import com.assetslookup.data.internal.IFragmentInteraction;

public class GoalsContainerFragment extends BaseParentNestedFragment implements IFragmentInteraction {

    public GoalsContainerFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManagerHelper.attachIfNotExist(GoalsListFragment.class);
        toolbar.setTitle("Goals");
    }

    @Override
    public void sendMessage(Message message) {

    }
}
