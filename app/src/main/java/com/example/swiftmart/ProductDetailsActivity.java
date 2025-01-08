package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager2.widget.ViewPager2;

import com.example.swiftmart.Adapter.ProductImageSliderAdapter;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.Utils.CustomToast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {
    private TextView productDetailsProductName, productDetailsProductDescription, productDetailsProductPrice;
    private FirebaseFirestore db;
    private String productId;
    private ViewPager2 productDetailsViewPager;
    private AppCompatButton productAddToCartButton, productBuyNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initialization();
        loadProductData(productId);

    }

    private void initialization(){
        productDetailsProductName = findViewById(R.id.productDetailsProductName);
        productDetailsProductDescription = findViewById(R.id.productDetailsProductDescription);
        productDetailsProductPrice = findViewById(R.id.productDetailsProductPrice);
        productDetailsViewPager = findViewById(R.id.productDetailsViewPager);

        productAddToCartButton = findViewById(R.id.productAddToCartButton);

        db = FirebaseFirestore.getInstance();

        productId = getIntent().getStringExtra("productId");

    }

    private void loadProductData(String productId) {
        db.collection("Products").document(productId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            CustomToast.showToast(ProductDetailsActivity.this, R.drawable.img_logo, "Error in fetching details");
                        }

                        if (value != null && value.exists()){
                            ProductModel product = value.toObject(ProductModel.class);
                            if (product != null) {
                                displayProductDetails(product);
                                setupImageSlider(product.getImgurls());
                            }
                        }
                    }
                });
    }

    private void displayProductDetails(ProductModel product) {
        productDetailsProductName.setText(product.getName());
        productDetailsProductPrice.setText("₹"+product.getPrice());
        productDetailsProductDescription.setText(product.getDescription());
    }

    private void setupImageSlider(List<String> imageUrls) {
        ProductImageSliderAdapter adapter = new ProductImageSliderAdapter(this, imageUrls);
        productDetailsViewPager.setAdapter(adapter);

//        // Automatic slide logic
//        final int delay = 3000; // Delay time in milliseconds
//        final Handler handler = new Handler(Looper.getMainLooper());
//        final Runnable runnable = new Runnable() {
//            int currentPage = 0;
//
//            @Override
//            public void run() {
//                if (currentPage >= imageUrls.size()) {
//                    currentPage = 0; // Reset to the first page
//                }
//                productDetailsViewPager.setCurrentItem(currentPage++, true);
//                handler.postDelayed(this, delay);
//            }
//        };
//
//        // Start the automatic sliding
//        handler.postDelayed(runnable, delay);
//
//        productDetailsViewPager.setOnClickListener(v -> {
//            handler.removeCallbacks(runnable);
//        });

    }

    private void handleAddToCartClick(){
        productAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void handleBuyClick(){
        productBuyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}