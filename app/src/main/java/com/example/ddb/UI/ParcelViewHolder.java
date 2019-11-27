package com.example.ddb.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ddb.Entities.Parcel;
import com.example.ddb.R;

public class ParcelViewHolder extends RecyclerView.ViewHolder  {

    TextView id;
    TextView type;
    TextView fragile;
    TextView weight;
    TextView address;
    TextView recipient;

    public ParcelViewHolder(@NonNull View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.parcel_id_tv);
        type = itemView.findViewById(R.id.parcel_type_tv);
        fragile = itemView.findViewById(R.id.parcel_fragile_tv);
        weight = itemView.findViewById(R.id.parcel_weight_tv);
        address = itemView.findViewById(R.id.parcel_address_tv);
        recipient = itemView.findViewById(R.id.parcel_recipient_tv);
    }

    public void fillView(Parcel parcel)
    {
        id.setText(parcel.getParcelID());
        type.setText(parcel.getType().toString());
        weight.setText(String.valueOf(parcel.getWeight()));
        address.setText(parcel.getDistributionCenterAddress().toString());
        recipient.setText(parcel.getRecipientPhone());
        if(parcel.isFragile())
            fragile.setText(android.R.string.yes);
        else
            fragile.setText(android.R.string.no);
        switch (parcel.getType()){
            case Envelope:
                type.setText(R.string.envelope_);
                break;
            case SmallPackage:
                type.setText(R.string.small_package_);
                break;
            default:
                type.setText(R.string.large_package_);
        }
    }
}
