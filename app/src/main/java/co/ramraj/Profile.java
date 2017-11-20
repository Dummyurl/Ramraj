package co.ramraj;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Profile extends Fragment implements OnClickListener{
    Button btnbilling,btnupdate;

    String Fname,Lname,Email,Password,Cpassword,Address,State,Zcode,Country,Month,Day;
    EditText fname,lname,email,password,cpassword,address,state,zcode,country;
    Spinner month,day;
//    Switch subscriber;
    TextView confirmerror;
//    String emailPattern = "[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+";
    ArrayAdapter<String> dataAdapter;
    String value;
    JSONArray userinfo = null;
    String message;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String page;
    Fragment fragment;
    android.support.v4.app.FragmentTransaction ft;
    RelativeLayout idsignup;
    int LoginId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view=inflater.inflate(R.layout.profile, container, false);
        //img=(ImageView)view.findViewById(R.id.banner);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("Signinpage", true);

        editor.commit();
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        LoginId =sharedpreferences.getInt("LoginId", 0);
        Email=sharedpreferences.getString("email","");

        final List<String> list1 = new ArrayList<String>();
        for(int i=01;i<32;i++)
        {
            if(i<10)
            {
                value="0".concat(""+i);
            }
            else
            {
                value=""+i;
            }

            list1.add(value);
        }
        final List<String> list2 = new ArrayList<String>();
        for(int i=01;i<31;i++)
        {
            if(i<10)
            {
                value="0".concat(""+i);
            }
            else
            {
                value=""+i;
            }
            list2.add(value);
        }
        final List<String> list3 = new ArrayList<String>();
        for(int i=01;i<30;i++)
        {
            if(i<10)
            {
                value="0".concat(""+i);
            }
            else
            {
                value=""+i;
            }
            list3.add(value);
        }
        btnbilling=(Button)view.findViewById(R.id.btnbilling);
        btnupdate=(Button)view.findViewById(R.id.btnupdate);

        fname=(EditText)view.findViewById(R.id.edtfname);
        lname=(EditText)view.findViewById(R.id.edtlname);
        email=(EditText)view.findViewById(R.id.edtemail);
        password=(EditText)view.findViewById(R.id.edtpassword);
        cpassword=(EditText)view.findViewById(R.id.edtconfermpassword);
        address=(EditText)view.findViewById(R.id.edtaddress);
        state=(EditText)view.findViewById(R.id.edtstate);
        zcode=(EditText)view.findViewById(R.id.edtzipcode);
        country=(EditText)view.findViewById(R.id.edtcountry);
        month=(Spinner)view.findViewById(R.id.spmonth);
        day=(Spinner)view.findViewById(R.id.spdate);
        confirmerror=(TextView)view.findViewById(R.id.confirmerror);
        email.setText(Email);
        btnupdate.setOnClickListener(this);
        btnbilling.setOnClickListener(this);
        //		month.setOnClickListener(this);
        //		day.setOnClickListener(this);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(month);

            // Set popupWindow height to 500px
            popupWindow.setHeight(250);
        }
        catch (Exception e) {
            // silently fail...
        }
        month.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                //				Toast.makeText(getActivity(), month.getSelectedItem().toString(),
                //						Toast.LENGTH_SHORT).show();
                Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(new SimpleDateFormat("MMM").parse(month.getSelectedItem().toString()));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                int monthInt = cal.get(Calendar.MONTH) + 1;
                Month=""+monthInt;
                if(monthInt<10)
                {
                    monthInt=0+monthInt;
                }
                if((monthInt==01)||(monthInt==03)||(monthInt==05)||(monthInt==07)||(monthInt==10)||(monthInt==12))
                {
                    dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,list1);

                }
                else if((monthInt==04)||(monthInt==06)||(monthInt==9)||(monthInt==11))
                {
                    dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,list2);

                }
                else if((monthInt==02))
                {
                    dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,list3);

                }
                else
                {
                    dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,list1);

                }
                //				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,list1);

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                day.setAdapter(dataAdapter);
                try {
                    Field popup = Spinner.class.getDeclaredField("mPopup");
                    popup.setAccessible(true);

                    // Get private mPopup member variable and try cast to ListPopupWindow
                    android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(day);

                    // Set popupWindow height to 500px
                    popupWindow.setHeight(250);
                }
                catch (Exception e) {
                    // silently fail...
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        day.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                //				Toast.makeText(getActivity(), day.getSelectedItem().toString(),
                //						Toast.LENGTH_SHORT).show();
                Day=day.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



        fname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // put the code of save Database here
                fname.setError(null);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        lname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // put the code of save Database here
                lname.setError(null);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // put the code of save Database here
                email.setError(null);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // put the code of save Database here
                password.setError(null);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        cpassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // put the code of save Database here
                cpassword.setError(null);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Password=password.getText().toString();
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Password=password.getText().toString();
                if(Password.contains(s))
                {
                    confirmerror.setVisibility(View.GONE);
                }
                else
                {
                    confirmerror.setVisibility(View.VISIBLE);
                }


            }
        });
        address.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // put the code of save Database here
                address.setError(null);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        state.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // put the code of save Database here
                state.setError(null);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        zcode.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // put the code of save Database here
                zcode.setError(null);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        country.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // put the code of save Database here
                country.setError(null);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        new Progress().execute();

        return view;
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.btnbilling:

                fragment = new Setting();
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.add(R.id.frame, fragment);
                ft.addToBackStack("add" + MainActivity.add);
                ft.commit();
                MainActivity.add++;

                break;
            case R.id.btnupdate:
                Fname=fname.getText().toString();
                Lname=lname.getText().toString();
                Email=email.getText().toString();
                Password=password.getText().toString();
                Cpassword=cpassword.getText().toString();
                Zcode=zcode.getText().toString();
                if(Fname.equalsIgnoreCase(""))
                {
                    fname.requestFocus();
                    fname.setText("");
                    fname.setError("Please fill first name");
                }
                else
                {
                    if(Lname.equalsIgnoreCase(""))
                    {
                        lname.requestFocus();
                        lname.setText("");
                        lname.setError("Please fill last name");
                    }
                    else
                    {


                                if(Password.equalsIgnoreCase(""))
                                {
                                    password.requestFocus();
                                    password.setText("");
                                    password.setError("Please fill password");
                                }
                                else
                                {
                                    if(Cpassword.equalsIgnoreCase(""))
                                    {
                                        cpassword.requestFocus();
                                        cpassword.setText("");
                                        cpassword.setError("Please fill confirm password");
                                    }
                                    else
                                    {
                                        if(Password.equals(Cpassword))
                                        {
                                            if(Address.equalsIgnoreCase(""))
                                            {
                                                address.requestFocus();
                                                address.setText("");
                                                address.setError("Please fill address");
                                            }
                                            else
                                            {
                                                if(State.equalsIgnoreCase(""))
                                                {
                                                    state.requestFocus();
                                                    state.setText("");
                                                    state.setError("Please fill state");
                                                }
                                                else
                                                {
                                                    if(Zcode.equalsIgnoreCase(""))
                                                    {
                                                        zcode.requestFocus();
                                                        zcode.setText("");
                                                        zcode.setError("Please fill zip code");
                                                    }
                                                    else
                                                    {
                                                        if(Country.equalsIgnoreCase(""))
                                                        {
                                                            country.requestFocus();
                                                            country.setText("");
                                                            country.setError("Please fill country");
                                                        }
                                                        else
                                                        {
                                                            new Update().execute();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else
                                        {
                                            cpassword.requestFocus();
                                            cpassword.setText("");
                                            cpassword.setError("Password not matched");
                                        }
                                    }
                                }
                            }

                }
                break;
        }
    }
    public class Update extends AsyncTask<String, String, String> {
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
            JSONParser jParser = new JSONParser();
            // getting JSON string from URL//fname,lname,email,pwd,pwd2,zipcode,month,day,subscriber
            JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/myaccount.php?fname=" + URLEncoder.encode(Fname) + "&lname=" + URLEncoder.encode(Lname) + "&email=" + URLEncoder.encode(Email) + "&pwd=" + Password + "&pwd2=" + Cpassword + "&zipcode=" + Zcode + "&month=" + Month + "&day=" + Day + "&updateaccount=1&user_id=" + LoginId);
            try {
                // Getting Array of Employee
                userinfo = json.getJSONArray("userinfo");
                int length = userinfo.length();
                // looping of List
                for (int i = 0; i < length; i++) {
                    JSONObject c = userinfo.getJSONObject(i);

                    Fname = c.getString("fname");
                    Lname = c.getString("lname");
                    Email = c.getString("email");
                    Address = c.getString("address");
                    State = c.getString("state");
                    Zcode = c.getString("zipcode");
                    Country = c.getString("country");
//                    Month=c.getString()


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pdia.dismiss();
            pdia = null;

            fname.setText(Fname);
            lname.setText(Lname);
            email.setText(Email);
            address.setText(Address);
            state.setText(State);
            zcode.setText(Zcode);
            country.setText(Country);
        }
    }
        public class Progress extends AsyncTask<String,String,String>
        {
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
                JSONParser jParser = new JSONParser();
                // getting JSON string from URL//fname,lname,email,pwd,pwd2,zipcode,month,day,subscriber
                JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/checkout.php?user_id="+LoginId);
                try {
                    // Getting Array of Employee
                    userinfo = json.getJSONArray("userinfo");
                    int length= userinfo.length();
                    // looping of List
                    for (int i = 0; i < length; i++) {
                        JSONObject c = userinfo.getJSONObject(i);

                        Fname=c.getString("fname");
                        Lname=c.getString("lname");
                        Email=c.getString("email");
                        Address = c.getString("address");
                        State = c.getString("state");
                        Zcode = c.getString("zipcode");
                        Country = c.getString("country");
//                    Month=c.getString()


                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @SuppressWarnings("deprecation")
            @Override
            protected void onPostExecute(String result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                pdia.dismiss();
                pdia = null;

                fname.setText(Fname);
                lname.setText(Lname);
                email.setText(Email);
                address.setText(Address);
                state.setText(State);
                zcode.setText(Zcode);
                country.setText(Country);
            }
     }
}
