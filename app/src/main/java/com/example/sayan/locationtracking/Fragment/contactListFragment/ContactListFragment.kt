package com.example.sayan.locationtracking.Fragment.contactListFragment


/**
 * In this fragment we call list of contacts and show them here
 *
 */

import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.sayan.locationtracking.Activity.MapNewActivity
import com.example.sayan.locationtracking.Adapter.ListAdapter
import com.example.sayan.locationtracking.Details.Persondetails


import java.util.ArrayList
import android.content.Intent
import com.example.sayan.locationtracking.R
import android.view.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.lang.ref.PhantomReference
import android.content.Context.SEARCH_SERVICE
import android.app.SearchManager
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView


class ContactListFragment : Fragment()
{
    lateinit var recyclerView: RecyclerView
    lateinit var adapter : ListAdapter
    lateinit var fab:FloatingActionButton

    lateinit var databaseReference : DatabaseReference
    lateinit var firebaseAuth : FirebaseAuth
    lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {

        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_contact_list, container, false)

        recyclerView = view.findViewById(com.example.sayan.locationtracking.R.id.rv)
        fab=view.findViewById(R.id.floatingActionButton)


        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!

        databaseReference = FirebaseDatabase.getInstance().getReference("contacts")


        fab.setOnClickListener {
            // Click action
            save()  // save data to firebase database

        }

        val linearLayoutManager = LinearLayoutManager(MapNewActivity().getInstance())
        recyclerView.setLayoutManager(linearLayoutManager)
        adapter = ListAdapter(activity, getContacts())
        recyclerView.adapter = adapter

        return view
    }


    private fun save()      // code to save data in firebase database
    {
        val listDetails:List<Persondetails> = adapter.list

        databaseReference.setValue(listDetails)
    }

    private fun getContacts(): List<Persondetails>  //Using content resolver we get list of contacts
    {
        val list = ArrayList<Persondetails>()
        try
        {
            val cursor = activity!!.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
            cursor!!.moveToFirst()
            while (cursor!!.moveToNext())
            {
                list.add(Persondetails(cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                )), cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))))
            }


            for (i in list.indices)
            {
                list[i].checked = false
                list[i].setVisibility(View.GONE)
            }
        } catch (e: Exception) {
            Log.e("My ERROR", e.message)
        }

        return list
    }

    public fun getListAdapter():ListAdapter
    {
        return adapter
    }
}