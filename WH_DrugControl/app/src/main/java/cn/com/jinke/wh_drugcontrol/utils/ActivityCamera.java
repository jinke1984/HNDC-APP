package cn.com.jinke.wh_drugcontrol.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;

@SuppressLint("NewApi")
public class ActivityCamera extends ActivityBase {
	//private final String TAG = "ActivityCamera";

	private final int WHAT_MSG_SHOW_FOCUS_VIEW = 0;

	private final int WHAT_MSG_GONE_PROGRESS_BAR = 1;

	private SurfaceView mSurfaceView;

	private SurfaceHolder mSurfaceHolder;

	private final int CAMERA_FACING_BACK = 0;
	private final int CAMERA_FACING_FRONT = 1;

	private Camera mCamera;

	private int mCurrentCameraFacing = CAMERA_FACING_BACK;

	private ImageView mBtnTakePicture;

	private ImageView mBtnBack;

	private ImageView mBtnSwitchCamera;

	private RelativeLayout mContainer;

	private View mProgressBar;

	// �������?
	private ImageView mAutoFocus;

	private RelativeLayout mAutoFocusLayout;

	private AutoFocusCallback mAutoFocusCallback;

	private Rect mFocusRectBoundary = new Rect();

	private boolean mFocusKeyDown = false;

	private boolean mFocusing = false;

	// ������Ӧ
	private SensorManager mSensorManager;

	private Sensor mSensor;

	private SensorEventListener mListener;

	private int mOrientation = GetImageUtils.ORIENTATION_ROTATE_0;

	private String mOutFilePath;
	public static final String OUT_PATH = "OUT_PATH";

	private boolean mDefaultIsFront = false;
	public static final String FRONT_CAMERA = "FRONT_CAMERA";

	private int mScreenHeight, mScreenWidth;

	private int mCameraCount = 1;

