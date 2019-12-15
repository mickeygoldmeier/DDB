package com.example.ddb.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ddb.Data.CitiesList;
import com.example.ddb.Data.NotifyDataChange;
import com.example.ddb.Data.User_dataSource_Maneger.UsersDS;
import com.example.ddb.Data.Users;
import com.example.ddb.Entities.User;
import com.example.ddb.R;
import com.example.ddb.Utils.TimedData;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class MainActivity extends AppCompatActivity {
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UsersDS.notifyToUserList(new NotifyDataChange<List<User>>() {
            @Override
            public void OnDataChanged(List<User> obj) {
                users = obj;
            }

            @Override
            public void onFailure(Exception exception) {
                Intent i = new Intent(getApplicationContext(), NoInternetConnection.class);
                i.setFlags(FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(i);
            }
        });

        // update the cities list
        CitiesList.UpdateCitiesList(getApplicationContext());


        // add the image to the back
        try {
            String url = TimedData.getHourlyURLBack();
            Glide.with(this)
                    .asDrawable()
                    .load(url)
                    .fitCenter()
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
        welcomeTV.setText(TimedData.getHourlyBless());

        final EditText id_ed = findViewById(R.id.id_et);
        final TextView message_tv = findViewById(R.id.message_tv);
        final Button signin_btn = findViewById(R.id.signin_btn);
        final Context context = this;
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    User user = getUser(normalizePhoneNumber(id_ed.getText().toString()));

                    EditText password_et = findViewById(R.id.password_et);
                    if (password_et.getVisibility() == EditText.VISIBLE) {
                        if (password_et.getText().toString().equals(user.getPassword())) {
                            Intent i = new Intent(getApplicationContext(), MainScreenCompany.class);
                            i.putExtra("userID", user.getUserID());
                            Users.setUsersList(users);
                            finish();
                            UsersDS.stopNotifyToUserList();
                            startActivity(i);
                        } else
                            throw new StringException(R.string.forgot_your_password);

                    } else {
                        password_et.setVisibility(View.VISIBLE);
                        message_tv.setText(R.string.enter_your_password);
                    }

                } catch (StringException e) {
                    message_tv.setText(e.getStringRef());
                    message_tv.setTextColor(Color.RED);
                }
            }
        });

        // sign up to the system
        final TextView signup_tv = findViewById(R.id.signup_tv);
        signup_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUpScreen.class);
                startActivity(i);
            }
        });
    }

    private String normalizePhoneNumber(String number) {
        if (number.startsWith("05"))
            number = number.replaceFirst("05", "+9725");
        return number;
    }


    public User getUser(String id) throws StringException {
        for (User user : users)
            if (user.getUserID().equals(id))
                return user;
        throw new StringException(R.string.phone_number_error);
    }

    // Inner exception with reference to string from R.String
    private class StringException extends Exception {
        private int stringRef;

        public StringException(int stringRef) {
            super();
            this.stringRef = stringRef;
        }

        public int getStringRef() {
            return stringRef;
        }
    }

}
