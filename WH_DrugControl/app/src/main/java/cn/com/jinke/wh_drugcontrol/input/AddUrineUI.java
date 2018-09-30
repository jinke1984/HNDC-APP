package cn.com.jinke.wh_drugcontrol.input;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.cloudwalk.libproject.Bulider;
import cn.cloudwalk.libproject.util.Base64Util;
import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.PermissionUI;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.customview.AppDialog;
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.customview.CustomEditText;
import cn.com.jinke.wh_drugcontrol.input.manager.FaceManager;
import cn.com.jinke.wh_drugcontrol.input.manager.FaceManager.BackUIResultCallBack;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.input.manager.UrineManager;
import cn.com.jinke.wh_drugcontrol.input.model.Urine;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.persion.model.Photo;
import cn.com.jinke.wh_drugcontrol.thread.Dispatcher;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.BDLocationTL;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.PermissionsChecker;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.StorageUtil;

/**
 * 新增定期尿检
 * Created by jinke on 2017/8/16.
 */

@Route(path = RouteUtils.R_ADD_URINE_UI)
public class AddUrineUI extends ProjectBaseUI implements OnCheckedChangeListener, BackUIResultCallBack {

    /**
     * 尿检地点
     */
    @ViewInject(R.id.urine_address_et)
    private CustomEditText mUrineAddressEt;

    /**
     *  检测人
     */
    @ViewInject(R.id.urine_persion_et)
    private CustomEditText mUrinePersonEt;

    /**
     * 尿检次数
     */
    @ViewInject(R.id.urine_num_et)
    private CustomEditText mUrineNumET;

    /**
     * 尿检结果
     */
    @ViewInject(R.id.urine_result_radiogroup)
    private RadioGroup mUrineResultRg;

    @ViewInject(R.id.yinx)
    private RadioButton mYinxRb;

    @ViewInject(R.id.yanx)
    private RadioButton mYanxRb;

    @ViewInject(R.id.u_image_look_layout)
    private RelativeLayout mLookLayout;

    @ViewInject(R.id.u_image_look_iv)
    private ImageView mLookIV;

    private Persion mPersion = null;
    private Document mDocument = null;
    private String mUrineAddress;
    private String mUrinePerson;
    private String mUrineNum = null;

    /**
     *  1阴性 2阳性
     */
    private String mUrineResult;

    /**
     * 附件上传成功后的路径
     */
    private String mPath;
    private String mLocalPath;
    private String mLocalDetail = "";
    private UserCard mUserCard = null;

    private int[] MSG = new int[]{ADD_URINE_MSG, UPLOAD_PHOTO_SUCCESS, UPLOAD_PHOTO_FAIL, ACCESS_NET_FAILED};

    /**
     *  所需的全部权限
     */
    static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 权限检测器
     */
    private PermissionsChecker mPermissionsChecker;


    private Urine mUrine = null;
    private int mNum = 0;

    /**
     * 人脸比对的照片
     */
    private String faceImgPath = "";

