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

        holder.addressFullName.setText(address.getFullName());
        holder.addressText.setText(address.getHouseNo() + ", " + address.getRoadName() + ", ");
        holder.addressState.setText(address.getCity() + ", " + address.getState() + " - " + address.getPinCode());
        holder.addressNumber.setText(address.getPhoneNumber());
        holder.addressType.setText(address.getAddressType());

    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView addressFullName, addressText, addressState, addressNumber, addressType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            addressFullName = itemView.findViewById(R.id.addressFullName);
            addressText = itemView.findViewById(R.id.addressText);
            addressState = itemView.findViewById(R.id.addressState);
            addressNumber = itemView.findViewById(R.id.addressNumber);
            addressType = itemView.findViewById(R.id.addressType);
        }
    }

}
