package com.example.swiftmart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.ProductDetailsActivity;
import com.example.swiftmart.R;
import com.example.swiftmart.Utils.CustomToast;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;

public class AdminCategoryAdapter extends RecyclerView.Adapter<AdminCategoryAdapter.ViewHolder>{

    Context context;
    ArrayList<ProductModel> datalist;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AdminCategoryAdapter(Context context, ArrayList<ProductModel> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public AdminCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_product, parent, false);
        return new AdminCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCategoryAdapter.ViewHolder holder, int position) {
        ProductModel product = datalist.get(position);

        Glide.with(holder.cardProductImage.getContext())
                .load(product.getImgurls().get(0))
                .placeholder(R.drawable.img_animation)
                .into(holder.cardProductImage);
        holder.cardProductName.setText(product.getName());
        holder.cardProductDescription.setText(product.getDescription());
        holder.cardProductPrice.setText("₹" + product.getPrice());

        // Set slide-in animation
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
        holder.itemView.startAnimation(animation);
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cardProductImage;
        TextView cardProductName, cardProductDescription, cardProductPrice;
        LinearLayout cardProductLinearLayout;
        ImageButton wishlistButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardProductLinearLayout = itemView.findViewById(R.id.cardProductLinearLayout);
            cardProductImage = itemView.findViewById(R.id.cardProductImage);
            cardProductName = itemView.findViewById(R.id.cardProductName);
            cardProductDescription = itemView.findViewById(R.id.cardProductDescription);
            cardProductPrice = itemView.findViewById(R.id.cardProductPrice);
            wishlistButton = itemView.findViewById(R.id.wishlistButton);

        }
    }

}
