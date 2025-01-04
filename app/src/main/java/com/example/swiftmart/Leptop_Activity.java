package com.example.swiftmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

public class Leptop_Activity extends AppCompatActivity {
    ImageView backleptop;
    LinearLayout hplogo;
    ScrollView laptopScrollView;
    HorizontalScrollView laptopHorizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leptop);

        laptopScrollView=findViewById(R.id.laptopScrollView);
        laptopHorizontalScrollView=findViewById(R.id.laptopHorizontalScrollView);

        backleptop = findViewById(R.id.backleptop);
        hplogo = findViewById(R.id.hplogo);

        laptopScrollView.setVerticalScrollBarEnabled(false);
        laptopHorizontalScrollView.setHorizontalScrollBarEnabled(false);

        backleptop.setOnClickListener(v -> onBackPressed());


    }
}
