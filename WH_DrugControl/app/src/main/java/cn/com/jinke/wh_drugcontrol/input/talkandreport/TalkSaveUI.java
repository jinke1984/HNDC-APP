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
import com.blankj.utilcode.utils.EmptyUtils;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.chat.manager.ChatManager;
import cn.com.jinke.wh_drugcontrol.chat.model.RoomInfo;
import cn.com.jinke.wh_drugcontrol.customview.CustomEditText;
import cn.com.jinke.wh_drugcontrol.input.manager.InputManager;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.input.model.Talk;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Map;
import cn.com.jinke.wh_drugcontrol.persion.model.Photo;
import cn.com.jinke.wh_drugcontrol.thread.Dispatcher;
import cn.com.jinke.wh_drugcontrol.utils.AlbumUtils;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.StorageUtil;

/**
 * 新增定期访谈
 * Created by jinke on 2017/8/17.
 */

@Route(path = RouteUtils.R_TALK_SAVE_UI)
public class TalkSaveUI extends BaseUI {

    @ViewInject(R.id.talk_persion_et)
    private CustomEditText mPersionET;

    @ViewInject(R.id.talk_object_et)
    private CustomEditText mObjectET;

    @ViewInject(R.id.talk_count_et)
    private CustomEditText mCountET;

    @ViewInject(R.id.talk_address_et)
    private CustomEditText mAddressET;

    @ViewInject(R.id.talk_location_et)
    private CustomEditText mLocalET;

    @ViewInject(R.id.t_face_layout)
    private LinearLayout mTFaceLayout;

    @ViewInject(R.id.t_result_radiogroup)
    private RadioGroup mTFRg;

    @ViewInject(R.id.t_shi)
    private RadioButton mTShiRB;

    @ViewInject(R.id.t_fou)
    private RadioButton mTFouRB;

    /**
     * 谈话人
     */
    private String mPerson;

    /**
     * 谈话对象
     */
    private String mTak_Object;

    /**
     * 谈话次数
     */
    private String mCount;

    /**
     * 谈话地点
     */
    private String mAddress;

    /**
     * 附件
     */
    private String mPath;
    private String mLocalPath;
    private Talk mTalk;

    @ViewInject(R.id.image_look_layout)
    private RelativeLayout mImageLayout;

    @ViewInject(R.id.image_look_iv)
    private ImageView mImageIV;

