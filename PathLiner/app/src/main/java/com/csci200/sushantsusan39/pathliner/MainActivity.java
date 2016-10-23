package com.csci200.sushantsusan39.pathliner;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {
    Firebase mFirebase;
    LocationManager mLocationManager;
    Location mLocation;
    private Button mStartTrackingButton, mEndTrackingButton, mSeeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        mFirebase = new Firebase("https://exam2001.firebaseio.com");
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mStartTrackingButton = (Button) findViewById(R.id.startrecord);
        mEndTrackingButton = (Button) findViewById(R.id.endtrack);
        mSeeButton = (Button) findViewById(R.id.seemap);

        mStartTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStartTracking();
            }
        });

        mEndTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doEndTracking();
            }
        });

        mSeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMaps();
            }
        });

    }


    private boolean checkLocationStatus(){
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return true;
        } else {
            String toastMessage = "Please turn your GPS location";
            Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
            return false;
        }
    }
    public  Location getLocationData(){
        if (!checkLocationStatus()) {
            return null;
        } else {
            try {
                mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) == null ?
                        mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) :
                        mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return mLocation;
            }catch (SecurityException e){
                return null;
            }
        }
    }

    public void doStartTracking(){
        mLocation = getLocationData();
        Double latitudeData = mLocation.getLatitude();
        Double longitudeData = mLocation.getLongitude();
        final Firebase latStart = mFirebase.child("StartLatitude");
        final Firebase longStart = mFirebase.child("EndLatitude");
        latStart.setValue(latitudeData);
        longStart.setValue(longitudeData);
    }

    public void doEndTracking(){
        mLocation = getLocationData();
        Double latitudeData = mLocation.getLatitude();
        Double longitudeData = mLocation.getLongitude();
        final Firebase latEnd = mFirebase.child("StartLongitude");
        final Firebase longEnd = mFirebase.child("EndLongitude");
        latEnd.setValue(latitudeData);
        longEnd.setValue(longitudeData);

    }

    public void showMaps() {
        Intent popRest = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(popRest);
    }


}
