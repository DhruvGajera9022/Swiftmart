package com.example.swiftmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.swiftmart.Account.Add_Address_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class ConfirmAddressActivity extends AppCompatActivity {
    private TextView confirmAddressAddNew, confirmAddressType, confirmAddressFullName, confirmAddressText, confirmAddressState, confirmAddressNumber, confirmAddressEdit;
    private AppCompatButton confirmAddressDeliver;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String uid, addressID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_address);

        initialization();
        getAddress();
        handleNewClick();
        handleEditClick();
        handleDeliverClick();

    }

    private void initialization(){
        confirmAddressAddNew = findViewById(R.id.confirmAddressAddNew);
        confirmAddressType = findViewById(R.id.confirmAddressType);
        confirmAddressFullName = findViewById(R.id.confirmAddressFullName);
        confirmAddressText = findViewById(R.id.confirmAddressText);
        confirmAddressState = findViewById(R.id.confirmAddressState);
        confirmAddressNumber = findViewById(R.id.confirmAddressNumber);
        confirmAddressEdit = findViewById(R.id.confirmAddressEdit);

        confirmAddressDeliver = findViewById(R.id.confirmAddressDeliver);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
    }

    private void getAddress() {
        db.collection("Users").document(uid).collection("Addresses")
                .whereEqualTo("isDefault", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }

                        if (snapshots != null && !snapshots.isEmpty()) {
                            for (QueryDocumentSnapshot document : snapshots) {
                                confirmAddressType.setText(document.getString("addressType"));
                                confirmAddressFullName.setText(document.getString("fullName"));
                                confirmAddressText.setText(document.getString("houseNo") + ", " + document.getString("roadName") + ", ");
                                confirmAddressState.setText(document.getString("city") + ", " + document.getString("state") + " - " + document.getString("pinCode"));
                                confirmAddressNumber.setText(document.getString("phoneNumber"));
                                addressID = document.getString("aid");
                            }
                        }
                    }
                });
    }

    private void handleNewClick(){
        confirmAddressAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmAddressActivity.this, Add_Address_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void handleEditClick(){
        confirmAddressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmAddressActivity.this, Add_Address_Activity.class);
                intent.putExtra("addressID", addressID);
                startActivity(intent);
            }
        });
    }

    private void handleDeliverClick(){
        confirmAddressDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmAddressActivity.this, PaymentActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

}