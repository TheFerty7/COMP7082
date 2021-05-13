package com.example.myfirstapp.Presenter;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class LocationAdapter implements ILocation {
    IPhotos photos;

    public LocationAdapter(IPhotos photos) {
        this.photos = photos;
    }

    @Override
    public List<Address> getLocationInformation(Geocoder geocoder, Location location) throws IOException {
        return photos.getLocationInformation(geocoder, location);
    }

    @Override
    public void setLocationInformation(List<Address> addresses, TextView latitudeTv, TextView longitudeTv, TextView locationTv) { }

    @Override
    public Geocoder getGeocodeInformation(Activity page) {
        return null;
    }
}
