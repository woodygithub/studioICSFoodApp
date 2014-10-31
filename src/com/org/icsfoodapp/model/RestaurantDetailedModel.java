package com.org.icsfoodapp.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class RestaurantDetailedModel extends Response {

	RestaurantDetailedData data;
	
	public RestaurantDetailedData getData() {
		return data;
	}

	public void setData(RestaurantDetailedData data) {
		this.data = data;
	}

	public static class RestaurantDetailedData {
		String id;
		String remark;
		String name;
		String business_hours;
		String address;
		String phone;
		String spend;
		String location_x;
		String location_y;
		ArrayList<String> subway;
		String environment;
		String building;
		String atmosphere;
		String unformatted_remarks;
		String parking_info;
		String parking_address;
		String parking_money;
		String landmark;
		String business_circle;
		String favourable;
		ArrayList<String> style;
		ArrayList<Video> video;
		Service service;
		String roomcount;
		String maximumcount;

		public String getEnvironment() {
			return environment;
		}

		public void setEnvironment(String environment) {
			this.environment = environment;
		}

		public String getBuilding() {
			return building;
		}

		public void setBuilding(String building) {
			this.building = building;
		}

		public String getAtmosphere() {
			return atmosphere;
		}

		public void setAtmosphere(String atmosphere) {
			this.atmosphere = atmosphere;
		}

		public String getUnformatted_remarks() {
			return unformatted_remarks;
		}

		public void setUnformatted_remarks(String unformatted_remarks) {
			this.unformatted_remarks = unformatted_remarks;
		}

		public String getParking_info() {
			return parking_info;
		}

		public void setParking_info(String parking_info) {
			this.parking_info = parking_info;
		}

		public String getParking_address() {
			return parking_address;
		}

		public void setParking_address(String parking_address) {
			this.parking_address = parking_address;
		}

		public String getParking_money() {
			return parking_money;
		}

		public void setParking_money(String parking_money) {
			this.parking_money = parking_money;
		}

		public String getLandmark() {
			return landmark;
		}

		public void setLandmark(String landmark) {
			this.landmark = landmark;
		}

		public String getBusiness_circle() {
			return business_circle;
		}

		public void setBusiness_circle(String business_circle) {
			this.business_circle = business_circle;
		}

		public String getFavourable() {
			return favourable;
		}

		public void setFavourable(String favourable) {
			this.favourable = favourable;
		}

		public ArrayList<String> getSubway() {
			return subway;
		}

		public void setSubway(ArrayList<String> subway) {
			this.subway = subway;
		}

		public String getLocation_x() {
			return location_x;
		}

		public void setLocation_x(String location_x) {
			this.location_x = location_x;
		}

		public String getLocation_y() {
			return location_y;
		}

		public void setLocation_y(String location_y) {
			this.location_y = location_y;
		}

		public String getMaximumcount() {
			return maximumcount;
		}

		public void setMaximumcount(String maximumcount) {
			this.maximumcount = maximumcount;
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
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getSpend() {
			return spend;
		}
		public void setSpend(String spend) {
			this.spend = spend;
		}
		public ArrayList<String> getStyle() {
			return style;
		}
		public void setStyle(ArrayList<String> style) {
			this.style = style;
		}
		public ArrayList<Video> getVideo() {
			return video;
		}
		public void setVideo(ArrayList<Video> video) {
			this.video = video;
		}
		public Service getService() {
			return service;
		}
		public void setService(Service service) {
			this.service = service;
		}
		public String getRoomcount() {
			return roomcount;
		}
		public void setRoomcount(String roomcount) {
			this.roomcount = roomcount;
		}

		public static class Style {
			String name;
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
		}
		
		public static class Video {
			String name;
			String video;
			String image;
			
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getVideo() {
				return video;
			}
			public void setVideo(String video) {
				this.video = video;
			}
			public String getImage() {
				return image;
			}
			public void setImage(String image) {
				this.image = image;
			}
		}
		
		public static class Service {
			int room;
			int Wifi;
			@SerializedName(value = "Credit Card")
			int CreditCard;
			int Takeout;
			@SerializedName(value = "Waiting Area")
			int WaitingArea;
			@SerializedName(value = "Baby Chair")
			int BabyChair;
			int Accessibility;
			@SerializedName(value = "Maximum number of Private Room")
			int MaximumNumberOfPrivateRoom;
			@SerializedName(value = "dai bo")
			int daibo;
			public int getDaibo() {
				return daibo;
			}
			public void setDaibo(int daibo) {
				this.daibo = daibo;
			}
			public int getRoom() {
				return room;
			}
			public void setRoom(int room) {
				this.room = room;
			}
			public int getWifi() {
				return Wifi;
			}
			public void setWifi(int wifi) {
				Wifi = wifi;
			}
			public int getCreditCard() {
				return CreditCard;
			}
			public void setCreditCard(int creditCard) {
				CreditCard = creditCard;
			}
			public int getTakeout() {
				return Takeout;
			}
			public void setTakeout(int takeout) {
				Takeout = takeout;
			}
			public int getWaitingArea() {
				return WaitingArea;
			}
			public void setWaitingArea(int waitingArea) {
				WaitingArea = waitingArea;
			}
			public int getBabyChair() {
				return BabyChair;
			}
			public void setBabyChair(int babyChair) {
				BabyChair = babyChair;
			}
			public int getAccessibility() {
				return Accessibility;
			}
			public void setAccessibility(int accessibility) {
				Accessibility = accessibility;
			}
			public int getMaximumNumberOfPrivateRoom() {
				return MaximumNumberOfPrivateRoom;
			}
			public void setMaximumNumberOfPrivateRoom(int maximumNumberOfPrivateRoom) {
				MaximumNumberOfPrivateRoom = maximumNumberOfPrivateRoom;
			}
		}
	}
}
