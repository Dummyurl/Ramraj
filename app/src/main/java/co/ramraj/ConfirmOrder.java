package co.ramraj;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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


public class ConfirmOrder extends Fragment {
	ListView orderlist;
	JSONArray productcart = null;
	ArrayList<Holder> arraylist = new ArrayList<Holder>();
	String Product_id,Product_name,Product_price,Product_quantity,Product_image;
	Bitmap myBitmap,bitmap;
	TextView txtheader,txttotalprice;
	Button btnnext,btnback;
	int Order_Count;
	Double Total_Price=0.0;
	int length;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	int  LoginId;
	RelativeLayout idconfirmorder;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.confirmorder, container, false);
		orderlist=(ListView)view.findViewById(R.id.orderlist);
		btnnext=(Button)view.findViewById(R.id.btnnext);
		btnback=(Button)view.findViewById(R.id.btnback);
		txtheader=(TextView)view.findViewById(R.id.txtheader);
		txttotalprice=(TextView)view.findViewById(R.id.txttotalprice);
		idconfirmorder=(RelativeLayout)view.findViewById(R.id.idconfirmorder);
		idconfirmorder.setVisibility(View.GONE);

		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
		LoginId =sharedpreferences.getInt("LoginId", 0);
		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putBoolean("Signinpage",false);

		//		img=(ImageView)view.findViewById(R.id.banner);
		if(LoginId==0)
		{

			editor = sharedpreferences.edit();
			editor.putString("page","Order");
			
//			Home.mDrawerList.setItemChecked(1, true);


			Fragment fragment = new Signin();
			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.frame, fragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}
		else
		{
			new OrderProgressBar().execute();
		}
		editor.commit();

		btnnext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Fragment fragment = new CheckOut();
//				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//				fragmentTransaction.replace(R.id.content_frame, fragment);
//				fragmentTransaction.addToBackStack(null);
//				fragmentTransaction.commit();
				CheckOut fragment = new CheckOut();
				FragmentManager fm = getActivity().getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.add(R.id.frame, fragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.addToBackStack("add"+MainActivity.add);
				ft.commit();
				MainActivity.add++;

			}
		});
		btnback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager manager = getActivity().getSupportFragmentManager();
				manager.popBackStack();
			}
		});
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
			//http://www.puyangan.com/api/cart.php?user_id=67
			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/cart.php?user_id="+LoginId);
			try {
				//http://www.puyangan.com/api/category.php?cid=178
				// Getting Array of Employee
				productcart = json.getJSONArray("productcart");
				length= productcart.length();
				Order_Count=length;
				// looping of List
				for (int i = 0; i < length; i++) {
					JSONObject c = productcart.getJSONObject(i);
					//					Bitmap myBitmap = null;
					InputStream input = null;

					Product_id = c.getString("product_id");
					Product_name = c.getString("product_name");
					Product_price = c.getString("product_price");
					Product_quantity = c.getString("product_quantity");
					Product_image = c.getString("product_image");
					Holder h = new Holder();
					if(Product_image.equalsIgnoreCase(""))
					{

					}
					else
					{

						URL url = new URL(Product_image);
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
						h.setBitmap(myBitmap);
						connection.disconnect();
					}
					h.setProduct_id(Product_id);
					h.setProduct_name(Product_name); 
					h.setProduct_price(Product_price);
					h.setProduct_quantity(Product_quantity);
					h.setProduct_image(Product_image);

					arraylist.add(h);

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
			orderlist.setAdapter(new MyCustomAdapter(getActivity(),arraylist));
			ListUtils.setDynamicHeight(orderlist);
			txttotalprice.setText("$"+Total_Price);
			txtheader.setText("CONFIRM ORDER ("+length+")");
			idconfirmorder.setVisibility(View.VISIBLE);

		}

	}
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
			params.height = height + (orderlist.getHeight() * (mListAdapter.getCount() - 1));
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
			TextView name,price,quantity;
			ImageView productImage;
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
				paramView = inflater.inflate(R.layout.confirmorderitems, paramViewGroup, false);
				holder= new ViewHolder();

				holder.name = (TextView) paramView.findViewById(R.id.txtname);
				holder.price = (TextView) paramView.findViewById(R.id.txtprice);
				holder.quantity = (TextView) paramView.findViewById(R.id.txtquantity);
				holder.productImage = (ImageView)paramView.findViewById(R.id.img);

				paramView.setTag(holder);
			}
			else{
				holder = (ViewHolder) paramView.getTag();
			}

			Holder h = list.get(paramInt);
			String name = h.getProduct_name();
			holder.name.setText(name);
			String price=h.getProduct_price();
			holder.price.setText("$"+price);
			Total_Price=Total_Price+Double.parseDouble(price);
			String quantity=h.getProduct_quantity();
			holder.quantity.setText("Quantity: "+quantity);
			bitmap=h.getBitmap();

			if(h.getProduct_image().equalsIgnoreCase(""))
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
	private Bitmap getCircleBitmap(Bitmap bitmap) {
		final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(output);

		final int color = Color.WHITE;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawOval(rectF, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		//		 bitmap.recycle();

		return output;
	}
}
