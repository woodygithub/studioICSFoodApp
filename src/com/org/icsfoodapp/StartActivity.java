package com.org.icsfoodapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		new Handler().postDelayed( new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(StartActivity.this, MainActivity.class));
			}
		}, 2000);
		
	}
}
