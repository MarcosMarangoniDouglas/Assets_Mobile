package com.assetslookup.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.User;
import com.assetslookup.data.internal.APIError;
import com.assetslookup.data.internal.ErrorUtils;
import com.assetslookup.data.network.IAssetsService;
import com.assetslookup.data.network.AssetsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity
  implements View.OnClickListener{

  IAssetsService assetsService = AssetsService.getInstance().create(IAssetsService.class);

  EditText editUsername;
  ProgressBar forgotPasswordProgressBar;
  Button btnResetPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_password);
    editUsername = findViewById(R.id.editUsername);
    forgotPasswordProgressBar = findViewById(R.id.forgotPasswordProgressBar);
    findViewById(R.id.btnResetPassword).setOnClickListener(this);
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
    forgotPasswordProgressBar.setVisibility(View.VISIBLE);
    String username = editUsername.getText().toString();
    User user = new User();
    user.setUsername(username);
    assetsService.forgotPassword(user).enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        if(response.code() == 200) {
          Toast.makeText(ForgotPasswordActivity.this, "EMAIL SENT", Toast.LENGTH_LONG).show();
        } else {
          APIError apiError = ErrorUtils.parseError(response);
          Toast.makeText(ForgotPasswordActivity.this, apiError.message(), Toast.LENGTH_LONG).show();
        }
        forgotPasswordProgressBar.setVisibility(View.INVISIBLE);
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {
        forgotPasswordProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(ForgotPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }
}
