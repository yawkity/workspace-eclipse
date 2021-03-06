package com.sehmon.rottentomatoesdemo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class BoxOfficeActivity extends Activity {
	protected static final String MOVIE_DETAIL_KEY = "movie";
	private ListView lvMovies;
	private BoxOfficeMovieAdapter adapterMovies;
	RottenTomatoesClient client;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_box_office);
		lvMovies = (ListView)findViewById(R.id.lvMovies);
		ArrayList<BoxOfficeMovie> aMovies = new ArrayList<BoxOfficeMovie>();
		adapterMovies = new BoxOfficeMovieAdapter(this, aMovies);
		lvMovies.setAdapter(adapterMovies);
		
		lvMovies.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(arg2));
				startActivity(i);
			}
		});
		
		fetchBoxOfficeMovies();

	}

	private void fetchBoxOfficeMovies() {
		client = new RottenTomatoesClient();
		client.getBoxOfficeMovies(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int code, JSONObject body){
				JSONArray items = null;
				try{
					items = body.getJSONArray("movies");
					
					ArrayList<BoxOfficeMovie> movies = BoxOfficeMovie.fromJson(items);
					for(BoxOfficeMovie movie:movies){
						adapterMovies.add(movie);
					}
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.box_office, menu);
		return true;
	}



}
