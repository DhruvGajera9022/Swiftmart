package com.example.swiftmart;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.Utils.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderTrackingActivity extends AppCompatActivity {
    private ImageView backOrderTracking, productImage;
    private TextView trackOrderName, trackOrderCompany, trackOrderQty, productDetailsProductPrice, trackOrderID;
    private TextView addressFullName, addressText, addressState, addressNumber;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String uid, orderID, addressID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        initialization();
        getOrderProductDetails();
        getOrderAddressDetails();

    }

    private void initialization(){
        backOrderTracking = findViewById(R.id.backOrderTracking);
        productImage = findViewById(R.id.productImage);
        trackOrderName = findViewById(R.id.trackOrderName);
        trackOrderCompany = findViewById(R.id.trackOrderCompany);
        trackOrderQty = findViewById(R.id.trackOrderQty);
        productDetailsProductPrice = findViewById(R.id.productDetailsProductPrice);
        trackOrderID = findViewById(R.id.trackOrderID);

        addressFullName = findViewById(R.id.addressFullName);
        addressText = findViewById(R.id.addressText);
        addressState = findViewById(R.id.addressState);
        addressNumber = findViewById(R.id.addressNumber);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        orderID = getIntent().getStringExtra("orderID");

        backOrderTracking.setOnClickListener(v -> onBackPressed());
    }

    private void getOrderProductDetails(){
        DocumentReference reference = db.collection("Orders").document(orderID);

        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()){
                    trackOrderName.setText(value.getString("name"));
                    trackOrderCompany.setText(value.getString("company"));
                    trackOrderQty.setText(value.getString("quantity"));
                    productDetailsProductPrice.setText(value.getString("totalAmount"));
                    trackOrderID.setText(value.getString("oid"));
                    addressID = value.getString("aid");

                    ArrayList<String> imgUrls = (ArrayList<String>) value.get("imgurls");
                    if (imgUrls != null && !imgUrls.isEmpty()) {
                        Picasso.get().load(imgUrls.get(0)).into(productImage);
                    }
                }
            }
        });
    }

    private void getOrderAddressDetails(){

        if (addressID == null || addressID.isEmpty()) {
            Log.e("OrderTrackingActivity", "Address ID is null or empty");
            return;
        }
        DocumentReference reference = db.collection("Users").document(uid).collection("Addresses").document(addressID);

        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()){
                    addressFullName.setText(value.getString("fullName"));
                    addressText.setText(value.getString("houseNo") + ", " + value.getString("roadName") + ", ");
                    addressState.setText(value.getString("city") + ", " + value.getString("state") + " - " + value.getString("pinCode"));
                    addressNumber.setText(value.getString("phoneNumber"));
                }
            }
        });
    }

}