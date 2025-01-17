package com.example.swiftmart.Account;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.example.swiftmart.MainActivity;
import com.example.swiftmart.R;
import com.example.swiftmart.Utils.CustomToast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Add_Address_Activity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private RadioGroup rgAddressType;
    private FusedLocationProviderClient fusedLocationClient;
    ImageView backaddnewaddress,cart3;
    private TextInputEditText addAddressFullName, addAddressPhoneNumber, addAddressPincode, addAddressState, addAddressCity, addAddressHouseNo, addAddressRoadName;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String uid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        addAddressFullName = findViewById(R.id.addAddressFullName);
        addAddressPhoneNumber = findViewById(R.id.addAddressPhoneNumber);
        addAddressPincode = findViewById(R.id.addAddressPincode);
        addAddressState = findViewById(R.id.addAddressState);
        addAddressCity = findViewById(R.id.addAddressCity);
        addAddressHouseNo = findViewById(R.id.addAddressHouseNo);
        addAddressRoadName = findViewById(R.id.addAddressRoadName);
        rgAddressType = findViewById(R.id.rgAddressType);
        backaddnewaddress = findViewById(R.id.backaddnewaddress);
        cart3 = findViewById(R.id.cart3);


        Button btnUseMyLocation = findViewById(R.id.btnUseMyLocation);
        AppCompatButton btnSaveAddress = findViewById(R.id.btnSaveAddress);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Handle "Use my location" button
        btnUseMyLocation.setOnClickListener(v -> requestLocationPermission());

        // Handle "Save Address" button
        btnSaveAddress.setOnClickListener(v -> {
            if (validateInputs()) {
                addAddress();
            }
        });

        backaddnewaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCartFragment();
            }
        });
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLocation();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                fetchAddressFromLocation(location);
            } else {
                Toast.makeText(this, "Unable to fetch location. Try again.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(this, "Failed to get location: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void fetchAddressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                // Update UI with fetched address details
                addAddressPincode.setText(address.getPostalCode());
                addAddressState.setText(address.getAdminArea());
                addAddressCity.setText(address.getLocality());
                addAddressRoadName.setText(address.getThoroughfare());

                Toast.makeText(this, "Location fetched successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No address found for this location.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error fetching address: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInputs() {
        if (addAddressFullName.getText().toString().isEmpty()) {
            addAddressFullName.setError("Full Name is required");
            return false;
        }
        if (addAddressPhoneNumber.getText().toString().isEmpty()) {
            addAddressPhoneNumber.setError("Phone Number is required");
            return false;
        }
        if (addAddressPincode.getText().toString().isEmpty()) {
            addAddressPincode.setError("Pincode is required");
            return false;
        }
        if (addAddressState.getText().toString().isEmpty()) {
            addAddressState.setError("State is required");
            return false;
        }
        if (addAddressCity.getText().toString().isEmpty()) {
            addAddressCity.setError("City is required");
            return false;
        }
        if (addAddressHouseNo.getText().toString().isEmpty()) {
            addAddressHouseNo.setError("House No. is required");
            return false;
        }
        if (addAddressRoadName.getText().toString().isEmpty()) {
            addAddressRoadName.setError("Road name is required");
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Location permission denied. Cannot fetch location.", Toast.LENGTH_SHORT).show();
            }
        }
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

    private void addAddress(){
        String fullName = addAddressFullName.getText().toString();
        String phoneNumber = addAddressPhoneNumber.getText().toString();
        String pincode = addAddressPincode.getText().toString();
        String state = addAddressState.getText().toString();
        String city = addAddressCity.getText().toString();
        String houseNo = addAddressHouseNo.getText().toString();
        String roadName = addAddressRoadName.getText().toString();

        String addressType = ((RadioButton) findViewById(rgAddressType.getCheckedRadioButtonId())).getText().toString();

        // Create a HashMap to store the address data
        HashMap<String, Object> addressData = new HashMap<>();
        addressData.put("fullName", fullName);
        addressData.put("phoneNumber", phoneNumber);
        addressData.put("houseNo", houseNo);
        addressData.put("roadName", roadName);
        addressData.put("city", city);
        addressData.put("state", state);
        addressData.put("pinCode", pincode);
        addressData.put("addressType", addressType);

        db.collection("Users")
                .document(uid)
                .collection("Addresses")
                .add(addressData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String addressId = documentReference.getId();
                        documentReference.update("AddressId", addressId)
                                .addOnSuccessListener(aVoid -> {
                                    CustomToast.showToast(Add_Address_Activity.this, "Address added");
                                })
                                .addOnFailureListener(e -> {
                                    CustomToast.showToast(Add_Address_Activity.this, "Failed to update addressId");
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        CustomToast.showToast(Add_Address_Activity.this, "Failed to add address");
                    }
                });

    }

}
