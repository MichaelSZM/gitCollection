package com.example.cameratest2;

import com.szm.cameratest2.utils.view.FacadeView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	private FacadeView facade;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		facade=(FacadeView) findViewById(R.id.facade);
		facade.setTransParentRectWH(300, 300);
		findViewById(R.id.btn_shutter).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		facade.takeRectPicture(true);
	}

}
