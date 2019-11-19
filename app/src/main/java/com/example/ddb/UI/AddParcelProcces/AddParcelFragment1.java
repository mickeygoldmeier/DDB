package com.example.ddb.UI.AddParcelProcces;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.ddb.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddParcelFragment1 extends DataGetterFragment{

    private String RecipientPhone;

    public AddParcelFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_parcel_fragment1, container, false);
    }

    @Override
    public HashMap<String, Object> getData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("RecipientPhone", RecipientPhone);
        return hashMap;
    }

    @Override
    public void saveInternalData() {
        RecipientPhone = "050202020";
    }
}
