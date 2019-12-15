package com.example.ddb.UI;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.print.PrintHelper;

import com.example.ddb.Data.Action;
import com.example.ddb.Data.Parcel_dataSource_Maneger.RegisteredPackagesDS;
import com.example.ddb.Data.Users;
import com.example.ddb.Entities.Address;
import com.example.ddb.Entities.Parcel;
import com.example.ddb.Entities.Person;
import com.example.ddb.Entities.User;
import com.example.ddb.R;

import net.glxn.qrgen.android.QRCode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

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
    Button deleteParcel;
    ImageView qrcode;
    Person person = new Person("+99999999","123", Calendar.getInstance(),new Address(),"ישראל","ישראלי");
    Parcel currentParcel;

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
        deleteParcel =view.findViewById(R.id.delete_parcel_btn);
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
                        .setMessage(R.string.share_or_print)
                        .setPositiveButton(R.string.print, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                printQRCode();
                            }
                        })
                        .setNegativeButton(R.string.share, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                shareQRCode();
                            }
                        })
                        .show();
            }
        });

        deleteParcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setMessage(R.string.delete_parcel)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                RegisteredPackagesDS.removeParcel(currentParcel, new Action<String>() {
                                    @Override
                                    public void onSuccess(String obj) {
                                        relativeLayout.setVisibility(View.GONE);
                                        MainScreenCompany.setParcels(RegisteredPackagesDS.getParcelList());
                                    }

                                    @Override
                                    public void onFailure(Exception exception) {

                                    }

                                    @Override
                                    public void onProgress(String status, double percent) {

                                    }
                                });
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                        .show();
            }
        });

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        return view;
    }

    private void printQRCode() {
        // Print the QR code
        PrintHelper photoPrinter = new PrintHelper(getActivity());
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = QRCode.from(currentParcel.getParcelID()).bitmap();
        photoPrinter.printBitmap("DDB " + currentParcel.getParcelID() + " QR code", bitmap);
    }

    private void shareQRCode() {
        // Share the QR code as image
        try {
            File file = new File(getContext().getCacheDir() + "/Image.png");
            Bitmap bitmap = QRCode.from(currentParcel.getParcelID()).bitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
            Uri uri = FileProvider.getUriForFile(getContext(), "com.example.ddb.fileprovider", file);

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, getContext().getResources().getString(R.string.share_to)));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void fillView(Parcel parcel) {
        currentParcel = parcel;
        for (User user:Users.getUsersList()) {
            if(parcel.getRecipientPhone().equals(user.getUserID())) {
                person = (Person) user;
                break;
            }
        }
        relativeLayout.setVisibility(View.VISIBLE);
        qrcode.setImageBitmap(QRCode.from(parcel.getParcelID()).bitmap());
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
