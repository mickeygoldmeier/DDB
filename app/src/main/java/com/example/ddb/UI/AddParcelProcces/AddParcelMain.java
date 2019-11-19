package com.example.ddb.UI.AddParcelProcces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ddb.R;
import com.matthewtamlin.sliding_intro_screen_library.buttons.IntroButton;
import com.matthewtamlin.sliding_intro_screen_library.core.IntroActivity;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import java.util.Collection;
import java.util.HashMap;

public class AddParcelMain extends AppCompatActivity {

    private boolean isFinished = false;
    HashMap<String, Object> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parcel_main);

        try{
            getActionBar().hide();
        } catch (Exception e)
        {
            getSupportActionBar().hide();
        }

        final ViewPager view_pager = findViewById(R.id.view_pager);
        final AddParcelAdapter parcelAdapter = new AddParcelAdapter(getSupportFragmentManager());
        view_pager.setAdapter(parcelAdapter);

        final DotIndicator dot_indect = findViewById(R.id.dot_indect);
        dot_indect.setNumberOfItems(parcelAdapter.getCount());

        view_pager.bringToFront();
        view_pager.invalidate();

        ((RelativeLayout)findViewById(R.id.relativeLayout)).bringToFront();

        // set up the next button
        final Button next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFinished)
                    hashMap = parcelAdapter.getData();
                try {
                    view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
                } catch (Exception e) {

                }
            }
        });

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                dot_indect.setSelectedItem(view_pager.getCurrentItem(), true);
            }

            @Override
            public void onPageSelected(int position) {
                if(view_pager.getCurrentItem() + 1 == parcelAdapter.getCount()) {
                    isFinished = true;
                    next_btn.setText(R.string.done);
                }
                else {
                    isFinished = false;
                    next_btn.setText(R.string.next);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ImageView gif_iv = findViewById(R.id.gif_iv);
        Glide.with(this).load(R.drawable.new_parcel_gif).into(gif_iv);
    }

    private Parcel convertHashMapToParcel(HashMap<String, Object> hashMap)
    {
        // tfytfgf
        return null;
    }
}
