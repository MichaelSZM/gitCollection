package com.szm.cameratest2.utils.view;

import com.szm.cameratest2.utils.utils.DisplayUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
/**
 * ���ֲ�
 * @author michael
 *
 */
public class MaskView extends View {

	/**�м�͸������Ļ���*/
	private Paint transparentPaint;
	/**���ܰ�͸�����򻭱�*/
	private Paint translucentPaint;
	/**
	 * ��Ļ���
	 */
	int widthScreen, heightScreen;
	/**
	 * �м�͸�����εĿ��
	 */
	private int cRectWidth,cRectHeight;
	/**�м���ε����Ͻ�x����*/
	int left;
	/**�м���ε����Ͻ�y����*/
	int top;
	
	public MaskView(Context context) {
		super(context);
		initPaint();
		initData(context);
	}

	
	
	public int getcRectWidth() {
		return cRectWidth;
	}



	public MaskView setcRectWidth(int cRectWidth) {
		this.cRectWidth = cRectWidth;
		invalidate();
		return this;
	}



	public int getcRectHeight() {
		return cRectHeight;
	}



	public MaskView setcRectHeight(int cRectHeight) {
		this.cRectHeight = cRectHeight;
		invalidate();
		return this;
	}



	/**
	 * ��ʼ�����������
	 * @param context
	 */
	private void initData(Context context) {
		Point point=DisplayUtils.getScreenMetrics(context);
		widthScreen=point.x;
		heightScreen=point.y;
		//����Ĭ��ֵ
		cRectWidth=200;
		cRectHeight=200;
	}

	/**
	 * ��ʼ������
	 */
	private void initPaint() {
		//�����м�͸��������ε�paint
		transparentPaint=new Paint(Paint.ANTI_ALIAS_FLAG);//�����
		transparentPaint.setColor(Color.BLUE);//���û�����ɫ
		transparentPaint.setStyle(Style.STROKE);//���û��ʱʴ���ʽ--�����
		transparentPaint.setStrokeWidth(5f);//���ñʴ���ϸ
		transparentPaint.setAlpha(30);//���û��ʵ���ɫ��͸����
		
		//����������Ӱ����
		translucentPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		translucentPaint.setColor(Color.GRAY);
		translucentPaint.setStyle(Style.FILL);//���
		translucentPaint.setAlpha(180);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		left=(widthScreen-cRectWidth)/2;
		top=(heightScreen-cRectHeight)/2;
		Rect centerRect=new Rect(left, top, left+cRectWidth, top+cRectHeight);
		//����������Ӱ����
		canvas.drawRect(0, 0, widthScreen, top-1, translucentPaint);
		canvas.drawRect(0, top+cRectHeight+1, widthScreen, heightScreen, translucentPaint);
		canvas.drawRect(0, top-1, left-1, top+cRectHeight+1, translucentPaint);
		canvas.drawRect(left+cRectWidth+1, top-1, widthScreen, top+cRectHeight+1, translucentPaint);
		
		//�����м�͸���������
		canvas.drawRect(left, top, left+cRectWidth, top+cRectHeight, transparentPaint);
	}
	
}
