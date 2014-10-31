package com.org.icsfoodapp.fragments;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import com.fax.utils.Utils;
import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.http.RequestFactory;
import com.fax.utils.task.GsonAsyncTask;
import com.fax.utils.view.TopBarContain;
import com.org.icsfoodapp.MainActivity;
import com.org.icsfoodapp.MyApp;
import com.org.icsfoodapp.R;

import com.org.icsfoodapp.model.User;
import com.org.icsfoodapp.model.UserResponse;
import com.org.icsfoodapp.pickavator.CommonChoosePicActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingFragment extends MenuLockFragment {
	User user = MyApp.getLogedUser();
	Bitmap avatorBitmap;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_setting, container, false);
		view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if(avatorBitmap!=null){
					avatorBitmap.compress(CompressFormat.JPEG, 50, baos);
					HttpRequestBase requestBase = RequestFactory.createPost(MyApp.ApiUrl+"Public/changeheadimage", 
							new BasicNameValuePair("head_image", Utils.byte2HexStr(baos.toByteArray()))
							);
					new GsonAsyncTask<UserResponse>(getActivity(), requestBase) {
						@Override
						protected void onPostExecuteSuc(UserResponse result) {
							user.getData().get(0).setHead_image(result.getData().getHead_image());
							MyApp.saveLogedUser(user);
							startActivity(new Intent(getActivity(), MainActivity.class));
							getActivity().finish();
						}
					}.setProgressDialog().execute();
				}
				if(((CompoundButton)view.findViewById(R.id.radioButton1)).isChecked()){//中文
					changeTo(Locale.SIMPLIFIED_CHINESE);
				}else{//英文
					changeTo(Locale.ENGLISH);
				}
				
			}
		});
		final ImageView avator = (ImageView)view.findViewById(R.id.setting_avator);
		if(user!=null){
			if (user.getLang()==0) {
				((CompoundButton)view.findViewById(R.id.radioButton1)).setChecked(true);
			}else if (user.getLang()==1) {
				((CompoundButton)view.findViewById(R.id.radioButton2)).setChecked(true);
			}
			BitmapManager.bindView(avator, user.getHeadImageUrl());
		}
		
		avator.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (user!=null) {
					CommonChoosePicActivity.start(getActivity(), true, new CommonChoosePicActivity.ChoosePicListener() {
						@Override
						public void onChoosed(android.app.Activity activity, Bitmap bitmap) {
							activity.finish();
							avatorBitmap = BitmapManager.scaleToMiniBitmap(bitmap, 200, 200);
							
							avator.setImageBitmap(avatorBitmap);
						}
					});
				}else{
					Toast.makeText(getActivity(), getString(R.string.please_login), Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		return new TopBarContain(view.getContext()).setContentView(view)
				.setLeftBtn(R.drawable.topbar_ic_back, new View.OnClickListener() {
			public void onClick(View v) {
				getFragmentManager().popBackStack();
			}
		});
	}
	
	private void changeTo(Locale locale){
		//应用内配置语言
		Resources resources =getResources();//获得res资源对象  
		Configuration config = resources.getConfiguration();//获得设置对象  
		if(locale.equals(config.locale)) return;
		config.locale = locale; //简体中文
		resources.updateConfiguration(config, resources.getDisplayMetrics());
		if(user!=null){
			if(config.locale.equals(Locale.SIMPLIFIED_CHINESE))
			{
				user.setLang(0);
			}else if (config.locale.equals(Locale.ENGLISH)){
				user.setLang(1);
			}
			MyApp.saveLogedUser(user);
		}
	}
}
