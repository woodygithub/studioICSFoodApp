package com.org.icsfoodapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantList extends Response implements Serializable{
	ArrayList<RestaurantInList> data;
	
	public ArrayList<RestaurantInList> getData() {
		return data;
	}
	public static class RestaurantInList implements ImageModelImp,Serializable{
		String id;
		String rid;
		String name;
		String image;
		String style;
		int score;
		int customer_score;
		String count;
		public int getCustomer_score() {
			return customer_score;
		}
		public String getRid() {
			return rid;
		}

		public RestaurantInList(String id){
			this.id= id;
		}
		
		public String getId() {
			return id;
		}
		public String getName() {
			return name;
		}
		public String getImage() {
			return image;
		}
		public String getStyle() {
			return style;
		}
		public int getScore() {
			return score;
		}
		public String getCount() {
			return count;
		}

		@Override
		public String getSmailImage() {
			return image;
		}
		@Override
		public String getVideo() {
			return null;
		}
		
	}
}
