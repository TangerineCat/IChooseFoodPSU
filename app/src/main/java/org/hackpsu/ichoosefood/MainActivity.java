package org.hackpsu.ichoosefood;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import Fragments.ListFragment;

public class MainActivity extends ActionBarActivity {

    static int position =0;
    Menu mMenu;
    ListFragment mlistFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        mlistFragment = ListFragment.newInstance(position++);
        fragmentManager.beginTransaction()
                .replace(R.id.container, mlistFragment)
                .commit();

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        mMenu = menu;
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//        actionBar.setDisplayShowTitleEnabled(true);
//        return true;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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
        else if(id == R.id.add_member) {
            ArrayList<String> meh = mlistFragment.getNames();
            meh.add("Person " + position++);
            mlistFragment.setNames(meh);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .detach(mlistFragment)
                    .commit();

            fragmentManager.beginTransaction()
                    .attach(mlistFragment)
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ChooseLocation(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
