package com.example.swiftmart.Account;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swiftmart.Adapter.AddressAdapter;
import com.example.swiftmart.MainActivity;
import com.example.swiftmart.Model.AddressModel;
import com.example.swiftmart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null && !value.isEmpty()){
                            List<AddressModel> data = value.toObjects(AddressModel.class);

                            datalist.clear();
                            datalist.addAll(data);

                            adapter = new AddressAdapter(Address_Activity.this, datalist);
                            addressRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            addressRecyclerView.setHasFixedSize(true);
                            addressRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        }
                    }
                });
    }

}