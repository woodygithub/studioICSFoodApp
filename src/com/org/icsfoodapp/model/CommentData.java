package com.org.icsfoodapp.model;

import java.util.List;

import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Commet;

public class CommentData extends Response {

	List<Commet> data;

	public List<Commet> getData() {
		return data;
	}

	public void setData(List<Commet> data) {
		this.data = data;
	}
}
