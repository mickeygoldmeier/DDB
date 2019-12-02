package com.example.ddb.Data;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import java.util.List;
import java.util.Locale;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class GPSLocation extends Activity implements LocationListener {

    // Acquire a reference to the system Location Manager
    LocationManager locationManager;

    private EditText city;
    private EditText street;
    private EditText number;
    private Context context;

    public void setUp(EditText _city, EditText _street, EditText _number, Context _context) {
        city = _city;
        street = _street;
        number = _number;
        context = _context;

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Check the SDK version and whether the permission is already granted or not.
        final LocationListener locationListener = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Until you grant the permission, we canot display the location", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTextViews(Location location) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<android.location.Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                String temp1 = addresses.get(0).getAddressLine(0);
                String[] temp2 = temp1.split(", ");

                city.setText(temp2[1]);
                street.setText(splitNumberFromStreet(temp2[0])[0]);
                number.setText(splitNumberFromStreet(temp2[0])[1]);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }


    private String[] splitNumberFromStreet(String numberAndStreet)
    {
        String[] result = {"", ""};
        String[] splitedAddress = numberAndStreet.split(" ");

        for(int i = 0; i < splitedAddress.length - 1; i++)
            result[0] += splitedAddress[i] + " ";

        result[0] = result[0].substring(0, result[0].length() - 1);
        result[1] = splitedAddress[splitedAddress.length - 1];

        return result;
    }


    @Override
    public void onLocationChanged(Location location) {
        updateTextViews(location);
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

    public void stopListeners()
    {
        locationManager.removeUpdates(this);
        locationManager = null;
    }
}
