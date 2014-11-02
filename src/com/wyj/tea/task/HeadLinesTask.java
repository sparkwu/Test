package com.wyj.tea.task;

import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.wyj.json.JsonUtils;
import com.wyj.tea.entity.main.HeadLines;

public class HeadLinesTask extends AsyncTask<String, Void, HeadLines> {
	private HeadLines mHeadLines;
	private ValueCallBack callBack;
	public HeadLinesTask( HeadLines mHeadLines, ValueCallBack callBack ) {
		this.mHeadLines=mHeadLines;
		this.callBack=callBack;
	}
	
	@Override
	protected HeadLines doInBackground(String... params) {
		final String json = JsonUtils.getJSON(params[0]);
	
		if(mHeadLines==null || mHeadLines.getData()==null ){
			mHeadLines = JSON.parseObject(json, HeadLines.class);
		}else{
			
			HeadLines newHeadline = JSON.parseObject(json, HeadLines.class);
			for(int i=0;i<newHeadline.getData().size();i++){
				mHeadLines.getData().add(newHeadline.getData().get(i));
			}
		}
		return mHeadLines;
	}
	@Override
	protected void onPostExecute(HeadLines result) {
		callBack.excute(result);
	}
	//回调接口
	public interface ValueCallBack{
		public void excute(HeadLines headLines);
	}
	
	

}



