package com.wyj.tea.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;
import com.wyj.tea.R;
import com.wyj.tea.common.CommonDialog;
import com.wyj.tea.db.DBHelper;
import com.wyj.tea.entity.details.Details;
import com.wyj.tea.task.DetailsTask;
import com.wyj.tea.task.DetailsTask.ValueCallBack;

public class DetailsActivity extends Activity implements OnClickListener {
	private static final String URL = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent&id=%s";
	private static final String SQL_INSERT_COLLECT = "INSERT INTO table_collect(id, title, create_time, nickname) VALUES(?, ?, ?, ?)";
	private static final String SQL_QUERY_COLLECT_BY_ID = "SELECT * FROM table_collect WHERE id = ?";
	private static final String SQL_QUERY_HISTORY_BY_ID = "SELECT * FROM table_history WHERE id = ?";
	private static final String SQL_INSERT_HISTORY = "INSERT INTO table_history(id, title, create_time, nickname) VALUES(?, ?, ?, ?)";
	private ViewHolder viewHolder;
	private Details details;
	private Button btnReturn;
	private Button btnShare;
	private Button btnCollect;
	private WebView webView;
	private DBHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 实例化数据库
		db = new DBHelper(DetailsActivity.this);
		setContentView(R.layout.activity_details);
		initUI();
		initData();
	}
	
	private void initHistoryData() {
		Cursor cursor = db.executeQuery(SQL_QUERY_HISTORY_BY_ID,details.getData().getId());
		if (cursor.getCount() > 0) {
		} else {
			db.executeSQL(SQL_INSERT_HISTORY,details.getData().getId(), details.getData().getTitle(), details.getData().getCreate_time(),details.getData().getAuthor());
		}
		
	}

	private void initUI() {
		btnReturn = (Button) findViewById(R.id.btn_return);
		btnReturn.setOnClickListener(this);
		btnShare = (Button) findViewById(R.id.btn_share);
		btnShare.setOnClickListener(this);
		btnCollect = (Button) findViewById(R.id.btn_collect);
		btnCollect.setOnClickListener(this);
		webView = (WebView) findViewById(R.id.wv_wap_content);
		WebSettings bs = webView.getSettings();
		bs.setBuiltInZoomControls(true);

		viewHolder = new ViewHolder();
		viewHolder.tvCreateTime = (TextView) findViewById(R.id.tv_create_time);
		viewHolder.tvAuthor = (TextView) findViewById(R.id.tv_author);
		viewHolder.tvSource = (TextView) findViewById(R.id.tv_source);
		viewHolder.tvTitle = (TextView) findViewById(R.id.tv_title);
	}

	private void initData() {
		Intent intent = getIntent();
		String id = intent.getStringExtra("id");
		DetailsTask task = new DetailsTask(new ValueCallBack() {
			@Override
			public void excute(Details object) {
				details = object;	
				viewHolder.tvCreateTime.setText(details.getData().getCreate_time());
				viewHolder.tvAuthor.setText(details.getData().getAuthor());
				viewHolder.tvTitle.setText(details.getData().getTitle());
				webView.loadDataWithBaseURL(null,details.getData().getWap_content(), "text/html", "UTF-8",null);
				//存到历史记录中
				initHistoryData();
			}
		});
		task.execute(String.format(URL, id));
		
	}
	
	class ViewHolder {
		public TextView tvTitle;
		public TextView tvSource;
		public TextView tvCreateTime;
		public TextView tvAuthor;
		public TextView tvWapContent;
		public SmartImageView imgWeiboUrl;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_return:
			finish();
			break;
		case R.id.btn_collect:
			collect();
			break;
		case R.id.btn_share:
			startActivity(new Intent(this,ShareActivity.class));
			break;
		default:
			break;
		}

	}

	private void collect() {

		CommonDialog.confirm(this, "是否收藏该记录？",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Cursor cursor = db.executeQuery(SQL_QUERY_COLLECT_BY_ID,details.getData().getId());
						if (cursor.getCount() > 0) {
							Toast.makeText(DetailsActivity.this, "数据已存在",Toast.LENGTH_LONG).show();
						} else {
							db.executeSQL(SQL_INSERT_COLLECT,details.getData().getId(), details.getData().getTitle(), details.getData().getCreate_time(),details.getData().getAuthor());
							Toast.makeText(DetailsActivity.this, "收藏成功",Toast.LENGTH_LONG).show();
						}
					}
	});
	}
}
