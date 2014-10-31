package com.org.icsfoodapp.model;

import java.util.ArrayList;

public class RestaurantActivityList extends Response {

	ArrayList<RestaurantResponse.RestaurantInfo.Activity> data;

	public ArrayList<RestaurantResponse.RestaurantInfo.Activity> getData() {
		return data;
	}

	public void setData(ArrayList<RestaurantResponse.RestaurantInfo.Activity> data) {
		this.data = data;
	}
}
