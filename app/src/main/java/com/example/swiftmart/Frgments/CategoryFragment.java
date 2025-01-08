package com.example.swiftmart.Frgments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.swiftmart.Adapter.ProductAdapter;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.R;
import com.example.swiftmart.Utils.CustomToast;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    private LinearLayout mobiles, earbuds, tv, laptop, headphone, speaker, keyword, mouse, camera, smartwatch, tablet; // All category
    private LinearLayout iphone, samsung, vivo, oppo, mi, realme, motorola, poco, goggle, oneplus, iqoo, nothing; // All mobiles company
    private LinearLayout boatlogo, realmelogo, onepluslogo, nothinglogo, triggerlogo, trukelogo;
    private HorizontalScrollView mobileScrollView, earphoneHorizontalScrollView, tvHorizontalScrollView, laptopHorizontalScrollView;
    private ScrollView homeFragmentScrollView;
    private View mobileIndicator, earbudsIndicator, tvIndicator, laptopsIndicator, headphonesIndicator, speakersIndicator, keyboardIndicator, mouseIndicator, cameraIndicator, smartwatchesIndicator, tabletsIndicator;

    private ArrayList<ProductModel> datalist = new ArrayList<>();
    private RecyclerView adminCategoryRecyclerview;
    private FirebaseFirestore db;
    private ProductAdapter adapter;

    public CategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        db = FirebaseFirestore.getInstance();

        initViews(view);
        getAllProducts();
        setCategoryClickListeners();
        setMobileCompanyClickListeners();
        setEarbudsCompanyClickListeners();

        handleOnBackPress();

        return view;
    }


    private void initViews(View view) {
        // recycler view
        adminCategoryRecyclerview = view.findViewById(R.id.adminCategoryRecyclerview);

        // all categories
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

        // category indicators
        mobileIndicator = view.findViewById(R.id.mobileIndicator);
        earbudsIndicator = view.findViewById(R.id.earbudsIndicator);
        tvIndicator = view.findViewById(R.id.tvIndicator);
        laptopsIndicator = view.findViewById(R.id.laptopsIndicator);
        headphonesIndicator = view.findViewById(R.id.headphonesIndicator);
        speakersIndicator = view.findViewById(R.id.speakersIndicator);
        keyboardIndicator = view.findViewById(R.id.keyboardIndicator);
        mouseIndicator = view.findViewById(R.id.mouseIndicator);
        cameraIndicator = view.findViewById(R.id.cameraIndicator);
        smartwatchesIndicator = view.findViewById(R.id.smartwatchesIndicator);
        tabletsIndicator = view.findViewById(R.id.tabletsIndicator);

        // mobile companies
        iphone = view.findViewById(R.id.iphone);
        samsung = view.findViewById(R.id.samsung);
        vivo = view.findViewById(R.id.vivo);
        oppo = view.findViewById(R.id.oppo);
        mi = view.findViewById(R.id.mi);
        realme = view.findViewById(R.id.realme);
        motorola = view.findViewById(R.id.motorola);
        poco = view.findViewById(R.id.poco);
        goggle = view.findViewById(R.id.goggle);
        oneplus = view.findViewById(R.id.oneplus);
        iqoo = view.findViewById(R.id.iqoo);
        nothing = view.findViewById(R.id.nothing);

        // earbuds companies
        boatlogo = view.findViewById(R.id.boatlogo);
        realmelogo = view.findViewById(R.id.realmelogo);
        onepluslogo = view.findViewById(R.id.onepluslogo);
        nothinglogo = view.findViewById(R.id.nothinglogo);
        triggerlogo = view.findViewById(R.id.triggerlogo);
        trukelogo = view.findViewById(R.id.trukelogo);

        homeFragmentScrollView = view.findViewById(R.id.homeFragmentScrollView);
        mobileScrollView = view.findViewById(R.id.mobileHorizontalScrollView);
        earphoneHorizontalScrollView = view.findViewById(R.id.earphoneHorizontalScrollView);
        tvHorizontalScrollView = view.findViewById(R.id.tvHorizontalScrollView);
        laptopHorizontalScrollView = view.findViewById(R.id.laptopHorizontalScrollView);

        homeFragmentScrollView.setVerticalScrollBarEnabled(false);
        mobileScrollView.setHorizontalScrollBarEnabled(false);
        earphoneHorizontalScrollView.setHorizontalScrollBarEnabled(false);
        tvHorizontalScrollView.setHorizontalScrollBarEnabled(false);
        laptopHorizontalScrollView.setHorizontalScrollBarEnabled(false);

    }

    // Get all the products from the database
    private void getAllProducts(){
        adminCategoryRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        datalist.clear();

        db.collection("Products")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(getContext(), R.drawable.img_logo, "Error in data fetching");
                            return;
                        }

                        if (value != null && !value.isEmpty()){
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                datalist.add(productModel);

                                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                                adminCategoryRecyclerview.setLayoutManager(layoutManager);
                                adapter = new ProductAdapter(getContext(), datalist);
                                adminCategoryRecyclerview.setHasFixedSize(true);
                                adminCategoryRecyclerview.setAdapter(adapter);
                                adminCategoryRecyclerview.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                    }
                });
    }

    private void setCategoryClickListeners() {
        mobiles.setOnClickListener(v -> {
            handleCategoryClick("Mobile", mobileScrollView);
            selectIndicator(mobileIndicator);
        });
        earbuds.setOnClickListener(v -> {
            handleCategoryClick("AirBuds", earphoneHorizontalScrollView);
            selectIndicator(earbudsIndicator);
        });
        tv.setOnClickListener(v -> {
            handleCategoryClick("TV", tvHorizontalScrollView);
            selectIndicator(tvIndicator);
        });
        laptop.setOnClickListener(v -> {
            handleCategoryClick("Laptop", laptopHorizontalScrollView);
            selectIndicator(laptopsIndicator);
        });
        headphone.setOnClickListener(v -> {
            selectIndicator(headphonesIndicator);
        });
        speaker.setOnClickListener(v -> {
            selectIndicator(speakersIndicator);
        });
        keyword.setOnClickListener(v -> {
            selectIndicator(keyboardIndicator);
        });
        mouse.setOnClickListener(v -> {
            selectIndicator(mouseIndicator);
        });
        camera.setOnClickListener(v -> {
            selectIndicator(cameraIndicator);
        });
        smartwatch.setOnClickListener(v -> {
            selectIndicator(smartwatchesIndicator);
        });
        tablet.setOnClickListener(v -> {
            selectIndicator(tabletsIndicator);
        });
    }

    private void setMobileCompanyClickListeners() {
        iphone.setOnClickListener(v -> fetchFilteredData("Mobile", "Apple"));
        samsung.setOnClickListener(v -> fetchFilteredData("Mobile", "Samsung"));
        vivo.setOnClickListener(v -> fetchFilteredData("Mobile", "Vivo"));
        oppo.setOnClickListener(v -> fetchFilteredData("Mobile", "Oppo"));
        mi.setOnClickListener(v -> fetchFilteredData("Mobile", "Xiaomi"));
        realme.setOnClickListener(v -> fetchFilteredData("Mobile", "Realme"));
        motorola.setOnClickListener(v -> fetchFilteredData("Mobile", "Motorola"));
        poco.setOnClickListener(v -> fetchFilteredData("Mobile", "Poco"));
        goggle.setOnClickListener(v -> fetchFilteredData("Mobile", "Google"));
        oneplus.setOnClickListener(v -> fetchFilteredData("Mobile", "OnePlus"));
        iqoo.setOnClickListener(v -> fetchFilteredData("Mobile", "Iqoo"));
        nothing.setOnClickListener(v -> fetchFilteredData("Mobile", "Nothing"));
    }

    private void setEarbudsCompanyClickListeners() {
        boatlogo.setOnClickListener(v -> fetchFilteredData("AirBuds", "Boat"));
        realmelogo.setOnClickListener(v -> fetchFilteredData("AirBuds", "Realme"));
        onepluslogo.setOnClickListener(v -> fetchFilteredData("AirBuds", "OnePlus"));
        nothinglogo.setOnClickListener(v -> fetchFilteredData("AirBuds", "Nothing"));
        triggerlogo.setOnClickListener(v -> fetchFilteredData("AirBuds", "Trigger"));
        trukelogo.setOnClickListener(v -> fetchFilteredData("AirBuds", "Truke"));
    }

    private void handleCategoryClick(String category, HorizontalScrollView visibleScrollView) {
        clearData();
        showOnlySelectedScrollView(visibleScrollView);
        fetchCategoryData(category);
    }

    private void fetchCategoryData(String category) {
        db.collection("Products")
                .whereEqualTo("category", category)
                .get()
                .addOnCompleteListener(this::handleFetchResult);
    }

    private void fetchFilteredData(String category, String company) {
        db.collection("Products")
                .whereEqualTo("category", category)
                .whereEqualTo("company", company)
                .get()
                .addOnCompleteListener(this::handleFetchResult);
    }

    private void handleFetchResult(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            List<ProductModel> data = task.getResult().toObjects(ProductModel.class);
            datalist.clear();
            datalist.addAll(data);

            if (adapter == null) {
                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                adminCategoryRecyclerview.setLayoutManager(layoutManager);
                adapter = new ProductAdapter(getContext(), datalist);
                adminCategoryRecyclerview.setHasFixedSize(true);
                adminCategoryRecyclerview.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(getContext(), "Failed to load products", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearData() {
        datalist.clear();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void showOnlySelectedScrollView(HorizontalScrollView visibleScrollView) {
        mobileScrollView.setVisibility(View.GONE);
        earphoneHorizontalScrollView.setVisibility(View.GONE);
        tvHorizontalScrollView.setVisibility(View.GONE);
        laptopHorizontalScrollView.setVisibility(View.GONE);

        visibleScrollView.setVisibility(View.VISIBLE);
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message + " Clicked", Toast.LENGTH_SHORT).show();
    }

    private void selectIndicator(View indicator) {
        indicator.setVisibility(View.VISIBLE);

        mobileIndicator.setVisibility(View.GONE);
        earbudsIndicator.setVisibility(View.GONE);
        tvIndicator.setVisibility(View.GONE);
        laptopsIndicator.setVisibility(View.GONE);
        headphonesIndicator.setVisibility(View.GONE);
        speakersIndicator.setVisibility(View.GONE);
        keyboardIndicator.setVisibility(View.GONE);
        mouseIndicator.setVisibility(View.GONE);
        cameraIndicator.setVisibility(View.GONE);
        smartwatchesIndicator.setVisibility(View.GONE);
        tabletsIndicator.setVisibility(View.GONE);

        indicator.setVisibility(View.VISIBLE);
    }

    // handle onBack press
    private void handleOnBackPress(){
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new HomeFragment())
                        .commit();

//                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
//                bottomNavigationView.setSelectedItemId(R.id.home);
            }
        });
    }

}