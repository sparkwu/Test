package com.wyj.tea.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.wyj.tea.R;
import com.wyj.tea.entity.main.Data;
import com.wyj.tea.entity.main.HeadLines;

public class HeadLinesAdapter extends BaseAdapter{

	private HeadLines headLines;
	private Context context;
	
	private ViewHolder viewHolder;
	public HeadLinesAdapter(Context context, HeadLines headLines){
		this.context = context;
		this.headLines = headLines;
	}
	@Override
	public int getCount() {
		return headLines.getData().size();
	}

	@Override
	public Object getItem(int position) {
		return headLines.getData().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.list_fragment, parent,false);
			
			viewHolder = new ViewHolder();
			viewHolder.imgWapThumb = (SmartImageView) convertView.findViewById(R.id.img_wap_thumb);
			viewHolder.tvCreateTime = (TextView) convertView.findViewById(R.id.tv_create_time);
			viewHolder.tvNickname = (TextView) convertView.findViewById(R.id.tv_nickname);
			viewHolder.tvSource = (TextView) convertView.findViewById(R.id.tv_source);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Data data = headLines.getData().get(position);
		//将图片放入空间中
		viewHolder.imgWapThumb.setImageUrl(data.getWap_thumb());
		viewHolder.tvCreateTime.setText(data.getCreate_time());
		viewHolder.tvNickname.setText(data.getNickname());
		viewHolder.tvSource.setText(data.getSource());
		viewHolder.tvTitle.setText(data.getTitle());
		return convertView;
	}
	class ViewHolder {
		public TextView tvTitle;
		public TextView tvSource;
		public TextView tvCreateTime;
		public TextView tvNickname;
		public TextView tvDescription;
		public SmartImageView imgWapThumb;
	}

}
