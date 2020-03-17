package com.assetslookup.ui.assets;


import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.Asset;
import com.assetslookup.data.db.entities.SearchQuote;
import com.assetslookup.data.internal.APIError;
import com.assetslookup.data.internal.ErrorUtils;
import com.assetslookup.data.internal.IFragmentInteraction;
import com.assetslookup.ui.HomeActivity;
import com.assetslookup.ui.shared.BaseChildNestedFragment;
import com.assetslookup.ui.shared.CustomEditText;
import com.assetslookup.ui.shared.DrawableClickListener;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
  ProgressBar assetCreateProgressBar;
  Boolean isUpdate = false;
  String idToUpdate;


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
    //toolbar.setTitle("CREATE ASSET");
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
            bundle.putString("SEARCH_CODE", quoteName);
          } else {
            bundle.putString("SEARCH_CODE", "");
          }
          fragmentManagerHelper.attach(SearchQuoteFragment.class, bundle);
        }
      }
    });

    assetCreateProgressBar = view.findViewById(R.id.assetCreateProgressBar);

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



    if(getArguments() != null) {
      if (getArguments().getSerializable("EDIT_ASSET") != null) {
        Asset asset = (Asset) getArguments().getSerializable("EDIT_ASSET");

        editAssetName.setText(asset.getName());
        editStockQuantity.setText(convertToFormat(asset.getBalance()));
        editUnitPrice.setText(asset.getUnit().toString());
        editStockLocation.setText(asset.getGroupA());
        editStockSection.setText(asset.getGroupB());
        editStockCategory.setText(asset.getGroupC());
        btnCreateAsset.setText("U P D A T E  A S S E T");
        isUpdate = true;
        idToUpdate = asset.getId();

        if(asset.getCode().length()!=0){
          checkPublicStock.setChecked(true);
          onRadioPublicStockClick(view);
          editQuoteName.setText(asset.getCode());
        }


      }
    }


  }

  DecimalFormat df = new DecimalFormat("#");
  private String convertToFormat(double value){

    return df.format(value);
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
    String stockQuantity;
    String unitPrice;
    if(editStockQuantity.getText().toString().isEmpty()) {
      stockQuantity = "0";
    } else stockQuantity = editStockQuantity.getText().toString();
    if(editUnitPrice.getText().toString().isEmpty()) {
      unitPrice = "0";
    } else unitPrice = editUnitPrice.getText().toString();
    Double quantityOfStocks = Double.parseDouble(stockQuantity);
    Double priceUnit = Double.parseDouble(unitPrice);

    String assetName = editAssetName.getText().toString();
    String quoteName = editQuoteName.getText().toString();
    String stockLocation = editStockLocation.getText().toString();
    String stockSection = editStockSection.getText().toString();
    String stockCategory = editStockCategory.getText().toString();

    Asset asset = new Asset();
    asset.setName(assetName);
    asset.setUnit(priceUnit);
    asset.setBalance(quantityOfStocks);
    if(!quoteName.isEmpty()) {
      asset.setAutorefresh(true);
    } else {
      asset.setAutorefresh(false);
    }
    asset.setCode(quoteName);
    asset.setGroupA(stockCategory);
    asset.setGroupB(stockLocation);
    asset.setGroupC(stockSection);

    assetCreateProgressBar.setVisibility(View.VISIBLE);

    if(isUpdate){
      asset.setId(idToUpdate);

      assetsService.updateAsset(asset).enqueue(new Callback<Asset>() {
        @Override
        public void onResponse(Call<Asset> call, Response<Asset> response) {
          if(response.code() == 200) {
            Toast.makeText(getContext(), "Asset updated successfully!", Toast.LENGTH_SHORT).show();
            //Return to Assets Container
            fragmentManagerHelper.goBack();

          } else {
            APIError apiError = ErrorUtils.parseError(response);
            Toast.makeText(getContext(), apiError.message(), Toast.LENGTH_SHORT).show();
          }
          assetCreateProgressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onFailure(Call<Asset> call, Throwable t) {
          Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
          assetCreateProgressBar.setVisibility(View.INVISIBLE);
        }
      });



    }


    else{
      assetsService.createAsset(asset).enqueue(new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
          if(response.code() == 200) {
            Toast.makeText(getContext(), "Asset created successfully!", Toast.LENGTH_SHORT).show();
            //Return to Assets Container
            fragmentManagerHelper.goBack();

          } else {
            APIError apiError = ErrorUtils.parseError(response);
            Toast.makeText(getContext(), apiError.message(), Toast.LENGTH_SHORT).show();
          }
          assetCreateProgressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
          Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
          assetCreateProgressBar.setVisibility(View.INVISIBLE);
        }
      });

    }

  }

  private void onRadioPublicStockClick(View v) {
    if(checkPublicStock.isChecked()) {
      // Show quotes name option
      ConstraintLayout.LayoutParams newLayoutParams = (ConstraintLayout.LayoutParams) layoutStockInput.getLayoutParams();
      double density = getResources().getDisplayMetrics().density;
      newLayoutParams.topMargin = (int)(90 * density);
      layoutStockInput.setLayoutParams(newLayoutParams);
      layoutPublicStock.setVisibility(View.VISIBLE);
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
      Bundle bundle = new Bundle();
      String quoteName = editQuoteName.getText().toString();
      if(!quoteName.isEmpty()) {
        bundle.putString("SEARCH_CODE", quoteName);
      } else {
        bundle.putString("SEARCH_CODE", "");
      }
      fragmentManagerHelper.attach(SearchQuoteFragment.class, bundle);
    }
  }

  @Override
  public void sendMessage(Message message) {
    if(message.what == 1) {
      SearchQuote searchQuote = (SearchQuote) message.obj;
      editQuoteName.setText(searchQuote.getCode());
    }
  }
}
