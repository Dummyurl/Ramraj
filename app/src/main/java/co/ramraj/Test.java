package co.ramraj;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;


public class Test extends Fragment {
	String Fname,Lname,Email,Address,State,Zipcode,Country,S_Fname,S_Lname,S_Address,S_State,S_Zipcode,S_Country;
	EditText edtfname,edtlname,edtemail,edtaddress,edtstate,edtzipcode,edtcountry,edt_sfname,edt_slname,edt_saddress,edt_sstate,edt_szipcode,edt_scountry;
	JSONArray userinfo = null;
	int length;
	CheckBox checkbox;
	int LoginId;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	Button btnupdate;
	String URL;
	RelativeLayout idcheckout;
	Fragment fragment;
	FragmentTransaction ft;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.checkout, container, false);
		//img=(ImageView)view.findViewById(R.id.banner);
		edtfname=(EditText)view.findViewById(R.id.edtfname);
		edtlname=(EditText)view.findViewById(R.id.edtlname);
		edtemail=(EditText)view.findViewById(R.id.edtemail);
		edtaddress=(EditText)view.findViewById(R.id.edtaddress);
		edtstate=(EditText)view.findViewById(R.id.edtstate);
		edtzipcode=(EditText)view.findViewById(R.id.edtzipcode);
		edtcountry=(EditText)view.findViewById(R.id.edtcountry);

		edt_sfname=(EditText)view.findViewById(R.id.edtsfname);
		edt_slname=(EditText)view.findViewById(R.id.edtslname);
		edt_saddress=(EditText)view.findViewById(R.id.edtsaddress);
		edt_sstate=(EditText)view.findViewById(R.id.edtsstate);
		edt_szipcode=(EditText)view.findViewById(R.id.edtszipcode);
		edt_scountry=(EditText)view.findViewById(R.id.edtscountry);

		checkbox=(CheckBox)view.findViewById(R.id.checkbox);
		btnupdate=(Button)view.findViewById(R.id.btnupdate);

		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		LoginId =sharedpreferences.getInt("LoginId", 0);
		if(LoginId==0)
		{
			edtemail.setFocusable(true);
		}
		else
		{
			edtemail.setFocusable(false);
		}



		edtfname.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				edtfname.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		edtlname.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				edtlname.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		edtemail.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				edtemail.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});

		edtaddress.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				edtaddress.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		edtstate.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				edtstate.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});


		edtzipcode.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				edtzipcode.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		edtcountry.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				edtcountry.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		edt_sfname.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				edt_sfname.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		edt_slname.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				edt_slname.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

		});

		edt_saddress.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				edt_saddress.setError(null);
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		edt_sstate.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				edt_sstate.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		edt_szipcode.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				edt_szipcode.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		edt_scountry.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				edt_scountry.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		btnupdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				EditText ,,,,,,,,,,;
				Fname=edtfname.getText().toString();
				Lname=edtlname.getText().toString();
				Email=edtemail.getText().toString();
				Address=edtaddress.getText().toString();
				State=edtstate.getText().toString();
				Zipcode=edtzipcode.getText().toString();
				Country=edtcountry.getText().toString();

				S_Fname=edt_sfname.getText().toString();
				S_Lname=edt_slname.getText().toString();
				S_Address=edt_saddress.getText().toString();
				S_State=edt_sstate.getText().toString();
				S_Zipcode=edt_szipcode.getText().toString();
				S_Country=edt_scountry.getText().toString();

				if(Fname.equalsIgnoreCase(""))
				{
					edtfname.requestFocus();
					edtfname.setText("");
					edtfname.setError("Please fill first name");
				}
				else
				{
					if(Lname.equalsIgnoreCase(""))
					{
						edtlname.requestFocus();
						edtlname.setText("");
						edtlname.setError("Please fill last name");
					}
					else
					{
						if(Country.equalsIgnoreCase(""))
						{
							edtcountry.requestFocus();
							edtcountry.setText("");
							edtcountry.setError("Please fill country");
						}
						else
						{
							if(Address.equalsIgnoreCase(""))
							{
								edtaddress.requestFocus();
								edtaddress.setText("");
								edtaddress.setError("Please fill address");
							}
							else
							{
								if(Zipcode.length()<6)
								{
									edtzipcode.requestFocus();
									edtzipcode.setText("");
									edtzipcode.setError("Please fill zip code");
								}
								else

								{
									if(checkbox.isChecked())
									{
										if(S_Fname.equalsIgnoreCase(""))
										{
											edt_sfname.requestFocus();
											edt_sfname.setText("");
											edt_sfname.setError("Please fill first name");
										}
										else
										{
											if(S_Lname.equalsIgnoreCase(""))
											{
												edt_slname.requestFocus();
												edt_slname.setText("");
												edt_slname.setError("Please fill last name");
											}
											else
											{
												if(S_Country.equalsIgnoreCase(""))
												{
													edt_scountry.requestFocus();
													edt_scountry.setText("");
													edt_scountry.setError("Please fill country");
												}
												else
												{
													if(S_Address.equalsIgnoreCase(""))
													{
														edt_saddress.requestFocus();
														edt_saddress.setText("");
														edt_saddress.setError("Please fill address");
													}
													else
													{
														if(S_Zipcode.length()<6)
														{
															edt_szipcode.requestFocus();
															edt_szipcode.setText("");
															edt_szipcode.setError("Please fill zip code");
														}
														else
														{
															//work for progress	String Fname,Lname,Email,Country,Address,Zipcode,S_Fname,S_Lname,S_Country,S_Address,S_Zipcode;

															URL="/api/checkout.php?user_id="+LoginId+"&fname="+Fname+"&lname="+Lname+"&address="+Address+"&country="+Country+"&zipcode="+Zipcode+"&updateaccount"+"&device_id="+Splash.device_id;
															new UpdateProgress().execute();
														}
													}
												}
											}
										}
									}
									else
									{
										//work for progress
										URL="/api/checkout.php?user_id="+LoginId+"&fname="+Fname+"&lname="+Lname+"&address="+Address+"&country="+Country+"&zipcode="+Zipcode+"&ship_fname="+S_Fname+"&ship_lname="+S_Lname+"&ship_address="+S_Address+"&ship_country="+S_Country+"&ship_zipcode="+S_Zipcode+"&updateaccount&differentshipping"+"&device_id="+Splash.device_id;
										new UpdateProgress().execute();
									}
								}
							}
						}
					}
				}


			}
		});

