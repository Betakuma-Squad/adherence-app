package com.squad.betakuma.adherence_app.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.squad.betakuma.adherence_app.PrescriptionListActivity;

/**
 * Created by sherryuan on 2018-09-16.
 */

public class AlarmReceiver extends BroadcastReceiver {

    String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Trigger the notification
        String title = "Reminder to take " + intent.getStringExtra("dosage") + " of " + intent.getStringExtra("title");
        NotificationScheduler.showNotification(context, PrescriptionListActivity.class, title);
    }
}