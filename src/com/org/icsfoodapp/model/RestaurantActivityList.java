package com.org.icsfoodapp.model;

import java.util.ArrayList;

import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity;

public class RestaurantActivityList extends Response {

	ArrayList<Activity> data;

	public ArrayList<Activity> getData() {
		return data;
	}

	public void setData(ArrayList<Activity> data) {
		this.data = data;
	}
}
