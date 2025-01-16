package com.example.swiftmart.Frgments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swiftmart.Adapter.CartAdapter;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.R;
import com.example.swiftmart.Utils.CustomToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class CartFragment extends Fragment{
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

        adapter = new CartAdapter(getContext(), datalist);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cartRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String data, boolean isPlus) {
                if (isPlus) {
                    updateTotalAdapterPlus(data);
                } else {
                    updateTotalAdapterMinus(data);
                }
            }
        });

    }

    private void getCartData() {
        db.collection("Users").document(uid).collection("Cart").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot value = task.getResult();

                        if (value != null && !value.isEmpty()) {
                            datalist.clear();
                            totalPrice = 0;

                            for (QueryDocumentSnapshot documentSnapshot : value) {
                                ProductModel product = documentSnapshot.toObject(ProductModel.class);
                                String priceString = product.getPrice();
                                if (priceString != null) {
                                    try {
                                        int price = Integer.parseInt(priceString);
                                        totalPrice += price * product.getQty();
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
                            datalist.clear();
                            adapter.notifyDataSetChanged();
                            cartProductTotal.setText("0");
                            cartProductVoucherTotal.setText("0");
                            cartProductDeliveryTotal.setText("0");
                            cartProductFinalTotal.setText("0");
                        }
                    } else {
                        CustomToast.showToast(getContext(), "Error in fetching cart data");
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    CustomToast.showToast(getContext(), "Failed to fetch cart data");
                });
    }

    private void updateTotal() {
        NumberFormat currencyFormat = NumberFormat.getNumberInstance(Locale.getDefault());
//        CustomToast.showToast(getContext(), currencyFormat.format(totalPrice));

        cartProductTotal.setText(currencyFormat.format(totalPrice));
//        cartProductVoucherTotal.setText(currencyFormat.format(totalPrice));
        cartProductDeliveryTotal.setText(currencyFormat.format(deliveryCharges));

        int finalTotal = totalPrice + deliveryCharges;
        cartProductFinalTotal.setText(currencyFormat.format(finalTotal));
    }

    private void updateTotalAdapterPlus(String data) {
        double itemPrice = Double.parseDouble(data);

        totalPrice += itemPrice;

        updateTotal();
    }

    private void updateTotalAdapterMinus(String data) {

        double itemPrice = Double.parseDouble(data);

        if (totalPrice > 0) {
            totalPrice -= itemPrice;
        }

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
