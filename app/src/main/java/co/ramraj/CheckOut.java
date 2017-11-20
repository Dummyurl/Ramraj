package co.ramraj;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
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
import java.net.URLEncoder;


public class CheckOut extends Fragment {
	String Fname,Lname,Email,Address,State,Zipcode,Country,S_Fname,S_Lname,S_Address,S_State,S_Zipcode,S_Country;
	EditText edtfname,edtlname,edtemail,edtaddress,edtstate,edtzipcode,edtcountry,edt_sfname,edt_slname,edt_saddress,edt_sstate,edt_szipcode,edt_scountry;
	JSONArray userinfo = null;
	int length;
	CheckBox checkbox;
	int LoginId;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	Button btnProceed;
	String URL;
	RelativeLayout idcheckout;
	Fragment fragment;
	FragmentTransaction ft;
	String emailPattern = "[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+";
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
		btnProceed=(Button)view.findViewById(R.id.btnProceed);

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



		NumberKeyListener nameinput = new NumberKeyListener() {

			public int getInputType() {
				return InputType.TYPE_MASK_VARIATION;
			}

			@Override
			protected char[] getAcceptedChars() {
				return new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
						'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',' '};
			}
		};

		edtfname.setKeyListener(nameinput);
		edtlname.setKeyListener(nameinput);

		edt_sfname.setKeyListener(nameinput);
		edt_slname.setKeyListener(nameinput);


		NumberKeyListener emailinput = new NumberKeyListener() {

			public int getInputType() {
				return InputType.TYPE_MASK_VARIATION;
			}

			@Override
			protected char[] getAcceptedChars() {
				return new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
						'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '@', '.', '_'};
			}
		};
		edtemail.setKeyListener(emailinput);

		NumberKeyListener addressinput = new NumberKeyListener() {

			public int getInputType() {
				return InputType.TYPE_MASK_VARIATION;
			}

			@Override
			protected char[] getAcceptedChars() {
				return new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
						'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
						'0', '1', '2', '3', '4', '5', '6', '7', '8', '9','.', '_', '#', ',',' ','-',':'};
			}
		};
		edtaddress.setKeyListener(addressinput);
		edt_saddress.setKeyListener(addressinput);

		NumberKeyListener stateinput = new NumberKeyListener() {

			public int getInputType() {
				return InputType.TYPE_MASK_VARIATION;
			}

			@Override
			protected char[] getAcceptedChars() {
				return new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
						'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
						,' '};
			}
		};
		edtstate.setKeyListener(stateinput);
		edt_sstate.setKeyListener(stateinput);

		NumberKeyListener zipinput = new NumberKeyListener() {

			public int getInputType() {
				return InputType.TYPE_MASK_VARIATION;
			}

			@Override
			protected char[] getAcceptedChars() {
				return new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
						'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
						'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
			}
		};
		edtzipcode.setKeyListener(zipinput);
		edt_szipcode.setKeyListener(zipinput);

		NumberKeyListener countryinput = new NumberKeyListener() {

			public int getInputType() {
				return InputType.TYPE_MASK_VARIATION;
			}

			@Override
			protected char[] getAcceptedChars() {
				return new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
						'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
				};
			}
		};
		edtcountry.setKeyListener(countryinput);
		edt_scountry.setKeyListener(countryinput);








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
		btnProceed.setOnClickListener(new OnClickListener() {

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
						if(Email.equalsIgnoreCase(""))
						{
							edtemail.requestFocus();
							edtemail.setText("");
							edtemail.setError("Please fill email");
						}
						else
						{
							if(Email.matches(emailPattern))
							{
								if(Address.equalsIgnoreCase(""))
								{
									edtaddress.requestFocus();
									edtaddress.setText("");
									edtaddress.setError("Please fill address");
								}
								else
								{
									if(State.equalsIgnoreCase(""))
									{
										edtstate.requestFocus();
										edtstate.setText("");
										edtstate.setError("Please fill email");
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
											if(Country.equalsIgnoreCase(""))
											{
												edtcountry.requestFocus();
												edtcountry.setText("");
												edtcountry.setError("Please fill country");
											}
											else
											{
												if(!checkbox.isChecked())
												{
													URL="/api/checkout.php?user_id="+LoginId+"&fname="+ URLEncoder.encode(Fname)+"&lname="+URLEncoder.encode(Lname)+"&email="+URLEncoder.encode(Email)+"&address="+URLEncoder.encode(Address)+"&state="+URLEncoder.encode(State)+"&country="+URLEncoder.encode(Country)+"&zipcode="+URLEncoder.encode(Zipcode)+"&updateaccount"+"&device_id="+URLEncoder.encode(Splash.device_id);
													new ProceedProgress().execute();
												}
												else
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
																edt_slname.setError("Please fill last anme");
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
																	if(S_State.equalsIgnoreCase(""))
																	{
																		edt_sstate.requestFocus();
																		edt_sstate.setText("");
																		edt_sstate.setError("Please fill state");
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
																			if(S_Country.equalsIgnoreCase(""))
																			{
																				edt_scountry.requestFocus();
																				edt_scountry.setText("");
																				edt_scountry.setError("Please fill country");
																			}
																			else
																			{
																				URL="/api/checkout.php?user_id="+LoginId+"&fname="+URLEncoder.encode(Fname)+"&lname="+URLEncoder.encode(Lname)+"&email="+URLEncoder.encode(Email)+"&address="+URLEncoder.encode(Address)+"&state="+URLEncoder.encode(State)+"&country="+URLEncoder.encode(Country)+"&zipcode="+URLEncoder.encode(Zipcode)+"&ship_fname="+URLEncoder.encode(S_Fname)+"&ship_lname="+URLEncoder.encode(S_Lname)+"&ship_address="+URLEncoder.encode(S_Address)+"&ship_state="+URLEncoder.encode(S_State)+"&ship_country="+URLEncoder.encode(S_Country)+"&ship_zipcode="+URLEncoder.encode(S_Zipcode)+"&updateaccount&differentshipping"+"&device_id="+URLEncoder.encode(Splash.device_id);
																				new ProceedProgress().execute();
																			}
																		}
																	}
																}
															}
														}


												}
											}
										}
									}
								}
							}
							else
							{
								edtemail.requestFocus();
								edtemail.setText("");
								edtemail.setError("Invalid email id.");
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
					edt_saddress.setText("");
					edt_sstate.setText("");
					edt_szipcode.setText("");
					edt_scountry.setText("");


					edt_sfname.setHint("First Name");
					edt_slname.setHint("Last name");

					edt_saddress.setHint("Address");
					edt_sstate.setHint("State");
					edt_szipcode.setHint("Zip Code");
					edt_scountry.setHint("Country");
				}
				else
				{
					edt_sfname.setText(S_Fname);
					edt_slname.setText(S_Lname);

					edt_saddress.setText(S_Address);
					edt_sstate.setText(S_State);
					edt_szipcode.setText(S_Zipcode);
					edt_scountry.setText(S_Country);
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

					Address = c.getString("address");
					State = c.getString("state");
					Zipcode = c.getString("zipcode");
					Country = c.getString("country");

					S_Fname = c.getString("ship_fname");
					S_Lname = c.getString("ship_lname");

					S_Address = c.getString("ship_address");
					S_State = c.getString("ship_state");
					S_Zipcode = c.getString("ship_zipcode");
					S_Country = c.getString("ship_country");


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

			edtaddress.setText(Address);
			edtstate.setText(State);
			edtzipcode.setText(Zipcode);
			edtcountry.setText(Country);

			edt_sfname.setText(S_Fname);
			edt_slname.setText(S_Lname);

			edt_saddress.setText(S_Address);
			edt_sstate.setText(S_State);
			edt_szipcode.setText(S_Zipcode);
			edt_scountry.setText(S_Country);
		}

	}
	public class ProceedProgress extends AsyncTask<String,String, String>
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
//			AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT).create();
//
//			alertDialog.setTitle("Successfully updated.");
//			alertDialog.setCancelable(false);
////			alertDialog.setMessage(Html.fromHtml("<font color='#00478f'><b>Are you sure?</b></font>"));
//			alertDialog.setIcon(R.drawable.lounchicon);
//			alertDialog.setButton("OK", new  DialogInterface.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
////					Payment fragment = new Payment();
////					FragmentManager fm = getActivity().getSupportFragmentManager();
////					FragmentTransaction ft = fm.beginTransaction();
////					ft.add(R.id.content_frame, fragment);
////					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
////					ft.addToBackStack("add"+Home.add);
////					ft.commit();
////					Home.add++;
////					Fragment fragment = new Payment();
////					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////					fragmentTransaction.replace(R.id.content_frame, fragment);
////					fragmentTransaction.addToBackStack(null);
////					fragmentTransaction.commit();
//					fragment = new Payment();
//					ft = getActivity().getSupportFragmentManager().beginTransaction();
////					ft.replace(R.id.frame,fragment);
////					ft.commit();
//					ft.add(R.id.frame, fragment);
//					ft.addToBackStack("add" + MainActivity.add);
//					ft.commit();
//					MainActivity.add++;
//				}
//			});
//			alertDialog.show();
			fragment = new Payment();
			ft = getActivity().getSupportFragmentManager().beginTransaction();
//					ft.replace(R.id.frame,fragment);
//					ft.commit();
			ft.add(R.id.frame, fragment);
			ft.addToBackStack("add" + MainActivity.add);
			ft.commit();
			MainActivity.add++;
		}
	}
}