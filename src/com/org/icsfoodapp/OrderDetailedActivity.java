package com.org.icsfoodapp;

import java.util.List;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import com.org.icsfoodapp.model.RestaurantResponse;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.android.msp.demo.Util;
import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.http.RequestFactory;
import com.fax.utils.task.GsonAsyncTask;
import com.fax.utils.view.TopBarContain;
import com.fax.utils.view.list.ObjectXAdapter;
import com.fax.utils.view.list.ObjectXListView;

import com.org.icsfoodapp.model.OrderDetailResponse;
import com.org.icsfoodapp.model.OrderDetailResponse.OrderDetailData;
import com.org.icsfoodapp.model.OrderDetailResponse.OrderDetailData.OrderCode;
import com.org.icsfoodapp.model.OrderListResponse.OrderListData;

public class OrderDetailedActivity extends Activity {

	View view;
	String orderNumber;
	OrderListData rData;
	OrderDetailData resultData;
	public static void start(Activity activity,OrderListData data){
		activity.startActivity(new Intent().setClass(activity, OrderDetailedActivity.class)
			.putExtra(OrderListData.class.getName(), data));
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rData = (OrderListData) getIntent().getSerializableExtra(OrderListData.class.getName());
		orderNumber = rData.getTrade_no();
		view = getLayoutInflater().inflate(R.layout.order_detailed_layout, null, false);
		TopBarContain topBarContain = new TopBarContain(this)
			.setTitle("", R.drawable.topbar_ic_logo, 0)
			.setLeftBtn(R.drawable.topbar_ic_back,  new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					OrderDetailedActivity.this.finish();
				}
			}).setContentView(view);
		setContentView(topBarContain);
		BitmapManager.init(this);
		if(rData.getUse()!=3){
			view.findViewById(R.id.order_detailed_paybut).setVisibility(View.GONE);
			view.findViewById(R.id.order_detailed_codelist_title).setVisibility(View.VISIBLE);
			view.findViewById(R.id.order_detailed_codelist).setVisibility(View.VISIBLE);
		}
		HttpRequestBase requestBase = RequestFactory.createPost(MyApp.ApiUrl+"Code/codeList", 
				new BasicNameValuePair("trade_no", orderNumber),
				new BasicNameValuePair("lang",MyApp.getLang()));
		new GsonAsyncTask<OrderDetailResponse>(this, requestBase) {
			@Override
			protected void onPostExecuteSuc(final OrderDetailResponse result) {
				
				if(result.isOk()){
					resultData = result.getData();
					BitmapManager.bindView((ImageView)(view.findViewById(R.id.order_detailed_image)), resultData.getImage());
					((TextView)(view.findViewById(R.id.order_detailed_name))).setText(resultData.getName());
					((TextView)(view.findViewById(R.id.order_detailed_number))).setText(resultData.getNum());
					((TextView)(view.findViewById(R.id.order_detailed_total))).setText(resultData.getTotal());
					((TextView)(view.findViewById(R.id.order_detailed_order_no))).setText(resultData.getTrade_no());
					((TextView)(view.findViewById(R.id.order_detailed_order_time))).setText(resultData.getOrder_time());
					
					if(rData.getUse()!=3){
						
						if(result.getData().getCode()!=null){
							if(result.getData().getCode().size()>0){
								((ObjectXListView)view.findViewById(R.id.order_detailed_codelist)).setAdapter(new ObjectXAdapter.SingleLocalPageAdapter<OrderCode>() {
									@Override
									public View bindView(OrderCode data, int position, View convertView) {
										View v = getLayoutInflater().inflate(R.layout.order_detailed_code_item, null, false);
										((TextView)(v.findViewById(R.id.order_code_label))).setText(getString(R.string.code)+(position+1));
										((TextView)(v.findViewById(R.id.order_code_no_item))).setText(data.getCode());
										((TextView)(v.findViewById(R.id.order_code_status))).setText(data.getStatus().equals("1")?"USED":"");
										return v;
									}
			
									@Override
									public List<OrderCode> instanceNewList()
											throws Exception {
										return result.getData().getCode();
									}
								});
							}
						}
					}
				}else{
					Toast.makeText(OrderDetailedActivity.this.getApplicationContext(), result.getMsg(),
							Toast.LENGTH_LONG).show();
					finish();
				}
			}
		}.setProgressDialog().execute();
	}
	public void payOrderClick(View v){
		if(resultData!=null){
			RestaurantResponse.RestaurantInfo.Activity data = new RestaurantResponse.RestaurantInfo.Activity();
			data.setTuijian_image(resultData.getImage());
			data.setName(resultData.getName());
			data.setId(resultData.getRid());
			data.setActivity_cost(resultData.getPrice());
			data.setNum(resultData.getNum());
			data.setTrade_no(resultData.getTrade_no());
			data.setMax_number(resultData.getNum());
			OrderActivity.start(this, data, 0, false);
		}
	}

}
