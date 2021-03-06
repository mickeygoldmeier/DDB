package com.example.ddb.UI.AddParcelProcces;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ddb.Data.Users;
import com.example.ddb.R;
import com.example.ddb.Utils.DataCheck;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddParcelFragment1 extends DataGetterFragment {

    static private String RecipientPhone;
    private AutoCompleteTextView recipient_phone_atv;
    private String[] phone_numbers;

    public AddParcelFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_parcel_fragment1, container, false);

        // fill the text view with the phone of the last time the fragment created
        //recipient_phone_et = view.findViewById(R.id.recipient_phone_et);

        recipient_phone_atv = view.findViewById(R.id.recipient_phone_et);
        recipient_phone_atv.setText(RecipientPhone);
        phone_numbers = Users.getStringPhoneList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, phone_numbers);
        recipient_phone_atv.setAdapter(adapter);

        // when the phone number changing, check its good

        recipient_phone_atv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!recipient_phone_atv.getText().toString().matches("(((05)|(\\+?(9725)))[0-9]{8})")) {
                    recipient_phone_atv.setTextColor(Color.RED);
                }
                else
                    recipient_phone_atv.setTextColor(Color.BLACK);
                RecipientPhone = recipient_phone_atv.getText().toString();
            }
        });


        return view;
    }

    @Override
    public HashMap<String, Object> getData() throws Exception{
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            RecipientPhone = DataCheck.normalizePhoneNumber(RecipientPhone);
            hashMap.put("RecipientPhone", RecipientPhone);
        }
        catch (Exception e){
            Toast.makeText(getContext(), R.string.wrong_phone_number, Toast.LENGTH_SHORT).show();
        }
        RecipientPhone = "";
        recipient_phone_atv.setText("");
        return hashMap;
    }

    @Override
    public boolean allFieldsFull() {
        try {
            DataCheck.normalizePhoneNumber(recipient_phone_atv.getText().toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
