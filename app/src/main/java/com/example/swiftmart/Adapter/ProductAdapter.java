package com.example.swiftmart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductModel> datalist;

    public ProductAdapter(Context context, ArrayList<ProductModel> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.cardProductImage.getContext())
                .load(datalist.get(position).getImgurls().get(0))
                .into(holder.cardProductImage);
        holder.cardProductName.setText(datalist.get(position).getName());
        holder.cardProductDescription.setText(datalist.get(position).getDescription());
        holder.cardProductPrice.setText(datalist.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cardProductImage;
        TextView cardProductName, cardProductDescription, cardProductPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardProductImage = itemView.findViewById(R.id.cardProductImage);
            cardProductName = itemView.findViewById(R.id.cardProductName);
            cardProductDescription = itemView.findViewById(R.id.cardProductDescription);
            cardProductPrice = itemView.findViewById(R.id.cardProductPrice);

        }
    }

}
