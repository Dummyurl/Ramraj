package co.ramraj;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ProductDetails extends Fragment {
	Button back,addtocart,btnvieworder;
	TextView title,name,price,descriptiontitle,description;
	ImageView productimg;
	JSONArray products = null;
	String PD_Id,PD_Name,PD_Image,PD_Regular_price,PD_Sale_price,PD_Price,PD_Description;
	Bitmap myBitmap;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	int  LoginId;
	public static String str_productdetailsname;
	TextView txtstore;
	JSONArray cartinfo = null;
	RelativeLayout idproductdetails;
	Fragment fragment;
	android.support.v4.app.FragmentTransaction ft;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.product_detail, container, false);
		back=(Button)view.findViewById(R.id.btnback);
		addtocart=(Button)view.findViewById(R.id.btnaddtocart);
		title=(TextView)view.findViewById(R.id.txttitle);
		name=(TextView)view.findViewById(R.id.txtname);
		price=(TextView)view.findViewById(R.id.txtprice);
		descriptiontitle=(TextView)view.findViewById(R.id.txtdescriptiontitle);
		description=(TextView)view.findViewById(R.id.txtdescription);
		productimg=(ImageView)view.findViewById(R.id.productimg);
		txtstore=(TextView)view.findViewById(R.id.txt11);
		btnvieworder=(Button)view.findViewById(R.id.btnvieworder);
		idproductdetails=(RelativeLayout)view.findViewById(R.id.idproductdetails);
		idproductdetails.setVisibility(View.GONE);
		title.setText(Categories.str_categories+Categories.str_subcategories+Product.str_product);
		txtstore.setText(Categories.store);
		new Progress().execute();

		addtocart.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
				LoginId =sharedpreferences.getInt("LoginId", 0);
//				if(LoginId==0)
//				{
//					AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT).create();
//
//					alertDialog.setTitle("First Signin");
//					alertDialog.setIcon(R.drawable.lounchicon);
//					alertDialog.setButton("SIGNIN", new  DialogInterface.OnClickListener() {
//
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							//							TabStackTemple.this.finish();
//							SharedPreferences.Editor editor = sharedpreferences.edit();
//							editor.putString("page", "productdetails");
//							editor.commit();
//
//
////							Home.mDrawerList.setItemChecked(1, true);
////							Fragment fragment = new Signin();
////							FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////							FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////							fragmentTransaction.replace(R.id.content_frame, fragment);
////							fragmentTransaction.addToBackStack(null);
////							fragmentTransaction.commit();
//							fragment = new Signin();
//							ft = getActivity().getSupportFragmentManager().beginTransaction();
//							ft.replace(R.id.frame,fragment);
//							ft.commit();
//						}
//					});
//					alertDialog.setButton2("CANCEL", new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialog, int which) {
//
//						}
//					});
//					alertDialog.show();
//
//
//
//
//
//				}
//				else
//				{
					new AddtoCartProgressBar().execute();
//				}

			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager manager = getActivity().getSupportFragmentManager();
				manager.popBackStack();
			}
		});
		btnvieworder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Order fragment = new Order();
//				FragmentManager fm = getActivity().getSupportFragmentManager();
//				FragmentTransaction ft = fm.beginTransaction();
//				ft.add(R.id.content_frame, fragment);
//				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//				ft.addToBackStack("add"+MainActivity.add);
//				ft.commit();
//				MainActivity.add++;
//				fragment = new Order();
//				ft = getActivity().getSupportFragmentManager().beginTransaction();
//				ft.replace(R.id.frame,fragment);
//				ft.commit();
				fragment = new Order();
				ft = getActivity().getSupportFragmentManager().beginTransaction();
				ft.add(R.id.frame,fragment);
				ft.addToBackStack("add" + MainActivity.add);
				ft.commit();
				MainActivity.add++;
			}
		});
		//img=(ImageView)view.findViewById(R.id.banner);
		return view;
	}
	public class AddtoCartProgressBar extends AsyncTask<String, String, String>
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
			// getting JSON string from URL//checkuserforgotpwd=1,user_login=emailid
			
			JSONObject[] json = new JSONObject[2];
			json[0] = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/addtocart.php?user_id="+LoginId+"&product_id="+Product.Product_Id+"&qunt=1"+"&device_id="+Splash.device_id);
			json[1] = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/cart.php?user_id="+LoginId+"&device_id="+Splash.device_id);
			
			
			
//			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/addtocart.php?user_id="+LoginId+"&product_id="+Product.Product_Id+"&qunt=1");
			try {
				//http://www.puyangan.com/api/category.php
				// Getting Array of Employee
				cartinfo = json[1].getJSONArray("cartinfo");
				int lengths=cartinfo.length();
				
				for(int j=0;j<lengths;j++)
				{
					JSONObject cc = cartinfo.getJSONObject(j);

					Categories.store=cc.getString("totalitems");

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
			txtstore.setText(Categories.store);
		}
	}
	public class Progress extends AsyncTask<String, String, String>
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
			// getting JSON string from URL//checkuserforgotpwd=1,user_login=emailid
			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/products.php?pid="+Product.Product_Id);
			try {
				//http://www.puyangan.com/api/category.php?cid=178
				// Getting Array of Employee
				products = json.getJSONArray("products");
				int length= products.length();
				// looping of List
				for (int i = 0; i < length; i++) {
					JSONObject c = products.getJSONObject(i);
					//					Bitmap myBitmap = null;
					InputStream input = null;
					PD_Id = c.getString("id");
					PD_Name = c.getString("name");
					PD_Image = c.getString("image");
					PD_Regular_price = c.getString("regular_price");
					PD_Sale_price = c.getString("sale_price");
					PD_Price = c.getString("price");
					PD_Description=c.getString("description");

					if(PD_Image.equalsIgnoreCase(""))
					{

					}
					else
					{

						URL url = new URL(PD_Image);
						HttpURLConnection connection = (HttpURLConnection) url.openConnection();
						connection.setDoInput(true);
						connection.setInstanceFollowRedirects(false);
						connection.setRequestMethod("GET");
						connection.connect();
						input = connection.getInputStream();

						//						BitmapFactory.Options opts = new BitmapFactory.Options();
						//						// opts.inJustDecodeBounds = true; 
						//						opts.inSampleSize=4;    
						//						myBitmap = BitmapFactory.decodeStream(input,null, opts);
						//						Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 200, 200, true);
						//myBitmap.recycle();

						myBitmap = BitmapFactory.decodeStream(input);
						//						h.setBitmap(myBitmap);
						connection.disconnect();
					}
				}
			} 
			catch (JSONException e) {
				e.printStackTrace();
			}
			catch(IOException e)
			{

			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pdia.dismiss();
			pdia = null;
			idproductdetails.setVisibility(View.VISIBLE);
//			title.setText("Title");
			name.setText(PD_Name);
			price.setText("$"+PD_Price);
			if(PD_Description.equalsIgnoreCase(""))
			{
				description.setText(R.string.dummydata);
			}
			else
			{
				description.setText(PD_Description);
			}

			productimg.setImageBitmap(myBitmap);




		}

	}
}