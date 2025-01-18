package com.example.swiftmart.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swiftmart.Account.Add_Address_Activity;
import com.example.swiftmart.Model.AddressModel;
import com.example.swiftmart.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Context context;
    ArrayList<AddressModel> datalist;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String uid;

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

        holder.addressLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Add_Address_Activity.class);
                intent.putExtra("addressID", address.getAid());
                context.startActivity(intent);
            }
        });

        holder.addressLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder deletedialog = new AlertDialog.Builder(context);
                deletedialog.setCancelable(false)
                        .setMessage("Are you sure to delete this ?")
                        .setTitle("Delete")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db = FirebaseFirestore.getInstance();
                                mAuth = FirebaseAuth.getInstance();
                                uid = mAuth.getCurrentUser().getUid();

                                db.collection("Users")
                                        .document(uid)
                                        .collection("Addresses")
                                        .document(datalist.get(position).getAid())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dialog.dismiss();
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                deletedialog.show();
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView addressFullName, addressText, addressState, addressNumber, addressType;
        LinearLayout addressLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            addressFullName = itemView.findViewById(R.id.addressFullName);
            addressText = itemView.findViewById(R.id.addressText);
            addressState = itemView.findViewById(R.id.addressState);
            addressNumber = itemView.findViewById(R.id.addressNumber);
            addressType = itemView.findViewById(R.id.addressType);

            addressLinearLayout = itemView.findViewById(R.id.addressLinearLayout);
        }
    }

}
