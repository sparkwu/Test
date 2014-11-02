package com.wyj.tea.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wyj.tea.R;
import com.wyj.tea.adapter.HeadLinesAdapter;
import com.wyj.tea.entity.main.Data;
import com.wyj.tea.entity.main.HeadLines;
import com.wyj.tea.task.HeadLinesTask;
import com.wyj.tea.task.HeadLinesTask.ValueCallBack;

public class SearchActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	public static final String SEARCHPATH = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.searcListByTitle&page=%s&rows=10&search=%s";
	// 跳转按钮
	private Button btnReturn;
	private Button btnHome;
	// 头部赋值文本框
	private TextView tvHead;
	private LinearLayout linearNoSearch;

	private ListView lvSearch;
	private HeadLines mHeadLines = null;
	private HeadLinesAdapter adapter;
	// 页数
	private int page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);
		initUI();
		initText();
		initData();
	}

	private void initText() {
		LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) linearNoSearch.getLayoutParams(); // 取控件vpImage当前的布局参数
		linearParams.height = 0;// 当控件的高强制设成0象素
		linearNoSearch.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件vpImage
		
	}

	private void initUI() {
		btnHome = (Button) findViewById(R.id.btn_home);
		btnHome.setOnClickListener(this);
		btnReturn = (Button) findViewById(R.id.btn_return);
		btnReturn.setOnClickListener(this);
		lvSearch = (ListView) findViewById(R.id.lv_search);
		lvSearch.setOnItemClickListener(this);

		tvHead = (TextView) findViewById(R.id.tv_search_head);
		linearNoSearch = (LinearLayout) findViewById(R.id.linear_no_search);
	}

	private void initData() {
		Intent intent = getIntent();
		String searchKey = intent.getStringExtra("key");
		tvHead.setText(searchKey);
		HeadLinesTask task = new HeadLinesTask(mHeadLines, new ValueCallBack() {
			@Override
			public void excute(HeadLines object) {
				mHeadLines = object;
				if (mHeadLines.getData().size() != 0) {
					adapter = new HeadLinesAdapter(SearchActivity.this,mHeadLines);
					lvSearch.setAdapter(adapter);
				} else {
					LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) linearNoSearch.getLayoutParams(); // 取控件vpImage当前的布局参数
					linearParams.height = 100;// 当控件的高强制设成0象素
					linearNoSearch.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件vpImage
				}
			}
		});
		task.execute(String.format(SEARCHPATH, page, searchKey));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_return:
			finish();
			break;
		case R.id.btn_home:
			startActivity(new Intent(this, MainActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Data data = mHeadLines.getData().get(position);
		Intent intent = new Intent(this, DetailsActivity.class);
		intent.putExtra("id", data.getId());
		startActivity(intent);
	}
}
