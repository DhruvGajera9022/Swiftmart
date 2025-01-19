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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.swiftmart.Account.Address_Activity;
import com.example.swiftmart.Account.Edit_profile_Activity;
import com.example.swiftmart.Account.Language_Activity;
import com.example.swiftmart.Account.OrdersActivity;
import com.example.swiftmart.Account.WishlistActivity;
import com.example.swiftmart.LoginActivity;
import com.example.swiftmart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class AccountFragment extends Fragment {
    private ImageButton btnEditProfile, btnLanguage, btnOrderHistory, btnWishlist, btnAboutUs, btnPrivacyPolicy, btnDeleteUser, profileRateUsBtn, btnAddress;
    private AppCompatButton btnLogout;
    private LinearLayout llWishlist, llEditProfile, llOrderHistory, llAboutUs, llPrivacyPolicy,llLanguage, llDeleteUser, llRateUs,llsavedaddress;
    private TextView accountFragmentUserName;
    private String uid;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ProgressBar accountFragmentProgressBar;

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

        llWishlist = view.findViewById(R.id.llwishlist);
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
        btnWishlist = view.findViewById(R.id.wishlist);
        btnAboutUs = view.findViewById(R.id.profileAboutUsBtn);
        btnPrivacyPolicy = view.findViewById(R.id.profilePrivacyPolicyBtn);
        btnDeleteUser = view.findViewById(R.id.profileDeleteUserBtn);
        profileRateUsBtn = view.findViewById(R.id.profileRateUsBtn);
        btnAddress = view.findViewById(R.id.addressBtn);

        btnLogout = view.findViewById(R.id.userLogout);
        accountFragmentProgressBar = view.findViewById(R.id.accountFragmentProgressBar);

        getUserData();

        handleEditProfileClick();
        handleOrderHistoryClick();
        handleSelectLanguageClick();
        handleWishlistClick();
        handleSavedAddressClick();
        handleDeleteAccountClick();

        handleAboutUsClick();
        handlePrivacyPolicyClick();
        handleRateUsClick();

        handleOnBackPress();
        handleUserLogout();

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

    // handle edit profile click
    private void handleEditProfileClick(){
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
    }

    // handle order history click
    private void handleOrderHistoryClick(){
        btnOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrdersActivity.class);
                startActivity(intent);
            }
        });

        llOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrdersActivity.class);
                startActivity(intent);
            }
        });
    }

    // handle select language click
    private void handleSelectLanguageClick(){
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
    }

    // handle wishlist click
    private void handleWishlistClick(){
        btnWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WishlistActivity.class);
                startActivity(intent);
            }
        });

        llWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WishlistActivity.class);
                startActivity(intent);
            }
        });
    }

    // handle saved address click
    private void handleSavedAddressClick(){
        llsavedaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Address_Activity.class);
                startActivity(intent);
            }
        });

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Address_Activity.class);
                startActivity(intent);
            }
        });
    }

    // TODO handle delete account click
    private void handleDeleteAccountClick(){

    }

    // TODO handle about us click
    private void handleAboutUsClick(){

    }

    // TODO handle privacy policy click
    private void handlePrivacyPolicyClick(){

    }

    // TODO handle rate us click
    private void handleRateUsClick(){

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

    // handle user logout
    private void handleUserLogout(){
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                if (getContext() != null){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    progress();
                    getActivity().finish();
                }
            }
        });
    }

    // handle progress bar
    public void progress(){
        if (btnLogout.isPressed()){
            btnLogout.setVisibility(View.GONE);
            accountFragmentProgressBar.setVisibility(View.VISIBLE);
        }else {
            btnLogout.setVisibility(View.VISIBLE);
            accountFragmentProgressBar.setVisibility(View.GONE);
        }
    }

}