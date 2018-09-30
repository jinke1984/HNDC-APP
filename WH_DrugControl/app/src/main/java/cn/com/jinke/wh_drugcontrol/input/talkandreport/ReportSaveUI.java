package cn.com.jinke.wh_drugcontrol.input.talkandreport;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.customview.CustomEditText;
import cn.com.jinke.wh_drugcontrol.input.manager.InputManager;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.input.model.DruguserslistEntity;
import cn.com.jinke.wh_drugcontrol.input.model.Report;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Map;
import cn.com.jinke.wh_drugcontrol.persion.model.Photo;
import cn.com.jinke.wh_drugcontrol.thread.Dispatcher;
import cn.com.jinke.wh_drugcontrol.utils.AlbumUtils;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.StorageUtil;

/**
 * Created by jinke on 2017/8/17.
 */

@Route(path = RouteUtils.R_REPORT_SAVE_UI)
public class ReportSaveUI extends BaseUI {

    @ViewInject(R.id.report_persion_et)
    private CustomEditText mPersionET;

    @ViewInject(R.id.report_count_et)
    private CustomEditText mCountET;

    @ViewInject(R.id.report_address_et)
    private CustomEditText mAddressET;

    @ViewInject(R.id.report_location_et)
    private CustomEditText mLocalET;

    @ViewInject(R.id.r_image_look_layout)
    private RelativeLayout mLookLayout;

    @ViewInject(R.id.r_face_layout)
    private LinearLayout mFaceLayout;

    @ViewInject(R.id.r_face_radiogroup)
    private RadioGroup mFaceRadioGroup;

    @ViewInject(R.id.shi)
    private RadioButton mShiRB;

    @ViewInject(R.id.fou)
    private RadioButton mFouRB;

    @ViewInject(R.id.r_image_look_iv)
    private ImageView mLookIV;

    private String mRecord = null;
    private String mNum = null;
    private String mAddress = null;
    private String mPath = null;
    private String mLocalPath;

    private Report mReport = null;

    private int[] MSG = new int[]{ADD_REPORT_MSG, UPLOAD_PHOTO_SUCCESS, UPLOAD_PHOTO_FAIL,
            ACCESS_NET_FAILED, MAP_SAVE_SUCCESS, QRCODEMSG};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case ADD_REPORT_MSG:
                dismissLoading();
                if(msg.arg1 == SUCCESS){
                    showToast(R.string.dqbgbccg);
                    finish();
                }else{
                    showToast(R.string.dqbgbcsb);
                }
                break;
            case UPLOAD_PHOTO_SUCCESS:
                dismissLoading();
                if (msg.arg1 == SUCCESS) {
                    Photo photo = (Photo)msg.obj;
                    if(photo.getType() == Photo.Type.TYPE_FACE){
                        if(mPathMap.containsKey(Photo.Type.TYPE_FACE)){
                            mPathMap.remove(Photo.Type.TYPE_FACE);
                        }
                    }
                    mPathMap.put(photo.getType(), photo);
                    showToast(R.string.fjsccg);
                }else{
                    showToast(R.string.fjscsb);
                }
                break;
            case UPLOAD_PHOTO_FAIL:
                dismissLoading();
                showToast(R.string.fjscsb);
                break;
            case ACCESS_NET_FAILED:
                dismissLoading();
                break;
            case MAP_SAVE_SUCCESS:
                if(msg.arg1 == SUCCESS){
                    int type = Photo.Type.TYPE_LOCATION;
                    Map map = (Map)msg.obj;
                    String local = map.getLocation();
                    String photo = map.getPhoto();
                    uploadPicture(photo, local, type);
                    mLocalET.setText(local);
                }
                break;
            case QRCODEMSG:
                dismissLoading();
                if(msg.arg1 == SUCCESS){
                    showToast(getString(R.string.smcg));
                }else{
                    String cause = (String)msg.obj;
                    if(TextUtils.isEmpty(cause)){
                        cause = getString(R.string.smsb);
                    }
                    failDialog(cause);
                }
                break;
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    protected void onCreate() {
        registerMessages(MSG);
        setContentView(R.layout.ui_report_save);
    }

    @Override
    protected String onTitleShow() {
        String title = String.format(getString(R.string.sddqbg), mPersion.getRealName());
        return title;
    }

