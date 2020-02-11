package com.assetslookup.ui.admin.warehouse;


import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.internal.FragmentManagerHelper;
import com.assetslookup.data.internal.IFragmentManagerHelper;
import com.assetslookup.data.internal.JsonReader;
import com.assetslookup.data.network.IAssetsService;
import com.assetslookup.data.network.AssetsService;
import com.assetslookup.ui.admin.FragmentInteraction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WarehousesFragmentList extends Fragment
        implements View.OnClickListener,  SwipeRefreshLayout.OnRefreshListener, FragmentInteraction {

  IAssetsService warehouseService = AssetsService.getInstance().create(IAssetsService.class);

  List<Warehouse> warehouses;
  WarehousesListAdapter warehouseListAdapter;
  private FloatingActionButton floatingActionButton;
  private RecyclerView warehousesList;

  private SwipeRefreshLayout swipeRefreshLayout;
  private ProgressBar progressBar;

  JsonReader jsonReader;
  IFragmentManagerHelper fragmentManagerHelper;

  public WarehousesFragmentList() { }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_admin_warehouses_list, container, false);
    jsonReader = new JsonReader(getContext());
    warehousesList = view.findViewById(R.id.warehousesList);

    fragmentManagerHelper = new FragmentManagerHelper(
            getFragmentManager(), R.id.warehousesFragmentContainer);

    floatingActionButton = view.findViewById(R.id.floatingActionButton);
    floatingActionButton.setOnClickListener(this);
    swipeRefreshLayout = view.findViewById(R.id.warehousesListRefresh);
    swipeRefreshLayout.setOnRefreshListener(this);

    progressBar = view.findViewById(R.id.progress_loader);

    warehousesList.setLayoutManager(new LinearLayoutManager(getContext()));
    warehousesList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    warehouses = new ArrayList<>();
    warehouseListAdapter = new WarehousesListAdapter(warehouses, this);

    warehousesList.setAdapter(warehouseListAdapter);

    getData();

    return view;
  }

  private void getData() {
    progressBar.setVisibility(View.VISIBLE);
    warehouseService.getAllWarehouse().enqueue(new Callback<List<Warehouse>>() {
      @Override
      public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
        if(response.body() != null) {
          warehouses.clear();
          warehouses.addAll(response.body());
          warehouseListAdapter.notifyDataSetChanged();
          swipeRefreshLayout.setRefreshing(false);
          progressBar.setVisibility(View.INVISIBLE);
        }
      }

      @Override
      public void onFailure(Call<List<Warehouse>> call, Throwable t) {
        Toast.makeText(getContext(), "Failed to reach the server", Toast.LENGTH_LONG).show();
        Log.d("ERROR", t.getMessage());
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d("WAREHOUSES", "RESUME");
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.floatingActionButton:
        onFloatingActionButtonClicked(v);
        break;
    }
  }

  private void onFloatingActionButtonClicked(View v) {
    fragmentManagerHelper.attach(WarehouseAddFragment.class);
  }

  @Override
  public void onRefresh() {
    getData();
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if(!hidden) {
      onRefresh();
    }
  }

  @Override
  public void sendMessage(Message message) {
    Warehouse warehouse;
    Bundle bundle;
    warehouse = (Warehouse) message.obj;
    bundle = new Bundle();
    bundle.putSerializable("WAREHOUSE", warehouse);
    fragmentManagerHelper.attach(WarehouseDetailFragment.class, bundle);
  }

}
