package com.example.ddb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ddb.R;

public class NoInternetConnection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_connection);

        try {
            getActionBar().hide();
        } catch (Exception e) {
            getSupportActionBar().hide();
        }

        ImageView gif_iv = findViewById(R.id.gif_no_internet_iv);
        Glide.with(this).load(R.drawable.no_connection).into(gif_iv);

        Button close_ok_screen_btn = findViewById(R.id.no_internet_btn);
        close_ok_screen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }
}
