package com.example.sayan.locationtracking.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.sayan.locationtracking.Details.Persondetails;
import java.util.List;

/**
 * Created by ictsupport on 17-09-2018.
 */

@Dao
public interface ContactDao
{
    @Query("SELECT * FROM contacts WHERE did = :id LIMIT 1")
    Persondetails findDirectorById(int id);

    @Query("SELECT * FROM contacts WHERE full_name = :full_name LIMIT 1")
    Persondetails findDirectorByFullName(String full_name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Persondetails details);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Persondetails> details);

    @Update(onConflict=OnConflictStrategy.IGNORE)
    void update(Persondetails details);

    @Query("DELETE FROM contacts ")
    void deleteALL();

    @Query("SELECT * FROM contacts ORDER BY full_name ASC")
    LiveData<List<Persondetails>> getLiveAllContacts();

    @Query("SELECT * FROM contacts ORDER BY full_name ASC")
    List<Persondetails> getAllContacts();

    @Delete
    void delete(Persondetails model);
}