package com.org.icsfoodapp.model;

import java.io.Serializable;
import java.util.List;

public class OrderListResponse extends Response {

	List<OrderListData> data;
	public List<OrderListData> getData() {
		return data;
	}
	public static class OrderListData implements Serializable {
		String id;
		String uid;
		String rid;
		String trade_no;
		String total;
		String type;
		String pay_time;
		String buyer_email;
		String remarks;
		String package_id;
		String order_title;
		String num;
		String price;
		String name;
		String remark;
		String pay_status;
		String pay_trade_no;
		String trade_status;
		String order_time;
		String image;
		int Use;
		public String getId() {
			return id;
		}
		public String getUid() {
			return uid;
		}
		public String getRid() {
			return rid;
		}
		public String getTrade_no() {
			return trade_no;
		}
		public String getTotal() {
			return total;
		}
		public String getType() {
			return type;
		}
		public String getPay_time() {
			return pay_time;
		}
		public String getBuyer_email() {
			return buyer_email;
		}
		public String getRemarks() {
			return remarks;
		}
		public String getPackage_id() {
			return package_id;
		}
		public String getOrder_title() {
			return order_title;
		}
		public String getNum() {
			return num;
		}
		public String getPrice() {
			return price;
		}
		public String getName() {
			return name;
		}
		public String getRemark() {
			return remark;
		}
		public String getPay_status() {
			return pay_status;
		}
		public String getPay_trade_no() {
			return pay_trade_no;
		}
		public String getTrade_status() {
			return trade_status;
		}
		public String getOrder_time() {
			return order_time;
		}
		public String getImage() {
			return image;
		}
		public int getUse() {
			return Use;
		}
	}
}