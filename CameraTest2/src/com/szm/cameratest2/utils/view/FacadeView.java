package com.szm.cameratest2.utils.view;

import com.szm.cameratest2.utils.control.CameraControlManager;
import com.szm.cameratest2.utils.utils.DisplayUtils;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.widget.FrameLayout;
/**
 * �����ṩ��view������surfaceView��MaskView��
 * @author michael
 *
 */
public class FacadeView extends FrameLayout {

	private CameraSurfaceView cameraSurfaceView;
	private MaskView maskView;
	private int transparentWidth,transparentHeight;
	private int screenW,screenH;
	
	public FacadeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Point point=DisplayUtils.getScreenMetrics(context);
		screenW=point.x;
		screenH=point.y;
		cameraSurfaceView=new CameraSurfaceView(context);
		maskView=new MaskView(context);
		LayoutParams params=new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		addView(cameraSurfaceView, params);
		addView(maskView, params);
	}

	/**
	 * ����͸��������
	 * @param w
	 * @param h
	 */
	public void setTransParentRectWH(int w,int h){
		maskView.setcRectHeight(h).setcRectWidth(w);
		invalidate();
	}
	
	/**
	 * ����(true ָ������ false ȫ��)
	 * @param flag
	 */
	public void takeRectPicture(boolean flag){
		if(flag){
			transparentWidth=maskView.getcRectWidth();
			transparentHeight=maskView.getcRectHeight();
			CameraControlManager.getInstance().doTakePicture(transparentWidth,transparentHeight,screenW,screenH);
		}else{
			CameraControlManager.getInstance().doTakePicture();
		}
	}
	
}
