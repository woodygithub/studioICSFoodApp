package com.org.icsfoodapp;

import com.fax.utils.view.TopBarContain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrderPaySuccessActivity extends Activity {
	View view;
	String orderNumber;
	public static void start(Activity activity,String orderNumber){
		activity.startActivity(new Intent().setClass(activity, OrderPaySuccessActivity.class)
			.putExtra(String.class.getName(), orderNumber));
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = getLayoutInflater().inflate(R.layout.orderpay_success_layout, null, false);
		
		TopBarContain topBarContain = new TopBarContain(this)
			.setTitle("", R.drawable.topbar_ic_logo, 0)
			.setLeftBtn(R.drawable.topbar_ic_back,  new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					OrderPaySuccessActivity.this.finish();
				}
			}).setContentView(view);
		setContentView(topBarContain);
		init();
	}
	private void init() {
		orderNumber = getIntent().getStringExtra(String.class.getName());
		((TextView)findViewById(R.id.order_succ_number)).setText(orderNumber);
	}
	public void myOrderClick(View v){
		OrderTabsPagerFragmentActivity.start(this);
		finish();
	}

}
