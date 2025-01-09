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


public class CartFragment extends Fragment {
    private RecyclerView cartRecyclerView;

    private ArrayList<ProductModel> datalist = new ArrayList<>();
    CartAdapter adapter;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String uid;

    public CartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        initialization(view);
        getCartData();
        handleOnBackPress();

        return view;
    }

    private void initialization(View view){

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();


        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
    }

    // fetch cart data
    private void getCartData(){
        datalist.clear();

        db.collection("Users")
                .document(uid)
                .collection("Cart")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            CustomToast.showToast(getContext(), R.drawable.img_logo, "Error in fetching cart data");
                        }

                        if (value != null && !value.isEmpty()){
                            for (QueryDocumentSnapshot documentSnapshot : value){
                                ProductModel product = documentSnapshot.toObject(ProductModel.class);
                                datalist.add(product);
                            }
                            adapter = new CartAdapter(getContext(), datalist);
                            cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            cartRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            cartRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
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