    private int[] MSG = new int[]{ADD_CONVERSATION_MSG, UPLOAD_PHOTO_SUCCESS, UPLOAD_PHOTO_FAIL,
            ACCESS_NET_FAILED, MAP_SAVE_SUCCESS, QRCODEMSG, CHAT_ROOM_NUMBER};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
            case ADD_CONVERSATION_MSG:
                dismissLoading();
                if (msg.arg1 == SUCCESS) {
                    showToast(R.string.dqftbccg);
                    finish();
                } else {
                    showToast(R.string.dqftbcsb);
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
                } else {
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
                    Map map = (Map)msg.obj;
                    int type = Photo.Type.TYPE_LOCATION;
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
            case CHAT_ROOM_NUMBER:
//                dismissLoading();
//                RoomInfo roomInfo = (RoomInfo) msg.obj;
//                if (EmptyUtils.isEmpty(roomInfo)) {
//                    break;
//                }
//                ARouter.getInstance().build(RouteUtils.R_CHAT_VIDEO)
//                        .withString(B_ID, roomInfo.getRoomNum())
//                        .navigation();
                break;
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    protected void onCreate() {
        registerMessages(MSG);
        setContentView(R.layout.ui_talk_save);
    }

    @Override
    protected void loadData() {
        if(mObject != null){
            mTalk = (Talk)super.mObject;
            mPersionET.setText(mTalk.getTalker());
            mObjectET.setText(mTalk.getTalkObject());
            mCountET.setText(mTalk.getTalkCounts()+"");
            mAddressET.setText(mTalk.getTalkPlace());

            String cameraPicAdd = mTalk.getCameraPicAdd();
            String fileAdd = mTalk.getFileAdd();
            String[] paths = fileAdd.split(",");

//            if(paths.length <2){
//                paths = new String[2];
//            }

            addPhotoMap(cameraPicAdd, Photo.Type.TYPE_FACE);
            //addPhotoMap(paths[0], Photo.Type.TYPE_LOCATION);
            addPhotoMap(paths[0], Photo.Type.TYPE_ADJUNCT);

            if(CommUtils.getInstance().isFace(mTalk.getCameraPicAdd())){
                mTShiRB.setChecked(true);
                mTFouRB.setChecked(false);
            }else{
                mTShiRB.setChecked(false);
                mTFouRB.setChecked(true);
            }

            if (!TextUtils.isEmpty(paths[0])) {
                if (mImageLayout.getVisibility() == View.GONE) {
                    mImageLayout.setVisibility(View.VISIBLE);
                }
//                Glide.with(this).load(imageUrl+paths[0]).placeholder(R.drawable.default_msg)
//                        .error(R.drawable.default_msg).into(mImageIV);

                PhotoManager.getInstance().getPic(paths[0], mImageIV);
            }

        }else{
            faceTofaceDialog(true);
            if(mTFaceLayout.getVisibility() == View.VISIBLE){
                mTFaceLayout.setVisibility(View.GONE);
            }

            String drugName = mPersion.getRealName();
            String userName = MasterManager.getInstance().getUserCard().getInfoName();
            int num = mCountNum + 1;
            mPersionET.setText(userName);
            mObjectET.setText(drugName);
            mCountET.setText(num+"");
        }
    }

    @Override
    protected String onTitleShow() {
        String title = String.format(getString(R.string.sddqft), mPersion.getRealName());
        return title;
    }

    @Override
    protected boolean onCheckBeforeSave() {
        boolean result = true;
        mPerson = mPersionET.getText().trim();
        mTak_Object = mObjectET.getText().trim();
        mCount = mCountET.getText().trim();
        mAddress = mAddressET.getText().trim();

        if(TextUtils.isEmpty(mPerson)){
            result = false;
            showToast(R.string.thrmy);
        }

        if(TextUtils.isEmpty(mTak_Object)){
            result = false;
            showToast(R.string.thdxmy);
        }

        if(TextUtils.isEmpty(mCount)){
            result = false;
            showToast(R.string.thcsmy);
        }

        if(TextUtils.isEmpty(mAddress)){
            result = false;
            showToast(R.string.thddmy);
        }

//        if(!mPathMap.containsKey(Photo.Type.TYPE_LOCATION)){
//            result = false;
//            showToast(R.string.thwzjtmy);
//        }

        return result;
    }

    @Event(value = {R.id.t_result_radiogroup}, type = android.widget.RadioGroup.OnCheckedChangeListener.class)
    private void onCheckedChanged(RadioGroup group, int checkedId){
        View view = group.findViewById(checkedId);
        if(!view.isPressed()){
            return;
        }
        switch (checkedId){
            case R.id.t_shi:
                isFace = MDM;
                AlbumUtils.openCamera(this);
                break;
            case R.id.t_fou:
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
        if (isNetworkConnected()) {
            showLoading();
            mPathMap = PhotoMapSort(mPathMap);
            ArrayList<String> pathList = new ArrayList<>();
            for (HashMap.Entry<Integer, Photo> entry : mPathMap.entrySet()){
                String path = entry.getValue().getUrl();
                pathList.add(path);
            }

            int size = pathList.size();
            if (size == 1) {
                mPath = pathList.get(0);
            } else {
                StringBuffer pathBuffer = new StringBuffer();
                for (int i = 0; i < size; i++) {
                    pathBuffer.append(pathList.get(i));
                    if (i != size - 1) {
                        pathBuffer.append(",");
                    }
                }
                mPath = pathBuffer.toString();
            }

            String id = null;
            if(mTalk != null){
                id = mTalk.getId();
            }

            InputManager.getInstance().saveTalk(mPersion.getRealName(), mPersion.getId(), mDocument.getId(), mPath, mPerson, mTak_Object, mCount,
                    mAddress, mPersion.getIdentityCard(), isFace, mDocument.getType(), id);
        } else {
            showToast(R.string.common_network_unavailable);
        }

    }

    @Override
    protected String onFaceTitleShow() {
        return getString(R.string.sfsmdmth);
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
        Dispatcher.runOnSingleThread(new Runnable() {

            @Override
            public void run() {
                try {
                    showLoading();
                    int type = Photo.Type.TYPE_FACE;
                    PhotoManager.getInstance().upLoadPic(path, mLocalDetail, mUserCard.getUserId(), type);
                } catch (JSONException e) {
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
        showLoading();
        ChatManager.getInstance().getVideoChatRoomNumber(mPersion.getId());
    }

    @Override
    protected String onNoFaceToFaceShow() {
        return getString(R.string.ftmyzhtrnbd);
    }

    @Override
    protected String onNoFaceShow() {
        return getString(R.string.ftmyzht);
    }

    @Override
    protected String onNoPhotoShow() {
        return getString(R.string.xtmyjcdspsdzp);
    }

    @Event(value = {R.id.talk_scan_btn, R.id.image_look_iv, R.id.talk_location_et}, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.talk_scan_btn:
//                if(!mPathMap.containsKey(Photo.Type.TYPE_LOCATION)){
//                    showToast(R.string.qxscwzjtfj);
//                    return;
//                }
                ARouter.getInstance().build(RouteUtils.R_RECTPHOTO_UI).navigation(this, REQUEST_CODE_GET_PIC);
                break;
            case R.id.image_look_iv:
                ARouter.getInstance().build(RouteUtils.R_PERSION_PREVIEW_UI)
                        .withString(B_PATH, mLocalPath).navigation();
                break;
            case R.id.talk_location_et:
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
        switch (requestCode) {
            case REQUEST_CODE_GET_PIC:
                if (resultCode == RESULT_OK) {

                    if(mPathMap.containsKey(Photo.Type.TYPE_ADJUNCT)){
                        mPathMap.remove(Photo.Type.TYPE_ADJUNCT);
                    }
                    int type = Photo.Type.TYPE_ADJUNCT;
                    mLocalPath = data.getStringExtra(EXTRA_PHOTO_PATH);
                    uploadPicture(mLocalPath, mLocalDetail, type);

                    if (!TextUtils.isEmpty(mLocalPath)) {
                        if (mImageLayout.getVisibility() == View.GONE) {
                            mImageLayout.setVisibility(View.VISIBLE);
                        }
                        Glide.with(this).load(mLocalPath).placeholder(R.drawable.default_msg)
                                .error(R.drawable.default_msg).into(mImageIV);
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
