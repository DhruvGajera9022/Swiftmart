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

public class Address_Activity extends AppCompatActivity {

    ImageView backaddress,cart2;
    LinearLayout addnewadr;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        backaddress=findViewById(R.id.backaddress);
        cart2=findViewById(R.id.cart2);
        addnewadr=findViewById(R.id.addnewadr);

        backaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCartFragment();
            }
        });

        addnewadr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Address_Activity.this, Add_Address_Activity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void navigateToCartFragment() {
        // Navigate to MainActivity and pass data to show CartFragment
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("showFragment", "cart");
        startActivity(intent);
        finish(); // Close the current activity if necessary
    }
}