package com.example.ddb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ddb.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hide the Action Bar
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            getActionBar().hide();
        }

        // set the welcome title
        TextView welcomeTV = findViewById(R.id.welcome_tv);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour > 0 && hour < 5)
            welcomeTV.setText(R.string.good_night);
        else if (hour >= 5 && hour < 13)
            welcomeTV.setText(R.string.good_morning);
        else if (hour >= 13 && hour < 20)
            welcomeTV.setText(R.string.good_afternoon);
        else
            welcomeTV.setText(R.string.good_evning);
    }
}
