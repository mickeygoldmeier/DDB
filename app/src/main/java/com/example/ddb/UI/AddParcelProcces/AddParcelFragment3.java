package com.example.ddb.UI.AddParcelProcces;


import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.ddb.Data.CitiesList;
import com.example.ddb.Data.GPSLocation;
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
    private GPSLocation gpsLocation;

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

        final EditText street_et = view.findViewById(R.id.street_et);
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
                        if(cities_actv.isEnabled())
                            cities_actv.setTextColor(Color.BLACK);
                        else
                            cities_actv.setTextColor(Color.LTGRAY);
                        break;
                    } else
                        cities_actv.setTextColor(Color.RED);
                }
            }
        });


        final Switch find_GPS_auto_swc = view.findViewById(R.id.find_GPS_auto_swc);
        final LinearLayout GPSon = (LinearLayout) view.findViewById(R.id.gps_is_on_ll);
        final LinearLayout GPSoff = (LinearLayout)view.findViewById(R.id.gps_is_off_ll);

        cities_actv.setDropDownHeight(0);
        cities_actv.setTextColor(Color.LTGRAY);
        gpsLocation = new GPSLocation();
        gpsLocation.setUp(cities_actv, street_et, home_number_et, getContext(), GPSon, GPSoff);

        find_GPS_auto_swc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    gpsLocation = new GPSLocation();
                    gpsLocation.setUp(cities_actv, street_et, home_number_et, getContext(), GPSon, GPSoff);
                    gpsLocation.changeEditTextState(true);
                }
                else
                {
                    gpsLocation.changeEditTextState(false);
                    gpsLocation.stopListeners();
                    gpsLocation = null;
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
