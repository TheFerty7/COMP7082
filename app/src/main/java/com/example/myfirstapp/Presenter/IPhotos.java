package com.example.myfirstapp.Presenter;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;

public interface IPhotos {
    // logic to update photo in your way, must include caption
    void updatePhoto(String path, String caption);

    // get photo location info
    List<Address> getLocationInformation(Geocoder geocoder, Location location) throws IOException;
}
