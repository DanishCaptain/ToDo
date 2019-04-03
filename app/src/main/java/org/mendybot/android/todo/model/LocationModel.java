package org.mendybot.android.todo.model;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public final class LocationModel {
    private static LocationModel singleton;
    private Activity activity;
    private double longitude;
    private double latitude;
    private String country;
    private String city;
    private String userAgent;

    private LocationModel() {
    }

    public void init(Activity activity) {
        this.activity = activity;


        try {
            LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
//                return;
            }
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            Location location = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                Geocoder gcd = new Geocoder(activity, Locale.getDefault());
                try {
                    List<Address> addressList = gcd.getFromLocation(latitude, longitude, 100);
                    for (Address address : addressList) {
                        country = address.getCountryName();
                        city = address.getLocality();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch(SecurityException t) {
            // need access
        } catch(Throwable t) {
            System.out.println(t.getMessage());
        }
        userAgent = System.getProperty("http.agent");

    }

    public synchronized static LocationModel getInstance() {
        if (singleton == null) {
            singleton = new LocationModel();
        }
        return singleton;
    }

}
