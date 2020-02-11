package com.assetslookup.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.db.AssetsDatabase;
import com.assetslookup.data.db.entities.User;
import com.assetslookup.data.network.IAssetsService;
import com.assetslookup.data.network.AssetsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    AssetsDatabase assetsDatabase;
    IAssetsService assetsService =
            AssetsService.getInstance().create(IAssetsService.class);

    String username, password;
    EditText editPasssword, editUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        assetsDatabase = AssetsDatabase.getAppDatabase(this.getApplicationContext());

        editUsername = findViewById(R.id.editUsername);
        editPasssword = findViewById(R.id.editPassword);

        // TODO REMOVE LATER
        findViewById(R.id.imgHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUsername.setText("ww.meta@chemistry.org");
                editPasssword.setText("1234");
            }
        });

    }

    public void onSignUp(View view) {
        Intent it = new Intent(this, SignUpActivity.class);
        startActivity(it);
    }

    public void onSignIn(View view) {
        username = editUsername.getText().toString();
        password = editPasssword.getText().toString();

        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        assetsDatabase.userDao().deleteAllUsers();
        assetsService.login(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200) {
                    if(response.body() != null) {
                        User loggedUser = response.body();
                        assetsDatabase.userDao().insertUser(loggedUser);
                        //AssetsService.setToken(response.body().token);
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Username or password incorrect",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Unable to reach the server",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onForgotPassword(View view) {
        Intent it = new Intent(this, ResetPasswordActivity.class);
        startActivity(it);
    }
}
