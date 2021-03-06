package com.sehmon.rottentomatoesdemo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RottenTomatoesClient {
	private final String API_KEY = "tbd74bfrgjwt423rgyzmdrvh";
	private final String API_BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0/";
	private final String MOVIES_REQUESTED = "30";
	private AsyncHttpClient client;
	
	public RottenTomatoesClient(){
		this.client = new AsyncHttpClient();
	}
	
	private String getApiUrl(String relativeUrl){
		return API_BASE_URL + relativeUrl;
	}
	
	public void getBoxOfficeMovies(JsonHttpResponseHandler handler){
		String url = getApiUrl("lists/movies/box_office.json");
		RequestParams params = new RequestParams("apikey", API_KEY);
		params.put("limit", MOVIES_REQUESTED);
		client.get(url, params, handler);
	}
}
