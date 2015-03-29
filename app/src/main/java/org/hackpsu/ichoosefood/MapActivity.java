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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends ActionBarActivity {
    static final LatLng PSU = new LatLng(40.7948376, -77.8653124);
    private GoogleMap map;
    private Marker loc;

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
        // uncomment following line later
        //loc.remove();
        // LatLng newloc = TODO: implement function to get current location
        //loc = map.addMarker(new MarkerOptions()
//                .position(TODO: NEW POSITION)
//                .title("Penn State")
//                .snippet("Code made here!"));

        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(newloc, 2000));

        // Zoom in, animating the camera :D
        // map.animateCamera(CameraUpdateFactory.zoomTo(10), 20, null);
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
