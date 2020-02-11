package com.assetslookup.ui.assets;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
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
import android.widget.Toast;
import android.widget.Toolbar;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.Asset;
import com.assetslookup.data.db.entities.Assets;
import com.assetslookup.data.internal.APIError;
import com.assetslookup.data.internal.ErrorUtils;
import com.assetslookup.data.internal.IFragmentInteraction;
import com.assetslookup.data.network.AssetsService;
import com.assetslookup.data.network.IAssetsService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssetsListFragment extends Fragment
  implements IFragmentInteraction {

  private IAssetsService assetsService = AssetsService.getInstance().create(IAssetsService.class);

  private RecyclerView assetsList;
  private AssetsListAdapter assetsListAdapter;
  private List<Asset> assets;

  private ProgressBar progressAssets;
  private SwipeRefreshLayout assetsRefresh;

  public AssetsListFragment() {
    assets = new ArrayList<>();
    assetsListAdapter = new AssetsListAdapter(assets);
  }

  public static AssetsListFragment newInstance() {
    AssetsListFragment fragment = new AssetsListFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_assets_list, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    assetsList = view.findViewById(R.id.assetsList);
    progressAssets = view.findViewById(R.id.progressAssets);
    assetsRefresh = view.findViewById(R.id.assetsRefresh);

    assetsList.setLayoutManager(new LinearLayoutManager(getContext()));
    assetsList.setAdapter(assetsListAdapter);
    refreshAssets();

    assetsRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        refreshAssets();
      }
    });
  }

  private void refreshAssets(){
    progressAssets.setVisibility(View.VISIBLE);
    assetsService.getAllAssets().enqueue(new Callback<Assets>() {
      @Override
      public void onResponse(Call<Assets> call, Response<Assets> response) {
        if(response.code() == 200) {
          if(response.body() != null) {
            assets.clear();
            assets.addAll(response.body().getAssets());
            assetsListAdapter.notifyDataSetChanged();
          }
        } else {
          APIError apiError = ErrorUtils.parseError(response);
          Toast.makeText(getContext(), apiError.message(), Toast.LENGTH_SHORT).show();
        }
        progressAssets.setVisibility(View.INVISIBLE);
        assetsRefresh.setRefreshing(false);
      }

      @Override
      public void onFailure(Call<Assets> call, Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        progressAssets.setVisibility(View.INVISIBLE);
        assetsRefresh.setRefreshing(false);
      }
    });
  }

  @Override
  public void sendMessage(Message message) {

  }
}
