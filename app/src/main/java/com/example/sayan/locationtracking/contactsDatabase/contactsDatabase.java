package com.example.sayan.locationtracking.contactsDatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.sayan.locationtracking.Dao.ContactDao;
import com.example.sayan.locationtracking.Details.Persondetails;

/**
 * Created by ictsupport on 17-09-2018.
 */

@Database(entities = {Persondetails.class}, version = 2)

public abstract class contactsDatabase extends RoomDatabase
{
    //private static contactsDatabase INSTANCE;
   // public static final String db_name="contacts.db";

   /* public static contactsDatabase getDatabase(final Context context)
    {

        if(INSTANCE==null)
        {
            synchronized (contactsDatabase.class)
            {
                if(INSTANCE==null)
                {
                    INSTANCE= Room.
                            databaseBuilder(context.getApplicationContext(),contactsDatabase.class,db_name)
                            .allowMainThreadQueries()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Log.d("contactsDatabase","populating data....");
                                    //new PopulateDataAsync(INSTANCE).execute();
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }*/

    /*public void clearData()
    {
        if(INSTANCE!=null)
        {

        }
    }*/
    public abstract ContactDao contactDao();
}