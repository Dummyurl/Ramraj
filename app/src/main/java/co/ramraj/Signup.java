package co.ramraj;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
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


public class Signup extends Fragment implements OnClickListener{
	Button btnsignin;
	String Fname,Lname,Email,Password,Cpassword,Address,State,Zcode,Country,Month,Day,Subscribe;
	EditText fname,lname,email,password,cpassword,address,state,zcode,country;
	Spinner month,day;
	Switch subscriber;
	TextView confirmerror,txtsignup;
	String emailPattern = "[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+";
	ArrayAdapter<String> dataAdapter;
	String value;
	JSONArray responce = null;
	String userId,message;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	String page;
	Fragment fragment;
	android.support.v4.app.FragmentTransaction ft;
	RelativeLayout idsignup;
	int check=1;
	ImageView btnsubmit;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.accountsignup, container, false);
		//img=(ImageView)view.findViewById(R.id.banner);

		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putBoolean("Signinpage", true);










		editor.commit();
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
		btnsignin=(Button)view.findViewById(R.id.btnsignin);
		btnsubmit=(ImageView) view.findViewById(R.id.btnsubmit);

		fname=(EditText)view.findViewById(R.id.edtfname);
		lname=(EditText)view.findViewById(R.id.edtlname);
		email=(EditText)view.findViewById(R.id.edtemail);
		password=(EditText)view.findViewById(R.id.edtpassword);
		cpassword=(EditText)view.findViewById(R.id.edtconfermpassword);
		address=(EditText) view.findViewById(R.id.edtaddress);
		state=(EditText)view.findViewById(R.id.edtstate);
		zcode=(EditText)view.findViewById(R.id.edtzipcode);
		country=(EditText)view.findViewById(R.id.edtcountry);
		month=(Spinner)view.findViewById(R.id.spmonth);
		day=(Spinner)view.findViewById(R.id.spdate);
		confirmerror=(TextView)view.findViewById(R.id.confirmerror);
		txtsignup=(TextView)view.findViewById(R.id.txtsignup);
		subscriber=(Switch)view.findViewById(R.id.subscrib);
		btnsubmit.setOnClickListener(this);
		btnsignin.setOnClickListener(this);
		Subscribe = "no";
		btnsubmit.setEnabled(false);
		//		month.setOnClickListener(this);
		//		day.setOnClickListener(this);

		txtsignup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
			if(check==1)
			{
				btnsubmit.setEnabled(true);
				check=0;
			}
				else
			{
				btnsubmit.setEnabled(false);
				check=1;
			}



			}
		});
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
				Month = "" + monthInt;
				if (monthInt < 10) {
					monthInt = 0 + monthInt;
				}
				if ((monthInt == 01) || (monthInt == 03) || (monthInt == 05) || (monthInt == 07) || (monthInt == 10) || (monthInt == 12)) {
					dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list1);

				} else if ((monthInt == 04) || (monthInt == 06) || (monthInt == 9) || (monthInt == 11)) {
					dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list2);

				} else if ((monthInt == 02)) {
					dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list3);

				} else {
					dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list1);

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
				} catch (Exception e) {
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
				Day = day.getSelectedItem().toString();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		subscriber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// do something, the isChecked will be
				// true if the switch is in the On position
				if (isChecked) {
					Subscribe = "yes";

				} else {
					Subscribe = "no";

				}
			}
		});

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

		fname.setKeyListener(nameinput);
		lname.setKeyListener(nameinput);

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
		email.setKeyListener(emailinput);

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
		address.setKeyListener(addressinput);

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
		state.setKeyListener(stateinput);

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
		zcode.setKeyListener(zipinput);

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
		country.setKeyListener(countryinput);






		NumberKeyListener name1 = new NumberKeyListener() {

			public int getInputType() {
				return InputType.TYPE_MASK_VARIATION;
			}

			@Override
			protected char[] getAcceptedChars() {
				return new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
						'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
						'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '@', '.', '_', '#', '$', '%', '&', '*', '-', '+', '(', ')', '!', '"', '\'', ':',
						';', '/', '?', ',', '~', '`', '|', '\\', '^', '<', '>', '{', '}', '[', ']', '=', '.', '�','\n',' ','�', '�', '�', '�'};
			}
		};









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
		return view;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btnsignin:
