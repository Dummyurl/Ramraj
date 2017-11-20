package co.ramraj;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


public class Payment extends Fragment implements AdapterView.OnItemSelectedListener{
	RadioButton radio1,radio2;
	CheckBox checkbox;
	Button btnpayment;
	int LoginId;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	LinearLayout rlpayment;
	Spinner spcardtype,spMonth,spYear;
	EditText cardnumber,csc;
	private String[] payment_type = { "Select card","Solo","Maestro","Amex","Discover","Visa","Mastercard"};
	public  String[] SpMonth = { "01","02","03","04","05","06","07","08","09","10","11","12"};
	public Integer[] SpYear ;//= { "2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030"};
	String CardNumber,Month,Year,CSCNumber;
	String CardType;
	TextView errortext,txtammount;
	Integer currentYear;
	SharedPreferences prfs;
	String Via;
	int length;
	JSONArray payment=null;
	String success,message;
	JSONObject json;
	String URL;
	Fragment fragment;
	android.support.v4.app.FragmentTransaction ft;
	RelativeLayout idpayment;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.payment_activity, container, false);
		//img=(ImageView)view.findViewById(R.id.banner);
		radio1=(RadioButton)view.findViewById(R.id.radio1);
		radio2=(RadioButton)view.findViewById(R.id.radio2);
		checkbox=(CheckBox)view.findViewById(R.id.checkBox1);
		btnpayment=(Button)view.findViewById(R.id.btnpayment);
		rlpayment=(LinearLayout)view.findViewById(R.id.rlpayment);
		txtammount=(TextView)view.findViewById(R.id.txtamount);
		txtammount.setText("$"+Cart.Total_Price);
		radio2.setChecked(true);
