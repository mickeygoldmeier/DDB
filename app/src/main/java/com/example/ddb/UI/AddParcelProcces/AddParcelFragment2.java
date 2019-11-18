package com.example.ddb.UI.AddParcelProcces;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ddb.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddParcelFragment2 extends Fragment implements GetDataInterface {


    public AddParcelFragment2() {
        // Required empty public constructor
    }

    private final boolean[] fragile = {true};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_parcel_fragment2, container, false);

        Spinner parcel_type_spr = view.findViewById(R.id.parcel_type_spr);
        final String[] type = {getString(R.string.envelope),
                getString(R.string.small_package),
                getString(R.string.large_package)};
        parcel_type_spr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), type[i], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter aa1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, type);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parcel_type_spr.setAdapter(aa1);


        Spinner fragile_spr = view.findViewById(R.id.fragile_spr);
        final boolean[] fragileType = {true, false};
        final String[] fragileText = {"זה שביר", "זה לא שביר"};
        parcel_type_spr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fragile[0] = fragileType[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter aa2 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, fragileText);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fragile_spr.setAdapter(aa2);

        return view;
    }

    @Override
    public HashMap<String, Object> getData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Fragile", fragile[0]);
        return hashMap;
    }
}
