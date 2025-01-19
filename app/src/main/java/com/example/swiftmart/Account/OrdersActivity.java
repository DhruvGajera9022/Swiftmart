package com.example.swiftmart.Account;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.swiftmart.Adapter.OrderProductAdapter;
import com.example.swiftmart.Adapter.TabAdapter;
import com.example.swiftmart.Model.OrderModel;
import com.example.swiftmart.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);


        // Initialize views
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);

        // Setup adapter
        tabAdapter = new TabAdapter(OrdersActivity.this);
        viewPager.setAdapter(tabAdapter);

        // Connect TabLayout with ViewPager2
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Active");
                            break;
                        case 1:
                            tab.setText("Completed");
                            break;
                        case 2:
                            tab.setText("Canceled");
                            break;
                    }
                }
        );
        mediator.attach();

    }
}