//		if(LoginId==0)
//		{
//
//		}
//		else
//		{
		new ProgressBar().execute();
//		}

		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(checkbox.isChecked())
				{
					edt_sfname.setText("");
					edt_slname.setText("");
					edt_scountry.setText("");
					edt_saddress.setText("");
					edt_szipcode.setText("");

					edt_sfname.setHint("First Name");
					edt_slname.setHint("Last name");
					edt_scountry.setHint("Country");
					edt_saddress.setHint("Address");
					edt_szipcode.setHint("Zip Code");
				}
				else
				{
					edt_sfname.setText(S_Fname);
					edt_slname.setText(S_Lname);
					edt_scountry.setText(S_Country);
					edt_saddress.setText(S_Address);
					edt_szipcode.setText(S_Zipcode);
				}
			}
		});

		return view;
	}
	public class ProgressBar extends AsyncTask<String, String, String>
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
			//http://www.puyangan.com/api/cart.php?user_id=67//http://www.puyangan.com/api/checkout.php?user_id=67
			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/checkout.php?user_id="+LoginId+"&device_id="+Splash.device_id);
			try {
				//http://www.puyangan.com/api/category.php?cid=178
				// Getting Array of Employee
				userinfo = json.getJSONArray("userinfo");
				length= userinfo.length();
				// looping of List
				for (int i = 0; i < length; i++) {
					JSONObject c = userinfo.getJSONObject(i);
					//					Bitmap myBitmap = null;
					InputStream input = null;
					Fname = c.getString("fname");
					Lname = c.getString("lname");
					Email = c.getString("email");
					Country = c.getString("country");
					Address = c.getString("address");
					Zipcode = c.getString("zipcode");

					S_Fname = c.getString("ship_fname");
					S_Lname = c.getString("ship_lname");
					S_Country = c.getString("ship_country");
					S_Address = c.getString("ship_address");
					S_Zipcode = c.getString("ship_zipcode");


				}
			}
			catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pdia.dismiss();
			pdia = null;

			edtfname.setText(Fname);
			edtlname.setText(Lname);
			edtemail.setText(Email);
			edtcountry.setText(Country);
			edtaddress.setText(Address);
			edtzipcode.setText(Zipcode);

			edt_sfname.setText(S_Fname);
			edt_slname.setText(S_Lname);
			edt_scountry.setText(S_Country);
			edt_saddress.setText(S_Address);
			edt_szipcode.setText(S_Zipcode);
		}

	}
	public class UpdateProgress extends AsyncTask<String,String, String>
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
			//http://www.puyangan.com/api/cart.php?user_id=67//http://www.puyangan.com/api/checkout.php?user_id=67
			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+URL);

			return null;
		}
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pdia.dismiss();
			pdia = null;
			AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT).create();

			alertDialog.setTitle("Successfully updated.");
			alertDialog.setCancelable(false);
//			alertDialog.setMessage(Html.fromHtml("<font color='#00478f'><b>Are you sure?</b></font>"));
			alertDialog.setIcon(R.mipmap.launchicon);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
//					Payment fragment = new Payment();
//					FragmentManager fm = getActivity().getSupportFragmentManager();
//					FragmentTransaction ft = fm.beginTransaction();
//					ft.add(R.id.content_frame, fragment);
//					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//					ft.addToBackStack("add"+Home.add);
//					ft.commit();
//					Home.add++;
//					Fragment fragment = new Payment();
//					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//					fragmentTransaction.replace(R.id.content_frame, fragment);
//					fragmentTransaction.addToBackStack(null);
//					fragmentTransaction.commit();
					fragment = new Payment();
					ft = getActivity().getSupportFragmentManager().beginTransaction();
//					ft.replace(R.id.frame,fragment);
//					ft.commit();
					ft.add(R.id.frame, fragment);
					ft.addToBackStack("add" + MainActivity.add);
					ft.commit();
					MainActivity.add++;
				}
			});
			alertDialog.show();
		}
	}
}