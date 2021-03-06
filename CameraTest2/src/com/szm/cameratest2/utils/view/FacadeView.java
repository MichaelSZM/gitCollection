package com.szm.cameratest2.utils.view;

import com.szm.cameratest2.utils.control.CameraControlManager;
import com.szm.cameratest2.utils.utils.DisplayUtils;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.widget.FrameLayout;
/**
 * 对外提供的view（包裹surfaceView和MaskView）
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
	 * 设置透明区域宽高
	 * @param w
	 * @param h
	 */
	public void setTransParentRectWH(int w,int h){
		maskView.setcRectHeight(h).setcRectWidth(w);
		invalidate();
	}
	
	/**
	 * 拍摄(true 指定区域 false 全局)
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
