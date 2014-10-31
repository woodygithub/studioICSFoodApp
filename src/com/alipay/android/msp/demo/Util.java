package com.alipay.android.msp.demo;

import android.app.Activity;
import com.alipay.android.msp.model.Result;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.android.app.sdk.AliPay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static final int RQF_PAY = 1;

	public static final int RQF_LOGIN = 2;
	private static Activity activity;
	
	
	public static void orderService(Activity act, String subject, String body, String total, String tradeNo) throws Exception{
		activity = act;
		String info = getNewOrderInfo(subject, body, total, tradeNo);
		String sign = Rsa.sign(info, Keys.PRIVATE);
		sign = URLEncoder.encode(sign,"UTF-8");
		info += "&sign=\"" + sign + "\"&" + getSignType();
		//info += "&sign=\"" + Keys.DEFAULT_MD5 + "\"&" + "sign_type=\"MD5\"";
		final String orderInfo = info;
		new Thread() {
			public void run() {
				AliPay alipay = new AliPay(activity, mHandler);
				
				//设置为沙箱模式，不设置默认为线上环境
				//alipay.setSandBox(true);

				String result = alipay.pay(orderInfo);
				Message msg = new Message();
				msg.what = RQF_PAY;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		}.start();
	}
	
	public static String getNewOrderInfo(String subject, String body, String total, String tradeNo) {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Keys.DEFAULT_PARTNER);
		sb.append("\"&out_trade_no=\"");
		sb.append(tradeNo);
		sb.append("\"&subject=\"");
		sb.append(subject);
		sb.append("\"&body=\"");
		sb.append(body);
		sb.append("\"&total_fee=\"");
		sb.append(total);
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
		sb.append(URLEncoder.encode("http://fireteam.ideer.cn/alipay/notify_url.php"));
		//sb.append("\"&biz_type=\"trust_login");
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		//sb.append("\"&return_url=\"");
		//sb.append(URLEncoder.encode("")); //http://m.alipay.com
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(Keys.DEFAULT_SELLER);

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"&it_b_pay=\"2h");
		sb.append("\"");

		return new String(sb);
	}
	private static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		java.util.Random r = new java.util.Random();
		key += r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
	
	public static void doLogin(final Activity activity) {
		Util.activity = activity;
		final String orderInfo = getUserInfo();
		new Thread() {
			public void run() {
				String result = new AliPay(activity, mHandler).pay(orderInfo);

				Message msg = new Message();
				msg.what = RQF_LOGIN;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		}.start();
	}
	
	private static String getUserInfo() {
		return getUserInfo("");
	}
	private static String getUserInfo(String userid) {
		return trustLogin(Keys.DEFAULT_PARTNER, userid);
	}

	private static String trustLogin(String partnerId, String appUserId) {
		StringBuilder sb = new StringBuilder();
		sb.append("app_name=\"mc\"&biz_type=\"trust_login\"&partner=\"");
		sb.append(partnerId);
		Log.d("TAG", "UserID = " + appUserId);
		if (!TextUtils.isEmpty(appUserId)) {
			appUserId = appUserId.replace("\"", "");
			sb.append("\"&app_id=\"");
			sb.append(appUserId);
		}
		sb.append("\"");

		String info = sb.toString();

		// 请求信息签名
		String sign = Rsa.sign(info, Keys.PRIVATE);
		try {
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		info += "&sign=\"" + sign + "\"&" + getSignType();

		return info;
	}
	private static String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
	public static Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			String json = (String) msg.obj;
			Result result = new Result(json);
			String message = "";
			switch (msg.what) {
			case RQF_PAY:{
				if(!result.isSignOk()){
					message = "秘钥验证错误!";
					Toast.makeText(activity.getApplicationContext(),message, Toast.LENGTH_LONG).show();
					return;
				}
				message = result.getSresultstatus().get(result.getResultStatus());
				if ("9000".equals(result.getResultStatus())) {
				}
				Toast.makeText(activity.getApplicationContext(),message, Toast.LENGTH_LONG).show();
			}
				break;
			case RQF_LOGIN: {
				Toast.makeText(activity.getApplicationContext(), result.getResult(), Toast.LENGTH_LONG).show();
			}
				break;
			default:
				break;
			}
		};
	};
}
