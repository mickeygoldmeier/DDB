package com.example.ddb.UI;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.ddb.Data.NotifyDataChange;
import com.example.ddb.Data.Parcel_dataSource_Maneger.RegisteredPackagesDS;
import com.example.ddb.Data.Users;
import com.example.ddb.Entities.Company;
import com.example.ddb.Entities.Parcel;
import com.example.ddb.UI.AddParcelProcces.AddParcelMain;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.ddb.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainScreenCompany extends AppCompatActivity {

    private Company company;
    private List<Parcel> parcels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_company);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddParcelMain.class);
                RegisteredPackagesDS.stopNotifyToParcelList();
                startActivity(intent);
            }
        });

        // set the company name
        try {
            Bundle b = getIntent().getExtras();
            String str = b.getString("userID");
            company = (Company) Users.getUser(str);
            getSupportActionBar().setTitle(company.getName());
        } catch (Exception e) {
            getSupportActionBar().setTitle(e.getMessage());
        }

        // set the blessing text
        int[] blessings = {R.string.blessing_1, R.string.blessing_2, R.string.blessing_3};
        TextView hello_tv = findViewById(R.id.hello_tv);
        hello_tv.setText(blessings[new Random().nextInt((blessings.length))]);
    }

    private void getPercelList(){
        RegisteredPackagesDS.notifyToParcelList(new NotifyDataChange<List<Parcel>>() {
            @Override
            public void OnDataChanged(List<Parcel> obj) {
                parcels = obj;
            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
    }
}
