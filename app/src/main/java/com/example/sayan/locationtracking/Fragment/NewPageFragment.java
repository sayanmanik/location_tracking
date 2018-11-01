package com.example.sayan.locationtracking.Fragment;

import android.arch.lifecycle.Observer;
import android.content.Context;
import com.example.sayan.locationtracking.App.AppDemo;
import com.example.sayan.locationtracking.ItemTouchHelper.RecyclerItemTouchHelper;
import com.example.sayan.locationtracking.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.RelativeLayout;

import com.example.sayan.locationtracking.Adapter.FragmentAdapter;
import com.example.sayan.locationtracking.Details.Persondetails;

import com.example.sayan.locationtracking.ViewModel.ContactViewModel;

import java.util.List;

import static android.view.View.GONE;

/**
 * Created by 1605476 and 25-Aug-18
 **/

public class NewPageFragment extends DialogFragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    Context ctx;
    RecyclerView mrecyclerview;
    ContactViewModel viewModel;
    FragmentAdapter adapter;
    List<Persondetails> details;
    RelativeLayout layout;
    //static NewPageFragment f;

    /*static Fragment newInstance()
    {
        f = new NewPageFragment();

        // Supply num input as an argument.
        //Bundle args = new Bundle();
        //args.putInt("num", num);
        //f.setArguments(args);

        return f;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment, container,
                false);
        ctx = getActivity();

        mrecyclerview = rootView.findViewById(R.id.rvToDoList);
        layout=rootView.findViewById(R.id.relative);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;

        mrecyclerview.setLayoutManager(layoutManager);
        viewModel = ViewModelProviders.of(getActivity()).get(ContactViewModel.class);
        details = viewModel.getAllDetails();
        viewModel.getAllLiveDetails().observe(this, new Observer<List<Persondetails>>() {
            @Override
            public void onChanged(@Nullable List<Persondetails> persondetails) {
                //adapter.getDetails();
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new FragmentAdapter(details, ctx);
        mrecyclerview.setAdapter(adapter);

        if(details.size()>0)
        {
            layout.setVisibility(GONE);
        }

        ItemTouchHelper.SimpleCallback itemTouchhelper = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchhelper).attachToRecyclerView(mrecyclerview);

        return rootView;
    }

    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof FragmentAdapter.MyViewHolder) {
            String name = details.get(viewHolder.getAdapterPosition()).getName();

            final Persondetails itemDetail = details.get(viewHolder.getAdapterPosition());
            final int index = viewHolder.getAdapterPosition();
            AppDemo.getApp().getDatabase().contactDao().delete(itemDetail);
            adapter.removeItem(index);

        }
    }
}