package cn.com.jinke.wh_drugcontrol.word;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;

import org.json.JSONException;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.customview.AppTwoDialog;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.persion.manager.PersionManager;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.persion.model.Photo;
import cn.com.jinke.wh_drugcontrol.task.StartTaskUI;
import cn.com.jinke.wh_drugcontrol.thread.Dispatcher;
import cn.com.jinke.wh_drugcontrol.utils.BDLocationTL;
import cn.com.jinke.wh_drugcontrol.utils.DocUtil;
import cn.com.jinke.wh_drugcontrol.utils.IDocumentType;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * BaseWordUI
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/6
 */

public abstract class BaseWordUI extends ProjectBaseUI implements IDocumentType {

    private int[] MSG = new int[]{REQ_DELETE_DOC, UPLOAD_PHOTO_SUCCESS, UPLOAD_PHOTO_FAIL, ACCESS_NET_FAILED};

    protected String mAttachmentUrl;

    protected Persion mPersion;
    protected Document mDocument;

    private String mLocalDetail = "";

    protected boolean finishWhenLoadErr = true;

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case UPLOAD_PHOTO_SUCCESS:
            // 上传图片成功
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                Photo photo = (Photo) msg.obj;
                if (photo != null) {
                    mAttachmentUrl = photo.getUrl();
                    setupPreviewImage(mAttachmentUrl);
                }
            } else {
                showToast(getString(R.string.upload_attachment_err));
            }
            break;
        case UPLOAD_PHOTO_FAIL:
            // 上传图片失败
            dismissLoading();
            showToast(getString(R.string.upload_attachment_err));
            break;
        case ACCESS_NET_FAILED:
            dismissLoading();
            finishActivityWithToast();
            break;
        case REQ_DELETE_DOC:
            dismissLoading();
//            if (msg.arg1 == SUCCESS) {
            showToast((String) msg.obj);
            finish();
