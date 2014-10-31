package com.org.icsfoodapp;

import java.util.List;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.http.RequestFactory;
import com.fax.utils.task.GsonAsyncTask;
import com.fax.utils.view.TopBarContain;
import com.fax.utils.view.list.ObjectXAdapter;
import com.fax.utils.view.list.ObjectXListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.org.icsfoodapp.model.CommentData;
import com.org.icsfoodapp.model.Response;
import com.org.icsfoodapp.model.RestaurantResponse;
import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo;
import com.org.icsfoodapp.model.RestaurantResponse.RestaurantInfo.Commet;

public class RestaurantCommentInner extends Activity {
	CommentData commentData;
	RestaurantResponse.RestaurantInfo data;
	ObjectXListView listview;
	public static void start(Activity activity,RestaurantResponse.RestaurantInfo data){
		activity.startActivity(
				new Intent(activity, RestaurantCommentInner.class)
				.putExtra(RestaurantResponse.RestaurantInfo.class.getName(), data));
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listview = new ObjectXListView(this);
		listview.setDivider(new ColorDrawable(Color.WHITE));
		listview.setDividerHeight(1);
		listview.setBackgroundResource(R.color.darkblue);
		listview.setHeaderDividersEnabled(false);
		View footView = getLayoutInflater().inflate(R.layout.comment_submit_foot_layout, null, false);
		final TopBarContain topBarContain = new TopBarContain(this)
			.setTitle("",R.drawable.topbar_ic_logo, 0)
			.setLeftFinish(null, R.drawable.topbar_ic_back)
			.setContentView(listview);
		listview.addFooterView(footView);
		listview.setPullRefreshEnable(false);
		listview.setPullLoadEnable(false);
		listview.setPullRefreshWhitoutViewEnable(true);
		setContentView(topBarContain);
		listview.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!(v instanceof EditText)){
					 MyApp.hideKeyBoard(RestaurantCommentInner.this.getApplicationContext(), topBarContain);
				}
				return false;
			}
		});
		data = (RestaurantInfo) getIntent().getSerializableExtra(RestaurantResponse.RestaurantInfo.class.getName());
		
		listview.setAdapter(new ObjectXAdapter.PagesAdapter<Commet>() {
			@Override
			public View bindView(Commet commet, int position,View view) {
				LayoutInflater inflater = LayoutInflater.from(getApplication());
				if(view==null) view = inflater.inflate(R.layout.restaurant_comment_item_layout, null);
				((TextView)view.findViewById(R.id.retaurant_comment_item_name)).setText(commet.getName());
				((RatingBar)view.findViewById(R.id.retaurant_comment_item_score)).setRating(commet.getScore());
				((TextView)view.findViewById(R.id.retaurant_comment_item_text)).setText(commet.getText());
				BitmapManager.bindView((ImageView)view.findViewById(R.id.retaurant_comment_item_photo),commet.getImage());
				return view;
			}

			@Override
			public String getUrl(int page) {
				return MyApp.ApiUrl + "Restaurant/commet/id/"+data.getId()+"/p/"+page;
			}

			@Override
			public List<Commet> instanceNewList(String json) throws Exception {
				commentData = new Gson().fromJson(json, new TypeToken<CommentData>() {}.getType());
				return commentData.getData();
			}
			
		});
		footView.findViewById(R.id.comment_foot_submit_button).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String comment = ((EditText)findViewById(R.id.comment_foot_content)).getText().toString();
				if(TextUtils.isEmpty(comment)){
					Toast.makeText(RestaurantCommentInner.this, R.string.please_input_comment, Toast.LENGTH_SHORT).show();
					return;
				}
				HttpRequestBase requestBase = RequestFactory.createPost(MyApp.ApiUrl+ "Restaurant/commet", 
						new BasicNameValuePair("rid", data.getId()), 
						new BasicNameValuePair("commet", comment), 
						new BasicNameValuePair("score", ((RatingBar) findViewById(R.id.comment_foot_currscore) ).getRating() + ""), 
						new BasicNameValuePair("isall", "1")
						);
				new GsonAsyncTask<Response>(RestaurantCommentInner.this, requestBase) {
					@Override
					protected void onPostExecute(Response response) {
						if(response!=null && !response.isOk()) setToast(response.getMsg());//设置Msg
						if(response.isOk()) ((EditText)listview.findViewById(R.id.comment_foot_content)).setText("");
						super.onPostExecute(response);
					}
					@Override
					protected void onPostExecuteSuc(Response result) {
					}
				}.setProgressDialog().setToast(true).execute();
			}
		});
	}
	
	
		
}
