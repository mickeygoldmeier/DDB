package com.example.ddb.UI.AddParcelProcces;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.ddb.Data.CitiesList;
import com.example.ddb.Entities.Address;
import com.example.ddb.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddParcelFragment3 extends DataGetterFragment {

    private static String Country = "ישראל";
    private static String City;
    private static String Street;
    private static int Number;

    public AddParcelFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_parcel_fragment3, container, false);

        final EditText home_number_et = view.findViewById(R.id.home_number_et);
        home_number_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    Number = Integer.parseInt(editable.toString());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        EditText street_et = view.findViewById(R.id.street_et);
        street_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Street = editable.toString();
            }
        });

        final AutoCompleteTextView cities_actv = view.findViewById(R.id.cities_actv);
        final String[] citiesList = CitiesList.getCitiesArray();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, citiesList);
        cities_actv.setAdapter(adapter);
        cities_actv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                for (String city : citiesList) {
                    if (city.equals(editable.toString())) {
                        City = editable.toString();
                        cities_actv.setTextColor(Color.BLACK);
                        break;
                    } else
                        cities_actv.setTextColor(Color.RED);
                }
            }
        });

        return view;
    }

    @Override
    public HashMap<String, Object> getData() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();
        Address address;
        try {
            address = new Address(Country, City, Street, Number);
        } catch (Exception e) {
            throw new Exception(e);
        }
        hashMap.put("DistributionCenterAddress", address);
        return hashMap;
    }
}
