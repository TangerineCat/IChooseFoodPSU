package org.hackpsu.ichoosefood;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderApi;

public class MapActivity extends ActionBarActivity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    static final LatLng PSU = new LatLng(40.7948376, -80.8653124);
    private GoogleMap map;
    private Marker loc;
    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mCurrentLocation;
    FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // What does this even do?
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        loc = map.addMarker(new MarkerOptions()
            .position(PSU)
            .title("Penn State")
            .snippet("Code made here!"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(PSU, 2000));

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        // Zoom in, animating the camera :D
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 20, null);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000 * 10);
        mLocationRequest.setFastestInterval(1000 * 5);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnectionSuspended(int i)
    {}


    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) { }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;

        loc.remove();

        LatLng newLoc = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

        loc = map.addMarker(new MarkerOptions()
                .position(newLoc)
                .title("User Location")
                .snippet("Please Work!"));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(newLoc, 2000));

        // Zoom in, animating the camera :D
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 20, null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    public void getCurrentLocation(View view) {
        // uncomment following line later
        loc.remove();

        mCurrentLocation = fusedLocationProviderApi.getLastLocation(mGoogleApiClient);


        LatLng newLoc = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

        loc = map.addMarker(new MarkerOptions()
                .position(newLoc)
                .title("User Location")
                .snippet("Please Work!"));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(newLoc, 2000));

        // Zoom in, animating the camera :D
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 20, null);
    }

    public void LocationChosen(View view) {
        Intent intent = new Intent(this, ChooseActivity.class);
        LatLng ll = loc.getPosition();
        intent.putExtra("LONGITUDE",ll.longitude);
        intent.putExtra("LATITUDE",ll.latitude);

        startActivity(intent);
        // TODO: pass down coordinates
    }
}
