package com.example.sayan.locationtracking.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.sayan.locationtracking.Details.Persondetails;
import com.example.sayan.locationtracking.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.view.View.GONE;

/**
 * Created by 1605476 and 16-Sep-18
 **/

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> implements Filterable
{
    int pos;
    Context ctx;
    List<Persondetails> persondetails;
    List<Persondetails> details;

    List<Persondetails> listFiltered;
    //ContactsAdapterListener listener;

    public ListAdapter(Context c, List<Persondetails> persondetails) {
        this.ctx = c;
        this.persondetails = persondetails;
        this.listFiltered = persondetails;
        details=new ArrayList<>();

    }

    public ListAdapter(Context ctx) {
        this.ctx = ctx;
    }
    //this.listener = listener;

    /*public void setListener(ContactsAdapterListener l) {
        listener = l;
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position)
    {
        //holder.checkBox.setVisibility(GONE);
        pos=position;
        holder.nameText.setText(persondetails.get(position).getName());
        holder.phoneText.setText(persondetails.get(position).getPhone());

        View v = holder.v;

//        holder.checkBox.setOnCheckedChangeListener(null);

        v.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                Log.d("TAG1", "Long Pressed");

                if(persondetails.get(position).getVisiblity()==GONE&&persondetails.get(position).getChecked()==false)
                {
                    holder.checkBox.setVisibility(View.VISIBLE);
                    holder.checkBox.setChecked(true);

                    persondetails.get(position).setVisibility(View.VISIBLE);
                    persondetails.get(position).setChecked(true);
                    details.add(persondetails.get(position));
                }

                else
                {
                    holder.checkBox.setVisibility(View.GONE);
                    holder.checkBox.setChecked(false);

                    persondetails.get(position).setVisibility(View.GONE);
                    persondetails.get(position).setChecked(false);
                    details.remove(persondetails.get(position));
                }

                /*holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!persondetails.get(holder.getAdapterPosition()).isChecked) {
                            holder.checkBox.setVisibility(View.VISIBLE);
                            holder.checkBox.setChecked(true);
                        } else {
                            holder.checkBox.setVisibility(View.GONE);
                            holder.checkBox.setChecked(false);
                        }
                    }
                });*/

                //listener.onContactSelected(position);
                return true;
            }
        });
            holder.checkBox.setVisibility(persondetails.get(position).getVisiblity());
            holder.checkBox.setChecked(persondetails.get(position).getChecked());

    }

    public List<Persondetails> getDetails()
    {
        notifyDataSetChanged();
        return details;
    }

    @Override
    public int getItemCount() {
        return persondetails.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    listFiltered = persondetails;
                } else {
                    List<Persondetails> newListFiltered = new ArrayList<>();

                    for (Persondetails details : persondetails) {
                        if (details.getName().toLowerCase().contains(charString.toLowerCase()) || details.getPhone().contains(constraint)) {
                            newListFiltered.add(details);
                        }
                    }

                    listFiltered = newListFiltered;
                }

                FilterResults results = new FilterResults();
                results.values = listFiltered;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                persondetails = (List<Persondetails>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView phoneText;
        public  CheckBox checkBox;
        View v;

        public MyViewHolder(View itemView) {
            super(itemView);
            v = itemView;

            checkBox = itemView.findViewById(R.id.check);
            nameText = itemView.findViewById(R.id.name_contact);
            phoneText = itemView.findViewById(R.id.phone_contact);
        }
/*        public static int getPos()
        {
            return pos;
        }

    }
    public interface ContactsAdapterListener
    {
        void onContactSelected(int pos);
    }*/
    }

    public List<Persondetails> getList()
    {
        return details;
    }
    public int getPos()
    {
        return pos;
    }
}