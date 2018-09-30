package cn.com.jinke.wh_drugcontrol.persion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import cn.cloudwalk.libproject.Bulider;
import cn.cloudwalk.libproject.util.Base64Util;
import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.chat.manager.Constans;
import cn.com.jinke.wh_drugcontrol.customview.AppDialog;
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.customview.AppTwoDialog;
import cn.com.jinke.wh_drugcontrol.customview.NewGridView;
import cn.com.jinke.wh_drugcontrol.input.manager.FaceManager;
import cn.com.jinke.wh_drugcontrol.input.manager.FaceManager.BackUIResultCallBack;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.adapter.PersionTaskAdapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.PersionManager;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.persion.model.PersionTask;
import cn.com.jinke.wh_drugcontrol.persion.model.Photo;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ETYPE;
import cn.com.jinke.wh_drugcontrol.thread.Dispatcher;
import cn.com.jinke.wh_drugcontrol.utils.AlbumUtils;
import cn.com.jinke.wh_drugcontrol.utils.BDLocationTL;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.MyTextUtils;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.StorageUtil;

/**
 * 吸毒人员信息
 * Created by jinke on 2017/8/2.
 */

@Route(path = RouteUtils.R_PERSION_PERSIONDETAIL_UI)
public class PersionDetailUI extends ProjectBaseUI implements BackUIResultCallBack {

    @ViewInject(R.id.head_img)
    private ImageView mHeadIV;

    @ViewInject(R.id.name)
    private TextView mNameTV;

    @ViewInject(R.id.phone)
    private TextView mPhoneTV;

    @ViewInject(R.id.id_card)
    private TextView idCardTV;

    @ViewInject(R.id.control_grid)
    private NewGridView mGridView;

    @ViewInject(R.id.persion_source_tv)
    private TextView mSourceTV;

    @ViewInject(R.id.persion_source_iv)
    private ImageView mSourceIV;

    @ViewInject(R.id.head_from_img)
    private ImageView mFromIV;

    private Persion mPersion = null;
    private String mLocalDetail = "";

    private int[] MSG = new int[]{DOC_ID_MSG, ACCESS_NET_FAILED, UPLOAD_PHOTO_SUCCESS,
            UPLOAD_PHOTO_FAIL, UPDATE_DJ_PHOTO};

    private UserCard mUserCard = null;

    /**
     * 登记表照片类集合
     */
    private ArrayList<String> mPhotoPathList = new ArrayList<>();

