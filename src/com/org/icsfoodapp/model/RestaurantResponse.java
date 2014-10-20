package com.org.icsfoodapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RestaurantResponse extends Response{
	RestaurantInfo data;

	public RestaurantInfo getData() {
		return data;
	}
	
	public static class RestaurantInfo implements Serializable  {
		String id;
		String remark;
		String name;
		String business_hours;
		String address;
		String list_image;
		String zhu_image;
		String confidential_image;
		ArrayList<TuijianImage> tuijian_image;
		ArrayList<String> style;
		int expert_score;
		int customer_score;
		ArrayList<HuanjingImage> huanjing_image;
		ArrayList<Dishes> dishes;
		ArrayList<RestaurantInfo.Activity> activity;
		ArrayList<Commet> commet;
		String totalCommet;
		int isStar;
		String nickname;
		
		public ArrayList<TuijianImage> getTuijian_image() {
			return tuijian_image;
		}

		public void setTuijian_image(ArrayList<TuijianImage> tuijian_image) {
			this.tuijian_image = tuijian_image;
		}

		public String getConfidential_image() {
			return confidential_image;
		}

		public void setConfidential_image(String confidential_image) {
			this.confidential_image = confidential_image;
		}

		public String getZhu_image() {
			return zhu_image;
		}

		public void setZhu_image(String zhu_image) {
			this.zhu_image = zhu_image;
		}

		public int getIsStar() {
			return isStar;
		}

		public void setIsStar(int isStar) {
			this.isStar = isStar;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getId() {
			return id;
		}
	
		public void setId(String id) {
			this.id = id;
		}
	
		public String getRemark() {
			return remark;
		}
	
		public void setRemark(String remark) {
			this.remark = remark;
		}
	
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
			this.name = name;
		}
	
		public String getBusiness_hours() {
			return business_hours;
		}
	
		public void setBusiness_hours(String business_hours) {
			this.business_hours = business_hours;
		}
	
		public String getAddress() {
			return address;
		}
	
		public void setAddress(String address) {
			this.address = address;
		}
	
		public String getList_image() {
			return list_image;
		}
	
		public void setList_image(String list_image) {
			this.list_image = list_image;
		}
	
		public ArrayList<String> getStyle() {
			return style;
		}
	
		public void setStyle(ArrayList<String> style) {
			this.style = style;
		}
	
		public int getExpert_score() {
			return expert_score;
		}
	
		public void setExpert_score(int expert_score) {
			this.expert_score = expert_score;
		}
	
		public int getCustomer_score() {
			return customer_score;
		}
	
		public void setCustomer_score(int customer_score) {
			this.customer_score = customer_score;
		}
	
		public ArrayList<HuanjingImage> getHuanjing_image() {
			return huanjing_image;
		}
	
		public void setHuanjing_image(ArrayList<HuanjingImage> huanjing_image) {
			this.huanjing_image = huanjing_image;
		}
	
		public ArrayList<Dishes> getDishes() {
			return dishes;
		}
	
		public void setDishes(ArrayList<Dishes> dishes) {
			this.dishes = dishes;
		}
	
		public ArrayList<RestaurantInfo.Activity> getActivity() {
			return activity;
		}
	
		public void setActivity(
				ArrayList<RestaurantInfo.Activity> activity) {
			this.activity = activity;
		}
	
		public ArrayList<Commet> getCommet() {
			return commet;
		}
	
		public void setCommet(ArrayList<Commet> commet) {
			this.commet = commet;
		}
	
		public String getTotalCommet() {
			return totalCommet;
		}
	
		public void setTotalCommet(String totalCommet) {
			this.totalCommet = totalCommet;
		}
	
		public boolean isStar() {
			return isStar == 1;
		}
	
		public void setStar(boolean isStar) {
			this.isStar = isStar ? 1:0;
		}
	
		public static class HuanjingImage implements ImageModelImp ,Serializable{
			String name;
			String image;
			String s_image;
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public void setImage(String image) {
				this.image = image;
			}
			public void setS_image(String s_image) {
				this.s_image = s_image;
			}
			@Override
			public String getSmailImage() {
				return image;
			}
			public String getImage() {
				return image;
			}
			@Override
			public String getId() {
				return null;
			}
			@Override
			public String getVideo() {
				return null;
			}
			
		}
		
		public static class Dishes implements ImageModelImp, Serializable{
			String id;
			String name;
			String image;
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getImage() {
				return image;
			}
			public void setImage(String image) {
				this.image = image;
			}
			@Override
			public String getSmailImage() {
				return image;
			}
			@Override
			public String getVideo() {
				return null;
			}
		}
		
		public static class Activity implements ImageModelImp, Serializable{
			String id;
			String name;
			String max_number;
			String activity_cost;
			String image;
			String tuijian_image;
			String rid;
			String remark;
			String activity_start;
			String activity_end;
			String big_image;
			List<Activity> small_image;
			int isStar;
			String total;
			String num;
			String trade_no;
			public void setTrade_no(String trade_no) {
				this.trade_no = trade_no;
			}
			public String getTrade_no() {
				return trade_no;
			}
			public String getNum() {
				return num;
			}
			public void setNum(String num) {
				this.num = num;
			}
			public void setMax_number(String max_number) {
				this.max_number = max_number;
			}
			public void setActivity_cost(String activity_cost) {
				this.activity_cost = activity_cost;
			}
			public void setTuijian_image(String tuijian_image) {
				this.tuijian_image = tuijian_image;
			}
			public String getMax_number() {
				return max_number;
			}
			public String getActivity_cost() {
				return activity_cost;
			}
			public String getTuijian_image() {
				return tuijian_image;
			}
			public String getTotal() {
				return total;
			}
			public void setTotal(String total) {
				this.total = total;
			}
			public boolean getIsStar() {
				return isStar==1;
			}
			public void setIsStar(boolean isStar) {
				this.isStar = isStar?1:0;
			}
			public String getBig_image() {
				return big_image;
			}
			public void setBig_image(String big_image) {
				this.big_image = big_image;
			}
			public List<Activity> getSmall_image() {
				return small_image;
			}
			public void setSmall_image(List<Activity> small_image) {
				this.small_image = small_image;
			}
			public String getRid() {
				return rid;
			}
			public void setRid(String rid) {
				this.rid = rid;
			}
			public String getRemark() {
				return remark;
			}
			public void setRemark(String remark) {
				this.remark = remark;
			}
			public String getActivity_start() {
				return activity_start;
			}
			public void setActivity_start(String activity_start) {
				this.activity_start = activity_start;
			}
			public String getActivity_end() {
				return activity_end;
			}
			public void setActivity_end(String activity_end) {
				this.activity_end = activity_end;
			}
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getImage() {
				return image;
			}
			public void setImage(String image) {
				this.image = image;
			}
			@Override
			public String getSmailImage() {
				return image;
			}
			@Override
			public String getVideo() {
				return null;
			}
		}
		
		public static class Commet implements CommentItemImp, Serializable{
			String uid;
			String nickname;
			String commet;
			int score;
			String head_image;
			public String getUid() {
				return uid;
			}
			public void setUid(String uid) {
				this.uid = uid;
			}
			public String getNickname() {
				return nickname;
			}
			public void setNickname(String nickname) {
				this.nickname = nickname;
			}
			public String getCommet() {
				return commet;
			}
			public void setCommet(String commet) {
				this.commet = commet;
			}
			public int getScore() {
				return score;
			}
			public void setScore(int score) {
				this.score = score;
			}
			public String getHead_image() {
				return head_image;
			}
			public void setHead_image(String head_image) {
				this.head_image = head_image;
			}
			@Override
			public String getImage() {
				return head_image;
			}
			@Override
			public String getName() {
				return nickname;
			}
			@Override
			public String getText() {
				return commet;
			}
		}
		
		public static class TuijianImage implements Serializable{
			String image;
			String did;
			public String getImage() {
				return image;
			}
			public void setImage(String image) {
				this.image = image;
			}
			public String getDid() {
				return did;
			}
			public void setDid(String did) {
				this.did = did;
			}
		}
		
	}

}
