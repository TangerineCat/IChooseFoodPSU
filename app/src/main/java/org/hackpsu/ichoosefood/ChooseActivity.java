package org.hackpsu.ichoosefood;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ChooseActivity extends ActionBarActivity {
    private static final String CONSUMER_KEY = "Ku0Ghowg8VFVxejRiZduGg";
    private static final String CONSUMER_SECRET = "E9I7SF0wIbJUrGaJOyXqZseRzHM";
    private static final String TOKEN = "1wSi0yA2wUBZZwet8jBhuiIGJc5murOp";
    private static final String TOKEN_SECRET = "nPqPpqXzVWA7SfUnvUVg41CDYPM";
    YelpAPI yelpApi = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);


    public class NewArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public NewArrayAdapter(Context context, String[] values) {
            super(context, R.layout.rowlayout, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
            TextView nameView = (TextView) rowView.findViewById(R.id.name);
            TextView categoryView = (TextView) rowView.findViewById(R.id.categories);
            TextView distanceView = (TextView) rowView.findViewById(R.id.distance);
            ImageView ratingView = (ImageView) rowView.findViewById(R.id.rating);
            // reuse views

            String s = values[position];
            JSONParser parser = new JSONParser();
            JSONObject obj = null;
            try {
                obj = (JSONObject) parser.parse(s);
            }
            catch (ParseException pe){
                System.out.println("Error: could not parse JSON response:");
                System.out.println(s);
            }

            nameView.setText(obj.get("name").toString());
            String categories = obj.get("categories").toString();
            distanceView.setText(obj.get("distance").toString());
            categoryView.setText(categories);
            try {
                URL url = new URL(obj.get("rating_url").toString());
                HttpGet httpRequest = null;

                httpRequest = new HttpGet(url.toURI());

                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse response = (HttpResponse) httpClient.execute(httpRequest);
                HttpEntity entity = response.getEntity();
                BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
                InputStream input = b_entity.getContent();

                Bitmap bitmap = BitmapFactory.decodeStream(input);

                ratingView.setImageBitmap(bitmap);
            } catch (Exception ex){
                // boo fucking hoo
            }

            return rowView;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        double lat,lng;
        String[] dataarray = new String[6];
        if (extras != null){
            lat = extras.getDouble("LATITUDE");
            lng = extras.getDouble("LONGITUDE");
        }
        else {
            lat = 40.7961;
            lng = -77.8628;
        }

        int entries = 0;
        int radius = -500;
        JSONArray businesses = null;
        while(entries < 6 && radius < 40000) {
            radius += 1500;
            String searchResponseJson = yelpApi.searchNearbyLocation(lng, lat, radius);
            JSONParser parser = new JSONParser();
            JSONObject response = null;
            try {
                response = (JSONObject) parser.parse(searchResponseJson);
            } catch (ParseException pe) {
                System.out.println("Error: could not parse JSON response:");
                System.out.println(searchResponseJson);
                //System.exit(1);
            }
            businesses = (JSONArray) response.get("businesses");
            entries = businesses.size();
        }

        int[] indices = new int[entries];
        for (int i = 0; i< entries; i++){
            indices[i] = i;
        }
        shuffleArray(indices);

        for (int i = 0; i<6 && i< entries; i++)
        {
            JSONObject business = (JSONObject) businesses.get(indices[i]);
            dataarray[i] = business.toString();
        }
        setContentView(R.layout.activity_choose);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                .add(R.id.container, new PlaceholderFragment()).commit();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose, menu);
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

    // Implementing Fisher–Yates shuffle
    static void shuffleArray(int[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
//    public static class PlaceholderFragment extends Fragment {
//        public PlaceholderFragment() { }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savdInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_display_message,
//                    container, false);
//            return rootView;
//        }
//    }
}
