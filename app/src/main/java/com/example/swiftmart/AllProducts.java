package com.example.swiftmart;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swiftmart.Adapter.AllProductAdapter;
import com.example.swiftmart.Adapter.ProductAdapter;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.Utils.CustomToast;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllProducts extends AppCompatActivity {
    private RecyclerView allProductsRecyclerView;
    private FirebaseFirestore db;
    private ArrayList<ProductModel> datalist = new ArrayList<>();
    private AllProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        db = FirebaseFirestore.getInstance();

        allProductsRecyclerView = findViewById(R.id.allProductsRecyclerView);



        getAllProducts();

    }


    // get all products
    private void getAllProducts(){
        allProductsRecyclerView.setLayoutManager(new LinearLayoutManager(AllProducts.this));

        db.collection("Products")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(AllProducts.this, "Error in data fetching");
                            return;
                        }

                        if (value != null && !value.isEmpty()){
                            datalist.clear();
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                                datalist.add(productModel);

                                GridLayoutManager layoutManager = new GridLayoutManager(AllProducts.this, 2);
                                allProductsRecyclerView.setLayoutManager(layoutManager);
                                adapter = new AllProductAdapter(AllProducts.this, datalist);
                                allProductsRecyclerView.setHasFixedSize(true);
                                allProductsRecyclerView.setAdapter(adapter);
                                allProductsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                    }
                });
    }

}