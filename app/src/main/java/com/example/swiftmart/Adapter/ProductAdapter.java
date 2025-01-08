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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.ProductDetailsActivity;
import com.example.swiftmart.R;
import com.example.swiftmart.Utils.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductModel> datalist;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String uid = mAuth.getUid();

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

        holder.wishlistButton.setImageResource(
                product.isWishlisted() ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);

        // Check if the product is in the wishlist using QuerySnapshot
        db.collection("Users")
                        .document(uid)
                                .collection("wishlist")
                                        .whereEqualTo("pid", product.getPid())
                                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                        if (value != null && !value.isEmpty()){
                                                            product.setWishlisted(true);
                                                            holder.wishlistButton.setImageResource(R.drawable.ic_heart_filled);
                                                        }else {
                                                            product.setWishlisted(false);
                                                            holder.wishlistButton.setImageResource(R.drawable.ic_heart_outline);
                                                        }
                                                    }
                                                });


        holder.cardProductLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productId", product.getPid());
                context.startActivity(intent);
            }
        });

        // Handle wishlist button clicks
        holder.wishlistButton.setOnClickListener(v -> {
            boolean isWishlisted = !product.isWishlisted();
            product.setWishlisted(isWishlisted);

            // Update UI
            holder.wishlistButton.setImageResource(
                    isWishlisted ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);

            // Update Firestore
            if (isWishlisted) {
                db.collection("Users")
                        .document(uid)
                        .collection("wishlist")
                        .document(product.getPid())
                        .set(product)
                        .addOnSuccessListener(aVoid ->
                                CustomToast.showToast(context, R.drawable.img_logo, "Added to wishlist!")
                        )
                        .addOnFailureListener(e ->
                                CustomToast.showToast(context, R.drawable.img_logo,"Failed to add to wishlist: ")
                        );
            } else {
                db.collection("Users")
                        .document(uid)
                        .collection("wishlist")
                        .document(product.getPid())
                        .delete()
                        .addOnSuccessListener(aVoid ->
                                CustomToast.showToast(context, R.drawable.img_logo, "Removed from wishlist!")
                        )
                        .addOnFailureListener(e ->
                                CustomToast.showToast(context, R.drawable.img_logo, "Failed to remove from wishlist")
                        );
            }
        });

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
