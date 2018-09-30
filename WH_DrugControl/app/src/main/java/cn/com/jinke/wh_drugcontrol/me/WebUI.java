package cn.com.jinke.wh_drugcontrol.me;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import android.webkit.WebViewClient;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.customview.AppListDialog;
import cn.com.jinke.wh_drugcontrol.utils.AlbumUtils;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * WebUi
 * Created by jinke on 2017/9/19.
 */

@Route(path = RouteUtils.R_WEB_UI)
public class WebUI extends ProjectBaseUI {

    String TAG = WebUI.class.getSimpleName();

    @ViewInject(R.id.webview)
    private WebView mProgressWebView;

    private String mUrl = null;
    private String mContent = null;
    private Header mHeader = null;

    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_web);
    }

    @Override
    protected void onInitView() {

        //开启硬件加速
        getWindow().addFlags(0x01000000);

        Intent intent = getIntent();
        if (intent != null) {
            mUrl = intent.getStringExtra(URL);
            mContent = intent.getStringExtra(CONTENT);
            String title = intent.getStringExtra(TITLE);
            mHeader = getHeader();
            if (mHeader != null) {
                mHeader.rightLayout.setVisibility(View.GONE);
                mHeader.titleText.setMaxWidth(300);
                mHeader.titleText.setMaxLines(1);
                mHeader.titleText.setEllipsize(TextUtils.TruncateAt.END);
                mHeader.titleText.setText(title);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onInitData() {
        load();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetJavaScriptEnabled")
    private void load() {
        WebSettings settings = mProgressWebView.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setUseWideViewPort(false);
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDomStorageEnabled(true);
        settings.setSavePassword(false);
        settings.setPluginState(PluginState.ON);
        mProgressWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        initWebViewFileChoose(settings);

        mProgressWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                HitTestResult hit = view.getHitTestResult();
                if(hit == null){
                    return false;
                }

                int hitType = hit.getType();
                if (hitType == WebView.HitTestResult.SRC_ANCHOR_TYPE) {
                    view.loadUrl(url);
                    mUrl = url;
                    view.loadUrl(url);
                    return true;
                }
                else if (hitType == 0) {
                    return false;
                }
                else {
                    return false;
                }
            }
        });

        mProgressWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (!TextUtils.isEmpty(title) && title.length() > 10) {
                    title = title.substring(0, 10) + "...";
                }
                mHeader.titleText.setText(title);
                super.onReceivedTitle(view, title);
            }
        });

        if (mUrl != null) {
            AppLogger.d(TAG, "loadUrl:" + mUrl);
            mProgressWebView.loadUrl(mUrl);
        } else {
            AppLogger.d(TAG, "loadData:" + mContent);
            mProgressWebView.loadDataWithBaseURL(null, mContent, "text/html", "utf-8", null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initWebViewFileChoose(WebSettings settings) {
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        mProgressWebView.setWebChromeClient(new WebChromeClient() {

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                uploadMessage = valueCallback;
                AppLogger.d(TAG, "openFileChooser For Android < 3.0 ");
                showOptions();
            }

            // For Android  >= 3.0
            public void openFileChooser(ValueCallback valueCallback, String acceptType) {
                uploadMessage = valueCallback;
                AppLogger.d(TAG, "openFileChooser For Android >= 3.0 ");
                showOptions();
            }

            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadMessage = valueCallback;
                AppLogger.d(TAG, "openFileChooser For Android >= 4.1");
                showOptions();
            }

            /**
             * For android 5.0+
             */
            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                AppLogger.d(TAG, "openFileChooser For Android 5.0+");

                showOptions();
                return true;
            }

        });
    }


    private Intent mSourceIntent;

    public void showOptions() {
        ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.pz));
        list.add(getString(R.string.xc));
        String tishi = getString(R.string.xzfs);
        AppListDialog dialog = new AppListDialog(this, tishi, list, false);
        dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mSourceIntent = AlbumUtils.takeBigPicture();
                        startActivityForResult(mSourceIntent, AlbumUtils.REQUEST_CODE_IMAGE_CAPTURE);
                        break;
                    case 1:
                        mSourceIntent = AlbumUtils.choosePicture();
                        startActivityForResult(mSourceIntent, AlbumUtils.REQUEST_CODE_PICK_IMAGE);
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        AppLogger.d(TAG, "onActivityResult resultCode " + resultCode + " data " + data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AlbumUtils.REQUEST_CODE_IMAGE_CAPTURE:
                case AlbumUtils.REQUEST_CODE_PICK_IMAGE: {
                    String sourcePath = AlbumUtils.retrievePath(this, mSourceIntent, data);
                    if (uploadMessage != null) {
                        if (TextUtils.isEmpty(sourcePath) || !new File(sourcePath).exists()) {
                            break;
                        }
                        Uri uri = Uri.fromFile(new File(sourcePath));
                        uploadMessage.onReceiveValue(uri);
                    }
                    if (uploadMessageAboveL != null) {
                        // 5.0的回调
                        Uri uri = getImageContentUri(new File(sourcePath));
                        if (uri == null) {
                            return;
                        }
                        uploadMessageAboveL.onReceiveValue(new Uri[]{uri});
                        uploadMessageAboveL = null;
                    }
                    break;
                }
                default:
                    break;
            }
        } else {
            //解决 拍照取消后不能再次点击 的问题
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }
            if (uploadMessageAboveL != null) {
                // 5.0的回调
                uploadMessageAboveL.onReceiveValue(null);
                uploadMessageAboveL = null;
            }
        }
    }

    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

}
