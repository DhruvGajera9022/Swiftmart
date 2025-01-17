package com.example.swiftmart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swiftmart.Account.Add_Address_Activity;
import com.example.swiftmart.Model.AddressModel;
import com.example.swiftmart.R;
import com.example.swiftmart.Utils.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Context context;
    ArrayList<AddressModel> datalist;
    private int selectedPosition = -1;

    public AddressAdapter(Context context, ArrayList<AddressModel> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddressModel address = datalist.get(position);

        holder.addressRadioButton.setText(address.getFullName() + "\n"
                + address.getPhoneNumber() + "\n"
                + address.getHouseNo() + ", "
                + address.getRoadName() + ", "
                + address.getCity() + ", "
                + address.getState() + "-"
                + address.getPinCode());

        holder.addressRadioButton.setChecked(position == selectedPosition);

        holder.addressRadioButton.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
        });

        holder.popupMenuIcon.setOnClickListener(v -> {
        });
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public AddressModel getSelectedAddress() {
        if (selectedPosition != -1) {
            return datalist.get(selectedPosition);
        }
        return null;
    }

    private void editAddress(String addressID) {
        CustomToast.showToast(context, addressID);

        Intent intent = new Intent(context, Add_Address_Activity.class);
        intent.putExtra("addressId", addressID);
        context.startActivity(intent);
    }


    private void deleteAddress(int position) {
        if (position < 0 || position >= datalist.size()) return;

        AddressModel address = datalist.get(position);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("Users")
                .document(uid)
                .collection("Addresses")
                .document(address.getAddressId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    datalist.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Address deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to delete address", Toast.LENGTH_SHORT).show());
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        RadioButton addressRadioButton;
        ImageView popupMenuIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            addressRadioButton = itemView.findViewById(R.id.addressRadioButton);
            popupMenuIcon = itemView.findViewById(R.id.popupMenuIcon);


        }
    }

}
