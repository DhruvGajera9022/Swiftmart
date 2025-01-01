package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TV_Activity extends AppCompatActivity {

    ImageView backtv;
    TextView tvtext;
    LinearLayout tvdetailes;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        backtv=findViewById(R.id.backtv);
        tvtext=findViewById(R.id.tvtext);
        tvdetailes=findViewById(R.id.tvdetailes);

        tvtext.setPaintFlags(tvtext.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        backtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvdetailes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(TV_Activity.this, tv_detailsActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}