package com.example.myfirstapp.Presenter;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivityPresenter implements IPhotos, ILocation {
    @Override
    public void updatePhoto(String path, String caption) {
        String[] attr = path.split("_");
        if (attr.length >= 3) {
            File to = new File(attr[0] + "_" + caption + "_" + attr[2] + "_" + attr[3] + ".jpeg");
            File from = new File(path);
            from.renameTo(to);
        }
    }

    @Override
    public List<Address> getLocationInformation(Geocoder geocoder, Location location) throws IOException {
        List<Address> addresses = geocoder.getFromLocation(
                location.getLatitude(), location.getLongitude(), 1
        );
        return addresses;
    }

    @Override
    public void setLocationInformation(List<Address> addresses, TextView latitudeTv, TextView longitudeTv, TextView locationTv) {
        latitudeTv.setText("Latitude: " + String.valueOf(addresses.get(0).getLatitude()).substring(0, 8) + "");
        longitudeTv.setText("Longitude: " + String.valueOf(addresses.get(0).getLongitude()).substring(0, 8) + "");
        locationTv.setText("Location: " + addresses.get(0).getAddressLine(0) + "");
    }

    @Override
    public Geocoder getGeocodeInformation(Activity page) {
        Geocoder geocoder = new Geocoder(page, Locale.getDefault());
        return geocoder;
    }
}
