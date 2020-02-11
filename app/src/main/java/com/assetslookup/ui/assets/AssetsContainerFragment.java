package com.assetslookup.ui.assets;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assetslookup.R;
import com.assetslookup.data.internal.FragmentManagerHelper;
import com.assetslookup.data.internal.IFragmentInteraction;

public class AssetsContainerFragment extends Fragment
  implements IFragmentInteraction {
  private static final String ARG_PARAM1 = "param1";
  FragmentManagerHelper fragmentManagerHelper;

  private String mParam1;

  public AssetsContainerFragment() { }

  public static AssetsContainerFragment newInstance() {
    AssetsContainerFragment fragment = new AssetsContainerFragment();
    Bundle args = new Bundle();
    //args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      //mParam1 = getArguments().getString(ARG_PARAM1);
    }

    fragmentManagerHelper = new FragmentManagerHelper(getChildFragmentManager(), R.id.assetsFragmentContainer);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_assets_container, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void sendMessage(Message message) {
    
  }
}
