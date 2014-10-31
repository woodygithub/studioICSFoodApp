package com.org.icsfoodapp.model;

public class RestaurantAct extends Response {

	RestaurantResponse.RestaurantInfo.Activity data;

	public RestaurantResponse.RestaurantInfo.Activity getData() {
		return data;
	}

	public void setData(RestaurantResponse.RestaurantInfo.Activity data) {
		this.data = data;
	}
}
