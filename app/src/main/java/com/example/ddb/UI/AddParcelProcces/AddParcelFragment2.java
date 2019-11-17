package com.example.ddb.UI.AddParcelProcces;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ddb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddParcelFragment2 extends Fragment {


    public AddParcelFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Spinner parcel_type_spr = getActivity().findViewById(R.id.parcel_type_spr);
        List<String> list = new ArrayList<String>();
        list.add("אופציה 1");
        list.add("אופציה 2");
        list.add("אופציה 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // TODO: לבדוק למה זה קורס בשורה הזאת
        parcel_type_spr.setAdapter(dataAdapter);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_parcel_fragment2, container, false);
    }

}
