package com.example.ddb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddb.Data.Action;
import com.example.ddb.Data.CitiesList;
import com.example.ddb.Data.Parcel_dataSource_Maneger.RegisteredPackagesDS;
import com.example.ddb.Data.Users;
import com.example.ddb.Entities.Address;
import com.example.ddb.Entities.Parcel;
import com.example.ddb.Entities.Parcel_Type;
import com.example.ddb.Entities.User;
import com.example.ddb.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // update the cities list
        CitiesList.UpdateCitiesList();

        // hide the Action Bar and the Status bar
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            getActionBar().hide();
        }
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        // set the welcome title
        TextView welcomeTV = findViewById(R.id.welcome_tv);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 5)
            welcomeTV.setText(R.string.good_night);
        else if (hour >= 5 && hour < 13)
            welcomeTV.setText(R.string.good_morning);
        else if (hour >= 13 && hour < 17)
            welcomeTV.setText(R.string.good_afternoon);
        else
            welcomeTV.setText(R.string.good_evning);


        final EditText id_ed = findViewById(R.id.id_et);
        final TextView message_tv = findViewById(R.id.message_tv);
        Button signin_btn = findViewById(R.id.signin_btn);

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    User user = Users.getUser(normalizePhoneNumber(id_ed.getText().toString()));

                    Intent i = new Intent(getApplicationContext(), CompanyMainScreen.class);
                    i.putExtra("userID", user.getUserID());
                    finish();
                    startActivity(i);

                } catch (Exception e) {
                    message_tv.setText(R.string.phone_number_error);
                    message_tv.setTextColor(Color.RED);
                }
            }
        });
    }

    private String normalizePhoneNumber(String number) {
        if (number.startsWith("05"))
            number = number.replaceFirst("05", "+9725");
        return number;
    }


}
