package com.sehmon.simpleweather;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class WeatherGrabber {
	private final String API_KEY = "0399e17a7d2d55fc";
	private final String API_BASE = "http://api.wunderground.com/api/";
	
	private AsyncHttpClient client;
	
	public WeatherGrabber(){
		this.client = new AsyncHttpClient();
	}
	
	public String getUrl(String zipCode){
		return API_BASE + API_KEY + "conditions/lang:EN/q/" + zipCode + ".json";
		
	}
	
	public void getWeather(JsonHttpResponseHandler handler){
		//TODO set url to get the text from the zipcode box
		String url = getUrl("07036");
		RequestParams params = new RequestParams("apikey", API_KEY);
		client.get(url, params, handler);
	}
	

}
