package com.assetslookup.ui.admin.summary;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assetslookup.R;
import com.assetslookup.data.internal.FragmentManagerHelper;
import com.assetslookup.data.internal.IFragmentManagerHelper;
import com.assetslookup.ui.admin.FragmentInteraction;

public class SummariesFragment extends Fragment implements FragmentInteraction {

  IFragmentManagerHelper fragmentManagerHelper;

  public SummariesFragment() { }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    fragmentManagerHelper = new FragmentManagerHelper(getChildFragmentManager(), R.id.summariesFragmentContainer);
    View view = inflater.inflate(R.layout.fragment_admin_summaries, container, false);

    fragmentManagerHelper.attach(SummariesFragmentList.class);

    Toolbar toolbar = view.findViewById(R.id.toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fragmentManagerHelper.goBack();
      }
    });

    return view;
  }

  @Override
  public void sendMessage(Message message) {
  }



}
