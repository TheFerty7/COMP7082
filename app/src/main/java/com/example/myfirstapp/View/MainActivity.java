package com.example.myfirstapp.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.myfirstapp.Presenter.MainActivityPresenter;
import com.example.myfirstapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int SEARCH_ACTIVITY_REQUEST_CODE = 2;
    String currentPhotoPath;
    private ArrayList<String> photos = null;
    private int index = 0;

    // MVP Activity
    private MainActivityPresenter mainActivityPresenter;

    // Location Client Information
    FusedLocationProviderClient mFusedLocationClient;
    TextView latitudeTextView, longitTextView, locationTextView;
    int PERMISSION_ID = 44;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photos = findPhotos(new Date(Long.MIN_VALUE), new Date(), "");

        if (photos.size() == 0) {
            displayPhoto(null);
        } else {
            displayPhoto(photos.get(index));
        }

        latitudeTextView = findViewById(R.id.latTextView);
        longitTextView = findViewById(R.id.lonTextView);
        locationTextView = findViewById(R.id.tvLocation);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
        getLastLocation();

        mainActivityPresenter = new MainActivityPresenter();
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting location
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            try {
                                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1
                                );

                                latitudeTextView.setText("Latitude: " + String.valueOf(addresses.get(0).getLatitude()).substring(0, 8) + "");
                                longitTextView.setText("Longitude: " + String.valueOf(addresses.get(0).getLongitude()).substring(0, 8) + "");
                                locationTextView.setText("Location: " + addresses.get(0).getAddressLine(0) + "");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    public Boolean checkCaption(){
        String cap = ((EditText)findViewById(R.id.etCaption)).getText().toString();
        return true;
    }

    public void searchPhoto(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivityForResult(intent, SEARCH_ACTIVITY_REQUEST_CODE);
    }

    public void sharePhoto(View view){
        try {
            Uri imageUri = FileProvider.getUriForFile(
                    MainActivity.this,
                    "com.example.myfirstapp.fileprovider", //(use your app signature + ".provider" )
                    new File(photos.get(index)));
            Intent intent = new Intent(Intent.ACTION_SEND);
            TextInputLayout caption = findViewById(R.id.tvCaption);
            intent.putExtra(Intent.EXTRA_TEXT, caption.getEditText().getText());
            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
            intent.setType("image/jpeg");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                DateFormat format = new SimpleDateFormat("yyyy‐MM‐dd HH:mm:ss");
                Date startTimestamp , endTimestamp;
                try {
                    String from = (String) data.getStringExtra("STARTTIMESTAMP");
                    String to = (String) data.getStringExtra("ENDTIMESTAMP");
                    startTimestamp = format.parse(from);
                    endTimestamp = format.parse(to);
                } catch (Exception ex) {
                    startTimestamp = null;
                    endTimestamp = null;
                }
                String keywords = (String) data.getStringExtra("KEYWORDS");
                index = 0;
                photos = findPhotos(startTimestamp, endTimestamp, keywords);
                if (photos.size() == 0) {
                    displayPhoto(null);
                } else {
                    displayPhoto(photos.get(index));
                }
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageView mImageView = (ImageView) findViewById(R.id.ivGallery);
            mImageView.setImageBitmap(BitmapFactory.decodeFile(currentPhotoPath));
            photos = findPhotos(new Date(Long.MIN_VALUE), new Date(), "");
        }
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.println("IO Ex");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.myfirstapp.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            System.out.println("Resolve is null");
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "_caption_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private ArrayList<String> findPhotos(Date startTimestamp, Date endTimestamp, String keywords) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/Android/data/com.example.myfirstapp/files/Pictures");
        ArrayList<String> photos = new ArrayList<String>();
        ArrayList<File> fList = new ArrayList<File>(Arrays.asList(Objects.requireNonNull(file.listFiles())));
        if (fList != null) {
            // Using lambda java streams - functional programming
            fList.forEach(f -> {
                if (((startTimestamp == null && endTimestamp == null) || (f.lastModified() >= startTimestamp.getTime()
                        && f.lastModified() <= endTimestamp.getTime())
                ) && (keywords == "" || f.getPath().contains(keywords)))
                    photos.add(f.getPath());
            });
        }
        return photos;
    }

    public void scrollPhotos(View v) {
        mainActivityPresenter.updatePhoto(photos.get(index), ((EditText) findViewById(R.id.etCaption)).getText().toString());
        photos = findPhotos(new Date(Long.MIN_VALUE), new Date(), "");
        switch (v.getId()) {
            case R.id.btnPrev:
                if (index > 0) {
                    index--;
                }
                break;
            case R.id.btnNext:
                if (index < (photos.size() - 1)) {
                    index++;
                }
                break;
            default:
                break;
        }
        displayPhoto(photos.get(index));
    }

    private void displayPhoto(String path) {
        ImageView iv = (ImageView) findViewById(R.id.ivGallery);
        TextView tv = (TextView) findViewById(R.id.tvTimestamp);
        EditText et = (EditText) findViewById(R.id.etCaption);
        if (path == null || path == "") {
            iv.setImageResource(R.mipmap.ic_launcher);
            et.setText("");
            tv.setText("");
        } else {
            iv.setImageBitmap(BitmapFactory.decodeFile(path));
            String[] attr = path.split("_");
            et.setText(attr[1]);
            tv.setText(attr[2]);
        }
    }

//    private void updatePhoto(String path, String caption) {
//        String[] attr = path.split("_");
//        if (attr.length >= 3) {
//            File to = new File(attr[0] + "_" + caption + "_" + attr[2] + "_" + attr[3] + ".jpeg");
//            File from = new File(path);
//            from.renameTo(to);
//        }
//    }
}