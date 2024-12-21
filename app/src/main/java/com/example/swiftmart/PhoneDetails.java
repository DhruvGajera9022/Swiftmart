package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PhoneDetails extends AppCompatActivity {

    TextView iphonetext;
    LinearLayout warranty;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_details);

        iphonetext=findViewById(R.id.iphonetext);
        warranty=findViewById(R.id.warranty);

        iphonetext.setPaintFlags(iphonetext.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        warranty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent warranty=new Intent(PhoneDetails.this, WarrantyDetails.class);
                startActivity(warranty);
            }
        });

    }
}