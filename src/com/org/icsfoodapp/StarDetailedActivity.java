package com.org.icsfoodapp;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.org.icsfoodapp.model.RestaurantList;
import com.org.icsfoodapp.model.RestaurantResponse;
import com.org.icsfoodapp.model.StarDetailed;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.view.TopBarContain;
import com.fax.utils.view.list.ObjectXAdapter;
import com.fax.utils.view.list.ObjectXListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class StarDetailedActivity extends Activity {
	StarDetailed starDtl;
	ObjectXListView listview;
	public static void start(Fragment fragment,RestaurantResponse.RestaurantInfo data) {
		fragment.startActivity(new Intent().setClass(fragment.getActivity(), StarDetailedActivity.class)
				.putExtra(RestaurantResponse.RestaurantInfo.class.getName(),data));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final RestaurantResponse.RestaurantInfo r = (RestaurantResponse.RestaurantInfo)getIntent().getSerializableExtra(RestaurantResponse.RestaurantInfo.class.getName());
		listview = new ObjectXListView(this);
		listview.setDivider(new ColorDrawable(Color.DKGRAY));
		listview.setDividerHeight(1);
		listview.setBackgroundResource(R.color.darkblue);
		listview.setHeaderDividersEnabled(false);
		listview.setFooterDividersEnabled(false);
		listview.setPullRefreshWhitoutViewEnable(true);
		listview.setPullLoadWhitoutViewEnable(true);
		
		View headerView = getLayoutInflater().inflate(R.layout.celebrity_page_header_layout, null, false);
		listview.addHeaderView(headerView);
		TopBarContain topBarContain = new TopBarContain(this)
		.setTitle("", R.drawable.topbar_ic_logo, 0)
		.setLeftBtn(R.drawable.topbar_ic_back,  new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StarDetailedActivity.this.finish();
			}
		}).setContentView(listview);
		listview.setPullRefreshEnable(false);
		listview.setPullLoadEnable(false);
		listview.setAdapter(new ObjectXAdapter.PagesAdapter<StarDetailed.StarData.StarRestaurant>() {

			@Override
			public View bindView(StarDetailed.StarData.StarRestaurant star, int position, View view) {
				LayoutInflater inflater = LayoutInflater.from(getApplication());
				view = inflater.inflate(R.layout.star_restaurant_list_item_layout, null);
				BitmapManager.init(StarDetailedActivity.this);
				BitmapManager.bindView((ImageView)view.findViewById(R.id.str_retaurant_item_photo),
						star.getImage());
				((TextView)view.findViewById(R.id.str_retaurant_item_name)).setText(star.getName());
				((TextView)view.findViewById(R.id.str_retaurant_item_text)).setText(star.getRemark());
				((RatingBar)view.findViewById(R.id.str_retaurant_item_score)).setRating(star.getScore());
				return view;
			}

			@Override
			public String getUrl(int page) {
				return MyApp.ApiUrl+"Expert/info/id/1/lang/cn?id="+r.getId()+"&p="+page+"&lang="+MyApp.getLang();
			}

			@Override
			public List<StarDetailed.StarData.StarRestaurant> instanceNewList(String json) throws Exception {
				Gson gson = new Gson();
				starDtl = gson.fromJson(json, new TypeToken<StarDetailed>() {}.getType());
				return starDtl.getData().getStar();
			}

			@Override
			public void onLoadSuc(List<StarDetailed.StarData.StarRestaurant> list) {
				super.onLoadSuc(list);
				
				BitmapManager.bindView((ImageView)listview.findViewById(R.id.celebrity_image), starDtl.getData().getBig_image());
				((TextView)listview.findViewById(R.id.celebrity_name)).setText(starDtl.getData().getNickname());
				((TextView)listview.findViewById(R.id.celebrity_remark)).setText(starDtl.getData().getRemark());
				
			}

			@Override
			public void onItemClick(StarDetailed.StarData.StarRestaurant t, View view, int position, long id) {
				super.onItemClick(t, view, position, id);
				RestaurantInnerActivity.start(StarDetailedActivity.this, new RestaurantList.RestaurantInList(t.getRid()));
			}
		});
		
		setContentView(topBarContain);
	}
	
}
