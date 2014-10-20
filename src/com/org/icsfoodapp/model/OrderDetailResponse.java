package com.org.icsfoodapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderDetailResponse extends Response {

	OrderDetailData data;
	public OrderDetailData getData() {
		return data;
	}
	public static class OrderDetailData implements Serializable{
		
		ArrayList<OrderCode> code;
		String rid;
		String name;
		String remark;
		String image;
		String trade_no;
		String order_time;
		String pay_time;
		String price;
		String total;
		String num;
		public ArrayList<OrderCode> getCode() {
			return code;
		}
		public String getRid() {
			return rid;
		}
		public String getName() {
			return name;
		}
		public String getRemark() {
			return remark;
		}
		public String getImage() {
			return image;
		}
		public String getTrade_no() {
			return trade_no;
		}
		public String getOrder_time() {
			return order_time;
		}
		public String getPay_time() {
			return pay_time;
		}
		public String getPrice() {
			return price;
		}
		public String getTotal() {
			return total;
		}
		public String getNum() {
			return num;
		}
		public static class OrderCode{
			String type;
            String package_id;
            String code;
            String status;
			public String getType() {
				return type;
			}
			public String getPackage_id() {
				return package_id;
			}
			public String getCode() {
				return code;
			}
			public String getStatus() {
				return status;
			}
		}
	}
}
