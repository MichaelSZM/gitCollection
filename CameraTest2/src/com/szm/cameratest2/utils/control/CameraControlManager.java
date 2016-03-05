package com.szm.cameratest2.utils.control;

import java.io.IOException;

import com.szm.cameratest2.utils.utils.FileUtil;
import com.szm.cameratest2.utils.utils.ImageUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * ����ͷ���ƹ�����
 * ���򿪣�����Ԥ����
 * @author michael
 *
 */
public class CameraControlManager {

	/**����ͷ����*/
	private Camera mCamera;
	
	
	private static CameraControlManager cameraControlManager;
	/**�Ƿ����Ԥ��״̬*/
	private boolean isPreviewing;
	
	private CameraControlManager(){
		
	}
	
	public static CameraControlManager getInstance(){
		if(cameraControlManager==null){
			synchronized (CameraControlManager.class) {
				if(cameraControlManager==null){
					cameraControlManager=new CameraControlManager();
				}
			}
		}
		return cameraControlManager;
	}
	
	/**
	 * ��������ͷ
	 */
	public void doOpenCamera(){
		mCamera=Camera.open();
	}
	
	/**
	 * ���ò���
	 * @param parameters
	 */
	public void setParameters(Camera.Parameters parameters){
		mCamera.setParameters(parameters);
	}
	
	/**
	 * ��ʼԤ��
	 */
	public void startPreView(){
		mCamera.setDisplayOrientation(90);
		mCamera.startPreview();
		isPreviewing=true;
	}
	
	/**
	 * ��ȡandroid.hardware.Camera����
	 * @return
	 */
	public android.hardware.Camera getCamera(){
		return mCamera;
	}

	/**
	 * ֹͣ���
	 */
	public void doStopCamera() {
		mCamera.stopPreview();
		isPreviewing=false;
		mCamera.release();
		mCamera=null;
	}

	/**
	 * ��surface������ͷ
	 * @param holder
	 */
	public void setPreviewDisplay(SurfaceHolder holder) {
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
			mCamera.release();
		}
	}
	
	/**
	 * ��ͨ����
	 */
	public void doTakePicture(){
		if(isPreviewing&&mCamera!=null){
			mCamera.takePicture(mShutterCallback, null, mJpegPictureCallback);
		}
	}
	
	int DST_RECT_WIDTH, DST_RECT_HEIGHT,SCREEN_WIDTH,SCREEN_HEIGHT;
	/**
	 * ����ָ�����򷽷�
	 * @param w
	 * @param h
	 */
	public void doTakePicture(int w, int h,int screenW,int screenH){
		if(isPreviewing && (mCamera != null)){
			Log.i("szm--", "�������ճߴ�:width = " + w + " h = " + h);
			DST_RECT_WIDTH = w;
			DST_RECT_HEIGHT = h;
			SCREEN_WIDTH=screenW;
			SCREEN_HEIGHT=screenH;
			mCamera.takePicture(mShutterCallback, null, mRectJpegPictureCallback);
		}
	}
	
	/*Ϊ��ʵ�����յĿ������������ձ�����Ƭ��Ҫ���������ص�����*/
	ShutterCallback mShutterCallback = new ShutterCallback() 
	//���Ű��µĻص������������ǿ����������Ʋ��š����ꡱ��֮��Ĳ�����Ĭ�ϵľ������ꡣ
	{
		public void onShutter() {
			// ���ÿ�����
		}
	};
	
	/**
	 * ��������
	 */
	PictureCallback mJpegPictureCallback = new PictureCallback() 
	//��jpegͼ�����ݵĻص�,����Ҫ��һ���ص�
	{
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.i("szm--", "myJpegCallback:onPictureTaken...");
			Bitmap b = null;
			if(null != data){
				b = BitmapFactory.decodeByteArray(data, 0, data.length);//data���ֽ����ݣ����������λͼ
				mCamera.stopPreview();
				isPreviewing = false;
			}
			//����ͼƬ��sdcard
			if(null != b)
			{
				FileUtil.saveBitmap(b);
			}
			//�ٴν���Ԥ��
			mCamera.startPreview();
			isPreviewing = true;
		}
	};

	/**
	 * ����ָ�������Rect
	 */
	PictureCallback mRectJpegPictureCallback = new PictureCallback() 
	//��jpegͼ�����ݵĻص�,����Ҫ��һ���ص�
	{
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.i("szm--", "myJpegCallback:onPictureTaken...");
			Bitmap b = null;
			if(null != data){
				b = BitmapFactory.decodeByteArray(data, 0, data.length);//data���ֽ����ݣ����������λͼ
				mCamera.stopPreview();
				isPreviewing = false;
			}
			//����ͼƬ��sdcard
			if(null != b)
			{
				int scalW=DST_RECT_WIDTH*b.getWidth()/SCREEN_WIDTH;
				int scalH=DST_RECT_HEIGHT*b.getHeight()/SCREEN_HEIGHT;
				int x = (b.getWidth() - scalW)/2;
				int y = (b.getHeight()-scalH)/2;
				Log.i("szm--", "---x=="+x+"---y=="+y);
				Log.i("szm--", "b.getWidth() = " + b.getWidth()
						+ " b.getHeight() = " + b.getHeight());
				Bitmap rectBitmap = Bitmap.createBitmap(b, x, y, scalW, scalH);
				FileUtil.saveBitmap(rectBitmap);
				if(b.isRecycled()){
					b.recycle();
					b = null;
				}
				if(rectBitmap.isRecycled()){
					rectBitmap.recycle();
					rectBitmap = null;
				}
			}
			//�ٴν���Ԥ��
			mCamera.startPreview();
			isPreviewing = true;
			if(!b.isRecycled()){
				b.recycle();
				b = null;
			}

		}
	};
}
