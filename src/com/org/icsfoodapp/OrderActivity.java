package com.org.icsfoodapp;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.android.msp.demo.Util;
import com.alipay.android.msp.model.Result;
import com.fax.utils.Arith;
import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.http.RequestFactory;
import com.fax.utils.task.GsonAsyncTask;
import com.fax.utils.view.TopBarContain;
import com.org.icsfoodapp.model.AfterPayResponse;
import com.org.icsfoodapp.model.OrderResponse;
import com.org.icsfoodapp.model.Response;

public class OrderActivity extends Activity {

	public static final int ACT_TYP = 1;
	public static final int MENU_TYP = 2;
	View view;
	Spinner mSpinner;
	com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity r;
	boolean isNew;
	
	public static void start(Activity activity,
			com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity data, int typ) {
		start(activity, data, typ, true);
	}
	
	public static void start(Activity activity,
			com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity data, int typ, boolean isNew) {
		activity.startActivity(new Intent().setClass(activity, OrderActivity.class)
			.putExtra(com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity.class.getName(), data)
			.putExtra(Integer.class.getName(), typ).putExtra(Boolean.class.getName(), isNew));
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		r = (com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity)getIntent()
			.getSerializableExtra(com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity.class
			.getName());
		isNew = getIntent().getBooleanExtra(Boolean.class.getName(), true);
		view = getLayoutInflater().inflate(R.layout.order_layout, null, false);
		
		TopBarContain topBarContain = new TopBarContain(this)
			.setTitle("", R.drawable.topbar_ic_logo, 0)
			.setLeftBtn(R.drawable.topbar_ic_back,  new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					OrderActivity.this.finish();
				}
			}).setContentView(view);
		setContentView(topBarContain);
		BitmapManager.init(this);
		init();
		
	}
	private void init() {
		BitmapManager.bindView(findViewById(R.id.order_bigimage), r.getTuijian_image());
		((TextView)findViewById(R.id.order_name)).setText(r.getName());
		((TextView)findViewById(R.id.order_price)).setText(r.getActivity_cost());
		mSpinner = (Spinner) findViewById(R.id.order_number);
		ArrayAdapter<String> tAdapter = new ArrayAdapter<String>(this,
				R.layout.num_select_item,
				getNumItems(Integer.parseInt(r.getMax_number())));
		tAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		mSpinner.setAdapter(tAdapter);
		if(isNew){
			mSpinner.setSelection(tAdapter.getPosition("1")<0?0:tAdapter.getPosition("1"));
		}else {
			mSpinner.setSelection(tAdapter.getPosition(r.getMax_number())<0?0:tAdapter.getPosition("1"));
			mSpinner.setEnabled(false);
			mSpinner.setClickable(false);
		}
		mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				double num = Double.parseDouble(((TextView) view.findViewById(android.R.id.text1)).getText().toString());
				double cost = Double.parseDouble(r.getActivity_cost());
				((TextView) OrderActivity.this.findViewById(R.id.order_total)).setText(String.valueOf(Arith.round(Arith.mul(num, cost), 2)));
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				((TextView) OrderActivity.this.findViewById(R.id.order_total)).setText("0");
			}
		});
	}
	
	private String[] getNumItems(int len) {
		String[] numArr = new String[len+1];
		numArr[0]="0";
		for (int i = 1; i < numArr.length; i++) {
			numArr[i] = String.valueOf(i);
		}
		return numArr;
	}
	public void payClick(View view){
		if(isNew){
			newPay();
		}else {
			rePay();
		}
		
	}
	private void newPay() {
		int typ = getIntent().getIntExtra(Integer.class.getName(), 0);
		if (typ==0){
			return;
		}
		String num = (String)mSpinner.getSelectedItem();
		if (Integer.parseInt(num) < 1) {
			Toast.makeText(OrderActivity.this, "请选择数量!", Toast.LENGTH_LONG).show();
			return;
		}
		
		HttpRequestBase requestBase = RequestFactory.createPost(MyApp.ApiUrl+"Orders/insertOrders", 
			new BasicNameValuePair("type", String.valueOf(typ)),
			new BasicNameValuePair("package_id", r.getId()),
			new BasicNameValuePair("price", r.getActivity_cost()),
			new BasicNameValuePair("num", num)
			);
		new GsonAsyncTask<OrderResponse>(OrderActivity.this, requestBase) {
			@Override
			protected void onPostExecuteSuc(OrderResponse result) {
				if(!result.isOk()){
					Toast.makeText(OrderActivity.this, result.getMsg(), Toast.LENGTH_LONG).show();
				}else {
					try {
						Util.mHandler = new payHandler(result);
						Util.orderService(OrderActivity.this, result.getData().getOrder_title(), result.getData().getOrder_title(), result.getData().getTotal(), result.getData().getTrade_no());
					} catch (Exception e) {
					}
				}
			}
		}.setProgressDialog().execute();
	}
	private void rePay() {
		HttpRequestBase requestBase = RequestFactory.createPost(MyApp.ApiUrl+"Orders/buyAgain", 
			new BasicNameValuePair("trade_no", r.getTrade_no()));
		new GsonAsyncTask<OrderResponse>(this, requestBase) {
			@Override
			protected void onPostExecuteSuc(OrderResponse result) {
				if(!result.isOk()){
					Toast.makeText(getApplicationContext(), result.getMsg(), Toast.LENGTH_LONG).show();
				}else {
					try {
						Util.mHandler = new payHandler(result);
						Util.orderService(OrderActivity.this, result.getData().getOrder_title(), result.getData().getOrder_title(), result.getData().getTotal(), result.getData().getTrade_no());
					} catch (Exception e) {
					}
				}
			}
		}.setProgressDialog().execute();
	}
	@SuppressLint("HandlerLeak")
	private class payHandler extends Handler {
		OrderResponse orderResult;
			public payHandler(OrderResponse result) {
			super();
			this.orderResult = result;
		}

		public payHandler(Callback callback) {
			super(callback);
		}

		public payHandler(Looper looper, Callback callback) {
			super(looper, callback);
		}

		public payHandler(Looper looper) {
			super(looper);
		}

			public void handleMessage(android.os.Message msg) {
				String json = (String) msg.obj;
				Result result = new Result(json);
				String message = "";
				switch (msg.what) {
				case Util.RQF_PAY:{
					if(!result.isSignOk()){
						Toast.makeText(OrderActivity.this.getApplicationContext(),message, Toast.LENGTH_LONG).show();
						return;
					}
					String resultStatusString = result.getContent(result.getResultStatus(),"(",")");
					message = Result.getSresultstatus().get(resultStatusString);
					if ("9000".equals(resultStatusString) && "true".equals(result.getContent(result.getResult(), "&success=\"", "\"&"))) {
						HttpRequestBase requestBase = RequestFactory.createPost(MyApp.ApiUrl+"Orders/changeStatus", 
								new BasicNameValuePair("out_trade_no", orderResult.getData().getTrade_no())
								);
						new GsonAsyncTask<AfterPayResponse>(OrderActivity.this, requestBase) {
							@Override
							protected void onPostExecuteSuc(AfterPayResponse result) {
								if(result.isOk()){
									OrderPaySuccessActivity.start(OrderActivity.this, orderResult.getData().getTrade_no());
									finish();
								}
							}
						}.setProgressDialog().execute();
						
					}
					Toast.makeText(OrderActivity.this.getApplicationContext(),message, Toast.LENGTH_LONG).show();
				}
					break;
				default:
					break;
				}
			};
		};
}
