package com.org.icsfoodapp.model;

import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity;

public class RestaurantMenuDetailed extends Response {

	Activity data;

	public Activity getData() {
		return data;
	}

	public void setData(Activity data) {
		this.data = data;
	}
	
}
