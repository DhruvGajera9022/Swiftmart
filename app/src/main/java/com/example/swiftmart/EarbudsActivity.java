package com.example.swiftmart;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EarbudsActivity extends AppCompatActivity {
    TextView earbudstext,earbudstext1;
    LinearLayout earbudsdetailes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earbuds);

        earbudstext=findViewById(R.id.earbudstext);
        earbudstext1=findViewById(R.id.earbudstext1);
        earbudsdetailes=findViewById(R.id.earbudsdetailes);


        earbudstext.setPaintFlags(earbudstext.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        earbudstext1.setPaintFlags(earbudstext1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);




    }
}