package co.ramraj;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Categories extends Fragment implements OnClickListener {
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	HashMap<String, List<String>> listDataChildid;
	JSONArray responce = null;
	JSONArray cartinfo = null;
	JSONArray productslist = null;
	JSONArray subresponce = null;
	String  sub;
	String Id,Name,Image,Regular_price,Sale_price,Price;
	ArrayList<Holder> list = new ArrayList<Holder>();
	public static String subcatid;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	public static String str_categories;
	public static String str_subcategories;
	public static String str_subcategoriesid;
	int  LoginId;
	Button btnvieworder,btnall,btnrecent,btnfeature,btncategory;
	public static TextView txtstore,txtstoreclick;
	public static String store="0";
	Fragment fragment;
	FragmentTransaction ft;
	RelativeLayout idcategory;
	String lengt;
	int l=0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.categories, container, false);
		expListView = (ExpandableListView)view.findViewById(R.id.expandableListView1);
		txtstore=(TextView)view.findViewById(R.id.txt11);
		txtstoreclick=(TextView)view.findViewById(R.id.txtstoreclick);
		// preparing list data
		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putBoolean("Signinpage", false);
		editor.putString("page", "Categories");
		editor.commit();
		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		LoginId =sharedpreferences.getInt("LoginId", 0);
		btnvieworder=(Button)view.findViewById(R.id.btnvieworder);
		btnall=(Button)view.findViewById(R.id.btnall);
		btnrecent=(Button)view.findViewById(R.id.btnrecents);
		btnfeature=(Button)view.findViewById(R.id.btnfeature);
		btncategory=(Button)view.findViewById(R.id.btncategory);
		
		btncategory.setTextColor(getResources().getColor(R.color.btnactiveColor));
		btnrecent.setTextColor(Color.parseColor("#000000"));
		btnfeature.setTextColor(Color.parseColor("#000000"));
		btnall.setTextColor(Color.parseColor("#000000"));
	
		btnall.setOnClickListener(this);
		btnrecent.setOnClickListener(this);
		btnfeature.setOnClickListener(this);
		txtstore.setText(Categories.store);
//		MainActivity.navigationView.getMenu().getItem(3).setChecked(true);
		MainActivity.mSelectedItem=3;
		MainActivity.mMenuAdapter.notifyDataSetChanged();
//		Home.mDrawerList.setItemChecked(4, true);
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
				fragment = new Order();
				ft = getActivity().getSupportFragmentManager().beginTransaction();
//				ft.replace(R.id.frame,fragment);
				ft.add(R.id.frame,fragment);
				ft.addToBackStack("add" + MainActivity.add);
				ft.commit();
				MainActivity.add++;
//				MainActivity.navigationView.getMenu().getItem(2).setChecked(true);
				MainActivity.mSelectedItem=2;
				MainActivity.mMenuAdapter.notifyDataSetChanged();

			}
		});
		txtstoreclick.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				fragment = new Cart();
				ft = getActivity().getSupportFragmentManager().beginTransaction();
				ft.add(R.id.frame, fragment);
				ft.addToBackStack("add" + MainActivity.add);
				ft.commit();
				MainActivity.add++;
//				MainActivity.navigationView.getMenu().getItem(1).setChecked(true);
				MainActivity.mSelectedItem=1;
				MainActivity.mMenuAdapter.notifyDataSetChanged();

			}
		});
		new CategoryProgressBar().execute();

		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				//				Toast.makeText(getActivity(),
				//						"Group Clicked " + listDataHeader.get(groupPosition),
				//						Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				//				Toast.makeText(getActivity(),
				//						listDataHeader.get(groupPosition) + " Expanded",
				//						Toast.LENGTH_SHORT).show();
				str_categories=listDataHeader.get(groupPosition);
				str_categories=str_categories+"/";
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				//				Toast.makeText(getActivity(),
				//						listDataHeader.get(groupPosition) + " Collapsed",
				//						Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				//				Toast.makeText(
				//						getActivity(),listDataHeader.get(groupPosition)+ " : "+ listDataChild.get(listDataHeader.get(groupPosition)).get(
				//								childPosition), Toast.LENGTH_SHORT).show();

				str_subcategories=listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
				str_subcategoriesid=listDataChildid.get(listDataHeader.get(groupPosition)).get(childPosition);

				subcatid = str_subcategoriesid;
				str_subcategories=str_subcategories+"/";
				//				Product fragment = new Product();
				//				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				//				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				//				fragmentTransaction.replace(R.id.content_frame, fragment);
				//				fragmentTransaction.addToBackStack(null);
				//				fragmentTransaction.commit();

//				Product fragment = new Product();
//				FragmentManager fm = getActivity().getSupportFragmentManager();
//				FragmentTransaction ft = fm.beginTransaction();
//				ft.add(R.id.content_frame, fragment);
//				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//				ft.addToBackStack("add"+MainActivity.add);
//				ft.commit();
//				MainActivity.add++;
				fragment = new Product();
				ft = getActivity().getSupportFragmentManager().beginTransaction();
