package com.org.icsfoodapp.model;

import java.util.List;

import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo;

public class Star extends Response {
	List<RestaurantInfo> data;

	public List<RestaurantInfo> getData() {
		return data;
	}

	public void setData(List<RestaurantInfo> data) {
		this.data = data;
	}
}
