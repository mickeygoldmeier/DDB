package com.example.ddb.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail extends AsyncTask<Void, Void, Void> {
    private Context context;
    private Session session;
    private String email;
    private String subject;
    private String massage;
    private Button sendBTN;
    private ProgressBar progressBar;
    private TextView textView;

    // Class constructor
    public SendEmail(Context context, String email, String subject, String massage, Button sendBTN, ProgressBar progressBar, TextView textView) {
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.massage = massage;
        this.sendBTN = sendBTN;
        this.progressBar = progressBar;
        this.textView = textView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        sendBTN.setVisibility(View.GONE);
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected void onPostExecute(Void avoid) {
        super.onPostExecute(avoid);
        progressBar.setVisibility(ProgressBar.GONE);
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Creating properties
        Properties prop = new Properties();

        // Config the properties for gmail
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465");

        // Creating new session
        session = Session.getDefaultInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("nmjavaproject2020@gmail.com", "nmmg1999");
                    }
                });

        try {
            MimeMessage mm = new MimeMessage(session);

            mm.setFrom(new InternetAddress("nmjavaproject2020@gmail.com"));
            mm.addRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(email)});
            mm.setSubject(subject);
            mm.setText(massage);

            // Send the email
            Transport.send(mm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
