package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PhoneActivity extends AppCompatActivity {

    TextView iphonetext;
    LinearLayout iphonedetailes;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        iphonetext=findViewById(R.id.iphonetext);
        iphonedetailes=findViewById(R.id.iphonedetailes);

        iphonetext.setPaintFlags(iphonetext.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        iphonedetailes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iphonedetailes=new Intent(PhoneActivity.this, PhoneDetails.class);
                startActivity(iphonedetailes);
            }
        });

    }
}