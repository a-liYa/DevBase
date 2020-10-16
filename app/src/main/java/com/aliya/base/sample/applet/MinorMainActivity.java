package com.aliya.base.sample.applet;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.BaseActivity;
import com.aliya.base.sample.databinding.ActivityMinorMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

/**
 * 次要的 MainActivity
 *
 * @author a_liYa
 * @date 2020/10/8 21:01.
 */
public class MinorMainActivity extends BaseActivity {

    ActivityMinorMainBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityMinorMainBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment =
                (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host);
        NavigationUI.setupWithNavController(mViewBinding.navigation,
                navHostFragment.getNavController());

//        mViewBinding.navigation.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()) {
//
//                        }
//                        Log.e("TAG", "onNavigationItemSelected: " + item);
//                        return true; // true:表示当前Menu应该选中，否则Menu不能选中
//                    }
//                });
    }
}