    private boolean isFace = false;
    private boolean isModfy = true;

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
            case UPLOAD_PHOTO_SUCCESS:
                dismissLoading();
                if (msg.arg1 == SUCCESS) {
                    Photo photo = (Photo) msg.obj;
                    mPath = photo.getUrl();
                    showToast(R.string.fjsccg);
                }else{
                    showToast(R.string.fjscsb);
                }
                break;
            case UPLOAD_PHOTO_FAIL:
                dismissLoading();
                showToast(R.string.fjscsb);
                break;
            case ADD_URINE_MSG:
                dismissLoading();
                if (msg.arg1 == SUCCESS) {
                    showToast(getString(R.string.upload_success));
                    finish();
                } else {
                    final String errMsg = (String) msg.obj;
                    showToast(errMsg);
                }
                break;
            case ACCESS_NET_FAILED:
                dismissLoading();
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
        registerMessages(MSG);
        setContentView(R.layout.ui_add_urine);
        mPermissionsChecker = new PermissionsChecker(this);
    }

    @Override
    protected void onInitView() {
        //initLocation();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            isModfy = bundle.getBoolean(TYPE, isModfy);
            mPersion = (Persion) bundle.getSerializable(PERSION);
            mDocument = (Document) bundle.getSerializable(DOCUMENT);
            mUrine = (Urine)bundle.getSerializable(OBJECT);
            mNum = bundle.getInt(NUM, mNum);
        }

        Header header = getHeader();
        if (header != null) {
            String title = String.format(getString(R.string.sdnj), mPersion.getRealName());
            header.titleText.setText(title);
            header.rightLayout.setVisibility(View.VISIBLE);
            header.rightImageBtn.setVisibility(View.GONE);
            header.rightText.setText(R.string.bc);
            header.rightLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadData();
                }
            });
        }

        if(isModfy){
            faceUrineDialog();
        }

        FaceManager.getInstance().setOnBackUIResultCallBack(this);
        mUrineResultRg.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onInitData() {
        mUserCard = MasterManager.getInstance().getUserCard();
        if(mUrine != null){
            mUrineAddressEt.setText(mUrine.getUrinePlace());
            mUrinePersonEt.setText(mUrine.getTestcontactPerson());
            mUrineNumET.setText(mUrine.getUrineCounts()+"");
            mUrineResult = mUrine.getUrineResult();
            mPath = mUrine.getFileAdd();
            if(mUrineResult.equals("1")){
                mYinxRb.setChecked(true);
                mYanxRb.setChecked(false);
            }else if(mUrineResult.equals("2")){
                mYinxRb.setChecked(false);
                mYanxRb.setChecked(true);
            }

            if(!TextUtils.isEmpty(mPath)) {
                if (mLookLayout.getVisibility() == View.GONE) {
                    mLookLayout.setVisibility(View.VISIBLE);
                }
//                StringBuffer url = new StringBuffer();
//                url.append(RequestHelper.getInstance().);
//                url.append(mPath);
//                Glide.with(mContext).load(url.toString()).placeholder(R.drawable.default_msg)
//                        .error(R.drawable.default_msg).into(mLookIV);

                PhotoManager.getInstance().getPic(mPath, mLookIV);
            }
        }else{
            String userName = MasterManager.getInstance().getUserCard().getInfoName();
            int num = mNum + 1;
            mUrinePersonEt.setText(userName);
            mUrineNumET.setText(num+"");
        }
    }

    @Event(value = {R.id.scan_btn, R.id.u_image_look_iv}, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_btn:
                if(mPermissionsChecker.lacksPermissions(PERMISSIONS)){
                    AppLogger.e("no permission");
                    startPermissionsUI();
                }else{
                    ARouter.getInstance().build(RouteUtils.R_RECTPHOTO_UI)
                            .navigation(this, REQUEST_CODE_GET_PIC);
                }
                break;
            case R.id.u_image_look_iv:
                ARouter.getInstance().build(RouteUtils.R_PERSION_PREVIEW_UI)
                        .withString(B_PATH, mLocalPath).navigation();
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.yinx:
                mUrineResult = "1";
                break;
            case R.id.yanx:
                mUrineResult = "2";
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackUIResult(boolean isLivePass, boolean isVerfyPass, int resultType, byte[] bestFaceImgData) {
        if(isLivePass){
            showLoading();
            String imgBestBase64 = Base64Util.encode(bestFaceImgData);
            FaceManager.getInstance().compare(mPersion.getPersonID(), imgBestBase64, new FaceManager.DataCallBack() {
                @Override
                public void requestFailure(String errorMsg) {
                    dismissLoading();
                    tipDialog(getString(R.string.eljcsb));
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
                            tipDialog(getString(R.string.rlbdsbts));
                        }else{
                            faceImgPath = result.optString(IMGAURL);
                        }
                    }else{
                        tipDialog(getString(R.string.eljcsb));
                    }
                }
            });
        }else{
            tipDialog(getString(R.string.thjcsb));
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
                    mLocalPath = data.getStringExtra(EXTRA_PHOTO_PATH);
                    uploadPicture(mLocalPath);
                    if(!TextUtils.isEmpty(mLocalPath)) {
                        if (mLookLayout.getVisibility() == View.GONE) {
                            mLookLayout.setVisibility(View.VISIBLE);
                        }
                        Glide.with(this).load(mLocalPath).placeholder(R.drawable.default_msg)
                                .error(R.drawable.default_msg).into(mLookIV);
                    }
                }
                break;
            case PermissionUI.REQUEST_CODE:
                //权限的请求
                if(resultCode == PermissionUI.PERMISSIONS_DENIED){
                    MessageProxy.sendEmptyMessage(FINISH_ALL_ACTIVITY);
                }else{
                    ARouter.getInstance().build(RouteUtils.R_RECTPHOTO_UI).navigation(this, REQUEST_CODE_GET_PIC);
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
    private void uploadPicture(final String picPath) {
        Dispatcher.runOnSingleThread(new Runnable() {
            @Override
            public void run() {
                try {
                    showLoading();
                    int type = Photo.Type.TYPE_DEFAULT;

                    PhotoManager.getInstance().upLoadPic(picPath, mLocalDetail, mUserCard.getUserId(), type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 上传之前检查数据有效性
     *
     * @return true：有效；false：无效
     */
    private boolean checkDataBeforeUpload() {
        mUrineAddress = mUrineAddressEt.getText().trim();
        mUrinePerson = mUrinePersonEt.getText().trim();
        mUrineNum = mUrineNumET.getText().trim();

        if (TextUtils.isEmpty(mUrineAddress) ||
                TextUtils.isEmpty(mUrinePerson) ||
                TextUtils.isEmpty(mUrineResult) ||
                TextUtils.isEmpty(mPath) ) {
            return false;
        }
        return true;
    }

    /**
     * 上传数据
     */
    private void uploadData() {
        if (!checkDataBeforeUpload()) {
            showToast(getString(R.string.common_err_tips));
            return;
        }

        if (isNetworkConnected()) {
            StringBuffer path = new StringBuffer();
            String id = null;
            if(mUrine != null){
                id = mUrine.getId();
            }

            if(isFace && !TextUtils.isEmpty(faceImgPath.trim())){
                //做了
                path.append(faceImgPath);
                path.append(",");
                path.append(mPath);
            }else{
                //没做
                path.append(mPath);
            }

            showLoading();
            UrineManager.getInstance().uploadData(mPersion, mDocument, mDocument.getId(), mUrineNum,
                    mUrineAddress, mUrinePerson, mUrineResult, path.toString(), id);

        } else {
            showToast(R.string.common_network_unavailable);
        }
    }

    private void startPermissionsUI() {
        PermissionUI.startActivityForResult(this, PERMISSIONS);
    }

    /**
     * 定位工作帮助类
     */
    private void initLocation() {
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
                } else {
                    tipDialog(getString(R.string.dqftdwts));
                }
            }
        });
    }

    private void tipDialog(String context) {
        AppOneDialog dialog = new AppOneDialog(this, context, getString(R.string.tishi), false, getString(R.string.qd));
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

    private void faceUrineDialog(){
        AppDialog dialog = new AppDialog(this, getString(R.string.xyzssnj), getString(R.string.tishi));
        dialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (v.getId()) {
                    case R.id.ok:
                        //TODO 海南因为vpn的事情不能定位，先这样处理
                        //AlbumUtils.openCamera(BaseUI.this);
                        isFace = true;
                        FaceManager.getInstance().startLive(AddUrineUI.this);
                        break;
                    case R.id.cancel:
                        isFace = false;
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BDLocationTL.getInstance().stopLocation();
    }

}