//            }
            break;
        default:
            break;
        }
        return super.handleMessage(msg);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG);
    }

    @Override
    protected void onInitView() {

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mPersion = (Persion) bundle.getSerializable(PERSION);
            mDocument = (Document) bundle.getSerializable(DOCUMENT);
        }

        Header header = getHeader();
        if (header != null) {
            String title = String.format(getString(onSetTitleFormatResId()), mPersion.getRealName());
            header.titleText.setText(title);
            header.rightLayout.setVisibility(View.VISIBLE);
            header.rightImageBtn.setVisibility(View.GONE);
            header.rightText.setText(R.string.bc);
            header.rightLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData2Server();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //startLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //BDLocationTL.getInstance().stopLocation();
    }

    protected void saveData2Server() {
        if (!checkDataBeforeUpload()) {
            showToast(getString(R.string.common_err_tips));
            return;
        }

        if (isNetworkConnected()) {
            showLoading();

            uploadData();
        } else {
            showToast(R.string.common_network_unavailable);
        }
    }

    private void startLocation() {
        showLoading();
        BDLocationTL.getInstance().startLocation(new BDLocationTL.MyLocationLisenner() {
            @Override
            public void onLocationFailed(BDLocation bdLocation) {
                //定位失败
                dismissLoading();
                showMessageDialog(getString(R.string.dqftdwts), true);
            }

            @Override
            public void onLocationSuccess(BDLocation bdLocation) {
                //定位成功
                dismissLoading();
                if (bdLocation != null) {
                    showToast(R.string.onlocationsuccess);
                    mLocalDetail = bdLocation.getAddrStr() + bdLocation.getLocationDescribe();
                } else {
                    showMessageDialog(getString(R.string.dqftdwts), true);
                }
            }
        });
    }

    /**
     * 用对话框显示信息message
     *
     * @param message   要显示的消息
     * @param ok2finish true：点击ok关闭页面，false：dismiss该对话框
     */
    protected final void showMessageDialog(final String message, final boolean ok2finish) {
        final AppOneDialog dialog = new AppOneDialog(this, message, getString(R.string.tishi), false, getString(R.string.qd));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                case R.id.one_ok:
                    if (ok2finish) {
                        finish();
                    } else {
                        dialog.dismiss();
                    }
                    break;
                default:
                    break;
                }
            }
        });
        dialog.show();
    }

    /**
     * 显示修改提示对话框
     *
     * @param message   消息
     * @param alterType 1档案修改 2档案删除
     * @param docId     档案id
     * @param personId  吸毒人员id
     * @param sourceId  资源id
     * @param docType   档案资源类型（如：1=决定书）
     */
    protected final void showAlterMessageDialog(final String message,
                                                final int alterType,
                                                final String docId,
                                                final String personId,
                                                final String sourceId,
                                                final int docType) {

        final AppTwoDialog dialog = new AppTwoDialog(this, message, getString(R.string.tishi), false,
                getString(R.string.qd), getString(R.string.qx));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                case R.id.two_ok:
                    StartTaskUI.navigation(alterType, docId, personId, sourceId, docType);
                    dialog.dismiss();

                    finish();
                    break;
                case R.id.two_cancel:
                    dialog.dismiss();
                    break;
                }
            }
        });

        dialog.show();
    }

    abstract int[] getFilterDocumentStatus();

    protected void dealDocumentStatus(boolean add) {

        if (mDocument == null || Document.DOC_STATUS_END.equals(mDocument.getDocStatus())) {
            disableSave();
            showMessageDialog(getString(R.string.reject_doc_end), false);
            return;
        }

        if (!add) return;

        DocUtil.checkDocStatus(this, getFilterDocumentStatus());
    }

    protected final void handleUploadMessage(final Message msg) {
        dismissLoading();
        if (msg.arg1 == SUCCESS) {
            PersionManager.getInstance().getDocId(mPersion.getId());
            showToast(getString(R.string.upload_success));
            finish();
        } else {
            final String errMsg = (String) msg.obj;
            showToast(errMsg);
        }
    }

    protected final void takePicture() {
        ARouter.getInstance().build(RouteUtils.R_RECTPHOTO_UI)
                .navigation(this, REQUEST_CODE_GET_SCAN_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GET_SCAN_PIC && resultCode == RESULT_OK) {
            String localPreviewImagePath = data.getStringExtra(EXTRA_PHOTO_PATH);
            setPreviewImageView(localPreviewImagePath);
            uploadPicture(localPreviewImagePath);
        }
    }

    private void setPreviewImageView(String localImagePath) {
        if (getPreviewImageView() != null) {
            getPreviewImageView().setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(localImagePath)
                    .into(getPreviewImageView());
        }
    }

    protected void setupPreviewImage(String webImageUrl) {
        if (getPreviewImageView() != null) {
            getPreviewImageView().setVisibility(View.VISIBLE);
            //Glide.with(this).load(PhotoManager.getInstance().IMAGE_URL + webImageUrl).into(getPreviewImageView());
            PhotoManager.getInstance().getPic(webImageUrl, getPreviewImageView());
        }
    }

    /**
     * 上传图片
     *
     * @param picPath 包含扩展名的图片路径
     */
    private void uploadPicture(final String picPath) {
//        if (TextUtils.isEmpty(mLocalDetail)) {
//            showToast(R.string.onlocation);
//            return;
//        }

        Dispatcher.runOnSingleThread(new Runnable() {
            @Override
            public void run() {
                try {
                    showLoading();
                    PhotoManager.getInstance().uploadPicWithUserId(picPath, mLocalDetail, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onInitData() {
    }

    protected void finishActivityWithToast() {
        if (finishWhenLoadErr) {
            showToast(R.string.server_err);
            finish();
        }
    }

    /**
     * 禁能保存按钮（隐藏）
     */
    protected void disableSave() {
        Header header = getHeader();
        if (header != null) {
            header.rightLayout.setEnabled(false);
            header.rightLayout.setVisibility(View.GONE);
        }
    }



    protected ImageView getPreviewImageView() {
        return null;
    }

    /**
     * 获取格式化的标题字符串资源id
     *
     * @return resource id
     */
    protected abstract int onSetTitleFormatResId();

    /**
     * 上传数据到服务器之前进行数据校验
     *
     * @return true：数据通过校验
     */
    protected abstract boolean checkDataBeforeUpload();

    /**
     * 上传数据到服务器
     */
    protected abstract void uploadData();

}
