package com.wyj.tea.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wyj.tea.R;
import com.wyj.tea.entity.favorite.Favorite;

public class FavoriteAdapter extends BaseAdapter{

	private Context context;
	private List<Favorite> favorites;
	private ViewHolder viewHolder;
	public FavoriteAdapter(Context context, List<Favorite> favorites){
		this.context = context;
		this.favorites = favorites;
	}
	@Override
	public int getCount() {
		return favorites.size();
	}

	@Override
	public Object getItem(int position) {
		return favorites.get(position);
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
			viewHolder.tvCreateTime = (TextView) convertView.findViewById(R.id.tv_nickname);
			viewHolder.tvNickname = (TextView) convertView.findViewById(R.id.tv_source);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Favorite favorite = favorites.get(position);
		viewHolder.tvCreateTime.setText(favorite.getCreate_time());
		viewHolder.tvNickname.setText(favorite.getNickname());
		viewHolder.tvTitle.setText(favorite.getTitle());
		return convertView;
	}
	class ViewHolder {
		public TextView tvTitle;
		public TextView tvCreateTime;
		public TextView tvNickname;
	}

}
