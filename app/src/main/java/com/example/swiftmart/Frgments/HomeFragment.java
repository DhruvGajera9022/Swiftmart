package com.example.swiftmart.Frgments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.swiftmart.Adapter.ProductAdapter;
import com.example.swiftmart.EarphoneActivity;
import com.example.swiftmart.Account.Edit_profile_Activity;
import com.example.swiftmart.Leptop_Activity;
import com.example.swiftmart.MobilesActivity;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.R;
import com.example.swiftmart.tv_brandActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
    NestedScrollView homeFragmentScrollView;
    HorizontalScrollView homeFragmentHorizontalScrollView;
    SwipeRefreshLayout homeFragmentSwipeRefresh;
    BottomSheetDialog sheetDialog;
    ProgressBar homeFragmentProgressBar;

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
        homeFragmentProgressBar = view.findViewById(R.id.homeFragmentProgressBar);

        homeFragmentScrollView.setVerticalScrollBarEnabled(false);
        homeFragmentHorizontalScrollView.setHorizontalScrollBarEnabled(false);


        getUserData();
        handleHomeFragmentUserAvtarClick();
        getAllProducts();
        swipeRefresh();

        handleMobileClick();
        handleEarbudsClick();
        handleTVClick();
        handleLaptopClick();

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showBottomSheetDialog();
            }
        });

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
                    homeFragmentUserName.append(value.getString("Username").split(" ")[0]);
                    Picasso.get().load(value.getString("Image")).into(homeFragmentUserAvatar);
                }
            }
        });

    }

    // Get all the products from the database
    private void getAllProducts(){
        homeFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeFragmentProgressBar.setVisibility(View.VISIBLE);
        datalist.clear();

        db.collection("Products")
                .limit(30)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            homeFragmentProgressBar.setVisibility(View.GONE);
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

    // handle homeFragmentUserAvatar click
    private void handleHomeFragmentUserAvtarClick(){
        homeFragmentUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeFragmentUserAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), Edit_profile_Activity.class);
                        startActivity(intent);
                    }
                });
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


    private void showBottomSheetDialog() {
        sheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialog);
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_dialog,
                (LinearLayout) getView().findViewById(R.id.bottomSheetLinearLayout));

        Button bottomSheetCancelButton = dialogView.findViewById(R.id.bottomSheetCancelButton);
        Button bottomSheetOkayButton = dialogView.findViewById(R.id.bottomSheetOkayButton);

        sheetDialog.setContentView(dialogView);
        sheetDialog.setCancelable(false);
        sheetDialog.show();

        bottomSheetCancelButton.setOnClickListener(v -> sheetDialog.dismiss());

        bottomSheetOkayButton.setOnClickListener(v -> {
            sheetDialog.dismiss();
            requireActivity().finish(); // Close the activity
        });
    }

}