//		rlpayment.setVisibility(View.VISIBLE);
		btnpayment.setEnabled(false);


		radio1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				radio1.setChecked(true);
				radio2.setChecked(false);
				rlpayment.setVisibility(View.GONE);
			}
		});
		radio2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				radio2.setChecked(true);
				radio1.setChecked(false);
				rlpayment.setVisibility(View.VISIBLE);
			}
		});
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					btnpayment.setEnabled(true);
				}
				else
				{
					btnpayment.setEnabled(false);
				}
			}
		});

		currentYear = Calendar.getInstance().get(Calendar.YEAR);
		SpYear =new Integer[] { currentYear,currentYear+1,currentYear+2,currentYear+3,currentYear+4,currentYear+5,currentYear+5,currentYear+6,currentYear+7,currentYear+8,currentYear+9,currentYear+10};

		spcardtype=(Spinner)view.findViewById(R.id.sptype);
		spMonth=(Spinner)view.findViewById(R.id.spmonth);
		spYear=(Spinner)view.findViewById(R.id.spyear);

		cardnumber=(EditText)view.findViewById(R.id.cardnumber);
		csc=(EditText)view.findViewById(R.id.cscnumber);
		btnpayment=(Button)view.findViewById(R.id.btnpayment);

		ArrayAdapter<String> adapter_type = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item, payment_type);
		spcardtype.setAdapter(adapter_type);
		spcardtype.setOnItemSelectedListener(this);

		ArrayAdapter<String> adapter_month = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item, SpMonth);
		spMonth.setAdapter(adapter_month);
		spMonth.setOnItemSelectedListener(this);

		ArrayAdapter<Integer> adapter_year = new ArrayAdapter<Integer>(getActivity(),R.layout.spinner_item, SpYear);
		spYear.setAdapter(adapter_year);
		spYear.setOnItemSelectedListener(this);


		errortext=(TextView)view.findViewById(R.id.errortext);



		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
		LoginId =sharedpreferences.getInt("LoginId", 0);

		//			new ProgressBar().execute();	
		btnpayment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
				LoginId =sharedpreferences.getInt("LoginId", 0);
				if(radio1.isChecked())
				{


						URL="/api/gateways/cod.php?user_id="+LoginId+"&device_id="+Splash.device_id;


					new PaymentProgressBar().execute();
				}
				else
				{
					CardNumber=cardnumber.getText().toString();
					CSCNumber=csc.getText().toString();
					CardType = spcardtype.getSelectedItem().toString();
					Month=spMonth.getSelectedItem().toString();
					Year=spYear.getSelectedItem().toString();

					if(CardType.equalsIgnoreCase("Select card"))
					{
						errortext.setVisibility(View.VISIBLE);
						errortext.setText("Please select card type");
					}
					else
					{

						if((CardNumber.equalsIgnoreCase(""))||(CardNumber.length()<16))
						{
							cardnumber.setError("Fill valid cardnumber.");
							cardnumber.requestFocus();
							cardnumber.setText("");
						}
						else
						{


							if((CSCNumber.equalsIgnoreCase(""))||(CSCNumber.length()<3))
							{
								csc.setError("Fill csc number.");
								csc.requestFocus();
								csc.setText("");
								csc.setFocusable(true);
							}
							else
							{

									URL="/api/gateways/paypal.php?user_id="+LoginId+"&cctype="+CardType+"&cc_number="+CardNumber+"&cc_code="+CSCNumber+"&cc_month="+Month+"&cc_year="+Year+"&device_id="+Splash.device_id;



								new PaymentProgressBar().execute();
							}

						}
					}
				}
			}
			//			}
		});
		return view;
	}
	class PaymentProgressBar extends AsyncTask<String, String, String> {
		private ProgressDialog pdia;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdia = new ProgressDialog(getActivity());
			pdia.setMessage("Loading...");
			pdia.show();
			pdia.setCancelable(false);
			// Log.e("pre","execute");
		}
		@Override
		protected String doInBackground(String... f_url) {
			//Log.e("do","execute");
			JSONParser jParser = new JSONParser();

			// getting JSON string from URL//http://www.puyangan.com/api/gateways/cod.php?user_id=29
			//http://www.puyangan.com/api/paypal.php?user_id=47&cctype=Visa&cc_number=646464645654654&cc_code=000&cc_month=03&cc_year=2019




				json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+URL);

			try {
				// Getting Array of Employee
				payment = json.getJSONArray("payment");
				length= payment.length();
				// looping of List
				for (int i = 0; i < length; i++) {
					JSONObject c = payment.getJSONObject(i);

					// Storing each json item in variable

					success = c.getString("success");
					message = c.getString("message");
				}
			} 
			catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {
			//				try {
			//					//Log.e("post","execute");
			pdia.dismiss();
			pdia = null;

			if(Integer.parseInt(success)==1)
			{
				AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT).create();

				alertDialog.setTitle(message);
				alertDialog.setCancelable(false);
				//				alertDialog.setMessage(Html.fromHtml("<font color='#00478f'><b>Are you sure?</b></font>"));
				alertDialog.setIcon(R.mipmap.launchicon);
				alertDialog.setButton("OK", new  DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						FragmentManager manager = getActivity().getSupportFragmentManager();
						if (manager.getBackStackEntryCount() > 0) {
							// If there are back-stack entries, leave the FragmentActivity
							// implementation take care of them.
							int l=manager.getBackStackEntryCount();
							for(int i=0;i<=l;i++)
							{
								manager.popBackStack();
							}
							

						} else {
							// Otherwise, ask user if he wants to leave :)
//							super.onBackPressed();
						}
						
//						Signin fragment = new Signin();
//						FragmentManager fm = getActivity().getSupportFragmentManager();
//						FragmentTransaction ft = fm.beginTransaction();
//						ft.add(R.id.content_frame, fragment);
//						ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//						ft.addToBackStack("add" + MainActivity.add);
//						ft.commit();
//						MainActivity.add++;
//						Home.mDrawerList.setItemChecked(4, true);
						fragment = new Order();
						ft = getActivity().getSupportFragmentManager().beginTransaction();
//						ft.replace(R.id.frame,fragment);
//						ft.commit();
						ft.add(R.id.frame, fragment);
//						ft.addToBackStack("add" + MainActivity.add);
						ft.commit();
//						MainActivity.add++;
					}
				});
				alertDialog.show();
			}
			else
			{
				errortext.setVisibility(View.VISIBLE);
				errortext.setText(message);
			}
		}
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		errortext.setVisibility(View.GONE);

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
}