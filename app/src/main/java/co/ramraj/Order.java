package co.ramraj;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
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


public class Order extends Fragment {
	ListView orderlist;
	JSONArray orders = null;
	ArrayList<Holder> arraylist = new ArrayList<Holder>();
	ArrayList<Holder> subarraylist = new ArrayList<Holder>();
	String ORDER_id,ORDER_price,ORDER_date,ORDER_status,ORDER_statusdate;
	TextView txtheader,emptyorder;
	int length,LoginId,checkorderid=0;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	RelativeLayout idorder;
	public static String Order_ID;
	Fragment fragment;
	FragmentManager fm;
	FragmentTransaction ft;
	Boolean check;
	String page;
	String sub;
	JSONArray orderitems = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.order, container, false);
		orderlist=(ListView)view.findViewById(R.id.orderlist);
		idorder=(RelativeLayout)view.findViewById(R.id.idorder);
		idorder.setVisibility(View.GONE);

		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		LoginId =sharedpreferences.getInt("LoginId", 0);
		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		emptyorder=(TextView)view.findViewById(R.id.emptyorder);
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putBoolean("Signinpage",false);
		check =sharedpreferences.getBoolean("Signinpage", false);
		page =sharedpreferences.getString("page", "");
		editor.putString("page","Order");

		//		img=(ImageView)view.findViewById(R.id.banner);

//
//
//			//			Home.mDrawerList.setItemChecked(1, true);
//
//
////			Fragment fragment = new Signin();
////			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////			fragmentTransaction.replace(R.id.content_frame, fragment);
////			fragmentTransaction.addToBackStack(null);
////			fragmentTransaction.commit();
//			fragment = new Signin();
//			ft = getActivity().getSupportFragmentManager().beginTransaction();
//			ft.replace(R.id.frame,fragment);
//			ft.commit();
//			if(LoginId==0)
//			{
//
//				if(check)
//				{
//					AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT).create();
//
//					alertDialog.setTitle("First Signin");
//					alertDialog.setIcon(R.drawable.lounchicon);
//					alertDialog.setButton("OK", new  DialogInterface.OnClickListener() {
//
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							//							TabStackTemple.this.finish();
//							fragment = new Signin();
//							ft = getActivity().getSupportFragmentManager().beginTransaction();
//							ft.replace(R.id.frame,fragment);
//							ft.commit();
//						}
//					});
//					alertDialog.show();
//				}
//				else
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
//							fragment = new Signin();
//							ft = getActivity().getSupportFragmentManager().beginTransaction();
//							ft.replace(R.id.frame, fragment);
//							ft.commit();
//						}
//					});
//					alertDialog.setButton2("CANCEL", new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialog, int which) {
//							if(page.equalsIgnoreCase("Cart"))
//							{
//								fragment = new Cart();
//								ft = getActivity().getSupportFragmentManager().beginTransaction();
//								ft.replace(R.id.frame, fragment);
//								ft.commit();
////								Home.mDrawerList.setItemChecked(2, true);
//							}
//							else if(page.equalsIgnoreCase("Order"))
//							{
//								fragment = new Order();
//								ft = getActivity().getSupportFragmentManager().beginTransaction();
//								ft.replace(R.id.frame, fragment);
//								ft.commit();
////								Home.mDrawerList.setItemChecked(3, true);
//							}
//							else if(page.equalsIgnoreCase("Categories"))
//							{
//								fragment = new Categories();
//								ft = getActivity().getSupportFragmentManager().beginTransaction();
//								ft.replace(R.id.frame, fragment);
//								ft.commit();
////								Home.mDrawerList.setItemChecked(4, true);
//							}
//							else if(page.equalsIgnoreCase("Recents"))
//							{
//								fragment = new Recents();
//								ft = getActivity().getSupportFragmentManager().beginTransaction();
//								ft.replace(R.id.frame, fragment);
//								ft.commit();
////								Home.mDrawerList.setItemChecked(5, true);
//							}
//							else if(page.equalsIgnoreCase("Features"))
//							{
//								fragment = new Features();
//								ft = getActivity().getSupportFragmentManager().beginTransaction();
//								ft.replace(R.id.frame, fragment);
//								ft.commit();
////								Home.mDrawerList.setItemChecked(6, true);
//							}
//							else if(page.equalsIgnoreCase("All"))
//							{
//								fragment = new All();
//								ft = getActivity().getSupportFragmentManager().beginTransaction();
//								ft.replace(R.id.frame, fragment);
//								ft.commit();
////								Home.mDrawerList.setItemChecked(7, true);
//							}
//							else
//							{
//								fragment = new Setting();
//								ft = getActivity().getSupportFragmentManager().beginTransaction();
//								ft.replace(R.id.frame,fragment);
//								ft.commit();
////								Home.mDrawerList.setItemChecked(8, true);
//							}
//						}
//					});
//
//
//					alertDialog.show();
//				}
//			}
//			else
//			{
		new OrderProgressBar().execute();
