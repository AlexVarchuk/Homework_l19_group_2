package com.example.olexandr.homework_l19_group_2.servise;

import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.olexandr.homework_l19_group_2.R;
import com.example.olexandr.homework_l19_group_2.database.BaseDataNotification;
import com.google.android.gms.gcm.GcmListenerService;


public class MyGcmListenerService extends GcmListenerService {

    private SQLiteDatabase mSQLiteDatabase;
    @Override
    public void onMessageReceived(String from, Bundle data) {

        showNotification(this, data);

        BaseDataNotification mDatabase = new BaseDataNotification(this, "mydata.db", null, 1);
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
        if (sound == 1) builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.eralash));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1234, builder.build());
    }

    public void setDatabase(Bundle data) {
        String message = data.getString("message");
        String title = data.getString("title");
        String subtitle = data.getString("subtitle");
        String tickerText = data.getString("tickerText");
        int vibrate = Integer.valueOf(data.getString("vibrate"));
        int sound = Integer.valueOf(data.getString("sound"));

        ContentValues newValues = new ContentValues();
        newValues.put(BaseDataNotification.MESSAGE_COLUMN, message);
        newValues.put(BaseDataNotification.TITLE_COLUMN, title);
        newValues.put(BaseDataNotification.SUBTITLE_COLUMN, subtitle);
        newValues.put(BaseDataNotification.TICKETTEXT_COLUMN, tickerText);
        newValues.put(BaseDataNotification.VIBRATION_COLUMN, vibrate);
        newValues.put(BaseDataNotification.SOUND_COLUMN, sound);
        mSQLiteDatabase.insert("Notification", null, newValues);
    }
}