	/**
	 * ��ʼԤ������
	 */
	private Runnable startPreviewCameraRunnable = new Runnable() {

		@Override
		public void run() {
			try {
				if (mCamera != null) {
					mCamera.startPreview();
					autoFocus();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				mHandler.sendEmptyMessage(WHAT_MSG_GONE_PROGRESS_BAR);
			}
		}
	};

	// TODO Initialize camera picture callback
	Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			new SavePictureTask().execute(data);
		}
	};

	// TODO Initialize surface holder callback.
	SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {

		public void surfaceCreated(SurfaceHolder holder) {

			if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
				mContainer.setVisibility(View.INVISIBLE);
				mProgressBar.setVisibility(View.VISIBLE);
				return;
			}
			mProgressBar.setVisibility(View.INVISIBLE);
			mContainer.setVisibility(View.VISIBLE);

			if (mDefaultIsFront && VERSION.SDK_INT >= 9) {
				if (mCameraCount > 1) {
					openFrontCamera(holder);
				} else {
					openCamera(holder);
				}
			} else {
				openCamera(holder);
			}
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {

			if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
				return;
			}

			createBoundary();
			initCameraParametersAndStartPreview();
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			releaseCamera();
		}
	};

	/**
	 * ������ͷ
	 * 
	 * @param holder
	 */
	private void openCamera(SurfaceHolder holder) {
		// TODO Open camera.
		mCamera = Camera.open();
		try {
			mCamera.setPreviewDisplay(holder);
			mCurrentCameraFacing = CAMERA_FACING_BACK;
		} catch (IOException e) {
			mCamera.release();
			mCamera = null;
		}
	}

	@TargetApi(9)
	private void openFrontCamera(SurfaceHolder holder) {
		int numberOfCameras = mCameraCount;
		CameraInfo cameraInfo = new CameraInfo();
		for (int i = 0; i < numberOfCameras; i++) {
			Camera.getCameraInfo(i, cameraInfo);
			if (cameraInfo.facing == CAMERA_FACING_FRONT) {
				mCamera = Camera.open(i);
				try {
					mCamera.setPreviewDisplay(holder);
					mCurrentCameraFacing = CAMERA_FACING_FRONT;
				} catch (IOException e) {
					mCamera.release();
					mCamera = null;
				}
			}
		}
	}

	private class ComparatorSize implements Comparator<Size> {

		@Override
		public int compare(Size s1, Size s2) {
			// TODO Auto-generated method stub
			if (s1.width > s2.width) {
				return 1;
			} else if (s1.width < s2.width) {
				return -1;
			} else {
				if (s1.height > s2.height) {
					return 1;
				} else if (s1.height < s2.height) {
					return -1;
				} else {
					return 0;
				}
			}
		}

	}

	private void initCameraParametersAndStartPreview() {
		Camera.Parameters lParameters = mCamera.getParameters();
		lParameters.setPictureFormat(PixelFormat.JPEG);
		// mCamera.stopPreview();

		List<Size> sizes = lParameters.getSupportedPictureSizes();
		boolean setSize = false;
		Collections.sort(sizes, new ComparatorSize());
		try {

			for (Size s : sizes) {

			}

			float scale = 1f * mScreenWidth / mScreenHeight;

			int cutIndex = 0;
			float tempScale = 0;
			Size tempSize = null;
			for (int i = sizes.size() - 1; i >= 0; i--) {
				tempSize = sizes.get(i);

				if (tempSize.width != mScreenHeight) {
					continue;
				}
				tempScale = 1f * tempSize.height / tempSize.width;

				if (Math.abs(scale - tempScale) < 0.001f) {
					cutIndex = i;

					setSize = true;
					lParameters.setPreviewSize(tempSize.width, tempSize.height);
					break;
				}
			}
			// lParameters.setPreviewSize(1920, 1080);
			// setSize = true;
			int maxWidth = 0;
			int maxHeight = 0;
			if (setSize) {

				// // ǰ��
				// if (mCameraCount > 1 && mCurrentCameraFacing ==
				// CAMERA_FACING_FRONT)
				// {
				// cutIndex = sizes.size() - 1;
				// }
				// // ����
				// else
				// {
				// cutIndex = sizes.size() * 2 / 3;
				// }
				lParameters.setPictureSize(sizes.get(cutIndex).width,
						sizes.get(cutIndex).height);
				maxWidth = (int) (((float) sizes.get(cutIndex).width / (float) sizes
						.get(cutIndex).height) * (float) mScreenWidth);
				maxHeight = mScreenWidth;
			} else {
				Size maxSize = sizes.get(sizes.size() - 1);
				maxWidth = (int) (((float) maxSize.width / (float) maxSize.height) * (float) mScreenWidth);
				maxHeight = mScreenWidth;

				lParameters.setPreviewSize(maxSize.width, maxSize.height);
				lParameters.setPictureSize(maxSize.width, maxSize.height);
			}

			RelativeLayout.LayoutParams video_VIEWLAYOUT = (RelativeLayout.LayoutParams) mSurfaceView
					.getLayoutParams();
			if (video_VIEWLAYOUT.width != maxWidth) {
				video_VIEWLAYOUT.width = maxWidth;
				video_VIEWLAYOUT.height = maxHeight;
				mSurfaceView.setLayoutParams(video_VIEWLAYOUT);

				RelativeLayout.LayoutParams focus_VIEWLAYOUT = (RelativeLayout.LayoutParams) mAutoFocusLayout
						.getLayoutParams();
				focus_VIEWLAYOUT.width = maxWidth;
				focus_VIEWLAYOUT.height = maxHeight;
				mAutoFocusLayout.setLayoutParams(video_VIEWLAYOUT);
			}

			mCamera.setParameters(lParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}

		new Thread(startPreviewCameraRunnable).start();
	}

	/**
	 * �ر�����ͷ
	 */
	private void releaseCamera() {
		// TODO Release the camera
		if (mCamera == null) {
			return;
		}
		// mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	/**
	 * ����
	 */
	private void takePicture() {
		if (mCamera != null) {
			mAutoFocusLayout.setBackgroundColor(Color.BLACK);
			mBtnTakePicture.setEnabled(false);

			mCamera.takePicture(null, null, pictureCallback);
			unregisterSensorListener();
		}
	}

	/**
	 * ����
	 */
	private void autoFocus() {
		if (mCamera != null) {
			mFocusing = true;
			mHandler.sendEmptyMessage(WHAT_MSG_SHOW_FOCUS_VIEW);
			mCamera.autoFocus(mAutoFocusCallback);
		}
	}

	/**
	 * �����߽�
	 */
	private void createBoundary() {
		mSurfaceView.getLocalVisibleRect(mFocusRectBoundary);
		final int defWidth = mAutoFocus.getWidth() / 2;
		final int defHeight = mAutoFocus.getHeight() / 2;
		mFocusRectBoundary.left += defWidth;
		mFocusRectBoundary.right -= defWidth;
		mFocusRectBoundary.top += defHeight;
		mFocusRectBoundary.bottom -= defHeight;
	}

	@Override
	protected void onPreIinitialized(Bundle savedInstanceState) {
		Window window = getWindow();
		window.setBackgroundDrawableResource(android.R.color.background_dark);
		mOutFilePath = getIntent().getStringExtra(OUT_PATH);
		mDefaultIsFront = getIntent().getBooleanExtra(FRONT_CAMERA, false);
		mScreenWidth = getResources().getDisplayMetrics().heightPixels;
		mScreenHeight = getResources().getDisplayMetrics().widthPixels;
		initCameraCount();
		initSensor();
	}

	private void initCameraCount() {
		if (VERSION.SDK_INT >= 9) {
			mCameraCount = Camera.getNumberOfCameras();
		}
	}

	/**
	 * ��ʼ��SurfaceView
	 */
	private void initSurfaceView() {
		// TODO Initialize surface view
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(mSurfaceCallback);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	/**
	 * ��ʼ��������Ӧ��
	 */
	private void initSensor() {
		// TODO Initialize sensor
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		mListener = new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent event) {
				float x = event.values[SensorManager.DATA_X];
				float y = event.values[SensorManager.DATA_Y];

				mOrientation = GetImageUtils.getDeviceOrientation(x, y);
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		};

		if (mSensor == null) {
			mOrientation = GetImageUtils.ORIENTATION_ROTATE_90;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_FOCUS && !mFocusKeyDown) {
			mFocusKeyDown = true;
			if (!mFocusing) {
				autoFocus();
			}
			return true;
		}

		if ((keyCode == KeyEvent.KEYCODE_CAMERA || keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
				&& event.getRepeatCount() == 0) {
			takePicture();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_FOCUS) {
			mFocusKeyDown = false;
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected void onResume() {
		try {

			if (mCamera != null) {
				mAutoFocus.setVisibility(View.VISIBLE);
				new Thread(startPreviewCameraRunnable).start();
			}

			registerSensorListener();

		} catch (Exception e) {
			e.printStackTrace();
		}

		mBtnTakePicture.setEnabled(true);
		super.onResume();
	}

	@Override
	protected void onPause() {
		try {

			if (mCamera != null) {
				mCamera.stopPreview();
			}

			unregisterSensorListener();
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onPause();
	}

	/**
	 * ע��������Ӧ������
	 */
	private void registerSensorListener() {
		if (mSensorManager != null && mSensor != null) {
			mSensorManager.registerListener(mListener, mSensor,
					SensorManager.SENSOR_MIN);
		}
	}

	/**
	 * ע��������Ӧ������
	 */
	private void unregisterSensorListener() {
		if (mSensorManager != null && mSensor != null)
			mSensorManager.unregisterListener(mListener);
	}

	/**
	 * ����ͼ������
	 * 
	 * @author Usniyo
	 */
	private class SavePictureTask extends AsyncTask<byte[], Void, Uri> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			mProgressBar.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected Uri doInBackground(byte[]... jpegData) {
			// TODO Save picture.
			File lPictureTempFile = new File(mOutFilePath);

			try {
				if (!GetImageUtils.sdCardAvailable())
					throw new Exception("SDCard not mount!");

				FileOutputStream lTempFileOutputStream = new FileOutputStream(
						lPictureTempFile);
				lTempFileOutputStream.write(jpegData[0]);
				lTempFileOutputStream.close();
				jpegData[0] = null;

				if (mCameraCount > 1
						&& mCurrentCameraFacing == CAMERA_FACING_FRONT) {
					mOrientation = (mOrientation + 180) % 360;
				}

				GetImageUtils.setPictureExifDegree(lPictureTempFile.getPath(),
						mOrientation);

			} catch (Exception e) {
				e.printStackTrace();

				if (lPictureTempFile.exists()) {
					lPictureTempFile.delete();
				}
				cancel(true);
			}

			return Uri.fromFile(lPictureTempFile);
		}

		@Override
		protected void onCancelled() {
			// Toast.makeText(CameraDemoActivity.this, "����ʧ�ܣ�",
			// Toast.LENGTH_SHORT)
			// .showString();

			// registerSensorListener();
			// new Thread(startPreviewCameraRunnable).start();
			// mBtnTakePicture.setEnabled(true);
			mProgressBar.setVisibility(View.GONE);
			setResult(RESULT_FIRST_USER, null);
			finish();
		}

		@Override
		protected void onPostExecute(Uri uri) {
			// mSensorManager.registerListener(mListener, mSensor,
			// SensorManager.SENSOR_MIN);
			// Toast.makeText(CameraDemoActivity.this, "����ɹ���?",
			// Toast.LENGTH_SHORT)
			// .showString();

			// CameraUtil.broadcastNewPicture(CameraDemoActivity.this, result);
			mProgressBar.setVisibility(View.GONE);
			Intent intent = new Intent();
			intent.setData(uri);
			setResult(RESULT_OK, intent);
			finish();
		}
	}

	@Override
	protected boolean handleMessage(Message msg) {
		// TODO Handle the message.

		if (msg.what == WHAT_MSG_SHOW_FOCUS_VIEW) {
			// ����ʱ���ý����ɼ��Ե�����
			mAutoFocus.setVisibility(View.VISIBLE);

			return true;
		}

		if (msg.what == WHAT_MSG_GONE_PROGRESS_BAR) {
			mContainer.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
			return true;
		}

		return false;
	}

	@Override
	protected void onInitViews(Bundle savedInstanceState) {
		setContentView(R.layout.act_camera);
		mSurfaceView = (SurfaceView) findViewById(R.id.act_camera_surface_view);
		mAutoFocus = (ImageView) findViewById(R.id.act_camera_auto_focus_image);
		mBtnTakePicture = (ImageView) findViewById(R.id.act_camera_btn_take_picture);
		mAutoFocusLayout = (RelativeLayout) findViewById(R.id.act_camera_auto_focus_layout);
		mBtnBack = (ImageView) findViewById(R.id.act_camera_btn_back);
		mContainer = (RelativeLayout) findViewById(R.id.container);
		mBtnSwitchCamera = (ImageView) findViewById(R.id.act_camera_btn_switch_camera);
		mProgressBar = findViewById(R.id.act_camera_progress_bar);

		mBtnSwitchCamera.setVisibility(mCameraCount > 1 ? View.VISIBLE
				: View.GONE);

		mContainer.setVisibility(View.INVISIBLE);

		initSurfaceView();
	}

	@Override
	protected void onInitListeners(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mBtnTakePicture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				takePicture();
			}
		});
		mBtnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mAutoFocusCallback = new AutoFocusCallback() {

			@Override
			public void onAutoFocus(boolean success, Camera camera) {
				mAutoFocus.setVisibility(View.GONE);
				mAutoFocusLayout.scrollTo(0, 0);
				mFocusing = false;
			}
		};
		mAutoFocusLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_UP && !mFocusing) {
					int x = (int) event.getX();
					int y = (int) event.getY();
					int xBoundary = GetImageUtils.checkXBoundary(
							mFocusRectBoundary, x);
					int yBoundary = GetImageUtils.checkYBoundary(
							mFocusRectBoundary, y);
					int centerX = mFocusRectBoundary.centerX();
					int centerY = mFocusRectBoundary.centerY();
					int xShifting = centerX - x;
					int yShifting = centerY - y;
					int halfWidth = mAutoFocus.getWidth() / 2;
					int halfHeight = mAutoFocus.getHeight() / 2;

					if (xBoundary == -1) {
						xShifting = centerX - halfWidth;
					} else if (xBoundary == 1) {
						xShifting = -(centerX - halfWidth);
					}

					if (yBoundary == -1) {
						yShifting = centerY - halfHeight;
					} else if (yBoundary == 1) {
						yShifting = -(centerY - halfHeight);
					}

					v.scrollTo(xShifting, yShifting);

					autoFocus();
				}
				return true;
			}
		});

		mBtnSwitchCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mProgressBar.setVisibility(View.VISIBLE);
				mCamera.stopPreview();
				releaseCamera();

				new Thread(new Runnable() {

					@Override
					public void run() {
						if (mCurrentCameraFacing == CAMERA_FACING_BACK) {
							openFrontCamera(mSurfaceHolder);
						} else {
							openCamera(mSurfaceHolder);
						}

						// new Thread(startPreviewCameraRunnable).start();
						initCameraParametersAndStartPreview();
					}
				}).start();
			}
		});
	}

}
