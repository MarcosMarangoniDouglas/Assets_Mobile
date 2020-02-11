package com.assetslookup.ui.assets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.Asset;

import java.text.NumberFormat;
import java.util.List;

public class AssetsListAdapter extends RecyclerView.Adapter<AssetsListAdapter.AssetsListViewHolder> {

  private List<Asset> assets;
  private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

  public AssetsListAdapter(List<Asset> assets) {
    this.assets = assets;
  }

  @NonNull
  @Override
  public AssetsListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View v = LayoutInflater
            .from(viewGroup.getContext())
            .inflate(R.layout.fragment_assets_list_item, viewGroup, false);
    AssetsListViewHolder viewHolder = new AssetsListViewHolder(v);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull AssetsListViewHolder assetsListViewHolder, int i) {
    assetsListViewHolder.txtCode.setText(assets.get(i).getCode());
    assetsListViewHolder.txtName.setText(assets.get(i).getName());
    assetsListViewHolder.txtBalance.setText(numberFormat.format(assets.get(i).getBalance()));
  }

  @Override
  public int getItemCount() {
    return assets.size();
  }

  public class AssetsListViewHolder extends RecyclerView.ViewHolder {
    public TextView txtName, txtCode, txtBalance;
    public AssetsListViewHolder(@NonNull View itemView) {
      super(itemView);
      txtName = itemView.findViewById(R.id.txtName);
      txtCode = itemView.findViewById(R.id.txtCode);
      txtBalance = itemView.findViewById(R.id.txtBalance);
    }
  }
}
