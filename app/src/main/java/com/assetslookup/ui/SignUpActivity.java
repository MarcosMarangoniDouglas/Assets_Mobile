package com.assetslookup.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.User;
import com.assetslookup.data.network.IAssetsService;
import com.assetslookup.data.network.AssetsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity{

  IAssetsService warehouseService = AssetsService.getInstance().create(IAssetsService.class);

  EditText editPassword;
  EditText editUsername;
  EditText editName;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    Toolbar toolbar = findViewById(R.id.signUpToolBar);

    editName = findViewById(R.id.editName);
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
    String name = editName.getText().toString();

    //TODO handle unassigned and noImage
    //User user = new User(name, username, password, "client", "unAssigned", "", "", "NoImage", "");
/*    warehouseService.createUser(user).enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        if(response.code() == 200) {
          Toast.makeText(SignUpActivity.this, "USER CREATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(SignUpActivity.this, "Your credentials are wrong", Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        Toast.makeText(SignUpActivity.this, "The server is down", Toast.LENGTH_SHORT).show();
      }
    });*/
  }
}
