package com.assetslookup.ui.admin.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.User;
import com.assetslookup.data.internal.FragmentManagerHelper;
import com.assetslookup.data.internal.IFragmentManagerHelper;
import com.assetslookup.data.internal.What;
import com.assetslookup.data.network.IAssetsService;
import com.assetslookup.data.network.AssetsService;
import com.assetslookup.ui.admin.FragmentInteraction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragmentList extends Fragment
  implements View.OnClickListener,  SwipeRefreshLayout.OnRefreshListener, FragmentInteraction {

  IAssetsService warehouseService = AssetsService.getInstance().create(IAssetsService.class);
  List<User> users;

  private RecyclerView usersList;
  private UserListAdapter usersListAdapter;
  private FloatingActionButton floatingActionButton;
  private SwipeRefreshLayout swipeRefreshLayout;
  private ProgressBar progressBar;

  IFragmentManagerHelper fragmentManagerHelper;

  public UserFragmentList() { }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_admin_users_list, container, false);
    usersList = view.findViewById(R.id.usersList);

    fragmentManagerHelper = new FragmentManagerHelper(
      getFragmentManager(), R.id.usersFragmentContainer);

    floatingActionButton = view.findViewById(R.id.floatingActionButton);
    floatingActionButton.setOnClickListener(this);
    swipeRefreshLayout = view.findViewById(R.id.usersListRefresh);
    swipeRefreshLayout.setOnRefreshListener(this);
    progressBar = view.findViewById(R.id.progress_loader);

    users = new ArrayList<>();
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    usersList.setLayoutManager(linearLayoutManager);
    usersListAdapter = new UserListAdapter(users, this);
    usersList.setAdapter(usersListAdapter);
    usersList.setItemAnimator(new DefaultItemAnimator());

    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(usersList.getContext(),
            linearLayoutManager.getOrientation());
    usersList.addItemDecoration(dividerItemDecoration);

    getData();

    return view;
  }

  private void getData() {
    progressBar.setVisibility(View.VISIBLE);
    warehouseService.getAllUsers().enqueue(new Callback<List<User>>() {
      @Override
      public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        if(response.code() == 200) {
          if(response.body() != null) {
            users.clear();
            users.addAll(response.body());
            usersListAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.INVISIBLE);
          }
        } else {
          Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
          progressBar.setVisibility(View.INVISIBLE);
        }
      }

      @Override
      public void onFailure(Call<List<User>> call, Throwable t) {
        Toast.makeText(getContext(), "Failed to reach the server", Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
        Log.d("ERROR", t.getMessage());
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d("USERS", "RESUME");
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.floatingActionButton:
        onFloatingActionButtonClicked(v);
        break;
    }
  }

  private void onFloatingActionButtonClicked(View v) {
    fragmentManagerHelper.attach(UserAddFragment.class);
  }

  @Override
  public void onRefresh() {
    getData();
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if(!hidden) {
      onRefresh();
    }
  }

  @Override
  public void sendMessage(Message message) {
    Bundle bundle;
    final User user;
    final String username;
    progressBar.setVisibility(View.VISIBLE);
    switch (message.what) {
      case What.REMOVE:
        bundle = (Bundle) message.obj;
        username = bundle.getString("USERNAME");
        user = new User();
        user.setUsername(username);
        warehouseService.deleteUser(user).enqueue(new Callback<User>() {
          @Override
          public void onResponse(Call<User> call, Response<User> response) {
            if(response.code() == 200) {
              deleteItem(user);
            } else {
              progressBar.setVisibility(View.INVISIBLE);
              Toast.makeText(getContext(), "Failed to remove " + username,
                Toast.LENGTH_LONG).show();
            }

          }

          @Override
          public void onFailure(Call<User> call, Throwable t) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), "Failed to remove " + username,
                    Toast.LENGTH_LONG).show();
          }
        });
        break;
      case What.ROLE_UPDATE:
        bundle = (Bundle) message.obj;
        user = new User();
        username = bundle.getString("USERNAME");
        String role = bundle.getString("ROLE");

        user.setRole(role);
        user.setUsername(username);
        warehouseService.editUserRole(user).enqueue(new Callback<Void>() {
          @Override
          public void onResponse(Call<Void> call, Response<Void> response) {
            progressBar.setVisibility(View.INVISIBLE);
            usersListAdapter.refreshUserRole(user.getRole(), user.getUsername());
          }

          @Override
          public void onFailure(Call<Void> call, Throwable t) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), "Failed to edit " + username + " role",
                    Toast.LENGTH_LONG).show();
          }
        });
        break;
      case What.WAREHOUSE_UPDATE:
        bundle = (Bundle) message.obj;
        user = new User();
        username = bundle.getString("USERNAME");
        String warehouse = bundle.getString("WAREHOUSE");

        user.setFavouriteWarehouse(warehouse);
        user.setUsername(username);
        warehouseService.editUserFavouriteWarehouse(user).enqueue(new Callback<Void>() {
          @Override
          public void onResponse(Call<Void> call, Response<Void> response) {
            progressBar.setVisibility(View.INVISIBLE);
            usersListAdapter.refreshUserFavouriteWarehouse(user.getFavouriteWarehouse(), user.getUsername());
          }

          @Override
          public void onFailure(Call<Void> call, Throwable t) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), "Failed to edit " + username + " favourite warehouse",
                    Toast.LENGTH_LONG).show();
          }
        });
        break;
    }
  }

  private void deleteItem(final User user) {
    final int position = usersListAdapter.getUserPostion(user.getUsername());
    View rowView = null;
    for (int i = 0; i < usersList.getChildCount(); i++) {
      View v = usersList.getChildAt(i);
      TextView username = v.findViewById(R.id.txtUsername);
      if(username.getText().toString().equals(user.getUsername())) {
        rowView = v;
        break;
      }
    }

    if(rowView == null) {
      Toast.makeText(getContext(), user.getUsername() + "removed successfully!",
        Toast.LENGTH_LONG).show();
      return;
    }

    Animation anim = AnimationUtils.loadAnimation(requireContext(),
      android.R.anim.slide_out_right);
    anim.setDuration(300);
    rowView.startAnimation(anim);
    new Handler().postDelayed(new Runnable() {
      public void run() {
        usersListAdapter.removeUser(position);
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getContext(), user.getUsername() + "removed successfully!",
          Toast.LENGTH_LONG).show();
      }

    }, anim.getDuration());
  }
}
