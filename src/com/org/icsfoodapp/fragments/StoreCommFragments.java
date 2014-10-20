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
import com.org.icsfoodapp.RestaurantInnerActivity;
import com.org.icsfoodapp.RestaurantMenuIneerActivity;
import com.org.icsfoodapp.StarDetailedActivity;
import com.org.icsfoodapp.model.ImageModelImp;
import com.org.icsfoodapp.model.RestaurantList;
import com.org.icsfoodapp.model.RestaurantList.RestaurantInList;
import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo;
import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity;
import com.org.icsfoodapp.model.Star;
import com.org.icsfoodapp.model.StoreInfo;

public class StoreCommFragments extends MenuLockFragment {
	StoreInfo storeInfo;
	ObjectXListView listview;
	static String type;
	public final static String TYPE_1 = "restaurantStarList";
	public final static String TYPE_2 = "DishesStarList";
	public final static String TYPE_3 = "ActivityStarList";
	public static void start(FragmentActivity fragmentActivity, int id,String type){
		StoreCommFragments.type = type;
		fragmentActivity.getSupportFragmentManager().beginTransaction()
			.add(id, new StoreCommFragments()).addToBackStack(null).commit();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		listview = new ObjectXListView(getActivity());
		listview.setDivider(new ColorDrawable(Color.WHITE));
		listview.setDividerHeight(1);
		listview.setBackgroundResource(R.color.darkblue);
		listview.setHeaderDividersEnabled(false);
		
		View headerView = inflater.inflate(R.layout.store_grid_header_layout, null, false);
		listview.addHeaderView(headerView);
		
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
		listview.setAdapter(new ObjectXAdapter.GridPagesAdapter<ImageModelImp>(2) {
			@Override
			public String getUrl(int page) {
				return MyApp.ApiUrl + "Public/info/lang?p=" + page+"&lang=" + MyApp.getLang();
			}

			@SuppressWarnings("unchecked")
			@Override
			public List<ImageModelImp> instanceNewList(String json) throws Exception {
				Gson gson = new Gson();
				storeInfo = gson.fromJson(json, new TypeToken<StoreInfo>() {}.getType());
				return ((List<ImageModelImp>) (TYPE_1.equals(type)?
						storeInfo.getData().get(0).getRestaurantStarList()
						:TYPE_2.equals(type)?
								storeInfo.getData().get(0).getDishesStarList()
								:TYPE_3.equals(type)?
										storeInfo.getData().get(0).getActivityStarList()
										:new ArrayList<ImageModelImp>()));
			}

			@Override
			protected View bindGridView(final ImageModelImp imageModel, int position, View view) {
				LayoutInflater inflater = LayoutInflater.from(getActivity().getApplication());
				view = inflater.inflate(R.layout.store_comm_list_item, null);
				BitmapManager.init(StoreCommFragments.this.getActivity());
				imageModel.getId();
				BitmapManager.bindView((ImageView)view.findViewById(R.id.store_image),
						imageModel.getImage());
				((TextView)view.findViewById(R.id.store_name)).setText(imageModel.getName());
				
				return view;
			}

			@Override
			public void onLoadSuc(List<ImageModelImp> list) {
				super.onLoadSuc(list);
				BitmapManager.bindView((ImageView)listview.findViewById(R.id.store_menu_avator),
						storeInfo.getData().get(0).getHead_image());
				((TextView)listview.findViewById(R.id.store_menu_name)).setText(storeInfo.getData().get(0).getNickname());
				String count = "0";
				if (TYPE_1.equals(type)) {
					count = storeInfo.getData().get(0).getStar().getOne();
				} else if (TYPE_2.equals(type)) {
					count = storeInfo.getData().get(0).getStar().getTwo();
				} else if (TYPE_3.equals(type)) {
					count = storeInfo.getData().get(0).getStar().getThree();
				}
				((TextView)listview.findViewById(R.id.store_menu_count)).setText(count);
			}

			@Override
			public void onItemClick(ImageModelImp imageModel, View view, int position,
					long id) {
				super.onItemClick(imageModel, view, position, id);
				if (TYPE_1.equals(type)) {
					RestaurantInnerActivity.start(StoreCommFragments.this, (RestaurantInList) imageModel);
				}else if(TYPE_2.equals(type)) {
					RestaurantMenuIneerActivity.start(StoreCommFragments.this, (Activity) imageModel);
				}else if(TYPE_3.equals(type)) {
					RestaurantActActivity.start(StoreCommFragments.this, (Activity) imageModel);
				}
			}
		});
		
		return topBarContain;
	}

	
}
