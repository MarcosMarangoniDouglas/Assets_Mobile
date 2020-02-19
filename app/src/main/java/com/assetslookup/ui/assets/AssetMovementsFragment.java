package com.assetslookup.ui.assets;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.Asset;
import com.assetslookup.data.db.entities.Movement;
import com.assetslookup.data.internal.APIError;
import com.assetslookup.data.internal.ErrorUtils;
import com.assetslookup.ui.shared.BaseChildNestedFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssetMovementsFragment extends BaseChildNestedFragment {

  String assetId;

  TextView txtName;
  TextView txtCode;
  TextView txtBalance;

  SwipeRefreshLayout assetMovementsRefresh;
  RecyclerView assetMovementsList;
  AssetMovementsListAdapter assetMovementsListAdapter;
  List<Movement> movements;

  ProgressBar progressBar;

  public AssetMovementsFragment() {
    movements = new ArrayList<>();
    assetMovementsListAdapter = new AssetMovementsListAdapter(movements);
    assetId = "";
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_asset_movements, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    toolbar.setTitle("MOVEMENTS");

    txtName = view.findViewById(R.id.txtName);
    txtCode = view.findViewById(R.id.txtCode);
    txtBalance = view.findViewById(R.id.txtBalance);

    assetMovementsRefresh = view.findViewById(R.id.assetMovementsRefresh);
    assetMovementsRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        refreshAssetMovements();
      }
    });
    assetMovementsList = view.findViewById(R.id.assetMovementsList);
    assetMovementsList.setAdapter(assetMovementsListAdapter);
    assetMovementsList.setLayoutManager(new LinearLayoutManager(getContext()));

    progressBar = view.findViewById(R.id.progressBar);

    if(getArguments() != null) {
      Bundle args = getArguments();
      if(args.getString("ASSET_ID") != null) {
        assetId = args.getString("ASSET_ID");
        refreshAssetMovements();
      }
    }
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    if(!hidden) {
      toolbar.setTitle("MOVEMENTS");
    }
  }

  public void refreshAssetMovements() {
    if(assetId.isEmpty()) return;
    progressBar.setVisibility(View.VISIBLE);
    assetsService.getAsset(assetId).enqueue(new Callback<Asset>() {
      @Override
      public void onResponse(Call<Asset> call, Response<Asset> response) {
        if(response.code() == 200) {
          if(response.body() != null) {
            Asset asset = response.body();
            txtName.setText(asset.getName());
            txtCode.setText(asset.getCode());
            if(asset.getBalance() != null) {
              txtBalance.setText(numberFormat.format(asset.getBalance()));
            } else {
              txtBalance.setText(numberFormat.format(0));
            }
            movements.clear();
            movements.addAll(response.body().getMovements());
            assetMovementsListAdapter.notifyDataSetChanged();
          }
        } else {
          APIError apiError = ErrorUtils.parseError(response);
          Toast.makeText(getContext(), apiError.message(), Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.INVISIBLE);
        assetMovementsRefresh.setRefreshing(false);
      }

      @Override
      public void onFailure(Call<Asset> call, Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
        assetMovementsRefresh.setRefreshing(false);
      }
    });
  }
}
