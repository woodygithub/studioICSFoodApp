package com.org.icsfoodapp;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.http.HttpUtils;
import com.org.icsfoodapp.fragments.HomeFragments;
import com.org.icsfoodapp.fragments.MyStoreFragment;
import com.org.icsfoodapp.fragments.RestaurantActivityListFragments;
import com.org.icsfoodapp.fragments.RestaurantListFragments;
import com.org.icsfoodapp.fragments.RestaurantMenuListFragments;
import com.org.icsfoodapp.fragments.SettingFragment;
import com.org.icsfoodapp.fragments.StarGridFragments;
import com.org.icsfoodapp.fragments.StoreCommFragments;
import com.org.icsfoodapp.model.User;

public class MainActivity extends FragmentActivity {
	private static final int Request_Login = 1;
	DrawerLayout drawerLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLogedView();
		drawerLayout = (DrawerLayout) View.inflate(this, R.layout.activity_main, null);
		initLogedView(drawerLayout);
		setContentView(drawerLayout);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new HomeFragments()).commit();
		findViewById(R.id.menu_avator).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(MyApp.getLogedUser()!=null){
					new AlertDialog.Builder(MainActivity.this).setTitle(R.string.Commons_Prompt)
						.setMessage(R.string.sure_to_logout)
						.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which) {
								logout();
							}
						}).setNegativeButton(android.R.string.cancel, null).show();
				}else startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), Request_Login);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initLogedView(drawerLayout);
	}


	public void myStoresClick(View view){
		if(MyApp.getLogedUser()==null){
			Toast.makeText(this, getString(R.string.please_login), Toast.LENGTH_LONG).show();
			return;
		}
		drawerLayout.closeDrawers();
		//StoreCommFragments.start(this, R.id.content_frame, StoreCommFragments.TYPE_1);
		MyStoreFragment.start(this, R.id.content_frame);
	}
	
	public void myOrdersClick(View view){

		if(MyApp.getLogedUser()==null){
			Toast.makeText(this, getString(R.string.please_login), Toast.LENGTH_LONG).show();
			return;
		}
		drawerLayout.closeDrawers();
		OrderTabsPagerFragmentActivity.start(this);
	}
	public void menuStarClick(View view){
		drawerLayout.closeDrawers();
		StarGridFragments.start(this, R.id.content_frame);
	}
	
	public void menuRestaurantClick(View view){
		drawerLayout.closeDrawers();
		RestaurantListFragments.start(this, R.id.content_frame);
	}
	public void menuActivityClick(View view){
		drawerLayout.closeDrawers();
		RestaurantActivityListFragments.start(this, R.id.content_frame);
	}
	public void menuMenuClick(View view){
		drawerLayout.closeDrawers();
		RestaurantMenuListFragments.start(this, R.id.content_frame);
	}
	public void menuSettingClick(View view){
		drawerLayout.closeDrawers();
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.content_frame, new SettingFragment()).addToBackStack(null).commit();
	}
	private void logout(){
		MyApp.saveLogedUser(null);
		HttpUtils.clearCookies();
		((ImageView)findViewById(R.id.menu_avator)).setImageResource(R.drawable.menu_ic_person);
		((TextView)findViewById(R.id.menu_name)).setText(R.string.please_login);
		((TextView)findViewById(R.id.menu_my_stores)).setText("0");
		((TextView)findViewById(R.id.menu_my_orders)).setText("0");
	}
	private void initLogedView(View drawerLayout){
		User user = MyApp.getLogedUser();
		if(user == null) return;
		changeLangTo(user.getLang()==0?Locale.SIMPLIFIED_CHINESE:Locale.ENGLISH);
		BitmapManager.bindView(findViewById(R.id.menu_avator), user.getHeadImageUrl());
		((TextView)drawerLayout.findViewById(R.id.menu_name)).setText(user.getNickName());
		((TextView)drawerLayout.findViewById(R.id.menu_my_stores)).setText(user.getCount1());
		((TextView)drawerLayout.findViewById(R.id.menu_my_orders)).setText(user.getCount2());	}
	private void initLogedView(){
		User user = MyApp.getLogedUser();
		if(user == null) return;
		changeLangTo(user.getLang()==0?Locale.SIMPLIFIED_CHINESE:Locale.ENGLISH);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			switch (requestCode) {
			case Request_Login:
				initLogedView(drawerLayout);
				break;
			}
		}
	}
	public void showMenu(){
		drawerLayout.openDrawer(Gravity.START);
	}
	public void lockMenu(){
		drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
	}
	public void releaseMenu(){
		drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
	}
	private void changeLangTo(Locale locale){
		//应用内配置语言
		Resources resources =getResources();//获得res资源对象  
		Configuration config = resources.getConfiguration();//获得设置对象  
		if(locale.equals(config.locale)) return;
		config.locale = locale; //简体中文
		resources.updateConfiguration(config, resources.getDisplayMetrics());
	}
}