package com.example.ddb.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.ddb.Data.Users;
import com.example.ddb.Entities.Company;
import com.example.ddb.Entities.User;
import com.example.ddb.R;

import java.util.Random;

public class CompanyMainScreen extends AppCompatActivity {

    private Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_main_screen);

        // hide the Action Bar and the Status bar
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            getActionBar().hide();
        }
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // set the company name
        try {
            Bundle b = getIntent().getExtras();
            String str = b.getString("userID");
            company = (Company) Users.getUser(str);
            TextView tv = findViewById(R.id.company_name_tv);
            tv.setText(company.getName() + "!");
        } catch (Exception e) {
            TextView tv = findViewById(R.id.company_name_tv);
            tv.setText(e.getMessage());
        }

        // set the blessing text
        int[] blessings = {R.string.blessing_1, R.string.blessing_2, R.string.blessing_3};
        TextView hello_tv = findViewById(R.id.hello_tv);
        hello_tv.setText(blessings[new Random().nextInt((blessings.length))]);

        // log out button
        Button log_out_btn = findViewById(R.id.log_out_btn);
        log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("skfjkj")
                        .setMessage(R.string.log_out)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                finish();
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });
    }
}
