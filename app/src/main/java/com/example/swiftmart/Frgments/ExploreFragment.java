package com.example.swiftmart.Frgments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.swiftmart.Adapter.ProductAdapter;
import com.example.swiftmart.MainActivity;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.R;
import com.example.swiftmart.Utils.CustomToast;
import com.example.swiftmart.Utils.FilterData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {
    private SearchView exploreFragmentSearchView;
    private SwipeRefreshLayout exploreFragmentSwipeRefresh;
    private RecyclerView exploreFragmentRecyclerView;
    private ProgressBar exploreFragmentProgressBar;
    private ImageButton exploreFilterIcon;
    private BottomSheetDialog sheetDialog;

    FirebaseFirestore db;
    ArrayList<ProductModel> datalist = new ArrayList<>();
    ProductAdapter adapter;

    int selectedTab = 1;


    public ExploreFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);


        initialization(view);

        getAllProducts();
        swipeRefresh();
        handleSearch();

        handleFilterButton();

        handleOnBackPress();

        return view;
    }

    private void initialization(View view){
        exploreFragmentSearchView = view.findViewById(R.id.exploreFragmentSearchView);
        exploreFragmentSwipeRefresh = view.findViewById(R.id.exploreFragmentSwipeRefresh);
        exploreFragmentRecyclerView = view.findViewById(R.id.exploreFragmentRecyclerView);
        exploreFragmentProgressBar = view.findViewById(R.id.exploreFragmentProgressBar);
        exploreFilterIcon = view.findViewById(R.id.exploreFilterIcon);

        db = FirebaseFirestore.getInstance();

    }


    // Get all the products from the database
    private void getAllProducts(){
        exploreFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        exploreFragmentProgressBar.setVisibility(View.VISIBLE);
        datalist.clear();

        db.collection("Products")
                .limit(30)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(getContext(), "Error in data fetching");
                            exploreFragmentProgressBar.setVisibility(View.GONE);
                            return;
                        }


                        if (value != null && !value.isEmpty()){
                            exploreFragmentProgressBar.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                datalist.add(productModel);

                                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                                exploreFragmentRecyclerView.setLayoutManager(layoutManager);
                                adapter = new ProductAdapter(getContext(), datalist);
                                exploreFragmentRecyclerView.setHasFixedSize(true);
                                exploreFragmentRecyclerView.setAdapter(adapter);
                                exploreFragmentRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                    }
                });
    }

    // Swipe refresh layout
    private void swipeRefresh(){
        exploreFragmentSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllProducts();

                exploreFragmentSwipeRefresh.setRefreshing(false);
            }
        });
    }

    // handle search
    private void handleSearch(){
        exploreFragmentSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProducts(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchProducts(newText);
                return false;
            }
        });
    }

    // handle searchProduct
    private void searchProducts(String query){
        exploreFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        exploreFragmentProgressBar.setVisibility(View.VISIBLE);
        datalist.clear();

        if (query.isEmpty()){
            getAllProducts();
        } else {
            datalist.clear();
            db.collection("Products")
                    .whereGreaterThanOrEqualTo("name", query)
                    .whereLessThanOrEqualTo("name", query + '\uf8ff')
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                CustomToast.showToast(getContext(), "Error in data fetching");
                                exploreFragmentProgressBar.setVisibility(View.GONE);
                                return;
                            }

                            // Hide progress bar as soon as the data starts processing
                            exploreFragmentProgressBar.setVisibility(View.GONE);

                            if (value != null && !value.isEmpty()) {
                                // Clear the list before adding new data
                                datalist.clear();

                                for (QueryDocumentSnapshot documentSnapshot : value) {
                                    ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                    datalist.add(productModel);
                                }

                                // Set the layout manager and adapter only once
                                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                                exploreFragmentRecyclerView.setLayoutManager(layoutManager);
                                adapter = new ProductAdapter(getContext(), datalist);
                                exploreFragmentRecyclerView.setHasFixedSize(true);
                                exploreFragmentRecyclerView.setAdapter(adapter);
                                exploreFragmentRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            } else {
                                // Handle empty data case
                                CustomToast.showToast(getContext(), "No products found.");
                            }
                        }
                    });
        }
    }

    // handle filter button click
    private void handleFilterButton(){
        exploreFilterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
    }

    // Updated showBottomSheetDialog() method implementation
    private void showBottomSheetDialog() {
        sheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialog);
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.product_filter_layout,
                (LinearLayout) getView().findViewById(R.id.bottomSheetLinearLayout));

        // Initialize views
        RangeSlider priceRangeSlider = dialogView.findViewById(R.id.price_range_slider);
        ChipGroup categoryChips = dialogView.findViewById(R.id.category_chips);
        RadioGroup sortRadioGroup = dialogView.findViewById(R.id.sort_radio_group);
        Button resetButton = dialogView.findViewById(R.id.resetButton);
        Button applyButton = dialogView.findViewById(R.id.applyButton);
        ImageButton closeButton = dialogView.findViewById(R.id.closeButton);

        // Setup category chips
        setupCategoryChips(categoryChips);

        // Setup price range slider
        priceRangeSlider.addOnChangeListener((slider, value, fromUser) -> {
            // Handle price range changes
            List<Float> values = slider.getValues();
            float minPrice = values.get(0);
            float maxPrice = values.get(1);
        });

        // Button click listeners
        closeButton.setOnClickListener(v -> sheetDialog.dismiss());

        resetButton.setOnClickListener(v -> {
            // Reset all filters to default values
            priceRangeSlider.setValues(0f, 1000f);
            categoryChips.clearCheck();
            sortRadioGroup.clearCheck();
        });

        applyButton.setOnClickListener(v -> {
            // Collect filter values
            List<Float> priceRange = priceRangeSlider.getValues();
            List<String> selectedCategories = getSelectedCategories(categoryChips);
            int sortOption = sortRadioGroup.getCheckedRadioButtonId();

            // Apply filters
            applyFilters(priceRange, selectedCategories, sortOption);
            sheetDialog.dismiss();
        });

        sheetDialog.setContentView(dialogView);
        sheetDialog.show();
    }

    // Helper methods
    private void setupCategoryChips(ChipGroup chipGroup) {
        String[] categories = getResources().getStringArray(R.array.categories);
        for (String category : categories) {
            Chip chip = new Chip(requireContext());
            chip.setText(category);
            chip.setCheckable(true);
            chipGroup.addView(chip);
        }
    }

    private List<String> getSelectedCategories(ChipGroup chipGroup) {
        List<String> selectedCategories = new ArrayList<>();
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            if (chip.isChecked()) {
                selectedCategories.add(chip.getText().toString());
            }
        }
        return selectedCategories;
    }

    private void applyFilters(List<Float> priceRange, List<String> categories, int sortOption) {
        float minPrice = priceRange.get(0);
        float maxPrice = priceRange.get(1);

        // Create FilterData object
        FilterData filterData = new FilterData(minPrice, maxPrice, categories, sortOption);

        // Get readable sort option text
        String sortText;
        if (sortOption == R.id.sort_price_low) {
            sortText = "Price: Low to High";
        } else if (sortOption == R.id.sort_price_high) {
            sortText = "Price: High to Low";
        } else if (sortOption == R.id.sort_rating) {
            sortText = "Rating";
        } else {
            sortText = "No sort selected";
        }

        // Build toast message
        StringBuilder message = new StringBuilder();
        message.append("Price Range: $").append(String.format("%.2f", minPrice))
                .append(" - $").append(String.format("%.2f", maxPrice)).append("\n");

        message.append("Categories: ");
        if (categories.isEmpty()) {
            message.append("None selected");
        } else {
            message.append(TextUtils.join(", ", categories));
        }
        message.append("\n");

        message.append("Sort By: ").append(sortText);

        // Show toast
        CustomToast.showToast(getContext(), message.toString());
    }

    // handle onBack press
    private void handleOnBackPress(){
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                startActivity(new Intent(getContext(), MainActivity.class));

//                requireActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frameLayout, new HomeFragment())
//                        .commit();
//
//                LinearLayout homeLayout, exploreLayout, categoryLayout, profileLayout;
//                ImageView homeImage, exploreImage, categoryImage, profileImage;
//                TextView homeText, exploreText, categoryText, profileText;
//
//                homeLayout = requireActivity().findViewById(R.id.homeLayout);
//                exploreLayout = requireActivity().findViewById(R.id.exploreLayout);
//                categoryLayout = requireActivity().findViewById(R.id.categoryLayout);
//                profileLayout = requireActivity().findViewById(R.id.profileLayout);
//
//                homeImage = requireActivity().findViewById(R.id.homeImage);
//                exploreImage = requireActivity().findViewById(R.id.exploreImage);
//                categoryImage = requireActivity().findViewById(R.id.categoryImage);
//                profileImage = requireActivity().findViewById(R.id.profileImage);
//
//                homeText = requireActivity().findViewById(R.id.homeText);
//                exploreText = requireActivity().findViewById(R.id.exploreText);
//                categoryText = requireActivity().findViewById(R.id.categoryText);
//                profileText = requireActivity().findViewById(R.id.profileText);
//
//                // Update UI to reflect that Home tab is selected
//                exploreText.setVisibility(View.GONE);
//                categoryText.setVisibility(View.GONE);
//                profileText.setVisibility(View.GONE);
//
//                exploreImage.setImageResource(R.drawable.icon_explore);
//                categoryImage.setImageResource(R.drawable.icon_category);
//                profileImage.setImageResource(R.drawable.icon_person);
//
//                exploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//                categoryLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//                profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//
//                homeText.setVisibility(View.VISIBLE);
//                homeImage.setImageResource(R.drawable.icon_home_selected);
//                homeLayout.setBackgroundResource(R.drawable.rounded_back_home_200);
//
//                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1f,1f,1f, ScaleAnimation.RELATIVE_TO_SELF, 0.0f, ScaleAnimation.RELATIVE_TO_SELF, 0.0f);
//                scaleAnimation.setDuration(200);
//                scaleAnimation.setFillAfter(true);
//                homeLayout.startAnimation(scaleAnimation);
//
//                selectedTab = 1;

            }
        });
    }

}