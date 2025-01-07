package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Edit_profile_Activity extends AppCompatActivity {

    ImageView cart, backediteprofile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        cart = findViewById(R.id.cart);
        backediteprofile = findViewById(R.id.backediteprofile);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCartFragment();
            }
        });

        backediteprofile.setOnClickListener(new View.OnClickListener() {
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

    private void navigateToCartFragment() {
        // Navigate to MainActivity and pass data to show CartFragment
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("showFragment", "cart");
        startActivity(intent);
        finish(); // Close the current activity if necessary
    }
}