//				ft.replace(R.id.frame,fragment);
				ft.add(R.id.frame, fragment);
				ft.addToBackStack("add" + MainActivity.add);
				ft.commit();
				MainActivity.add++;

				return false;
			}
		});
		return view;
	}


	public class CategoryProgressBar extends AsyncTask<String, String, String>
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
			listDataHeader = new ArrayList<String>();
			listDataChild = new HashMap<String, List<String>>();
			listDataChildid = new HashMap<String, List<String>>();
			JSONParser jParser = new JSONParser();

			JSONObject[] json = new JSONObject[2];
			json[0] = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/category.php");
			json[1] = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/cart.php?user_id="+LoginId+"&device_id="+Splash.device_id);

			//		    return jsons;http://www.puyangan.com/api/cart.php?user_id=0


			// getting JSON string from URL//checkuserforgotpwd=1,user_login=emailid
			//			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/category.php");
			try {
				//http://www.puyangan.com/api/category.php
				// Getting Array of Employee
				responce = json[0].getJSONArray("categories");
				cartinfo = json[1].getJSONArray("cartinfo");
				int length= responce.length();
				int lengths=cartinfo.length();
				// looping of List
				for (int i = 0; i < length; i++) {
					JSONObject c = responce.getJSONObject(i);

					listDataHeader.add(c.getString("name"));

					sub=c.getString("subcat");
					List<String> WebDesigning = new ArrayList<String>();
					List<String> WebDesignings = new ArrayList<String>();
					if(sub.equalsIgnoreCase("[]"))
					{
						listDataChild.put(listDataHeader.get(i), WebDesigning);
						listDataChildid.put(listDataHeader.get(i), WebDesigning);
					}
					else
					{
						JSONObject jObj = new JSONObject(sub);
						subresponce = jObj.getJSONArray("subcategories");
						int len=subresponce.length();


						for (int j = 0; j < len; j++) {
							JSONObject cc = subresponce.getJSONObject(j);

							String name=cc.getString("name");
							String Subcatid=cc.getString("catid");
							String Subcount=cc.getString("count");
							Holder h = new Holder();
							h.setSubcatid(Subcatid);
							h.setSubcount(Subcount);
							h.setSale_price(Sale_price);
							WebDesigning.add(name);
							WebDesignings.add(Subcatid);
							list.add(h);
							

						}
						listDataChild.put(listDataHeader.get(i), WebDesigning);
						listDataChildid.put(listDataHeader.get(i), WebDesignings);
					}


				}
				for(int j=0;j<lengths;j++)
				{
					JSONObject cc = cartinfo.getJSONObject(j);

					store=cc.getString("totalitems");

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
			ExpandableListAdapter	listAdapter = new co.ramraj.ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

			// setting list adapter
			expListView.setAdapter(listAdapter);
			txtstore.setText(store);
		}

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btnrecents:
//			fragment = new Recents();
//			fm = getActivity().getSupportFragmentManager();
//			ft = fm.beginTransaction();
//			ft.add(R.id.content_frame, fragment);
//			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//			ft.addToBackStack("add"+MainActivity.add);
//			ft.commit();
//			MainActivity.add++;
			fragment = new Recents();
			ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.frame,fragment);
//			ft.add(R.id.frame,fragment);
//			ft.addToBackStack("add" + MainActivity.add);
			ft.commit();
//			MainActivity.add++;
//			MainActivity.navigationView.getMenu().getItem(4).setChecked(true);
			MainActivity.mSelectedItem=4;
			MainActivity.mMenuAdapter.notifyDataSetChanged();
			break;

		case R.id.btnfeature:
//			fragment = new Features();
//			fm = getActivity().getSupportFragmentManager();
//			ft = fm.beginTransaction();
//			ft.add(R.id.content_frame, fragment);
//			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//			ft.addToBackStack("add"+MainActivity.add);
//			ft.commit();
//			MainActivity.add++;
			 fragment = new Features();
			ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.frame,fragment);
//			ft.add(R.id.frame,fragment);
//			ft.addToBackStack("add" + MainActivity.add);
			ft.commit();
//			MainActivity.add++;
//			MainActivity.navigationView.getMenu().getItem(5).setChecked(true);
			MainActivity.mSelectedItem=5;
			MainActivity.mMenuAdapter.notifyDataSetChanged();
			break;
		case R.id.btnall:
//			fragment = new All();
//			fm = getActivity().getSupportFragmentManager();
//			ft = fm.beginTransaction();
//			ft.add(R.id.content_frame, fragment);
//			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//			ft.addToBackStack("add"+MainActivity.add);
//			ft.commit();
//			MainActivity.add++;
			 fragment = new All();
			ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.frame,fragment);
//			ft.add(R.id.frame, fragment);
//			ft.addToBackStack("add" + MainActivity.add);
			ft.commit();
//			MainActivity.add++;
//			MainActivity.navigationView.getMenu().getItem(6).setChecked(true);
			MainActivity.mSelectedItem=6;
			MainActivity.mMenuAdapter.notifyDataSetChanged();
			break;

		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
}
