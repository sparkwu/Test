package com.wyj.tea.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyj.tea.R;

public class ChaBaiKeActivity extends Activity implements OnClickListener{
	private Button btnReturn;
	private Button btnHome;
	private TextView tvCollect;
	private TextView tvHistory;
	private TextView tvCopyright;
	private ImageView imgSearch;
	private EditText etSearch;
	private TextView tvSearchTea;
	private TextView tvSuggestion;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chabaike);
		initUI();
	}
	private void initUI() {
		btnHome = (Button) findViewById(R.id.btn_home);
		btnHome.setOnClickListener(this);
		btnReturn = (Button) findViewById(R.id.btn_return);
		btnReturn.setOnClickListener(this);
		tvCollect = (TextView) findViewById(R.id.tv_collect);
		tvCollect.setOnClickListener(this);
		tvHistory = (TextView) findViewById(R.id.tv_history);
		tvHistory.setOnClickListener(this);
		tvCopyright = (TextView) findViewById(R.id.tv_copyright);
		tvCopyright.setOnClickListener(this);
		tvSuggestion = (TextView) findViewById(R.id.tv_suggestion);
		tvSuggestion.setOnClickListener(this);
		//ËÑË÷
		imgSearch = (ImageView) findViewById(R.id.img_search);
		imgSearch.setOnClickListener(this);
		etSearch = (EditText) findViewById(R.id.et_searchEdit);
		
		tvSearchTea = (TextView) findViewById(R.id.tv_serachtea);
		tvSearchTea.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_return:
			finish();
			break;
		case R.id.btn_home:
			startActivity(new Intent(this,MainActivity.class));
			break;
		case R.id.tv_collect:
			startActivity(new Intent(this,FavoriteActivity.class));
			break;
		case R.id.tv_history:
			startActivity(new Intent(this,HistoryActivity.class));
			break;
		case R.id.tv_copyright:
			startActivity(new Intent(this,CopyrightActivity.class));
			break;
		case R.id.tv_suggestion:
			startActivity(new Intent(this,SuggestionActivity.class));
			break;
		case R.id.img_search:
			String searchKey = etSearch.getText().toString();
			if(searchKey.length()>0){
				Intent intent = new Intent(this, SearchActivity.class);
				intent.putExtra("key", searchKey);
				startActivity(intent);
			}
			break;
		case R.id.tv_serachtea:
			etSearch.setText(null);
			Intent intent = new Intent(this, SearchActivity.class);
			intent.putExtra("key", "²è");
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}
	
}
