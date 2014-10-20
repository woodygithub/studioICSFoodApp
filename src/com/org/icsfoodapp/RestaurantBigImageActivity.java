package com.org.icsfoodapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.task.GsonAsyncTask;
import com.org.icsfoodapp.model.RestaurantList.RestaurantInList;
import com.org.icsfoodapp.model.RestaurantResponse;

public class RestaurantBigImageActivity extends Fragment {
	OnHeadlineSelectedListener mCallback;
	public static void start(FragmentActivity fragmentActivity, int id,RestaurantInList data){
		fragmentActivity.startActivity(new Intent().setClass(fragmentActivity, RestaurantBigImageActivity.class)
				.putExtra(RestaurantInList.class.getName(), data));
	}
	
	interface OnHeadlineSelectedListener {
		public void onArticleSelected(int position);
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//RestaurantInList r = (RestaurantInList) getIntent().getSerializableExtra(RestaurantInList.class.getName());
		View view = inflater.inflate(R.layout.restaurant_bigpag_layout, container, false);
		
		new GsonAsyncTask<RestaurantResponse>(getActivity(), MyApp.ApiUrl + "Restaurant/info/id/1?id=" + 0 + "&lang=" + MyApp.getLang()) {
			@Override
			protected void onPostExecuteSuc(RestaurantResponse result) {
				init(result);
			}
		}.setProgressDialog().execute();
		return view;
	}
	protected void init(RestaurantResponse result) {
			ImageView indexPageImage = (ImageView) getActivity().findViewById(R.id.restaurant_indexpage);
			BitmapManager.init(getActivity());
			BitmapManager.bindView(indexPageImage, result.getData().getZhu_image());
	}
	
}
