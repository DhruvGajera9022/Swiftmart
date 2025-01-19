package com.example.swiftmart.Frgments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swiftmart.R;

public class TabFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        emptyView = view.findViewById(R.id.emptyView);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get tab position from arguments
        int position = getArguments().getInt("tab_position", 0);

        // Set empty view text based on tab
        switch (position) {
            case 0:
                emptyView.setText("No active items");
                // Load active items data
                break;
            case 1:
                emptyView.setText("No completed items");
                // Load completed items data
                break;
            case 2:
                emptyView.setText("No canceled items");
                // Load canceled items data
                break;
        }
    }
}