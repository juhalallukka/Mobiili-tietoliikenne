package com.example.a1_1_gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;

    private FusedLocationProviderClient fLPClient;
    private TextView textV_GPS;

    private double currentLatitude;
    private double currentLongitude;
    private  List<Address> locationArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        textV_GPS = findViewById(R.id.textView_GPS);
        textV_GPS.setOnClickListener(this);

        fLPClient = LocationServices.getFusedLocationProviderClient(this);

        checkPermissions();

        getLocation();
        //findCityByLocation(currentLatitude,currentLongitude); //tätä kutsutaan jos klikkaa tekstiä

    }




    public void findCityByLocation(double latitude, double longitude)
    {
        Geocoder geocoder = new Geocoder(this);

        try {

           locationArray = geocoder.getFromLocation(latitude,longitude, 1);

           String city = locationArray.get(0).getLocality();

           //textV_GPS.setText(city);
            textV_GPS.append("\n"+city);


        } catch (IOException e) {
            e.printStackTrace();
            //textV_GPS.setText("hups");

        }



    }



    public void getLocation()
    {
        fLPClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){



                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();

                    String latitudeString = String.format("%.2f", currentLatitude);
                    String longitudeString = String.format("%.2f", currentLongitude);

                    String textToTextView = latitudeString +", "+longitudeString;

                    textV_GPS.setText(textToTextView);

                    //findCityByLocation(currentLatitude,currentLongitude)
                }

            }
        });
    }


    public void checkPermissions()
    {
        //textV_GPS.setText("permission");

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == textV_GPS.getId())
        {
            //getLocation();
            findCityByLocation(currentLatitude, currentLongitude);
        }

    }
}
