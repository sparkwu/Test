package com.wyj.tea.task;

import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.wyj.json.JsonUtils;
import com.wyj.tea.entity.details.Details;

public class DetailsTask extends AsyncTask<String, Void, Details>{
	private Details details;
	private ValueCallBack callBack;
	
	public DetailsTask(ValueCallBack callBack) {
		this.callBack = callBack;
	}
	@Override
	protected Details doInBackground(String... params) {
		String json = JsonUtils.getJSON(params[0]);
		details = JSON.parseObject(json, Details.class);
		return details;
	}
	@Override
	protected void onPostExecute(Details result) {
		callBack.excute(result);
	}
	//回调接口
	public interface ValueCallBack{
		public void excute(Details object);
	}
}
