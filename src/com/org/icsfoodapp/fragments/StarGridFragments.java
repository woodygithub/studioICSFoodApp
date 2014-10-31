package com.org.icsfoodapp.fragments;

import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.org.icsfoodapp.MyApp;
import com.org.icsfoodapp.model.RestaurantResponse;
import com.org.icsfoodapp.model.Star;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.view.TopBarContain;
import com.fax.utils.view.list.ObjectXAdapter;
import com.fax.utils.view.list.ObjectXListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.org.icsfoodapp.R;

import com.org.icsfoodapp.StarDetailedActivity;

public class StarGridFragments extends MenuLockFragment {
	Star star;
	public static void start(FragmentActivity fragmentActivity, int id){
		fragmentActivity.getSupportFragmentManager().beginTransaction()
			.replace(id, new StarGridFragments()).addToBackStack(null).commit();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		ObjectXListView listview = new ObjectXListView(getActivity());
		listview.setDivider(new ColorDrawable(Color.WHITE));
		listview.setDividerHeight(0);
		listview.setBackgroundResource(R.color.darkblue);
		listview.setHeaderDividersEnabled(false);
		
		TopBarContain topBarContain = new TopBarContain(getActivity())
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
		listview.setAdapter(new ObjectXAdapter.GridPagesAdapter<RestaurantResponse.RestaurantInfo>(2) {

			@Override
			public String getUrl(int page) {
				return MyApp.ApiUrl + "Expert/list/p/1?p="+page+"&lang="+MyApp.getLang();
			}

			@Override
			public List<RestaurantResponse.RestaurantInfo> instanceNewList(String json)
					throws Exception {
				Gson gson = new Gson();
				star = gson.fromJson(json, new TypeToken<Star>() {}.getType());
				return star.getData();
			}

			@Override
			protected View bindGridView(final RestaurantResponse.RestaurantInfo starInfo, int position,
					View view) {
				LayoutInflater inflater = LayoutInflater.from(getActivity().getApplication());
				view = inflater.inflate(R.layout.fragment_celebrity_list_item, null);
				BitmapManager.init(StarGridFragments.this.getActivity());
				starInfo.getId();
				BitmapManager.bindView((ImageView)view.findViewById(R.id.grid_item_image),
						starInfo.getList_image());
				((TextView)view.findViewById(R.id.celebrity_name)).setText(starInfo.getNickname());
				view.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						StarDetailedActivity.start(StarGridFragments.this, starInfo);
					}
				});
				return view;
			}
		});
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//RestaurantInnerActivity.start(StarGridFragments.this, star.getData().get(position-1));
			}
		});
		return topBarContain;
	}

	
}
