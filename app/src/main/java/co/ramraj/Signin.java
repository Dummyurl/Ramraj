package co.ramraj;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;


public class Signin extends Fragment implements OnClickListener {
    Button btnsignup, btnsend, btncancel;
    ToggleButton btnshowpassword;
    EditText user_name, password, edtemail;
    TextView fpassword, text1, text2, error, txtmessage;
    String User_Name, Password, Email;
    RelativeLayout idaccountsignin, idmessage, idtop;
    JSONArray responce = null;
    String userId;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String page, message;
    Fragment fragment;
    FragmentTransaction ft;
    int LoginId;
    String email;
    ImageView btnsubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.accountsignin, container, false);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //img=(ImageView)view.findViewById(R.id.banner);
        btnsignup = (Button) view.findViewById(R.id.btnsignup);
        btnshowpassword = (ToggleButton) view.findViewById(R.id.btnshowpassword);
        btnsubmit = (ImageView) view.findViewById(R.id.btnsubmit);
        user_name = (EditText) view.findViewById(R.id.edtusername);
        password = (EditText) view.findViewById(R.id.edtpassword);
        fpassword = (TextView) view.findViewById(R.id.fpassword);
        txtmessage = (TextView) view.findViewById(R.id.textmessage);
        idmessage = (RelativeLayout) view.findViewById(R.id.idmessage);
        idtop = (RelativeLayout) view.findViewById(R.id.idtop);
        idaccountsignin = (RelativeLayout) view.findViewById(R.id.idaccountsignin);
        btnsignup.setOnClickListener(this);
        btnshowpassword.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
        fpassword.setOnClickListener(this);
        idmessage.setVisibility(View.GONE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("Signinpage", true);
//		MainActivity.navigationView.getMenu().getItem(0).setChecked(true);

        editor.commit();
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        LoginId = sharedpreferences.getInt("LoginId", 0);
        email = sharedpreferences.getString("email", "");
        if (LoginId == 0) {
            idtop.setVisibility(View.VISIBLE);
            idmessage.setVisibility(View.GONE);
        } else {
            idtop.setVisibility(View.GONE);
            idmessage.setVisibility(View.VISIBLE);
            txtmessage.setText("You are login with " + email);
        }
        user_name.setHint("Email Address");
        password.setHint("Password");
        NumberKeyListener emailinput = new NumberKeyListener() {

            public int getInputType() {
                return InputType.TYPE_MASK_VARIATION;
            }

            @Override
            protected char[] getAcceptedChars() {
                return new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '@', '.', '_'};
            }
        };
        user_name.setKeyListener(emailinput);
        user_name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // put the code of save Database here
                user_name.setError(null);
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
        return view;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Fragment fragment;
        FragmentManager fm;
        android.support.v4.app.FragmentTransaction ft;
        switch (v.getId()) {
            case R.id.btnsubmit:
                Email = user_name.getText().toString();
                Password = password.getText().toString();
                if (Email.equalsIgnoreCase("")) {
                    user_name.requestFocus();
                    user_name.setText("");
                    user_name.setError("Please fill username or email");

                } else {
                    if (Password.equalsIgnoreCase("")) {
                        password.requestFocus();
                        password.setText("");
                        password.setError("Please fill password");

                    } else {
                        new Submit().execute();

                    }
                }


                break;
            case R.id.btnshowpassword:
                boolean press = ((ToggleButton) v).isChecked();
                if (press)
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
//		case R.id.fusername:
//			rltop.setBackgroundResource(R.color.xyz);
//			LayoutInflater inflater=getActivity().getLayoutInflater();
//			View popupView = inflater.inflate(R.layout.popup, null);  
//			final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);  
//
//			btncancel = (Button)popupView.findViewById(R.id.btncancel);
//			btnsend = (Button)popupView.findViewById(R.id.btnsend);
//			edtemail=(EditText)popupView.findViewById(R.id.edtemail);
//			text1=(TextView)popupView.findViewById(R.id.textView1);
//			text2=(TextView)popupView.findViewById(R.id.textView2);
//			error=(TextView)popupView.findViewById(R.id.txterror);
//			text1.setText("Forgot Username");
//			btncancel.setOnClickListener(new OnClickListener(){
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//
//					//					rltop.setAlpha(1F);
//					rltop.setBackgroundResource(R.color.white);
//					popupWindow.dismiss();
//				}});
//			btnsend.setOnClickListener(new OnClickListener(){
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					Email=edtemail.getText().toString();
//					if(Email.trim().length()<1)
//					{
//						//						empty.setVisibility(View.VISIBLE);
//						//						empty.setText("Please enter file name");
//						error.setText("Please fill email address");
//						error.setTextColor(Color.RED);
//						error.setVisibility(View.VISIBLE);
//					}
//					else
//					{
//
//						//						popupWindow.dismiss();
//						new ForgotProgress().execute();
//						rltop.setAlpha(1F);
//						rltop.setBackgroundResource(R.color.white);
//						popupWindow.dismiss();
//
//					}
//					//					rltop.setAlpha(1F);
////					rltop.setBackgroundResource(R.color.white);
////					popupWindow.dismiss();
//				}});
//			popupWindow.showAtLocation(popupView, LayoutParams.WRAP_CONTENT,10,200);
//			popupWindow.setFocusable(true);
//			popupWindow.update();
//			//			rltop.setAlpha(0.2F);
//
//			break;
            case R.id.fpassword:
                idaccountsignin.setBackgroundResource(R.color.xyz);
                LayoutInflater inflaterr = getActivity().getLayoutInflater();
                View popupVieww = inflaterr.inflate(R.layout.popup, null);
                final PopupWindow popupWindoww = new PopupWindow(popupVieww, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

                btncancel = (Button) popupVieww.findViewById(R.id.btncancel);
                btnsend = (Button) popupVieww.findViewById(R.id.btnsend);
                edtemail = (EditText) popupVieww.findViewById(R.id.edtemail);
                NumberKeyListener emailinput = new NumberKeyListener() {

                    public int getInputType() {
                        return InputType.TYPE_MASK_VARIATION;
                    }

                    @Override
                    protected char[] getAcceptedChars() {
                        return new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '@', '.', '_'};
                    }
                };
                edtemail.setKeyListener(emailinput);
                error = (TextView) popupVieww.findViewById(R.id.txterror);
                btncancel.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        //					rltop.setAlpha(1F);
                        idaccountsignin.setBackgroundResource(R.color.white);
                        popupWindoww.dismiss();
                    }
                });
                btnsend.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Email = edtemail.getText().toString();
                        if (Email.trim().length() < 1) {
                            //						edtemail.requestFocus();
                            //						edtemail.setText("");
                            //						edtemail.setError("Enter Email Address");
                            error.setText("Please fill email address");
                            error.setTextColor(Color.RED);
                            error.setVisibility(View.VISIBLE);
                        } else {

                            //						popupWindow.dismiss();
                            new ForgotProgress().execute();
                            idaccountsignin.setAlpha(1F);
                            idaccountsignin.setBackgroundResource(R.color.white);
                            popupWindoww.dismiss();

                        }
                        //					rltop.setAlpha(1F);
                        //					rltop.setBackgroundResource(R.color.white);
                        //					popupWindoww.dismiss();
                    }
                });
                popupWindoww.showAtLocation(popupVieww, LayoutParams.WRAP_CONTENT, 10, 200);
                popupWindoww.setFocusable(true);
                popupWindoww.update();
                break;
            case R.id.btnsignup:
