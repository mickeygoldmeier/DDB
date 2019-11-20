package com.example.ddb.UI.AddParcelProcces;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
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

import com.example.ddb.Data.CitiesList;
import com.example.ddb.Entities.Address;
import com.example.ddb.Entities.Parcel_Type;
import com.example.ddb.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

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

        // set up the cities spinner
        Spinner cities_spr = view.findViewById(R.id.cities_spr);
        final String[] cities = CitiesList.getCitiesArray();
        cities_spr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                City = cities[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter aa1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, cities);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cities_spr.setAdapter(aa1);

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


        return view;
    }

    @Override
    public HashMap<String, Object> getData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        Address address = new Address(Country, City, Street, Number);
        hashMap.put("DistributionCenterAddress", address);
        return hashMap;
    }
}
