package com.example.swiftmart.Frgments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.swiftmart.Address_Activity;
import com.example.swiftmart.Edit_profile_Activity;
import com.example.swiftmart.Language_Activity;
import com.example.swiftmart.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;


public class AccountFragment extends Fragment {
    private ImageButton btnEditProfile, btnLanguage, btnOrderHistory, wishlist, btnAboutUs, btnPrivacyPolicy, btnDeleteUser, profileRateUsBtn,addressBtn;
    private AppCompatButton btnLogout;
    private LinearLayout llwishlist, llEditProfile, llOrderHistory, llAboutUs, llPrivacyPolicy,llLanguage, llDeleteUser, llRateUs,llsavedaddress;
    private TextView accountFragmentUserName;
    private String uid;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public AccountFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        accountFragmentUserName = view.findViewById(R.id.accountFragmentUserName);

        llwishlist = view.findViewById(R.id.llwishlist);
        llEditProfile = view.findViewById(R.id.llEditProfile);
        llOrderHistory = view.findViewById(R.id.llOrderHistory);
        llAboutUs = view.findViewById(R.id.llAboutUs);
        llPrivacyPolicy = view.findViewById(R.id.llPrivacyPolicy);
        llLanguage = view.findViewById(R.id.llLanguage);
        llDeleteUser = view.findViewById(R.id.llDeleteUser);
        llRateUs = view.findViewById(R.id.llRateUs);
        llsavedaddress = view.findViewById(R.id.llsavedaddress);

        btnEditProfile = view.findViewById(R.id.profileEditProfileBtn);
        btnLanguage = view.findViewById(R.id.profileSelectLanguageBtn);
        btnOrderHistory = view.findViewById(R.id.profileOrderHistoryBtn);
        wishlist = view.findViewById(R.id.wishlist);
        btnAboutUs = view.findViewById(R.id.profileAboutUsBtn);
        btnPrivacyPolicy = view.findViewById(R.id.profilePrivacyPolicyBtn);
        btnDeleteUser = view.findViewById(R.id.profileDeleteUserBtn);
        profileRateUsBtn = view.findViewById(R.id.profileRateUsBtn);
        addressBtn = view.findViewById(R.id.addressBtn);

        btnLogout = view.findViewById(R.id.userLogout);

        getUserData();

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Edit_profile_Activity.class);
                startActivity(intent);
            }
        });

        llEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Edit_profile_Activity.class);
                startActivity(intent);
            }
        });

        llsavedaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Address_Activity.class);
                startActivity(intent);
            }
        });

        addressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Address_Activity.class);
                startActivity(intent);
            }
        });

        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Language_Activity.class);
                startActivity(intent);
            }
        });

        llLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Language_Activity.class);
                startActivity(intent);
            }
        });

        handleOnBackPress();

        return view;
    }

    // Get the user data from the database
    private void getUserData(){
        uid = mAuth.getCurrentUser().getUid();
        DocumentReference reference = db.collection("Users").document(uid);

        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()){
                    accountFragmentUserName.append(value.getString("Username").split(" ")[0]);
                }
            }
        });

    }

    // handle onBack press
    private void handleOnBackPress(){
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new HomeFragment())
                        .commit();

//                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
//                bottomNavigationView.setSelectedItemId(R.id.home);
            }
        });
    }

}