package com.wyj.tea.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.wyj.tea.R;

public class WelcomeActivity extends Activity{
	public static final String IS_FIRST = "isfirst";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		idFirst();
	}
	//判断是否是第一次进入
	private void idFirst() {
		//该数据会一直存在，直到项目被卸载
		final SharedPreferences sp = getSharedPreferences(IS_FIRST, MODE_PRIVATE);
		final boolean isFirst = sp.getBoolean(IS_FIRST, true);
		
		new CountDownTimer(2000,2000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				
			}
			
			@Override
			public void onFinish() {
				if(isFirst){
					SharedPreferences.Editor editor = sp.edit();
					editor.putBoolean(IS_FIRST, false);
					editor.commit();
					startActivity(new Intent(WelcomeActivity.this,GuideActivity.class));
				} else{
					startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
				}
				finish();
			}
			
		}.start();
	}
}
