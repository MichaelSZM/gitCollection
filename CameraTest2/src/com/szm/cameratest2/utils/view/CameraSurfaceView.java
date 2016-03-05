package com.szm.cameratest2.utils.view;

import com.szm.cameratest2.utils.control.CameraControlManager;
import com.szm.cameratest2.utils.utils.CameraPara;
import com.szm.cameratest2.utils.utils.DisplayUtils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
/**
 * Ԥ������
 * @author michael
 *
 */
public class CameraSurfaceView extends SurfaceView implements Callback{

	private CameraControlManager cameraControlManager;
	private CameraPara cameraPara;
	float ra;
	
	public CameraSurfaceView(Context context) {
		super(context);
		ra=DisplayUtils.getScreenRate(context);
		getHolder().setFormat(PixelFormat.TRANSPARENT);//����͸��
		getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//����surface�Ǹ��������͵�
		getHolder().addCallback(this);
		cameraControlManager=CameraControlManager.getInstance();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		cameraControlManager.doOpenCamera();
		cameraPara=new CameraPara(cameraControlManager.getCamera());
		cameraPara.setRates(ra);
		cameraPara.setValues();
		cameraControlManager.setParameters(cameraPara.getParameters());
		cameraControlManager.setPreviewDisplay(holder);
		cameraControlManager.startPreView();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		cameraControlManager.doStopCamera();
	}

	
	
}
