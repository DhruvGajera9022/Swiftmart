package com.example.swiftmart;

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

public class MobilesActivity extends AppCompatActivity {

    LinearLayout iphone,vivo,oppo,mi,realme,samsung,motorola,poco,goggle,oneplues;
    private ViewPager2 viewPager;
    private MobileSliderAdapter mobilesliderAdapter;
    private List<Integer> imageList; // List of drawable images
    private Handler sliderHandler = new Handler();
    ImageView backmobiles;
    ArrayList<ProductModel> datalist = new ArrayList<>();
    private RecyclerView mobileRecyclerView;
    private FirebaseFirestore db;
    ProductAdapter adapter;
    ScrollView mobileScrollView;
    HorizontalScrollView mobileHorizontalScrollView;
    private ProgressBar mobileActivityProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobiles);

        db = FirebaseFirestore.getInstance();

        mobileScrollView=findViewById(R.id.mobileScrollView);
        mobileHorizontalScrollView=findViewById(R.id.mobileHorizontalScrollView);

        iphone=findViewById(R.id.iphone);
        vivo=findViewById(R.id.vivo);
        oppo=findViewById(R.id.oppo);
        mi=findViewById(R.id.mi);
        realme=findViewById(R.id.realme);
        samsung=findViewById(R.id.samsung);
        motorola=findViewById(R.id.motorola);
        poco=findViewById(R.id.poco);
        goggle=findViewById(R.id.goggle);
        oneplues=findViewById(R.id.oneplues);
        backmobiles=findViewById(R.id.backmobiles);
        mobileRecyclerView=findViewById(R.id.mobileRecyclerView);
        mobileActivityProgressBar=findViewById(R.id.mobileActivityProgressBar);

        mobileScrollView.setVerticalScrollBarEnabled(false);
        mobileHorizontalScrollView.setHorizontalScrollBarEnabled(false);

        getMobiles();
        getMobileCompany();

        backmobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


        viewPager = findViewById(R.id.viewPager);

        // Add drawable images to the list
        imageList = Arrays.asList(
                R.drawable.i1,
                R.drawable.vivo1,
                R.drawable.motorola1,
                R.drawable.realme1,
                R.drawable.samsung1,
                R.drawable.oppo1,
                R.drawable.mi1,
                R.drawable.oneplus1,
                R.drawable.poco1
        );

        mobilesliderAdapter = new MobileSliderAdapter(this, imageList);
        viewPager.setAdapter(mobilesliderAdapter);

        // Add swipe listener for manual changes
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
            int currentItem = viewPager.getCurrentItem();
            int nextItem = (currentItem + 1) % imageList.size(); // Loop back to the first item
            viewPager.setCurrentItem(nextItem, true); // Smooth scroll
            sliderHandler.postDelayed(this, 3000); // Slide every 3 seconds
        }
    };

    // handle slider on onPause
    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable); // Stop slider when activity is paused
    }

    // handle slider on onResume
    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    // handle on back press
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Get all the mobiles
    private void getMobiles(){
        mobileRecyclerView.setLayoutManager(new LinearLayoutManager(MobilesActivity.this));
        mobileActivityProgressBar.setVisibility(View.VISIBLE);
        datalist.clear();

        db.collection("Products")
                .whereEqualTo("category", "Mobile")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(MobilesActivity.this,  "Error in data fetching");
                            mobileActivityProgressBar.setVisibility(View.GONE);
                            return;
                        }


                        if (value != null && !value.isEmpty()){
                            mobileActivityProgressBar.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                datalist.add(productModel);

                                GridLayoutManager layoutManager = new GridLayoutManager(MobilesActivity.this, 2);
                                mobileRecyclerView.setLayoutManager(layoutManager);
                                adapter = new ProductAdapter(MobilesActivity.this, datalist);
                                mobileRecyclerView.setHasFixedSize(true);
                                mobileRecyclerView.setAdapter(adapter);
                                mobileRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                    }
                });


    }

    // Get single mobile company data
    private void getCompany(String company){
        mobileRecyclerView.setLayoutManager(new LinearLayoutManager(MobilesActivity.this));
        mobileActivityProgressBar.setVisibility(View.VISIBLE);
        datalist.clear();

        db.collection("Products")
                .whereEqualTo("category", "Mobile")
                .whereEqualTo("company", company)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(MobilesActivity.this, "Error in data fetching");
                            mobileActivityProgressBar.setVisibility(View.GONE);
                            return;
                        }

                        if (value != null && !value.isEmpty()){
                            mobileActivityProgressBar.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                datalist.add(productModel);

                                GridLayoutManager layoutManager = new GridLayoutManager(MobilesActivity.this, 2);
                                mobileRecyclerView.setLayoutManager(layoutManager);
                                adapter = new ProductAdapter(MobilesActivity.this, datalist);
                                mobileRecyclerView.setHasFixedSize(true);
                                mobileRecyclerView.setAdapter(adapter);
                                mobileRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                    }
                });
    }

    // Get company wise mobile data
    private void getMobileCompany(){
        iphone.setOnClickListener(v -> getCompany("Apple"));
        vivo.setOnClickListener(v -> getCompany("Vivo"));
        oppo.setOnClickListener(v -> getCompany("Oppo"));
        mi.setOnClickListener(v -> getCompany("Xiaomi"));
        realme.setOnClickListener(v -> getCompany("Realme"));
        samsung.setOnClickListener(v -> getCompany("Samsung"));
        motorola.setOnClickListener(v -> getCompany("Motorola"));
        goggle.setOnClickListener(v -> getCompany("Oppo"));
        oneplues.setOnClickListener(v -> getCompany("OnePlus"));
    }

}