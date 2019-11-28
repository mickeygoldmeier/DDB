package com.example.ddb.UI;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ddb.Data.NotifyDataChange;
import com.example.ddb.Data.Parcel_dataSource_Maneger.RegisteredPackagesDS;
import com.example.ddb.Data.Users;
import com.example.ddb.Entities.Company;
import com.example.ddb.Entities.Parcel;
import com.example.ddb.UI.AddParcelProcces.AddParcelMain;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddb.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainScreenCompany extends AppCompatActivity {

    private Company company;
    private List<Parcel> parcels = new ArrayList<>();
    private RecyclerView parcelRecyclerView;



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
                intent.putExtra("company_id", company.getUserID());
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

        // set up the recycle view of the parcels
        parcelRecyclerView = findViewById(R.id.parcel_list_rv);
        parcelRecyclerView.setHasFixedSize(true);
        parcelRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RegisteredPackagesDS.notifyToParcelList(new NotifyDataChange<List<Parcel>>() {
            @Override
            public void OnDataChanged(List<Parcel> obj) {
                if (parcelRecyclerView.getAdapter() == null) {
                    for (Parcel parcel:obj) {
                        if (parcel.getCompanyID()==company.getUserID())
                            parcels.add(parcel);
                    }
                    parcels = obj;
                    parcelRecyclerView.setAdapter(new ParcelRecycleViewAdapter());
                } else parcelRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getBaseContext(), "error to get parcel list\n" + exception.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public class ParcelRecycleViewAdapter extends RecyclerView.Adapter<ParcelViewHolder> {
        @NonNull
        @Override
        public ParcelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_parcel_view_holder, parent, false);
            return new ParcelViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ParcelViewHolder holder, int position) {
            Parcel parcel = parcels.get(position);
            holder.fillView(parcel);
        }

        @Override
        public int getItemCount() {
            return parcels.size();
        }
    }


    protected void onDestroy() {
        RegisteredPackagesDS.stopNotifyToParcelList();
        super.onDestroy();
    }
}