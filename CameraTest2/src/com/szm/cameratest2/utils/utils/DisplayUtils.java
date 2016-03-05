package com.szm.cameratest2.utils.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class DisplayUtils {

	/**
	 * ��ȡ��Ļ��Ⱥ͸߶ȣ���λΪpx
	 * @param context
	 * @return
	 */
	public static Point getScreenMetrics(Context context){
		DisplayMetrics dm =context.getResources().getDisplayMetrics();
		int w_screen = dm.widthPixels;
		int h_screen = dm.heightPixels;
		return new Point(w_screen, h_screen);
	}
	
	/**
	 * ��ȡ��Ļ�����
	 * @param context
	 * @return
	 */
	public static float getScreenRate(Context context){
		Point P = getScreenMetrics(context);
		float H = P.y;
		float W = P.x;
		return (H/W);
	}
	
}
