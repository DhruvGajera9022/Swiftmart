package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class tv_brandActivity extends AppCompatActivity {


    LinearLayout samsunglogo,lglogo,milogo,acerlogo,infinixlogo,motorolalogo,realmelogo;
    ImageView backetvbrand;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_brand);

        samsunglogo=findViewById(R.id.samsunglogo);
        lglogo=findViewById(R.id.lglogo);
        milogo=findViewById(R.id.milogo);
        acerlogo=findViewById(R.id.acerlogo);
        infinixlogo=findViewById(R.id.infinixlogo);
        motorolalogo=findViewById(R.id.motorolalogo);
        realmelogo=findViewById(R.id.realmelogo);
        backetvbrand=findViewById(R.id.backetvbrand);

        backetvbrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        samsunglogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(tv_brandActivity.this, TV_Activity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}