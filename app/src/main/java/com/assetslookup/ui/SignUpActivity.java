package com.assetslookup.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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

public class SignUpActivity extends AppCompatActivity{

  IAssetsService warehouseService = AssetsService.getInstance().create(IAssetsService.class);

  EditText editFirstName;
  EditText editLastName;
  EditText editUsername;
  EditText editPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);

    Toolbar toolbar = findViewById(R.id.signUpToolBar);

    editFirstName = findViewById(R.id.editFirstName);
    editLastName = findViewById(R.id.editLastName);
    editUsername = findViewById(R.id.editUsername);
    editPassword = findViewById(R.id.editPassword);

    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  public void onSignUp(View view) {
    String username = editUsername.getText().toString();
    String password = editPassword.getText().toString();
    String firstName = editFirstName.getText().toString();
    String lastName = editLastName.getText().toString();

    User user = new User(username, firstName, lastName, password);
    warehouseService.signUp(user).enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        if(response.code() == 200) {
          Toast.makeText(SignUpActivity.this, "USER CREATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        } else {
          APIError apiError = ErrorUtils.parseError(response);
          Toast.makeText(SignUpActivity.this, apiError.message(), Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }
}
