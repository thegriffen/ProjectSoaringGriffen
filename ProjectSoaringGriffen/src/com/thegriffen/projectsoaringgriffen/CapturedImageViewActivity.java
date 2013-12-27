package com.thegriffen.projectsoaringgriffen;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.thegriffen.projectsoaringgriffen.utils.CameraConstants;

public class CapturedImageViewActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.captured);
		ImageView cap_image=(ImageView)findViewById(R.id.captured_imageView);
		CameraConstants.bmp=BitmapFactory.decodeByteArray(CameraConstants.data1, 0, CameraConstants.data1.length);
		cap_image.setImageBitmap(CameraConstants.bmp);
	}
}