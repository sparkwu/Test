package com.wyj.tea.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.image.SmartImageView;
import com.wyj.tea.R;
import com.wyj.tea.activity.DetailsActivity;
import com.wyj.tea.adapter.HeadLinesAdapter;
import com.wyj.tea.db.DBHelper;
import com.wyj.tea.entity.main.Data;
import com.wyj.tea.entity.main.HeadLines;
import com.wyj.tea.task.HeadLinesTask;
import com.wyj.tea.task.HeadLinesTask.ValueCallBack;
import com.wyj.tea.task.ImageTask;

public class MainFragment extends Fragment implements OnScrollListener, OnItemClickListener{
	public static final String IMAGES = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getSlideshow";
	//头条数据路径
	public static final String HEADLINEPATH = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines&page=%s&rows=15";
	//数据库语句
	private static final String SQL_INSERT_TEA = "INSERT INTO table_tea(id, title, create_time, nickname,wap_thumb) VALUES(?, ?, ?, ?, ?)";
	private static final String SQL_QUERY_TEA_BY_ID = "SELECT * FROM table_tea WHERE id = ?";
	private HeadLines mHeadLines; //null
	private ListView lvHead;
	private HeadLinesAdapter adapter;
	
	// 加载时控件
	private boolean isLastRow = false;
	// 页数
	private int page = 0;
	private Bundle args;
	//ViewPager图片
	private FrameLayout flImage;
	private ViewPager vpImage;
	private ImageView[] tips;
	private ViewGroup group;
	private List<SmartImageView> imageViews;
	private DBHelper db;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		args = getArguments();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment, container, false);
		db = new DBHelper(getActivity());
		initUI(layout);
		initData();
		return layout;
	}

	private void initUI(View layout) {
		lvHead = (ListView) layout.findViewById(R.id.lv_main);
		lvHead.setOnItemClickListener(this);
		lvHead.setOnScrollListener(this);
		vpImage = (ViewPager) layout.findViewById(R.id.vp_image);
		group = (ViewGroup) layout.findViewById(R.id.vg_image);
		flImage = (FrameLayout) layout.findViewById(R.id.fl_image);
	}

	private void initTeaData() {
		for (int i = 0; i < mHeadLines.getData().size(); i++) {
			Data data = mHeadLines.getData().get(i);
			Cursor cursor = db.executeQuery(SQL_QUERY_TEA_BY_ID,data.getId());
			if (cursor.getCount() > 0) {
			} else {
				db.executeSQL(SQL_INSERT_TEA,data.getId(), data.getTitle(), data.getCreate_time(),data.getNickname(),data.getWap_thumb());
			}
			
		}
		
	}
	private void initData() {
		if(HEADLINEPATH.equals(args.getString("bundle"))){
			//为ViewPager赋值
			imageViews = new ArrayList<SmartImageView>();
			ImageTask imageTask = new ImageTask(getActivity(), imageViews,vpImage,tips,group);
			imageTask.execute(IMAGES);
			
		}else{
			LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) flImage.getLayoutParams(); // 取控件vpImage当前的布局参数
			linearParams.height = 0;// 当控件的高强制设成0象素
			flImage.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件vpImage
		}
		//为ListView赋值
		HeadLinesTask task = new HeadLinesTask(mHeadLines,new ValueCallBack() {
			
			@Override
			public void excute(HeadLines object) {
				mHeadLines =object;
				if(adapter == null){
					adapter = new HeadLinesAdapter(getActivity(), mHeadLines);
					lvHead.setAdapter(adapter);
				}else {
					adapter.notifyDataSetChanged();
				}
				initTeaData();
			}
		});
		task.execute(String.format(args.getString("bundle"), page));
	}
	/******************* OnItemClickListener *********************/
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Data data = mHeadLines.getData().get(position);
		Intent intent = new Intent(getActivity(),DetailsActivity.class);
		intent.putExtra("id",data.getId());
		startActivity(intent);
		
	}
	/******************* OnItemClickListener *********************/
	
	/******************* OnScrollListener *********************/
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 判断到达底部后滚动条是否停止
		if (scrollState == SCROLL_STATE_IDLE && isLastRow) {
			page++;
			HeadLinesTask task = new HeadLinesTask(mHeadLines,new ValueCallBack() {
				
				@Override
				public void excute(HeadLines object) {
					mHeadLines = object;
					if(adapter == null){
						adapter = new HeadLinesAdapter(getActivity(), mHeadLines);
						lvHead.setAdapter(adapter);
					}else {
						adapter.notifyDataSetChanged();
					}
					initTeaData();
				}
			});
			task.execute(String.format(args.getString("bundle"), page));
			// 设置底部标记状态为否
			isLastRow = false;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
		// 判断ListView是否到达底部
		if (firstVisibleItem + visibleItemCount == totalItemCount&& totalItemCount > 0) {
			isLastRow = true;
		}

	}
	/******************* OnScrollListener *********************/
	
}
