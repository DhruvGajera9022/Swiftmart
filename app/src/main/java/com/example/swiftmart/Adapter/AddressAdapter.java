package com.example.swiftmart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swiftmart.Model.AddressModel;
import com.example.swiftmart.R;

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
        holder.addressRadioButton.setText(address.getFullName() + "\n"
                + address.getPhoneNumber() + "\n"
                + address.getHouseNo() + ", "
                + address.getRoadName() + ", "
                + address.getCity() + ", "
                + address.getState() + "-"
                + address.getPinCode());
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RadioButton addressRadioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            addressRadioButton = itemView.findViewById(R.id.addressRadioButton);


        }
    }

}
