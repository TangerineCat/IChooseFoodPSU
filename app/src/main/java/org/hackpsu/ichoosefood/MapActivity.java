package org.hackpsu.ichoosefood;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends ActionBarActivity {
    static final LatLng PSU = new LatLng(40.7948376, -77.8653124);
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // What does this even do?
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        Marker pennstate = map.addMarker(new MarkerOptions()
            .position(PSU)
            .title("Penn State")
            .snippet("Code made here!"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(PSU, 2000));

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

    public void getCurrentLocation(View view) {
        // TODO: implement function to get current location
        // TODO: move camera and animate!
    }

    public void LocationChosen(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
        // TODO: pass down coordinates
    }
}
