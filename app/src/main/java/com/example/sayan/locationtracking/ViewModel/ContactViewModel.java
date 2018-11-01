package com.example.sayan.locationtracking.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.sayan.locationtracking.App.AppDemo;
import com.example.sayan.locationtracking.Dao.ContactDao;
import com.example.sayan.locationtracking.Details.Persondetails;
import com.example.sayan.locationtracking.contactsDatabase.contactsDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 1605476 and 27-Sep-18
 **/
public class ContactViewModel extends ViewModel
{
    private ContactDao contactDao;
    private LiveData<List<Persondetails>> list;
    private List<Persondetails> persondetails;


    public ContactViewModel()
    {
        contactDao= AppDemo.getApp().getDatabase().contactDao();
        //executorService= Executors.newSingleThreadExecutor();
        list=contactDao.getLiveAllContacts();
        persondetails=contactDao.getAllContacts();
    }

    public LiveData<List<Persondetails>> getAllLiveDetails()
    {
       return list;
    }

    public List<Persondetails> getAllDetails()
    {
        return persondetails;
    }


    public void insert(Persondetails details)
    {
        contactDao.insert(details);
    }

}