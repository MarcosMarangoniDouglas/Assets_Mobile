package com.assetslookup.ui.admin.report;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assetslookup.R;
import com.assetslookup.data.internal.FragmentManagerHelper;
import com.assetslookup.data.internal.IFragmentManagerHelper;
import com.assetslookup.ui.admin.FragmentInteraction;

public class ReportsFragment extends Fragment implements FragmentInteraction {

  IFragmentManagerHelper fragmentManagerHelper;

  public ReportsFragment() { }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    fragmentManagerHelper = new FragmentManagerHelper(getChildFragmentManager(), R.id.reportsFragmentContainer);
    View view = inflater.inflate(R.layout.fragment_admin_reports, container, false);
    fragmentManagerHelper.attach(ReportsFragmentList.class);
    return view;
  }

  @Override
  public void sendMessage(Message message) {

  }
}
