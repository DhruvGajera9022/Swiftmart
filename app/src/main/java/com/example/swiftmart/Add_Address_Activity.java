package com.example.swiftmart;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Add_Address_Activity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private RadioGroup rgAddressType;
    private FusedLocationProviderClient fusedLocationClient;
    ImageView backaddnewaddress,cart3;
    TextInputEditText etFullName, etPhoneNumber, etPincode, etState, etCity, etHouseNo, etRoadName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        etFullName = findViewById(R.id.etFullName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPincode = findViewById(R.id.etPincode);
        etState = findViewById(R.id.etState);
        etCity = findViewById(R.id.etCity);
        etHouseNo = findViewById(R.id.etHouseNo);
        etRoadName = findViewById(R.id.etRoadName);
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
                String addressType = ((RadioButton) findViewById(rgAddressType.getCheckedRadioButtonId())).getText().toString();
                Toast.makeText(this, "Address saved successfully as " + addressType, Toast.LENGTH_SHORT).show();
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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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
                etPincode.setText(address.getPostalCode());
                etState.setText(address.getAdminArea());
                etCity.setText(address.getLocality());
                etRoadName.setText(address.getThoroughfare());

                Toast.makeText(this, "Location fetched successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No address found for this location.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error fetching address: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInputs() {
        if (etFullName.getText().toString().isEmpty()) {
            etFullName.setError("Full Name is required");
            return false;
        }
        if (etPhoneNumber.getText().toString().isEmpty()) {
            etPhoneNumber.setError("Phone Number is required");
            return false;
        }
        if (etPincode.getText().toString().isEmpty()) {
            etPincode.setError("Pincode is required");
            return false;
        }
        if (etState.getText().toString().isEmpty()) {
            etState.setError("State is required");
            return false;
        }
        if (etCity.getText().toString().isEmpty()) {
            etCity.setError("City is required");
            return false;
        }
        if (etHouseNo.getText().toString().isEmpty()) {
            etHouseNo.setError("House No. is required");
            return false;
        }
        if (etRoadName.getText().toString().isEmpty()) {
            etRoadName.setError("Road name is required");
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
}
