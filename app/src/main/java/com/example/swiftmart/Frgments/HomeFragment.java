package com.example.swiftmart.Frgments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.swiftmart.Adapter.MobileSliderAdapter;
import com.example.swiftmart.Adapter.ProductAdapter;
import com.example.swiftmart.AllProducts;
import com.example.swiftmart.EarphoneActivity;
import com.example.swiftmart.Account.Edit_profile_Activity;
import com.example.swiftmart.Leptop_Activity;
import com.example.swiftmart.MobilesActivity;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.R;
import com.example.swiftmart.Utils.CustomToast;
import com.example.swiftmart.tv_brandActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    LinearLayout mobiles, earbuds, tv, laptop, headphone, speaker, keyword, mouse, camera, smartwatch, tablet;
    FirebaseFirestore db;
    RecyclerView homeFragmentFeaturedRecyclerView, homeFragmentMostPopularRecyclerView, homeFragmentNewRecyclerView;
    CircleImageView homeFragmentUserAvatar;
    TextView homeFragmentUserName, seeAll1, seeAll2, seeAll3;
    FirebaseAuth mAuth;
    String uid;
    NestedScrollView homeFragmentScrollView;
    BottomSheetDialog sheetDialog;
    SearchView homeFragmentSearchView;
    private ViewPager2 mainViewPager;
    private MobileSliderAdapter mobilesliderAdapter;
    private Handler sliderHandler = new Handler();

    private DatabaseReference databaseReference;
    private List<String> imageUrls;

    private ArrayList<ProductModel> featuredDataList = new ArrayList<>();
    private ArrayList<ProductModel> mostPopularDataList = new ArrayList<>();
    private ArrayList<ProductModel> newArrivedDataList = new ArrayList<>();

    private ProductAdapter featuredAdapter;
    private ProductAdapter mostPopularAdapter;
    private ProductAdapter newArrivedAdapter;

    public HomeFragment() {

    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("root");
        imageUrls = new ArrayList<>();

        homeFragmentScrollView = view.findViewById(R.id.homeFragmentScrollView);

        homeFragmentFeaturedRecyclerView = view.findViewById(R.id.homeFragmentFeaturedRecyclerView);
        homeFragmentMostPopularRecyclerView = view.findViewById(R.id.homeFragmentMostPopularRecyclerView);
        homeFragmentNewRecyclerView = view.findViewById(R.id.homeFragmentNewRecyclerView);

        mobiles = view.findViewById(R.id.mobiles);
        earbuds = view.findViewById(R.id.earbuds);
        tv = view.findViewById(R.id.tv);
        laptop = view.findViewById(R.id.laptop);
        headphone = view.findViewById(R.id.headphone);
        speaker = view.findViewById(R.id.speaker);
        keyword = view.findViewById(R.id.keyword);
        mouse = view.findViewById(R.id.mouse);
        camera = view.findViewById(R.id.camera);
        smartwatch = view.findViewById(R.id.smartwatch);
        tablet = view.findViewById(R.id.tablet);
        homeFragmentUserAvatar = view.findViewById(R.id.homeFragmentUserAvatar);
        homeFragmentUserName = view.findViewById(R.id.homeFragmentUserName);
        homeFragmentSearchView = view.findViewById(R.id.homeFragmentSearchView);

        mainViewPager = view.findViewById(R.id.mainViewPager);

        seeAll1 = view.findViewById(R.id.seeAll1);
        seeAll2 = view.findViewById(R.id.seeAll2);
        seeAll3 = view.findViewById(R.id.seeAll3);

        homeFragmentScrollView.setVerticalScrollBarEnabled(false);


        getUserData();
        handleHomeFragmentUserAvtarClick();
//        handleSearch();
//        getAllProducts();
        getFeaturedData();
        getMostPopularData();
        getNewArrivedData();
        handleSeeAllClick();
        getImageUrls();

//        handleMobileClick();
//        handleEarbudsClick();
//        handleTVClick();
//        handleLaptopClick();

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showBottomSheetDialog();
            }
        });

        return view;
    }

    // Get the user data from the database
    private void getUserData(){
        uid = mAuth.getCurrentUser().getUid();
        DocumentReference reference = db.collection("Users").document(uid);

        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()){
                    homeFragmentUserName.setText(value.getString("Username").split(" ")[0]);
                    Picasso.get().load(value.getString("Image")).into(homeFragmentUserAvatar);
                }
            }
        });

    }

    // handle homeFragmentUserAvatar click
    private void handleHomeFragmentUserAvtarClick(){
        homeFragmentUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeFragmentUserAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), Edit_profile_Activity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    // get Featured Data
    private void getFeaturedData(){
        homeFragmentFeaturedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        db.collection("Products")
                .limit(10)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(getContext(), "Error in data fetching");
                            return;
                        }

                        if (value != null && !value.isEmpty()){
                            featuredDataList.clear();
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                featuredDataList.add(productModel);

                                featuredAdapter = new ProductAdapter(getContext(), featuredDataList);
                                homeFragmentFeaturedRecyclerView.setHasFixedSize(true);
                                homeFragmentFeaturedRecyclerView.setAdapter(featuredAdapter);
                                homeFragmentFeaturedRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                    }
                });
    }

    // get Most Popular Data
    private void getMostPopularData(){
        homeFragmentMostPopularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        db.collection("Products")
                .limit(10)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(getContext(), "Error in data fetching");
                            return;
                        }

                        if (value != null && !value.isEmpty()){
                            mostPopularDataList.clear();
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                mostPopularDataList.add(productModel);

                                mostPopularAdapter = new ProductAdapter(getContext(), mostPopularDataList);
                                homeFragmentMostPopularRecyclerView.setHasFixedSize(true);
                                homeFragmentMostPopularRecyclerView.setAdapter(mostPopularAdapter);
                                homeFragmentMostPopularRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                    }
                });
    }

    // get New Arrived Data
    private void getNewArrivedData(){
        homeFragmentNewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        db.collection("Products")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(10)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(getContext(), "Error in data fetching");
                            return;
                        }

                        if (value != null && !value.isEmpty()){
                            newArrivedDataList.clear();
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                newArrivedDataList.add(productModel);

                                newArrivedAdapter = new ProductAdapter(getContext(), newArrivedDataList);
                                homeFragmentNewRecyclerView.setHasFixedSize(true);
                                homeFragmentNewRecyclerView.setAdapter(newArrivedAdapter);
                                homeFragmentNewRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                    }
                });
    }

    // handle see all click
    private void handleSeeAllClick(){
        seeAll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AllProducts.class);
                startActivity(intent);
            }
        });
        seeAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AllProducts.class);
                startActivity(intent);
            }
        });
        seeAll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AllProducts.class);
                startActivity(intent);
            }
        });
    }

    private void getImageUrls() {
        databaseReference.child("Home").child("imgurls").addValueEventListener(new ValueEventListener() {
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

                    mobilesliderAdapter = new MobileSliderAdapter(getContext(), imageUrls);
                    mainViewPager.setAdapter(mobilesliderAdapter);

                    sliderHandler.postDelayed(slideRunnable, 3000);
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
            if (mainViewPager != null && mobilesliderAdapter != null) {
                int nextItem = (mainViewPager.getCurrentItem() + 1) % mobilesliderAdapter.getItemCount();
                mainViewPager.setCurrentItem(nextItem);
                sliderHandler.postDelayed(this, 3000);
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(slideRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(slideRunnable, 3000);
    }


    private void showBottomSheetDialog() {
        sheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialog);
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_dialog,
                (LinearLayout) getView().findViewById(R.id.bottomSheetLinearLayout));

        Button bottomSheetCancelButton = dialogView.findViewById(R.id.bottomSheetCancelButton);
        Button bottomSheetOkayButton = dialogView.findViewById(R.id.bottomSheetOkayButton);

        sheetDialog.setContentView(dialogView);
        sheetDialog.setCancelable(false);
        sheetDialog.show();

        bottomSheetCancelButton.setOnClickListener(v -> sheetDialog.dismiss());

        bottomSheetOkayButton.setOnClickListener(v -> {
            sheetDialog.dismiss();
            requireActivity().finish();
        });
    }








    // handle search
//    private void handleSearch(){
//        homeFragmentSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                searchProducts(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                searchProducts(newText);
//                return false;
//            }
//        });
//    }

//    // handle searchProduct
//    private void searchProducts(String query){
//        homeFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        datalist.clear();
//
//        if (query.isEmpty()){
//            getAllProducts();
//        } else {
//            datalist.clear();
//            db.collection("Products")
//                    .whereGreaterThanOrEqualTo("name", query)
//                    .whereLessThanOrEqualTo("name", query + '\uf8ff')
//                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                            if (error != null) {
//                                CustomToast.showToast(getContext(), "Error in data fetching");
//                                return;
//                            }
//
//                            if (value != null && !value.isEmpty()) {
//                                // Clear the list before adding new data
//                                datalist.clear();
//
//                                for (QueryDocumentSnapshot documentSnapshot : value) {
//                                    ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
//                                    datalist.add(productModel);
//                                }
//
//                                // Set the layout manager and adapter only once
//                                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
//                                homeFragmentRecyclerView.setLayoutManager(layoutManager);
//                                adapter = new ProductAdapter(getContext(), datalist);
//                                homeFragmentRecyclerView.setHasFixedSize(true);
//                                homeFragmentRecyclerView.setAdapter(adapter);
//                                homeFragmentRecyclerView.setItemAnimator(new DefaultItemAnimator());
//                            } else {
//                                // Handle empty data case
//                                CustomToast.showToast(getContext(), "No products found.");
//                            }
//                        }
//                    });
//        }
//    }

}