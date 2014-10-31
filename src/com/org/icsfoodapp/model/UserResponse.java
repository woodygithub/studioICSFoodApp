package com.org.icsfoodapp.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class UserResponse extends Response implements Serializable{
	Data data;
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	public static class Data implements Serializable{
		String nickname;
		String head_image;
		int lang;
		Star star;
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
		public int getLang() {
			return lang;
		}
		public void setLang(int lang) {
			this.lang = lang;
		}
		public Star getStar() {
			return star;
		}
		public void setStar(Star star) {
			this.star = star;
		}
		public static class Star implements Serializable{
			@SerializedName(value = "1")
			String count1;
			@SerializedName(value = "2")
			String count2;
			@SerializedName(value = "3")
			String count3;
			public String getCount1() {
				return count1;
			}
			public void setCount1(String count1) {
				this.count1 = count1;
			}
			public String getCount2() {
				return count2;
			}
			public void setCount2(String count2) {
				this.count2 = count2;
			}
			public String getCount3() {
				return count3;
			}
			public void setCount3(String count3) {
				this.count3 = count3;
			}
			
			public String addCount1() {
				return this.count1 = String.valueOf(Integer.valueOf(this.count1)+1);
			}
			public String subCount1() {
				int count = Integer.valueOf(this.count1);
				return this.count1 = String.valueOf(count==0?0:count-1);
			}
			
			public String addCount2() {
				return this.count2 = String.valueOf(Integer.valueOf(this.count2)+1);
			}
			public String subCount2() {
				int count = Integer.valueOf(this.count2);
				return this.count2 = String.valueOf(count==0?0:count-1);
			}
			
			public String addCount3() {
				return this.count3 = String.valueOf(Integer.valueOf(this.count3)+1);
			}
			public String subCount3() {
				int count = Integer.valueOf(this.count3);
				return this.count3 = String.valueOf(count==0?0:count-1);
			}
		}
	}
}
