package com.example.swiftmart;

import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swiftmart.Adapter.ProductAdapter;
import com.example.swiftmart.Model.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminCategory extends AppCompatActivity {

    private LinearLayout mobiles, earbuds, tv, laptop, headphone, speaker, keyword, mouse, camera, smartwatch, tablet;
    private LinearLayout iphone, samsung, vivo, oppo, mi, realme;
    private HorizontalScrollView mobileScrollView, earphoneHorizontalScrollView, tvHorizontalScrollView, laptopHorizontalScrollView;

    private ArrayList<ProductModel> datalist = new ArrayList<>();
    private RecyclerView adminCategoryRecyclerview;
    private FirebaseFirestore db;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        db = FirebaseFirestore.getInstance();
        initViews();
        setCategoryClickListeners();
        setMobileCompanyClickListeners();
    }

    private void initViews() {
        // Initialize views
        adminCategoryRecyclerview = findViewById(R.id.adminCategoryRecyclerview);
        mobiles = findViewById(R.id.mobiles);
        earbuds = findViewById(R.id.earbuds);
        tv = findViewById(R.id.tv);
        laptop = findViewById(R.id.laptop);
        headphone = findViewById(R.id.headphone);
        speaker = findViewById(R.id.speaker);
        keyword = findViewById(R.id.keyword);
        mouse = findViewById(R.id.mouse);
        camera = findViewById(R.id.camera);
        smartwatch = findViewById(R.id.smartwatch);
        tablet = findViewById(R.id.tablet);

        iphone = findViewById(R.id.iphone);
        samsung = findViewById(R.id.samsung);
        vivo = findViewById(R.id.vivo);
        oppo = findViewById(R.id.oppo);
        mi = findViewById(R.id.mi);
        realme = findViewById(R.id.realme);

        mobileScrollView = findViewById(R.id.mobileHorizontalScrollView);
        earphoneHorizontalScrollView = findViewById(R.id.earphoneHorizontalScrollView);
        tvHorizontalScrollView = findViewById(R.id.tvHorizontalScrollView);
        laptopHorizontalScrollView = findViewById(R.id.laptopHorizontalScrollView);
    }

    private void setCategoryClickListeners() {
        mobiles.setOnClickListener(v -> handleCategoryClick("Mobile", mobileScrollView));
        earbuds.setOnClickListener(v -> handleCategoryClick("Earbuds", earphoneHorizontalScrollView));
        tv.setOnClickListener(v -> handleCategoryClick("TV", tvHorizontalScrollView));
        laptop.setOnClickListener(v -> handleCategoryClick("Laptop", laptopHorizontalScrollView));

        headphone.setOnClickListener(v -> showToast("Headphones"));
        speaker.setOnClickListener(v -> showToast("Speakers"));
        keyword.setOnClickListener(v -> showToast("Keyboard"));
        mouse.setOnClickListener(v -> showToast("Mouse"));
        camera.setOnClickListener(v -> showToast("Camera"));
        smartwatch.setOnClickListener(v -> showToast("Smartwatch"));
        tablet.setOnClickListener(v -> showToast("Tablet"));
    }

    private void setMobileCompanyClickListeners() {
        iphone.setOnClickListener(v -> fetchFilteredData("Mobile", "Apple"));
        samsung.setOnClickListener(v -> fetchFilteredData("Mobile", "Samsung"));
        vivo.setOnClickListener(v -> fetchFilteredData("Mobile", "Vivo"));
        oppo.setOnClickListener(v -> fetchFilteredData("Mobile", "Oppo"));
        mi.setOnClickListener(v -> fetchFilteredData("Mobile", "Xiaomi"));
        realme.setOnClickListener(v -> fetchFilteredData("Mobile", "Realme"));
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
                GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
                adminCategoryRecyclerview.setLayoutManager(layoutManager);
                adapter = new ProductAdapter(this, datalist);
                adminCategoryRecyclerview.setHasFixedSize(true);
                adminCategoryRecyclerview.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(this, "Failed to load products", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, message + " Clicked", Toast.LENGTH_SHORT).show();
    }
}
