package com.example.ddb.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ddb.Data.Action;
import com.example.ddb.Data.CitiesList;
import com.example.ddb.Data.Config_dataSource_Maneger.ConfigDS;
import com.example.ddb.Data.GPSLocation;
import com.example.ddb.Data.NotifyDataChange;
import com.example.ddb.Data.User_dataSource_Maneger.UsersDS;
import com.example.ddb.Data.Users;
import com.example.ddb.Entities.Company;
import com.example.ddb.Entities.Person;
import com.example.ddb.Entities.User;
import com.example.ddb.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private List<User> users;

    // Acquire a reference to the system Location Manager
    LocationManager locationManager;
    // Define a listener that responds to location updates
    LocationListener locationListener;

    Button GPSbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UsersDS.notifyToParcelList(new NotifyDataChange<List<User>>() {
            @Override
            public void OnDataChanged(List<User> obj) {
                users = obj;
            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
        // update the cities list
        CitiesList.UpdateCitiesList(getApplicationContext());



        // add the image to the back
        try {
            String url = "https://images.unsplash.com/photo-1498036882173-b41c28a8ba34?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1100&q=60";
            Glide.with(this)
                    .load(url)
                    .centerCrop()
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            findViewById(R.id.main_activity_ll).setBackground(resource);
                        }
                    });
        } catch (Exception e) {

        }

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
                    User user = getUser(normalizePhoneNumber(id_ed.getText().toString()));

                    Intent i = new Intent(getApplicationContext(), MainScreenCompany.class);
                    i.putExtra("userID", user.getUserID());
                    finish();
                    startActivity(i);

                } catch (Exception e) {
                    message_tv.setText(R.string.phone_number_error);
                    message_tv.setTextColor(Color.RED);
                }
            }
        });

        GPSbtn = findViewById(R.id.tempGPS);
        setUp();
        GPSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

    }

    private String normalizePhoneNumber(String number) {
        if (number.startsWith("05"))
            number = number.replaceFirst("05", "+9725");
        return number;
    }


    /////////////////////////////////////////////////////////////////////////
    private void setUp() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                // Toast.makeText(getBaseContext(), location.toString(), Toast.LENGTH_LONG).show();
                GPSbtn.setText(getPlace(location));
                Toast.makeText(getApplicationContext(), getPlace(location), Toast.LENGTH_LONG);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
    }

    public void getLocation() {
        //     Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }

    public String getPlace(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);
                return cityName;
            }
            return "no place: \n (" + location.getLongitude() + " , " + location.getLatitude() + ")";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "IOException ...";
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Until you grant the permission, we canot display the location", Toast.LENGTH_SHORT).show();
        }
    }

    public User getUser(String id) throws Exception {
        for (User user : users)
            if (user.getUserID().equals(id))
                return user;
        throw new Exception();
    }

}
