package com.org.icsfoodapp.model;

import java.util.List;

public class Star extends Response {
	List<RestaurantResponse.RestaurantInfo> data;

	public List<RestaurantResponse.RestaurantInfo> getData() {
		return data;
	}

	public void setData(List<RestaurantResponse.RestaurantInfo> data) {
		this.data = data;
	}
}
