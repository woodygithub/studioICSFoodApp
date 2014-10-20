package com.org.icsfoodapp.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.org.icsfoodapp.model.RestaurantList.RestaurantInList;
import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity;

public class StoreInfo extends Response {

	List<StoreData> data;
	public List<StoreData> getData() {
		return data;
	}
	public void setData(List<StoreData> data) {
		this.data = data;
	}
	public static class StoreData{
        String id;
        String nickname;
        String head_image;
        Star star;
		List<RestaurantInList> restaurantStarList;
        List<Activity> DishesStarList;
        List<Activity> ActivityStarList;
        public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getHead_image() {
			return head_image;
		}
		public void setHead_image(String head_image) {
			this.head_image = head_image;
		}
		public Star getStar() {
			return star;
		}
		public void setStar(Star star) {
			this.star = star;
		}
		public List<RestaurantInList> getRestaurantStarList() {
			return restaurantStarList;
		}
		public void setRestaurantStarList(List<RestaurantInList> restaurantStarList) {
			this.restaurantStarList = restaurantStarList;
		}
		public List<Activity> getDishesStarList() {
			return DishesStarList;
		}
		public void setDishesStarList(List<Activity> dishesStarList) {
			DishesStarList = dishesStarList;
		}
		public List<Activity> getActivityStarList() {
			return ActivityStarList;
		}
		public void setActivityStarList(List<Activity> activityStarList) {
			ActivityStarList = activityStarList;
		}
		public static class Star {
            @SerializedName(value = "1")
            String one;
            @SerializedName(value = "2")
            String two;
            @SerializedName(value = "3")
            String three;
			public String getOne() {
				return one;
			}
			public void setOne(String one) {
				this.one = one;
			}
			public String getTwo() {
				return two;
			}
			public void setTwo(String two) {
				this.two = two;
			}
			public String getThree() {
				return three;
			}
			public void setThree(String three) {
				this.three = three;
			}
        }
        
    }
}
