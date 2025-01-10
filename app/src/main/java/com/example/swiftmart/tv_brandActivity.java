package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.swiftmart.Adapter.MobileSliderAdapter;
import com.example.swiftmart.Adapter.ProductAdapter;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.Utils.CustomToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class tv_brandActivity extends AppCompatActivity {

    LinearLayout samsunglogo, lglogo, milogo, tcllogo;
    ImageView backetvbrand;
    private ViewPager2 tvViewPager;
    private MobileSliderAdapter tvSliderAdapter;
    private Handler sliderHandler = new Handler();
    private RecyclerView tvRecyclerView;
    ArrayList<ProductModel> datalist = new ArrayList<>();
    private FirebaseFirestore db;
    ProductAdapter adapter;
    ScrollView tvActivityScrollView;
    ProgressBar tvActivityProgressBar;

    private DatabaseReference databaseReference;
    private List<String> imageUrls;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_brand);

        db = FirebaseFirestore.getInstance();

        tvActivityScrollView = findViewById(R.id.tvActivityScrollView);
        databaseReference = FirebaseDatabase.getInstance().getReference("root");
        imageUrls = new ArrayList<>();

        // Initialize views
        samsunglogo = findViewById(R.id.samsunglogo);
        lglogo = findViewById(R.id.lglogo);
        milogo = findViewById(R.id.milogo);
        tcllogo = findViewById(R.id.tcllogo);
        backetvbrand = findViewById(R.id.backetvbrand);
        tvRecyclerView=findViewById(R.id.tvRecyclerView);
        tvActivityProgressBar=findViewById(R.id.tvActivityProgressBar);

        tvActivityScrollView.setVerticalScrollBarEnabled(false);

        getTVs();
        getTVsCompany();

        getImageUrls();
        tvViewPager = findViewById(R.id.tvViewPager);

     
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Get all the TVs
    private void getTVs(){
        tvRecyclerView.setLayoutManager(new LinearLayoutManager(tv_brandActivity.this));
        tvActivityProgressBar.setVisibility(View.VISIBLE);
        datalist.clear();

        db.collection("Products")
                .whereEqualTo("category", "TV")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(tv_brandActivity.this,  "Error in data fetching");
                            tvActivityProgressBar.setVisibility(View.GONE);
                            return;
                        }


                        if (value != null && !value.isEmpty()){
                            tvActivityProgressBar.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                datalist.add(productModel);

                                GridLayoutManager layoutManager = new GridLayoutManager(tv_brandActivity.this, 2);
                                tvRecyclerView.setLayoutManager(layoutManager);
                                adapter = new ProductAdapter(tv_brandActivity.this, datalist);
                                tvRecyclerView.setHasFixedSize(true);
                                tvRecyclerView.setAdapter(adapter);
                                tvRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                    }
                });


    }

    // Get single TVs company data
    private void getCompany(String company){
        tvRecyclerView.setLayoutManager(new LinearLayoutManager(tv_brandActivity.this));
        tvActivityProgressBar.setVisibility(View.VISIBLE);
        datalist.clear();

        db.collection("Products")
                .whereEqualTo("category", "TV")
                .whereEqualTo("company", company)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(tv_brandActivity.this, "Error in data fetching");
                            tvActivityProgressBar.setVisibility(View.GONE);
                            return;
                        }


                        if (value != null && !value.isEmpty()){
                            tvActivityProgressBar.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                datalist.add(productModel);

                                GridLayoutManager layoutManager = new GridLayoutManager(tv_brandActivity.this, 2);
                                tvRecyclerView.setLayoutManager(layoutManager);
                                adapter = new ProductAdapter(tv_brandActivity.this, datalist);
                                tvRecyclerView.setHasFixedSize(true);
                                tvRecyclerView.setAdapter(adapter);
                                tvRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                    }
                });


    }

    // Get company wise TV data
    private void getTVsCompany(){
        samsunglogo.setOnClickListener(v -> getCompany("Samsung"));
        lglogo.setOnClickListener(v -> getCompany("LG"));
        milogo.setOnClickListener(v -> getCompany("Xiaomi"));
        tcllogo.setOnClickListener(v -> getCompany("TCL"));
    }

    private void getImageUrls() {
        databaseReference.child("TV").child("imgurls").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    imageUrls.clear();
                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                        String imageUrl = dataSnapshot.getValue(String.class);
                        if (imageUrl != null) {
                            imageUrls.add(imageUrl);
                        }
                    }

                    tvSliderAdapter = new MobileSliderAdapter(tv_brandActivity.this, imageUrls);
                    tvViewPager.setAdapter(tvSliderAdapter);

                    sliderHandler.postDelayed(slideRunnable, 3000);
                }
            }
        });
    }

    private final Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            if (tvViewPager != null && tvViewPager != null) {
                int nextItem = (tvViewPager.getCurrentItem() + 1) % tvSliderAdapter.getItemCount();
                tvViewPager.setCurrentItem(nextItem);
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
