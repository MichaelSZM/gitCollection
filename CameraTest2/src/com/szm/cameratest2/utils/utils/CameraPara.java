package com.szm.cameratest2.utils.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;

/**
 * ����ͷ��һЩ��������
 * 
 * @author michael
 *
 */
public class CameraPara {

	/**�����еĿ��*/
	private int paraWidth = 0;
	/**���������õĸ߶�*/
	private int paraHeight = 0;
	//Ԥ���������
	/**
	 * ��ȡ��Ļ�����
	 */
	private float rates=1;
	/**
	 * �Ƚ�������������
	 */
	private CameraSizeComparator sizeComparator=new CameraSizeComparator();
	private Camera.Parameters parameters;

	public CameraPara(Camera camera) {
		this.parameters = camera.getParameters();
	}
	
	/**
	 * ������ͷ��������ֵ
	 */
	public void setValues(){
		parameters.setPictureFormat(PixelFormat.JPEG);//�������պ�洢��ͼƬ��ʽ
//		setColorEffect();
		setPictureSizes();
		setPreviewSizes();
		setFocusModes();
		parameters.setRotation(90);
	}
	
	public void setRates(float ra){
		this.rates=ra;
	}
	
	/**
	 * ��ȡ���ò���
	 * @return
	 */
	public Parameters getParameters(){
		return parameters;
	}
	
	/**
	 * ����ColorEffects
	 */
	private void setColorEffect() {
		List<String> colorEffects = parameters.getSupportedColorEffects();
		Iterator<String> iterator = colorEffects.iterator();
		while (iterator.hasNext()) {
			String currentEffect = iterator.next();
			Log.i("szm--", "surport:--" + currentEffect);
			if (currentEffect.equals(Camera.Parameters.EFFECT_SOLARIZE)) {// �ع�
				parameters.setColorEffect(currentEffect);
				break;
			}
		}
	}

	/**
	 * ����PictureSizes
	 */
	private void setPictureSizes(){
		List<Size> list= parameters.getSupportedPictureSizes();
		Collections.sort(list, sizeComparator);
		int i = 0;
		for(Size s:list){
			if((s.height >= 800) && equalRate(s, rates)){
				break;
			}
			i++;
		}
		if(i == list.size()){
			i = 0;//���û�ҵ�����ѡ��С��size
		}
		parameters.setPictureSize(list.get(i).width, list.get(i).height);
	}
	
	/**
	 * �жϱ����Ƿ����
	 * @param s
	 * @param rate
	 * @return
	 */
	private  boolean equalRate(Size s, float rate){
		float r = (float)(s.width)/(float)(s.height);
		if(Math.abs(r - rate) <= 0.03)
		{
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * ����Ԥ���ߴ��С
	 */
	private void setPreviewSizes(){
		List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
		Collections.sort(sizes, sizeComparator);
		int i = 0;
		for(Size s:sizes){
			if((s.height >= 800) && equalRate(s, rates)){
				break;
			}
			i++;
		}
		if(i == sizes.size()){
			i = 0;//���û�ҵ�����ѡ��С��size
		}
		parameters.setPreviewSize(sizes.get(i).width, sizes.get(i).height);
	}
	
	/**
	 * ���þ۽�ģʽ
	 */
	private void setFocusModes(){
		List<String> focusModes = parameters.getSupportedFocusModes();
		if(focusModes.contains("continuous-video")){
			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
		}
	}
	
//	/**
//	 * ����Ԥ���ߴ��С
//	 */
//	private void setPreviewSizes1() {
//		// ����Ԥ���Ĵ�С
//		int bestWidth = 0, bestHeight = 0;
//		// ��ȡ֧�ֵĳߴ��б�
//		List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
//		if (sizes.size() > 1) {
//			Iterator<Camera.Size> iterator_size = sizes.iterator();
//			// ѭ��������ѡ����ӽ��ڴ�ֵ���Ǹ�
//			while (iterator_size.hasNext()) {
//				Camera.Size current_size = iterator_size.next();
//				if (current_size.width > bestWidth && current_size.width < LARGEST_WIDTH
//						&& current_size.height > bestHeight && current_size.height < LARGEST_HEIGHT) {
//					bestWidth = current_size.width;
//					bestHeight = current_size.height;
//				}
//			}
//		}
//		if (bestWidth != 0 && bestHeight != 0) {// �ҵ��˺��ʵĳߴ磬���óߴ磬���Ҹ���surface(Ԥ���ؼ�)�Ըô�С��ʾ
//			parameters.setPreviewSize(bestWidth, bestHeight);
//			paraWidth=bestWidth;
//			paraHeight=bestHeight;
////			surface.setLayoutParams(new LinearLayout.LayoutParams(bestWidth, bestHeight));
//		}
//	}

	
	/**
	 * �Զ���ıȽ���
	 * @author null
	 *
	 */
	public  class CameraSizeComparator implements Comparator<Camera.Size>{
		public int compare(Size lhs, Size rhs) {
			// TODO Auto-generated method stub
			if(lhs.width == rhs.width){
				return 0;
			}
			else if(lhs.width > rhs.width){
				return 1;
			}
			else{
				return -1;
			}
		}
	}
	
}
