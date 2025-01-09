package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.HorizontalScrollView;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EarphoneActivity extends AppCompatActivity {

    LinearLayout boatlogo,realmelogo,onepluslogo,nothinglogo,triggerlogo,trukelogo;
    private ViewPager2 viewPager1;
    private MobileSliderAdapter earphonesliderAdapter;
    private List<Integer> imageList1; // List of drawable images
    private Handler sliderHandler = new Handler();
    ImageView backearphone;
    private RecyclerView earphoneRecyclerView;
    ArrayList<ProductModel> datalist = new ArrayList<>();
    private FirebaseFirestore db;
    ProductAdapter adapter;
    ScrollView earphoneScrollView;
    HorizontalScrollView earphoneHorizontalScrollView;
    ProgressBar earphoneActivityProgressBar;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earphone);

        db = FirebaseFirestore.getInstance();

        earphoneScrollView = findViewById(R.id.earphoneScrollView);
        earphoneHorizontalScrollView = findViewById(R.id.earphoneHorizontalScrollView);

        boatlogo=findViewById(R.id.boatlogo);
        nothinglogo=findViewById(R.id.nothinglogo);
        realmelogo=findViewById(R.id.realmelogo);
        onepluslogo=findViewById(R.id.onepluslogo);
        triggerlogo=findViewById(R.id.triggerlogo);
        trukelogo=findViewById(R.id.trukelogo);
        backearphone=findViewById(R.id.backearphone);
        earphoneRecyclerView=findViewById(R.id.earphoneRecyclerView);
        earphoneActivityProgressBar=findViewById(R.id.earphoneActivityProgressBar);

        earphoneScrollView.setVerticalScrollBarEnabled(false);
        earphoneHorizontalScrollView.setHorizontalScrollBarEnabled(false);

        getEarbuds();
        getEarbudsCompany();

        backearphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        viewPager1 = findViewById(R.id.viewPager1);

        // Add drawable images to the list
        imageList1 = Arrays.asList(
                R.drawable.boat1,
                R.drawable.boult1,
                R.drawable.realme2,
                R.drawable.noise1,
                R.drawable.samsung2,
                R.drawable.mi2,
                R.drawable.oneplus2
        );

        earphonesliderAdapter = new MobileSliderAdapter(this, imageList1);
        viewPager1.setAdapter(earphonesliderAdapter);

        // Add swipe listener for manual changes
        viewPager1.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000); // Restart the auto-slider after swipe
            }
        });
        // Start auto-slide
        sliderHandler.postDelayed(sliderRunnable, 3000);

    }
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = viewPager1.getCurrentItem();
            int nextItem = (currentItem + 1) % imageList1.size(); // Loop back to the first item
            viewPager1.setCurrentItem(nextItem, true); // Smooth scroll
            sliderHandler.postDelayed(this, 3000); // Slide every 3 seconds
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable); // Stop slider when activity is paused
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000); // Resume slider when activity is resumed
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Get all the earbuds
    private void getEarbuds(){
        earphoneRecyclerView.setLayoutManager(new LinearLayoutManager(EarphoneActivity.this));
        earphoneActivityProgressBar.setVisibility(View.VISIBLE);
        datalist.clear();

        db.collection("Products")
                .whereEqualTo("category", "AirBuds")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(EarphoneActivity.this, "Error in data fetching");
                            earphoneActivityProgressBar.setVisibility(View.GONE);
                            return;
                        }


                        if (value != null && !value.isEmpty()){
                            earphoneActivityProgressBar.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                datalist.add(productModel);

                                GridLayoutManager layoutManager = new GridLayoutManager(EarphoneActivity.this, 2);
                                earphoneRecyclerView.setLayoutManager(layoutManager);
                                adapter = new ProductAdapter(EarphoneActivity.this, datalist);
                                earphoneRecyclerView.setHasFixedSize(true);
                                earphoneRecyclerView.setAdapter(adapter);
                                earphoneRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                    }
                });


    }

    // Get single earbuds company data
    private void getCompany(String company){
        earphoneRecyclerView.setLayoutManager(new LinearLayoutManager(EarphoneActivity.this));
        earphoneActivityProgressBar.setVisibility(View.VISIBLE);
        datalist.clear();

        db.collection("Products")
                .whereEqualTo("category", "AirBuds")
                .whereEqualTo("company", company)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(EarphoneActivity.this,  "Error in data fetching");
                            earphoneActivityProgressBar.setVisibility(View.GONE);
                            return;
                        }


                        if (value != null && !value.isEmpty()){
                            earphoneActivityProgressBar.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                datalist.add(productModel);

                                GridLayoutManager layoutManager = new GridLayoutManager(EarphoneActivity.this, 2);
                                earphoneRecyclerView.setLayoutManager(layoutManager);
                                adapter = new ProductAdapter(EarphoneActivity.this, datalist);
                                earphoneRecyclerView.setHasFixedSize(true);
                                earphoneRecyclerView.setAdapter(adapter);
                                earphoneRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                    }
                });


    }

    // Get company wise earbuds data
    private void getEarbudsCompany(){
        boatlogo.setOnClickListener(v -> getCompany("Boat"));
        realmelogo.setOnClickListener(v -> getCompany("Realme"));
        onepluslogo.setOnClickListener(v -> getCompany("OnePlus"));
        nothinglogo.setOnClickListener(v -> getCompany("Nothing"));
        triggerlogo.setOnClickListener(v -> getCompany("Trigger"));
        trukelogo.setOnClickListener(v -> getCompany("Truke"));
    }

}