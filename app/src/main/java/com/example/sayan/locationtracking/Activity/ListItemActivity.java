package com.example.sayan.locationtracking.Activity;

import android.app.DialogFragment;
import android.app.Fragment;

import android.app.SearchManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.example.sayan.locationtracking.Adapter.ListAdapter;

import com.example.sayan.locationtracking.App.AppDemo;
import com.example.sayan.locationtracking.Dao.ContactDao;
import com.example.sayan.locationtracking.Details.Persondetails;

import com.example.sayan.locationtracking.Fragment.NewPageFragment;
import com.example.sayan.locationtracking.R;
import com.example.sayan.locationtracking.contactsDatabase.contactsDatabase;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by 1605476 and 25-Aug-18
 **/

public class ListItemActivity extends AppCompatActivity {
    //FrameLayout layout;
    RecyclerView recyclerview;
    ListAdapter adapter;
    private SearchView searchView;
    FloatingActionButton fab;
    contactsDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        fab = findViewById(R.id.fab);
        //layout=findViewById(R.id.frame);
        recyclerview = findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerview.setLayoutManager(layoutManager);
        adapter = new ListAdapter(this, getContacts());

        //database = Room.databaseBuilder(getApplicationContext(),contactsDatabase.class, contactsDatabase.db_name).build();

        //adapter.setListener(ListAdapter.ContactsAdapterListener );
        recyclerview.setAdapter(adapter);

        //fillItems();

        //layout.getForeground().setAlpha(0);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame,new NewPageFragment());
                transaction.commit();*/

                //new DatabaseAsync().execute();

                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                /*Fragment prev = getFragmentManager().findFragmentByTag("dialog");

                if (prev != null) {
                    ft.remove(prev);
                }*/

                ft.addToBackStack(null);
                NewPageFragment dialogFragment = new NewPageFragment();
                dialogFragment.show(ft,"dialog");

                //ImageView resultImage = (ImageView) findViewById(R.id.resultImage);
                //Bitmap resultBmp = BlurBuilder.blur(this, BitmapFactory.decodeResource(getResources(), R.drawable.logo_ssaurel));
                //recyclerview.setImageBitmap(resultBmp);

//                layout.getForeground().setAlpha(220);
                /*Blurry.with(ListItemActivity.this).
                        radius(25).
                        sampling(2).
                        async().
                        animate(500).
                        onto((ViewGroup)findViewById(R.id.relative));*/

            }
        });
    }

    private List<Persondetails> getContacts() {
        List<Persondetails> list = new ArrayList<>();
        try {
            Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                list.add(new Persondetails(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                )), cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
            }


            for (int i = 0; i < list.size(); i++) {
                list.get(i).setChecked(false);
                list.get(i).setVisibility(View.GONE);
            }
        } catch (SQLiteException e) {
            Log.e("My ERROR", e.getMessage());
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_list_item, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }
        if(id==R.id.action_tick)
        {
            save();
        }
        return super.onOptionsItemSelected(item);
    }

    private void save()
    {
        new DatabaseAsync().execute();
    }

    @Override
    public void onBackPressed() {

        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }


    class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids)
        {
            //int pos=adapter.getPos();
            List<Persondetails> persondetails=adapter.getList();
            //Persondetails details=persondetails.get(persondetails);

            //database.contactDao().insert(details);
            Log.d("TAG3",AppDemo.getApp().toString());
            AppDemo.getApp().getDatabase().contactDao().insert(persondetails);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            /*Fragment prev = getFragmentManager().findFragmentByTag("dialog");

            if (prev != null) {
                ft.remove(prev);
            }*/

            ft.addToBackStack(null);
            NewPageFragment dialogFragment = new NewPageFragment();
            dialogFragment.show(ft, "dialog");

        }
    }
}