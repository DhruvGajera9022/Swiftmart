package com.example.swiftmart.Frgments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swiftmart.Adapter.CartAdapter;
import com.example.swiftmart.Adapter.ProductAdapter;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.R;
import com.example.swiftmart.Utils.CustomToast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class CartFragment extends Fragment implements CartAdapter.CartTotalUpdateListener {
    private RecyclerView cartRecyclerView;
    private ArrayList<ProductModel> datalist = new ArrayList<>();
    private CartAdapter adapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String uid;
    private TextView cartProductTotal, cartProductDeliveryTotal, cartProductVoucherTotal, cartProductFinalTotal;
    private int totalPrice = 0;
    private int deliveryCharges = 50;

    public CartFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        initialization(view);
        getCartData();
        handleOnBackPress();

        return view;
    }

    private void initialization(View view) {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        cartProductTotal = view.findViewById(R.id.cartProductTotal);
        cartProductDeliveryTotal = view.findViewById(R.id.cartProductDeliveryTotal);
        cartProductVoucherTotal = view.findViewById(R.id.cartProductVoucherTotal);
        cartProductFinalTotal = view.findViewById(R.id.cartProductFinalTotal);

        adapter = new CartAdapter(getContext(), datalist, this);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cartRecyclerView.setAdapter(adapter);
    }

    private void getCartData() {
        db.collection("Users").document(uid).collection("Cart").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    CustomToast.showToast(getContext(), "Error in fetching cart data");
                    return;
                }

                if (value != null && !value.isEmpty()) {
                    datalist.clear();
                    totalPrice = 0;

                    for (QueryDocumentSnapshot documentSnapshot : value) {
                        ProductModel product = documentSnapshot.toObject(ProductModel.class);
                        String priceString = product.getPrice();
                        if (priceString != null) {
                            try {
                                int price = Integer.parseInt(priceString);
                                totalPrice += price;
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                CustomToast.showToast(getContext(), "Invalid price format detected");
                            }
                        }
                        datalist.add(product);
                    }

                    // Update UI
                    adapter.notifyDataSetChanged();
                    updateTotal();
                } else {
                    CustomToast.showToast(getContext(), "Cart is empty");
                    datalist.clear();
                    adapter.notifyDataSetChanged();
                    updateTotal();
                }
            }
        });
    }

    private void updateTotal() {
        cartProductTotal.setText(String.valueOf(totalPrice));
        cartProductVoucherTotal.setText(String.valueOf(totalPrice));
        cartProductFinalTotal.setText(String.valueOf(totalPrice + deliveryCharges));
    }

    @Override
    public void onCartItemUpdated() {
        updateTotal();
    }

    private void handleOnBackPress() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new HomeFragment())
                        .commit();
            }
        });
    }
}
