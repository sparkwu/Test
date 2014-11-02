package com.wyj.tea.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wyj.tea.R;

public class GuideActivity extends Activity implements OnPageChangeListener{
	private ViewPager vpGuide;
	//装载点点的数组
	private ImageView[] tips;
	private ViewGroup group;
	private List<ImageView> listImages;
	private int[] guides = new int[]{R.drawable.slide1,R.drawable.slide2,R.drawable.slide3};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initUI();
		initData();
		initImagePoint();
	}

	private void initUI() {
		vpGuide = (ViewPager) findViewById(R.id.vp_guide);
		group = (ViewGroup) findViewById(R.id.viewGroup);
	}


	private void initImagePoint() {
		tips = new ImageView[guides.length];
		for (int i = 0; i < guides.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(10, 10));
			tips[i] = imageView;
			if(i==0){
				tips[i].setBackgroundResource(R.drawable.page_now);
			}else{
				tips[i].setBackgroundResource(R.drawable.page);
			}
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 5;
			layoutParams.rightMargin = 5;
			group.addView(imageView, layoutParams);
		}
	}

	private void initData() {
		listImages = new ArrayList<ImageView>();
		
		ImageView imageView = null;
		for (int i = 0; i < guides.length; i++) {
			imageView = new ImageView(this);
			imageView.setImageResource(guides[i]);
			listImages.add(imageView);
		}
		ImageView img = listImages.get(listImages.size()-1);
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(GuideActivity.this,MainActivity.class));
				finish();
			}
		});
		//为ViewPager赋值
		vpGuide.setAdapter(new MyPagerAdapter());
		//设置监听，主要是设置点点的背景
		vpGuide.setOnPageChangeListener(this);
		//设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
	//	vpGuide.setCurrentItem((listImages.size()) * 100);
	}
	/******************OnPageChangeListener*******************/
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		setImageBackground(arg0 % listImages.size());
	}
	/******************OnPageChangeListener*******************/
	/**
	 * 设置选中的tip的背景
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems){
		for(int i=0; i<tips.length; i++){
			if(i == selectItems){
				tips[i].setBackgroundResource(R.drawable.page_now);
			}else{
				tips[i].setBackgroundResource(R.drawable.page);
			}
		}
	}

	class MyPagerAdapter extends PagerAdapter{
		
		@Override
		public int getCount() {
			return listImages.size();
		}
		
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		//向list中加图片
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(listImages.get(position));
			return listImages.get(position);
		}
		//移除已经划过的图片
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(listImages.get(position));
		}
	}
}