    /**
     * 人脸比对的照片
     */
    private String faceImgPath = "";

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
            case DOC_ID_MSG:
                dismissLoading();
                if (msg.arg1 == SUCCESS) {
                    showToast(R.string.hqdacg);
                    Document document = PersionManager.getInstance().getDocument();
                    if(document != null){
                        mPersion.setPersonID(document.getPersonID());
                    }

                } else {
                    showToast(R.string.hqdasb);
                }
                break;
            case ACCESS_NET_FAILED:
                dismissLoading();
                break;
            case UPLOAD_PHOTO_SUCCESS:
                dismissLoading();
                if (msg.arg1 == SUCCESS) {
                    showLoading();
                    Photo photo = (Photo) msg.obj;
                    String path = photo.getUrl();
                    mPhotoPathList.add(path);
                    PersionManager.getInstance().updateDJPhoto(mPersion.getId(), path);
                } else {
                    showToast(R.string.fjscsb);
                }
                break;
            case UPLOAD_PHOTO_FAIL:
                dismissLoading();
                showToast(R.string.fjscsb);
                break;
            case UPDATE_DJ_PHOTO:
                dismissLoading();
                String result = (String) msg.obj;
                showToast(result);
                if(msg.arg1 == SUCCESS){
                    if (mFromIV.getVisibility() == View.GONE) {
                        mFromIV.setVisibility(View.VISIBLE);
                    }
                }
                break;
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StorageUtil.ensureDirExist(StorageUtil.CLOUDWALK_PATH);
        Bulider.publicFilePath = StorageUtil.CLOUDWALK_PATH;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_persion_detail);
    }

    @Override
    protected void onInitView() {

        mUserCard = MasterManager.getInstance().getUserCard();
        FaceManager.getInstance().setOnBackUIResultCallBack(this);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mPersion = (Persion) bundle.getSerializable(PERSION);
        }

        Header header = getHeader();
        if (header != null) {
            UserCard userCard = MasterManager.getInstance().getUserCard();
            header.rightLayout.setVisibility(View.GONE);
            header.titleText.setText(R.string.xdryxx);
            header.rightText.setVisibility(View.GONE);
            header.rightImageBtn.setBackgroundResource(R.drawable.zkryxx_message);
            header.rightImageBtn.setVisibility(View.VISIBLE);
            if (JDZG.equals(userCard.getUserType())) {
                header.rightLayout.setVisibility(View.VISIBLE);
            } else {
                header.rightLayout.setVisibility(View.GONE);
            }
            header.rightImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchOutNet();
                }
            });
        }
    }

    /**
     * 聊天功能在外网操作
     */
    private void switchOutNetDialog(){
        String content = String.format(getString(R.string.wwts), getString(R.string.lt));
        AppOneDialog dialog = new AppOneDialog(this, content, getString(R.string.tishi), true, getString(R.string.qd));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.one_ok:
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    /**
     * true 代表在内网，false代表在外网
     */
    private void switchOutNet(){
        boolean result = ShareUtils.getInstance().get(ESHARE.SYS, SWITCH_NET, ETYPE.BOOL);
        if(result){
            switchOutNetDialog();
        }else{
            ARouter.getInstance().build(RouteUtils.R_CHAT_UI)
                    .withSerializable(CHAT_PERSION, mPersion)
                    .withBoolean(BOOLEAN, true)
                    .navigation();
        }
    }

    @Override
    protected void onInitData() {

        showLoading();
        PersionManager.getInstance().getDocId(mPersion.getIdentityCard());

        String photoPath = mPersion.getPhotoPath();
        String headPath = mPersion.getPhotoAddress();
        int prvS = mPersion.getLastTimeScore();
        int nowS = mPersion.getScore();

        String sex = CommUtils.getInstance().getSexZw(mPersion.getSex());
        String s = String.format(getString(R.string.xmhxb), mPersion.getRealName(), sex);
        String phone = String.format(getString(R.string.yddhm), CommUtils.getInstance().getWU(mPersion.getCellphone()));
        String idCard = String.format(getString(R.string.sfzhm), mPersion.getIdentityCard());
        String source = String.format(getString(R.string.jf), String.valueOf(nowS));

//        Glide.with(this)
//                .load(IMAGE_URL + headPath)
//                .placeholder(R.drawable.item_default_head)
//                .error(R.drawable.item_default_head)
//                .into(mHeadIV);

//        PhotoManager.getInstance().getPCFile(headPath, mHeadIV);

        PhotoManager.getInstance().getPic(headPath, mHeadIV);

        mNameTV.setText(s);
        mPhoneTV.setText(phone);
        idCardTV.setText(idCard);
        mSourceTV.setText(source);
        mPhotoPathList.add(headPath);

        if (nowS < prvS) {
            if (mSourceIV.getVisibility() == View.GONE) {
                mSourceIV.setVisibility(View.VISIBLE);
            }
            mSourceIV.setBackgroundResource(R.drawable.jf_down);
        } else if (nowS > prvS) {
            if (mSourceIV.getVisibility() == View.GONE) {
                mSourceIV.setVisibility(View.VISIBLE);
            }
            mSourceIV.setBackgroundResource(R.drawable.jf_up);
        } else {
            if (mSourceIV.getVisibility() == View.VISIBLE) {
                mSourceIV.setVisibility(View.GONE);
            }
        }

        ArrayList<PersionTask> ptList = new ArrayList<>();
        initData(ptList);
        PersionTaskAdapter persionTaskAdapter = new PersionTaskAdapter(this);
        persionTaskAdapter.setData(ptList);
        mGridView.setAdapter(persionTaskAdapter);

        if (MyTextUtils.isEmptyOfNull(photoPath)) {
            if (JDZG.equals(mUserCard.getUserType())) {
                registerPhoto();
            }

            if (mFromIV.getVisibility() == View.VISIBLE) {
                mFromIV.setVisibility(View.GONE);
            }
        } else {
            if (mFromIV.getVisibility() == View.GONE) {
                mFromIV.setVisibility(View.VISIBLE);
            }
            mPhotoPathList.add(photoPath);
        }

    }

//    /**
//     * 定位工作帮助类
//     */
//    private void initLocation() {
//        showLoading();
//        BDLocationTL.getInstance().startLocation(new BDLocationTL.MyLocationLisenner() {
//            @Override
//            public void onLocationFailed(BDLocation bdLocation) {
//                //定位失败
//                dismissLoading();
//                tipDialog(getString(R.string.xqdwsbs));
//            }
//
//            @Override
//            public void onLocationSuccess(BDLocation bdLocation) {
//                //定位成功
//                dismissLoading();
//                if (bdLocation != null) {
//                    mLocalDetail = bdLocation.getAddrStr() + bdLocation.getLocationDescribe();
//                    AlbumUtils.openCamera(PersionDetailUI.this);
//                } else {
//                    tipDialog(getString(R.string.xqdwsbs));
//                }
//            }
//        });
//    }

    @Override
    protected void onResume() {
        super.onResume();
        registerMessages(MSG);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterMessages(MSG);
        BDLocationTL.getInstance().stopLocation();
    }

    private void initData(ArrayList<PersionTask> aList) {
        int[] imageResIds = {R.drawable.jczl_btn, R.drawable.sjcx_btn, R.drawable.jzxx_btn,
                R.drawable.dqft_btn, R.drawable.dqlj_btn, R.drawable.dqbg_btn, R.drawable.wslr_btn};

        int[] titleResIds = {R.string.jczl, R.string.sjcx, R.string.jzxx,
                R.string.dqft, R.string.dqlj, R.string.dqbg, R.string.wslr};

        for (int inx = 0; inx < imageResIds.length; ++inx) {
            PersionTask task = new PersionTask(imageResIds[inx], getString(titleResIds[inx]));
            aList.add(task);
        }
    }

//    private void initData(ArrayList<PersionTask> aList) {
//        int[] imageResIds = {R.drawable.jczl_btn, R.drawable.sjcx_btn, R.drawable.jzxx_btn,
//                R.drawable.dqft_btn, R.drawable.dqlj_btn, R.drawable.dqbg_btn, R.drawable.wslr_btn, R.drawable.leave_btn};
//
//        int[] titleResIds = {R.string.jczl, R.string.sjcx, R.string.jzxx,
//                R.string.dqft, R.string.dqlj, R.string.dqbg, R.string.wslr, R.string.qjcz};
//
//        for (int inx = 0; inx < imageResIds.length; ++inx) {
//            PersionTask task = new PersionTask(imageResIds[inx], getString(titleResIds[inx]));
//            aList.add(task);
//        }
//    }

    @Event(value = {R.id.control_grid}, type = android.widget.AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                //基础资料
                bundleSendData(RouteUtils.R_PERSION_BASEINFO_UI, false, false);
                break;
            case 1:
                //综合查询
                bundleSendData(RouteUtils.R_PERSION_INTERGRATED_QUERY, false, false);
                break;
//            case 2:
//                //采集信息
//                bundleSendData(RouteUtils.R_PERSION_GATHERING_INFORMATION, false, false);
//                break;
            case 2:
                //居住信息
                bundleSendData(RouteUtils.R_LOCATION_UI, true, true);
                break;
            case 3:
                //定期访谈
                bundleSendData(RouteUtils.R_INTERVIEW_UI, true, true);
                break;
            case 4:
                //定期尿检
                bundleSendData(RouteUtils.R_URINE_TEST_UI, true, true);
                break;
            case 5:
                //定期报告
                bundleSendData(RouteUtils.R_REPORT_UI, true, true);
                break;
            case 6:
                //文书录入
                bundleSendData(RouteUtils.R_WORD_PROC_UI, true, false);
                break;
//            case 7:
//                //请假操作
//                bundleSendData(RouteUtils.R_LEAVE_UI, true, true);
//                break;
            default:
                break;
        }
    }

    /**
     * 跳转之前判断是否有档案
     *
     * @param aARouterPath UIPath
     * @param checkDoc     true：检查是否有档案；false：直接跳转
     * @param isecide      检查是否有决定书
     */
    private void bundleSendData(String aARouterPath, boolean checkDoc, boolean isecide) {
        Document mDocument = PersionManager.getInstance().getDocument();
        //查询是否已经建立了档案
        if (checkDoc && ((mDocument == null) || (TextUtils.isEmpty(mDocument.getId())))) {
            showToast(R.string.myjlda);
            return;
        }

        //查询是否已经有决定书
        if (isecide && mDocument.getDecisionEntity() == 0) {
            showToast(R.string.jdsmylr);
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(PERSION, mPersion);
        bundle.putSerializable(DOCUMENT, mDocument);
        ARouter.getInstance().build(aARouterPath).withBundle(BUNDLE, bundle).navigation();
    }

    @Event(value = {R.id.phone, R.id.head_img, R.id.persion_source_layout},
            type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.phone:
                sendMsgDialog();
                break;
            case R.id.head_img:
                PictureUI.startActivity(this, mPhotoPathList, 0);
                break;
            case R.id.persion_source_layout:
                Bundle bundle = new Bundle();
                bundle.putSerializable(PERSION, mPersion);
                ARouter.getInstance().build(RouteUtils.R_PERSION_SOURCE_DETAIL_UI)
                        .withBundle(BUNDLE, bundle)
                        .navigation();
                break;
            default:
                break;
        }
    }

    private void sendMsgDialog() {
        String content = String.format(getString(R.string.nyxtfdx), mPersion.getRealName());
        AppDialog dialog = new AppDialog(this, content, getString(R.string.tishi));
        dialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (v.getId()) {
                    case R.id.ok:
                        String phone = mPersion.getCellphone();
                        if (!TextUtils.isEmpty(phone)) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
                            startActivity(intent);
                        } else {
                            showToast(R.string.myhm);
                        }
                        break;
                    case R.id.cancel:
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    private void registerPhoto() {
        AppDialog dialog = new AppDialog(this, getString(R.string.rybdsfkszx), getString(R.string.tishi));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ok:
                        //TODO 替换成活体检测和人脸比对
                        //initLocation();
                        //AlbumUtils.openCamera(PersionDetailUI.this);
                        FaceManager.getInstance().startLive(PersionDetailUI.this);
                        break;
                    case R.id.cancel:
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onBackUIResult(boolean isLivePass, boolean isVerfyPass, int resultType, byte[] bestFaceImgData) {
        //TODO 注意这里的逻辑修改成活体检测做完以后就拍照提供登记表数据 jinke 2018/1/13
        if(isLivePass){
            tipDialog(getString(R.string.thjccgdj), true);
        }else{
            tipDialog(getString(R.string.thjcsb), false);
        }
    }

    private void tipDialog(String context, final boolean isSuc) {
        AppOneDialog dialog = new AppOneDialog(this, context, getString(R.string.tishi), true, getString(R.string.qd));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.one_ok:
                        if(isSuc){
                            AlbumUtils.openCamera(PersionDetailUI.this);
                        }
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
        if (resultCode != -1 && requestCode != 3) {
            return;
        }
        switch (requestCode) {
            case AlbumUtils.OPEN_ALBUM:
                if (data == null) {
                    return;
                }
                AlbumUtils.startPhotoZoom(
                        AlbumUtils.getDirFromAlbumUri(data.getData()), this);
                break;
            case AlbumUtils.OPEN_CAMERA:
                AlbumUtils.startPhotoZoom(Environment.getExternalStorageDirectory()
                        + "/" + Constans.IMAGE_MASTER_CROP, this);
                break;
            case AlbumUtils.PHOTO_RESULT:
                if (data == null) {
                    return;
                }
                Bundle b = data.getExtras();
                if (b != null) {
                    byte[] d = b.getByteArray("data");
                    onSendPic(d);
                }
                break;
            default:
                break;
        }
    }

    protected void onSendPic(byte[] d) {
        final long nowtime = System.currentTimeMillis();
        final String path = StorageUtil.PERSION_SAVE_PATH + nowtime + CHAT_PHOTO_NAME;
        Bitmap bitmap = BitmapFactory.decodeByteArray(d, 0, d.length);
        StorageUtil.saveAsJpeg(bitmap, StorageUtil.PERSION_SAVE_PATH, String.valueOf(nowtime), 75, true);
        Dispatcher.runOnSingleThread(new Runnable() {
            @Override
            public void run() {
                try {
                    showLoading();
                    int type = Photo.Type.TYPE_DEFAULT;
                    PhotoManager.getInstance().upLoadPic(path, mLocalDetail, mUserCard.getUserId(), type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
