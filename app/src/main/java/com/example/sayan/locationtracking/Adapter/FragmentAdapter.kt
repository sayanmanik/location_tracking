package com.example.sayan.locationtracking.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sayan.locationtracking.Details.Persondetails
import kotlinx.android.synthetic.main.fragment_item_contact.view.*

/** Created by 1605476 and 29-Sep-18
 **/

class FragmentAdapter(val items:MutableList<Persondetails>,val ctx: Context) : RecyclerView.Adapter<FragmentAdapter.MyViewHolder>()
{
    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        return  MyViewHolder(LayoutInflater.from(ctx).inflate(com.example.sayan.locationtracking.R.layout.fragment_item_contact,parent,false))
    }

    override
    fun getItemCount(): Int
    {
        return items.size;
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override
    fun onBindViewHolder(holder: FragmentAdapter.MyViewHolder, position: Int)
    {
        holder.tvName.text = items[position].getName()
        holder.tvNumber.text = items[position].getPhone();
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class MyViewHolder (v: View):RecyclerView.ViewHolder(v)
    {
        val tvName=v.name_contact;
        val tvNumber=v.phone_contact;
        val foreGround=v.view_foreground;
        val backGround=v.view_background;

    }


    fun removeItem(pos:kotlin.Int)
    {
        items.removeAt(pos)
        notifyItemChanged(pos)
    }

    fun restoreItem(item:Persondetails,pos:kotlin.Int)
    {
        items.add(pos,item)
        notifyItemChanged(pos)
    }
}