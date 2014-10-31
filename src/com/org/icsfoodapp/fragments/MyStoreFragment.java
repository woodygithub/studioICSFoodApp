package com.org.icsfoodapp.fragments;

import java.util.ArrayList;

import com.org.icsfoodapp.MyApp;
import com.org.icsfoodapp.RestaurantActActivity;
import com.org.icsfoodapp.RestaurantInnerActivity;
import com.org.icsfoodapp.RestaurantMenuIneerActivity;
import com.org.icsfoodapp.model.MyStoreResponse;
import com.org.icsfoodapp.model.RestaurantList;
import com.org.icsfoodapp.model.RestaurantResponse;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.task.GsonAsyncTask;
import com.fax.utils.view.TopBarContain;
import com.org.icsfoodapp.R;

public class MyStoreFragment extends Fragment {
	View view;
	public static void start(FragmentActivity fragmentActivity, int id){
		fragmentActivity.getSupportFragmentManager().beginTransaction()
			.replace(id, new MyStoreFragment()).addToBackStack(null).commit();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.my_store_layout, null, false);
		BitmapManager.init(getActivity());
		TopBarContain topBarContain = new TopBarContain(getActivity())
			.setTitle("", R.drawable.topbar_ic_logo, 0)
			.setLeftBtn(R.drawable.topbar_ic_back,  new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getFragmentManager().popBackStack();
				}
			}).setContentView(view);
		
		new GsonAsyncTask<MyStoreResponse>(getActivity(), MyApp.ApiUrl + "Public/star?lang="+MyApp.getLang()){
			@Override
			protected void onPostExecuteSuc(MyStoreResponse result) {
				if(result.isOk()){
					init(result);
				}
			}
		}.execute();
		return topBarContain;
	}

	private void init(MyStoreResponse result) {
		BitmapManager.bindView(((ImageView)view.findViewById(R.id.store_avator)), MyApp.getLogedUser().getHeadImageUrl());
		((TextView)view.findViewById(R.id.store_name)).setText(MyApp.getLogedUser().getNickName());
		initMore();
		MyStoreResponse.MyStoreData data = result.getData().get(0);
		final ArrayList<RestaurantResponse.RestaurantInfo.Activity> activity = data.getActivity();
		final ArrayList<RestaurantResponse.RestaurantInfo.Activity> dishes = data.getDishes();
		final ArrayList<RestaurantResponse.RestaurantInfo.Activity> restaurant = data.getRestaurant();
		if (activity.size()>0) {
			BitmapManager.bindView(((ImageView)view.findViewById(R.id.store_activity_image1)), activity.get(0).getImage());
			((TextView)view.findViewById(R.id.store_activity_text1)).setText(activity.get(0).getName());
			view.findViewById(R.id.store_activity1).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					RestaurantActActivity.start(getActivity(), activity.get(0));
				}
			});
		}
		if (activity.size()>1) {
			BitmapManager.bindView(((ImageView)view.findViewById(R.id.store_activity_image2)), activity.get(1).getImage());
			((TextView)view.findViewById(R.id.store_activity_text2)).setText(activity.get(1).getName());
			view.findViewById(R.id.store_activity2).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					RestaurantActActivity.start(getActivity(), activity.get(1));
				}
			});
		}
		if (dishes.size()>0) {
			BitmapManager.bindView(((ImageView)view.findViewById(R.id.store_menu_image1)), dishes.get(0).getImage());
			((TextView)view.findViewById(R.id.store_menu_text1)).setText(dishes.get(0).getName());
			view.findViewById(R.id.store_menu1).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					RestaurantMenuIneerActivity.start(getActivity(), dishes.get(0));
				}
			});
		}
		if (dishes.size()>1) {
			BitmapManager.bindView(((ImageView)view.findViewById(R.id.store_menu_image2)), dishes.get(1).getImage());
			((TextView)view.findViewById(R.id.store_menu_text2)).setText(dishes.get(1).getName());
			view.findViewById(R.id.store_menu2).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					RestaurantMenuIneerActivity.start(getActivity(), dishes.get(1));
				}
			});
		}
		if (restaurant.size()>0) {
			BitmapManager.bindView(((ImageView)view.findViewById(R.id.store_restaurant_image1)), restaurant.get(0).getImage());
			((TextView)view.findViewById(R.id.store_restaurant_text1)).setText(restaurant.get(0).getName());
			view.findViewById(R.id.store_restaurant1).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					RestaurantInnerActivity.start(getActivity(), new RestaurantList.RestaurantInList(restaurant.get(0).getId()));
				}
			});
		}
		if (restaurant.size()>1) {
			BitmapManager.bindView(((ImageView)view.findViewById(R.id.store_restaurant_image2)), restaurant.get(1).getImage());
			((TextView)view.findViewById(R.id.store_restaurant_text2)).setText(restaurant.get(1).getName());
			view.findViewById(R.id.store_restaurant2).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					RestaurantInnerActivity.start(getActivity(), new RestaurantList.RestaurantInList(restaurant.get(1).getId()));
				}
			});
		}
	}
	
	private void initMore() {
		view.findViewById(R.id.store_activity_more).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StoreCommFragments.start(getActivity(), R.id.content_frame, StoreCommFragments.TYPE_3);
			}
		});
		view.findViewById(R.id.store_menu_more).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StoreCommFragments.start(getActivity(), R.id.content_frame, StoreCommFragments.TYPE_2);
			}
		});
		view.findViewById(R.id.store_restaurant_more).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StoreCommFragments.start(getActivity(), R.id.content_frame, StoreCommFragments.TYPE_1);
			}
		});
	}
	
}
