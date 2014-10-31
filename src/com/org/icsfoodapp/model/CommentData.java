package com.org.icsfoodapp.model;

import java.util.List;

public class CommentData extends Response {

	List<RestaurantResponse.RestaurantInfo.Commet> data;

	public List<RestaurantResponse.RestaurantInfo.Commet> getData() {
		return data;
	}

	public void setData(List<RestaurantResponse.RestaurantInfo.Commet> data) {
		this.data = data;
	}
}
