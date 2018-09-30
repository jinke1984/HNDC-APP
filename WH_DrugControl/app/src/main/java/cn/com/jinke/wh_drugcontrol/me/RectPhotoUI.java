package cn.com.jinke.wh_drugcontrol.me;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.utils.SizeUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.customview.DrawImageView;
import cn.com.jinke.wh_drugcontrol.manager.AutoFocusManager;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.StorageUtil;
import cn.com.jinke.wh_drugcontrol.utils.Utils;

/**
 * Created by jinke on 2017/8/16.
 * 修改  红色边框以A4纸张（210mm×297mm）比例 动态调整
 */

@Route(path = RouteUtils.R_RECTPHOTO_UI)
public class RectPhotoUI extends ProjectBaseUI implements SurfaceHolder.Callback {

    private boolean isPreview = false;

    private SurfaceHolder mySurfaceHolder = null;
    private Camera myCamera = null;
    private Bitmap mBitmap = null;

    /**
     *  屏幕宽度
     */
    private int mScreenWidth;

    /**
     *  屏幕高度
     */
    private int mScreenHeight;

    /**
     *  A4纸宽度
     */
    private final float mA4Width = 210f;

    /**
     *  A4纸高度
     */
    private final float mA4Height = 297f;

    float mA4Ratio = mA4Width / mA4Height;
    int mPadingX = 20;
    int mPadingY;

    private AutoFocusManager autoFocusManager;

    /**
     * 预览SurfaceView
     */
    @ViewInject(R.id.previewSV)
    private SurfaceView mPreviewSV = null;

    @ViewInject(R.id.drawIV)
    private DrawImageView mDrawIV = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_rect_photo);
    }

    @Override
    protected void onPreInitView() {
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window myWindow = this.getWindow();
        myWindow.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onInitView() {

        tipDialog();
        mPadingX = SizeUtils.dp2px(this, mPadingX);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = wm.getDefaultDisplay().getWidth();
        mScreenHeight = wm.getDefaultDisplay().getHeight();
        mPreviewSV.setZOrderOnTop(false);
        mySurfaceHolder = mPreviewSV.getHolder();
        //translucent半透明 transparent透明
        mySurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mySurfaceHolder.addCallback(this);
        mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        float clipWidth = mScreenWidth - 2 * mPadingX;
        mPadingY = (int) ((mScreenHeight - (clipWidth) / mA4Ratio) / 2);
        mDrawIV.initX(mPadingX, mPadingY, mScreenWidth - mPadingX, mScreenHeight - mPadingY);
        mDrawIV.Change_onDraw(new Canvas());

    }

    @Override
    protected void onInitData() {

    }

    @Event(value = {R.id.drawIV}, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.drawIV:
                if (isPreview && myCamera != null) {
                    myCamera.takePicture(myShutterCallback, null, myJpegCallback);
                }
            default:
                break;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initCamera();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        myCamera = Camera.open();
        try {
            myCamera.setPreviewDisplay(mySurfaceHolder);
            AppLogger.e("SurfaceHolder.Callback: surfaceCreated!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            if (null != myCamera) {
                myCamera.release();
                myCamera = null;
            }
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != myCamera) {
            //在启动PreviewCallback时这个必须在前不然退出出错。
            myCamera.setPreviewCallback(null);
            //这里实际上注释掉也没关系
            myCamera.stopPreview();
            isPreview = false;
            myCamera.release();
            myCamera = null;
        }
    }

    //初始化相机
    public void initCamera() {
        if (isPreview) {
            myCamera.stopPreview();
        }
        if (null != myCamera) {
            Camera.Parameters myParam = myCamera.getParameters();

            myParam.setPictureFormat(PixelFormat.JPEG);

            //查询camera支持的picturesize和previewsize
            List<Camera.Size> pictureSizes = myParam.getSupportedPictureSizes();
            List<Camera.Size> previewSizes = myParam.getSupportedPreviewSizes();
            Camera.Size picSize = null;
            Camera.Size preSize = null;
            for (int i = 0; i < pictureSizes.size(); i++) {
                Camera.Size size = pictureSizes.get(i);
                if (size.height == mScreenWidth && size.width == mScreenHeight) {
                    picSize = size;
                }
            }
            for (int i = 0; i < previewSizes.size(); i++) {
                Camera.Size size = previewSizes.get(i);
                if (size.height == mScreenWidth && size.width == mScreenHeight) {
                    preSize = size;
                }
            }

            //设置大小和方向等参数
            myParam.setPictureSize(picSize.width, picSize.height);
            myParam.setPreviewSize(preSize.width, preSize.height);
            myParam.set("rotation", 90);
            myCamera.setDisplayOrientation(90);
            myParam.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            myCamera.setParameters(myParam);
            myCamera.startPreview();
            autoFocusManager = new AutoFocusManager(this, myCamera);
            isPreview = true;
        }
    }

    /**
     * 为了实现拍照的快门声音及拍照保存照片需要下面三个回调变量
     */
    ShutterCallback myShutterCallback = new ShutterCallback() {

        @Override
        public void onShutter() {
            // TODO Auto-generated method stub
            AppLogger.e("myShutterCallback:onShutter...");
        }
    };

    /**
     * 拍摄的未压缩原数据的回调,可以为null
     */
    PictureCallback myRawCallback = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            AppLogger.e("myRawCallback:onPictureTaken...");
        }
    };

    /**
     * 对jpeg图像数据的回调,最重要的一个回调
     */
    PictureCallback myJpegCallback = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            AppLogger.e("myJpegCallback:onPictureTaken...");
            if (null != data) {

                mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                int old_h = mBitmap.getHeight();
                int old_w = mBitmap.getWidth();
                if (old_h < old_w) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, false);
                }
                myCamera.stopPreview();
                isPreview = false;

                int h = mBitmap.getHeight();
                int w = mBitmap.getWidth();
                int a = mPadingY + (mScreenHeight - 2 * mPadingY);
                AppLogger.e("拍照的图片：" + w + "+" + h);
                AppLogger.e("y + height：" + a);

                /**
                 * y + height must be <= bitmap.height()
                 * Bitmap source, int x, int y, int width, int height
                 */
                Bitmap rectBitmap = Bitmap.createBitmap(mBitmap, mPadingX, mPadingY,
                        mScreenWidth - 2 * mPadingX, mScreenHeight - 2 * mPadingY);

                //保存图片到sdcard
                if (null != rectBitmap) {
                    final String path = saveScan(rectBitmap);

                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_PHOTO_PATH, path);
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(RESULT_CANCELED);
                }
            }
            finish();
        }
    };

    public String saveScan(Bitmap bitmap) {

        StringBuffer fileName = new StringBuffer();
        long currentTime = System.currentTimeMillis();
        fileName.append("SCAN");
        fileName.append(currentTime);

        StorageUtil.saveAsJpeg(bitmap, StorageUtil.BOOK_SCAN_SAVE_PATH, fileName.toString(), 50, true);

        final String filePath = StorageUtil.BOOK_SCAN_SAVE_PATH + fileName.toString() + ".jpg";
        AppLogger.e("saveJpeg：存储完毕！" + filePath);

        return filePath;
    }

    /**
     * 无意中按返回键时要释放内存
     */
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        RectPhotoUI.this.finish();
    }

    private void tipDialog() {

        AppOneDialog dialog = new AppOneDialog(this, getString(R.string.smtssm), getString(R.string.tishi),
                true, getString(R.string.qd));
        dialog.show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (autoFocusManager != null) {
            autoFocusManager.stop();
        }
    }

}
