package cn.com.jinke.wh_drugcontrol.me;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import org.xutils.view.annotation.ViewInject;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.PermissionUI;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.customview.AppDialog;
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.me.model.Map;
import cn.com.jinke.wh_drugcontrol.thread.Dispatcher;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.PermissionsChecker;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.StorageUtil;

/**
 * Created by jinke on 2017/8/9.
 */

@Route(path = RouteUtils.R_MAP_UI)
public class MapUI extends ProjectBaseUI implements SensorEventListener, OnMapLoadedCallback {

    private static final int REQUEST_CODE = 0; // 请求码

    @ViewInject(R.id.mapview)
    private MapView mMapView;

    public MyLocationListenner myListener = new MyLocationListenner();
    private BaiduMap mBaiduMap;
    private LocationClient mLocClient;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private int mCurrentDirection = 0;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;

    private boolean mIsLoad = false;  //地图是否加载完成
    private String mLocalDetail;

    private final static String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    private PermissionsChecker mChecker; // 权限检测器

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_map);
        mChecker = new PermissionsChecker(this);
    }

    @Override
    protected void onInitView() {
        hintDialog();
        Header header = getHeader();
        if(header != null){
            header.titleText.setText(R.string.dwdqwz);
            header.rightLayout.setVisibility(View.VISIBLE);
            header.rightImageBtn.setVisibility(View.GONE);
            header.rightText.setText(R.string.bc);
            header.rightLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(mIsLoad){
                        saveDialog();
                    }else{
                        showToast(R.string.dtmyjzwc);
                    }
                }
            });
        }

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapLoadedCallback(this);
        mBaiduMap.setMyLocationEnabled(true);  // 开启定位图层
        mChecker = new PermissionsChecker(this);
        if (mChecker.lacksPermissions(PERMISSIONS)){
            startPermissionsUI();
        }else{
            start();
        }
    }

    @Override
    protected void onInitData() {

    }

    private void start(){
        mLocClient = new LocationClient(this); // 定位初始化
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(10*1000);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        option.setIgnoreKillProcess(false);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                mLocalDetail = location.getAddrStr() + location.getLocationDescribe();
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        @Override
        public void onConnectHotSpotMessage(String var1, int var2){}
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mLocClient.stop();  // 退出时销毁定位
        mBaiduMap.setMyLocationEnabled(false); // 关闭定位图层
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void hintDialog(){

        AppOneDialog dialog = new AppOneDialog(this, getString(R.string.dtwzts), getString(R.string.tishi),
                true, getString(R.string.qd));
        dialog.show();
    }

    private void saveDialog() {

        AppDialog dialog = new AppDialog(this, getString(R.string.sfbcdqwz), getString(R.string.tishi));
        dialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (v.getId()) {
                    case R.id.cancel:
                        break;
                    case R.id.ok:
                        snapshotMap();
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onMapLoaded() {
        mIsLoad = true;
    }


    //百度地图截图
    private void snapshotMap(){

        Dispatcher.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                showLoading();
            }
        });

        mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                Map map = new Map();
                long currentTime = System.currentTimeMillis();
                StringBuffer fileName = new StringBuffer();
                fileName.append("MAP");
                fileName.append(currentTime);
                StorageUtil.saveAsJpeg(bitmap, StorageUtil.MAP_PATH, fileName.toString(), 50, true);
                map.setPhoto(StorageUtil.MAP_PATH+fileName.toString()+CHAT_PHOTO_NAME);
                map.setLocation(mLocalDetail);
                MessageProxy.sendMessage(MAP_SAVE_SUCCESS, SUCCESS, map);
                dismissLoading();
                finish();
            }
        });
    }

    private void startPermissionsUI() {
        PermissionUI.startActivityForResult(MapUI.this, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if(requestCode == PermissionUI.REQUEST_CODE && resultCode == PermissionUI.PERMISSIONS_DENIED){
            MessageProxy.sendEmptyMessage(FINISH_ALL_ACTIVITY);
        }else{
            start();
        }
    }
}
