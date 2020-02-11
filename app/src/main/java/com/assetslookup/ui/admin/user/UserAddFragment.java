package com.assetslookup.ui.admin.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.User;
import com.assetslookup.data.internal.FragmentManagerHelper;
import com.assetslookup.data.network.IAssetsService;
import com.assetslookup.data.network.AssetsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAddFragment extends Fragment implements View.OnClickListener {

  private IAssetsService warehouseService =
    AssetsService.getInstance().create(IAssetsService.class);

  private FragmentManagerHelper fragmentManagerHelper;

  private EditText editAnswer;
  private EditText editUsername;
  private EditText editPassword;
  private EditText editName;

  private Spinner spinnerRoles;
  private Spinner spinnerQuestions;

  private ArrayAdapter<String> rolesAdapter;
  private ArrayAdapter<String> questionsAdapter;

  private Button btnCreateUser;

  private User user;

  public UserAddFragment() { }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_admin_users_add, container, false);

    fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.usersFragmentContainer);

    btnCreateUser = view.findViewById(R.id.btnCreateUser);
    btnCreateUser.setOnClickListener(this);

    editAnswer = view.findViewById(R.id.editAnswer);
    editUsername = view.findViewById(R.id.editUsername);
    editName = view.findViewById(R.id.editName);
    editPassword = view.findViewById(R.id.editPassword);

    spinnerRoles = view.findViewById(R.id.spinnerRoles);
    rolesAdapter = new ArrayAdapter<String>(
            getContext(),
            R.layout.spinner_item,
            getResources().getStringArray(R.array.roles_entries));
    spinnerRoles.setAdapter(rolesAdapter);

    spinnerQuestions  = view.findViewById(R.id.spinnerQuestions);
    questionsAdapter = new ArrayAdapter<String>(
            getContext(),
            R.layout.spinner_item,
            getResources().getStringArray(R.array.questions_entries));
    spinnerQuestions.setAdapter(questionsAdapter);
    return view;
  }

  @Override
  public void onClick(View v) {
    onButtonCreateUserClicked(v);
  }

  private void onButtonCreateUserClicked(View v) {
    User newUser = getUser();
    warehouseService.createUser(newUser).enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        if(response.code() == 200) {
          if(response.body() != null) {
            Toast.makeText(getContext(), "User created", Toast.LENGTH_LONG).show();
          }
        } else {
          Toast.makeText(getContext(), "This email has already been used"
                  , Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        Toast.makeText(getContext(), "Something wnet wrong when trying to reach the server"
                , Toast.LENGTH_LONG).show();
      }
    });
  }

  private User getUser() {
    String name = editName.getText().toString();
    String username = editUsername.getText().toString();
    String password = editPassword.getText().toString();
    String role = spinnerRoles.getSelectedItem().toString();
    String question = spinnerQuestions.getSelectedItem().toString();
    String answer = editAnswer.getText().toString();

    return new User(name, username, password, role, "", question, answer, "", "");
  }
}
