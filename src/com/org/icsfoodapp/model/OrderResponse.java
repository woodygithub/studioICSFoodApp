package com.org.icsfoodapp.model;

import android.R.integer;

public class OrderResponse extends Response {
	
	OrderResponseData data;
	
	public OrderResponseData getData() {
		return data;
	}

	public static class OrderResponseData{
		String id;
		String uid;
        String rid;
        String package_id;
        String trade_no;
        int type;
        String order_title;
        String order_time;
        String price;
        int num;
        String total;
        int pay_status;
        String pay_time;
        String buyer_emali;
        String remarks;
        String name;
        String name_en;
        String remark;
        String remark_en;
        String pay_trade_no;
        String trade_status;
		public String getId() {
			return id;
		}
		public String getPay_time() {
			return pay_time;
		}
		public String getBuyer_emali() {
			return buyer_emali;
		}
		public String getRemarks() {
			return remarks;
		}
		public String getName() {
			return name;
		}
		public String getName_en() {
			return name_en;
		}
		public String getRemark() {
			return remark;
		}
		public String getRemark_en() {
			return remark_en;
		}
		public String getPay_trade_no() {
			return pay_trade_no;
		}
		public String getTrade_status() {
			return trade_status;
		}
		public String getUid() {
			return uid;
		}
		public String getRid() {
			return rid;
		}
		public String getPackage_id() {
			return package_id;
		}
		public String getTrade_no() {
			return trade_no;
		}
		public int getType() {
			return type;
		}
		public String getOrder_title() {
			return order_title;
		}
		public String getOrder_time() {
			return order_time;
		}
		public String getPrice() {
			return price;
		}
		public int getNum() {
			return num;
		}
		public String getTotal() {
			return total;
		}
		public int getPay_status() {
			return pay_status;
		}
	}
}
