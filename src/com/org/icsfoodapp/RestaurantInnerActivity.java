package com.org.icsfoodapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import com.org.icsfoodapp.model.RestaurantList;
import com.org.icsfoodapp.model.RestaurantResponse;
import com.org.icsfoodapp.model.User;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.http.RequestFactory;
import com.fax.utils.task.GsonAsyncTask;
import com.fax.utils.view.CircleProgressBarView;
import com.fax.utils.view.TopBarContain;
import com.fax.utils.view.list.ObjectXAdapter;
import com.fax.utils.view.list.ObjectXListView;
import com.fax.utils.view.pager.NetImgsViewPager;
import com.fax.utils.view.pager.PointIndicator;

import com.org.icsfoodapp.model.ImageModelImp;
import com.org.icsfoodapp.model.Response;
import com.org.icsfoodapp.pickavator.WXShareUtils;
import com.org.icsfoodapp.views.HorizontalListView;

@SuppressLint("NewApi")
public class RestaurantInnerActivity extends Activity {
	static BigImageActivity bigImageActivity;
	HorizontalListView galleryBaseInfo;
	NetImgsViewPager viewPagerMenu;
	HorizontalListView galleryActivities;
	ObjectXListView listComment;
	RestaurantResponse.RestaurantInfo data;
	TopBarContain topBarContain;
	View view;
	View acitvitytopView;
	ScrollView scrollView;
	String[] tuijianArr;
	LinearLayout commentLayout;
	public static void start(Fragment fragment, RestaurantList.RestaurantInList data) {
		start(fragment, data, null);
	}
	public static void start(Fragment fragment, RestaurantList.RestaurantInList data, RestaurantResponse result) {
		fragment.startActivity(new Intent().setClass(fragment.getActivity(), RestaurantInnerActivity.class)
			.putExtra(RestaurantList.RestaurantInList.class.getName(),data)
			.putExtra(RestaurantResponse.class.getName(), result));
	}
	public static void start(Activity activity, RestaurantList.RestaurantInList data) {
		start(activity, data, null);
	}
	public static void start(Activity activity, RestaurantList.RestaurantInList data,RestaurantResponse result) {
		activity.startActivity(new Intent().setClass(activity, RestaurantInnerActivity.class)
			.putExtra(RestaurantResponse.class.getName(), result)
			.putExtra(RestaurantList.RestaurantInList.class.getName(),data));
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RestaurantList.RestaurantInList r = (RestaurantList.RestaurantInList) getIntent().getSerializableExtra(RestaurantList.RestaurantInList.class.getName());
		RestaurantResponse oldResult = (RestaurantResponse) getIntent().getSerializableExtra(RestaurantResponse.class.getName());
		view =  getLayoutInflater().inflate(R.layout.restaurant_inner_layout, null, false);
		if(oldResult!=null){
			overridePendingTransition(R.anim.main_up_translate, R.anim.main_up_alpha_scale);
		}
		//topView = getLayoutInflater().inflate(R.layout.restaurant_top_layout, null, false);
		//View scrollHeaderView = getLayoutInflater().inflate(R.layout.restaurant_scroll_header_layout, null, false);
		topBarContain = new TopBarContain(this)
				.setTitle("", R.drawable.topbar_ic_logo, 0)
				.setLeftBtn(R.drawable.topbar_ic_back,  new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						RestaurantInnerActivity.this.finish();
					}
				}).setContentView(view);
		setContentView(topBarContain);
		scrollView = ((ScrollView)findViewById(R.id.restaurantinner));
		((TextView) findViewById(R.id.restaurant_top_name)).setText(r.getName());
		((TextView) findViewById(R.id.restaurant_top_style)).setText(r.getStyle());
		((ScrollView)findViewById(R.id.restaurantinner)).setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!(v instanceof EditText)){
					 MyApp.hideKeyBoard(RestaurantInnerActivity.this.getApplicationContext(), topBarContain);
				}
				return false;
			}
		});
		((TextView)findViewById(R.id.scroll_to_menu)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				scrollView.scrollTo(0, findViewById(R.id.restaurant_menupager).getTop());
			}
		});
		((TextView)findViewById(R.id.scroll_to_activity)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int top = findViewById(R.id.restaurant_menupager).getTop();
				scrollView.scrollTo(0, findViewById(R.id.restanrant_galleryactivities_top).getTop()+top);
			}
		});

		//galleryBaseInfo = (HorizontalListView) findViewById(R.id.restanrant_gallerybaseinfo);
		viewPagerMenu = (NetImgsViewPager) findViewById(R.id.restaurant_gallerymenu);
		galleryActivities = (HorizontalListView) findViewById(R.id.restanrant_galleryactivities);
		listComment = (ObjectXListView) findViewById(R.id.restanrant_comment_list);
		commentLayout = (LinearLayout)findViewById(R.id.restanrant_comment_list_layout);
		if (oldResult==null) {
			new GsonAsyncTask<RestaurantResponse>(this, MyApp.ApiUrl + "Restaurant/info/id/1?id=" + r.getId() + "&lang=" + MyApp.getLang()) {
				@Override
				protected void onPostExecuteSuc(RestaurantResponse result) {
					init(topBarContain, result);
				}
			}.setProgressDialog().execute();
		}else{
			init(topBarContain, oldResult);
		}
		
	}
	
	public void shareClick(View v){
		WXShareUtils.shareUrl(this, getString(R.string.app_name));
	}
	public void restanrantCommentMoreClick(View v){
		RestaurantCommentInner.start(this, data);
	}
	public void restanrantMoreClick(View v){
		RestaurantDetailedActivity.start(this, data);
	}
	public void imageClick1(View v){
		ImageGridActivity.start(this, data, ImageGridActivity.H_I);
	}
	public void imageClick2(View v){
		ImageGridActivity.start(this, data, ImageGridActivity.C_I);
	}
	public void imageClick3(View v){
		ImageGridActivity.start(this, data, ImageGridActivity.V_I);
	}
	public void restanrantCommentSubmitClick(View v){
		String comment = ((EditText)findViewById(R.id.restanrant_comment_content)).getText().toString();
		if(TextUtils.isEmpty(comment)){
			Toast.makeText(this, R.string.please_input_comment, Toast.LENGTH_SHORT).show();
			return;
		}
		HttpRequestBase requestBase = RequestFactory.createPost(MyApp.ApiUrl+ "Restaurant/commet", 
				new BasicNameValuePair("rid", data.getId()), 
				new BasicNameValuePair("commet", comment), 
				new BasicNameValuePair("score", ((RatingBar) findViewById(R.id.restanrant_comment_currscore) ).getRating() + ""), 
				new BasicNameValuePair("isall", "3")
				);
		new GsonAsyncTask<Response>(this, requestBase) {
			@Override
			protected void onPostExecute(Response response) {
				if(response!=null && !response.isOk()) setToast(response.getMsg());//设置MSG
				if(response.isOk()) ((EditText)findViewById(R.id.restanrant_comment_content)).setText("");
				super.onPostExecute(response);
			}
			@Override
			protected void onPostExecuteSuc(Response result) {
			}
		}.setProgressDialog().setToast(true).execute();
	}
	
	private void init(final TopBarContain topBarContain, final RestaurantResponse result) {
		data = result.getData();
		((TextView) findViewById(R.id.restaurant_top_name)).setText(data.getName());
		((TextView) findViewById(R.id.restaurant_top_style)).setText(data.getStyle().get(0));
		((RatingBar) findViewById(R.id.restaurant_top_expertscore)).setRating(data.getExpert_score());
		((RatingBar) findViewById(R.id.restaurant_top_customerscore)).setRating(data.getCustomer_score());
		((TextView) findViewById(R.id.restaurant_top_business_hours)).setText(data.getBusiness_hours());
		((TextView) findViewById(R.id.restaurant_top_address)).setText(data.getAddress());
		((TextView) findViewById(R.id.restaurant_remark)).setText(data.getRemark());
		BitmapManager.bindView(findViewById(R.id.restaurant_list_image), data.getList_image());
		
		BitmapManager.bindView(findViewById(R.id.restaurant_buttonimage1), data.getHuanjing_image().get(0).getImage());
		BitmapManager.bindView(findViewById(R.id.restaurant_buttonimage2), data.getHuanjing_image().get(1).getImage());
		BitmapManager.bindView(findViewById(R.id.restaurant_buttonimage3), data.getHuanjing_image().get(2).getImage());
		
		setViewPageAdapter(viewPagerMenu, data.getTuijian_image());
		
		setGalleryAdapter(galleryActivities, data.getActivity());
		galleryActivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				RestaurantActActivity.start(RestaurantInnerActivity.this, data.getActivity().get(position));
			}
		});
		ArrayList<RestaurantResponse.RestaurantInfo.Commet> comments = data.getCommet();
		setListAdapter(listComment, comments);
		topBarContain.setRightBtn(
				result.getData().isStar()?R.drawable.topbar_ic_star_checked:R.drawable.topbar_ic_star_normal, 
				new View.OnClickListener() {
					User user = MyApp.getLogedUser();
					@Override
					public void onClick(final View v) {
						if (user==null){
							Toast.makeText(RestaurantInnerActivity.this, getString(R.string.please_login), Toast.LENGTH_SHORT).show();
							return;
						}
						final boolean aim = !result.getData().isStar();
						HttpRequestBase requestBase = RequestFactory.createPost(MyApp.ApiUrl+"Restaurant/star", 
							new BasicNameValuePair("status", aim?"1":"0"),
							new BasicNameValuePair("id", result.getData().getId()));
						new GsonAsyncTask<Response>(RestaurantInnerActivity.this, requestBase) {
							@Override
							protected void onPostExecuteSuc(Response r) {
								if(r.isOk()){
									result.getData().setStar(aim);
									int drawableResId = result.getData().isStar() ?
											R.drawable.topbar_ic_star_checked:R.drawable.topbar_ic_star_normal;
									((Button)v).setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableResId, 0);
									user = MyApp.getLogedUser();
									String a = aim?user.addCount1():user.subCount1();
									MyApp.saveLogedUser(user);
									a = null;
									Toast.makeText(RestaurantInnerActivity.this, r.getMsg(), Toast.LENGTH_SHORT).show();
								}
							}
						}.setProgressDialog().execute();
					}
				});
		if (bigImageActivity!=null) {
			bigImageActivity.finish();
			bigImageActivity = null;
		}
	}

	private void setGalleryAdapter(HorizontalListView gallery,final ArrayList<? extends ImageModelImp> imageModels){
		gallery.setAdapter(new BaseAdapter() {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if(convertView == null) convertView = View.inflate(getApplication(), R.layout.gallery_item_layout, null);
				ImageModelImp imageModel = getItem(position);
				((TextView) convertView.findViewById(R.id.gallerytext)).setText(imageModel.getName());
				if(imageModel instanceof RestaurantResponse.RestaurantInfo.Dishes){
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
		if (imageModels.size()>0&&imageModels.get(0) instanceof RestaurantResponse.RestaurantInfo.HuanjingImage) {
			gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					String[] imgs = new String[imageModels.size()];
					for(int i=0,size=imageModels.size();i<size;i++){
						imgs[i] = imageModels.get(i).getImage();
					}
					ImagesActivity.start(RestaurantInnerActivity.this, imgs, position);
				}
			});
		}
	}
	
	private void setViewPageAdapter(NetImgsViewPager viewPager,final ArrayList<RestaurantResponse.RestaurantInfo.TuijianImage> data){
		ArrayList<String> views = new ArrayList<String>();
		for (int i = 0; i < data.size(); i++) {
			final RestaurantResponse.RestaurantInfo.TuijianImage dishes = data.get(i);
			views.add(dishes.getImage());
			View item = View.inflate(getApplicationContext(), R.layout.gallery_item_layout, viewPager);
		}
		viewPager.setImageScaleType(ScaleType.CENTER_CROP);
		viewPager.setOnInstantiateItemListener(new NetImgsViewPager.OnInstantiateItemListener() {
			@Override
			public void onInstantiateItem(View view, CircleProgressBarView progressView, ImageView image, final int position) {
				image.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						RestaurantResponse.RestaurantInfo.Dishes d =new RestaurantResponse.RestaurantInfo.Dishes();
						d.setId(data.get(position).getDid());
						RestaurantMenuIneerActivity.start(RestaurantInnerActivity.this, d);
					}
				});
			}
		});
		viewPager.setImgs(views);
		PointIndicator pointIndicator = (PointIndicator) findViewById(R.id.restaurant_point_indicator);
		pointIndicator.bindViewPager(viewPager);
	}
	
	private void setListAdapter(ObjectXListView listComment, final ArrayList<RestaurantResponse.RestaurantInfo.Commet> comments){
		listComment.setPullRefreshEnable(false);
		listComment.setPullLoadEnable(false);
		listComment.setAdapter(new ObjectXAdapter.SingleLocalPageAdapter<RestaurantResponse.RestaurantInfo.Commet>() {
			@Override
			public View bindView(RestaurantResponse.RestaurantInfo.Commet commet, int position,View view) {
				LayoutInflater inflater = LayoutInflater.from(getApplication());
				view = inflater.inflate(R.layout.restaurant_comment_item_layout, null);
				((TextView)view.findViewById(R.id.retaurant_comment_item_name)).setText(commet.getName());
				((RatingBar)view.findViewById(R.id.retaurant_comment_item_score)).setRating(commet.getScore());
				((TextView)view.findViewById(R.id.retaurant_comment_item_text)).setText(commet.getText());
				((TextView)view.findViewById(R.id.retaurant_comment_item_text)).setLines(2);
				BitmapManager.bindView((ImageView)view.findViewById(R.id.retaurant_comment_item_photo),commet.getImage());
				((ScrollView)findViewById(R.id.restaurantinner)).scrollTo(0, 0);
				return view;
			}
			@Override
			public List<RestaurantResponse.RestaurantInfo.Commet> instanceNewList() throws Exception {
				return comments;
			}
		});
	}
}
