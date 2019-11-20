package com.example.ddb.UI.AddParcelProcces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ddb.R;

public class AddParcelDoneScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parcel_done_screen);

        try {
            getActionBar().hide();
        } catch (Exception e) {
            getSupportActionBar().hide();
        }

        ImageView gif_iv = findViewById(R.id.gif2_iv);
        Glide.with(this).load(R.drawable.add_pacel_done).into(gif_iv);

        Button close_ok_screen_btn = findViewById(R.id.close_ok_screen_btn);
        close_ok_screen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
