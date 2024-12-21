package com.example.swiftmart;

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

public class MobilesActivity extends AppCompatActivity {

    LinearLayout iphone,vivo,oppo,mi,realme,samsung,motorola,poco,goggle,oneplues;
    private ViewPager2 viewPager;
    private MobileSliderAdapter mobilesliderAdapter;
    private List<Integer> imageList; // List of drawable images
    private Handler sliderHandler = new Handler();
    ImageView backmobiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobiles);

        iphone=findViewById(R.id.iphone);
        vivo=findViewById(R.id.vivo);
        oppo=findViewById(R.id.oppo);
        mi=findViewById(R.id.mi);
        realme=findViewById(R.id.realme);
        samsung=findViewById(R.id.samsung);
        motorola=findViewById(R.id.motorola);
        poco=findViewById(R.id.poco);
        goggle=findViewById(R.id.goggle);
        oneplues=findViewById(R.id.oneplues);
        backmobiles=findViewById(R.id.backmobiles);


        iphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MobilesActivity.this, PhoneActivity.class);
                startActivity(i);

            }
        });


        backmobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


        viewPager = findViewById(R.id.viewPager);

        // Add drawable images to the list
        imageList = Arrays.asList(
                R.drawable.i1,
                R.drawable.vivo1,
                R.drawable.motorola1,
                R.drawable.realme1,
                R.drawable.samsung1,
                R.drawable.oppo1,
                R.drawable.mi1,
                R.drawable.oneplus1,
                R.drawable.poco1
        );

        mobilesliderAdapter = new MobileSliderAdapter(this, imageList);
        viewPager.setAdapter(mobilesliderAdapter);

        // Add swipe listener for manual changes
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
            int currentItem = viewPager.getCurrentItem();
            int nextItem = (currentItem + 1) % imageList.size(); // Loop back to the first item
            viewPager.setCurrentItem(nextItem, true); // Smooth scroll
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