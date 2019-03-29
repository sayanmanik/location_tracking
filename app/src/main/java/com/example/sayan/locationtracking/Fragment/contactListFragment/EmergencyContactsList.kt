package com.example.sayan.locationtracking.Fragment.contactListFragment

import com.example.sayan.locationtracking.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sayan.locationtracking.Activity.MapNewActivity
import com.example.sayan.locationtracking.Adapter.ListAdapter
import com.example.sayan.locationtracking.Details.Persondetails
import com.google.firebase.database.*

class EmergencyContactsList : Fragment(), EmergencyList
{

    lateinit var emergencyListInterface: EmergencyList

    lateinit var recyclerView: RecyclerView
    lateinit var adapter : ListAdapter
    lateinit var databaseReference:DatabaseReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        val view = inflater!!.inflate(R.layout.fragment_emergency_list, container, false)

        recyclerView = view.findViewById(com.example.sayan.locationtracking.R.id.rv)
        databaseReference = FirebaseDatabase.getInstance().getReference("contacts")


        val linearLayoutManager = LinearLayoutManager(MapNewActivity().getInstance())
        recyclerView.setLayoutManager(linearLayoutManager)

        val activity= getActivity()
       // val list=showData()
        emergencyListInterface = this
        showData()
//        adapter = ListAdapter(activity, showData())

//        recyclerView.adapter = adapter

        return view

    }

    override fun sendContact(list: java.util.ArrayList<Persondetails>?) {
        adapter = ListAdapter(activity, list)
        recyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

       /* recyclerView = view.findViewById(com.example.sayan.locationtracking.R.id.rv)
        databaseReference = FirebaseDatabase.getInstance().getReference("contacts")


        val linearLayoutManager = LinearLayoutManager(MapNewActivity().getInstance())
        recyclerView.setLayoutManager(linearLayoutManager)

        adapter = ListAdapter(MapNewActivity().getInstance(), showData())
        recyclerView.adapter = adapter */

    }

    fun showData()
    {
        var list= ArrayList<Persondetails>()

        databaseReference.addValueEventListener((object : ValueEventListener
        {
            override fun onDataChange(dataSnapshot: DataSnapshot)
            {
                for (postSnapshot in dataSnapshot.children)
                {

                    val details = postSnapshot.getValue(Persondetails::class.java)

                    if (details != null)
                    {
                        list.add(details)
                    }
                }
                emergencyListInterface.sendContact(list)
               // val size=list.size
                Log.v("DETAILS_1", list.toString()+" list size :"+list.size)
            }

            override fun onCancelled(databaseError: DatabaseError)
            {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }))

//        Log.v("DETAILS_2", list.toString()+" list size :"+list.size)
//        return list
    }
}