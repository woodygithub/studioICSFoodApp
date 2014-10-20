package com.org.icsfoodapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantImageGrid extends Response{
	
	RestaurantImageGradeData data;
	public RestaurantImageGradeData getData() {
		return data;
	}
	public static class RestaurantImageGradeData implements Serializable {
		ArrayList<HuanjingImage> huanjing_image;
		ArrayList<CaipingImage> caiping_image;
		ArrayList<VideoImage> video_image;
		public ArrayList<HuanjingImage> getHuanjing_image() {
			return huanjing_image;
		}
		public ArrayList<CaipingImage> getCaiping_image() {
			return caiping_image;
		}
		public ArrayList<VideoImage> getVideo_image() {
			return video_image;
		}
		public static class HuanjingImage implements ImageModelImp{
			String name;
		    String image;
		    String s_image;
			public String getName() {
				return name;
			}
			public String getImage() {
				return image;
			}
			public String getS_image() {
				return s_image;
			}
			@Override
			public String getId() {
				return null;
			}
			@Override
			public String getSmailImage() {
				return s_image;
			}
			@Override
			public String getVideo() {
				return null;
			}
		}
		public static class CaipingImage implements ImageModelImp{
			String name;
		    String image;
			public String getName() {
				return name;
			}
			public String getImage() {
				return image;
			}
			@Override
			public String getId() {
				return null;
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
		public static class VideoImage implements ImageModelImp{
			String name;
		    String image;
		    String video;
			public String getName() {
				return name;
			}
			public String getImage() {
				return image;
			}
			public String getVideo() {
				return video;
			}
			@Override
			public String getId() {
				return null;
			}
			@Override
			public String getSmailImage() {
				return image;
			}
		}
	}
}
