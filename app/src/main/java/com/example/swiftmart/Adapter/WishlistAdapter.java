package com.example.swiftmart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.ProductDetailsActivity;
import com.example.swiftmart.R;

import java.util.ArrayList;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {
    Context context;
    ArrayList<ProductModel> datalist;

    public WishlistAdapter(Context context, ArrayList<ProductModel> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_product_wishlist, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
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

        holder.wishlistCardProductLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productId", product.getPid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class WishlistViewHolder extends RecyclerView.ViewHolder{
        ImageView cardProductImage;
        TextView cardProductName, cardProductDescription, cardProductPrice;
        LinearLayout wishlistCardProductLinearLayout;

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);

            wishlistCardProductLinearLayout = itemView.findViewById(R.id.wishlistCardProductLinearLayout);
            cardProductImage = itemView.findViewById(R.id.cardProductImage);
            cardProductName = itemView.findViewById(R.id.cardProductName);
            cardProductDescription = itemView.findViewById(R.id.cardProductDescription);
            cardProductPrice = itemView.findViewById(R.id.cardProductPrice);

        }
    }

}
