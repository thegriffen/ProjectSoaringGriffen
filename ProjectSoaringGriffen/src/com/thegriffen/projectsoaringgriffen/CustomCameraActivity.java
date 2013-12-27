package com.thegriffen.projectsoaringgriffen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class CustomCameraActivity extends Activity implements SurfaceHolder.Callback {
	Camera mCamera=null;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	boolean previewing = false;
	int flag=0;
	int which = 0;
	int vesion = 0;
	int imgID = 0;
	public void turn() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {       
			if (Camera.getNumberOfCameras()>=2) {
				mCamera.stopPreview();
				mCamera.release();
				switch (which) {
				case 0:	
					mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
					which = 1;
					break;
				case 1:
					mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
					which = 0;
					break;
				}
				try {
					mCamera.setPreviewDisplay(surfaceHolder);
					//"this" is a SurfaceView which implements SurfaceHolder.Callback,
					//as found in the code examples
					mCamera.setPreviewCallback(null);
					// camera.setPreviewCallback(this);
					mCamera.startPreview();
				} catch (IOException exception) {
					mCamera.release();
					mCamera = null;
				}
				vesion=1;
			}
			else {
				AlertDialog.Builder ab=new AlertDialog.Builder(CustomCameraActivity.this);
				ab.setMessage("Device Having Only one Camera");
				ab.setCancelable(false);
				ab.setPositiveButton("ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).show();

			}
		}
		else {
			AlertDialog.Builder ab1=new AlertDialog.Builder(CustomCameraActivity.this);
			ab1.setMessage("This Device Does Not Support Dual Camera Feature");
			ab1.setCancelable(false);
			ab1.setPositiveButton("ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			}).show();
		}
	}

	private PictureCallback mPicture = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
			imgID = SaveImage(bmp);
		}
	};

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		getWindow().setFormat(PixelFormat.UNKNOWN);
		surfaceView = (SurfaceView)findViewById(R.id.camerapreview);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		Button cap_btn=(Button)findViewById(R.id.button01);
		Button retake=(Button)findViewById(R.id.retake);
		Button use=(Button)findViewById(R.id.Use);
		Button back=(Button)findViewById(R.id.back);
		Button turn=(Button)findViewById(R.id.turn);
		
		turn.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				turn();
			}
		});
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		use.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(flag >= 1) {
					Intent intent = new Intent(CustomCameraActivity.this, NewEntryActivity.class);
					intent.putExtra("com.thegriffen.projectsoaringgriffen.imgID", imgID);
					startActivity(intent);   
				}
				else {
					AlertDialog.Builder ab1=new AlertDialog.Builder(CustomCameraActivity.this);
					ab1.setMessage("Please Capture Image");
					ab1.setCancelable(false);
					ab1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					}).show();
				}
				flag = 0;
			}
		});
		retake.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCamera.startPreview();
				flag = 0;
			}
		});

		cap_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCamera.takePicture(null, null, mPicture);
				flag++;
			}
		});
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		if(previewing){
			mCamera.stopPreview();
			previewing = false;
		}

		if (mCamera != null) {
			try {
				mCamera.setPreviewDisplay(surfaceHolder);               
				mCamera.startPreview();
				previewing = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(vesion == 1)
		{
			Camera.open(which);   
		}
		else
		{
			mCamera = Camera.open();
		}



		try {
			Camera.Parameters parameters = mCamera.getParameters();
			if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
				// This is an undocumented although widely known feature
				parameters.set("orientation", "portrait");
				// For Android 2.2 and above
				mCamera.setDisplayOrientation(90);
				// Uncomment for Android 2.0 and above
				parameters.setRotation(90);
			} else {
				// This is an undocumented although widely known feature
				parameters.set("orientation", "landscape");
				// For Android 2.2 and above
				mCamera.setDisplayOrientation(0);
				// Uncomment for Android 2.0 and above
				parameters.setRotation(0);
			}
			mCamera.setParameters(parameters);
			mCamera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			mCamera.release();
			//  Log.v(LOGTAG,exception.getMessage());
		}
		mCamera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
		previewing = false;
	}
	
	private int SaveImage(Bitmap finalBitmap) {
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/Senior Project Images");    
		myDir.mkdirs();
		Random generator = new Random();
		int n = 100000;
		n = generator.nextInt(n);
		String fname = "Image-"+ n +".jpg";
		File file = new File(myDir, fname);
		if (file.exists()) {
			file.delete(); 
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}

}