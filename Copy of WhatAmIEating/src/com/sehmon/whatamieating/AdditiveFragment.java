package com.sehmon.whatamieating;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AdditiveFragment extends Fragment {

	NutrientProvider provider;
	public ArrayList<Additive> additives;
	AdditiveListAdapter adapter;
	ListView lv1;
	
	public static Fragment newInstance() {
		return new AdditiveFragment();
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_additive_list, null);
		adapter = new AdditiveListAdapter(getActivity(), R.id.list_item, (ArrayList<Additive>) provider.getAdditives());
		lv1 = (ListView)rootView.findViewById(R.id.listView1);
		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(getActivity(), DetailActivity.class);
				i.putExtra("name", adapter.getItem(arg2).getName());
				startActivity(i);	
			}	
		
		});
		
		return rootView;
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		provider = (NutrientProvider)activity;
		
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Log.e("test", ""+provider.getAdditives());	
		adapter = new AdditiveListAdapter(getActivity(), R.id.list_item,  (ArrayList<Additive>) provider.getAdditives());
		lv1.setAdapter(adapter);
		

		
	}
}
