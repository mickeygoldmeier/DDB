package com.example.ddb.UI;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddb.Entities.Parcel;
import com.example.ddb.R;

public class ParcelViewHolder extends RecyclerView.ViewHolder {

    TextView id;
    TextView type;
    TextView recipient;
    Parcel parcel;
    ExtraParcelInfo fragmentActivity;
    CardView cardView;

    public ParcelViewHolder(@NonNull View itemView, ExtraParcelInfo extraParcelInfo) {
        super(itemView);
        fragmentActivity = extraParcelInfo;

        id = itemView.findViewById(R.id.parcel_id_tv);
        type = itemView.findViewById(R.id.parcel_type_tv);
        recipient = itemView.findViewById(R.id.parcel_recipient_tv);

        cardView = itemView.findViewById(R.id.parcel_view_cv);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentActivity.fillView(parcel);
            }
        });
    }

    public void fillView(Parcel _parcel)
    {
        parcel = _parcel;
        id.setText(parcel.getParcelID());
        type.setText(parcel.getType().toString());
        recipient.setText(parcel.getRecipientPhone());
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
