package com.example  . sayan.locationtracking.Fragment.MapFragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.sayan.locationtracking.GPSTracker
import com.example.sayan.locationtracking.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_sign_up.*

class MyMapFragment : Fragment(),OnMapReadyCallback
{

    lateinit var locationManger : LocationManager

    var map: GoogleMap? = null

    var marker: Marker? = null

    lateinit var listener: GPSTracker
    var lat: Double = 0.0
    var lng:Double = 0.0

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        locationManger = activity?.getSystemService(LOCATION_SERVICE) as LocationManager

        listener = GPSTracker(context, getActivity())
        if (listener.canGetLocation()) {

            lat = listener.getLatitude()
            lng = listener.getLongitude()

        } else {
            listener.showSettingsAlert()
        }
        activity!!.startService(Intent(activity, GPSTracker::class.java))


       locationManger.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, listener)
       locationManger.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, listener)


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        Log.d("TAG", "Hello");
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)




        if (activity != null)
        {
            val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)

        }


    }
        /**
         *   implemented methods for onMapReadyCallback interface
         *
         */

        @SuppressLint("MissingPermission")

        override fun onMapReady(p0: GoogleMap?)
        {
            map = p0

            map?.setMyLocationEnabled(true)

            val latLng = LatLng(lat,lng)
            marker = map?.addMarker(MarkerOptions().position(latLng).title("Your Location"))
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f))

        }


    fun moveVehicle(finalPosition: Location)
    {
        val startPosition = marker?.position
        val handler = Handler()
        val start = SystemClock.uptimeMillis()
        val durationInMilis: Long = 300
        val hideMarker = false

        handler.post(object : Runnable {
            internal var elapsed: Long = 0
            internal var t: Long = 0
            override fun run() {
                elapsed = SystemClock.uptimeMillis() - start
                t = elapsed / durationInMilis

//                val newLatLng = LatLng(startPosition!!.latitude * (1 - t) + finalPosition.latitude * t, startPosition.longitude * (1 - t) + finalPosition.longitude * t)
        //        marker?.position  = newLatLng

                if (t < 1) {
                    handler.postDelayed(this, 16)
                } else {
                    if (hideMarker) {
                        marker?.isVisible  = false

                    } else {
                        marker?.isVisible  = true
                    }
                }

            }
        })
    }

    fun getCurrentMarker() : Marker?
    {
        if(marker == null)
        {
            Log.e("MAP_TAG","Marker value null")
        }

        return marker
    }

    override fun onStop() {
        super.onStop()

        activity?.stopService(Intent(activity,GPSTracker::class.java))
    }
}