    @Override
    protected void loadData() {
        if(mObject != null){
            mReport = (Report)mObject;
            mPersionET.setText(mReport.getRecordPerson());
            mCountET.setText(mReport.getReportCounts()+"");
            mAddressET.setText(mReport.getReportPlache());

            String cameraPicAdd = mReport.getCameraPicAdd();
            String fileAdd = mReport.getFileAdd();
            String[] paths = fileAdd.split(",");

            //fix bug the fileAdd value not contain's ','
//            if(paths.length <=2){
//                paths = new String[2];
//            }

            addPhotoMap(cameraPicAdd, Photo.Type.TYPE_FACE);
            //addPhotoMap(paths[0], Photo.Type.TYPE_LOCATION);
            addPhotoMap(paths[0], Photo.Type.TYPE_ADJUNCT);

            if(CommUtils.getInstance().isFace(mReport.getCameraPicAdd())){
                mShiRB.setChecked(true);
                mFouRB.setChecked(false);
            }else{
                mShiRB.setChecked(false);
                mFouRB.setChecked(true);
            }

            if (!TextUtils.isEmpty(paths[0])) {
                if (mLookLayout.getVisibility() == View.GONE) {
                    mLookLayout.setVisibility(View.VISIBLE);
                }
//                Glide.with(this).load(imageUrl+paths[0]).placeholder(R.drawable.default_msg)
//                        .error(R.drawable.default_msg).into(mLookIV);

                PhotoManager.getInstance().getPic(paths[0], mLookIV);
            }

        }else{
            faceTofaceDialog(false);
            if(mFaceLayout.getVisibility() == View.VISIBLE){
                mFaceLayout.setVisibility(View.GONE);
            }

            String userName = MasterManager.getInstance().getUserCard().getInfoName();
            int num = mCountNum + 1;
            mPersionET.setText(userName);
            mCountET.setText(num+"");
        }
    }

    @Override
    protected boolean onCheckBeforeSave() {

        boolean result = true;
        mRecord = mPersionET.getText().trim();
        mNum = mCountET.getText().trim();
        mAddress = mAddressET.getText().trim();

        if(TextUtils.isEmpty(mRecord)){
            showToast(R.string.no_record_man);
            result = false;
        }

        if(TextUtils.isEmpty(mNum)){
            showToast(R.string.qsrbgcs);
            result = false;
        }

        if(TextUtils.isEmpty(mAddress)){
            showToast(R.string.no_record_address);
            result = false;
        }

//        if(!mPathMap.containsKey(Photo.Type.TYPE_LOCATION)){
//            showToast(R.string.no_report_location_map_file);
//            result = false;
//        }

        return result;
    }


