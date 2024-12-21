package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.swiftmart.Adapter.MobileSliderAdapter;

import java.util.Arrays;
import java.util.List;

public class EarphoneActivity extends AppCompatActivity {

    LinearLayout boatlogo,boultlogo,realmelogo,onepluslogo,noiselogo,applelogo;
    private ViewPager2 viewPager1;
    private MobileSliderAdapter earphonesliderAdapter;
    private List<Integer> imageList1; // List of drawable images
    private Handler sliderHandler = new Handler();
    ImageView backearphone;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earphone);

        boatlogo=findViewById(R.id.boatlogo);
        boultlogo=findViewById(R.id.boultlogo);
        realmelogo=findViewById(R.id.realmelogo);
        onepluslogo=findViewById(R.id.onepluslogo);
        noiselogo=findViewById(R.id.noiselogo);
        applelogo=findViewById(R.id.applelogo);
        backearphone=findViewById(R.id.backearphone);

        boatlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EarphoneActivity.this, EarbudsActivity.class);
                startActivity(i);
            }
        });
        backearphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


        viewPager1 = findViewById(R.id.viewPager1);

        // Add drawable images to the list
        imageList1 = Arrays.asList(
                R.drawable.boat1,
                R.drawable.boult1,
                R.drawable.realme2,
                R.drawable.noise1,
                R.drawable.samsung2,
                R.drawable.mi2,
                R.drawable.oneplus2
        );

        earphonesliderAdapter = new MobileSliderAdapter(this, imageList1);
        viewPager1.setAdapter(earphonesliderAdapter);

        // Add swipe listener for manual changes
        viewPager1.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
            int currentItem = viewPager1.getCurrentItem();
            int nextItem = (currentItem + 1) % imageList1.size(); // Loop back to the first item
            viewPager1.setCurrentItem(nextItem, true); // Smooth scroll
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