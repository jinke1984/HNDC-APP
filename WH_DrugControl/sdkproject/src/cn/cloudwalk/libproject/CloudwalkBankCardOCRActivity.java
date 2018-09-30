package cn.cloudwalk.libproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;
import java.lang.reflect.Method;

import cn.cloudwalk.BankOcrSDK;
import cn.cloudwalk.callback.BankCardCallback;
import cn.cloudwalk.jni.BankCardInfo;
import cn.cloudwalk.libproject.camera.AutoFocusCameraPreview;
import cn.cloudwalk.libproject.camera.Delegate;
import cn.cloudwalk.libproject.progressHUD.CwProgressHUD;
import cn.cloudwalk.libproject.util.ImgUtil;
import cn.cloudwalk.libproject.util.Util;
import cn.cloudwalk.libproject.view.OcrMaskView;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

public class CloudwalkBankCardOCRActivity extends Activity implements Delegate, BankCardCallback {

    private static final String TAG = "BankCardOCR";

    public CwProgressHUD processDialog;
    private Dialog mDialog;

    private static final int CANCEL_FOCUS = 0,DRAW_LINE = 1;
    AutoFocusCameraPreview mAutoFoucsCameraPreview;
    OcrMaskView maskView;
    ImageView mIv_idrect;
    Bitmap bitmap;//抓拍的卡片bitmap
    int ocr_flag = Contants.OCR_FLAG_BANKCARD;

    public BankOcrSDK bankOcrSDK;
    private BankCardInfo bankCardInfo;
    int initRet = -1;
    String licence;
    volatile  boolean isWork;//正在进行处理
    Bitmap bmpCanLine;//扫描线
    Bitmap bmpfocus;
    Bitmap bmpfocused;
    final String OutJpgName = "bankcard.jpg";//保存的图片名
    boolean mAutoRatio;
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CANCEL_FOCUS:
                    maskView.clearFocus();
                    break;
                case DRAW_LINE:
                    cwDrawLine();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void cwDrawLine(){
        maskView.setLine(bankCardInfo.left, bankCardInfo.top, bankCardInfo.right, bankCardInfo.bottom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAutoRatio = getIntent().getBooleanExtra("BANKCARD_AUTO_RATIO",false);//是否支持竖版银行卡识别
        if (!mAutoRatio){
            setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);//不支持竖版银行卡则强制横屏显示
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cloudwalk_activity_bankocr);
        if (getIntent()!=null) {
            licence = getIntent().getStringExtra("LICENCE");
        } else {
            finish();
        }

        initView();
        initSDK();
        bmpCanLine = BitmapFactory.decodeResource(getResources(), R.drawable.scan_line);
        bmpfocus = BitmapFactory.decodeResource(getResources(), R.drawable.focus);
        bmpfocused = BitmapFactory.decodeResource(getResources(), R.drawable.focused);
        mAutoFoucsCameraPreview.setAutoRatio(mAutoRatio);
        Point point = getScreenSize();
        mAutoFoucsCameraPreview.setScreenSize(point.x,point.y);
        mAutoFoucsCameraPreview.setFlag(ocr_flag);
        mAutoFoucsCameraPreview.setSizeCallback(new AutoFocusCameraPreview.SizeCallback() {
            @Override
            public void onSizeChange(int width, int height, final int ocrRectW, final int ocrRectH) {
                maskView.setOcr(width, height, ocrRectW, ocrRectH, ocr_flag, bmpCanLine, bmpfocus, bmpfocused);
            }
        });

        deleteCachedJpg();

        processDialog = CwProgressHUD.create(this).setStyle(CwProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在识别中...").setCancellable(true).setAnimationSpeed(2)
                .setCancellable(false).setDimAmount(0.5f);

    }

    protected Point getScreenSize() {
        int realWidth = 0, realHeight = 0;
        Display display = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        realWidth = metrics.widthPixels;
        realHeight = metrics.heightPixels;
        try {
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
                realHeight = size.y;
            } else if (android.os.Build.VERSION.SDK_INT < 17
                    && android.os.Build.VERSION.SDK_INT >= 14) {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Point(realWidth,realHeight);
    }


    /**
     * 注册接口
     */
    private void initCallback() {
        bankOcrSDK.cwBankCardCallback(this);
        mAutoFoucsCameraPreview.setDelegate(this);
    }

    /**
     * 初始化SDK
     */
    private void initSDK() {
        bankOcrSDK = BankOcrSDK.getInstance(this);
        if (0!=initRet)
            initRet = bankOcrSDK.cwCreateCardHandle(licence);
        if (initRet != 0) {
            mDialog = new AlertDialog.Builder(this).setMessage("初始化失败，授权码无效")
                    .setNegativeButton("确定", new AlertDialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                        }
                    }).show();
        }
    }

