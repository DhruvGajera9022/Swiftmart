package com.example.swiftmart;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.swiftmart.Frgments.AccountFragment;
import com.example.swiftmart.Frgments.CartFragment;
import com.example.swiftmart.Frgments.CategoryFragment;
import com.example.swiftmart.Frgments.ExploreFragment;
import com.example.swiftmart.Frgments.HomeFragment;
import com.example.swiftmart.Utils.NetworkChangeReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NetworkChangeReceiver.NetworkChangeListener {

    String selectedLanguage;
    AlertDialog dialog;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver(this);

    private LinearLayout homeLayout, exploreLayout, categoryLayout, profileLayout;
    private ImageView homeImage, exploreImage, categoryImage, profileImage;
    private TextView homeText, exploreText, categoryText, profileText;
    private int selectedTab = 1;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_main);

        selectedLanguage = getIntent().getStringExtra("selectedLanguage");

        frameLayout = findViewById(R.id.frameLayout);

        homeLayout = findViewById(R.id.homeLayout);
        exploreLayout = findViewById(R.id.exploreLayout);
        categoryLayout = findViewById(R.id.categoryLayout);
        profileLayout = findViewById(R.id.profileLayout);

        homeImage = findViewById(R.id.homeImage);
        exploreImage = findViewById(R.id.exploreImage);
        categoryImage = findViewById(R.id.categoryImage);
        profileImage = findViewById(R.id.profileImage);

        homeText = findViewById(R.id.homeText);
        exploreText = findViewById(R.id.exploreText);
        categoryText = findViewById(R.id.categoryText);
        profileText = findViewById(R.id.profileText);

        if (selectedLanguage != null) {
            changeLanguage(selectedLanguage);
        }

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.frameLayout, HomeFragment.class, null)
                .commit();

        changeBottomTabs();

    }

    private void changeBottomTabs(){
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 1){

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    if (selectedTab > 1) {
                        transaction.setCustomAnimations(R.anim.left_to_right_enter, R.anim.left_to_right_exit);
                    } else {
                        transaction.setCustomAnimations(R.anim.right_to_left_enter, R.anim.right_to_left_exit);
                    }

                    transaction.replace(R.id.frameLayout, HomeFragment.class, null).commit();

                    exploreText.setVisibility(View.GONE);
                    categoryText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    exploreImage.setImageResource(R.drawable.icon_explore);
                    categoryImage.setImageResource(R.drawable.icon_category);
                    profileImage.setImageResource(R.drawable.icon_person);

                    exploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    categoryLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    homeText.setVisibility(View.VISIBLE);
                    homeImage.setImageResource(R.drawable.icon_home_selected);
                    homeLayout.setBackgroundResource(R.drawable.rounded_back_home_200);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1f,1f,1f, ScaleAnimation.RELATIVE_TO_SELF, 0.0f, ScaleAnimation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.startAnimation(scaleAnimation);

                    selectedTab = 1;
                }
            }
        });

        exploreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                if (selectedTab > 2) {
                    transaction.setCustomAnimations(R.anim.left_to_right_enter, R.anim.left_to_right_exit);
                } else {
                    transaction.setCustomAnimations(R.anim.right_to_left_enter, R.anim.right_to_left_exit);
                }

                transaction.replace(R.id.frameLayout, ExploreFragment.class, null).commit();

                if (selectedTab != 2){
                    homeText.setVisibility(View.GONE);
                    categoryText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.icon_home);
                    categoryImage.setImageResource(R.drawable.icon_category);
                    profileImage.setImageResource(R.drawable.icon_person);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    categoryLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    exploreText.setVisibility(View.VISIBLE);
                    exploreImage.setImageResource(R.drawable.icon_explore_selected);
                    exploreLayout.setBackgroundResource(R.drawable.rounded_back_explore_200);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1f,1f,1f, ScaleAnimation.RELATIVE_TO_SELF, 0.0f, ScaleAnimation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    exploreLayout.startAnimation(scaleAnimation);

                    selectedTab = 2;
                }
            }
        });

        categoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 3){

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    if (selectedTab > 3) {
                        transaction.setCustomAnimations(R.anim.left_to_right_enter, R.anim.left_to_right_exit);
                    } else {
                        transaction.setCustomAnimations(R.anim.right_to_left_enter, R.anim.right_to_left_exit);
                    }

                    transaction.replace(R.id.frameLayout, CategoryFragment.class, null).commit();

                    homeText.setVisibility(View.GONE);
                    exploreText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.icon_home);
                    exploreImage.setImageResource(R.drawable.icon_explore);
                    profileImage.setImageResource(R.drawable.icon_person);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    exploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    categoryText.setVisibility(View.VISIBLE);
                    categoryImage.setImageResource(R.drawable.icon_category_selected);
                    categoryLayout.setBackgroundResource(R.drawable.rounded_back_category_200);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1f,1f,1f, ScaleAnimation.RELATIVE_TO_SELF, 0.0f, ScaleAnimation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    categoryLayout.startAnimation(scaleAnimation);

                    selectedTab = 3;
                }
            }
        });

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 4){

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    if (selectedTab > 4) {
                        transaction.setCustomAnimations(R.anim.left_to_right_enter, R.anim.left_to_right_exit);
                    } else {
                        transaction.setCustomAnimations(R.anim.right_to_left_enter, R.anim.right_to_left_exit);
                    }

                    transaction.replace(R.id.frameLayout, AccountFragment.class, null).commit();

                    homeText.setVisibility(View.GONE);
                    exploreText.setVisibility(View.GONE);
                    categoryText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.icon_home);
                    exploreImage.setImageResource(R.drawable.icon_explore);
                    categoryImage.setImageResource(R.drawable.icon_category);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    exploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    categoryLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    profileText.setVisibility(View.VISIBLE);
                    profileImage.setImageResource(R.drawable.icon_person_selected);
                    profileLayout.setBackgroundResource(R.drawable.rounded_back_profile_200);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1f,1f,1f, ScaleAnimation.RELATIVE_TO_SELF, 0.0f, ScaleAnimation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profileLayout.startAnimation(scaleAnimation);

                    selectedTab = 4;
                }
            }
        });
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
