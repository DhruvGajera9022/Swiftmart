package com.example.swiftmart.Frgments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.swiftmart.EarbudsActivity;
import com.example.swiftmart.EarphoneActivity;
import com.example.swiftmart.MobilesActivity;
import com.example.swiftmart.R;
import com.example.swiftmart.tv_brandActivity;

public class HomeFragment extends Fragment {

    LinearLayout mobiles,earbuds,tv,leptop,headphone,speaker,keyword,mouse,camero,smartwatch,tablet;

    public HomeFragment() {

    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

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



        return view;
    }
}