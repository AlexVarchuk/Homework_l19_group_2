package com.example.olexandr.homework_l19_group_2;

import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private Button mClearList;
    private Button mUpdate;
    private ListView mNotificationList;

    private AdapterNotification mAdapter;
    private List<ItemNotification> mData = new ArrayList<>();
    private BaseDataNotification mBaseDataNotification;
    private SQLiteDatabase mSQLiteDatabase;
    private static final int NOTIFICATION_ID = 1225;


    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getToken();
        findViews();
        checkClickListener();


        mBaseDataNotification = new BaseDataNotification(this, "mydata.db", null, 1);
        mSQLiteDatabase = mBaseDataNotification.getReadableDatabase();

        mAdapter = new AdapterNotification(this, mData);
        mNotificationList.setAdapter(mAdapter);


        loadListView();


    }

    private void findViews() {
        mNotificationList = (ListView) findViewById(R.id.lv_notification_AM);
        mClearList = (Button) findViewById(R.id.btn_clear_list_AM);
        mUpdate = (Button) findViewById(R.id.btn_update_list_AM);
    }

    private void checkClickListener() {
        mClearList.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mNotificationList.setOnItemClickListener(this);
    }

    private void loadListView() {
        mData.clear();
        mData.addAll(setList());
        mAdapter.notifyDataSetChanged();
    }

    private void getToken() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InstanceID instanceID = InstanceID.getInstance(MainActivity.this);
                    String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    Log.d("kuku", "Token: " + token);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    private List<ItemNotification> setList() {
        List<ItemNotification> listView = new ArrayList<>();
        mCursor = mSQLiteDatabase.query(BaseDataNotification.DATABASE_TABLE, new String[]{
                        BaseDataNotification.MESSAGE_COLUMN, BaseDataNotification.TITLE_COLUMN,
                        BaseDataNotification.SUBTITLE_COLUMN, BaseDataNotification.TICKETTEXT_COLUMN,
                        BaseDataNotification.VIBRATION_COLUMN, BaseDataNotification.SOUND_COLUMN},
                null, null, null, null, null);

        while (mCursor.moveToNext()) {
            String message = mCursor.getString(mCursor.getColumnIndex(BaseDataNotification.MESSAGE_COLUMN));
            String title = mCursor.getString(mCursor.getColumnIndex(BaseDataNotification.TITLE_COLUMN));
            String subtitle = mCursor.getString(mCursor.getColumnIndex(BaseDataNotification.SUBTITLE_COLUMN));
            String tickerText = mCursor.getString(mCursor.getColumnIndex(BaseDataNotification.TICKETTEXT_COLUMN));
            int vibrate = mCursor.getInt(mCursor.getColumnIndex(BaseDataNotification.VIBRATION_COLUMN));
            int sound = mCursor.getInt(mCursor.getColumnIndex(BaseDataNotification.SOUND_COLUMN));
            listView.add(new ItemNotification(message, title, subtitle, tickerText, vibrate, sound));

        }


        return listView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_clear_list_AM:
                mSQLiteDatabase.delete(mBaseDataNotification.DATABASE_TABLE, null, null);
                mData.clear();
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.btn_update_list_AM:
                mData.clear();
                mData.addAll(setList());
                mAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String message = mData.get(position).getMassage();
        String title = mData.get(position).getTitle();
        String subtitle = mData.get(position).getSubtitle();
        String tickerText = mData.get(position).getTicketText();
        int vibrate = mData.get(position).getVibrate();
        int sound = mData.get(position).getSound();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_dialer)
                .setContentTitle(title)
                .setSubText(subtitle)
                .setContentText(message)
                .setTicker(tickerText)
                .setAutoCancel(true);

        if (vibrate == 1) builder.setVibrate(new long[]{0, 100, 200, 300});
        if (sound == 1)
            builder.setSound(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.eralash));

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}