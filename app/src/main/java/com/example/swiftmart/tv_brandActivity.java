package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.swiftmart.Adapter.ProductAdapter;
import com.example.swiftmart.Model.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class tv_brandActivity extends AppCompatActivity {

    LinearLayout samsunglogo, lglogo, milogo, tcllogo;
    ImageView backetvbrand;
    private ViewPager2 viewPagertv;
    private RecyclerView tvRecyclerView;
    ArrayList<ProductModel> datalist = new ArrayList<>();
    private FirebaseFirestore db;
    ProductAdapter adapter;
    ScrollView tvActivityScrollView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_brand);

        db = FirebaseFirestore.getInstance();

        tvActivityScrollView = findViewById(R.id.tvActivityScrollView);

        // Initialize views
        samsunglogo = findViewById(R.id.samsunglogo);
        lglogo = findViewById(R.id.lglogo);
        milogo = findViewById(R.id.milogo);
        tcllogo = findViewById(R.id.tcllogo);
        backetvbrand = findViewById(R.id.backetvbrand);
        tvRecyclerView=findViewById(R.id.tvRecyclerView);

        tvActivityScrollView.setVerticalScrollBarEnabled(false);

        getTVs();
        getTVsCompany();

        // Initialize ViewPager2
        viewPagertv = findViewById(R.id.viewPagertv);

     
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Get all the TVs
    private void getTVs(){
        tvRecyclerView.setLayoutManager(new LinearLayoutManager(tv_brandActivity.this));

        datalist.clear();

        db.collection("Products")
                .whereEqualTo("category", "TV")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<ProductModel> data = task.getResult().toObjects(ProductModel.class);
                            datalist.addAll(data);

                            GridLayoutManager layoutManager = new GridLayoutManager(tv_brandActivity.this, 2);
                            tvRecyclerView.setLayoutManager(layoutManager);
                            adapter = new ProductAdapter(tv_brandActivity.this, datalist);
                            tvRecyclerView.setHasFixedSize(true);
                            tvRecyclerView.setAdapter(adapter);
                        }
                    }
                });


    }

    // Get single TVs company data
    private void getCompany(String company){
        tvRecyclerView.setLayoutManager(new LinearLayoutManager(tv_brandActivity.this));

        datalist.clear();

        db.collection("Products")
                .whereEqualTo("category", "TV")
                .whereEqualTo("company", company)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<ProductModel> data = task.getResult().toObjects(ProductModel.class);
                            datalist.addAll(data);

                            GridLayoutManager layoutManager = new GridLayoutManager(tv_brandActivity.this, 2);
                            tvRecyclerView.setLayoutManager(layoutManager);
                            adapter = new ProductAdapter(tv_brandActivity.this, datalist);
                            tvRecyclerView.setHasFixedSize(true);
                            tvRecyclerView.setAdapter(adapter);
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

}
