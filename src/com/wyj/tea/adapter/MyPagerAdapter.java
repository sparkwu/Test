package com.wyj.tea.adapter;

import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends PagerAdapter{
	
	private List<SmartImageView> imageViews;
	
	public MyPagerAdapter(List<SmartImageView> imageViews) {
		this.imageViews = imageViews;
	}

	@Override
	public int getCount() {
		return imageViews.size();
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	//��list�м�ͼƬ
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(imageViews.get(position));
		return imageViews.get(position);
	}
	//�Ƴ��Ѿ�������ͼƬ
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(imageViews.get(position));
	}
}

