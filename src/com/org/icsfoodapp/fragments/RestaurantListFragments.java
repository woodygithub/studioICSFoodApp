package com.org.icsfoodapp.fragments;

import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.org.icsfoodapp.MyApp;
import com.org.icsfoodapp.model.RestaurantList;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.view.TopBarContain;
import com.fax.utils.view.list.ObjectXAdapter;
import com.fax.utils.view.list.ObjectXListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.org.icsfoodapp.BigImageActivity;

import com.org.icsfoodapp.R;

public class RestaurantListFragments extends MenuLockFragment {
	RestaurantList restaurantList;
	public static void start(FragmentActivity fragmentActivity, int id){
		fragmentActivity.getSupportFragmentManager().beginTransaction()
			.replace(id, new RestaurantListFragments()).addToBackStack(null).commit();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		ObjectXListView listview = new ObjectXListView(getActivity());
		listview.setDivider(new ColorDrawable(Color.WHITE));
		listview.setDividerHeight(1);
		listview.setBackgroundResource(R.color.darkblue);
		listview.setHeaderDividersEnabled(false);
		
		TopBarContain topBarContain = new TopBarContain(getActivity())
		.setTitle("", R.drawable.topbar_ic_logo, 0)
		.setLeftBtn(R.drawable.topbar_ic_back, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				( (MainActivity) getActivity()).showMenu();
				getFragmentManager().popBackStack();
				
			}
		}).setContentView(listview);
		listview.setPullRefreshEnable(false);
		listview.setPullLoadEnable(false);
		listview.setPullRefreshWhitoutViewEnable(true);
		listview.setPullLoadWhitoutViewEnable(true);
		listview.setAdapter(new ObjectXAdapter.PagesAdapter<RestaurantList.RestaurantInList>() {
			@Override
			public View bindView(RestaurantList.RestaurantInList restaurantListItem, int position, View view) {
				LayoutInflater inflater = LayoutInflater.from(getActivity().getApplication());
				view = inflater.inflate(R.layout.fragment_restaurant_list_item, null);
				BitmapManager.init(RestaurantListFragments.this.getActivity());
				restaurantListItem.getId();
				BitmapManager.bindView((ImageView)view.findViewById(R.id.restaurant_list_item_image),
						restaurantListItem.getImage());
				((TextView)view.findViewById(R.id.restaurant_list_item_name)).setText(restaurantListItem.getName());
				((RatingBar)view.findViewById(R.id.restaurant_list_item_score)).setRating(restaurantListItem.getScore());
				return view;
			}

			@Override
			public String getUrl(int page) {
				return MyApp.ApiUrl + "Restaurant/list/p/1?p="+page+"&lang="+MyApp.getLang();
			}

			@Override
			public List<RestaurantList.RestaurantInList> instanceNewList(String json)
					throws Exception {
				Gson gson = new Gson();
				restaurantList = gson.fromJson(json, new TypeToken<RestaurantList>() {}.getType());
				return restaurantList.getData();
			}

			@Override
			public void onItemClick(RestaurantList.RestaurantInList data, View view, int position, long id) {
				super.onItemClick(data, view, position, id);
				//RestaurantInnerActivity.start(RestaurantListFragments.this, data, true);
				BigImageActivity.start(RestaurantListFragments.this, data);
			}
		});
		return topBarContain;
	}

	
}
