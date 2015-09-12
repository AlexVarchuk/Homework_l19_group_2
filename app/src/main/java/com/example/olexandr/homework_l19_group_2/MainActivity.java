package com.example.olexandr.homework_l19_group_2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.olexandr.homework_l19_group_2.adater.AdapterNotification;
import com.example.olexandr.homework_l19_group_2.database.BaseDataNotification;
import com.example.olexandr.homework_l19_group_2.model.ItemNotification;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = MainActivity.class.getSimpleName();

    private Button mClearList;
    private Button mUpdate;
    private Button mClearDatabase;
    private ListView mNotificationList;

    private AdapterNotification mAdapter;
    private List<ItemNotification> mData;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private BaseDataNotification mBaseDataNotification;
    private SQLiteDatabase mSQLiteDatabase;
    private static final int NOTIFICATION_ID = 1234;


    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getToken();

        mNotificationList = (ListView) findViewById(R.id.lv_notification_AM);
        mClearDatabase = (Button) findViewById(R.id.btn_clear_database_AM);
        mClearList = (Button) findViewById(R.id.btn_clear_list_AM);
        mUpdate = (Button) findViewById(R.id.btn_update_list_AM);

        mClearDatabase.setOnClickListener(this);
        mClearList.setOnClickListener(this);
        mUpdate.setOnClickListener(this);

        mBaseDataNotification = new BaseDataNotification(this, "mydata.db", null, 1 );
        mSQLiteDatabase  = mBaseDataNotification.getReadableDatabase();

        mAdapter = new AdapterNotification(this, mData);
        mNotificationList.setAdapter(mAdapter);
        mNotificationList.setOnClickListener(this);


        mData.clear();
        mData.addAll(setList());
        mAdapter.notifyDataSetChanged();


    }

    private List<ItemNotification> setList() {
        List<ItemNotification> listView = new ArrayList<>();
        mCursor = mSQLiteDatabase.query(BaseDataNotification.DATABASE_TABLE, new String[]{
                        BaseDataNotification.MESSAGE_COLUMN, BaseDataNotification.TITLE_COLUMN,
                        BaseDataNotification.SUBTITLE_COLUMN, BaseDataNotification.TICKETTEXT_COLUMN,
                        BaseDataNotification.VIBRATION_COLUMN, BaseDataNotification.SOUND_COLUMN},
                null,null, null, null,null);

        while (mCursor.moveToNext()){
            String message = mCursor.getString(mCursor.getColumnIndex(BaseDataNotification.MESSAGE_COLUMN));
            String title = mCursor.getString(mCursor.getColumnIndex(BaseDataNotification.TITLE_COLUMN));
            String subtitle = mCursor.getString(mCursor.getColumnIndex(BaseDataNotification.SUBTITLE_COLUMN));
            String tickerText = mCursor.getString(mCursor.getColumnIndex(BaseDataNotification.TICKETTEXT_COLUMN));
            int vibrate = mCursor.getInt(mCursor.getColumnIndex(BaseDataNotification.VIBRATION_COLUMN));
            int sound = mCursor.getInt(mCursor.getColumnIndex(BaseDataNotification.SOUND_COLUMN));
            listView.add(new ItemNotification(message, title, subtitle, tickerText, vibrate, sound));
            return listView;
        }




        return listView;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddNotification_AL19:


                break;

            case R.id.btnUpdateNotification_AL19:

                break;

            case R.id.btnRemoveNotification_AL19:

                break;
        }
    }

    private void getToken() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InstanceID instanceID = InstanceID.getInstance(MainActivity.this);
                    String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    Log.d(TAG, "Token: " + token);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void onClickAddNotification() {
        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.sym_def_app_icon))
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setAutoCancel(true);

        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        mBuilder.setContentIntent(pendingIntent);

//        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

        Notification notification = mBuilder.build();
        notification.ledOnMS = 100;
        notification.ledOffMS = 100;
        notification.flags = Notification.FLAG_SHOW_LIGHTS;

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }



}