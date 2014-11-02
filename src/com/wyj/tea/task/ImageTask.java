package com.wyj.tea.task;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.wyj.json.JsonUtils;
import com.wyj.tea.R;
import com.wyj.tea.adapter.MyPagerAdapter;
import com.wyj.tea.entity.image.Data;
import com.wyj.tea.entity.image.Images;

public class ImageTask extends AsyncTask<String, Void, Images> implements OnPageChangeListener{

	private Context context;
	private List<SmartImageView> imageViews;
	private Images image = new Images();
	private ViewPager vpImage;
	private ImageView[] tips;
	private ViewGroup group;

	public ImageTask(Context context, List<SmartImageView> imageViews,
			ViewPager vpImage, ImageView[] tips, ViewGroup group) {
		super();
		this.context = context;
		this.imageViews = imageViews;
		this.vpImage = vpImage;
		this.tips = tips;
		this.group = group;
	}

	@Override
	protected Images doInBackground(String... params) {
		String json = JsonUtils.getJSON(params[0]);
		image = JSON.parseObject(json, Images.class);
		return image;
	}
	
	@Override
	protected void onPostExecute(Images result) {
		for (int i = 0; i < result.getData().size(); i++) {
			Data data = result.getData().get(i);
			SmartImageView imageView = new SmartImageView(context);
			imageView.setImageUrl(data.getImage_s());
			imageViews.add(imageView);
		}
		initImagePoint();
		//为ViewPager赋值
		vpImage.setAdapter(new MyPagerAdapter(imageViews));
		//设置监听事件，主要是为了点点的背景
		vpImage.setOnPageChangeListener(this);
	}

	private void initImagePoint() {
		tips = new ImageView[imageViews.size()];
		for (int i = 0; i < imageViews.size(); i++) {
			ImageView imageView = new ImageView(context);
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

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		setImageBackground(arg0 % imageViews.size());
	}
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

}
