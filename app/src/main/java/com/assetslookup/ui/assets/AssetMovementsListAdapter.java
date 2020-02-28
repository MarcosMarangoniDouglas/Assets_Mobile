package com.assetslookup.ui.assets;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.assetslookup.R;
import com.assetslookup.data.db.AssetsDatabase;
import com.assetslookup.data.db.entities.Asset;
import com.assetslookup.data.db.entities.Movement;
import com.assetslookup.data.internal.IFragmentInteraction;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssetMovementsListAdapter extends RecyclerView.Adapter<AssetMovementsListAdapter.AssetMovementsListViewHolder> {
  private List<Movement> movements;
  private Fragment fragment;
  private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

  public AssetMovementsListAdapter(List<Movement> movements, Fragment fragment) {
    this.movements = movements;
    this.fragment = fragment;
  }

  @NonNull
  @Override
  public AssetMovementsListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View v = LayoutInflater
      .from(viewGroup.getContext())
      .inflate(R.layout.fragment_asset_movements_list_item, viewGroup, false);
    AssetMovementsListViewHolder viewHolder = new AssetMovementsListViewHolder(v);
    return new AssetMovementsListViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull AssetMovementsListViewHolder assetsListViewHolder, final int i) {

    String date = null;
    try {
      Date parsedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault()).parse(movements.get(i).getDate());
      date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(parsedDate);
    } catch (ParseException e) {
      e.printStackTrace();
      date = "";
    }
    String kind = movements.get(i).getKind();
    Double balance = movements.get(i).getValue();
    String comment = movements.get(i).getComment();

    if(date != null) {
      assetsListViewHolder.txtDate.setText(date);
    }

    if(kind != null) {
      if(kind.equals("buy")) kind = "Buy";
      else if(kind.equals("dividend")) kind = "Dividend";
      assetsListViewHolder.txtKind.setText(kind);
    }

    if(balance != null) {
      assetsListViewHolder.txtValue.setText(numberFormat.format(balance));
    }

    if(comment != null) {
      assetsListViewHolder.txtComment.setText(comment);
    }
  }

  @Override
  public int getItemCount() {
    return movements.size();
  }

  class AssetMovementsListViewHolder extends RecyclerView.ViewHolder {
    TextView txtDate, txtKind, txtValue, txtComment;

    public AssetMovementsListViewHolder(@NonNull View itemView) {
      super(itemView);
      txtDate = itemView.findViewById(R.id.txtDate);
      txtKind = itemView.findViewById(R.id.txtKind);
      txtValue = itemView.findViewById(R.id.txtValue);
      txtComment = itemView.findViewById(R.id.txtComment);
    }
  }
}
