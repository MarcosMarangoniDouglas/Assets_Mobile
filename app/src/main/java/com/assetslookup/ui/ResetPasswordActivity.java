package com.assetslookup.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
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
import com.assetslookup.data.network.AssetsService;
import com.assetslookup.data.network.IAssetsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity
  implements View.OnClickListener {

  private String token = "";
  private String username = "";

  IAssetsService assetsService = AssetsService.getInstance().create(IAssetsService.class);

  Button btnResetPassword;
  ProgressBar resetPasswordProgressBar;
  Toolbar resetPasswordToolBar;

  EditText editPassword;
  EditText editRepeatPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reset_password);
    editPassword = findViewById(R.id.editPassword);
    editRepeatPassword = findViewById(R.id.editRepeatPassword);
    btnResetPassword = findViewById(R.id.btnResetPassword);
    btnResetPassword.setOnClickListener(this);
    resetPasswordProgressBar = findViewById(R.id.resetPasswordProgressBar);
    resetPasswordToolBar = findViewById(R.id.resetPasswordToolBar);
    resetPasswordToolBar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        goToParent();
      }
    });
    readToken(getIntent());
  }

  @Override
  public void onBackPressed() {
    goToParent();
  }

  private void goToParent() {
    if(isTaskRoot()) {
      startActivity(NavUtils.getParentActivityIntent(this));
      finish();
    } else {
      super.onBackPressed();
    }
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    readToken(intent);
  }

  private void readToken(Intent intent) {
    Uri data = intent.getData();
    if(data.getQueryParameter("token") != null &&
      data.getQueryParameter("username") != null) {
      token = data.getQueryParameter("token");
      username = data.getQueryParameter("username");
    }
  }

  @Override
  public void onClick(View v) {
    if(!editPassword.getText().toString().equals(editRepeatPassword.getText().toString())) {
      Toast.makeText(this, "Passwords does not match", Toast.LENGTH_SHORT).show();
    }
    String password = editPassword.getText().toString();
    resetPasswordProgressBar.setVisibility(View.VISIBLE);
    User user=  new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setForgotPasswordToken(token);
    assetsService.resetPassword(user).enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        if(response.code() == 200) {
          Toast.makeText(ResetPasswordActivity.this, "Password reset success!", Toast.LENGTH_SHORT).show();
        } else {
          APIError apiError = ErrorUtils.parseError(response);
          Toast.makeText(ResetPasswordActivity.this, apiError.message(), Toast.LENGTH_SHORT).show();
        }
        resetPasswordProgressBar.setVisibility(View.INVISIBLE);
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {
        resetPasswordProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(ResetPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }
}
