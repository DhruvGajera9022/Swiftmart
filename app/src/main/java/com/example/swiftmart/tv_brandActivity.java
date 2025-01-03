package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Arrays;
import java.util.List;

public class tv_brandActivity extends AppCompatActivity {

    LinearLayout samsunglogo, lglogo, milogo, tcllogo;
    ImageView backetvbrand;
    private ViewPager2 viewPagertv;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_brand);

        // Initialize views
        samsunglogo = findViewById(R.id.samsunglogo);
        lglogo = findViewById(R.id.lglogo);
        milogo = findViewById(R.id.milogo);
        tcllogo = findViewById(R.id.tcllogo);
        backetvbrand = findViewById(R.id.backetvbrand); // Initialize the ImageView

        // Set back button listener
        backetvbrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Initialize ViewPager2
        viewPagertv = findViewById(R.id.viewPagertv);

     
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
