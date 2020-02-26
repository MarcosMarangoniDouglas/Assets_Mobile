package com.assetslookup.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.assetslookup.R;
import com.assetslookup.data.internal.BottomNavigatorManager;
import com.assetslookup.ui.assets.AssetsContainerFragment;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    BottomNavigatorManager bottomNavigatorManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigatorManager = new BottomNavigatorManager(getSupportFragmentManager(), R.id.fragmentContainer);
        // FIRST SCREEN
        bottomNavigatorManager.attach(AssetsContainerFragment.class);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.ASSETS:
                bottomNavigatorManager.attach(AssetsContainerFragment.class);
                break;
            case R.id.GOALS:
                break;
            case R.id.PROFILE:
                break;
        }
        return false;
    }
}
