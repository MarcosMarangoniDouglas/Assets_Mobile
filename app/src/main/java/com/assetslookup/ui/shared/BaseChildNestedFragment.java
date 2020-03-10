package com.assetslookup.ui.shared;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.assetslookup.R;
import com.assetslookup.data.internal.FragmentManagerHelper;
import com.assetslookup.data.network.AssetsService;
import com.assetslookup.data.network.IAssetsService;

import java.text.NumberFormat;

public class BaseChildNestedFragment extends Fragment {
  protected FragmentManagerHelper fragmentManagerHelper;
  protected IAssetsService assetsService = AssetsService.getInstance().create(IAssetsService.class);
  protected NumberFormat numberFormat = NumberFormat.getNumberInstance();

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.fragmentContainer);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

}
