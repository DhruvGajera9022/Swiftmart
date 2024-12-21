package com.example.swiftmart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.swiftmart.Frgments.AccountFragment;
import com.example.swiftmart.Frgments.CartFragment;
import com.example.swiftmart.Frgments.CategoryFragment;
import com.example.swiftmart.Frgments.ExploreFragment;
import com.example.swiftmart.Frgments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new HomeFragment())
                    .commit();
            bottomNavigationView.setSelectedItemId(R.id.home);
        }


        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;


            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.explore) {
                selectedFragment = new ExploreFragment();
            } else if (itemId == R.id.category) {
                selectedFragment = new CategoryFragment();
            } else if (itemId == R.id.account) {
                selectedFragment = new AccountFragment();
            } else if (itemId == R.id.cart) {
                selectedFragment = new CartFragment();
            }

            // Replace the fragment
            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
            }

            return true;
        });
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}
