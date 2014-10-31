package com.org.icsfoodapp;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.shapes.ArcShape;
import com.org.icsfoodapp.model.RestaurantDetailedModel;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviPara;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.fax.utils.task.GsonAsyncTask;
import com.fax.utils.view.CardTextView;
import com.fax.utils.view.TopBarContain;

import com.org.icsfoodapp.model.RestaurantResponse;

public class RestaurantDetailedActivity extends Activity {
	
	RestaurantResponse.RestaurantInfo data;
	MapView mMapView;
	GeoCoder mSearch;
	LocationClient mLocClient;
	private SDKReceiver mReceiver;
	
	public static void start(Activity activity,RestaurantResponse.RestaurantInfo data){
		start(activity, data, 1);
	}
	public static void start(Activity activity,RestaurantResponse.RestaurantInfo data, int page){
		activity.startActivity(
				new Intent(activity, RestaurantDetailedActivity.class)
				.putExtra(RestaurantResponse.RestaurantInfo.class.getName(), data));
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		data = (RestaurantResponse.RestaurantInfo)getIntent()
				.getSerializableExtra(RestaurantResponse.RestaurantInfo.class.getName());
		String url = MyApp.ApiUrl + "Restaurant/more/id/1?id=" + data.getId()+"&lang="+MyApp.getLang();
		TopBarContain topBarContain = new TopBarContain(this)
			.setTitle("", R.drawable.topbar_ic_logo, 0)
			.setLeftFinish(null, R.drawable.topbar_ic_back)
			.setContentView(R.layout.restaurant_detailed_layout);
		setContentView(topBarContain);
		new GsonAsyncTask<RestaurantDetailedModel>(this, url){
			@Override
			protected void onPostExecuteSuc(RestaurantDetailedModel result) {
				init(result);
			}
		}.setProgressDialog().execute();
	}
	
	@Override
	protected void onPause() {
		if (mMapView != null) {
			mMapView.onPause();
		}
		
		
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (mMapView != null) {
			mMapView.onResume();
		}
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		if (mMapView != null) {
			mMapView.onDestroy();
		}
		// 取消监听 SDK 广播
		unregisterReceiver(mReceiver);
		mLocClient.stop();
		super.onDestroy();
	}
	private void init(RestaurantDetailedModel result){
		RestaurantDetailedModel.RestaurantDetailedData data = result.getData();
		// 注册 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);
		
		//定义Maker坐标点  
		final LatLng point = new LatLng( Double.parseDouble(data.getLocation_x()),
				Double.parseDouble(data.getLocation_y())); 
		mMapView = new MapView(this,new BaiduMapOptions()
			.overlookingGesturesEnabled(false)
			.scrollGesturesEnabled(false)
			.zoomControlsEnabled(false)
			.rotateGesturesEnabled(false));
		((FrameLayout)findViewById(R.id.map)).addView(mMapView);
		final BaiduMap mBaiduMap = mMapView.getMap();
		mBaiduMap.setMyLocationEnabled(true);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		//option.setCoorType("bd09ll"); // 设置坐标类型
		option.setCoorType("gcj02");
		option.setScanSpan(1000);
		// 定位初始化
		mLocClient = new LocationClient(getApplicationContext(), option);
		mLocClient.registerLocationListener(new BDLocationListener() {
			
			@Override
			public void onReceivePoi(BDLocation location) {
			}
			@Override
			public void onReceiveLocation(BDLocation location) {
				Log.e("onReceiveLocation", "onReceiveLocation run");
				if (location == null || mMapView == null){
					return;
				}
			}
		});
		mLocClient.start();
		mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}
			@Override
			public void onMapClick(LatLng arg0) {
				// 构建 导航参数
				final NaviPara para = new NaviPara();
				BDLocation location = mLocClient.getLastKnownLocation();
				if(location != null) {
					para.startPoint = new LatLng(location.getLatitude(),
							location.getLongitude());
					para.endPoint = point;
					BaiduMapNavigation.openWebBaiduMapNavi(para,
							RestaurantDetailedActivity.this);
				}
			}
		});
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					Toast.makeText(RestaurantDetailedActivity.this, "抱歉，未能找到结果",
							Toast.LENGTH_LONG).show();
					return;
				}
				mBaiduMap.clear();
				mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.icon_gcoding)));
				mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
						.getLocation()));
				Toast.makeText(RestaurantDetailedActivity.this, result.getAddress(),
						Toast.LENGTH_LONG).show();
				mSearch.destroy();
			}
			
			@Override
			public void onGetGeoCodeResult(GeoCodeResult result) {
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					Toast.makeText(RestaurantDetailedActivity.this, "抱歉，未能找到结果",
							Toast.LENGTH_LONG).show();
					return;
				}
				mBaiduMap.clear();
				mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding)));
				mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
						.getLocation()));
				String strInfo = String.format("纬度：%f 经度：%f",
						result.getLocation().latitude, result.getLocation().longitude);
				Toast.makeText(RestaurantDetailedActivity.this, strInfo, Toast.LENGTH_LONG)
					.show();
				mSearch.destroy();
			}
		});
		// 反Geo搜索
		mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(point));
		
		LinearLayout subways = (LinearLayout)findViewById(R.id.restaurant_detailed_subways);
		ImageView subwayIcon = new ImageView(this);
		subwayIcon.setImageResource(R.drawable.subway_icon);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 30, 0);
		lp.gravity = Gravity.CENTER_VERTICAL;
		subwayIcon.setLayoutParams(lp);
		subways.addView(subwayIcon);
		ArrayList<String> subays = data.getSubway();
		for (int i = 0; i < subays.size(); i++) {
			subways.addView(createSubwayView(subays.get(i)));
		}
		Drawable arrow = getResources().getDrawable(R.drawable.arrow);
		arrow.setBounds(0, 0, arrow.getMinimumWidth(), arrow.getMinimumHeight());
		((CardTextView)findViewById(R.id.restaurant_detailed_preferential))
			.setTitle(getString(R.string.preferential_information))
			.setSubText(data.getFavourable()).openView()
			.setTitleCompoundDrawables(null, null, arrow, null).setTitleCompoundDrawablePadding(30);
		((CardTextView)findViewById(R.id.restaurant_detailed_environment))
			.setTitle(getString(R.string.environment))
			.setSubText(data.getEnvironment()).closeView()
			.setTitleCompoundDrawables(null, null, arrow, null).setTitleCompoundDrawablePadding(30);
		((CardTextView)findViewById(R.id.restaurant_detailed_architecture))
			.setTitle(getString(R.string.architecture))
			.setSubText(data.getBuilding()).closeView()
			.setTitleCompoundDrawables(null, null, arrow, null).setTitleCompoundDrawablePadding(30);
		((CardTextView)findViewById(R.id.restaurant_detailed_atmosphere))
			.setTitle(getString(R.string.atmosphere))
			.setSubText(data.getAtmosphere()).closeView()
			.setTitleCompoundDrawables(null, null, arrow, null).setTitleCompoundDrawablePadding(30);
		((TextView)findViewById(R.id.restaurant_detailed_roommaxpeople))
			.setText(data.getMaximumcount());
		((TextView)findViewById(R.id.restaurant_detailed_parkinginfo))
			.setText(data.getParking_info() + " " + data.getParking_address());
		((TextView)findViewById(R.id.restaurant_detailed_costs)).setText(data.getParking_money());
		((TextView)findViewById(R.id.restaurant_detailed_style)).setText(data.getStyle().get(0));
		((TextView)findViewById(R.id.restaurant_detailed_spend)).setText(data.getSpend());
		((TextView)findViewById(R.id.restaurant_detailed_address)).setText(data.getAddress());
		((TextView)findViewById(R.id.restaurant_detailed_business_circle)).setText(data.getBusiness_circle());
		((TextView)findViewById(R.id.restaurant_detailed_landmark)).setText(data.getLandmark());
