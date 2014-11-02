package com.wyj.tea.common;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class CommonDialog {
	private static ProgressDialog progressDialog ;
	/**
	 *��ʾ�������Ի���
	 *context  ������
	 *message  ��ʾ����Ϣ
	 */
	public static void showprogressDialog(Context context,String message){
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(message);
		progressDialog.show();
	}
	
	/**
	 * �رս������Ի���
	 */
	public static void hideprogressDialog(){
		progressDialog.dismiss();
	}
	
	/**
	 * ȷ�϶Ի���
	 * @param context
	 * @param message
	 * @param listener
	 */
	public static void confirm(Context context, String message, OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setPositiveButton("ȷ��", listener);
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
}
