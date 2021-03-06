package com.sehmon.whatamieating;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


public class Food {

	private String upc;
	private String brand;
	private ArrayList<Nutrient> nutrients;
	private ArrayList<Additive> additives;

	private int score;
	
	public ArrayList<Nutrient> getNutrients() {
		return nutrients;
	}

	public ArrayList<Additive> getAdditives() {
		return additives;
	}

	public int getScore() {
		return score;
	}


	public String getUpc() {
		return upc;
	}

	public String getBrand() {
		return brand;
	}
	
	public static Food fromJson(JSONObject jsonObject){
		
		Food f = new Food();
		try{
			
			f.brand = jsonObject.getString("brand");
			f.upc = jsonObject.getString("upc");
			f.score = Integer.parseInt(jsonObject.getString("productscore"));
			
			//Loop through items in array
			JSONArray jsonNutrients = jsonObject.getJSONArray("nutrients");
			for(int i = 0; i < jsonNutrients.length(); i++){
				Nutrient n = new Nutrient();
			}
						
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return f;
	}
}
