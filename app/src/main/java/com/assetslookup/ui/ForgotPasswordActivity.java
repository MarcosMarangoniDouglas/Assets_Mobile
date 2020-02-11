package com.assetslookup.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.User;
import com.assetslookup.data.network.IAssetsService;
import com.assetslookup.data.network.AssetsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity
  implements View.OnClickListener{

  IAssetsService assetsService = AssetsService.getInstance().create(IAssetsService.class);

  EditText editUsername;
  ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_password);
    editUsername = findViewById(R.id.editUsername);
    progressBar = new ProgressBar(this);
    Toolbar toolbar = findViewById(R.id.forgotPasswordToolBar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  @Override
  public void onClick(View view) {
    progressBar.setVisibility(View.VISIBLE);
  }
}
