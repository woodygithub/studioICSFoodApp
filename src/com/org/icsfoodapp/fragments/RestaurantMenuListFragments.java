package com.org.icsfoodapp.fragments;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.org.icsfoodapp.MyApp;
import com.org.icsfoodapp.RestaurantMenuIneerActivity;
import com.org.icsfoodapp.model.RestaurantMenu;
import com.org.icsfoodapp.model.RestaurantResponse;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.view.TopBarContain;
import com.fax.utils.view.list.ObjectXAdapter;
import com.fax.utils.view.list.ObjectXListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.org.icsfoodapp.R;

public class RestaurantMenuListFragments extends MenuLockFragment {
	
	RestaurantMenu restaurantMenuList;
	TopBarContain topBarContain;
	ObjectXListView listview;
	public static void start(FragmentActivity fragmentActivity,int id) {
			fragmentActivity.getSupportFragmentManager().beginTransaction()
				.replace(id, new RestaurantMenuListFragments()).addToBackStack(null).commit();
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		listview = new ObjectXListView(getActivity());
		listview.setDivider(new ColorDrawable(Color.WHITE));
		listview.setDividerHeight(1);
		listview.setBackgroundResource(R.color.darkblue);
		listview.setHeaderDividersEnabled(false);
		View headView = inflater.inflate(R.layout.fragment_menu_list, null, false);
		listview.addHeaderView(headView);
		topBarContain = new TopBarContain(getActivity())
		.setTitle("", R.drawable.topbar_ic_logo, 0)
		.setLeftBtn(R.drawable.topbar_ic_back, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//( (MainActivity) getActivity()).showMenu();
				getFragmentManager().popBackStack();
			}
		}).setContentView(listview);
		listview.setPullRefreshEnable(false);
		listview.setPullLoadEnable(false);
		listview.setPullRefreshWhitoutViewEnable(true);
		listview.setPullLoadWhitoutViewEnable(true);
		listview.setAdapter(new ObjectXAdapter.PagesAdapter<RestaurantResponse.RestaurantInfo.Activity>() {
			@Override
			public View bindView(RestaurantResponse.RestaurantInfo.Activity activity, int position, View view) {
				LayoutInflater inflater = LayoutInflater.from(getActivity().getApplication());
				view = inflater.inflate(R.layout.fragment_menu_list_item, null);
				BitmapManager.init(RestaurantMenuListFragments.this.getActivity());
				activity.getId();
				BitmapManager.bindView((ImageView)view.findViewById(R.id.restaurant_menu_list_image),
						activity.getImage());
				((TextView)view.findViewById(R.id.restaurant_menu_list_name)).setText(activity.getName());
				((TextView)view.findViewById(R.id.restaurant_menu_list_remark)).setText(activity.getRemark());
				return view;
			}

			@Override
			public String getUrl(int page) {
				return MyApp.ApiUrl + "Dishes/list/p/1?p="+page+"&lang=" + MyApp.getLang();
			}

			@Override
			public List<RestaurantResponse.RestaurantInfo.Activity> instanceNewList(String json)
					throws Exception {
				Gson gson = new Gson();
				restaurantMenuList = gson.fromJson(json, new TypeToken<RestaurantMenu>() {}.getType());
				return restaurantMenuList.getData().getList();
			}

			@Override
			public void onLoadSuc(List<RestaurantResponse.RestaurantInfo.Activity> allList) {
				super.onLoadFinish(allList);
				View push = listview.findViewById(R.id.restaurant_menu_pushimage);
				BitmapManager.bindView(push, restaurantMenuList.getData().getPush().get(0).getImage());
				((TextView)listview.findViewById(R.id.restaurant_menu_pushname)).setText(restaurantMenuList.getData().getPush().get(0).getName());
				push.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startRestaurantMenuInner(restaurantMenuList.getData().getPush().get(0));
					}
				});
				List<Integer> layout = new ArrayList<Integer>();
				layout.add(R.id.restaurant_menu_subpushimage_item_1);
				layout.add(R.id.restaurant_menu_subpushimage_item_2);
				layout.add(R.id.restaurant_menu_subpushimage_item_3);
				for (int i = 0; i < layout.size()&& i < restaurantMenuList.getData().getSubpush().size(); i++) {
					View v = topBarContain.findViewById(layout.get(i));
					v.setOnClickListener(new View.OnClickListener() {
						int i;
						public View.OnClickListener setI(int i){
							this.i = i;
							return this;
						}
						@Override
						public void onClick(View v) {
							startRestaurantMenuInner(restaurantMenuList.getData().getSubpush().get(i));
						}
					}.setI(i));
					((TextView)v.findViewById(R.id.meddle_name))
						.setText(restaurantMenuList.getData().getSubpush().get(i).getName());
					
					BitmapManager.bindView((ImageView)v.findViewById(R.id.meddle_image)
							, restaurantMenuList.getData().getSubpush().get(i).getImage());
				}
			}
			@Override
			public void onItemClick(RestaurantResponse.RestaurantInfo.Activity t, View view, int position, long id) {
				super.onItemClick(t, view, position, id);
				startRestaurantMenuInner(t);
			}
			
		});
		return topBarContain;
	}

	private void startRestaurantMenuInner(RestaurantResponse.RestaurantInfo.Activity data) {
		RestaurantMenuIneerActivity.start(RestaurantMenuListFragments.this, data);
	}
	

	
}
