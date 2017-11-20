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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class OrderDetails extends Fragment {
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	ListView orderdetailslist;
	JSONArray delivery = null;
	JSONArray orders = null;
	JSONArray orderitems = null;
	RelativeLayout idorder;
	ArrayList<Holder> arraylist = new ArrayList<Holder>();
	ArrayList<Holder> orderarraylist = new ArrayList<Holder>();
//	String OD_itemid,OD_productname,OD_productprice,OD_totalproductprice,OD_productquantity,OD_productimage;
    String orderon,items,email,orderid,name,status,deliveredon,quantity,price,address,state,paymenttype,OD_productimage;
	TextView txtorderon,txtitems,txttotal,txtemail,txtphone,txtaddress,txtstate,txtpaymenttype;
	Bitmap myBitmap,bitmap;
	int length,itemlength,LoginId;
	View view;
	RelativeLayout idorderdetails;
	Double grandtotal=0.0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.order_details, container, false);

		//img=(ImageView)view.findViewById(R.id.banner);
		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putBoolean("Signinpage", false);
		editor.putString("page", "Order");
		editor.commit();
		idorder=(RelativeLayout)view.findViewById(R.id.idorder);
		idorder.setVisibility(View.GONE);
		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		LoginId =sharedpreferences.getInt("LoginId", 0);
		//		cartlist=(ListView)view.findViewById(R.id.cartlist);
		orderdetailslist=(ListView)view.findViewById(R.id.orderdetailslist);

		txtorderon = (TextView) view.findViewById(R.id.txtorderon);
		txtitems = (TextView) view.findViewById(R.id.txtitems);
		txttotal = (TextView) view.findViewById(R.id.txttotal);
		txtemail = (TextView) view.findViewById(R.id.txtemail);
		txtphone = (TextView) view.findViewById(R.id.txtphone);

		txtaddress = (TextView) view.findViewById(R.id.txtaddress);
		txtstate = (TextView) view.findViewById(R.id.txtstate);
		txtpaymenttype = (TextView) view.findViewById(R.id.txtpaymenttype);


		new OrderDetailsProgressBar().execute();
		return view;
	}
	public class OrderDetailsProgressBar extends AsyncTask<String, String, String>
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
			//http://www.puyangan.com/api/orders.php?user_id=29&order_id=6013
			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/orders.php?user_id="+LoginId+"&order_id="+Order.Order_ID+"&device_id="+Splash.device_id);
			try {
				//http://www.puyangan.com/api/category.php?cid=178
				// Getting Array of Employee
				orders = json.getJSONArray("orders");
				length= orders.length();
				// looping of List
				for (int i = 0; i < length; i++) {
					JSONObject c = orders.getJSONObject(i);
					//					Bitmap myBitmap = null;


					InputStream input = null;
					orderon = c.getString("cdate");
					items = c.getString("totalitems");
					email = c.getString("user_email");
                    status = c.getString("order_status");
					deliveredon = c.getString("orderstatusdate");
					address = c.getString("address");
					state = c.getString("state");
					paymenttype = c.getString("payment_method");



					Holder h = new Holder();

					h.setO_orderon(orderon);
					h.setO_items(items);

					h.setO_email(email);


					h.setO_state(status);
					h.setO_deliveredon(deliveredon);

					h.setO_address(address);
					h.setO_state(state);
					h.setO_paymenttype(paymenttype);

					arraylist.add(h);

				}

				orderitems = json.getJSONArray("orderitems");
				itemlength= orderitems.length();
				// looping of List
				for (int i = 0; i < itemlength; i++) {
					JSONObject c = orderitems.getJSONObject(i);
					InputStream input = null;

					orderid = c.getString("order_id");
					name = c.getString("product_name");
					quantity = c.getString("product_quantity");
					price = c.getString("product_final_price");
					OD_productimage = c.getString("image");

					Holder h1 = new Holder();
					if(OD_productimage.equalsIgnoreCase(""))
					{

					}
					else
					{

						URL url = new URL(OD_productimage);
						HttpURLConnection connection = (HttpURLConnection) url.openConnection();
						connection.setDoInput(true);
						connection.setInstanceFollowRedirects(false);
						connection.setRequestMethod("GET");
						connection.connect();
						input = connection.getInputStream();

						BitmapFactory.Options opts = new BitmapFactory.Options();
						// opts.inJustDecodeBounds = true;
						opts.inSampleSize=4;
						myBitmap = BitmapFactory.decodeStream(input,null, opts);
						//						Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 200, 200, true);
						//myBitmap.recycle();

						//						Bitmap myBitmap = BitmapFactory.decodeStream(input);
						h1.setOD_bitmap(myBitmap);
						connection.disconnect();
					}

					h1.setO_orderid(orderid);
					h1.setO_name(name);
					h1.setO_quantity(quantity);
					h1.setO_price(price);
					h1.setOD_imageurl(OD_productimage);
					orderarraylist.add(h1);

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
			orderdetailslist.setAdapter(new MyCustomAdapter(getActivity(), orderarraylist));
			ListUtils.setDynamicHeight(orderdetailslist);
			txtorderon.setText(orderon.substring(0, orderon.indexOf(" ")));
			txtitems.setText(items);
			txttotal.setText("$"+grandtotal);
			txtemail.setText(email);

			txtaddress.setText(address);
			txtstate.setText(state);
			txtpaymenttype.setText(paymenttype);
			idorder.setVisibility(View.VISIBLE);

		}

	}
	public static class ListUtils {
		public static void setDynamicHeight(ListView orderlist) {
			ListAdapter mListAdapter = orderlist.getAdapter();
			if (mListAdapter == null) {
				// when adapter is null
				return;
			}
			int height = 50;
			int desiredWidth = MeasureSpec.makeMeasureSpec(orderlist.getWidth(), MeasureSpec.UNSPECIFIED);
			for (int i = 0; i < mListAdapter.getCount(); i++) {
				View listItem = mListAdapter.getView(i, null, orderlist);
				listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
				height += listItem.getMeasuredHeight();
			}
			ViewGroup.LayoutParams params = orderlist.getLayoutParams();
			params.height = height+ (orderlist.getHeight() * (mListAdapter.getCount() - 1));
			orderlist.setLayoutParams(params);
			orderlist.requestLayout();
		}
	}
	class MyCustomAdapter extends BaseAdapter {

		LayoutInflater inflater;
		ArrayList<Holder> list;
		public MyCustomAdapter(FragmentActivity fragmentActivity, ArrayList<Holder> list) {
			inflater = LayoutInflater.from(fragmentActivity);
			this.list =list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int paramInt) {
			return paramInt;
		}

		class ViewHolder{
//			TextView orderitem_id,product_name,total_price,price,quantity;
			ImageView productImage;

			TextView txtorderid,txtname,txtstatus,txtdeliveredon,txtquantity,txtprice,txt1,txt2,txt3;
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
				paramView = inflater.inflate(R.layout.order_details_listitems, paramViewGroup, false);
				holder= new ViewHolder();


				holder.txtorderid = (TextView) paramView.findViewById(R.id.txtorderid);
				holder.txtname = (TextView) paramView.findViewById(R.id.txtname);
				holder.txtstatus = (TextView) paramView.findViewById(R.id.txtstatus);
				holder.txtdeliveredon = (TextView) paramView.findViewById(R.id.txtdeliveron);
				holder.txtquantity = (TextView) paramView.findViewById(R.id.txtquantity);
				holder.txtprice = (TextView) paramView.findViewById(R.id.txtprice);
				holder.txt1 = (TextView) paramView.findViewById(R.id.textView101);
				holder.txt2 = (TextView) paramView.findViewById(R.id.textView131);
				holder.txt3 = (TextView) paramView.findViewById(R.id.textView151);



				holder.productImage = (ImageView)paramView.findViewById(R.id.img);

				paramView.setTag(holder);
			}
			else{
				holder = (ViewHolder) paramView.getTag();
			}

			final Holder h = list.get(paramInt);
			//String orderon,items,total,email,orderid,name,status,deliveredon,quantity,price,address,state,paymenttype;


			orderid = h.getO_orderid();
			name = h.getO_name();
//			status = h.getO_status();
//			deliveredon = h.getO_deliveredon();
			quantity = h.getO_quantity();
			price = h.getO_price();


			holder.txtorderid.setText("Order Id : "+orderid);
			holder.txtname.setText(name);
			holder.txtstatus.setText(status.substring(status.indexOf(" "),status.length()));
			holder.txtdeliveredon.setText(deliveredon.substring(0,deliveredon.indexOf(" ")));
			holder.txtquantity.setText(quantity);
			holder.txtprice.setText("$"+price);

			grandtotal=grandtotal+Double.parseDouble(price);

			holder.txt1.setText("DELIVERED ON");
			holder.txt2.setText("QUANTITY");
			holder.txt3.setText("PRICE");


			bitmap=h.getOD_bitmap();

			if(h.getOD_imageurl().equalsIgnoreCase(""))
			{
				holder.productImage.setBackgroundResource(R.drawable.header);
			}
			else
			{
				//				holder.productImage.setImageBitmap(getCircleBitmap(bitmap));
				holder.productImage.setImageBitmap(bitmap);
			}




			return paramView;
		}
	}
}
