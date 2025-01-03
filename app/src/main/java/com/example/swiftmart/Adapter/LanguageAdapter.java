package com.example.swiftmart.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.swiftmart.R;

import java.util.ArrayList;

public class    LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<LanguageModel> arrlanguage;
    private int selectedPosition = -1; // Initially, no item is selected

    public LanguageAdapter(Context context, ArrayList<LanguageModel> arrlanguage) {
        this.context = context;
        this.arrlanguage = arrlanguage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imageView.setImageResource(arrlanguage.get(position).image);
        holder.textview.setText(arrlanguage.get(position).name);

        // Update UI based on whether the item is selected
        if (position == selectedPosition) {
            holder.relativeLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.lang_bg));
            holder.textview.setTextColor(Color.BLACK);
        } else {
            holder.relativeLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.langu_layout));
            holder.textview.setTextColor(Color.GRAY);
        }

        // Handle item click to update selected position
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged(); // Refresh the list to reflect the change
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrlanguage.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textview;
        RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.counteyimg);
            textview = itemView.findViewById(R.id.counteytext);
            relativeLayout = itemView.findViewById(R.id.language_item_layout); // ID for the outermost RelativeLayout
        }
    }

    public LanguageModel getSelectedLanguage() {
        if (selectedPosition != -1) {
            return arrlanguage.get(selectedPosition);
        }
        return null; // No language selected
    }

}
