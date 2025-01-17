package com.example.swiftmart.Account;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swiftmart.Adapter.OrderProductAdapter;
import com.example.swiftmart.Model.OrderModel;
import com.example.swiftmart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    private ImageView backOrders;
    private RecyclerView ordersRecyclerView;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ArrayList<OrderModel> orderList;
    private OrderProductAdapter orderAdapter;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        initialization();
        getOrders();
    }

    private void initialization() {
        backOrders = findViewById(R.id.backOrders);
        ordersRecyclerView = findViewById(R.id.ordersRecyclerView);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Check if the user is authenticated
        if (mAuth.getCurrentUser() != null) {
            uid = mAuth.getCurrentUser().getUid();
        } else {
            // Handle case where user is not logged in
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        orderList = new ArrayList<>();
        orderAdapter = new OrderProductAdapter(this, orderList);

        ordersRecyclerView.setAdapter(orderAdapter);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        backOrders.setOnClickListener(v -> onBackPressed());
    }

    private void getOrders() {
        db.collection("Orders")
                .whereEqualTo("uid", uid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            // Handle errors by logging and showing a toast
                            Toast.makeText(OrdersActivity.this, "Error loading orders", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (value != null && !value.isEmpty()) {
                            orderList.clear();
                            for (QueryDocumentSnapshot documentSnapshot : value) {
                                OrderModel orderModel = documentSnapshot.toObject(OrderModel.class);
                                orderList.add(orderModel);
                            }
                            orderAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
