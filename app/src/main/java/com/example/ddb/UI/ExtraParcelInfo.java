package com.example.ddb.UI;


import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.print.PrintHelper;

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
    ImageView qrcode;
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
        qrcode = view.findViewById(R.id.qr_code_iv);
        relativeLayout = view.findViewById(R.id.extra_parcel_rl);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout.setVisibility(View.GONE);
            }
        });
        view.bringToFront();

        ImageView QRCodeView = view.findViewById(R.id.qr_code_iv);
        QRCodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setMessage(R.string.empty_fields)
                        .setPositiveButton("הדפס", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                printQRCode();
                            }
                        })
                        .setNegativeButton("שתף", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                shareQRCode();
                            }
                        })
                        .show();
            }
        });

        return view;
    }

    private void printQRCode() {
        // Print the QR code
        PrintHelper photoPrinter = new PrintHelper(getActivity());
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.add_pacel_done);
        photoPrinter.printBitmap("droids.jpg - test print", bitmap);
    }

    private void shareQRCode() {
        // Share the QR code as image
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getContext().getResources().getResourcePackageName(R.drawable.add_pacel_done) +
                '/' + getContext().getResources().getResourceTypeName(R.drawable.add_pacel_done) +
                '/' + getContext().getResources().getResourceEntryName(R.drawable.add_pacel_done));

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "Choose way to share"));
    }

    public void fillView(Parcel parcel) {
        for (User user:Users.getUsersList()) {
            if(parcel.getRecipientPhone().equals(user.getUserID())) {
                person = (Person) user;
                break;
            }
        }
        relativeLayout.setVisibility(View.VISIBLE);
        //qrcode.s(parcel.getQRCode());
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
