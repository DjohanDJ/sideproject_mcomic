package com.example.m_comic.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.m_comic.R;
import com.example.m_comic.fragments.HomeFragment;
import com.example.m_comic.fragments.ProfileFragment;
import com.example.m_comic.fragments.SearchFragment;
import com.example.m_comic.fragments.UploadFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        doCreateNavigationListener();
    }

    private void doCreateNavigationListener() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationId);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerId, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.nav_search:
                    selectedFragment = new SearchFragment();
                    break;

                case R.id.nav_upload:
                    selectedFragment = new UploadFragment();
                    break;

                case R.id.nav_profile:
                    selectedFragment = ProfileFragment.getInstance();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerId, selectedFragment).commit();

            return true;
        }
    };
}