package com.assetslookup.ui.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.User;
import com.assetslookup.data.internal.APIError;
import com.assetslookup.data.internal.ErrorUtils;
import com.assetslookup.ui.shared.BaseChildNestedFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends BaseChildNestedFragment {


    private EditText fName,lName,email, newPassword, newPassword2;
    private ProgressBar progressProfile;
    private Button btnUpdate;
    private User userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        fName = view.findViewById(R.id.editFNameProfile);
        lName = view.findViewById(R.id.editLNameProfile);
        email = view.findViewById(R.id.editEmailAddProfile);
        newPassword = view.findViewById(R.id.editPasswordProfile);
        newPassword2 = view.findViewById(R.id.editReTypePasswordProfile);
        btnUpdate = view.findViewById(R.id.btnUpdateProfile);

        progressProfile = view.findViewById(R.id.profileProgressBar);

        userLogged();


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName = fName.getText().toString();
                String lastName = lName.getText().toString();
                String emailU = email.getText().toString();
                String newPwd1 = newPassword.getText().toString();
                String newPwd2 = newPassword2.getText().toString();
                

                User user = new User();

                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setUsername(emailU);
                user.setUserId(userId.getUserId());
                user.setPassword(userId.getPassword());
                user.setNewPassword1(newPwd1);
                user.setNewPassword2(newPwd2);


                progressProfile.setVisibility(View.VISIBLE);
                assetsService.updateUser(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.code() == 200 && response.body().getStatus().isEmpty()) {

                                Toast.makeText(getContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                            newPassword.setText("");
                            newPassword2.setText("");

                        }


                        else if(!response.body().getStatus().isEmpty()){
                            Toast.makeText(getContext(), response.body().getStatus(), Toast.LENGTH_SHORT).show();
                            newPassword.setText("");
                            newPassword.requestFocus();
                            newPassword2.setText("");

                        }

                        else {
                            APIError apiError = ErrorUtils.parseError(response);
                            Toast.makeText(getContext(), apiError.message(), Toast.LENGTH_SHORT).show();
                        }
                        progressProfile.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressProfile.setVisibility(View.INVISIBLE);

                    }
                });


            }
        });
    }




    private void userLogged(){
        progressProfile.setVisibility(View.VISIBLE);
        assetsService.getUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200) {
                    if(response.body() != null) {

                        Log.d("USER RESPONDE", response.body().getUserId());

                        fName.setText(response.body().getFirstName());
                        lName.setText(response.body().getLastName());
                        email.setText(response.body().getUsername());

                        userId = new User();
                        userId.setUserId(response.body().getUserId());
                        userId.setPassword(response.body().getPassword());

                    }
                } else {
                    APIError apiError = ErrorUtils.parseError(response);
                    Toast.makeText(getContext(), apiError.message(), Toast.LENGTH_SHORT).show();
                }
                progressProfile.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressProfile.setVisibility(View.INVISIBLE);

            }
        });
    }





}
