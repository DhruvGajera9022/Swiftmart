package com.example.swiftmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Leptop_Activity extends AppCompatActivity {
    ImageView backleptop;
    LinearLayout hplogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leptop);

        backleptop = findViewById(R.id.backleptop);
        hplogo = findViewById(R.id.hplogo);

        backleptop.setOnClickListener(v -> onBackPressed());


    }
}
