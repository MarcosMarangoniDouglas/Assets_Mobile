package com.assetslookup.ui.assets;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.Asset;
import com.assetslookup.data.internal.IFragmentInteraction;

import java.text.NumberFormat;
import java.util.List;

public class AssetsListAdapter extends RecyclerView.Adapter<AssetsListAdapter.AssetsListViewHolder> {

  private List<Asset> assets;
  private NumberFormat numberFormat = NumberFormat.getNumberInstance();

  private Fragment fragment;

  public AssetsListAdapter(List<Asset> assets, Fragment fragment) {
    this.assets = assets;
    this.fragment = fragment;
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
  public void onBindViewHolder(@NonNull AssetsListViewHolder assetsListViewHolder, final int i) {
    assetsListViewHolder.txtCode.setText(assets.get(i).getCode());
    assetsListViewHolder.txtName.setText(assets.get(i).getName());
    assetsListViewHolder.txtBalance.setText(numberFormat.format(assets.get(i).getBalance()));
    assetsListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendMessage(1, assets.get(i));
      }
    });
    assetsListViewHolder.btnEditAsset.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        sendMessage(2, assets.get(i));
      }
    });

    assetsListViewHolder.btnDeleteAsset.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        sendMessage(3, assets.get(i));
      }
    });
  }

  private void sendMessage(int what, Object obj) {
    Message m = new Message();
    m.what = what;
    m.obj = obj;
    ((IFragmentInteraction)fragment).sendMessage(m);
  }

  @Override
  public int getItemCount() {
    return assets.size();
  }

  public class AssetsListViewHolder extends RecyclerView.ViewHolder {
    public TextView txtName, txtCode, txtBalance;
    ImageButton btnDeleteAsset, btnEditAsset;
    public AssetsListViewHolder(@NonNull View itemView) {
      super(itemView);
      txtName = itemView.findViewById(R.id.txtName);
      txtCode = itemView.findViewById(R.id.txtCode);
      txtBalance = itemView.findViewById(R.id.txtBalance);
      btnDeleteAsset = itemView.findViewById(R.id.btnDeleteAsset);
      btnEditAsset = itemView.findViewById(R.id.btnEditAsset);
    }
  }
}
