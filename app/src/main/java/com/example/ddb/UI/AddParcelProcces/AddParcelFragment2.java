package com.example.ddb.UI.AddParcelProcces;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ddb.Entities.Parcel_Type;
import com.example.ddb.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddParcelFragment2 extends DataGetterFragment {

    private static double Weight;
    private static Parcel_Type Type;
    private static boolean Fragile;

    private Spinner parcel_type_spr;
    private Spinner fragile_spr;
    private EditText parcel_weight_et;

    public AddParcelFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_parcel_fragment2, container, false);

        // set up the type spinner
        parcel_type_spr = view.findViewById(R.id.parcel_type_spr);
        final String[] type = {getString(R.string.envelope),
                getString(R.string.small_package),
                getString(R.string.large_package)};
        ArrayAdapter aa1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, type);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parcel_type_spr.setAdapter(aa1);

        // set up the fragile spinner
        fragile_spr = view.findViewById(R.id.fragile_spr);
        final boolean[] fragileType = {true, false};
        final String[] fragileText = {"זה שביר", "זה לא שביר"};
        fragile_spr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Fragile = fragileType[i];
                Toast.makeText(getContext(), String.valueOf(Fragile), Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter aa2 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, fragileText);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fragile_spr.setAdapter(aa2);

        // set up the weight edit text
        parcel_weight_et = view.findViewById(R.id.parcel_weight_et);
        parcel_weight_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    Weight = Double.parseDouble(editable.toString());
                    parcel_weight_et.setTextColor(Color.BLACK);
                } catch (Exception e) {
                    parcel_weight_et.setTextColor(Color.RED);
                }
            }
        });

        return view;
    }

    @Override
    public HashMap<String, Object> getData() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();

        switch ((int) parcel_type_spr.getSelectedItemId()) {
            case 1:
                Type = Parcel_Type.SmallPackage;
                break;
            case 2:
                Type = Parcel_Type.LargePackage;
                break;
            default:
                Type = Parcel_Type.Envelope;
                break;
        }
        try {

            hashMap.put("Fragile", Fragile);
            hashMap.put("Weight", Weight);
            hashMap.put("Type", Type);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return hashMap;
    }

    @Override
    public boolean allFieldsFull() {
        return parcel_weight_et.getText().length() > 0;
    }
}