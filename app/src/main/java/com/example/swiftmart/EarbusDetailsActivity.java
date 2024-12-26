package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EarbusDetailsActivity extends AppCompatActivity {

    TextView earbudstext,free;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earbus_details);

        earbudstext=findViewById(R.id.earbudstext);
        free=findViewById(R.id.free);

        earbudstext.setPaintFlags(earbudstext.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        free.setPaintFlags(free.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }



}