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
    progressBar.setVisibility(View.VISIBLE);
  }
}
