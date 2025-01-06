package com.example.swiftmart.Frgments;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

    LinearLayout mobiles, earbuds, tv, laptop, headphone, speaker, keyword, mouse, camera, smartwatch, tablet;
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
    SwipeRefreshLayout homeFragmentSwipeRefresh;

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
        laptop = view.findViewById(R.id.laptop);
        headphone = view.findViewById(R.id.headphone);
        speaker = view.findViewById(R.id.speaker);
        keyword = view.findViewById(R.id.keyword);
        mouse = view.findViewById(R.id.mouse);
        camera = view.findViewById(R.id.camera);
        smartwatch = view.findViewById(R.id.smartwatch);
        tablet = view.findViewById(R.id.tablet);
        homeFragmentUserAvatar = view.findViewById(R.id.homeFragmentUserAvatar);
        homeFragmentUserName = view.findViewById(R.id.homeFragmentUserName);
        homeFragmentSwipeRefresh = view.findViewById(R.id.homeFragmentSwipeRefresh);

        homeFragmentScrollView.setVerticalScrollBarEnabled(false);
        homeFragmentHorizontalScrollView.setHorizontalScrollBarEnabled(false);


        getUserData();
        getAllProducts();
        swipeRefresh();

        handleMobileClick();
        handleEarbudsClick();
        handleTVClick();
        handleLaptopClick();

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
                    homeFragmentUserName.setText(value.getString("Username"));
                    Picasso.get().load(value.getString("Image")).into(homeFragmentUserAvatar);
                }
            }
        });

    }

    // Get all the products from the database
    private void getAllProducts(){
        homeFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        datalist.clear();

        db.collection("Products")
                .limit(10)
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
                            homeFragmentRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        }
                    }
                });
    }

    // Swipe refresh layout
    private void swipeRefresh(){
        homeFragmentSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllProducts();

                homeFragmentSwipeRefresh.setRefreshing(false);
            }
        });
    }

    // handle mobile click
    private void handleMobileClick(){
        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mobiles = new Intent(getActivity(), MobilesActivity.class);
                startActivity(mobiles);
            }
        });
    }

    // handle earbuds click
    private void handleEarbudsClick(){
        earbuds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gadgets = new Intent(getActivity(), EarphoneActivity.class);
                startActivity(gadgets);
            }
        });
    }

    // handle tv click
    private void handleTVClick(){
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gadgets = new Intent(getActivity(), tv_brandActivity.class);
                startActivity(gadgets);
            }
        });
    }

    // handle laptop click
    private void handleLaptopClick(){
        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gadgets = new Intent(getActivity(), Leptop_Activity.class);
                startActivity(gadgets);
            }
        });
    }

    // TODO handle headphone click
    private void handleHeadphoneClick(){
        headphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // TODO handle speaker click
    private void handleSpeakerClick(){
        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // TODO handle keyword click
    private void handleKeyboardClick(){
        keyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // TODO handle mouse click
    private void handleMouseClick(){
        mouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // TODO handle camera click
    private void handleCameraClick(){
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // TODO handle smartwatch click
    private void handleSmartwatchClick(){
        headphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // TODO handle tablet click
    private void handleTabletClick(){
        tablet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}