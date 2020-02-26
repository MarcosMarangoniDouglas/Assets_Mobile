package com.assetslookup.ui.assets;


import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.SearchQuote;
import com.assetslookup.data.internal.IFragmentInteraction;
import com.assetslookup.ui.shared.BaseChildNestedFragment;
import com.assetslookup.ui.shared.CustomEditText;
import com.assetslookup.ui.shared.DrawableClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssetsCreateFragment extends BaseChildNestedFragment
  implements View.OnClickListener, DrawableClickListener, IFragmentInteraction {

  ConstraintLayout layoutPublicStock, layoutStockInput;

  EditText editAssetName, editStockQuantity, editUnitPrice,
    editStockLocation, editStockSection, editStockCategory;
  CustomEditText editQuoteName;
  Button btnCreateAsset;
  CheckBox checkPublicStock;

  public AssetsCreateFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_assets_create, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    toolbar.setTitle("CREATE ASSET");
    editAssetName = view.findViewById(R.id.editAssetName);

    editQuoteName = view.findViewById(R.id.editQuoteName);
    editQuoteName.setDrawableClickListener(this);
    editQuoteName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
          Bundle bundle = new Bundle();
          String quoteName = editQuoteName.getText().toString();
          if(!quoteName.isEmpty()) {
            String[] quoteSplitted = quoteName.split(" - ");
            bundle.putString("SEARCH_CODE", quoteSplitted[0]);
            bundle.putString("SEARCH_NAME", quoteSplitted[1]);
          } else {
            bundle.putString("SEARCH_CODE", "");
            bundle.putString("SEARCH_NAME", "");
          }
          fragmentManagerHelper.attach(SearchQuoteFragment.class, bundle);
        }
      }
    });

    editStockQuantity = view.findViewById(R.id.editStockQuantity);
    editUnitPrice = view.findViewById(R.id.editUnitPrice);
    editStockLocation = view.findViewById(R.id.editStockLocation);
    editStockSection = view.findViewById(R.id.editStockSection);
    editStockCategory = view.findViewById(R.id.editStockCategory);

    layoutPublicStock = view.findViewById(R.id.layoutPublicStock);
    layoutStockInput = view.findViewById(R.id.layoutStockInput);

    btnCreateAsset = view.findViewById(R.id.btnCreateAsset);
    btnCreateAsset.setOnClickListener(this);

    checkPublicStock = view.findViewById(R.id.checkPublicStock);
    checkPublicStock.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.checkPublicStock:
        onRadioPublicStockClick(v);
        break;
      case R.id.btnCreateAsset:
        onBtnCreateAssetClick(v);
        break;
    }
  }

  private void onBtnCreateAssetClick(View v) {

  }

  private void onRadioPublicStockClick(View v) {
    if(checkPublicStock.isChecked()) {
      // Show quotes name option
      ConstraintLayout.LayoutParams newLayoutParams = (ConstraintLayout.LayoutParams) layoutStockInput.getLayoutParams();
      int density = (int) getResources().getDisplayMetrics().density;
      newLayoutParams.topMargin = 90 * density;
      layoutStockInput.setLayoutParams(newLayoutParams);
      layoutPublicStock.setVisibility(View.VISIBLE);
      editQuoteName.requestFocus();
    } else {
      // Hide quotes name option
      ConstraintLayout.LayoutParams newLayoutParams = (ConstraintLayout.LayoutParams) layoutStockInput.getLayoutParams();
      newLayoutParams.topMargin = 0;
      layoutStockInput.setLayoutParams(newLayoutParams);
      layoutPublicStock.setVisibility(View.GONE);
    }
  }

  @Override
  public void onClick(DrawablePosition target) {
    if(target == DrawablePosition.RIGHT) {
      Toast.makeText(getContext(), "Search for quote", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void sendMessage(Message message) {
    if(message.what == 1) {
      SearchQuote searchQuote = (SearchQuote) message.obj;
      editQuoteName.setText(searchQuote.getCode() + " - " +searchQuote.getName());
    }
  }
}
