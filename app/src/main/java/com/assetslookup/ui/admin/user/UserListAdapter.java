package com.assetslookup.ui.admin.user;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.User;
import com.assetslookup.data.internal.What;
import com.assetslookup.data.network.IAssetsService;
import com.assetslookup.data.network.AssetsService;
import com.assetslookup.ui.admin.FragmentInteraction;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder>{

  private List<User> users;
  private Fragment fragment;
  IAssetsService warehouseService;

  public UserListAdapter(List<User> users, Fragment fragment) {
    this.users = users;
    this.fragment = fragment;
    warehouseService = AssetsService.getInstance().create(IAssetsService.class);
  }

  public void refreshUserRole(String role, String username) {
    for (int i = 0; i < users.size(); i++) {
      if(users.get(i).getUsername().equals(username)) {
        users.get(i).setRole(role);
        notifyItemChanged(i);
        break;
      }
    }
  }

  public void refreshUserFavouriteWarehouse(String warehouse, String username) {
    for (int i = 0; i < users.size(); i++) {
      if(users.get(i).getUsername().equals(username)) {
        users.get(i).setFavouriteWarehouse(warehouse);
        notifyItemChanged(i);
        break;
      }
    }
  }

  public void removeUser(int position) {
    users.remove(position);
    notifyItemRemoved(position);
  }

  public int getUserPostion(String username) {
    for (int i = 0; i < users.size(); i++) {
      if(users.get(i).getUsername().equals(username)) {
        return i;
      }
    }
    return -1;
  }

  @NonNull
  @Override
  public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater
      .from(viewGroup.getContext())
      .inflate(R.layout.fragment_admin_users_list_row, viewGroup, false);
    UserViewHolder userViewHolder = new UserViewHolder(view);
    return userViewHolder;
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public void onBindViewHolder(@NonNull final UserViewHolder userViewHolder, int i) {
    final String username = users.get(i).getUsername();
    String name = users.get(i).getName();
    String favouriteWarehouse = users.get(i).getFavouriteWarehouse();

    userViewHolder.username.setText(username);
    userViewHolder.name.setText(name);

    String role = users.get(i).getRole();
    userViewHolder.role.setText(role);
    if(role.toLowerCase().equals("admin")) {
      userViewHolder.profileImage
              .setBorderColor(fragment.getResources().getColor(R.color.colorPrimaryDark));
      userViewHolder.profileImage.setImageResource(R.drawable.ic_domain_black_24dp);
    } else if(role.toLowerCase().equals("associate")) {
      userViewHolder.profileImage
              .setBorderColor(fragment.getResources().getColor(R.color.colorAccentDark));
      userViewHolder.profileImage.setImageResource(R.drawable.ic_local_shipping_black_24dp);
      userViewHolder.cardEditFavouriteWh.setVisibility(View.VISIBLE);
      userViewHolder.favouriteWarehouse.setVisibility(View.VISIBLE);
      userViewHolder.favouriteWarehouse.setText(favouriteWarehouse);

    } else {
      userViewHolder.profileImage
              .setBorderColor(fragment.getResources().getColor(R.color.darkGray));
      userViewHolder.profileImage.setImageResource(R.drawable.ic_person_black_24dp);
    }

    userViewHolder.btnEditFavouriteWh.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(userViewHolder.cardEditFavouriteWh.getVisibility() == View.INVISIBLE) return;
        warehouseService.getAllWarehouse().enqueue(new Callback<List<Warehouse>>() {
          @Override
          public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
            if(response.isSuccessful()) {
              final List<String> warehouses = new ArrayList<>();
              for (Warehouse warehouse : response.body()) {
                warehouses.add(warehouse.getName());
              }
              final String[] warehousesArray = new String[warehouses.size()];
              warehouses.toArray(warehousesArray);
              AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
              builder.setTitle("Pick a warehouse");
              builder.setItems(warehousesArray, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  User user = users.get(userViewHolder.getAdapterPosition());
                  Message m = new Message();
                  m.what = What.WAREHOUSE_UPDATE;
                  Bundle bundle = new Bundle();
                  bundle.putString("USERNAME", user.getUsername());
                  bundle.putString("WAREHOUSE", warehousesArray[which]);
                  m.obj = bundle;
                  ((FragmentInteraction)fragment).sendMessage(m);
                }
              });
              builder.show();
            } else {
              Toast.makeText(fragment.getContext(), "There was a problem trying to get the warehouses", Toast.LENGTH_SHORT).show();
            }
          }

          @Override
          public void onFailure(Call<List<Warehouse>> call, Throwable t) {

          }
        });
      }
    });

    userViewHolder.btnRemoveUser.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final User user = users.get(userViewHolder.getAdapterPosition());

        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setTitle("Are you sure you want to delete " + user.getUsername() + "?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

          }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Message m = new Message();
            m.what = What.ROLE_UPDATE;
            Bundle bundle = new Bundle();
            bundle.putString("USERNAME", user.getUsername());
            m.obj = bundle;
            ((FragmentInteraction)fragment).sendMessage(m);
          }
        });
        builder.show();
      }
    });

    userViewHolder.btnMoreOptions.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final String[] roles = fragment.getResources().getStringArray(R.array.roles_entries);

        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setTitle("Pick a role");
        builder.setItems(roles, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            User user = users.get(userViewHolder.getAdapterPosition());
            Message m = new Message();
            m.what = What.ROLE_UPDATE;
            Bundle bundle = new Bundle();
            bundle.putString("USERNAME", user.getUsername());
            bundle.putString("ROLE", roles[which]);
            m.obj = bundle;
            ((FragmentInteraction)fragment).sendMessage(m);
          }
        });
        builder.show();
      }
    });
  }

  @Override
  public int getItemCount() {
    return users.size();
  }

  public class UserViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImage;
    TextView username, name, role, favouriteWarehouse;
    ImageButton btnMoreOptions, btnRemoveUser, btnEditFavouriteWh;
    CardView cardEditFavouriteWh;

    public UserViewHolder(@NonNull View itemView) {
      super(itemView);
      profileImage = itemView.findViewById(R.id.profile_image);
      username = itemView.findViewById(R.id.txtUsername);
      name = itemView.findViewById(R.id.txtName);
      btnMoreOptions = itemView.findViewById(R.id.btnMoreOptions);
      btnRemoveUser = itemView.findViewById(R.id.btnRemoveUser);
      role = itemView.findViewById(R.id.txtRole);
      favouriteWarehouse = itemView.findViewById(R.id.txtFavoriteWh);
      btnEditFavouriteWh = itemView.findViewById(R.id.btnEditFavoriteWh);
      cardEditFavouriteWh = itemView.findViewById(R.id.cardEditFavouriteWh);
    }
  }
}
