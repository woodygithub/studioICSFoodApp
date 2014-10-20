package com.org.icsfoodapp.model;

import java.util.ArrayList;

import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity;

public class RestaurantAct extends Response {

	Activity data;

	public Activity getData() {
		return data;
	}

	public void setData(Activity data) {
		this.data = data;
	}
}
