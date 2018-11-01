package com.example.sayan.locationtracking.Details;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.v7.widget.RecyclerView;

import io.reactivex.annotations.NonNull;

/**
 * Created by 1605476 and 26-Aug-18
 **/

@Entity(tableName = "contacts")

public class Persondetails
{
    @ColumnInfo(name="full_name")
    public String name;
    @ColumnInfo(name="number")
    public String phone;
    public int position;
    public boolean isChecked=false;
    public int vis;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="did")
    public int id;

    public Persondetails()
    {

    }
    public Persondetails(@NonNull String name,@NonNull String phone)
    {
        this.name=name;
        this.phone=phone;
    }


    public void setVisibility(int vis)
    {
        this.vis=vis;
    }

    public int getVisiblity()
    {
        return vis;
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    public String getName()
    {
        return name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "Phone: " + this.phone;
    }
}