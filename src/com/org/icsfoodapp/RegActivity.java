package com.org.icsfoodapp;

import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.http.RequestFactory;
import com.fax.utils.task.GsonAsyncTask;
import com.fax.utils.view.TopBarContain;

import com.org.icsfoodapp.model.Response;
import com.org.icsfoodapp.pickavator.CommonChoosePicActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegActivity extends Activity {
	public static final String Extra_UserName="userName";
	public static final String Extra_UserPassword = "passWord";

	Bitmap avatorBitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final TopBarContain topBarContain = new TopBarContain(this).setTitle(R.string.login_sign_up)
		.setLeftFinish(null, R.drawable.topbar_ic_back)
		.setContentView(R.layout.activity_reg);
		setContentView(topBarContain);
		topBarContain.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!(v instanceof EditText)){
					 MyApp.hideKeyBoard(RegActivity.this.getApplicationContext(), topBarContain);
				}
				return false;
			}
		});
		final ImageView avator = (ImageView) findViewById(R.id.reg_avator);
		findViewById(R.id.reg_upload_group).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonChoosePicActivity.start(RegActivity.this, true, new CommonChoosePicActivity.ChoosePicListener() {
					@Override
					public void onChoosed(Activity activity, Bitmap bitmap) {
						activity.finish();
						avatorBitmap = BitmapManager.scaleToMiniBitmap(bitmap, 200, 200);
						avator.setImageBitmap(avatorBitmap);
					}
				});
			}
		});
		findViewById(R.id.reg_confirm_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String email = ((TextView)findViewById(R.id.reg_email)).getText().toString();
				if(TextUtils.isEmpty(email)){
					Toast.makeText(getApplication(), R.string.please_input_email, Toast.LENGTH_SHORT).show();
					return;
				}
				Pattern pattern = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
				if(!pattern.matcher(email).matches()){
					Toast.makeText(getApplication(), R.string.please_input_valid_email, Toast.LENGTH_SHORT).show();
					return;
				}
				String nickname = ((TextView)findViewById(R.id.reg_nickname)).getText().toString();
				if(TextUtils.isEmpty(nickname)){
					Toast.makeText(getApplication(), R.string.please_input_nickname, Toast.LENGTH_SHORT).show();
					return;
				}
				String mobile = ((TextView)findViewById(R.id.reg_mobile)).getText().toString();
				if(TextUtils.isEmpty(mobile)){
					Toast.makeText(getApplication(), R.string.please_input_mobile, Toast.LENGTH_SHORT).show();
					return;
				}
				pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
				if(!pattern.matcher(mobile).matches()){
					Toast.makeText(getApplication(), R.string.please_input_valid_mobile, Toast.LENGTH_SHORT).show();
					return;
				}
				
				final String password = ((TextView)findViewById(R.id.reg_password)).getText().toString();
				if(TextUtils.isEmpty(password)){
					Toast.makeText(getApplication(), R.string.please_input_password, Toast.LENGTH_SHORT).show();
					return;
				}
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if(avatorBitmap!=null){
					avatorBitmap.compress(CompressFormat.JPEG, 50, baos);
				}
				HttpRequestBase requestBase = RequestFactory.createPost(MyApp.ApiUrl+"Public/reg.json", 
						new BasicNameValuePair("username", email),
						new BasicNameValuePair("password", password),
						new BasicNameValuePair("nickname", nickname),
						new BasicNameValuePair("mobile", mobile),
						new BasicNameValuePair("head_image", byte2HexStr(baos.toByteArray()))
						);
				new GsonAsyncTask<Response>(RegActivity.this, requestBase) {
					@Override
					protected void onPostExecuteSuc(Response result) {
						regSuc(email, password);
					}
				}.setProgressDialog().execute();
			}
		});
		
	}
	
	/**注册成功*/
	private void regSuc(String userName, String passWord){
		setResult(RESULT_OK, new Intent().putExtra(Extra_UserName, userName)
					.putExtra(Extra_UserPassword, passWord));
		finish();
	}

	/*
     * 实现字节数组向十六进制的转换方法一
     */
    private static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }
}
