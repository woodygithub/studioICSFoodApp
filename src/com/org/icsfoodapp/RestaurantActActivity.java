package com.org.icsfoodapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.http.RequestFactory;
import com.fax.utils.task.GsonAsyncTask;
import com.fax.utils.view.TopBarContain;
import com.google.gson.Gson;
import com.org.icsfoodapp.R.layout;
import com.org.icsfoodapp.R.string;
import com.org.icsfoodapp.model.ImageModelImp;
import com.org.icsfoodapp.model.Response;
import com.org.icsfoodapp.model.RestaurantAct;
import com.org.icsfoodapp.model.User;
import com.org.icsfoodapp.model.RestaurantList.RestaurantInList;
import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Dishes;
import com.org.icsfoodapp.pickavator.WXShareUtils;
import com.org.icsfoodapp.views.HorizontalListView;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiConfiguration.Status;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantActActivity extends Activity {
	View view;
	RestaurantAct result;
	public static void start(Fragment fragment, com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity data) {
		fragment.startActivity(new Intent().setClass(fragment.getActivity(), RestaurantActActivity.class)
				.putExtra(com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity.class.getName(),data));
	}
	public static void start(Activity activity, com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity data) {
		activity.startActivity(new Intent().setClass(activity, RestaurantActActivity.class)
				.putExtra(com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity.class.getName(),data));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity r = 
				((com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity)
				getIntent().getSerializableExtra
				(com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Activity.class.getName()));
		view = getLayoutInflater().inflate(R.layout.fragment_activity_page, null, false);
		
		
		TopBarContain topBarContain = new TopBarContain(this)
				.setTitle("", R.drawable.topbar_ic_logo, 0)
				.setLeftBtn(R.drawable.topbar_ic_back,  new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						RestaurantActActivity.this.finish();
					}
				}).setRightBtn(R.drawable.share, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						shareClick(v);
					}
				}).setContentView(view);
		setContentView(topBarContain);
		new GsonAsyncTask<RestaurantAct>(this, MyApp.ApiUrl+ "Activity/info/id/1/lang/en?id=" + r.getId() + "&lang=" + MyApp.getLang()){

			@Override
			protected void onPostExecuteSuc(RestaurantAct result) {
				RestaurantActActivity.this.result = result;
				init(result);
			}
		}.setProgressDialog().execute();
	}
	public void shareClick(View view){
		WXShareUtils.shareUrl(this, getString(R.string.app_name));
	}
	public void buyClick(View view){
		if(MyApp.getLogedUser() == null){
			Toast.makeText(this.getApplicationContext(), getString(R.string.please_login), Toast.LENGTH_SHORT);
			LoginActivity.start(this);
		}else {
			OrderActivity.start(this, result.getData(),OrderActivity.ACT_TYP);
		}
		
	}
	public void collectActivityClick(View view){
		final boolean aim = !result.getData().getIsStar();
		HttpRequestBase requestBase = RequestFactory.createPost(MyApp.ApiUrl+ "Activity/star", 
				new BasicNameValuePair("id", result.getData().getId()),
				new BasicNameValuePair("status", aim?"1":"0")
				);
		new GsonAsyncTask<Response>(this, requestBase) {
			@Override
			protected void onPostExecute(Response response) {
				if(response!=null && !response.isOk()) setToast(response.getMsg());//����ʧ����ʾ�Ƿ��ص�Msg
				super.onPostExecute(response);
			}
			@Override
			protected void onPostExecuteSuc(Response result) {
				if (result.isOk()) {
					RestaurantActActivity.this.result.getData().setIsStar(aim);
					((Button)RestaurantActActivity.this.view.findViewById(R.id.activity_collect))
						.setText(RestaurantActActivity.this.result.getData().getIsStar()?R.string.alreadystore:R.string.store);
					User user = MyApp.getLogedUser();
					String a = aim?user.addCount1():user.subCount1();
					MyApp.saveLogedUser(user);
				}
			}
		}.setProgressDialog().setToast(true).execute();
	}
	
	private void init(RestaurantAct result) {
		BitmapManager.init(this);
		
		result.getData().getId();
		BitmapManager.bindView((ImageView)(view.findViewById(R.id.restaurant_activity_page_bigimage))
				, result.getData().getTuijian_image());
		((Button)view.findViewById(R.id.activity_collect))
			.setText(result.getData().getIsStar()?R.string.alreadystore:R.string.store);
		((TextView)view.findViewById(R.id.restaurant_activity_name))
			.setText(result.getData().getName());
		((TextView)view.findViewById(R.id.restaurant_activity_page_remark))
			.setText(result.getData().getRemark());
		
		HorizontalListView gallery = ((HorizontalListView)findViewById(R.id.restanrant_activity_smallgallery));
		setGalleryAdapter(gallery, result.getData().getSmall_image());
	}
	private void setGalleryAdapter(HorizontalListView gallery,final List<? extends ImageModelImp> imageModels){
		gallery.setAdapter(new BaseAdapter() {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if(convertView == null) convertView = View.inflate(getApplication(), R.layout.gallery_item_layout, null);
				ImageModelImp imageModel = getItem(position);
				((TextView) convertView.findViewById(R.id.gallerytext)).setText(imageModel.getName());
				if(imageModel instanceof Dishes){
					((TextView) convertView.findViewById(R.id.gallerytext)).setTextColor(Color.DKGRAY);
				}
				BitmapManager.bindView(convertView.findViewById(R.id.galleryimage), imageModel.getSmailImage());
				return convertView;
			}
			@Override
			public long getItemId(int position) {
				return 0;
			}
			@Override
			public ImageModelImp getItem(int position) {
				if (imageModels.size()==0) {
					return null;
				}
				return imageModels.get( position );
			}
			@Override
			public int getCount() {
				return imageModels.size();
			}
			
		});
	}

}
