package com.example.ddb.Utils;

import com.example.ddb.R;

import java.util.Calendar;

public class TimedData {
    public static int getHourlyBless() {
        // Get the current bless
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 5)
            return R.string.good_night;
        else if (hour >= 5 && hour < 13)
            return R.string.good_morning;
        else if (hour >= 13 && hour < 17)
            return R.string.good_afternoon;
        else
            return R.string.good_evning;
    }

    public static String getHourlyURLBack() {
        // Get the current back
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 5)
            return "https://images.unsplash.com/photo-1498036882173-b41c28a8ba34?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1100&q=60";
        else if (hour >= 5 && hour < 13)
            return "https://images.unsplash.com/photo-1445262102387-5fbb30a5e59d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1234&q=80";
        else if (hour >= 13 && hour < 17)
            return "https://images.unsplash.com/photo-1511933801659-156d99ebea3e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1234&q=80";
        else
            return "https://images.unsplash.com/photo-1500245804862-0692ee1bbee8?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1100&q=60";
    }
}