    /**
     * 初始化相关参数
     */
    private void initView() {
        mAutoFoucsCameraPreview = (AutoFocusCameraPreview) findViewById(R.id.CameraPreview);
        maskView = (OcrMaskView) findViewById(R.id.maskView);
        mIv_idrect = (ImageView) findViewById(R.id.iv_idrect);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCallback();
        mAutoFoucsCameraPreview.cwStartCamera();
   }

    @Override
    protected void onStop() {
        super.onStop();
        bitmap = null;
        isWork = false;
        mAutoFoucsCameraPreview.cwStopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bankOcrSDK.cwDestory();
        if (bmpCanLine!=null && !bmpCanLine.isRecycled()){
            bmpCanLine.recycle();
        }
        if (bmpfocus!=null && !bmpfocus.isRecycled()){
            bmpfocus.recycle();
        }
        if (bmpfocused!=null && !bmpfocused.isRecycled()){
            bmpfocused.recycle();
        }
        if (processDialog != null && processDialog.isShowing()) {
            processDialog.dismiss();
        }
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onOpenCameraError() {

    }

    @Override
    public void onFocus(float x, float y) {
        maskView.setFocus(x, y);

    }

    @Override
    public void onFocused() {
        maskView.setFocused();
        mHandler.sendEmptyMessageDelayed(CANCEL_FOCUS, 150);
    }

    @Override
    public void BankCardInfo(BankCardInfo bankCardInfo) {
        this.bankCardInfo = bankCardInfo;
        mHandler.sendEmptyMessage(DRAW_LINE);
    }

    @Override
    public void BankCardData(BankCardInfo bankCardInfo) {
        mHandler.removeCallbacksAndMessages(null);
        if (bankCardInfo!=null) {
            String cardNum = bankCardInfo.cardNum;
            String bankName = bankCardInfo.bankName;
            String cardName = bankCardInfo.cardName;
            String cardType = bankCardInfo.cardType;

            //图片质量不高或卡片不支持时,处理unknown情况
            if (bankName.equals("unknown")){
                runOnUiThread(new Runnable() {
                    @Override
                   public void run() {
                        if (processDialog!=null && processDialog.isShowing()) {
                           processDialog.dismiss();
                        }
                        mAutoFoucsCameraPreview.showCameraPreview();
                        isWork = false;
                    }
                });
                return;
            }

            //String path = Util.getDiskCacheDir(CloudwalkBankCardOCRActivity.this) + "/" + OutJpgName;
            String path = Bulider.publicFilePath+ "/" +System.currentTimeMillis()+ OutJpgName;

            //bgr字节数组保存成图片,在内存不足的情况下,可能会返回空
            bitmap = ImgUtil.byteArrayBGRToBitmap(bankCardInfo.jpgdata, bankCardInfo.width, bankCardInfo.height);
            if (bitmap!=null) {
                ImgUtil.saveJPGE_After(bitmap,path,100);
                Intent mIntent = new Intent(CloudwalkBankCardOCRActivity.this, BankCardResultActivity
                        .class);
                mIntent.putExtra("cardNum", cardNum);
                mIntent.putExtra("bankName", bankName);
                mIntent.putExtra("cardName", cardName);
                mIntent.putExtra("cardType", cardType);
                mIntent.putExtra("cardPath", path);

                startActivity(mIntent);
                finish();
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (processDialog!=null && processDialog.isShowing()) {
                            processDialog.dismiss();
                        }
                        mAutoFoucsCameraPreview.showCameraPreview();
                        isWork = false;
                    }
                });
            }
        } else {
            //识别失败了
           //finish();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (processDialog!=null && processDialog.isShowing()) {
                        processDialog.dismiss();
                    }
                    mAutoFoucsCameraPreview.showCameraPreview();
                    isWork = false;
                }
            });
        }
    }

    /**
     * 银行卡开始识别回调
     */
    @Override
    public void BankCardRecognizing() {
        if (!isWork) {
            deleteCachedJpg();
            isWork = true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (processDialog!=null && !processDialog.isShowing()) {
                        processDialog.setLabel(getString(R.string.bank_loading)).show();
                    }
                    mAutoFoucsCameraPreview.stopCameraPreview();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    /**
     * 清除可能已存储的银行卡图片
     */
    protected void deleteCachedJpg() {
        try {
            String path = Util.getDiskCacheDir(CloudwalkBankCardOCRActivity.this) + "/" + OutJpgName;
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
