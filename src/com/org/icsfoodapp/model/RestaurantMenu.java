package com.org.icsfoodapp.model;

import java.util.List;

import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity;

public class RestaurantMenu extends Response {

	RestaurantMenuInner data;
	
	public RestaurantMenuInner getData() {
		return data;
	}
	public void setData(RestaurantMenuInner data) {
		this.data = data;
	}
	public static class RestaurantMenuInner{
		List<Activity> push;
		List<Activity> subpush;
		List<Activity> list;
		String total;
		public List<Activity> getPush() {
			return push;
		}
		public void setPush(List<Activity> push) {
			this.push = push;
		}
		public List<Activity> getSubpush() {
			return subpush;
		}
		public void setSubpush(List<Activity> subpush) {
			this.subpush = subpush;
		}
		public List<Activity> getList() {
			return list;
		}
		public void setList(List<Activity> list) {
			this.list = list;
		}
		public String getTotal() {
			return total;
		}
		public void setTotal(String total) {
			this.total = total;
		}
	}
}
