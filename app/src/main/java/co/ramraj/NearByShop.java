package co.ramraj;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ADMIN on 16-Jun-17.
 */

public class NearByShop extends Fragment implements LocationListener {
    View view;
    ListView shopList;
    TextView txtmessage;
    JSONArray totalshop = null;
    int shoplength;
    String Id, Name, Phone, Email, City, State, Country, Address, Zipcode, Distance, Lat, Longi, Photo;
    ArrayList<Holder> list = new ArrayList<Holder>();
    private static final long MIN_TIME_BW_UPDATES = 1;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;
    public static Location clocation = null;
    public static Double mLatitude = 0.0, mLongitude = 0.0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.nearbyshoplist, container, false);
        shopList = (ListView) view.findViewById(R.id.shoplistview);
        txtmessage = (TextView) view.findViewById(R.id.txtempty);
        try {
            LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
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
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            clocation = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (clocation != null) {
                                mLatitude = clocation.getLatitude();
                                mLongitude = clocation.getLongitude();
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
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
        new NearbyShopProgresh().execute();
        return view;
    }

    public class NearbyShopProgresh extends AsyncTask<String, String, String> {
        private ProgressDialog pdia;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pdia = new ProgressDialog(getActivity());
            pdia.setMessage("Loading...");
            pdia.show();
            pdia.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
//			list.clear();

            JSONParser jParser = new JSONParser();
            // http://www.puyangan.com/api/products.php?show=featured
//			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/products.php?show=featured");
            JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url) + "/wp-content/plugins/nearby-shops/api.php?lati="+mLatitude+"&logi="+mLongitude);

            try {
                //http://www.puyangan.com/api/category.php?cid=178
                // Getting Array of Employee
                totalshop = json.getJSONArray("shops");
                shoplength = totalshop.length();
                for (int i = 0; i < shoplength; i++) {
                    JSONObject c = totalshop.getJSONObject(i);
                    //					Bitmap myBitmap = null;
                    InputStream input = null;
                    Id = c.getString("id");
                    Name = c.getString("name");
                    Phone = c.getString("phone");
                    Email = c.getString("email");
                    City = c.getString("city");
                    State = c.getString("state");
                    Country = c.getString("country");
                    Address = c.getString("address");
                    Zipcode = c.getString("zipcode");
                    Distance = c.getString("distance");
                    Longi = c.getString("longitude");
                    Lat = c.getString("latitude");
                    Photo = c.getString("photo");
                    Holder h = new Holder();
                    if (Photo.equalsIgnoreCase("")) {

                    } else {

                        URL url = new URL(Photo);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.setInstanceFollowRedirects(false);
                        connection.setRequestMethod("GET");
                        connection.connect();
                        input = connection.getInputStream();

                        BitmapFactory.Options opts = new BitmapFactory.Options();
                        // opts.inJustDecodeBounds = true;
                        opts.inSampleSize = 4;
                        Bitmap myBitmap = BitmapFactory.decodeStream(input, null, opts);
                        //						Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 200, 200, true);
                        //myBitmap.recycle();

                        //						Bitmap myBitmap = BitmapFactory.decodeStream(input);
                        h.setBitmap(myBitmap);
                        connection.disconnect();
                    }


                    h.setShopId(Id);
                    h.setShopName(Name);
                    h.setShopPhone(Phone);
                    h.setShopEmail(Email);
                    h.setShopCity(City);
                    h.setShopState(State);
                    h.setShopCountry(Country);
                    h.setShopAddress(Address);
                    h.setShopZipcode(Zipcode);
                    h.setShopDistance(Distance);
                    h.setShopLat(Lat);
                    h.setShopLong(Longi);
                    h.setShopPhoto(Photo);

                    list.add(h);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pdia.dismiss();
            pdia = null;
            shopList.setAdapter(new MyCustomAdapter(getActivity(), list));
            //			ListUtils.setDynamicHeight(gridview);
            if (shoplength == 0) {
                txtmessage.setVisibility(View.VISIBLE);
                shopList.setVisibility(View.GONE);
            } else {
                txtmessage.setVisibility(View.GONE);
                shopList.setVisibility(View.VISIBLE);
            }
        }

    }

    class MyCustomAdapter extends BaseAdapter {

        LayoutInflater inflater;
        ArrayList<Holder> list;

        public MyCustomAdapter(FragmentActivity fragmentActivity, ArrayList<Holder> list) {
            inflater = LayoutInflater.from(fragmentActivity);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int paramInt) {
            return paramInt;
        }

        class ViewHolder {
            TextView txtname, txtaddress, txtemail,txtphone;
            ImageView imgcall,imgshop;
            RelativeLayout rltop;
        }

        @Override
        public long getItemId(int paramInt) {
            return paramInt;
        }

        @Override
        public View getView(final int paramInt, View paramView, ViewGroup paramViewGroup) {

            final ViewHolder holder;
            if (paramView == null) {
                paramView = inflater.inflate(R.layout.nearbyshop_list_item, paramViewGroup, false);
                holder = new ViewHolder();

                holder.txtname = (TextView) paramView.findViewById(R.id.txtname);
                holder.txtaddress = (TextView) paramView.findViewById(R.id.txtaddress);
                holder.txtemail = (TextView) paramView.findViewById(R.id.txtemail);
                holder.txtphone = (TextView) paramView.findViewById(R.id.txtphone);
                holder.rltop = (RelativeLayout) paramView.findViewById(R.id.rltop);
                holder.imgcall = (ImageView) paramView.findViewById(R.id.imgcall);
                holder.imgshop = (ImageView) paramView.findViewById(R.id.imgshop);
                paramView.setTag(holder);
            } else {
                holder = (ViewHolder) paramView.getTag();
            }

            Holder h = list.get(paramInt);
            holder.txtname.setText(h.getShopName());
            holder.txtaddress.setText(h.getShopAddress()+", "+h.getShopCity()+", "+h.getShopState()+", "+h.getShopCountry()+"\n"+h.getShopZipcode());
            holder.txtemail.setText(h.getShopEmail());
            holder.txtphone.setText(h.getShopPhone());
            Bitmap bitmap = h.getBitmap();
            if (h.getShopPhoto().equalsIgnoreCase("")) {
                holder.imgshop.setBackgroundResource(R.drawable.header);
            } else {
                holder.imgshop.setImageBitmap(bitmap);
            }
            holder.rltop.setTag(paramInt);
            holder.rltop.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View vv) {
                    // TODO Auto-generated method stub
                    int pos1 = (Integer) vv.getTag();
                    Holder h1 = (Holder) list.get(pos1);
                    Intent in=new Intent(getActivity(),MapActivity.class);
                    in.putExtra("dest_lat", h1.getShopLat());
                    in.putExtra("dest_lng", h1.getShopLong());
                    in.putExtra("shop_name",h1.getShopName());
                    in.putExtra("shop_address",holder.txtaddress.getText().toString());
                    in.putExtra("shop_email",h1.getShopEmail());
                    in.putExtra("shop_phone",h1.getShopPhone());
                    startActivity(in);


                }
            });
            holder.txtphone.setTag(paramInt);
            holder.txtphone.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View vv) {
                    // TODO Auto-generated method stub
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+holder.txtphone.getText().toString()));

                    if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callIntent);

                }
            });
            return paramView;
        }
    }
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
}
