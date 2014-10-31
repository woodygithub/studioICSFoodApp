package com.org.icsfoodapp;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import com.fax.utils.http.RequestFactory;
import com.fax.utils.task.GsonAsyncTask;
import com.fax.utils.view.TopBarContain;
import com.org.icsfoodapp.model.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity {
	private static final int Request_Reg = 1;
	
	public static void start(Activity activity){
		activity.startActivity(new Intent().setClass(activity, LoginActivity.class));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final TopBarContain topBarContain = new TopBarContain(this).setTitle(R.string.login)
			.setLeftFinish(null, R.drawable.topbar_ic_back)
			.setContentView(R.layout.activity_login);
		setContentView(topBarContain);
		topBarContain.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!(v instanceof EditText)){
					 MyApp.hideKeyBoard(LoginActivity.this.getApplicationContext(), topBarContain);
				}
				return false;
			}
		});
		findViewById(R.id.login_sign_up).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(LoginActivity.this, RegActivity.class), Request_Reg);
			}
		});
		findViewById(R.id.login_confirm_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String userName = ((TextView)findViewById(R.id.login_name)).getText().toString();
				String passWord = ((TextView)findViewById(R.id.login_password)).getText().toString();
				if (userName.length()<1||passWord.length()<1) {
					Toast.makeText(LoginActivity.this, getString(R.string.please_input_emailpassword), Toast.LENGTH_LONG).show();
					return;
				}
				HttpRequestBase httpRequest = RequestFactory.createPost(MyApp.ApiUrl+"Public/login", 
						new BasicNameValuePair("username", userName),
						new BasicNameValuePair("password", passWord) );
				new GsonAsyncTask<User>(LoginActivity.this, httpRequest) {
					@Override
					protected void onPostExecuteSuc(User result) {
						if(result.isOk()){
							MyApp.saveLogedUser(result);
							setResult(RESULT_OK, new Intent().putExtra(User.class.getName(), result));
							finish();
						}
					}
				}.setProgressDialog().execute();
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			switch (requestCode) {
			case Request_Reg:
				((TextView)findViewById(R.id.login_name)).setText(data.getStringExtra(RegActivity.Extra_UserName));
				((TextView)findViewById(R.id.login_password)).setText(data.getStringExtra(RegActivity.Extra_UserPassword));
				findViewById(R.id.login_confirm_btn).performClick();
				break;
			}
		}
	}
	
}
