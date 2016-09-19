package com.example.jayjay.informer;

/**
 * Created by JayJay on 19/09/2016.
 */

import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;


public class PollingStations extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.onMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private GoogleMap mMap;

    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    private int curMapTypeIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.polling_stations);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        initListeners();
    }

    private void initListeners() {
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    private void initCamera(Location location) {
        CameraPosition position = CameraPosition.builder()
                .target(new LatLng(location.getLatitude(),
                        location.getLongitude()))
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);

        mMap.setMapType(MAP_TYPES[curMapTypeIndex]);
        mMap.setTrafficEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        initCamera(mCurrentLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this);

        String address = "";
        try {
            address = geocoder
                    .getFromLocation(latLng.latitude, latLng.longitude, 1)
                    .get(0).getAddressLine(0);
        } catch (IOException e) {
        }

        return address;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {

        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title(getAddressFromLatLng(latLng));

        options.icon(BitmapDescriptorFactory.defaultMarker());
        mMap.addMarker(options);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title(getAddressFromLatLng(latLng));

        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher)));

        mMap.addMarker(options);
    }

    private void drawOverlay(LatLng location, int width, int height) {
        GroundOverlayOptions options = new GroundOverlayOptions();
        options.position(location, width, height);

        options.image(BitmapDescriptorFactory
                .fromBitmap(BitmapFactory
                        .decodeResource(getResources(),
                                R.mipmap.ic_logo)));
        mMap.addGroundOverlay(options);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }
}


//import android.location.Location;
//import android.support.v4.app.FragmentActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//public class PollingStations extends FragmentActivity implements
//        OnMapReadyCallback,
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        GoogleMap.OnMarkerDragListener,
//        GoogleMap.OnMapLongClickListener,
//        View.OnClickListener{
//
//    //Our Map
//    private GoogleMap mMap;
//
//    //To store longitude and latitude from map
//    private double longitude;
//    private double latitude;
//
//    //Buttons
//    private ImageButton buttonSave;
//    private ImageButton buttonCurrent;
//    private ImageButton buttonView;
//
//    //Google ApiClient
//    private GoogleApiClient googleApiClient;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.polling_stations);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        //Initializing googleapi client
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//
//        //Initializing views and adding onclick listeners
//        buttonSave = (ImageButton) findViewById(R.id.buttonSave);
//        buttonCurrent = (ImageButton) findViewById(R.id.buttonCurrent);
//        buttonView = (ImageButton) findViewById(R.id.buttonView);
//        buttonSave.setOnClickListener(this);
//        buttonCurrent.setOnClickListener(this);
//        buttonView.setOnClickListener(this);
//    }
//
//    @Override
//    protected void onStart() {
//        googleApiClient.connect();
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        googleApiClient.disconnect();
//        super.onStop();
//    }
//
//    //Getting current location
//    private void getCurrentLocation() {
//        mMap.clear();
//        //Creating a location object
//        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        if (location != null) {
//            //Getting longitude and latitude
//            longitude = location.getLongitude();
//            latitude = location.getLatitude();
//
//            //moving the map to location
//            moveMap();
//        }
//    }
//
//    //Function to move the map
//    private void moveMap() {
//        //String to display current latitude and longitude
//        String msg = latitude + ", "+longitude;
//
//        //Creating a LatLng Object to store Coordinates
//        LatLng latLng = new LatLng(latitude, longitude);
//
//        //Adding marker to map
//        mMap.addMarker(new MarkerOptions()
//                .position(latLng) //setting position
//                .draggable(true) //Making the marker draggable
//                .title("Current Location")); //Adding a title
//
//        //Moving the camera
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//
//        //Animating the camera
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
//
//        //Displaying current coordinates in toast
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        LatLng latLng = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.setOnMarkerDragListener(this);
//        mMap.setOnMapLongClickListener(this);
//    }
//
//    @Override
//    public void onConnected(Bundle bundle) {
//        getCurrentLocation();
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onMapLongClick(LatLng latLng) {
//        //Clearing all the markers
//        mMap.clear();
//
//        //Adding a new marker to the current pressed position
//        mMap.addMarker(new MarkerOptions()
//                .position(latLng)
//                .draggable(true));
//    }
//
//    @Override
//    public void onMarkerDragStart(Marker marker) {
//
//    }
//
//    @Override
//    public void onMarkerDrag(Marker marker) {
//
//    }
//
//    @Override
//    public void onMarkerDragEnd(Marker marker) {
//        //Getting the coordinates
//        latitude = marker.getPosition().latitude;
//        longitude = marker.getPosition().longitude;
//
//        //Moving the map
//        moveMap();
//    }
//
//    @Override
//    public void onClick(View v) {
//        if(v == buttonCurrent){
//            getCurrentLocation();
//            moveMap();
//        }
//    }
//}