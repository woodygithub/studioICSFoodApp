package com.org.icsfoodapp.fragments;

import java.util.ArrayList;

import com.fax.utils.task.GsonAsyncTask;
import com.fax.utils.view.CircleProgressBarView;
import com.fax.utils.view.TopBarContain;
import com.fax.utils.view.pager.NetImgsViewPager;
import com.fax.utils.view.pager.PointIndicator;
import com.org.icsfoodapp.MainActivity;
import com.org.icsfoodapp.MyApp;
import com.org.icsfoodapp.R;
import com.org.icsfoodapp.RestaurantInnerActivity;
import com.org.icsfoodapp.SearchActivity;
import com.org.icsfoodapp.model.RestaurantList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RatingBar;
import android.widget.TextView;

public class HomeFragments extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_home, container, false);
		TopBarContain topBarContain = new TopBarContain(getActivity())
				.setTitle("", R.drawable.topbar_ic_logo, 0)
				.setLeftBtn(R.drawable.topbar_ic_menu, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						((MainActivity)getActivity()).showMenu();
					}
				}).setRightBtn(R.drawable.topbar_ic_search, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(getActivity(), SearchActivity.class));
					}
				}).setContentView(view);
		
		new GsonAsyncTask<RestaurantList>(getActivity(), MyApp.ApiUrl+"Index/list/lang/"+MyApp.getLang()) {
			@Override
			protected void onPostExecuteSuc(final RestaurantList result) {
				final NetImgsViewPager viewPager = (NetImgsViewPager) view.findViewById(R.id.view_pager);
				final PointIndicator pointIndicator = (PointIndicator) view.findViewById(R.id.point_indicator);
				ArrayList<String> imgList=new ArrayList<String>();
				for(RestaurantList.RestaurantInList restaurantInList:result.getData()){
					imgList.add(restaurantInList.getImage());
				}
				viewPager.setImageScaleType(ScaleType.CENTER_CROP);
				viewPager.setOnInstantiateItemListener(new NetImgsViewPager.OnInstantiateItemListener() {
					@Override
					public void onInstantiateItem(View view, CircleProgressBarView progressView, ImageView image, final int position) {
						image.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								startActivity(new Intent(getActivity(), RestaurantInnerActivity.class)
											.putExtra(RestaurantList.RestaurantInList.class.getName(), result.getData().get(position)));
								
							}
						});
					}
				});
				viewPager.setImgs(imgList);
				pointIndicator.bindViewPager(viewPager, new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						RestaurantList.RestaurantInList r = result.getData().get(position);
						((TextView)view.findViewById(R.id.home_restaurant_name)).setText(r.getName());
						((RatingBar)view.findViewById(R.id.home_restaurant_expertscore)).setRating(r.getScore());
						((RatingBar)view.findViewById(R.id.home_restaurant_customerscore)).setRating(r.getCustomer_score());
						((TextView)view.findViewById(R.id.home_restaurant_style)).setText(r.getStyle());
						//((TextView)view.findViewById(R.id.home_restaurant_recommend_count)).setText(r.getCount());
					}
					public void onPageScrolled(int arg0, float arg1, int arg2) {
					}
					public void onPageScrollStateChanged(int arg0) {
					}
				});
				pointIndicator.getOnPageChangeListener().onPageSelected(0);//强制回调第一个，使View显示
			}
		}.setProgressDialog().execute();
		
		return topBarContain;
	}

}
