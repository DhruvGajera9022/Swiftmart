package com.example.swiftmart;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.swiftmart.Frgments.AccountFragment;
import com.example.swiftmart.Frgments.CartFragment;
import com.example.swiftmart.Frgments.CategoryFragment;
import com.example.swiftmart.Frgments.ExploreFragment;
import com.example.swiftmart.Frgments.HomeFragment;
import com.example.swiftmart.Utils.NetworkChangeReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NetworkChangeReceiver.NetworkChangeListener {

    BottomNavigationView bottomNavigationView;
    String selectedLanguage;
    AlertDialog dialog;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        selectedLanguage = getIntent().getStringExtra("selectedLanguage");

        if (selectedLanguage != null) {
            changeLanguage(selectedLanguage);
        }

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

    private void changeLanguage(String newLanguage) {
        SharedPreferences sharedPreferences = getSharedPreferences("LANGUAGE_SETTINGS", MODE_PRIVATE);
        int item = sharedPreferences.getInt("item", 0);

        if (selectedLanguage != null) {
            switch (selectedLanguage) {
                case "Afrikaans":
                    selectLanguage("af", item);
                    break;
                case "Arabic":
                    selectLanguage("ar", item);
                    break;
                case "English":
                    selectLanguage("en", item);
                    break;
                case "French":
                    selectLanguage("fr", item);
                    break;
                case "Hindi":
                    selectLanguage("hi", item);
                    break;
                case "German":
                    selectLanguage("de", item);
                    break;
                case "Chinese":
                    selectLanguage("zh-rTW", item);
                    break;
                case "Russians":
                    selectLanguage("ru", item);
                    break;
                case "Spanish":
                    selectLanguage("sp", item);
                    break;
                case "Japanese":
                    selectLanguage("ja", item);
                    break;
                case "Indonesian":
                    selectLanguage("in-rID", item);
                    break;
                case "Italian":
                    selectLanguage("it", item);
                    break;
                case "Portuguese":
                    selectLanguage("pt", item);
                    break;
                case "Vietnamese":
                    selectLanguage("vi", item);
                    break;
                default:
                    Toast.makeText(this, "Unsupported language", Toast.LENGTH_SHORT).show();
                    return;
            }
        }
    }

    private void selectLanguage(String language, int item) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("LANGUAGE_SETTINGS", MODE_PRIVATE).edit();
        editor.putString("language", language);
        editor.putInt("item", item);
        editor.apply();
    }

    private void loadLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("LANGUAGE_SETTINGS", MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "en");
        selectLanguage(language, sharedPreferences.getInt("item", 0));
    }


    private void ShowDialog() {

        dialog = new AlertDialog.Builder(MainActivity.this)
                .setView(R.layout.no_internet_dialog)
                .setCancelable(false)
                .create();
        dialog.show();


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


//        TextView playButton = dialog.findViewById(R.id.playButton);
//
//        playButton.setOnClickListener(view->{
//
//            Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show();
//        });


    }

    @Override
    public void onNetworkChanged(boolean isConnected) {
        if (isConnected) {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }

        } else {

            if (dialog == null || !dialog.isShowing()) {

                ShowDialog();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeReceiver);
    }

}
