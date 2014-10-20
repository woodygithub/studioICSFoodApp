package com.org.icsfoodapp;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.task.GsonAsyncTask;
import com.fax.utils.view.TopBarContain;
import com.org.icsfoodapp.model.RestaurantResponse;
import com.org.icsfoodapp.model.RestaurantList.RestaurantInList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RatingBar;
import android.widget.TextView;

public class BigImageActivity extends Activity {
	
	TopBarContain topBarContain;
	View view;
	RestaurantInList r;
	public static void start(Fragment fragment, RestaurantInList data) {
		fragment.startActivity(new Intent().setClass(fragment.getActivity(), BigImageActivity.class)
				.putExtra(RestaurantInList.class.getName(),data));
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		r = (RestaurantInList) getIntent().getSerializableExtra(RestaurantInList.class.getName());
		view =  getLayoutInflater().inflate(R.layout.big_imageactivity, null, false);
		
		topBarContain = new TopBarContain(this)
				.setTitle("", R.drawable.topbar_ic_logo, 0)
				.setLeftBtn(R.drawable.topbar_ic_back,  new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						BigImageActivity.this.finish();
					}
				}).setContentView(view);
		setContentView(topBarContain);
		new GsonAsyncTask<RestaurantResponse>(this, MyApp.ApiUrl + "Restaurant/info/id/1?id=" + r.getId() + "&lang=" + MyApp.getLang()) {
			@Override
			protected void onPostExecuteSuc(RestaurantResponse result) {
				init(result);
			}
		}.setProgressDialog().execute();
		
	}

	protected void init(final RestaurantResponse result) {
		RestaurantResponse.RestaurantInfo data = result.getData();
		((TextView) findViewById(R.id.restaurant_show_name)).setText(data.getName());
		((TextView) findViewById(R.id.restaurant_show_style)).setText(data.getStyle().get(0));
		((RatingBar) findViewById(R.id.restaurant_show_expertscore)).setRating(data.getExpert_score());
		((RatingBar) findViewById(R.id.restaurant_show_customerscore)).setRating(data.getCustomer_score());
		BitmapManager.bindView(findViewById(R.id.restaurant_show_bigimage), data.getZhu_image());
		view.setOnTouchListener(new View.OnTouchListener() {
			float downX = -1, downY = -1, upX = -1, upY = -1;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction()==MotionEvent.ACTION_UP) {
					RestaurantInnerActivity.bigImageActivity = BigImageActivity.this;
					RestaurantInnerActivity.start(BigImageActivity.this, r, result);
				}
				return true;
			}
		});
	}
}
