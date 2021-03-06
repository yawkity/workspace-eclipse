package com.sehmon.whatamieating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class SingleFoodActivity extends Activity implements
		ActionBar.TabListener, NutrientProvider {
	
	Food f;
	ArrayList<Nutrient> nutrients;
	ArrayList<Additive> additives;
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_food);
		getFoodInfo();
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		mViewPager.setCurrentItem(1);
		actionBar.setTitle("Frito-Lays Doritos");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_food, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			Fragment f = new Fragment();
			f = ScoreFragment.newInstance();
			switch(position){
			case 0:
				f = NutrientFragment.newInstance();
				break;
			case 1:
				f = ScoreFragment.newInstance();
				break;
			case 2:
				f = AdditiveFragment.newInstance();
			} 
			
			return f;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	
	public void getFoodInfo(){
		AsyncTask<String, String, String> async = new AsyncTask<String, String, String>() {

			@Override
			protected String doInBackground(String... arg0) {
				Intent i = getIntent();
				String s = i.getStringExtra("upcCode");
				HttpClient client = new DefaultHttpClient();
				HttpGet getReq = new HttpGet("http://api.foodessentials.com/productscore?u=" + s + "&sid=b318d6d9-3858-432e-81bf-fe037cc313ae&f=json&api_key=m5pkqfejmtvxsw3en5rnjagu");
			  //HttpGet getReq = new HttpGet("http://api.foodessentials.com/productscore?u=028400091510&sid=b318d6d9-3858-432e-81bf-fe037cc313ae&f=json&api_key=m5pkqfejmtvxsw3en5rnjagu");
				String responseString = "";
				try {
					HttpResponse resp = client.execute(getReq);
					BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
					// read the response
					String temp = "";
					while ((temp = reader.readLine()) != null) {
						responseString += temp;
					}
					Log.e("MALTZ", "response is " + responseString);
					return responseString;
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return responseString;
			}
			
			@Override
			protected void onPostExecute(String res) {
				try {
					JSONObject jsonObject = new JSONObject(res);
					f = Food.fromJson(jsonObject.getJSONObject("product"));	
					JSONArray jsonAdditives = jsonObject.getJSONObject("product").getJSONArray("additives");
					JSONArray jsonNutrients = jsonObject.getJSONObject("product").getJSONArray("nutrients");
					nutrients = new ArrayList<Nutrient>();
					additives = new ArrayList<Additive>();
					for(int i = 0; i < jsonNutrients.length(); i++){
						nutrients.add(Nutrient.fromJson(jsonNutrients.getJSONObject(i)));
					}
					for(int i = 0; i < jsonAdditives.length(); i++){
						additives.add(Additive.fromJson(jsonAdditives.getJSONObject(i)));
					}
					
					Log.i("yawk", String.valueOf(additives.size()));
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		async.execute("blah");
	}

	@Override
	public List<Nutrient> getNutrients() {
		// TODO Auto-generated method stub
		if (nutrients == null){
			return new ArrayList<Nutrient>();
		}
		return nutrients;
	}
	
	public List<Additive> getAdditives(){
		if(additives == null){
			return new ArrayList<Additive>();
		}
		return additives;
	}

	@Override
	public Food getFood() {
		if(f== null){
			return new Food();
		}
		return f;
	}

	@Override
	public String title() {
		// TODO Auto-generated method stub
		return null;
	}
	


}
