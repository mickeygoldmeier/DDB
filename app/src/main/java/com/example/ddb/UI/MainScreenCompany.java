package com.example.ddb.UI;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ddb.Data.Action;
import com.example.ddb.Data.NotifyDataChange;
import com.example.ddb.Data.Parcel_dataSource_Maneger.RegisteredPackagesDS;
import com.example.ddb.Data.User_dataSource_Maneger.UsersDS;
import com.example.ddb.Data.Users;
import com.example.ddb.Entities.Address;
import com.example.ddb.Entities.Company;
import com.example.ddb.Entities.Parcel;
import com.example.ddb.Entities.Person;
import com.example.ddb.Entities.User;
import com.example.ddb.R;
import com.example.ddb.UI.AddParcelProcces.AddParcelMain;
import com.example.ddb.Utils.TimedData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainScreenCompany extends AppCompatActivity {

    private Company company;
    private static List<Parcel> parcels = new ArrayList<>();
    private static RecyclerView parcelRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_company);

        // add the image to the back
        try {
            String url = TimedData.getHourlyURLBack();
            Glide.with(this)
                    .asDrawable()
                    .load(url)
                    .fitCenter()
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            findViewById(R.id.back).setBackground(resource);
                        }
                    });
        } catch (Exception e) {

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddParcelMain.class);
                intent.putExtra("company_id", company.getUserID());
                startActivity(intent);
            }
        });


        // set the company name
        try {
            Bundle b = getIntent().getExtras();
            String str = b.getString("userID");
            company = (Company) Users.getUser(str);
            //company = (Company) b.get("user");
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
                parcels = obj;

                if (parcelRecyclerView.getAdapter() == null) {
                    parcelRecyclerView.setAdapter(new ParcelRecycleViewAdapter());
                }
                else {
                    parcelRecyclerView.getAdapter().notifyDataSetChanged();
                }
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
            ExtraParcelInfo extraParcelInfo = (ExtraParcelInfo) getSupportFragmentManager().findFragmentById(R.id.extra_parcel_frg);
            return new ParcelViewHolder(v, extraParcelInfo);
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

    public static void setParcels(List<Parcel> list){
        parcels = list;
        parcelRecyclerView.getAdapter().notifyDataSetChanged();
    }

    protected void onDestroy() {
        RegisteredPackagesDS.stopNotifyToParcelList();
        super.onDestroy();
    }
}
