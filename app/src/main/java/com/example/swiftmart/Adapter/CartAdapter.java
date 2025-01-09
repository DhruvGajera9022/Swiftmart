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
import com.bumptech.glide.request.RequestOptions;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    ArrayList<ProductModel> datalist;

    public CartAdapter(Context context, ArrayList<ProductModel> datalist) {
        this.context = context;
        this.datalist = (datalist != null) ? datalist : new ArrayList<>();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        if (datalist != null && !datalist.isEmpty()) {
            ProductModel product = datalist.get(position);

            if (product.getImgurls() != null && !product.getImgurls().isEmpty()) {
                Glide.with(holder.cartImage.getContext())
                        .load(product.getImgurls().get(0))
                        .apply(new RequestOptions().placeholder(R.drawable.img_animation))
                        .into(holder.cartImage);
            }

            holder.cartProductName.setText(product.getName());
            holder.cartProductPrice.setText(product.getPrice());
        }
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartImage;
        TextView cartProductName, cartProductPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            cartImage = itemView.findViewById(R.id.cartActivityImage);
            cartProductName = itemView.findViewById(R.id.cartProductName);
            cartProductPrice = itemView.findViewById(R.id.cartProductPrice);
        }
    }
}
