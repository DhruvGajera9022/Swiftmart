package com.example.swiftmart.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.swiftmart.Model.ProductModel;
import com.example.swiftmart.R;
import com.example.swiftmart.Utils.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private ArrayList<ProductModel> datalist;
    private int maxQuantity = 10;
    private int minQuantity = 1;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String uid;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String data, boolean isPlus);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

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

            // Format the price
            double unitPrice = Double.parseDouble(product.getPrice());
            NumberFormat currencyFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            holder.cartProductPrice.setText(currencyFormat.format(unitPrice));

            // Handle quantity plus
            holder.cartPlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentQuantity = product.getQty();
                    if (currentQuantity < maxQuantity) {
                        int newQuantity = currentQuantity + 1;
                        product.setQty(newQuantity);

                        // Recalculate the total price for this item
                        double totalPrice = unitPrice * newQuantity;
                        holder.cartProductPrice.setText(currencyFormat.format(totalPrice));

                        if (listener != null) {
                            listener.onItemClick(String.valueOf(totalPrice), true);
                        }
                    } else {
                        CustomToast.showToast(context, "Maximum quantity is 10");
                    }
                }
            });

            // Handle quantity minus
            holder.cartMinusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentQuantity = product.getQty();
                    if (currentQuantity > minQuantity) {
                        int newQuantity = currentQuantity - 1;
                        product.setQty(newQuantity);

                        double totalPrice = unitPrice * newQuantity;
                        holder.cartProductPrice.setText(currencyFormat.format(totalPrice));

                        if (listener != null) {
                            listener.onItemClick(String.valueOf(totalPrice), false);
                        }
                    } else {
                        CustomToast.showToast(context, "Minimum quantity is 1");
                    }
                }
            });

            // handle product delete from cart
            holder.cartTrashButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db = FirebaseFirestore.getInstance();
                    mAuth = FirebaseAuth.getInstance();
                    uid = mAuth.getCurrentUser().getUid();
                    db.collection("Users")
                            .document(uid)
                            .collection("Cart")
                            .document(datalist.get(position).getOid())
                            .get()
                            .addOnSuccessListener(documentSnapshot -> {
                                if (documentSnapshot.exists()) {
                                    documentSnapshot.getReference().delete();
                                } else {
                                    Log.d("Firestore", "Document does not exist.");
                                }
                            });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartImage;
        TextView cartProductName, cartProductPrice, cartProductQuantity;
        ImageButton cartPlusButton, cartMinusButton, cartTrashButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            cartImage = itemView.findViewById(R.id.cartActivityImage);
            cartProductName = itemView.findViewById(R.id.cartProductName);
            cartProductPrice = itemView.findViewById(R.id.cartProductPrice);
            cartProductQuantity = itemView.findViewById(R.id.cartProductQuantity);
            cartPlusButton = itemView.findViewById(R.id.cartPlusButton);
            cartMinusButton = itemView.findViewById(R.id.cartMinusButton);
            cartTrashButton = itemView.findViewById(R.id.cartTrashButton);
        }
    }
}

