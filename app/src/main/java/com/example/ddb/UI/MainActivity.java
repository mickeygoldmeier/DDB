package com.example.ddb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ddb.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hide the Action Bar and the Status bar
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            getActionBar().hide();
        }
        try {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            this.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } catch (Exception e) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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


        final EditText id_ed = findViewById(R.id.id_et);
        Button signin_btn = findViewById(R.id.signin_btn);
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_ed.setText(normalizePhoneNumber(id_ed.getText().toString()));
            }
        });
    }

    private String normalizePhoneNumber(String number)
    {
        if (number.startsWith("05"))
            number = number.replace("05", "+9725");
        return number;
    }
}
