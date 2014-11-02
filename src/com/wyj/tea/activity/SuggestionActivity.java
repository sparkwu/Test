package com.wyj.tea.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wyj.tea.R;

public class SuggestionActivity extends Activity implements OnClickListener{
	private ImageButton btnSubmit;
	//跳转按钮
	private Button btnReturn;
	private Button btnHome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_suggestion);
		initUI();
	}
	private void initUI() {
		btnHome = (Button) findViewById(R.id.btn_suggestion_home);
		btnHome.setOnClickListener(this);
		btnReturn = (Button) findViewById(R.id.btn_suggestion_return);
		btnReturn.setOnClickListener(this);
		
		btnSubmit =  (ImageButton) findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			Toast.makeText(this, "提交成功", Toast.LENGTH_LONG).show();
			startActivity(new Intent(this,ChaBaiKeActivity.class));
			break;
		case R.id.btn_suggestion_return:
			finish();
			break;
		case R.id.btn_suggestion_home:
			startActivity(new Intent(this,MainActivity.class));
			break;
		default:
			break;
		}
		
	}
}
