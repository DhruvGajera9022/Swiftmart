package com.example.swiftmart.Frgments;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swiftmart.Adapter.ProductAdapter;
import com.example.swiftmart.EarbudsActivity;
import com.example.swiftmart.EarphoneActivity;
import com.example.swiftmart.Leptop_Activity;
import com.example.swiftmart.MobilesActivity;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.R;
import com.example.swiftmart.tv_brandActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    LinearLayout mobiles, earbuds, tv, leptop, headphone, speaker, keyword, mouse, camero, smartwatch, tablet;
    FirebaseFirestore db;
    ArrayList<ProductModel> datalist = new ArrayList<>();
    RecyclerView homeFragmentRecyclerView;
    ProductAdapter adapter;
    CircleImageView homeFragmentUserAvatar;
    TextView homeFragmentUserName;
    FirebaseAuth mAuth;
    String uid;
    ScrollView homeFragmentScrollView;
    HorizontalScrollView homeFragmentHorizontalScrollView;

    public HomeFragment() {

    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        homeFragmentRecyclerView = view.findViewById(R.id.homeFragmentRecyclerView);
        homeFragmentScrollView = view.findViewById(R.id.homeFragmentScrollView);
        homeFragmentHorizontalScrollView = view.findViewById(R.id.homeFragmentHorizontalScrollView);

        mobiles = view.findViewById(R.id.mobiles);
        earbuds = view.findViewById(R.id.earbuds);
        tv = view.findViewById(R.id.tv);
        leptop = view.findViewById(R.id.leptop);
        headphone = view.findViewById(R.id.headphone);
        speaker = view.findViewById(R.id.speaker);
        keyword = view.findViewById(R.id.keyword);
        mouse = view.findViewById(R.id.mouse);
        camero = view.findViewById(R.id.camero);
        smartwatch = view.findViewById(R.id.smartwatch);
        tablet = view.findViewById(R.id.tablet);
        homeFragmentUserAvatar = view.findViewById(R.id.homeFragmentUserAvatar);
        homeFragmentUserName = view.findViewById(R.id.homeFragmentUserName);

        homeFragmentScrollView.setVerticalScrollBarEnabled(false);
        homeFragmentHorizontalScrollView.setHorizontalScrollBarEnabled(false);

        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mobiles = new Intent(getActivity(), MobilesActivity.class);
                startActivity(mobiles);
            }
        });
        earbuds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gadgets = new Intent(getActivity(), EarphoneActivity.class);
                startActivity(gadgets);
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gadgets = new Intent(getActivity(), tv_brandActivity.class);
                startActivity(gadgets);
            }
        });
        leptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gadgets = new Intent(getActivity(), Leptop_Activity.class);
                startActivity(gadgets);
            }
        });

        getAllProducts();
        getUserData();

        return view;
    }

    private void getAllProducts(){
        homeFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        datalist.clear();

        db.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<ProductModel> data = task.getResult().toObjects(ProductModel.class);
                            datalist.addAll(data);

                            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                            homeFragmentRecyclerView.setLayoutManager(layoutManager);
                            adapter = new ProductAdapter(getContext(), datalist);
                            homeFragmentRecyclerView.setHasFixedSize(true);
                            homeFragmentRecyclerView.setAdapter(adapter);
                        }
                    }
                });
    }

    private void getUserData(){
        uid = mAuth.getCurrentUser().getUid();
        DocumentReference reference = db.collection("Users").document(uid);

        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()){
                    homeFragmentUserName.setText(value.getString("Username"));
                    Picasso.get().load(value.getString("Image")).into(homeFragmentUserAvatar);
                }
            }
        });

    }

}