    @Event(value = {R.id.r_face_radiogroup}, type = android.widget.RadioGroup.OnCheckedChangeListener.class)
    private void onCheckedChanged(RadioGroup group, int checkedId) {
        View view = group.findViewById(checkedId);
        if(!view.isPressed()){
            return;
        }
        switch (checkedId){
            case R.id.shi:
                isFace = MDM;
                AlbumUtils.openCamera(this);
                break;
            case R.id.fou:
                if(mPathMap.containsKey(Photo.Type.TYPE_FACE)){
                    mPathMap.remove(Photo.Type.TYPE_FACE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSave() {
        if (isNetworkConnected()){
            showLoading();
            ArrayList<String> pathList = new ArrayList<>();
            mPathMap = PhotoMapSort(mPathMap);
            for (HashMap.Entry<Integer, Photo> entry : mPathMap.entrySet()){
                String path = entry.getValue().getUrl();
                pathList.add(path);
            }

            int size = pathList.size();
            if(size == 1){
                mPath = pathList.get(0);
            }else{
                StringBuffer pathBuffer = new StringBuffer();
                for(int i=0;i<size;i++){
                    pathBuffer.append(pathList.get(i));
                    if(i!=size-1){
                        pathBuffer.append(",");
                    }
                }
                mPath = pathBuffer.toString();
            }

            String id = null;
            if(mReport != null){
                id = mReport.getId();
            }

            InputManager.getInstance().saveReport(mPersion.getRealName(), mPersion.getId(), mDocument.getId(), mPath,
                    mRecord, mNum, mAddress, mPersion.getIdentityCard(), isFace, mDocument.getType(), id);
        }else {
            showToast(R.string.common_network_unavailable);
        }
    }

    @Override
    protected String onFaceTitleShow() {
        return getString(R.string.sfsmdmbxbg);
    }

    @Override
    protected void onSendPic(byte[] d) {
        final long nowtime = System.currentTimeMillis();
        StringBuffer pathBuffer = new StringBuffer();
        pathBuffer.append(StorageUtil.PERSION_SAVE_PATH);
        pathBuffer.append(nowtime);
        pathBuffer.append(CHAT_PHOTO_NAME);
        final String path = pathBuffer.toString();
        Bitmap bitmap = BitmapFactory.decodeByteArray(d, 0, d.length);
        StorageUtil.saveAsJpeg(bitmap, StorageUtil.PERSION_SAVE_PATH, String.valueOf(nowtime), 75, true);
        Dispatcher.runOnSingleThread(new Runnable(){

            @Override
            public void run() {
                try {
                    showLoading();
                    int type = Photo.Type.TYPE_FACE;
                    PhotoManager.getInstance().upLoadPic(path, mLocalDetail, mUserCard.getUserId(), type);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onFaceBeastImage() {
        if(mPathMap.containsKey(Photo.Type.TYPE_FACE)){
            mPathMap.remove(Photo.Type.TYPE_FACE);
        }

        Photo photo = new Photo();
        photo.setType(Photo.Type.TYPE_FACE);
        photo.setUrl(faceImgPath);
        mPathMap.put(photo.getType(), photo);
    }

    @Override
    protected void QRCodeScan(String drugIdCard, String qrCodeType, String time, String scanType) {
        showLoading();
        String userId = mUserCard.getUserId();
        InputManager.getInstance().QRCode(drugIdCard, qrCodeType, time, scanType, userId);
    }

    @Override
    protected void remoteVideo() {

    }

    @Override
    protected String onNoFaceToFaceShow() {
        return getString(R.string.bgmyzhtrnbd);
    }

    @Override
    protected String onNoFaceShow() {
        return getString(R.string.bgmyzht);
    }

    @Override
    protected String onNoPhotoShow() {
        return getString(R.string.xtmyjcdspzpft);
    }

    @Event(value = {R.id.report_scan_btn, R.id.r_image_look_iv, R.id.report_location_et}, type = View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.report_scan_btn:
//                if(!mPathMap.containsKey(Photo.Type.TYPE_LOCATION)){
//                    showToast(R.string.qxscwzjtfj);
//                    return;
//                }
                ARouter.getInstance().build(RouteUtils.R_RECTPHOTO_UI).navigation(this, REQUEST_CODE_GET_PIC);
                break;
            case R.id.r_image_look_iv:
                ARouter.getInstance().build(RouteUtils.R_PERSION_PREVIEW_UI)
                        .withString(B_PATH, mLocalPath).navigation();
                break;
            case R.id.report_location_et:
                ARouter.getInstance().build(RouteUtils.R_MAP_UI).navigation();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1 && requestCode != 3) {
            return;
        }
        switch (requestCode){
            case REQUEST_CODE_GET_PIC:
                if(resultCode == RESULT_OK){
                    if(mPathMap.containsKey(Photo.Type.TYPE_ADJUNCT)){
                        mPathMap.remove(Photo.Type.TYPE_ADJUNCT);
                    }
                    int type = Photo.Type.TYPE_ADJUNCT;
                    mLocalPath = data.getStringExtra(EXTRA_PHOTO_PATH);
                    uploadPicture(mLocalPath, mLocalDetail, type);

                    if (!TextUtils.isEmpty(mLocalPath)) {
                        if (mLookLayout.getVisibility() == View.GONE) {
                            mLookLayout.setVisibility(View.VISIBLE);
                        }
                        Glide.with(this).load(mLocalPath).placeholder(R.drawable.default_msg)
                                .error(R.drawable.default_msg).into(mLookIV);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 上传图片
     *
     * @param picPath 包含扩展名的图片路径
     */
    private void uploadPicture(final String picPath, final String local, final int type) {
        Dispatcher.runOnSingleThread(new Runnable() {
            @Override
            public void run() {
                try {
                    showLoading();
                    PhotoManager.getInstance().upLoadPic(picPath, local, mUserCard.getUserId(), type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
