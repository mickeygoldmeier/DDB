package com.example.ddb.UI.AddParcelProcces;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.ddb.Data.Action;
import com.example.ddb.Data.Parcel_dataSource_Maneger.RegisteredPackagesDS;
import com.example.ddb.Entities.Address;
import com.example.ddb.Entities.Parcel;
import com.example.ddb.Entities.Parcel_Type;
import com.example.ddb.R;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import java.util.HashMap;

public class AddParcelMain extends AppCompatActivity {

    private boolean isFinished = false;
    private Parcel parcel = new Parcel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        parcel.getIdFromDataBase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parcel_main);

        try {
            getActionBar().hide();
        } catch (Exception e) {
            getSupportActionBar().hide();
        }

        final ViewPager view_pager = findViewById(R.id.view_pager);
        final AddParcelAdapter parcelAdapter = new AddParcelAdapter(getSupportFragmentManager());
        view_pager.setAdapter(parcelAdapter);

        final DotIndicator dot_indect = findViewById(R.id.dot_indect);
        dot_indect.setNumberOfItems(parcelAdapter.getCount());

        view_pager.bringToFront();
        view_pager.invalidate();

        ((RelativeLayout) findViewById(R.id.relativeLayout)).bringToFront();

        // set up the next button
        final Button next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFinished) {
                    if (!parcelAdapter.allFieldsFull()) {
                        new AlertDialog.Builder(view.getContext())
                                .setMessage(R.string.empty_fields)
                                .setPositiveButton(R.string.got_it, null)
                                .setIcon(R.drawable.common_google_signin_btn_icon_dark)
                                .show();
                        return;
                    }
                    ProgressBar adding_prb = findViewById(R.id.adding_prb);
                    adding_prb.setVisibility(View.VISIBLE);
                    next_btn.setVisibility(View.GONE);
                    HashMap hashMap = new HashMap();
                    try {
                        hashMap = parcelAdapter.getData();
                    } catch (Exception e) {
                    }
                    try {
                        addParcelToFirebase(convertHashMapToParcel(hashMap));
                    } catch (Exception e) {
                    }
                }
                try {

                    view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
                    if (view_pager.getCurrentItem() == parcelAdapter.getCount() - 1)
                        isFinished = true;
                } catch (Exception e) {

                }
            }
        });

        final Button prev_btn = findViewById(R.id.prev_btn);
        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_pager.setCurrentItem(view_pager.getCurrentItem() - 1);
            }
        });

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                dot_indect.setSelectedItem(view_pager.getCurrentItem(), true);
            }

            @Override
            public void onPageSelected(int position) {
                if (view_pager.getCurrentItem() + 1 == parcelAdapter.getCount()) {
                    isFinished = true;
                    next_btn.setText(R.string.done);
                } else {
                    isFinished = false;
                    next_btn.setText(R.string.next);
                }

                if (view_pager.getCurrentItem() == 0)
                    prev_btn.setVisibility(View.INVISIBLE);
                else
                    prev_btn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ImageView gif_iv = findViewById(R.id.gif_iv);
        Glide.with(this).load(R.drawable.new_parcel_gif).into(gif_iv);
    }

    private Parcel convertHashMapToParcel(HashMap<String, Object> hashMap) throws Exception {
        try {
            parcel.setFragile((boolean) hashMap.get("Fragile"));
            parcel.setRecipientPhone((String) hashMap.get("RecipientPhone"));
            parcel.setWeight((double) hashMap.get("Weight"));
            parcel.setType((Parcel_Type) hashMap.get("Type"));
            parcel.setDistributionCenterAddress((Address) hashMap.get("DistributionCenterAddress"));
            parcel.setCompanyID(getIntent().getExtras().getString("company_id"));
            // parcel.setQRCode(QR_code.getMyBitmap(parcel.getParcelID()));
        } catch (Exception e) {
            throw new Exception(e);
        }
        return parcel;
    }

    private void addParcelToFirebase(Parcel parcel) {
        RegisteredPackagesDS.addParcel(parcel, new Action<String>() {
            @Override
            public void onSuccess(String obj) {
                Intent intent = new Intent(getApplicationContext(), AddParcelDoneScreen.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Exception exception) {
                Button next_btn = findViewById(R.id.next_btn);
                ProgressBar adding_prb = findViewById(R.id.adding_prb);

                adding_prb.setVisibility(View.GONE);
                next_btn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onProgress(String status, double percent) {

            }
        });

    }

}
