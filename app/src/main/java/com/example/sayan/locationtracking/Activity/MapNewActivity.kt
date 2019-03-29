package com.example.sayan.locationtracking.Activity


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.example.sayan.locationtracking.Fragment.MapFragment.MyMapFragment
import com.example.sayan.locationtracking.Fragment.contactListFragment.ContactListFragment
import com.example.sayan.locationtracking.Fragment.contactListFragment.EmergencyContactsList
import com.example.sayan.locationtracking.R
import kotlinx.android.synthetic.main.activity_map_new.*
import kotlinx.android.synthetic.main.app_bar_map_new.*

class MapNewActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{

    public lateinit var contextApplication : Context

    public lateinit var  searchView: SearchView

    public lateinit var manager: FragmentManager

    public lateinit var fragmentTransaction: FragmentTransaction

   public var mapNewActivity : MapNewActivity? = null



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_new)
        setSupportActionBar(toolbar)

        contextApplication=applicationContext

        manager = supportFragmentManager
        fragmentTransaction = manager.beginTransaction()


        fragmentTransaction.replace(R.id.frameContainer, MyMapFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        else
        {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.map_new, menu)

        val fragment = ContactListFragment()

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search)
                .actionView as SearchView
        assert(searchManager != null)
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(componentName))
        searchView.setMaxWidth(Integer.MAX_VALUE)

        // listening to search query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                fragment.getListAdapter().getFilter().filter(query)

                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                fragment.getListAdapter().getFilter().filter(query)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
         return super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_all_contacts ->
            {
                manager = supportFragmentManager
                fragmentTransaction = manager.beginTransaction()
                fragmentTransaction.replace(R.id.frameContainer,ContactListFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()

            }
            R.id.nav_favorites ->
            {
                manager = supportFragmentManager
                fragmentTransaction = manager.beginTransaction()
                fragmentTransaction.replace(R.id.frameContainer,EmergencyContactsList())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
            R.id.log_out ->
            {
                

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    @Synchronized
    fun getInstance(): MapNewActivity {
        if (mapNewActivity == null) {
            mapNewActivity = MapNewActivity()

        }

        return MapNewActivity()
    }
}
