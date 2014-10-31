package com.org.icsfoodapp.model;

import java.util.List;

public class RestaurantMenu extends Response {

	RestaurantMenuInner data;
	
	public RestaurantMenuInner getData() {
		return data;
	}
	public void setData(RestaurantMenuInner data) {
		this.data = data;
	}
	public static class RestaurantMenuInner{
		List<RestaurantResponse.RestaurantInfo.Activity> push;
		List<RestaurantResponse.RestaurantInfo.Activity> subpush;
		List<RestaurantResponse.RestaurantInfo.Activity> list;
		String total;
		public List<RestaurantResponse.RestaurantInfo.Activity> getPush() {
			return push;
		}
		public void setPush(List<RestaurantResponse.RestaurantInfo.Activity> push) {
			this.push = push;
		}
		public List<RestaurantResponse.RestaurantInfo.Activity> getSubpush() {
			return subpush;
		}
		public void setSubpush(List<RestaurantResponse.RestaurantInfo.Activity> subpush) {
			this.subpush = subpush;
		}
		public List<RestaurantResponse.RestaurantInfo.Activity> getList() {
			return list;
		}
		public void setList(List<RestaurantResponse.RestaurantInfo.Activity> list) {
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
