package com.example.swiftmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swiftmart.Adapter.AddressAdapter;
import com.example.swiftmart.Adapter.ProductAdapter;
import com.example.swiftmart.Model.AddressModel;
import com.example.swiftmart.Model.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Address_Activity extends AppCompatActivity {

    private ImageView backaddress,cart2;
    private LinearLayout addnewadr;
    private RecyclerView addressRecyclerView;
    private ArrayList<AddressModel> datalist = new ArrayList<>();
    private AddressAdapter adapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String uid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        addressRecyclerView = findViewById(R.id.addressRecyclerView);
        backaddress=findViewById(R.id.backaddress);
        cart2=findViewById(R.id.cart2);
        addnewadr=findViewById(R.id.addnewadr);

        getAllAddress();

        backaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCartFragment();
            }
        });

        addnewadr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Address_Activity.this, Add_Address_Activity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void navigateToCartFragment() {
        // Navigate to MainActivity and pass data to show CartFragment
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("showFragment", "cart");
        startActivity(intent);
        finish(); // Close the current activity if necessary
    }

    private void getAllAddress() {
        if (datalist == null) {
            datalist = new ArrayList<>();
        }

        addressRecyclerView.setLayoutManager(new LinearLayoutManager(Address_Activity.this));

        db.collection("Users")
                .document(uid)
                .collection("Addresses")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<AddressModel> data = task.getResult().toObjects(AddressModel.class);

                            datalist.clear();
                            datalist.addAll(data);

                            adapter = new AddressAdapter(Address_Activity.this, datalist);
                            addressRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            addressRecyclerView.setHasFixedSize(true);
                            addressRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        } else {
                            Toast.makeText(Address_Activity.this, "Failed to fetch addresses", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Address_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }




}