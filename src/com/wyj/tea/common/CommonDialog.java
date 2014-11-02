package com.wyj.tea.common;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class CommonDialog {
	private static ProgressDialog progressDialog ;
	/**
	 *显示进度条对话框
	 *context  上下文
	 *message  显示的消息
	 */
	public static void showprogressDialog(Context context,String message){
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(message);
		progressDialog.show();
	}
	
	/**
	 * 关闭进度条对话框
	 */
	public static void hideprogressDialog(){
		progressDialog.dismiss();
	}
	
	/**
	 * 确认对话框
	 * @param context
	 * @param message
	 * @param listener
	 */
	public static void confirm(Context context, String message, OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setPositiveButton("确定", listener);
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
}
