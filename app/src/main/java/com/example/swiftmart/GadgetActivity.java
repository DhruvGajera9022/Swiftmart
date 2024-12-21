package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class GadgetActivity extends AppCompatActivity {

    ImageView backgadgets;
    LinearLayout earbuds,watch,charging,speakers,powerbank;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gadget);

        backgadgets=findViewById(R.id.backgadgets);
        earbuds=findViewById(R.id.earbuds);
        watch=findViewById(R.id.watch);
        charging=findViewById(R.id.charging);
        speakers=findViewById(R.id.speakers);
        powerbank=findViewById(R.id.powerbank);

        earbuds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GadgetActivity.this, EarphoneActivity.class);
                startActivity(i);
            }
        });

        backgadgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}