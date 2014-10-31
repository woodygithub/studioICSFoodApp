package com.org.icsfoodapp.model;

import java.util.ArrayList;

public class MyStoreResponse extends Response {
	ArrayList<MyStoreData> data;
	public ArrayList<MyStoreData> getData() {
		return data;
	}
	public static class MyStoreData{
		String uid;
		ArrayList<RestaurantResponse.RestaurantInfo.Activity> restaurant;
		ArrayList<RestaurantResponse.RestaurantInfo.Activity> dishes;
		ArrayList<RestaurantResponse.RestaurantInfo.Activity> activity;
		public String getUid() {
			return uid;
		}
		public ArrayList<RestaurantResponse.RestaurantInfo.Activity> getRestaurant() {
			return restaurant;
		}
		public ArrayList<RestaurantResponse.RestaurantInfo.Activity> getDishes() {
			return dishes;
		}
		public ArrayList<RestaurantResponse.RestaurantInfo.Activity> getActivity() {
			return activity;
		}
	}
}