//			Fragment fragment = new Signin();
//			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//			fragmentTransaction.replace(R.id.content_frame, fragment);
//			fragmentTransaction.addToBackStack(null);
//			fragmentTransaction.commit();
			fragment = new Signin();
			ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.frame,fragment);
			ft.commit();
			break;
		case R.id.btnsubmit:
			Fname=fname.getText().toString();
			Lname=lname.getText().toString();
			Email=email.getText().toString();
			Password=password.getText().toString();
			Cpassword=cpassword.getText().toString();
			Address=address.getText().toString();
			State=state.getText().toString();
			Zcode=zcode.getText().toString();
			Country=country.getText().toString();

			if(Fname.equalsIgnoreCase(""))
			{
				fname.requestFocus();
				fname.setText("");
				fname.setError("Please fill fname");
			}
			else
			{
				if(Lname.equalsIgnoreCase(""))
				{
					lname.requestFocus();
					lname.setText("");
					lname.setError("Please fill lname");
				}
				else
				{
					if(Email.equalsIgnoreCase(""))
					{
						email.requestFocus();
						email.setText("");
						email.setError("Please fill email");
					}
					else
					{
						if (!Email.matches(emailPattern))
						{
							email.requestFocus();
							email.setText("");
							email.setError("Invalid email id.");
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
														new Register().execute();
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
				}
			}
			break;
		}
	}
	public class Register extends AsyncTask<String, String, String>
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
			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/register.php?fname="+URLEncoder.encode(Fname)+"&lname="+URLEncoder.encode(Lname)+"&email="+URLEncoder.encode(Email)+"&pwd="+Password+"&pwd2="+Cpassword+"&address="+URLEncoder.encode(Address)+"&state="+URLEncoder.encode(State)+"&country="+URLEncoder.encode(Country)+"&zipcode="+Zcode+"&month="+Month+"&day="+Day+"&subscriber="+Subscribe+"&registered=1");
			try {
				// Getting Array of Employee
				responce = json.getJSONArray("success");
				int length= responce.length();
				// looping of List
				for (int i = 0; i < length; i++) {
					JSONObject c = responce.getJSONObject(i);
					userId=c.getString("login_user_id");
					message=c.getString("message");
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
			if(Integer.parseInt(userId)==0)
			{

				AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT).create();
				alertDialog.setTitle(message);

				alertDialog.setIcon(R.mipmap.launchicon);
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				alertDialog.show();
			}
			else
			{
				SharedPreferences.Editor editor = sharedpreferences.edit();
				editor.putInt("LoginId",Integer.parseInt(userId));
				editor.putString("email",Email);
				editor.commit();

				sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
				page =sharedpreferences.getString("page", "");

//				if(page.equalsIgnoreCase("Order"))
//				{
////					Fragment fragment = new Order();
////					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////					fragmentTransaction.replace(R.id.content_frame, fragment);
////					fragmentTransaction.addToBackStack(null);
////					fragmentTransaction.commit();
//					 fragment = new Order();
//					ft = getActivity().getSupportFragmentManager().beginTransaction();
//					ft.replace(R.id.frame,fragment);
//					ft.commit();
//				}
//				else if(page.equalsIgnoreCase("Product"))
//				{
////					Fragment fragment = new Product();
////					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////					fragmentTransaction.replace(R.id.content_frame, fragment);
////					fragmentTransaction.addToBackStack(null);
////					fragmentTransaction.commit();
//					 fragment = new Product();
//					ft = getActivity().getSupportFragmentManager().beginTransaction();
//					ft.replace(R.id.frame,fragment);
//					ft.commit();
//				}
//				else if(page.equalsIgnoreCase("productdetails"))
//				{
////					Fragment fragment = new ProductDetails();
////					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////					fragmentTransaction.replace(R.id.content_frame, fragment);
////					fragmentTransaction.addToBackStack(null);
////					fragmentTransaction.commit();
//					 fragment = new ProductDetails();
//					ft = getActivity().getSupportFragmentManager().beginTransaction();
//					ft.replace(R.id.frame,fragment);
//					ft.commit();
//				}
//				else
//				{
//					Fragment fragment = new Categories();
//					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//					fragmentTransaction.replace(R.id.content_frame, fragment);
//					fragmentTransaction.addToBackStack(null);
//					fragmentTransaction.commit();
					 fragment = new Categories();
					ft = getActivity().getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.frame,fragment);
					ft.commit();
//				}


			}
		}
	}
}
