package com.example.myfirstapp.Presenter;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public interface ILocation {
    // gets the location information
    List<Address> getLocationInformation(Geocoder geocoder, Location location) throws IOException;

    // sets the location information
    void setLocationInformation(List<Address> addresses, TextView latitudeTv, TextView longitudeTv, TextView locationTv);

    // gets the geocode location of your type specified
    Geocoder getGeocodeInformation(Activity page);
}
