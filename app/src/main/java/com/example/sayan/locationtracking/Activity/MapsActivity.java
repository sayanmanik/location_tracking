package com.example.sayan.locationtracking.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.sayan.locationtracking.GPSTracker;
import com.example.sayan.locationtracking.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener
{
    private GoogleMap mMap;
    private LocationManager manager;
    GPSTracker listener;
    double lat, lng;
    Context context;
    String number;
    Marker marker;
    Button button_send, button_record;
    public static int VIDEO_TAG = 1000;
    public static int PICK_CONTACT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        context = this;

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        button_send = findViewById(R.id.btn1);
        button_record = findViewById(R.id.btn2);


        reqPermission();

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });

        button_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA);
                File file = getFilepath();
                Uri uri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, VIDEO_TAG);
            }
        });

        listener = new GPSTracker(context, MapsActivity.this);
        if (listener.canGetLocation()) {

            lat = listener.getLatitude();
            lng = listener.getLongitude();

        } else {
            listener.showSettingsAlert();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
    }

    public void moveVehicle(final Marker marker, final Location finalPosition)
    {
        final LatLng startPosition=marker.getPosition();
        final Handler handler=new Handler();
        final long start= SystemClock.uptimeMillis();
        final long durationInMilis=300;
        final boolean hideMarker=false;

        handler.post(new Runnable() {
            long elapsed;
            long t;
            @Override
            public void run()
            {
                elapsed=SystemClock.uptimeMillis()-start;
                t=elapsed/durationInMilis;

                LatLng latLng=new LatLng(startPosition.latitude*(1-t)+finalPosition.getLatitude()*t
                        ,startPosition.longitude*(1-t)+finalPosition.getLongitude()*t);

                marker.setPosition(latLng);

                if(t<1)
                {
                    handler.postDelayed(this,16);
                }
                else
                {
                    if(hideMarker)
                    {
                        marker.setVisible(false);

                    }
                    else
                    {
                        marker.setVisible(true);
                    }
                }

            }
        });
    }


    public void sendMessage(String number)
    {
        String message = "http://maps.google.com/maps?saddr=" + lat + "," + lng;
        SmsManager manager = SmsManager.getDefault();
        StringBuffer buffer = new StringBuffer();
        buffer.append(Uri.parse(message));
        manager.sendTextMessage(number, null, buffer.toString(), null, null);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera

        /*  LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        Geocoder geocoder = new Geocoder(getApplicationContext());

        LatLng latLng = new LatLng(lat, lng);
        marker=mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
        mMap.setMaxZoomPreference(20);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));

        Toast.makeText(MapsActivity.this, "Lat :" + lat + "Lng :" + lng, Toast.LENGTH_SHORT).show();
    }

    public File getFilepath()
    {
        File file = new File("sdcard/video_app");

        if (!file.exists())
        {
            file.mkdir();
        }

        File video_file = new File(file, "sample_video.mp4");
        return video_file;
    }

    public void reqPermission() {
        String[] permission = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.SEND_SMS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission, 1001);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case 1001:
                for (int grper : grantResults) {
                    grper = PackageManager.PERMISSION_GRANTED;
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == VIDEO_TAG)
        {
            if (requestCode == RESULT_OK)
            {
                Toast.makeText(getApplicationContext(), "Successfully Uploaded", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Not Uploaded", Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == PICK_CONTACT)
        {
            if (resultCode == RESULT_OK)
            {
                Uri uri = data.getData();
                Cursor c = managedQuery(uri, null, null, null, null);

                if (c.moveToFirst())
                {
                    String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    String hasPhone = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                    if (hasPhone.equalsIgnoreCase("1"))
                    {
                        Cursor phones =
                                getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);

                        phones.moveToFirst();
                        number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                         sendMessage(number);

                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_maps,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.emergency:
                Intent intent=new Intent(this,ListItemActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location)
    {
        moveVehicle(marker,location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}