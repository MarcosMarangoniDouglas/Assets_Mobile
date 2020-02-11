package com.assetslookup.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.db.AssetsDatabase;
import com.assetslookup.data.db.entities.Token;
import com.assetslookup.data.db.entities.User;
import com.assetslookup.data.internal.APIError;
import com.assetslookup.data.internal.ErrorUtils;
import com.assetslookup.data.network.IAssetsService;
import com.assetslookup.data.network.AssetsService;
import com.assetslookup.ui.assets.AssetsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    AssetsDatabase assetsDatabase;
    IAssetsService assetsService =
            AssetsService.getInstance().create(IAssetsService.class);

    String username, password;
    EditText editPasssword, editUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        assetsDatabase = AssetsDatabase.getAppDatabase(this.getApplicationContext());

        editUsername = findViewById(R.id.editUsername);
        editPasssword = findViewById(R.id.editPassword);

        // TODO REMOVE LATER
        findViewById(R.id.imgHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUsername.setText("cesar.reboucas@gmail.com");
                editPasssword.setText("teste1234");
            }
        });

    }

    private void goToAssetsHome() {
        Intent it = new Intent(this, AssetsActivity.class);
        startActivity(it);
    }

    public void onSignIn(View view) {
        username = editUsername.getText().toString();
        password = editPasssword.getText().toString();

        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        assetsDatabase.userDao().deleteAllUsers();
        assetsService.login(user).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.code() == 200) {
                    if(response.body() != null) {
                        Token token = response.body();
                        AssetsService.setToken(token.getToken());
                        Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                        goToAssetsHome();

                    }
                } else {
                    APIError apiError = ErrorUtils.parseError(response);
                    Toast.makeText(LoginActivity.this, apiError.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onSignUp(View view) {
        Intent it = new Intent(this, SignUpActivity.class);
        startActivity(it);
    }


    public void onForgotPassword(View view) {
        Intent it = new Intent(this, ForgotPasswordActivity.class);
        startActivity(it);
    }
}
