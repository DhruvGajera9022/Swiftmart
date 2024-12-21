package com.example.swiftmart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swiftmart.R;

import java.util.List;

public class EarphoneSliderAdapter extends RecyclerView.Adapter<EarphoneSliderAdapter.SliderViewHolder> {

    private List<Integer> imageList1; // List of drawable image resources
    private Context context;

    public EarphoneSliderAdapter(Context context, List<Integer> imageList1) {
        this.context = context;
        this.imageList1 = imageList1;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.earphoneslider_layout, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.imageView1.setImageResource(imageList1.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList1.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.imageView1);
        }
    }
}



//EarphoneSliderAdapter

