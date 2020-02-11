package com.assetslookup.ui.assets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class AssetsListAdapter extends RecyclerView.Adapter<AssetsListAdapter.AssetsListViewHolder> {

  @NonNull
  @Override
  public AssetsListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull AssetsListViewHolder assetsListViewHolder, int i) {

  }

  @Override
  public int getItemCount() {
    return 0;
  }

  public class AssetsListViewHolder extends RecyclerView.ViewHolder {
    public AssetsListViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
