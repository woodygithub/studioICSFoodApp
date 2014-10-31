package com.org.icsfoodapp.model;

import java.util.List;

import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo;

public class StarDetailed extends Response {
	StarData data;
	public StarData getData() {
		return data;
	}
	public void setData(StarData data) {
		this.data = data;
	}
	public static class StarData extends RestaurantInfo{
		List<StarRestaurant> star;
		String big_image;
		public String getBig_image() {
			return big_image;
		}
		public void setBig_image(String big_image) {
			this.big_image = big_image;
		}
		public List<StarRestaurant> getStar() {
			return star;
		}
		public void setStar(List<StarRestaurant> star) {
			this.star = star;
		}
		public static class StarRestaurant{
			String name;
			String remark;
			String rid;
			String uid;
			String image;
			int score;
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getRemark() {
				return remark;
			}
			public void setRemark(String remark) {
				this.remark = remark;
			}
			public String getRid() {
				return rid;
			}
			public void setRid(String rid) {
				this.rid = rid;
			}
			public String getUid() {
				return uid;
			}
			public void setUid(String uid) {
				this.uid = uid;
			}
			public String getImage() {
				return image;
			}
			public void setImage(String image) {
				this.image = image;
			}
			public int getScore() {
				return score;
			}
			public void setScore(int score) {
				this.score = score;
			}
		}
	}
}