//		((TextView)findViewById(R.id.restaurant_detailed_phone)).setText(data.getPhone());
//		((TextView)findViewById(R.id.restaurant_detailed_business_hours))
//			.setText(data.getBusiness_hours());
		((TextView)findViewById(R.id.restaurant_detailed_room)).setText(data.getRoomcount());
		((RadioButton)findViewById(R.id.restaurant_detailed_wifi))
			.setChecked(data.getService().getWifi()==1);
		((RadioButton)findViewById(R.id.restaurant_detailed_creditcard))
			.setChecked(data.getService().getCreditCard()==1);
		((RadioButton)findViewById(R.id.restaurant_detailed_takeout))
			.setChecked(data.getService().getTakeout()==1);
		((RadioButton)findViewById(R.id.restaurant_detailed_waitingarea))
			.setChecked(data.getService().getWaitingArea()==1);
		((RadioButton)findViewById(R.id.restaurant_detailed_accessibility))
			.setChecked(data.getService().getAccessibility()==1);
		((RadioButton)findViewById(R.id.restaurant_detailed_babychair))
			.setChecked(data.getService().getBabyChair()==1);
		((RadioButton)findViewById(R.id.restaurant_detailed_parkinglot))
			.setChecked(data.getService().getDaibo()==1);
		((TextView)findViewById(R.id.restaurant_detailed_remark)).setText(data.getRemark());
	}
	
	@SuppressLint({ "NewApi", "ResourceAsColor" })
	private TextView createSubwayView(CharSequence text) {
		TextView rb= new TextView(this);
		rb.setHeight(50);
		rb.setWidth(50);
		rb.setGravity(Gravity.CENTER);
		rb.setTextSize(12);
		rb.setText(text);
		PaintDrawable paintD = new PaintDrawable();
		paintD.getPaint().setColor(Color.WHITE);
		paintD.setShape(new ArcShape(0, 360));
		paintD.draw(new Canvas());
		rb.setBackground(paintD);
		rb.setTextColor(getResources().getColor(R.color.blue));
		return rb;
	}
	
	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */
	public class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				Toast.makeText(RestaurantDetailedActivity.this,
						"key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置",
						Toast.LENGTH_LONG).show();
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				Toast.makeText(RestaurantDetailedActivity.this,"网络出错",Toast.LENGTH_LONG)
					.show();
			}
		}
	}

	
}
