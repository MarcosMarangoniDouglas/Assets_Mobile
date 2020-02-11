package com.assetslookup.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class ResetPasswordActivity extends AppCompatActivity {

  IAssetsService assetsService = AssetsService.getInstance().create(IAssetsService.class);

  EditText editPassword;
  EditText editRepeatPassword;
  ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_password);

    editPassword = findViewById(R.id.editPassword);
    editRepeatPassword = findViewById(R.id.editRepeatPassword);

    progressBar = new ProgressBar(this);

    Toolbar toolbar = findViewById(R.id.forgotPasswordToolBar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  public void onResetPassword(View view) {
    String password = editPassword.getText().toString();
    String repeatPassword = editRepeatPassword.getText().toString();

    if(!password.equals(repeatPassword)) {
      Toast.makeText(this, "The passwords doesn't match", Toast.LENGTH_SHORT).show();
    }

    User user = new User("", "", password, "client", "", "", "", "", "");
    progressBar.setVisibility(View.VISIBLE);
    assetsService.editUserPassword(user).enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        if(response.isSuccessful()) {
          Snackbar.make(
            findViewById(android.R.id.content),
            "User password changed successfully!",
            Snackbar.LENGTH_LONG
          ).show();
        } else {
          Toast.makeText(ResetPasswordActivity.this, response.message(), Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.INVISIBLE);
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {
        Toast.makeText(ResetPasswordActivity.this, "Failed to contact the server", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
      }
    });
  }
}
