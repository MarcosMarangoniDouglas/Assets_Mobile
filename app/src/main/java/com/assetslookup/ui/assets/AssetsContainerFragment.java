package com.assetslookup.ui.assets;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assetslookup.R;
import com.assetslookup.data.internal.IFragmentInteraction;
import com.assetslookup.ui.shared.BaseParentNestedFragment;

public class AssetsContainerFragment extends BaseParentNestedFragment
  implements IFragmentInteraction {

  public AssetsContainerFragment() { }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    fragmentManagerHelper.attach(AssetsListFragment.class);
  }

  @Override
  public void sendMessage(Message message) {
    
  }
}
