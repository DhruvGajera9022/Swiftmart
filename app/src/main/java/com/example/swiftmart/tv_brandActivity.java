package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.swiftmart.Adapter.MobileSliderAdapter;
import com.example.swiftmart.Adapter.TvSliderAdapter;

import java.util.Arrays;
import java.util.List;

public class tv_brandActivity extends AppCompatActivity {


    LinearLayout samsunglogo,lglogo,milogo,tcllogo;
    ImageView backetvbrand;
    private ViewPager2 viewPagertv;
    private TvSliderAdapter tvSliderAdapter;
    private List<Integer> imageList; // List of drawable images
    private Handler sliderHandler = new Handler();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_brand);

        samsunglogo=findViewById(R.id.samsunglogo);
        lglogo=findViewById(R.id.lglogo);
        milogo=findViewById(R.id.milogo);
        tcllogo=findViewById(R.id.tcllogo);

        backetvbrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        viewPagertv = findViewById(R.id.viewPagertv);

        // Add drawable images to the list
        imageList = Arrays.asList(
                R.drawable.tv1,
                R.drawable.tv2,
                R.drawable.tv3,
                R.drawable.tv4,
                R.drawable.tv5
        );

        tvSliderAdapter = new TvSliderAdapter(this, imageList);
        viewPagertv.setAdapter(tvSliderAdapter);

        // Add swipe listener for manual changes
        viewPagertv.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000); // Restart the auto-slider after swipe
            }
        });
        // Start auto-slide
        sliderHandler.postDelayed(sliderRunnable, 3000);

    }
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = viewPagertv.getCurrentItem();
            int nextItem = (currentItem + 1) % imageList.size(); // Loop back to the first item
            viewPagertv.setCurrentItem(nextItem, true); // Smooth scroll
            sliderHandler.postDelayed(this, 3000); // Slide every 3 seconds
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable); // Stop slider when activity is paused
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000); // Resume slider when activity is resumed
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}