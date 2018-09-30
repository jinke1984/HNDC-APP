package cn.com.jinke.wh_drugcontrol.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;


public class PictureCutActivity extends ProjectBaseUI implements OnTouchListener, OnClickListener {

	private Matrix matrix = new Matrix();
	private Matrix savedMatrix = new Matrix();
	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	private int mode = NONE;
	private PointF start = new PointF();
	private PointF mid = new PointF();
	private float oldDist = 1f;
	private LinearLayout m_ivstandard_container;
	private ImageView m_ivsource;
	private ImageView m_ivstandard;
	private int screen_width;
	private int screen_height;
	private View l_btnSure;
	private View l_btnCancel;
	private int standard_size;
	private Bitmap l_bmBG = null;
	public static final String PATH_STRING = "path";

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cut_pic);
		screen_width = getWindowManager().getDefaultDisplay().getWidth();
		screen_height = getWindowManager().getDefaultDisplay().getHeight();
		Intent preIntent = getIntent();
		initialView();
		initStandardView();
		initData(preIntent.getStringExtra(PATH_STRING));
	}

	private void initialView() {
		m_ivsource = (ImageView) findViewById(R.id.share_two);
		m_ivstandard_container = (LinearLayout) findViewById(R.id.share_three);
		l_btnSure = findViewById(R.id.btn_record_ok);
		l_btnCancel = findViewById(R.id.btn_give_up);

		m_ivsource.setOnTouchListener(this);
		l_btnSure.setOnClickListener(this);
		l_btnCancel.setOnClickListener(this);
	}

	private void initStandardView() {
		int size = screen_width < screen_height ? screen_width : screen_height;
		m_ivstandard = new ImageView(getApplicationContext());
		m_ivstandard.setBackgroundResource(R.drawable.standard);
		if (screen_width >= 720) {
			LayoutParams params = new LayoutParams(720, 720);
			m_ivstandard.setLayoutParams(params);
			standard_size = 720;
		} else {
			LayoutParams params = new LayoutParams(size, size);
			m_ivstandard.setLayoutParams(params);
			standard_size = size;
		}
		m_ivstandard_container.addView(m_ivstandard);
	}
	
	private void initData(String a_path) {
		if (a_path == null || a_path.equals("")) {
			finish();
			return;
		}
		l_bmBG = BitmapUtils.getLowMemoryBitmap(a_path, 720 * 1280, true);
		if (l_bmBG == null) {
			finish();
			return;
		}
		try {
			ExifInterface exifInterface;
			exifInterface = new ExifInterface(a_path);
			int tag = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, -1);
			int degree = 0;
			if (tag == ExifInterface.ORIENTATION_ROTATE_90) {
				degree = 90;
			} else if (tag == ExifInterface.ORIENTATION_ROTATE_180) {
				degree = 180;
			} else if (tag == ExifInterface.ORIENTATION_ROTATE_270) {
				degree = 270;
			}
			Matrix m = new Matrix();
			if (degree != 0) {
				m.setRotate(degree, (float) l_bmBG.getWidth() / 2,
						(float) l_bmBG.getHeight() / 2);
				Bitmap tmpBitmap = l_bmBG;
				l_bmBG = Bitmap.createBitmap(l_bmBG, 0, 0, l_bmBG.getWidth(),
						l_bmBG.getHeight(), m, true);
				if (tmpBitmap != null && !tmpBitmap.isRecycled()
						&& tmpBitmap != l_bmBG) {
					tmpBitmap.recycle();
				}
			}
		} catch (OutOfMemoryError error) {
			l_bmBG = null;
			error.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			l_bmBG = getFullBitmap(l_bmBG);
			if (l_bmBG == null) {
				finish();
			} else {
				m_ivsource.setImageBitmap(l_bmBG);
			}
		}
	}

	@Override
	protected void onDestroy() {
		if (l_bmBG != null && !l_bmBG.isRecycled()) {
			l_bmBG.recycle();
		}
		super.onDestroy();
	}

	public float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float)Math.sqrt(x * x + y * y);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_record_ok:
			try {
				byte l_b[] = takeScreenShot(m_ivsource);
				if (l_b != null) {
					Intent resultIntent = new Intent();
					resultIntent.putExtra("data", l_b);
//					setResult(CodeConstants.PHOTORESOULT, resultIntent);
					setResult(RESULT_OK, resultIntent);
				} else {
					Toast.makeText(PictureCutActivity.this,
							PictureCutActivity.this.getString(R.string.Fail),
							Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(PictureCutActivity.this,
						PictureCutActivity.this.getString(R.string.Fail),
						Toast.LENGTH_SHORT).show();
			} finally {
				PictureCutActivity.this.finish();
			}
			break;
		case R.id.btn_give_up:
			PictureCutActivity.this.finish();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		ImageView view = (ImageView) v;
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			matrix.set(view.getImageMatrix());
			savedMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			m_ivsource.setScaleType(ScaleType.MATRIX);
			mode = DRAG;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			if (oldDist > 10f) {
				savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;
			}
			break;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				matrix.postTranslate(event.getX() - start.x, event.getY()
						- start.y);
				start.x = event.getX();
				start.y = event.getY();
				view.setImageMatrix(matrix);
			} else if (mode == ZOOM) {
				float newDist = spacing(event);
				if (newDist > 10f) {
					float scale = newDist / oldDist;
					matrix.set(savedMatrix);
					matrix.postScale(scale, scale, mid.x, mid.y);
					m_ivsource.setImageMatrix(matrix);
				}
			}
			break;
		}
		return true;
	}

	public void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	public byte[] takeScreenShot(View view) throws Exception {
		if (view.willNotCacheDrawing()) {
			view.setWillNotCacheDrawing(Boolean.FALSE);
		}
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		view.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
				SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
		Bitmap bitmap = view.getDrawingCache();
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int[] location = new int[2];
		m_ivstandard.getLocationOnScreen(location);
		int x = location[0];
		int y = location[1] - statusBarHeight;
		if (bitmap == null) {
			return null;
		}
		try {
			bitmap = Bitmap.createBitmap(bitmap, x, y,
					m_ivstandard_container.getWidth(),
					m_ivstandard_container.getHeight());
		} catch (OutOfMemoryError error) {
			bitmap = null;
			error.printStackTrace();
		}
		if (bitmap == null)
			return null;

		ByteArrayOutputStream l_oArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 60, l_oArrayOutputStream);
		// view.setDrawingCacheEnabled(false); // modify by zj 2013-01-28
		return l_oArrayOutputStream.toByteArray();
	}

	private Bitmap getFullBitmap(Bitmap srcBitmap) {
		if (srcBitmap == null)
			return null;
		Bitmap bitmapTemp = null;
		int scale = srcBitmap.getWidth() > srcBitmap.getHeight() ? srcBitmap
				.getHeight() : srcBitmap.getWidth();
		float size = ((float) standard_size) / scale;
		Matrix matrix = new Matrix();
		matrix.postScale(size, size);
		try {
			bitmapTemp = Bitmap.createBitmap(srcBitmap, 0, 0,
					srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
		} catch (OutOfMemoryError error) {
			if (bitmapTemp != null)
				bitmapTemp = null;
			error.printStackTrace();
		}
		return bitmapTemp;
	}
}
