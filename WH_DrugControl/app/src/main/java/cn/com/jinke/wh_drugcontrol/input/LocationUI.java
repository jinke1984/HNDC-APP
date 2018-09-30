package cn.com.jinke.wh_drugcontrol.input;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;

import org.json.JSONException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.PermissionUI;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.customview.AppListDialog;
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.customview.NewGridView;
import cn.com.jinke.wh_drugcontrol.input.adapter.HousePhotoAdapter;
import cn.com.jinke.wh_drugcontrol.input.manager.InputManager;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.input.model.HousePhoto;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Map;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.PictureUI;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.persion.model.Photo;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.thread.Dispatcher;
import cn.com.jinke.wh_drugcontrol.utils.AlbumUtils;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.BDLocationTL;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.PermissionsChecker;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.StorageUtil;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ETYPE;

/**
 * 居住信息
 * Created by jinke on 2017/8/9.
 */

@Route(path = RouteUtils.R_LOCATION_UI)
public class LocationUI extends ProjectBaseUI {

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @ViewInject(R.id.wzt_grid)
    private NewGridView mPlaceGV;

    @ViewInject(R.id.location_picture)
    private NewGridView mHouseGV;

    private HousePhotoAdapter mPlaceAdapter = null;
    private HousePhotoAdapter mHouseAdapter = null;

    private HousePhoto mHousePhoto = null;
    private Persion mPersion = null;
    private Document mDocument = null;

    /**
     *  位置图
     */
    private ArrayList<String> mPlaceList = new ArrayList<>();

    /**
     *  房屋照片
     */
    private ArrayList<String> mHouseList = new ArrayList<>();
    private int[] MSG = new int[]{HOUSE_PHOTO_MSG, ACCESS_NET_FAILED, UPLOAD_PHOTO_SUCCESS,
            UPLOAD_PHOTO_FAIL, MAP_SAVE_SUCCESS, SAVE_HOUSE_PHOTO};

    /**
     *  权限检测器
     */
    private PermissionsChecker mPermissionsChecker;

