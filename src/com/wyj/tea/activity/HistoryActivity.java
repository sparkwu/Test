package com.wyj.tea.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.wyj.tea.R;
import com.wyj.tea.entity.favorite.Favorite;
import com.wyj.tea.task.FavoriteTask;

public class HistoryActivity extends Activity implements OnItemClickListener,OnClickListener{
	private static final String SQL_QUERY_TEA = "SELECT * FROM table_history";
	private ListView lvHistory;
	private List<Favorite> favorites;
	// œ‡πÿ∞¥≈•
	private Button btnReturn;
	private Button btnHome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_history);
		initUI();
		initData();
	}
	private void initUI() {
		lvHistory = (ListView) findViewById(R.id.lv_history);
		lvHistory.setOnItemClickListener(this);
		
		btnHome = (Button) findViewById(R.id.btn_history_home);
		btnHome.setOnClickListener(this);
		btnReturn = (Button) findViewById(R.id.btn_history_return);
		btnReturn.setOnClickListener(this);
	}
	private void initData() {
		favorites = new ArrayList<Favorite>();
		FavoriteTask task = new FavoriteTask(this, lvHistory, favorites, SQL_QUERY_TEA);
		task.execute();
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_history_return:
			finish();
			break;
		case R.id.btn_history_home:
			startActivity(new Intent(this,MainActivity.class));
			break;
		default:
			break;
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		Favorite favorite = favorites.get(position);
		Intent intent = new Intent(this,DetailsActivity.class);
		intent.putExtra("id", favorite.getId());
		startActivity(intent);
		
	}
}
