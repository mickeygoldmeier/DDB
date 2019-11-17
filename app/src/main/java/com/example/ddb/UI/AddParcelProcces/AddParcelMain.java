package com.example.ddb.UI.AddParcelProcces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ddb.R;
import com.matthewtamlin.sliding_intro_screen_library.buttons.IntroButton;
import com.matthewtamlin.sliding_intro_screen_library.core.IntroActivity;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import java.util.Collection;

public class AddParcelMain extends AppCompatActivity {

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

        // set up the next button
        final Button next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                if(view_pager.getCurrentItem() + 1 == parcelAdapter.getCount())
                    next_btn.setText(R.string.done);
                else
                    next_btn.setText(R.string.next);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