//			Signup fragment = new Signup();
//			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//			fragmentTransaction.replace(R.id.content_frame, fragment);
//			fragmentTransaction.addToBackStack(null);
//			fragmentTransaction.commit();
                fragment = new Signup();
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, fragment);
                ft.commit();
                break;


        }
    }

    public class Submit extends AsyncTask<String, String, String> {
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
            // getting JSON string from URL//fname,lname,email,pwd,pwd2,zipcode,month,day,subscriber      checkuserlogin=1,user_login=emailid,user_pass=password
            JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url) + "/api/login.php?checkuserlogin=1&user_login=" + URLEncoder.encode(Email) + "&user_pass=" + Password);
            try {
                // Getting Array of Employee
                responce = json.getJSONArray("success");
                int length = responce.length();
                // looping of List
                for (int i = 0; i < length; i++) {
                    JSONObject c = responce.getJSONObject(i);
                    userId = c.getString("login_user_id");
                    // Storing each json item in variable
                    //					UserId =Integer.parseInt( c.getString("login_user_id"));
                    //					// Log.e("PickUpLocation",PickUpLocation);
                    //					msg = c.getString("message");
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
            if (Integer.parseInt(userId) == 0) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT).create();
                alertDialog.setTitle("Fill correct email and password");

                alertDialog.setIcon(R.mipmap.launchicon);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            } else {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("LoginId", Integer.parseInt(userId));
                getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                page = sharedpreferences.getString("page", "");

                if (page.equalsIgnoreCase("Order")) {
//					Fragment fragment = new                editor.putString("email", Email);
                editor.commit();


//					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//					fragmentTransaction.replace(R.id.content_frame, fragment);
//					fragmentTransaction.addToBackStack(null);
//					fragmentTransaction.commit();
                    fragment = new Order();
                    ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, fragment);
                    ft.commit();
                } else if (page.equalsIgnoreCase("Product")) {
//					Fragment fragment = new Product();
//					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//					fragmentTransaction.replace(R.id.content_frame, fragment);
//					fragmentTransaction.addToBackStack(null);
//					fragmentTransaction.commit();
                    fragment = new Product();
                    ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, fragment);
                    ft.commit();
                } else if (page.equalsIgnoreCase("productdetails")) {
//					Fragment fragment = new ProductDetails();
//					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//					fragmentTransaction.replace(R.id.content_frame, fragment);
//					fragmentTransaction.addToBackStack(null);
//					fragmentTransaction.commit();
                    fragment = new ProductDetails();
                    ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, fragment);
                    ft.commit();
                } else {
//					Fragment fragment = new Categories();
//					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//					fragmentTransaction.replace(R.id.content_frame, fragment);
//					fragmentTransaction.addToBackStack(null);
//					fragmentTransaction.commit();
                    fragment = new Categories();
                    ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, fragment);
                    ft.commit();
                }
            }
        }
    }

    public class ForgotProgress extends AsyncTask<String, String, String> {
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
            // getting JSON string from URL//checkuserforgotpwd=1,user_login=emailid
            JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url) + "/api/login.php?checkuserforgotpwd=1&user_login=" + URLEncoder.encode(Email));
            try {
                // Getting Array of Employee
                responce = json.getJSONArray("success");
                int length = responce.length();
                // looping of List
                for (int i = 0; i < length; i++) {
                    JSONObject c = responce.getJSONObject(i);
                    message = c.getString("message");
                    // Storing each json item in variable
                    //					UserId =Integer.parseInt( c.getString("login_user_id"));
                    //					// Log.e("PickUpLocation",PickUpLocation);
                    //					msg = c.getString("message");
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

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT).create();
            alertDialog.setTitle(message);

            alertDialog.setIcon(R.mipmap.launchicon);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }
    }
}
