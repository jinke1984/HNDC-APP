package cn.com.jinke.wh_drugcontrol.input.talkandreport;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.baidu.location.BDLocation;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.cloudwalk.libproject.Bulider;
import cn.cloudwalk.libproject.util.Base64Util;
import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.chat.manager.Constans;
import cn.com.jinke.wh_drugcontrol.customview.AppDialog;
import cn.com.jinke.wh_drugcontrol.customview.AppListDialog;
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.input.manager.FaceManager;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.persion.model.Photo;
import cn.com.jinke.wh_drugcontrol.utils.AlbumUtils;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.BDLocationTL;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.StorageUtil;
import cn.com.jinke.wh_drugcontrol.input.manager.FaceManager.BackUIResultCallBack;

/**
 * Created by jinke on 2017/8/17.
 * 定期报告和定期访谈新添加页面的父类
 */

public abstract class BaseUI extends ProjectBaseUI implements BackUIResultCallBack{

    /**
     * 1是面对面，0不是面对面
     */
    protected int isFace = BMDM;

    protected Persion mPersion = null;
    protected Document mDocument = null;
    protected Object mObject = null;
    protected boolean isModify = true;

    protected int mCountNum = 0;

    /**
     * 图片路径的集合
     */
    protected LinkedHashMap<Integer, Photo> mPathMap = new LinkedHashMap<Integer, Photo>();

    /**
     * 获取定位的实时地址
     */
    protected String mLocalDetail = "";
    protected UserCard mUserCard;

    /**
     * 是否有面对面照片的判断条件 true做了 false没有做
     */
    protected boolean isPhotoBack = false;

    /**
     * 是否做了活体检测和人脸比对的判断条件 true做了 false没有做
     */
    protected boolean isFaceBack = false;


