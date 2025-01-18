package com.example.swiftmart.CategoryScreen;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.swiftmart.Adapter.MobileSliderAdapter;
import com.example.swiftmart.Adapter.ProductAdapter;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.R;
import com.example.swiftmart.Utils.CustomToast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class KeyboardActivity extends AppCompatActivity {
    private ImageView backkeyboard;
    private LinearLayout keyboardHp, keyboardDell, keyboardZebronics, keyboardLogitech;
    private NestedScrollView keyboardScrollView;
    private HorizontalScrollView keyboardHorizontalScrollView;

    private MobileSliderAdapter keyboardSliderAdapter;
    private ViewPager2 keyboardViewPager;
    private Handler sliderHandler = new Handler();

    private FirebaseFirestore db;
    private DatabaseReference databaseReference;
    private List<String> imageUrls;

    private ArrayList<ProductModel> datalist = new ArrayList<>();
    private ProductAdapter adapter;
    private RecyclerView keyboardRecyclerView;
    private ProgressBar keyboardActivityProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        initialization();
        getKeyboard();
        getKeyboardsCompany();
        getImageUrls();

        backkeyboard.setOnClickListener(v -> onBackPressed());
        
    }

    private void initialization(){

        db = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("root");
        imageUrls = new ArrayList<>();

        keyboardScrollView = findViewById(R.id.keyboardScrollView);
        keyboardHorizontalScrollView = findViewById(R.id.keyboardHorizontalScrollView);

        keyboardViewPager = findViewById(R.id.keyboardViewPager);

        keyboardRecyclerView = findViewById(R.id.keyboardRecyclerView);
        keyboardActivityProgressBar = findViewById(R.id.keyboardActivityProgressBar);

        backkeyboard = findViewById(R.id.backkeyboard);

        keyboardHp = findViewById(R.id.keyboardHp);
        keyboardDell = findViewById(R.id.keyboardDell);
        keyboardZebronics = findViewById(R.id.keyboardZebronics);
        keyboardLogitech = findViewById(R.id.keyboardLogitech);

        keyboardScrollView.setVerticalScrollBarEnabled(false);
        keyboardHorizontalScrollView.setHorizontalScrollBarEnabled(false);
    }

    // Get all the laptops
    private void getKeyboard(){
        keyboardRecyclerView.setLayoutManager(new LinearLayoutManager(KeyboardActivity.this));
        keyboardActivityProgressBar.setVisibility(View.VISIBLE);
        datalist.clear();

        db.collection("Products")
                .whereEqualTo("category", "Keyboard")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(KeyboardActivity.this,  "Error in data fetching");
                            keyboardActivityProgressBar.setVisibility(View.GONE);
                            return;
                        }


                        if (value != null && !value.isEmpty()){
                            keyboardActivityProgressBar.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                datalist.add(productModel);

                                GridLayoutManager layoutManager = new GridLayoutManager(KeyboardActivity.this, 2);
                                keyboardRecyclerView.setLayoutManager(layoutManager);
                                adapter = new ProductAdapter(KeyboardActivity.this, datalist);
                                keyboardRecyclerView.setHasFixedSize(true);
                                keyboardRecyclerView.setAdapter(adapter);
                                keyboardRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                    }
                });


    }

    // Get single mobile company data
    private void getCompany(String company){
        keyboardRecyclerView.setLayoutManager(new LinearLayoutManager(KeyboardActivity.this));
        keyboardActivityProgressBar.setVisibility(View.VISIBLE);
        datalist.clear();

        db.collection("Products")
                .whereEqualTo("category", "Keyboard")
                .whereEqualTo("company", company)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(KeyboardActivity.this, "Error in data fetching");
                            keyboardActivityProgressBar.setVisibility(View.GONE);
                            return;
                        }

                        if (value != null && !value.isEmpty()){
                            keyboardActivityProgressBar.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                datalist.add(productModel);

                                GridLayoutManager layoutManager = new GridLayoutManager(KeyboardActivity.this, 2);
                                keyboardRecyclerView.setLayoutManager(layoutManager);
                                adapter = new ProductAdapter(KeyboardActivity.this, datalist);
                                keyboardRecyclerView.setHasFixedSize(true);
                                keyboardRecyclerView.setAdapter(adapter);
                                keyboardRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                    }
                });
    }

    // Get company wise mobile data
    private void getKeyboardsCompany(){
        keyboardHp.setOnClickListener(v -> getCompany("HP"));
        keyboardDell.setOnClickListener(v -> getCompany("Dell"));
        keyboardZebronics.setOnClickListener(v -> getCompany("Zebronics"));
        keyboardLogitech.setOnClickListener(v -> getCompany("Logitech"));
    }


    private void getImageUrls() {
        databaseReference.child("Keyboard").child("imgurls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    imageUrls.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String imageUrl = dataSnapshot.getValue(String.class);
                        if (imageUrl != null) {
                            imageUrls.add(imageUrl);
                        }
                    }

                    keyboardSliderAdapter = new MobileSliderAdapter(KeyboardActivity.this, imageUrls);
                    keyboardViewPager.setAdapter(keyboardSliderAdapter);

                    sliderHandler.postDelayed(slideRunnable, 3000);

                    // Add listener to reset the auto-slide when the page is changed
                    keyboardViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            sliderHandler.removeCallbacks(slideRunnable);
                            sliderHandler.postDelayed(slideRunnable, 3000);
                        }
                    });

                    // Handle touch events to reset auto-slide interval
                    keyboardViewPager.setOnTouchListener((v, event) -> {
                        sliderHandler.removeCallbacks(slideRunnable);
                        sliderHandler.postDelayed(slideRunnable, 3000);
                        return false;
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Database error: " + error.getMessage());
            }
        });
    }

    private final Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            if (keyboardViewPager != null && keyboardSliderAdapter != null) {
                int nextItem = (keyboardViewPager.getCurrentItem() + 1) % keyboardSliderAdapter.getItemCount();
                keyboardViewPager.setCurrentItem(nextItem);
                sliderHandler.postDelayed(this, 3000);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(slideRunnable, 3000);
    }
    
}