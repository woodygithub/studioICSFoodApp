package com.org.icsfoodapp.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.R.integer;

import com.google.gson.annotations.SerializedName;

public class User extends Response implements Serializable{
	ArrayList<Data> data;
	public ArrayList<Data> getData() {
		return data;
	}
	public void setData(ArrayList<Data> data) {
		this.data = data;
	}
	public int getLang(){
		return data.get(0).lang;
	}
	public void setLang(int lang){
		data.get(0).lang = lang;
	}
	public String getNickName(){
		return data.get(0).nickname;
	}
	public String getHeadImageUrl(){
		return data.get(0).head_image;
	}
	public String getCount1(){
		return (data.get(0).star.count1);
	}
	public String getCount2(){
		return (data.get(0).star.count2);
	}
	public String getCount3(){
		return (data.get(0).star.count3);
	}
	public String addCount1(){
		return (data.get(0).star.addCount1());
	}
	public String subCount1(){
		return (data.get(0).star.subCount1());
	}
	public String addCount2(){
		return (data.get(0).star.addCount2());
	}
	public String subCount2(){
		return (data.get(0).star.subCount2());
	}
	public String addCount3(){
		return (data.get(0).star.addCount3());
	}
	public String subCount3(){
		return (data.get(0).star.subCount3());
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