    /**
     * 人脸比对的照片
     */
    protected String faceImgPath = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StorageUtil.ensureDirExist(StorageUtil.CLOUDWALK_PATH);
        Bulider.publicFilePath = StorageUtil.CLOUDWALK_PATH;
        super.onCreate(savedInstanceState);
        onCreate();
    }

    @Override
    protected void onInitView() {
        FaceManager.getInstance().setOnBackUIResultCallBack(this);
        mUserCard = MasterManager.getInstance().getUserCard();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mPersion = (Persion) bundle.getSerializable(PERSION);
            mDocument = (Document) bundle.getSerializable(DOCUMENT);
            mObject = bundle.getSerializable(OBJECT);
            isModify = bundle.getBoolean(TYPE, isModify);
            mCountNum = bundle.getInt(NUM, mCountNum);
        }

        Header header = getHeader();
        if (header != null) {
            header.rightLayout.setVisibility(View.VISIBLE);
            header.rightImageBtn.setVisibility(View.GONE);
            header.rightText.setText(R.string.bc);
            header.titleText.setText(onTitleShow());
            header.rightLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCheckBeforeSave()) {
                        if (isFace == MDM) {
                            //面对面
//                            if (isPhotoBack) {
//                                onSave();
//                            } else {
//                                noPhotoDialog();
//                            }
                            if(!isFaceBack && isModify){
                                //没有做过活体检测和人脸比对
                                noFaceDialog(true);
                                return;
                            }

                            if(!isPhotoBack){
                                //没有做过面对面的照片
                                noPhotoDialog();
                                return;
                            }

                            onSave();

                        } else {
                            //不是面对面
                            onSave();
                        }
                    }
                }
            });
        }

    }

    private void faceErrorDialog(){
        AppOneDialog dialog = new AppOneDialog(this, getString(R.string.rlbdsbts), getString(R.string.tishi),
                false, getString(R.string.qd));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.one_ok:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }
    private void noFaceToFaceDialog(){
        AppDialog dialog = new AppDialog(BaseUI.this, onNoFaceToFaceShow(), getString(R.string.tishi));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.ok:
                        //确定
                        isFace = BMDM;
                        break;
                    case R.id.cancel:
                        //取消
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    private void noFaceDialog(final boolean result) {
        AppDialog dialog = new AppDialog(BaseUI.this, onNoFaceShow(), getString(R.string.tishi));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ok:
                        //确定
                        isFace = BMDM;
                        if (result) {
                            onSave();
                        }
                        break;
                    case R.id.cancel:
                        //取消
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    private void noPhotoDialog() {
        AppDialog dialog = new AppDialog(BaseUI.this, onNoPhotoShow(), getString(R.string.tishi));
        dialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (v.getId()) {
                    case R.id.ok:
                        //确定
                        isFace = BMDM;
                        onSave();
                        break;
                    case R.id.cancel:
                        //取消
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void onInitData() {
        loadData();
    }

    /**
     * 面对面对话框
     * @param type  true定期访谈， false定期报告
     */
    protected void faceTofaceDialog(final boolean type) {

        AppDialog dialog = new AppDialog(BaseUI.this, onFaceTitleShow(), getString(R.string.tishi));
        dialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (v.getId()) {
                    case R.id.ok:
                        isFace = MDM;
                        if(type){
                            operate();
                        }else{
                            operate();
                        }
                        break;
                    case R.id.cancel:
                        isFace = BMDM;
                        //initLocation(false);
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    private void operate(){
        ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.smfs));
        list.add(getString(R.string.htjc));
        String tishi = getString(R.string.xzfs);
        AppListDialog dialog = new AppListDialog(BaseUI.this, tishi, list, false);
        dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                switch (position) {
                    case 0:
                        //扫码
                        isFace = EWM;
                        Intent intent = new Intent(BaseUI.this,CaptureActivity.class);
                        intent.putExtra(B_CODE, false);
                        startActivityForResult(intent, CaptureActivity.DECODING);
                        break;
                    case 1:
                        isFace = MDM;
                        FaceManager.getInstance().startLive(BaseUI.this);
                        break;
                    default:
                        break;
                }
            }

        });
        dialog.show();
    }

    private void talkOperate(){
        ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.smfs));
        list.add(getString(R.string.htjc));
        list.add(getString(R.string.ycsp));
        String tishi = getString(R.string.xzfs);
        AppListDialog dialog = new AppListDialog(BaseUI.this, tishi, list, false);
        dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                switch (position) {
                    case 0:
                        //扫码
                        isFace = EWM;
                        Intent intent = new Intent(BaseUI.this,CaptureActivity.class);
                        intent.putExtra(B_CODE, false);
                        startActivityForResult(intent, CaptureActivity.DECODING);
                        break;
                    case 1:
                        isFace = MDM;
                        FaceManager.getInstance().startLive(BaseUI.this);
                        break;
                    case 2:
                        isFace = MDM;
                        remoteVideo();
                        break;
                    default:
                        break;
                }
            }

        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AlbumUtils.OPEN_ALBUM:
                if (data == null) {
                    return;
                }
                AlbumUtils.startPhotoZoom(AlbumUtils.getDirFromAlbumUri(data.getData()), this);
                break;
            case AlbumUtils.OPEN_CAMERA:
                AlbumUtils.startPhotoZoom(Environment.getExternalStorageDirectory()
                        + "/" + CodeConstants.IMAGE_MASTER_CROP, this);
                break;
            case AlbumUtils.PHOTO_RESULT:
                isPhotoBack = true;
                if (data == null) {
                    return;
                }
                Bundle b = data.getExtras();
                if (b != null) {
                    byte[] d = b.getByteArray("data");
                    onSendPic(d);
                }
                break;
            case CaptureActivity.DECODING:
                if (data == null) {
                    return;
                }
                Bundle bundle = data.getExtras();
                String result = bundle.getString("result");
                verifyQRCode(result);
                break;
            default:
                break;
        }
    }

    private void tipDialog(String context) {
        AppOneDialog dialog = new AppOneDialog(this, context, getString(R.string.tishi), false, getString(R.string.back));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.one_ok:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    /**
     * 定位工作帮助类
     *
     * @param isFace true:是 false:否
     */
    private void initLocation(final boolean isFace) {
        showLoading();
        BDLocationTL.getInstance().startLocation(new BDLocationTL.MyLocationLisenner() {
            @Override
            public void onLocationFailed(BDLocation bdLocation) {
                //定位失败
                dismissLoading();
                tipDialog(getString(R.string.dqftdwts));
            }

            @Override
            public void onLocationSuccess(BDLocation bdLocation) {
                //定位成功
                dismissLoading();
                if (bdLocation != null) {
                    mLocalDetail = bdLocation.getAddrStr() + bdLocation.getLocationDescribe();
                    AppLogger.e(mLocalDetail);
                    if (isFace) {
                        AlbumUtils.openCamera(BaseUI.this);
                    }
                } else {
                    tipDialog(getString(R.string.dqftdwts));
                }
            }
        });
    }


    protected void addPhotoMap(String path, int type) {
        if (path != null && !TextUtils.isEmpty(path) && !"null".equals(path)) {
            Photo photo = new Photo();
            photo.setUrl(path);
            photo.setType(type);
            mPathMap.put(type, photo);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BDLocationTL.getInstance().stopLocation();
    }

    /**
     * 对附件的map进行排序
     *
     * @param map
     * @return
     */
    protected LinkedHashMap<Integer, Photo> PhotoMapSort(Map<Integer, Photo> map) {
        List<Map.Entry<Integer, Photo>> infoIds = new ArrayList<Map.Entry<Integer, Photo>>(map.entrySet());

        //排序
        Collections.sort(infoIds, new Comparator<Map.Entry<Integer, Photo>>() {
            @Override
            public int compare(Map.Entry<Integer, Photo> o1, Map.Entry<Integer, Photo> o2) {
                Integer p1 = o1.getKey();
                Integer p2 = o2.getKey();
                if (p1 < p2) {
                    return -1;
                } else if (p1 > p2) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        LinkedHashMap<Integer, Photo> newMap = new LinkedHashMap<Integer, Photo>();

        for (Map.Entry<Integer, Photo> entity : infoIds) {
            newMap.put(entity.getKey(), entity.getValue());
        }

        return newMap;
    }

    @Override
    public void onBackUIResult(boolean isLivePass, boolean isVerfyPass, int resultType, byte[] bestFaceImgData) {
        //TODO 在这里做人脸比对
        AppLogger.e("isLivePass="+isLivePass);
        isFaceBack = isLivePass;
        isPhotoBack = true;
        if(isLivePass){
            showLoading();
            String imgBestBase64 = Base64Util.encode(Bulider.bestFaceData);
            FaceManager.getInstance().compare(mPersion.getId(), imgBestBase64, new FaceManager.DataCallBack() {
                @Override
                public void requestFailure(String errorMsg) {
                    dismissLoading();
                    noFaceToFaceDialog();
                }

                @Override
                public void requestSucess(JSONObject jb) {
                    dismissLoading();
                    int suc = jb.optInt(K_SUCCESS);
                    if(suc == SUCCESS){
                        JSONObject result = jb.optJSONObject(DATA);
                        double score = result.optDouble(SCORE);
                        boolean value = CommUtils.getInstance().compareToValue(score, F_RESULT);
                        if(!value){
                            faceErrorDialog();
                        }else{
                            faceImgPath = result.optString(IMGAURL);
                            onFaceBeastImage();
                        }
                    }else{
                        noFaceToFaceDialog();
                    }
                }
            });
        }else{
            noFaceDialog(false);
        }
    }

    private void verifyQRCode(final String aResult){
        showLoading();
        String[] array = aResult.split(";");
        if(array.length != Constans.QRCODE_MAX){
            dismissLoading();
            failDialog(getString(R.string.smsb));
            return;
        }

        final String drugIdCard = array[0];
        final String type = array[1];
        final String time = array[2];

        //1定期尿检 2定期报告 3考勤 4定期访谈 5突击尿检 6身体检查 7心理辅导 8服药记录 9其他服务
        //这里只做了 定期报告  和 定期访谈
        if(!type.equals("4") && !"2".equals(type)){
            dismissLoading();
            showToast(getString(R.string.smlxcu));
            finish();
            return;
        }

        if(!TextUtils.isEmpty(drugIdCard) && mPersion.getIdentityCard().equals(drugIdCard)){
            QRCodeScan(drugIdCard, type, time, type);
        }else{
            dismissLoading();
            failDialog(getString(R.string.smryftrbip));
        }
    }

    protected void failDialog(String message) {

        AppOneDialog dialog = new AppOneDialog(this, message, getString(R.string.tishi), false, null);
        dialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        dialog.show();
    }


    /**
     * 创建视图
     */
    protected abstract void onCreate();

    /**
     * 菜单栏的标题显示
     *
     * @return
     */
    protected abstract String onTitleShow();

    /**
     * 提交数据前的检测
     *
     * @return
     */
    protected abstract boolean onCheckBeforeSave();

    /**
     * 保存数据
     */
    protected abstract void onSave();

    /**
     * 面对面对话框的提示文字
     *
     * @return
     */
    protected abstract String onFaceTitleShow();

    /**
     * 上传面对面的照片
     *
     * @param d
     */
    protected abstract void onSendPic(byte[] d);

    /**
     * 没有面对面照片的提示
     *
     * @return
     */
    protected abstract String onNoPhotoShow();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 没有通过活体检测的提示
     * @return
     */
    protected abstract String onNoFaceShow();

    /**
     * 没有通过人脸比对的提示
     * @return
     */
    protected abstract String onNoFaceToFaceShow();

    /**
     *  获取最佳照片的通知
     */
    protected abstract void onFaceBeastImage();

    /**
     *  二维码扫描的方法
     */
    protected abstract void QRCodeScan(String drugIdCard, String qrCodeType, String time, String scanType);

    /**
     *  定期访谈的远程视频启动（定期报告可以不做处理）
     */
    protected abstract void remoteVideo();
}
