package com.org.icsfoodapp.fragments;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.view.TopBarContain;
import com.fax.utils.view.list.ObjectXAdapter;
import com.fax.utils.view.list.ObjectXListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.org.icsfoodapp.MainActivity;
import com.org.icsfoodapp.MyApp;
import com.org.icsfoodapp.R;
import com.org.icsfoodapp.RestaurantActActivity;
import com.org.icsfoodapp.model.RestaurantActivityList;
import com.org.icsfoodapp.model.RestaurantList.RestaurantInList;
import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity;

public class RestaurantActivityListFragments extends MenuLockFragment {
	
	RestaurantActivityList restaurantActivityList;
	ArrayList<Activity> dataList;
	
	public static void start(FragmentActivity fragmentActivity,int id) {
			fragmentActivity.getSupportFragmentManager().beginTransaction()
				.replace(id, new RestaurantActivityListFragments()).addToBackStack(null).commit();
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
		listview.setAdapter(new ObjectXAdapter.PagesAdapter<Activity>() {
			@Override
			public View bindView(Activity activity, int position, View view) {
				LayoutInflater inflater = LayoutInflater.from(getActivity().getApplication());
				view = inflater.inflate(R.layout.fragment_activity_list_item, null);
				BitmapManager.init(RestaurantActivityListFragments.this.getActivity());
				activity.getId();
				BitmapManager.bindView((ImageView)view.findViewById(R.id.restaurant_activity_image),
						activity.getImage());
				((TextView)view.findViewById(R.id.restaurant_activity_name)).setText(activity.getName());
				((TextView)view.findViewById(R.id.restaurant_activity_time)).setText(activity.getActivity_start()+"to"+activity.getActivity_end());
				((TextView)view.findViewById(R.id.restaurant_activity_remark)).setText(activity.getRemark());
				return view;
			}
			@Override
			public String getUrl(int page) {
				return MyApp.ApiUrl + "Activity/list/p/1/lang/?p="+page+"&lang=" + MyApp.getLang();
			}
			@Override
			public List<Activity> instanceNewList(String json) throws Exception {
				Gson gson = new Gson();
				restaurantActivityList = gson.fromJson(json, new TypeToken<RestaurantActivityList>() {}.getType());
				if (dataList==null) {
					dataList = new ArrayList<Activity>();
				}
				for (int i = 0; i < restaurantActivityList.getData().size(); i++) {
					dataList.add(restaurantActivityList.getData().get(i));
				}
				return restaurantActivityList.getData();
			}
			@Override
			public void onItemClick(Activity t, View view, int position, long id) {
				super.onItemClick(t, view, position, id);
				RestaurantActActivity.start(RestaurantActivityListFragments.this, dataList.get(position-1));
			}
		});
		return topBarContain;
	}

	

	
}
