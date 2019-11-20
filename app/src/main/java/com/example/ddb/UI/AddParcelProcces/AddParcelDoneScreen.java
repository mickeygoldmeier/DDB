package com.example.ddb.UI.AddParcelProcces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ddb.R;

public class AddParcelDoneScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parcel_done_screen);

        ImageView gif_iv = findViewById(R.id.gif2_iv);
        Glide.with(this).load(R.drawable.add_pacel_done).into(gif_iv);
    }
}
