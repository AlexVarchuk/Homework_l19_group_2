package com.example.olexandr.homework_l19_group_2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class BaseDataNotification extends SQLiteOpenHelper implements BaseColumns {

    public static final String DATABASE_NAME = "mydata.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE = "Notisfication";


    public static final String MESSAGE_COLUMN = "massage";
    public static final String TITLE_COLUMN = "title";
    public static final String SUBTITLE_COLUMN = "subtitle";
    public static final String TICKETTEXT_COLUMN = "tickertext";
    public static final String VIBRATION_COLUMN = "vibration";
    public static final String SOUND_COLUMN = "sound";

    public static final String DATABASE_CREATE_SCRIPT = "CREATE TABLE "
            + DATABASE_TABLE + " (" + BaseColumns._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MESSAGE_COLUMN
            + " TEXT, " + TITLE_COLUMN + " TEXT, " + SUBTITLE_COLUMN
            + " TEXT, " + TICKETTEXT_COLUMN + " TEXT, " + VIBRATION_COLUMN
            + " INTEGER, " + SOUND_COLUMN + " INT);";



    public BaseDataNotification(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
    }
}
