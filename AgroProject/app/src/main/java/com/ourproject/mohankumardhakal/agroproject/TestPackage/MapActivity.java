package com.ourproject.mohankumardhakal.agroproject.TestPackage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.ourproject.mohankumardhakal.agroproject.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements LocationListener {
    protected LocationManager locationManager;
    TextView currentPlace;
    double lat1, lat2, long1, long2;
    String pointA = "", pointB = "";
    TextView distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        final TextView txtVw = findViewById(R.id.placeName);
        currentPlace = findViewById(R.id.currentPlace);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        distance = findViewById(R.id.distance);
        //checks if permission isn't granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            /* TODO: Consider calling
                ActivityCompat#requestPermissions
             here to request the missing permissions, and then overriding
               public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                      int[] grantResults)
             to handle the case where the user grants the permission. See the documentation
             for ActivityCompat#requestPermissions for more details.
           */
            //#take permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            //if permission is already granted
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, MapActivity.this);
        }

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setCountry("NP")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        autocompleteFragment.setFilter(filter);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng queriedLocation = place.getLatLng();
                Log.v("Latitude1 is", "" + queriedLocation.latitude);
                Log.v("Longitude1 is", "" + queriedLocation.longitude);
                setLatLong(queriedLocation.latitude, queriedLocation.latitude, 0, String.valueOf(place.getName()));
                txtVw.setText(place.getName());
                distance.setText(String.valueOf(calculateDistance() / 1000));
            }


            @Override
            public void onError(Status status) {
                txtVw.setText(status.toString());
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        String mylocation = getCurrentLocationName(location.getLatitude(), location.getLongitude());
        currentPlace.setText(mylocation);
        setLatLong(location.getLatitude(), location.getLongitude(), 1, mylocation);
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

    //listens once the premission is granted or not
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, MapActivity.this);
                Log.i("Permission Requestd", "permission grandted");
            } else Log.i("Permission Requestd", "permission not grandted");

        }
    }

    public void setLatLong(double lat, double lang, int i, String name) {
        if (i == 0) {
            this.lat1 = lat;
            this.long1 = lang;
            this.pointA = name;
        } else if (i == 1) {
            this.lat2 = lat;
            this.long2 = lang;
            this.pointB = name;
        }
    }


    //determines the location using lat long of current address
    public String getCurrentLocationName(double lat, double lang) {
        Geocoder myLocation = new Geocoder(this, Locale.getDefault());
        String addressStr = "";
        try {
            List<Address> myList = myLocation.getFromLocation(lat, lang, 1);
            Address address = (Address) myList.get(0);
            addressStr = address.getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressStr;
    }

    public float calculateDistance() {
        //for first location
        Location locationA = new Location("Point A");
        locationA.setLatitude(lat1);
        locationA.setLongitude(long1);
        //for second location
        Location locationB = new Location("Point B");
        locationB.setLatitude(lat2);
        locationB.setLongitude(long2);
        float distance = locationA.distanceTo(locationB);
        return distance;
    }
}

