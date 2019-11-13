package com.example.ddb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ddb.Data.Users;
import com.example.ddb.Entities.Company;
import com.example.ddb.Entities.User;
import com.example.ddb.R;

public class CompanyMainScreen extends AppCompatActivity {

    private Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_main_screen);

        try {
            Bundle b = getIntent().getExtras();
            String str = b.getString("userID");
            company = (Company) Users.getUser(str);
            TextView tv = findViewById(R.id.textView);
            tv.setText(company.getName());
        }
        catch (Exception e){
            TextView tv = findViewById(R.id.textView);
            tv.setText(e.getMessage());
        }
    }
}
