package com.example.swiftmart;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.swiftmart.Account.Add_Address_Activity;
import com.example.swiftmart.Adapter.ProductImageSliderAdapter;
import com.example.swiftmart.Frgments.CartFragment;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.Utils.CustomToast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {
    private TextView productDetailsProductName, productDetailsProductDescription, productDetailsProductPrice;
    private String productId;
    private ViewPager2 productDetailsViewPager;
    private AppCompatButton productAddToCartButton, productBuyNowButton;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String uid;
    private List<String> currentImageUrls;

    private Handler sliderHandler = new Handler(Looper.getMainLooper());
    private Runnable sliderRunnable;

    private RelativeLayout productDetailsRelativeLayout;

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sliderRunnable != null) {
            sliderHandler.postDelayed(sliderRunnable, 3000);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initialization();
        loadProductData(productId);
        handleAddToCartClick();

    }

    private void initialization(){

        productDetailsRelativeLayout = findViewById(R.id.productDetailsRelativeLayout);

        productDetailsProductName = findViewById(R.id.productDetailsProductName);
        productDetailsProductDescription = findViewById(R.id.productDetailsProductDescription);
        productDetailsProductPrice = findViewById(R.id.productDetailsProductPrice);
        productDetailsViewPager = findViewById(R.id.productDetailsViewPager);

        productAddToCartButton = findViewById(R.id.productAddToCartButton);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        productId = getIntent().getStringExtra("productId");

    }

    private void loadProductData(String productId) {
        db.collection("Products").document(productId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            CustomToast.showToast(ProductDetailsActivity.this,  "Error in fetching details");
                        }

                        if (value != null && value.exists()){
                            ProductModel product = value.toObject(ProductModel.class);
                            if (product != null) {
                                displayProductDetails(product);
                                setupImageSlider(product.getImgurls());
                                currentImageUrls = product.getImgurls();
                            }
                        }
                    }
                });
    }

    private void displayProductDetails(ProductModel product) {
        productDetailsProductName.setText(product.getName());
        productDetailsProductPrice.setText(product.getPrice());
        productDetailsProductDescription.setText(product.getDescription());
    }

    private void setupImageSlider(List<String> imageUrls) {
        ProductImageSliderAdapter adapter = new ProductImageSliderAdapter(this, imageUrls);
        productDetailsViewPager.setAdapter(adapter);

        // Initialize sliderRunnable using the global sliderHandler
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = productDetailsViewPager.getCurrentItem();
                int nextItem = (currentItem + 1) % imageUrls.size(); // Loop back to the first item
                productDetailsViewPager.setCurrentItem(nextItem, true); // Smooth scroll
                sliderHandler.postDelayed(this, 3000); // Slide every 3 seconds
            }
        };

        // Start the slider
        sliderHandler.postDelayed(sliderRunnable, 3000);

        // Stop and restart slider on user interaction
        productDetailsViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                sliderHandler.removeCallbacks(sliderRunnable); // Stop the slider
                sliderHandler.postDelayed(sliderRunnable, 3000); // Restart after delay
            }
        });

        // Optionally stop slider completely when touched
        productDetailsViewPager.setOnTouchListener((v, event) -> {
            sliderHandler.removeCallbacks(sliderRunnable);
            sliderHandler.postDelayed(sliderRunnable, 3000); // Restart after touch
            return false; // Pass the touch event to ViewPager2
        });
    }

    private void handleAddToCartClick(){
        productAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
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

    // handle add to cart
    private void addToCart() {
        String productName = productDetailsProductName.getText().toString();
        String productPrice = productDetailsProductPrice.getText().toString();
        String productDescription = productDetailsProductDescription.getText().toString();

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentDate = currentDate.format(calForDate.getTime());
        String saveCurrentTime = currentTime.format(calForDate.getTime());

        Map<String, Object> cartMap = new HashMap<>();
        cartMap.put("imgurls", currentImageUrls);
        cartMap.put("name", productName);
        cartMap.put("price", productPrice);
        cartMap.put("description", productDescription);
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("pid", productId);
        cartMap.put("qty", 1);

        db.collection("Users")
                .document(uid)
                .collection("Cart")
                .whereEqualTo("pid", productId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        CustomToast.showToast(ProductDetailsActivity.this, "Item is already in your cart");
                    } else {
                        db.collection("Users")
                                .document(uid)
                                .collection("Cart")
                                .add(cartMap)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        String oid = documentReference.getId();
                                        documentReference.update("oid", oid)
                                                .addOnSuccessListener(aVoid -> {
                                                    CustomToast.showToast(ProductDetailsActivity.this, "Added to Cart");
                                                })
                                                .addOnFailureListener(e -> {
                                                    CustomToast.showToast(ProductDetailsActivity.this, "Failed to add to cart");
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        CustomToast.showToast(ProductDetailsActivity.this, "Error adding to cart");
                                    }
                                });
                    }
                });
    }


//    private void snackBar(String msg) {
//        Snackbar productDetailsActivitySnackBar = Snackbar.make(
//                        productDetailsRelativeLayout, msg, Snackbar.LENGTH_INDEFINITE
//                )
//                .setAction("Go to Cart", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        CartFragment cartFragment = new CartFragment();
//                        transaction.replace(R.id.frameLayout, cartFragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
//                    }
//                })
//                .setActionTextColor(Color.YELLOW);
//
//        productDetailsActivitySnackBar.show();
//    }

}