package com.example.ddb.UI;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ddb.R;
import com.example.ddb.Utils.SendEmail;

public class SignUpScreen extends AppCompatActivity {

    private EditText emailAddress;
    private EditText name;
    private boolean emailIsOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        // add the image to the back
        try {
            String url = "https://images.unsplash.com/photo-1498036882173-b41c28a8ba34?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1100&q=60";
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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().setTitle("");
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        emailAddress = findViewById(R.id.my_email_et);
        emailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().matches(".{5,}\\@.{4,}\\..{3,}")) {
                    emailAddress.setTextColor(Color.BLACK);
                    emailIsOk = true;
                } else {
                    emailAddress.setTextColor(Color.RED);
                    emailIsOk = false;
                }
            }
        });

        name = findViewById(R.id.my_name_et);

        final Button sendEmail = findViewById(R.id.send_mail_btn);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() == 0 ||
                        emailAddress.getText().toString().length() == 0 || !emailIsOk) {
                    new AlertDialog.Builder(view.getContext())
                            .setMessage(R.string.empty_fields)
                            .setPositiveButton(R.string.got_it, null)
                            .show();
                    return;
                }

                String myName = name.getText().toString();
                String subject = myName + " רוצה להצטרף לאפליקציה";
                String massage = "הלקוח " + myName + " רוצה להצטרף לאפליקציה.\nכתובת האימייל שלו ליצירת קשר היא: " + emailAddress.getText().toString();
                ProgressBar progressBar = findViewById(R.id.sending_email_pb);
                TextView textView = findViewById(R.id.we_got_deateils_tv);
                SendEmail email = new SendEmail(getApplicationContext(), "nmjavaproject2020@gmail.com", subject, massage, sendEmail, progressBar, textView);
                email.execute();
            }
        });
    }
}
