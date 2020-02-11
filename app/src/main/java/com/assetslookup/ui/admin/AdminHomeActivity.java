package com.assetslookup.ui.admin;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.assetslookup.R;
import com.assetslookup.data.db.AssetsDatabase;
import com.assetslookup.data.internal.BottomNavigatorManager;
import com.assetslookup.data.internal.IFragmentManagerHelper;
import com.assetslookup.ui.admin.product.ProductsFragment;
import com.assetslookup.ui.admin.summary.SummariesFragment;
import com.assetslookup.ui.admin.user.UserFragment;
import com.assetslookup.ui.admin.warehouse.WarehousesFragment;

import java.util.List;

public class AdminHomeActivity extends AppCompatActivity
  implements FragmentInteraction, BottomNavigationView.OnNavigationItemSelectedListener{

  AssetsDatabase assetsDatabase;
  IFragmentManagerHelper fragmentManagerHelper;

  BottomNavigationView bottomNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_home);

    // Dependencies
    assetsDatabase = AssetsDatabase.getAppDatabase(getApplicationContext());
    fragmentManagerHelper =
            new BottomNavigatorManager(getSupportFragmentManager(), R.id.fragmentContainer);
    fragmentManagerHelper.attach(UserFragment.class);

    bottomNavigationView = findViewById(R.id.bottomNavigationView);
    bottomNavigationView.setOnNavigationItemSelectedListener(this);
  }

  @Override
  public void sendMessage(Message message) {
      switch (message.what) {
        case 2:
          Log.d("TESTE", "MESSAGE ACTIVITY");
          Bundle bundle = new Bundle();
          bundle.putSerializable("product", (Product) message.obj);
          bundle.putString("summary", "showmovs");
          fragmentManagerHelper.attach(ProductsFragment.class, bundle);
          List<Fragment> fragments = getSupportFragmentManager().getFragments();
          bottomNavigationView.setOnNavigationItemSelectedListener(null);
          bottomNavigationView.setSelectedItemId(R.id.productMenu);
          bottomNavigationView.setOnNavigationItemSelectedListener(this);
          String oi = "";
          //Fragment fragment  = getSupportFragmentManager().findFragmentByTag(ProductsFragment.class.getName());
          break;
      }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    switch (menuItem.getItemId()) {
      case R.id.ordersMenu:
        fragmentManagerHelper.attach(SummariesFragment.class);
        break;
      case R.id.userMenu:
        fragmentManagerHelper.attach(UserFragment.class);
        break;
      case R.id.productMenu:
        fragmentManagerHelper.attach(ProductsFragment.class);
        break;
      case R.id.warehouseMenu:
        fragmentManagerHelper.attach(WarehousesFragment.class);
        break;
    }
    return true;
  }

}
