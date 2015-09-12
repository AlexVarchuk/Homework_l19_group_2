package com.example.olexandr.homework_l19_group_2.servise;

import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.olexandr.homework_l19_group_2.database.BaseDataNotification;
import com.google.android.gms.gcm.GcmListenerService;


public class MyGcmListenerService extends GcmListenerService {

    private BaseDataNotification mDatabase;
    private SQLiteDatabase mSQLiteDatabase;
    @Override
    public void onMessageReceived(String from, Bundle data) {

        showNotification(this, data);

        mDatabase = new BaseDataNotification(this, "mydata.db", null, 1);
        mSQLiteDatabase = mDatabase.getWritableDatabase();

        setDatabase(data);
    }

    public void showNotification(Context context, final Bundle bundle) {
        String message = bundle.getString("message");
        String title = bundle.getString("title");
        String subtitle = bundle.getString("subtitle");
        String tickerText = bundle.getString("tickerText");
        int vibrate = Integer.valueOf(bundle.getString("vibrate"));
        int sound = Integer.valueOf(bundle.getString("sound"));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(title)
                .setSubText(subtitle)
                .setContentText(message)
                .setTicker(tickerText)
                .setAutoCancel(true);

        if (vibrate == 1) builder.setVibrate(new long[]{0, 100, 200, 300});
//        if (sound == 1) builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.smb_jump_small));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1234, builder.build());
    }

    public void setDatabase(Bundle database) {
        String message = database.getString("message");
        String title = database.getString("title");
        String subtitle = database.getString("subtitle");
        String tickerText = database.getString("tickerText");
        int vibrate = Integer.valueOf(database.getString("vibrate"));
        int sound = Integer.valueOf(database.getString("sound"));

        ContentValues newValues = new ContentValues();
        newValues.put(mDatabase.MESSAGE_COLUMN, message);
        newValues.put(mDatabase.TITLE_COLUMN, title);
        newValues.put(mDatabase.SUBTITLE_COLUMN, subtitle);
        newValues.put(mDatabase.TICKETTEXT_COLUMN, tickerText);
        newValues.put(mDatabase.VIBRATION_COLUMN, vibrate);
        newValues.put(mDatabase.SOUND_COLUMN, sound);
        mSQLiteDatabase.insert("notifications", null, newValues);
    }
}
