package co.ramraj;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {

    //Defining Variables
    public static Toolbar toolbar;
    NavigationView navigationView;
    public static DrawerLayout drawerLayout;

    String[] title;
    String[] subtitle;
    int[] icon;
    public static ListView mDrawerList;
    public static MenuListAdapter mMenuAdapter;
    public static int mSelectedItem = 0;
    public static int add = 0;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    Handler handler;
    Runnable myRunnable;
    ConnectionDetector cd;
    String status = "0";
    boolean doubleBackToExitPressedOnce = false;
    String currentpage = "";
    Fragment fragment;
    FragmentTransaction ft;

    Signin signin = new Signin();
    Cart cart = new Cart();
    Order order = new Order();
    Categories categories = new Categories();
    Recents recents = new Recents();
    Features features = new Features();
    All all = new All();
    Setting settings = new Setting();
    String page;
    int LoginId;
    JSONArray totalshop = null;
    int shoplength;
    private Menu menu;
    private static final long MIN_TIME_BW_UPDATES = 1;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;
    public static Location clocation = null;
    public static Double mLatitude = 0.0, mLongitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {

            setSupportActionBar(toolbar);
        }
        title = new String[]{"Signin", "Cart", "Order", "Categories", "Recents", "Features", "All", "Settings", "Logout"};

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.drawable.titlelogo);
//        // Generate icon
//        icon = new int[]{R.drawable.action_about, R.drawable.action_settings, R.drawable.action_about, R.drawable.action_settings, R.drawable.action_settings, R.drawable.action_settings, R.drawable.action_settings};


        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerList = (ListView) findViewById(R.id.lst_menu_items);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        LoginId = sharedpreferences.getInt("LoginId", 0);
        ft = getSupportFragmentManager().beginTransaction();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (LoginId == 0) {
            title = new String[]{"Signin", "Cart", "Order", "Categories", "Recents", "Features", "All", "Settings"};
            mMenuAdapter = new MenuListAdapter(MainActivity.this, title);
            mDrawerList.setAdapter(mMenuAdapter);
            ft.replace(R.id.frame, signin);
            ft.commit();
            mSelectedItem = 0;
            editor.putString("page", "Account");
            editor.commit();
        } else {
            title = new String[]{"Signin", "Cart", "Order", "Categories", "Recents", "Features", "All", "Settings", "Logout"};
            mMenuAdapter = new MenuListAdapter(MainActivity.this, title);
            mDrawerList.setAdapter(mMenuAdapter);
            ft.replace(R.id.frame, all);
            ft.commit();
            mSelectedItem = 6;
            editor.putString("page", "All");
            editor.commit();
        }
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

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
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
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            clocation = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (clocation != null) {
                                mLatitude = clocation.getLatitude();
                                mLongitude = clocation.getLongitude();
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
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

        handler = new Handler();
        myRunnable = new Runnable() {
            @Override
            public void run() {

                new NearbyShopProgresh().execute();

                handler.postDelayed(this, 60 * 1000);
            }
        };
        handler.post(myRunnable);


//        drawerLayout.openDrawer(Gravity.START);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        mDrawerList.setBackgroundColor(Color.BLACK);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Locate Position
                SharedPreferences.Editor editor = sharedpreferences.edit();
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                currentpage = sharedpreferences.getString("page", "");
                ft = getSupportFragmentManager().beginTransaction();
                switch (position) {
                    case 0:
                        if (currentpage.equalsIgnoreCase("Account")) {

                        } else {
                            ft.replace(R.id.frame, signin);
                            editor.putString("page", "Account");
                            editor.commit();
                        }
                        break;
                    case 1:
                        if (currentpage.equalsIgnoreCase("Cart")) {

                        } else {
                            ft.replace(R.id.frame, cart);
                            editor.putString("page", "Cart");
                            editor.commit();
                        }
                        break;
                    case 2:
                        if (currentpage.equalsIgnoreCase("Order")) {

                        } else {
                            ft.replace(R.id.frame, order);
                            editor.putString("page", "Order");
                            editor.commit();
                        }
                        break;

                    case 3:
                        if (currentpage.equalsIgnoreCase("Categories")) {

                        } else {
                            ft.replace(R.id.frame, categories);
                            editor.putString("page", "Categories");
                            editor.commit();
                        }
                        break;
                    case 4:
                        if (currentpage.equalsIgnoreCase("Recents")) {

                        } else {
                            ft.replace(R.id.frame, recents);
                            editor.putString("page", "Recents");
                            editor.commit();
                        }
                        break;
                    case 5:
                        if (currentpage.equalsIgnoreCase("Features")) {

                        } else {
                            ft.replace(R.id.frame, features);
                            editor.putString("page", "Features");
                            editor.commit();
                        }
                        break;

                    case 6:
                        if (currentpage.equalsIgnoreCase("All")) {

                        } else {
                            ft.replace(R.id.frame, all);
                            editor.putString("page", "All");
                            editor.commit();
                        }

                        break;
                    case 7:
                        if (currentpage.equalsIgnoreCase("Settings")) {

                        } else {
                            ft.replace(R.id.frame, settings);
                            editor.putString("page", "Settings");
                            editor.commit();
                        }
                        break;
                    case 8:
                        editor = sharedpreferences.edit();
                        editor.putInt("LoginId", 0);
                        editor.putInt("fblogin", 0);
                        editor.putString("page", "");
                        editor.commit();
                        ft.replace(R.id.frame, signin);
                        break;
                }

                ft.commit();
//                mDrawerList.setBackgroundColor(getResources().getColor(R.color.SelectedColor));
                mSelectedItem = position;
                mMenuAdapter.notifyDataSetChanged();
//                mDrawerList.setItemChecked(position, true);
//                for (int i = 0; i < mDrawerList.getChildCount(); i++) {
//                    if (position == i) {
//                        mDrawerList.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.SelectedColor));
//                    } else {
//                        mDrawerList.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
//                    }
//                }
                // Get the title followed by the position
//                getSupportActionBar().setTitle("Emerald Jewellers");
                // Close drawer
                drawerLayout.closeDrawers();
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                LoginId = sharedpreferences.getInt("LoginId", 0);
                if (LoginId == 0) {
                    title = new String[]{"Signin", "Cart", "Order", "Categories", "Recents", "Features", "All", "Settings"};
                    mMenuAdapter.notifyDataSetChanged();
                    mMenuAdapter = new MenuListAdapter(MainActivity.this, title);
                    mDrawerList.setAdapter(mMenuAdapter);
                } else {
                    title = new String[]{"Signin", "Cart", "Order", "Categories", "Recents", "Features", "All", "Settings", "Logout"};
                    mMenuAdapter.notifyDataSetChanged();
                    mMenuAdapter = new MenuListAdapter(MainActivity.this, title);
                    mDrawerList.setAdapter(mMenuAdapter);
                }
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            // If there are back-stack entries, leave the FragmentActivity
            // implementation take care of them.
            manager.popBackStack();
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            page = sharedpreferences.getString("page", "");
            if (page.equalsIgnoreCase("Categories")) {
                Categories.txtstore.setText(Categories.store);
            } else if (page.equalsIgnoreCase("Recents")) {
                Recents.txtstore.setText(Categories.store);
            } else if (page.equalsIgnoreCase("Features")) {
                Features.txtstore.setText(Categories.store);
            } else if (page.equalsIgnoreCase("All")) {
                All.txtstore.setText(Categories.store);
            } else {
            }

        } else {
            // Otherwise, ask user if he wants to leave :)
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public class MenuListAdapter extends BaseAdapter {

        // Declare Variables
        Context context;
        String[] mTitle;
        //    String[] mSubTitle;
//    int[] mIcon;
        LayoutInflater inflater;
        View itemView;

        public MenuListAdapter(Context context, String[] title) {
            this.context = context;
            this.mTitle = title;
//        this.mSubTitle = subtitle;
//        this.mIcon = icon;
        }

        @Override
        public int getCount() {
            return mTitle.length;
        }

        @Override
        public Object getItem(int position) {
            return mTitle[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // Declare Variables
            TextView txtTitle;
            TextView txtSubTitle;
            ImageView imgIcon;

            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.drawer_list_item, parent,
                    false);

            // Locate the TextViews in drawer_list_item.xml
            txtTitle = (TextView) itemView.findViewById(R.id.title);
            txtSubTitle = (TextView) itemView.findViewById(R.id.subtitle);

            // Locate the ImageView in drawer_list_item.xml
            imgIcon = (ImageView) itemView.findViewById(R.id.icon);

            // Set the results into TextViews
            txtTitle.setText(mTitle[position]);
//        txtSubTitle.setText(mSubTitle[position]);

            // Set the results into ImageView
//        imgIcon.setImageResource(mIcon[position]);
//            if (position == 0)
//                itemView.setBackgroundColor(context.getResources().getColor(R.color.SelectedColor));
            if (position == mSelectedItem) {
                itemView.setBackgroundColor(context.getResources().getColor(R.color.SelectedColor));
            } else {
//                itemView.setBackgroundColor(Color.BLACK);
                itemView.setBackgroundColor(context.getResources().getColor(R.color.UnSelectedColor));
            }
            return itemView;
        }
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        return true;
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_button, menu);
        this.menu = menu;
        return true;
    }

    private void updateMenuTitles() {
        MenuItem MenuItem = menu.findItem(R.id.options_menu_main_locator);
        MenuItem.setTitle("" + shoplength);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        currentpage = sharedpreferences.getString("page", "");
        switch (item.getItemId()) {

            case android.R.id.home: {
                finish();
                return true;
            }
            case R.id.options_menu_main_cart: {
//                Toast.makeText(MainActivity.this, "Refresh App", Toast.LENGTH_LONG).show();
                if (currentpage.equalsIgnoreCase("cart")) {

                } else {
                    fragment = new Cart();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, fragment);
//                    ft.add(R.id.frame, fragment);
//                    ft.addToBackStack("add" + MainActivity.add);
                    ft.commit();
                    MainActivity.mSelectedItem = 1;
                    MainActivity.mMenuAdapter.notifyDataSetChanged();
                    editor.putString("page", "cart");
                    editor.commit();
                }
//                MainActivity.add++;
                return true;
            }
            case R.id.options_menu_main_locator: {
//                Toast.makeText(MainActivity.this, "Refresh App", Toast.LENGTH_LONG).show();

                if (currentpage.equalsIgnoreCase("nearby")) {

                } else {
                    fragment = new NearByShop();
                    ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, fragment);
//                    ft.add(R.id.frame, fragment);
//                    ft.addToBackStack("add" + MainActivity.add);
                    ft.commit();
                    editor.putString("page", "nearby");
                    editor.commit();
                }
                return true;
            }
            default: {

                return super.onOptionsItemSelected(item);
            }
        }
    }

    public static View getToolbarLogoIcon(Toolbar toolbar) {
        //check if contentDescription previously was set
        boolean hadContentDescription = android.text.TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        //find the view based on it's content description, set programatically or with android:contentDescription
        toolbar.findViewsWithText(potentialViews, contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        //Nav icon is always instantiated at this point because calling setLogoDescription ensures its existence
        View logoIcon = null;
        if (potentialViews.size() > 0) {
            logoIcon = potentialViews.get(0);
        }
        //Clear content description if not previously present
        if (hadContentDescription)
            toolbar.setLogoDescription(null);
        return logoIcon;
    }

    public class NearbyShopProgresh extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            JSONParser jParser = new JSONParser();
            // http://www.puyangan.com/api/products.php?show=featured
//			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/products.php?show=featured");
            JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url) + "/wp-content/plugins/nearby-shops/api.php?lati=" + mLatitude + "&logi=" + mLongitude);

            try {
                //http://www.puyangan.com/api/category.php?cid=178
                // Getting Array of Employee
                totalshop = json.getJSONArray("shops");
                shoplength = totalshop.length();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            updateMenuTitles();
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
