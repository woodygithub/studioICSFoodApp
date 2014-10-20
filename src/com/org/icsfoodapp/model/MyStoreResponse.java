package com.org.icsfoodapp.model;

import java.util.ArrayList;

import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity;

public class MyStoreResponse extends Response {
	ArrayList<MyStoreData> data;
	public ArrayList<MyStoreData> getData() {
		return data;
	}
	public static class MyStoreData{
		String uid;
		ArrayList<Activity> restaurant;
		ArrayList<Activity> dishes;
		ArrayList<Activity> activity;
		public String getUid() {
			return uid;
		}
		public ArrayList<Activity> getRestaurant() {
			return restaurant;
		}
		public ArrayList<Activity> getDishes() {
			return dishes;
		}
		public ArrayList<Activity> getActivity() {
			return activity;
		}
	}
}
