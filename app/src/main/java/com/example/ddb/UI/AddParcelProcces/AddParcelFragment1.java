package com.example.ddb.UI.AddParcelProcces;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.ddb.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddParcelFragment1 extends DataGetterFragment {

    static private String RecipientPhone;
    private EditText recipient_phone_et;

    public AddParcelFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_parcel_fragment1, container, false);

        // fill the text view with the phone of the last time the fragment created
        recipient_phone_et = view.findViewById(R.id.recipient_phone_et);
        recipient_phone_et.setText(RecipientPhone);

        // when the phone number changing, check its good
        recipient_phone_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
// validate the phone number using regex

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!recipient_phone_et.getText().toString().matches("(((05)|(\\+?(9725)))[0-9]{8})")) {
                    recipient_phone_et.setTextColor(Color.RED);
                }
                else
                    recipient_phone_et.setTextColor(Color.BLACK);
                RecipientPhone = recipient_phone_et.getText().toString();
            }
        });


        return view;
    }

    @Override
    public HashMap<String, Object> getData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("RecipientPhone", RecipientPhone);
        RecipientPhone = "";
        return hashMap;
    }
}
