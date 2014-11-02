package com.wyj.tea.activity;

import com.wyj.tea.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class CopyrightActivity extends Activity implements OnClickListener{
	// ��ذ�ť
	private Button btnReturn;
	private Button btnHome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_copyright);
		initUI();
	}

	private void initUI() {
		btnHome = (Button) findViewById(R.id.btn_copyright_home);
		btnHome.setOnClickListener(this);
		btnReturn = (Button) findViewById(R.id.btn_copyright_return);
		btnReturn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_copyright_home:
			startActivity(new Intent(this,MainActivity.class));
			break;
		case R.id.btn_copyright_return:
			finish();
			break;
		default:
			break;
		}
	}
}
