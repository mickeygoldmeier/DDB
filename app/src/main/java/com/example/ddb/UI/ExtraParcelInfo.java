package com.example.ddb.UI;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.ddb.Data.Users;
import com.example.ddb.Entities.Address;
import com.example.ddb.Entities.Parcel;
import com.example.ddb.Entities.Person;
import com.example.ddb.Entities.User;
import com.example.ddb.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExtraParcelInfo extends Fragment {

    TextView id;
    TextView type;
    TextView fragile;
    TextView weight;
    TextView address;
    TextView recipient;
    TextView recipient_name;
    RelativeLayout relativeLayout;
    Person person = new Person("+99999999","123", Calendar.getInstance(),new Address(),"ישראל","ישראלי");
    public ExtraParcelInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_extra_parcel_info, container, false);

        id = view.findViewById(R.id.parcel_id_tv);
        type = view.findViewById(R.id.parcel_type_tv);
        fragile = view.findViewById(R.id.parcel_fragile_tv);
        weight = view.findViewById(R.id.parcel_weight_tv);
        address = view.findViewById(R.id.parcel_address_tv);
        recipient = view.findViewById(R.id.parcel_recipient_tv);
        recipient_name = view.findViewById(R.id.parcel_recipient_name_tv);
        relativeLayout = view.findViewById(R.id.extra_parcel_rl);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout.setVisibility(View.GONE);
            }
        });
        view.bringToFront();

        return view;
    }

    public void fillView(Parcel parcel) {
        for (User user:Users.getUsersList()) {
            if(parcel.getRecipientPhone().equals(user.getUserID())) {
                person = (Person) user;
                break;
            }
        }
        relativeLayout.setVisibility(View.VISIBLE);
        id.setText(parcel.getParcelID());
        type.setText(parcel.getType().toString());
        weight.setText(String.valueOf(parcel.getWeight()));
        address.setText(parcel.getDistributionCenterAddress().toString());
        recipient.setText(parcel.getRecipientPhone());
        recipient_name.setText(person.getFirstName() + " " + person.getLastName());
        if (parcel.isFragile())
            fragile.setText(android.R.string.yes);
        else
            fragile.setText(android.R.string.no);
        switch (parcel.getType()) {
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
