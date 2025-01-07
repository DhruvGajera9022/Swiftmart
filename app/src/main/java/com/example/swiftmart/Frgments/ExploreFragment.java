package com.example.swiftmart.Frgments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.swiftmart.MainActivity;
import com.example.swiftmart.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExploreFragment extends Fragment {

    int selectedTab = 1;


    public ExploreFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);



        handleOnBackPress();

        return view;
    }


    // handle onBack press
    private void handleOnBackPress(){
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                startActivity(new Intent(getContext(), MainActivity.class));

//                requireActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frameLayout, new HomeFragment())
//                        .commit();
//
//                LinearLayout homeLayout, exploreLayout, categoryLayout, profileLayout;
//                ImageView homeImage, exploreImage, categoryImage, profileImage;
//                TextView homeText, exploreText, categoryText, profileText;
//
//                homeLayout = requireActivity().findViewById(R.id.homeLayout);
//                exploreLayout = requireActivity().findViewById(R.id.exploreLayout);
//                categoryLayout = requireActivity().findViewById(R.id.categoryLayout);
//                profileLayout = requireActivity().findViewById(R.id.profileLayout);
//
//                homeImage = requireActivity().findViewById(R.id.homeImage);
//                exploreImage = requireActivity().findViewById(R.id.exploreImage);
//                categoryImage = requireActivity().findViewById(R.id.categoryImage);
//                profileImage = requireActivity().findViewById(R.id.profileImage);
//
//                homeText = requireActivity().findViewById(R.id.homeText);
//                exploreText = requireActivity().findViewById(R.id.exploreText);
//                categoryText = requireActivity().findViewById(R.id.categoryText);
//                profileText = requireActivity().findViewById(R.id.profileText);
//
//                // Update UI to reflect that Home tab is selected
//                exploreText.setVisibility(View.GONE);
//                categoryText.setVisibility(View.GONE);
//                profileText.setVisibility(View.GONE);
//
//                exploreImage.setImageResource(R.drawable.icon_explore);
//                categoryImage.setImageResource(R.drawable.icon_category);
//                profileImage.setImageResource(R.drawable.icon_person);
//
//                exploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//                categoryLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//                profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//
//                homeText.setVisibility(View.VISIBLE);
//                homeImage.setImageResource(R.drawable.icon_home_selected);
//                homeLayout.setBackgroundResource(R.drawable.rounded_back_home_200);
//
//                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1f,1f,1f, ScaleAnimation.RELATIVE_TO_SELF, 0.0f, ScaleAnimation.RELATIVE_TO_SELF, 0.0f);
//                scaleAnimation.setDuration(200);
//                scaleAnimation.setFillAfter(true);
//                homeLayout.startAnimation(scaleAnimation);
//
//                selectedTab = 1;

            }
        });
    }

}