package com.org.icsfoodapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.lbsapi.auth.i;
import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.view.TopBarContain;
import com.fax.utils.view.list.ObjectXAdapter;
import com.fax.utils.view.list.ObjectXListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.org.icsfoodapp.model.ImageModelImp;
import com.org.icsfoodapp.model.RestaurantImageGrid;
import com.org.icsfoodapp.model.RestaurantImageGrid.RestaurantImageGradeData;
import com.org.icsfoodapp.model.RestaurantImageGrid.RestaurantImageGradeData.CaipingImage;
import com.org.icsfoodapp.model.RestaurantImageGrid.RestaurantImageGradeData.HuanjingImage;
import com.org.icsfoodapp.model.RestaurantImageGrid.RestaurantImageGradeData.VideoImage;
import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo;
import com.org.icsfoodapp.model.Star;

public class ImageGridActivity extends Activity {
	RestaurantInfo data;
	RestaurantImageGrid lists;
	String tag;
	public static final String H_I = "HuanjingImage";
	public static final String C_I = "CaipingImage";
	public static final String V_I = "VideoImage";
	private List<ImageModelImp> result;
	public static void start(Activity activity, RestaurantInfo data ,String tag){
		activity.startActivity(new Intent().setClass(activity, ImageGridActivity.class)
				.putExtra(RestaurantInfo.class.getName(), data).putExtra(String.class.getName(), tag));
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		data = (RestaurantInfo) getIntent().getSerializableExtra(RestaurantInfo.class.getName());
		tag = getIntent().getStringExtra(String.class.getName());
		ObjectXListView listview = new ObjectXListView(this);
		listview.setDivider(new ColorDrawable(Color.WHITE));
		listview.setDividerHeight(0);
		listview.setBackgroundResource(R.color.darkblue);
		listview.setHeaderDividersEnabled(false);
		
		TopBarContain topBarContain = new TopBarContain(this)
			.setTitle("", R.drawable.topbar_ic_logo, 0)
			.setLeftBtn(R.drawable.topbar_ic_back, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//( (MainActivity) getActivity()).showMenu();
				finish();
			}
		}).setContentView(listview);
		setContentView(topBarContain);


		listview.setPullRefreshEnable(false);
		listview.setPullLoadEnable(false);
		listview.setAdapter(new ObjectXAdapter.GridPagesAdapter<ImageModelImp>(3) {
			@Override
			public String getUrl(int page) {
				return MyApp.ApiUrl + "Restaurant/other?id="+data.getId()+"&lang="+MyApp.getLang()+"&p="+page;
			}
			@Override
			public List<ImageModelImp> instanceNewList(String json) throws Exception {
				Gson gson = new Gson();
				lists = gson.fromJson(json, new TypeToken<RestaurantImageGrid>() {}.getType());
				result = new ArrayList<ImageModelImp>();
				switch (tag) {
				case H_I:
					List<HuanjingImage> huanjingImages = lists.getData().getHuanjing_image();
					for (HuanjingImage huanjingImage : huanjingImages) {
						result.add(huanjingImage);
					}
					return result;
				case C_I:
					List<CaipingImage> caipingImages = lists.getData().getCaiping_image();
					for (CaipingImage caipingImage : caipingImages) {
						result.add(caipingImage);
					}
					return result;
				case V_I:
					List<VideoImage> videoImages = lists.getData().getVideo_image();
					for (VideoImage videoImage : videoImages) {
						result.add(videoImage);
					}
					return result;
				default:
					return null;
				}
			}
			@Override
			protected View bindGridView(final ImageModelImp starInfo,final int position, View view) {
				LayoutInflater inflater = LayoutInflater.from(ImageGridActivity.this.getApplicationContext());
				view = inflater.inflate(R.layout.fragment_image_list_item, null);
				BitmapManager.init(ImageGridActivity.this);
				starInfo.getId();
				BitmapManager.bindView((ImageView)view.findViewById(R.id.grid_item_image),
						starInfo.getImage());
				if (tag.equals(V_I)) {
					((ImageView)view.findViewById(R.id.icon_play)).setImageResource(R.drawable.play);
				}
				//((TextView)view.findViewById(R.id.celebrity_name)).setText(starInfo.getName());
				view.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (tag.equals(V_I)) {
							startVideoPlayActivity(position);
						}else{
							startImagesActivity(position);
						}
					}
				});
				return view;
			}
		});
	}
	private void startImagesActivity(int position) {
		String[] tuijianArr = new String[result.size()];
		for (int i = 0; i < result.size(); i++) {
			tuijianArr[i] = result.get(i).getImage();
		}
		ImagesActivity.start(ImageGridActivity.this, tuijianArr, position);
	}
	private void startVideoPlayActivity(int position) {
		MediaPlayerActivity.start(this,result.get(position).getVideo());
	}
}
