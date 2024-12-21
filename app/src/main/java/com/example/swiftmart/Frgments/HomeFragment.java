package com.example.swiftmart.Frgments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.swiftmart.GadgetActivity;
import com.example.swiftmart.MobilesActivity;
import com.example.swiftmart.R;

public class HomeFragment extends Fragment {

    LinearLayout mobiles,gadgets,electronics,tv;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mobiles = view.findViewById(R.id.mobiles);
        gadgets = view.findViewById(R.id.gadgets);
        electronics = view.findViewById(R.id.electronics);
        tv = view.findViewById(R.id.tv);

        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mobiles = new Intent(getActivity(), MobilesActivity.class);
                startActivity(mobiles);
            }
        });
        gadgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gadgets = new Intent(getActivity(), GadgetActivity.class);
                startActivity(gadgets);
            }
        });

        return view;
    }
}