    /**
     *  1位置图 2房屋图
     */
    private int mPhotoType = 1;
    private String mLocalDetail;
    private UserCard mUserCard = null;

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case HOUSE_PHOTO_MSG:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                mHousePhoto = (HousePhoto) msg.obj;
                setupHousePhotoData(mHousePhoto);
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
                String mPath = photo.getUrl();
                AppLogger.e(mPath);
                InputManager.getInstance().saveHousePhoto(mDocument, mPersion, mPhotoType, mPath);
            }
            break;
        case UPLOAD_PHOTO_FAIL:
            dismissLoading();
            break;
        case MAP_SAVE_SUCCESS:
            if (msg.arg1 == SUCCESS) {
                Map map = (Map)msg.obj;
                final String path = map.getPhoto();
                uploadPicture(path, mLocalDetail);
            }
            break;
        case SAVE_HOUSE_PHOTO:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                getHousePhotoData();
            }
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
        setContentView(R.layout.ui_location);
        mPermissionsChecker = new PermissionsChecker(this);
    }

    @Override
    protected void onInitView() {

        mUserCard = MasterManager.getInstance().getUserCard();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mPersion = (Persion) bundle.getSerializable(PERSION);
            mDocument = (Document) bundle.getSerializable(DOCUMENT);
        }

        Header header = getHeader();
        if (header != null) {
            String name = mPersion.getRealName();
            String title = String.format(getString(R.string.sdjzwzt), name);
            header.titleText.setText(title);
        }

        mPlaceAdapter = new HousePhotoAdapter(this);
        mPlaceGV.setAdapter(mPlaceAdapter);
        mHouseAdapter = new HousePhotoAdapter(this);
        mHouseGV.setAdapter(mHouseAdapter);

        mPlaceGV.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mPlaceList != null && !mPlaceList.isEmpty()) {
                    PictureUI.startActivity(LocationUI.this, mPlaceList, position);
                }
            }
        });

        mHouseGV.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mHouseList != null && !mHouseList.isEmpty()) {
                    PictureUI.startActivity(LocationUI.this, mHouseList, position);
                }
            }
        });
    }

    @Override
    protected void onInitData() {
        getHousePhotoData();
    }

    @Event(value = {R.id.current_location_tv, R.id.live_location_tv}, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
        case R.id.current_location_tv:
        case R.id.live_location_tv:
//            startLocationFor(view.getId());
//            doClick(view.getId());
            switchOutNet(view.getId());
            break;
        default:
            break;
        }
    }

    /**
     * true 代表在内网，false代表在外网
     */
    private void switchOutNet(int viewClickId){
        boolean result = ShareUtils.getInstance().get(ESHARE.SYS, SWITCH_NET, ETYPE.BOOL);
        if(result){
            switchOutNetDialog();
        }else{
            doClick(viewClickId);
        }
    }

    /**
     * 位置图和房屋照片的新增功能必须在外网操作
     */
    private void switchOutNetDialog(){
        String content = String.format(getString(R.string.wwts), getString(R.string.xzjzwztfwzp));
        AppOneDialog dialog = new AppOneDialog(this, content, getString(R.string.tishi), true, getString(R.string.qd));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.one_ok:
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    private void startLocationFor(final int viewClickId) {

        BDLocationTL.getInstance().startLocation(new BDLocationTL.MyLocationLisenner() {
            @Override
            public void onLocationFailed(BDLocation bdLocation) {
                //定位失败
                dismissLoading();
                tipDialog(getString(R.string.xqdwsbs));
            }

            @Override
            public void onLocationSuccess(BDLocation bdLocation) {

                //定位成功
                dismissLoading();
                if (bdLocation != null) {
                    mLocalDetail = bdLocation.getAddrStr() + bdLocation.getLocationDescribe();

                    doClick(viewClickId);
                } else {
                    tipDialog(getString(R.string.xqdwsbs));
                }
            }
        });
    }

    private void doClick(int viewClickId) {
        switch (viewClickId) {
        case R.id.current_location_tv:
            //位置图
            mPhotoType = 1;
            ARouter.getInstance().build(RouteUtils.R_MAP_UI).navigation();
            break;
        case R.id.live_location_tv:
            //房屋照片
            mPhotoType = 2;
            if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                AppLogger.e("no permission");
                startPermissionsUI();
            } else {
                takePicture();
            }
            break;
        default:
            break;
        }

    }

    private void tipDialog(String context) {
        AppOneDialog dialog = new AppOneDialog(this, context, getString(R.string.tishi), true, getString(R.string.qd));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
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
     * 从服务器获取房屋位置图
     */
    private void getHousePhotoData() {
        if (isNetworkConnected()) {
            showLoading();
            String idCard = mPersion.getIdentityCard();
            InputManager.getInstance().getHousePhoto(idCard);
        } else {
            showToast(R.string.common_network_unavailable);
        }
    }

    /**
     * 设置房屋位置图
     */
    private void setupHousePhotoData(HousePhoto housePhoto) {
        if (housePhoto == null) {
            return;
        }

        if (mPlaceList.size() > 0) {
            mPlaceList.clear();
        }

        if (mHouseList.size() > 0) {
            mHouseList.clear();
        }

        if (!TextUtils.isEmpty(mHousePhoto.getPositionAdd())) {
            String place = mHousePhoto.getPositionAdd().trim();

            if (!TextUtils.isEmpty(place) && !place.equals("null")) {
                String[] temp = place.split(",");
                Collections.addAll(mPlaceList, temp);
                mPlaceAdapter.setData(mPlaceList);
                mPlaceAdapter.notifyDataSetChanged();
            }

        }

        if (!TextUtils.isEmpty(mHousePhoto.getPictureAdd())) {
            String house = mHousePhoto.getPictureAdd().trim();

            if (!TextUtils.isEmpty(house) && !house.equals("null")) {
                String[] temp = house.split(",");
                Collections.addAll(mHouseList, temp);
                mHouseAdapter.setData(mHouseList);
                mHouseAdapter.notifyDataSetChanged();
            }
        }
    }

    private void takePicture() {
        ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.pz));
        list.add(getString(R.string.xc));
        String title = getString(R.string.sctp);
        AppListDialog dialog = new AppListDialog(LocationUI.this, title, list, true);
        dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                case 0:
                    AlbumUtils.openCamera(LocationUI.this);
                    break;
                case 1:
                    AlbumUtils.openAlbum(LocationUI.this);
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
            if (data == null){
                return;
            }
            AlbumUtils.startPhotoZoom(AlbumUtils.getDirFromAlbumUri(data.getData()), this);
            break;
        case AlbumUtils.OPEN_CAMERA:
            AlbumUtils.startPhotoZoom(Environment.getExternalStorageDirectory()
                    + "/" + CodeConstants.IMAGE_MASTER_CROP, this);
            break;
        case AlbumUtils.PHOTO_RESULT:
            if (data == null){
                return;
            }
            Bundle b = data.getExtras();
            if (b != null) {
                byte[] d = b.getByteArray("data");
                sendPic(d);
            }
            break;
        case PermissionUI.REQUEST_CODE:
            //权限的请求
            if (resultCode == PermissionUI.PERMISSIONS_DENIED) {
                MessageProxy.sendEmptyMessage(FINISH_ALL_ACTIVITY);
            } else {
                takePicture();
            }
            break;
        default:
            break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BDLocationTL.getInstance().stopLocation();
    }

    private void sendPic(byte[] d) {
        final long currentTime = System.currentTimeMillis();
        final String path = StorageUtil.PERSION_SAVE_PATH + currentTime + CHAT_PHOTO_NAME;
        Bitmap bitmap = BitmapFactory.decodeByteArray(d, 0, d.length);
        StorageUtil.saveAsJpeg(bitmap, StorageUtil.PERSION_SAVE_PATH, String.valueOf(currentTime), 75, true);
        uploadPicture(path, mLocalDetail);
    }

    private void startPermissionsUI() {
        PermissionUI.startActivityForResult(this, PERMISSIONS);
    }

    private void uploadPicture(final String path, final String localDetail) {
        Dispatcher.runOnSingleThread(new Runnable() {
            @Override
            public void run() {
                try {
                    showLoading();
                    int type = Photo.Type.TYPE_DEFAULT;
                    PhotoManager.getInstance().upLoadPic(path, localDetail, mUserCard.getUserId(), type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
