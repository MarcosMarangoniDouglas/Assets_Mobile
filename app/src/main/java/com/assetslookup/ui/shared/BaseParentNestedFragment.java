package com.assetslookup.ui.shared;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.support.v7.widget.Toolbar;

import com.assetslookup.R;
import com.assetslookup.data.internal.FragmentManagerHelper;

public class BaseParentNestedFragment extends Fragment {

  protected FragmentManagerHelper fragmentManagerHelper;
  protected Toolbar toolbar;
  protected FrameLayout frameLayout;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_container_base, container, false);
    toolbar = v.findViewById(R.id.toolBar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fragmentManagerHelper.goBack();
      }
    });
    frameLayout = v.findViewById(R.id.fragmentContainer);
    fragmentManagerHelper = new FragmentManagerHelper(getChildFragmentManager(), R.id.fragmentContainer);
    return v;
  }
}
