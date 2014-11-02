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
	//ͷ������·��
	public static final String HEADLINEPATH = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines&page=%s&rows=15";
	//���ݿ����
	private static final String SQL_INSERT_TEA = "INSERT INTO table_tea(id, title, create_time, nickname,wap_thumb) VALUES(?, ?, ?, ?, ?)";
	private static final String SQL_QUERY_TEA_BY_ID = "SELECT * FROM table_tea WHERE id = ?";
	private HeadLines mHeadLines; //null
	private ListView lvHead;
	private HeadLinesAdapter adapter;
	
	// ����ʱ�ؼ�
	private boolean isLastRow = false;
	// ҳ��
	private int page = 0;
	private Bundle args;
	//ViewPagerͼƬ
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
			//ΪViewPager��ֵ
			imageViews = new ArrayList<SmartImageView>();
			ImageTask imageTask = new ImageTask(getActivity(), imageViews,vpImage,tips,group);
			imageTask.execute(IMAGES);
			
		}else{
			LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) flImage.getLayoutParams(); // ȡ�ؼ�vpImage��ǰ�Ĳ��ֲ���
			linearParams.height = 0;// ���ؼ��ĸ�ǿ�����0����
			flImage.setLayoutParams(linearParams); // ʹ���úõĲ��ֲ���Ӧ�õ��ؼ�vpImage
		}
		//ΪListView��ֵ
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
		// �жϵ���ײ���������Ƿ�ֹͣ
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
			// ���õײ����״̬Ϊ��
			isLastRow = false;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
		// �ж�ListView�Ƿ񵽴�ײ�
		if (firstVisibleItem + visibleItemCount == totalItemCount&& totalItemCount > 0) {
			isLastRow = true;
		}

	}
	/******************* OnScrollListener *********************/
	
}