//			}




		editor.commit();


		return view;
	}
	public class OrderProgressBar extends AsyncTask<String, String, String>
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
			arraylist.clear();

			JSONParser jParser = new JSONParser();
			//http://www.puyangan.com/api/orders.php?user_id=29
			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/orders.php?user_id="+LoginId+"&device_id="+Splash.device_id+"&page="+1);
			try {
				//http://www.puyangan.com/api/category.php?cid=178
				// Getting Array of Employee
				orders = json.getJSONArray("orders");
				length= orders.length();
				// looping of List
				for (int i = 0; i < length; i++) {

					JSONObject c = orders.getJSONObject(i);
					//					Bitmap myBitmap = null;

					ORDER_id = c.getString("order_id");
					ORDER_price = c.getString("order_total");
					ORDER_date = c.getString("cdate");
					ORDER_status = c.getString("order_status");
					ORDER_statusdate=c.getString("orderstatusdate");

					sub=c.getString("orderitems");
					JSONObject jObj = new JSONObject(sub);
					orderitems = jObj.getJSONArray("orderitems");
					int len=orderitems.length();
					for (int j = 0; j < len; j++) {
						JSONObject cc = orderitems.getJSONObject(j);
						InputStream input = null;

						String product_name=cc.getString("product_name");
						String Image = cc.getString("image");
						Holder h1 = new Holder();
						h1.setImage(Image);
						if(Image.equalsIgnoreCase(""))
						{

						}
						else
						{

							URL url = new URL(Image);
							HttpURLConnection connection = (HttpURLConnection) url.openConnection();
							connection.setDoInput(true);
							connection.setInstanceFollowRedirects(false);
							connection.setRequestMethod("GET");
							connection.connect();
							input = connection.getInputStream();

							BitmapFactory.Options opts = new BitmapFactory.Options();
							// opts.inJustDecodeBounds = true;
							opts.inSampleSize=4;
							Bitmap myBitmap = BitmapFactory.decodeStream(input,null, opts);
							//						Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 200, 200, true);
							//myBitmap.recycle();

							//						Bitmap myBitmap = BitmapFactory.decodeStream(input);
							h1.setBitmap(myBitmap);
							connection.disconnect();
						}


						h1.setOrder_product_name(product_name);

						subarraylist.add(h1);
						Holder h = new Holder();
//						if(j==0)
//						{
//
//							h.setOrder_id(ORDER_id);
//							h.setOrder_price(ORDER_price);
//							h.setOrder_date(ORDER_date);
//							h.setOrder_status(ORDER_status);
//							h.setORDER_statusdate(ORDER_statusdate);
//
//						}
//						else
//						{
//
//							h.setOrder_id("");
//							h.setOrder_price(ORDER_price);
//							h.setOrder_date("");
//							h.setOrder_status(ORDER_status);
//							h.setORDER_statusdate(ORDER_statusdate);
//
//						}
						h.setOrder_id(ORDER_id);
						h.setOrder_price(ORDER_price);
						h.setOrder_date(ORDER_date);
						h.setOrder_status(ORDER_status);
						h.setORDER_statusdate(ORDER_statusdate);

						arraylist.add(h);

					}



//					Holder h = new Holder();
//					h.setOrder_id(ORDER_id);
//					h.setOrder_price(ORDER_price);
//					h.setOrder_date(ORDER_date);
//					h.setOrder_status(ORDER_status);
//
//					arraylist.add(h);

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
			orderlist.setAdapter(new MyCustomAdapter(getActivity(), arraylist, subarraylist));
//			ListUtils.setDynamicHeight(orderlist);
			idorder.setVisibility(View.VISIBLE);
			if(length==0)
			{
				emptyorder.setVisibility(View.VISIBLE);
			}
			else
			{
				emptyorder.setVisibility(View.GONE);
			}


		}

	}
	//	public static class ListUtils {
	//		public static void setDynamicHeight(ListView orderlist) {
	//			ListAdapter mListAdapter = orderlist.getAdapter();
	//			if (mListAdapter == null) {
	//				// when adapter is null
	//				return;
	//			}
	//			int height = 0;
	//			int desiredWidth = MeasureSpec.makeMeasureSpec(orderlist.getWidth(), MeasureSpec.UNSPECIFIED);
	//			for (int i = 0; i < mListAdapter.getCount(); i++) {
	//				View listItem = mListAdapter.getView(i, null, orderlist);
	//				listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
	//				height += listItem.getMeasuredHeight();
	//			}
	//			ViewGroup.LayoutParams params = orderlist.getLayoutParams();
	//			params.height = height + (orderlist.getHeight() * (mListAdapter.getCount() - 1));
	//			orderlist.setLayoutParams(params);
	//			orderlist.requestLayout();
	//		}
	//	}
	public static class ListUtils {
		public static void setDynamicHeight(ListView orderlist) {
			ListAdapter mListAdapter = orderlist.getAdapter();
			if (mListAdapter == null) {
				// when adapter is null
				return;
			}
			int height = 0;
			int desiredWidth = MeasureSpec.makeMeasureSpec(orderlist.getWidth(), MeasureSpec.UNSPECIFIED);
			for (int i = 0; i < mListAdapter.getCount(); i++) {
				View listItem = mListAdapter.getView(i, null, orderlist);
				listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
				height += listItem.getMeasuredHeight();
			}
			ViewGroup.LayoutParams params = orderlist.getLayoutParams();
			params.height = height;//+ (orderlist.getHeight() * (mListAdapter.getCount() - 1));
			orderlist.setLayoutParams(params);
			orderlist.requestLayout();
		}
	}
	class MyCustomAdapter extends BaseAdapter {

		LayoutInflater inflater;
		ArrayList<Holder> list;
		ArrayList<Holder> sublist;
		public MyCustomAdapter(FragmentActivity fragmentActivity, ArrayList<Holder> list,ArrayList<Holder> sublist) {
			inflater = LayoutInflater.from(fragmentActivity);
			this.list =list;
			this.sublist =sublist;
		}

		@Override
		public int getCount() {
			return sublist.size();
		}

		@Override
		public Object getItem(int paramInt) {
			return paramInt;
		}

		class ViewHolder{
			TextView txtdatetime,txtname,txtstatus,txtdeliverydate;
			Button btndetails;
			ImageView img;
		}

		@Override
		public long getItemId(int paramInt) {
			return paramInt;
		}
		@Override
		public View getView(final int paramInt, View paramView,ViewGroup paramViewGroup) {

			ViewHolder holder;
			if(paramView==null)
			{
				paramView = inflater.inflate(R.layout.order_listitems, paramViewGroup, false);
				holder= new ViewHolder();

				holder.txtdatetime = (TextView) paramView.findViewById(R.id.txtdatetime);
				holder.txtname = (TextView) paramView.findViewById(R.id.txtname);
				holder.txtstatus = (TextView) paramView.findViewById(R.id.txtstatus);
				holder.txtdeliverydate = (TextView) paramView.findViewById(R.id.txtdeliverydate);
				holder.btndetails = (Button) paramView.findViewById(R.id.btndetails);
				holder.img = (ImageView) paramView.findViewById(R.id.img);

				paramView.setTag(holder);
			}
			else{
				holder = (ViewHolder) paramView.getTag();
			}

			Holder h = list.get(paramInt);
			String O_id = h.getOrder_id();

			String O_price = h.getOrder_price();

			String O_date = h.getOrder_date();

			String O_status = h.getOrder_status();

			String O_setORDER_statusdate=h.getORDER_statusdate();

//			if(Integer.parseInt(O_id)!=checkorderid)
//			{
//				holder.txtdatetime.setVisibility(View.VISIBLE);
//				checkorderid=Integer.parseInt(O_id);
//			}
//			else
//			{
//				holder.txtdatetime.setVisibility(View.GONE);
//			}
			if(O_id.equalsIgnoreCase(""))
			{
				holder.txtdatetime.setVisibility(View.GONE);
			}
			else
			{
				holder.txtdatetime.setVisibility(View.VISIBLE);
			}
			holder.txtdatetime.setText(O_date + "(Order Id : " + O_id + ")");
			Holder h1 = sublist.get(paramInt);
			String product_name=h1.getOrder_product_name();
			holder.txtname.setText(product_name);

			Bitmap bitmap=h1.getBitmap();
			if(h1.getImage().equalsIgnoreCase(""))
			{
				holder.img.setBackgroundResource(R.mipmap.launchicon);
			}
			else
			{
				holder.img.setImageBitmap(bitmap);
			}
			String status=h.getOrder_status();
			holder.txtstatus.setText("Status : "+status);
			if(O_setORDER_statusdate.length()==0)
			{
				holder.txtdeliverydate.setText(status.substring(status.indexOf(" "),status.length())+" on : "+O_setORDER_statusdate);

			}
			else
			{
				holder.txtdeliverydate.setText(status.substring(status.indexOf(" "),status.length())+" on : "+O_setORDER_statusdate.substring(0,O_setORDER_statusdate.indexOf(" ")));

			}



			holder.btndetails.setTag(paramInt);
			holder.btndetails.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int pos=(Integer) v.getTag();
					Holder h1=(Holder)list.get(pos);
					Order_ID=h1.getOrder_id();
//					Fragment fragment = new OrderDetails();
//					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//					fragmentTransaction.replace(R.id.content_frame, fragment);
//					fragmentTransaction.addToBackStack(null);
//					fragmentTransaction.commit();

//					OrderDetails fragment = new OrderDetails();
//					FragmentManager fm = getActivity().getSupportFragmentManager();
//					FragmentTransaction ft = fm.beginTransaction();
//					ft.add(R.id.content_frame, fragment);
//					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//					ft.addToBackStack("add"+MainActivity.add);
//					ft.commit();
//					MainActivity.add++;
//					fragment = new OrderDetails();
//					ft = getActivity().getSupportFragmentManager().beginTransaction();
//					ft.replace(R.id.frame,fragment);
//					ft.commit();
					fragment = new OrderDetails();
					ft = getActivity().getSupportFragmentManager().beginTransaction();
//
					ft.add(R.id.frame,fragment);
					ft.addToBackStack("add" + MainActivity.add);
					ft.commit();
					MainActivity.add++;
				}
			});





			return paramView;
		}
	}


}
