package com.example.ddb.UI.AddParcelProcces;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddb.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddParcelFragment3 extends DataGetterFragment{

    public AddParcelFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_parcel_fragment3, container, false);
    }

    @Override
    public HashMap<String, Object> getData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Weight", 3.2);
        return hashMap;
    }

    @Override
    public void saveInternalData() {

    }
}
