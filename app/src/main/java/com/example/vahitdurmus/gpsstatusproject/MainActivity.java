package com.example.vahitdurmus.gpsstatusproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GpsStatus.Listener {


    LocationManager locationManager;
    LocationListener locationListener;

    TextView numberofgps;
    TextView numberofcgps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberofcgps=(TextView)findViewById(R.id.numberofconnectedgps);
        numberofgps=(TextView)findViewById(R.id.numberofgps);

        locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.addGpsStatusListener(this);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if (location.getExtras() != null) {
                     int satellites = location.getExtras().getInt("satellites");
                } else {
                     int satellites = 0;
                }

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
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

    @Override
    public void onGpsStatusChanged(int event) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        int satellites = 0;
        int satellitesInFix = 0;
        int timetofix = locationManager.getGpsStatus(null).getTimeToFirstFix();

        for (GpsSatellite sat : locationManager.getGpsStatus(null).getSatellites()) {
            if(sat.usedInFix()) {
                satellitesInFix++;
            }
            satellites++;
        }
        numberofgps.setText(String.valueOf(satellites));
        numberofcgps.setText(+""+String.valueOf(satellitesInFix));



    }
}
