package com.org.icsfoodapp;

import java.net.URLEncoder;
import java.util.List;

import com.fax.utils.view.TopBarContain;
import com.fax.utils.view.list.ObjectXAdapter;
import com.fax.utils.view.list.ObjectXListView;
import com.google.gson.Gson;

import com.org.icsfoodapp.model.RestaurantList;
import com.org.icsfoodapp.model.RestaurantList.RestaurantInList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends FragmentActivity{
	Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_search);
		findViewById(R.id.topbar_left_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TypeListFragment fragment = new TypeListFragment();
				getSupportFragmentManager().beginTransaction()
					.setCustomAnimations(R.anim.show_totop, R.anim.dismiss_tobottom, R.anim.show_totop, R.anim.dismiss_tobottom)
					.add(android.R.id.content, fragment).addToBackStack(fragment.getClass().getName()).commit();
			}
		});
		findViewById(R.id.topbar_right_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		final EditText editText = (EditText) findViewById(android.R.id.edit);
		final ObjectXListView listView = (ObjectXListView) findViewById(android.R.id.list);
		listView.setPullLoadEnable(false);
		listView.setPullRefreshEnable(false);
		listView.setPullRefreshWhitoutViewEnable(true);
		listView.setPullLoadWhitoutViewEnable(true);
		listView.setAdapter(new ObjectXAdapter.PagesAdapter<RestaurantList.RestaurantInList>() {
			@Override
			public View bindView(RestaurantInList t, int position, View convertView) {
				if(convertView == null){
					convertView = new TextView(SearchActivity.this);
					int padding = (int) MyApp.convertToDp(10);
					((TextView)convertView).setPadding(padding, padding, padding, padding);
				}
				((TextView)convertView).setText(t.getName());
				return convertView;
			}
			@Override
			public String getUrl(int page) {
				String words = URLEncoder.encode(editText.getText().toString());
				return MyApp.ApiUrl +"Public/search/keyword/"+ words + "/p/"+page+"/lang/" + MyApp.getLang();
			}
			@Override
			public List<RestaurantInList> instanceNewList(String json) throws Exception {
				return new Gson().fromJson(json, RestaurantList.class).getData();
			}
			@Override
			public void onItemClick(RestaurantInList t, View view, int position, long id) {
				super.onItemClick(t, view, position, id);
				startActivity(new Intent(SearchActivity.this, RestaurantInnerActivity.class)
						.putExtra(RestaurantInList.class.getName(), t)
						.putExtra(Boolean.class.getName(), true));
			}
			@Override
			protected boolean isAutoLoadAfterInit() {
				return false;
			}
		});
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				listView.clear();
				listView.setPullRefreshEnable(false);
				if(TextUtils.isEmpty(s)) return;
				handler.removeCallbacks(reloadRun);
				handler.postDelayed(reloadRun, 1000);
			}
			Runnable reloadRun = new Runnable() {
				@Override
				public void run() {
					listView.reload();
				}
			};
		});
	}
	//分类列表
	public static class TypeListFragment extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final ObjectXListView listView = new ObjectXListView(getActivity());
			View view = new TopBarContain(getActivity()).setTitle("", R.drawable.topbar_ic_logo, 0)
					.setLeftBtn(R.drawable.topbar_ic_back, new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							getFragmentManager().popBackStack();
						}
					}).setContentView(listView);
			view.setBackgroundResource(R.color.green);
			view.setBackgroundResource(R.color.green);
			view.setClickable(true);
			listView.setAdapter(new ObjectXAdapter.SinglePageAdapter<RestaurantList.RestaurantInList>() {
				@Override
				public View bindView(RestaurantInList t, int position, View convertView) {
					if(convertView == null){
						convertView = new TextView(listView.getContext());
						int padding = (int) MyApp.convertToDp(10);
						((TextView)convertView).setPadding(padding, padding, padding, padding);
					}
					((TextView)convertView).setText(t.getName());
					return convertView;
				}
				@Override
				public String getUrl() {
					return MyApp.ApiUrl +"Style/list/lang/" + MyApp.getLang();
				}
				@Override
				public List<RestaurantInList> instanceNewList(String json) throws Exception {
					return new Gson().fromJson(json, RestaurantList.class).getData();
				}
				@Override
				public void onItemClick(RestaurantInList t, View view, int position, long id) {
					super.onItemClick(t, view, position, id);
					RestaurantListFragment fragment = new RestaurantListFragment();
					fragment.setArguments(new Intent().putExtra(RestaurantInList.class.getName(), t).getExtras());
					getFragmentManager().beginTransaction()
						.setCustomAnimations(R.anim.show_totop, R.anim.dismiss_tobottom, R.anim.show_totop, R.anim.dismiss_tobottom)
						.add(android.R.id.content, fragment).addToBackStack(fragment.getClass().getName()).commit();
				}
			});
			return view;
		}
	}
	//分类餐厅
	public static class RestaurantListFragment extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final ObjectXListView listView = new ObjectXListView(getActivity());
			View view = new TopBarContain(getActivity()).setTitle("", R.drawable.topbar_ic_logo, 0)
					.setLeftBtn(R.drawable.topbar_ic_back, new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							getFragmentManager().popBackStack();
						}
					}).setContentView(listView);
			listView.setPullLoadEnable(false);
			listView.setPullRefreshEnable(false);
			listView.setPullRefreshWhitoutViewEnable(true);
			listView.setPullLoadWhitoutViewEnable(true);
			view.setBackgroundResource(R.color.green);
			view.setClickable(true);
			final RestaurantInList r = (RestaurantInList) getArguments().getSerializable(RestaurantInList.class.getName());
			listView.setAdapter(new ObjectXAdapter.SinglePageAdapter<RestaurantList.RestaurantInList>() {
				@Override
				public View bindView(RestaurantInList t, int position, View convertView) {
					if(convertView == null){
						convertView = new TextView(listView.getContext());
						int padding = (int) MyApp.convertToDp(10);
						((TextView)convertView).setPadding(padding, padding, padding, padding);
					}
					((TextView)convertView).setText(t.getName());
					return convertView;
				}
				@Override
				public String getUrl() {
					return MyApp.ApiUrl +"Style/info/id/"+r.getId()+"/lang/" + MyApp.getLang();
				}
				@Override
				public List<RestaurantInList> instanceNewList(String json) throws Exception {
					return new Gson().fromJson(json, RestaurantList.class).getData();
				}
				@Override
				public void onItemClick(RestaurantInList r, View view, int position, long id) {
					super.onItemClick(r, view, position, id);
					startActivity(new Intent(view.getContext(), BigImageActivity.class)
							.putExtra(RestaurantInList.class.getName(), r));
				}
			});
			return view;
		}
	}
}
