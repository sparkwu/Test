package com.wyj.tea.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.wyj.tea.R;
import com.wyj.tea.fragment.MainFragment;

public class MainActivity extends FragmentActivity implements TabListener, OnPageChangeListener{

	//ͷ������·��
	public static final String HEADLINEPATH = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines&page=%s&rows=15";
	// ������ҳ
	public static final String NEWCONTENTPATH = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent";
	//�ٿ�	
	public static final String BAIKEPATH = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&rows=15&page=%s&type=16";
	//��Ѷ
	public static final String INFORMATIONPATH = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&rows=15&page=%s&type=52";
	//��Ӫ
	public static final String OPERATE = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&rows=15&page=%s&type=53";
	//����
	public static final String DATAPAGER  = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&rows=15&page=%s&type=54";
	
	private ActionBar actionBar;
	private ViewPager viewPager;
	private List<MainFragment> fragments;
	private String[] path = new String[]{HEADLINEPATH,BAIKEPATH, INFORMATIONPATH, OPERATE, DATAPAGER};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
		initTabs();
		initViewPager();
	}

	private void initUI() {
		viewPager = (ViewPager) findViewById(R.id.vp_pager);
		viewPager.setOnPageChangeListener(this);
	}

	private void initTabs() {
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		//����TableHost��Ч��(����������ֵ)
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		actionBar.addTab(actionBar.newTab().setText("ͷ��").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("�ٿ�").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("��Ѷ").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("��Ӫ").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("����").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.more).setTabListener(new TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				startActivity(new Intent(MainActivity.this,ChaBaiKeActivity.class));
				
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		}));
		
	}

	private void initViewPager() {
		fragments = new ArrayList<MainFragment>();
		Bundle args = null;
		MainFragment fragment = null;
		for (int i = 0; i < getActionBar().getTabCount(); i++) {
			if(i==getActionBar().getTabCount()-1){
				
			} else {
				fragment = new MainFragment();
				args = new Bundle();
				args.putString("bundle", path[i]);
				fragment.setArguments(args);
				fragments.add(fragment);
			}
		}
		viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
	}
	/*****************TabListener******************/
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}
	/*****************TabListener******************/
	
	class MyFragmentAdapter extends FragmentPagerAdapter{

		public MyFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}
	}
	/*****************OnPageChangeListener******************/
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		getActionBar().setSelectedNavigationItem(arg0);
	}
	/*****************OnPageChangeListener******************/
}
