package com.example.sayan.locationtracking.App;

import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.util.Log;

import com.example.sayan.locationtracking.contactsDatabase.contactsDatabase;

/**
 * Created by 1605476 and 27-Sep-18
 **/


public class AppDemo extends Application
{
    private static AppDemo INSTANCE;
    private  static final String db_name="contacts.db";
    private contactsDatabase database;

    @Override
    public void onCreate()
    {
        super.onCreate();

        database= Room
                .databaseBuilder(getApplicationContext(),
                contactsDatabase.class,
                db_name)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        //Log.d("database tag",database.toString());
        INSTANCE=AppDemo.this;
    }

    public static AppDemo getApp()
    {
        return INSTANCE;
    }

    public contactsDatabase getDatabase()
    {
        return this.database;
    }
}