package co.ramraj;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends AppCompatActivity implements LocationListener {
    String Dest_lat, Dest_lng, Shop_name, Shop_address, Shop_email, Shop_phone;
    public static Double mLatitude = 0.0, mLongitude = 0.0;
    public static GoogleMap mGoogleMap;
    Double flat, flng, slat, slng;
    TextView distance, txt_shop_name, txt_shop_address, txt_shop_email, txt_shop_phone;
    Polyline line, nav_line;
    Toolbar toolbar;
    private static final long MIN_TIME_BW_UPDATES = 1;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;
    public static Location clocation = null;
    LatLng city1, city2, currentcity;
    String resultkm;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        txt_shop_name = (TextView) findViewById(R.id.txtname);
        txt_shop_address = (TextView) findViewById(R.id.txtaddress);
        txt_shop_email = (TextView) findViewById(R.id.txtemail);
        txt_shop_phone = (TextView) findViewById(R.id.txtphone);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled

                AlertDialog alertDialog = new AlertDialog.Builder(MapActivity.this).create();
                //				alertDialog.setTitle("Your phone does not support GPS or NETWORK access, you can still use the application but some features may not work properly.");
                alertDialog.setTitle("Message");
                alertDialog.setMessage("Please enable the GPS.");//but some features may not work properly
                //alertDialog.setIcon(R.drawable.ic_launcher);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                                            MapActivity.this.finish();
                        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                    }
                });
                alertDialog.show();
            } else {
                //this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        clocation = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (clocation != null) {
                            mLatitude = clocation.getLatitude();
                            mLongitude = clocation.getLongitude();
                        }
                    }
                }
                if (isGPSEnabled) {
                    if (clocation == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            clocation = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (clocation != null) {
                                mLatitude = clocation.getLatitude();
                                mLongitude = clocation.getLongitude();
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(MapActivity.this).create();
                                //				alertDialog.setTitle("Your phone does not support GPS or NETWORK access, you can still use the application but some features may not work properly.");
                                alertDialog.setMessage("Your phone does not find current location.");//but some features may not work properly
                                //alertDialog.setIcon(R.drawable.ic_launcher);
                                alertDialog.setButton("QUIT", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//                                            MapActivity.this.finish();
                                    }
                                });
                                alertDialog.show();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        distance = (TextView) findViewById(R.id.distancevalue);

        Intent in = getIntent();
        Dest_lat = in.getStringExtra("dest_lat");
        Dest_lng = in.getStringExtra("dest_lng");
        Shop_name = in.getStringExtra("shop_name");
        Shop_address = in.getStringExtra("shop_address");
        Shop_email = in.getStringExtra("shop_email");
        Shop_phone = in.getStringExtra("shop_phone");

        txt_shop_name.setText(Shop_name);
        txt_shop_address.setText(Shop_address);
        txt_shop_email.setText(Shop_email);
        txt_shop_phone.setText(Shop_phone);

        flat = mLatitude;
        flng = mLongitude;
        slat = Double.parseDouble(Dest_lat);
        slng = Double.parseDouble(Dest_lng);
        final FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        } else {
            mGoogleMap = fragment.getMap();
        }
        city1 = new LatLng(slat, slng);
        city2 = new LatLng(flat, flng);
        currentcity = new LatLng(mLatitude, mLongitude);
        mGoogleMap.addMarker(new MarkerOptions().position(city2).icon(BitmapDescriptorFactory.fromResource(R.drawable.redmarker)).title("Your current position"));
        mGoogleMap.addMarker(new MarkerOptions().position(city1).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title(Shop_name));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city2, 12));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnectingToInternet()) {

            Toast.makeText(MapActivity.this, "Please check your Internet connection", Toast.LENGTH_SHORT).show();

        } else {
            new connectAsyncTaskdirection().execute();
        }
        txt_shop_phone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View vv) {
                // TODO Auto-generated method stub
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+txt_shop_phone.getText().toString()));

                if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);

            }
        });
    }

    class connectAsyncTaskdirection extends AsyncTask<Void, Void, String> {
        private ProgressDialog pdia;
        String url;
        String responseString = null;
        String strDistance;
        double dist;
        float distkm;
        int integer;
        float distanceInMeters;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pdia = new ProgressDialog(MapActivity.this);//,R.style.MyTheme);
            //pdia.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            pdia.setMessage("Fetching route," + "\n" + " Please wait...");
            pdia.setCancelable(false);
            pdia.setIndeterminate(true);
            pdia.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            final LatLng origin = new LatLng(flat, flng);
            final LatLng dest = new LatLng(slat, slng);
            // Getting URL to the Google Directions API
            String strurl = getDirectionsUrl(origin, dest);

            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL objurl = new URL(strurl);
                // Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) objurl.openConnection();
                // Connecting to url
                urlConnection.connect();

                // Reading data from url
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb = new StringBuffer();

                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                data = sb.toString();
                br.close();

            } catch (Exception e) {
                Log.d("Exception url", e.toString());
            } finally {
                try {
                    iStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                urlConnection.disconnect();
            }

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(data);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (routes.size() > 0) {
                HashMap<String, String> point = routes.get(0).get(0);

                String DistanceKm = point.get("distance");

                resultkm = DistanceKm.replace("km", "Kilometers");
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pdia.dismiss();
            if (result != null) {
                drawPath(result);
                if (resultkm != null) {
                    if (resultkm.equalsIgnoreCase("")) {
                        distance.setText(" " + resultkm);
                        distance.setTextColor(Color.RED);
                        distance.setVisibility(View.GONE);
                    } else {
                        distance.setText(" " + resultkm);
                        distance.setTextColor(Color.RED);
                    }
                }
            }
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    public void drawPath(String result) {
        if (line != null) {
            Log.i("removing line", "removed");
            mGoogleMap.clear();
        }
        try {
            // Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            PolylineOptions options = new PolylineOptions().width(8).color(Color.parseColor("#4883da")).geodesic(true);
            for (int z = 0; z < list.size(); z++) {
                LatLng point = list.get(z);
                options.add(point);
            }
            line = mGoogleMap.addPolyline(options);

        } catch (JSONException e) {

        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }


    public void drawNavigationPath(String result) {
        try {
            // Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            PolylineOptions options = new PolylineOptions().width(5).color(Color.parseColor("#FF00FF")).geodesic(true);
            for (int z = 0; z < list.size(); z++) {
                LatLng point = list.get(z);
                options.add(point);
            }
            if (nav_line != null) {
                Log.i("removing line", "removed");
//            mGoogleMap.clear();
                nav_line.remove();
            }
            nav_line = mGoogleMap.addPolyline(options);

        } catch (JSONException e) {

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case android.R.id.home: {
                mGoogleMap.clear();
                finish();
                return true;
            }

            default: {

                return super.onOptionsItemSelected(item);
            }
        }
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
////        mGoogleMap = googleMap;
//
//        LatLng TutorialsPoint = new LatLng(mLatitude, mLongitude);
//        mGoogleMap.addMarker(new MarkerOptions().position(TutorialsPoint).title("Chandigarh"));
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(TutorialsPoint)
//                .zoom(11.0f)
//                .bearing(90)
//                .tilt(30)
//                .build();
//        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//    }

    @Override
    public void onLocationChanged(Location location) {
        clocation = location;
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
//        Toast.makeText(DriverMyRidesDetails.this, "check position=" + mLatitude, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onResume() {
        super.onResume();
//        autoComp_source_add.setText(strAdd);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mGoogleMap.clear();
    }
}
