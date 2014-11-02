package com.wyj.tea.task;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.ListView;

import com.wyj.tea.adapter.FavoriteAdapter;
import com.wyj.tea.db.DBHelper;
import com.wyj.tea.entity.favorite.Favorite;

public class FavoriteTask extends AsyncTask<Void, Void, List<Favorite>> {

	private Context context;
	private ListView lvFavorite;
	private DBHelper db;
	private List<Favorite> favorites;
	private FavoriteAdapter adapter;
	private String SQL_QUERY_TEA;

	public FavoriteTask(Context context, ListView lvFavorite,
			List<Favorite> favorites,String SQL_QUERY_TEA) {
		this.context = context;
		this.lvFavorite = lvFavorite;
		this.favorites = favorites;
		this.SQL_QUERY_TEA = SQL_QUERY_TEA;
		// ʵ�������ݿ�
		db = new DBHelper(context);
	}

	@Override
	protected List<Favorite> doInBackground(Void... params) {
		Favorite favorite = null;
		// �α꣬���ڷ������ݿ��е�ÿ������
		Cursor cursor = db.executeQuery(SQL_QUERY_TEA);
		// ������ѯ�����ݣ�����оͷ���true
		while (cursor.moveToNext()) {
			favorite = new Favorite();
			// ͨ����Ӧ������ѯ��Ӧ��ֵ
			favorite.setId(cursor.getString(cursor.getColumnIndex("id")));
			favorite.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			favorite.setCreate_time(cursor.getString(cursor.getColumnIndex("create_time")));
			favorite.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
			favorites.add(favorite);
		}
		return favorites;
	}
	@Override
	protected void onPostExecute(List<Favorite> result) {
		adapter = new FavoriteAdapter(context, result);
		lvFavorite.setAdapter(adapter);
	}

}
