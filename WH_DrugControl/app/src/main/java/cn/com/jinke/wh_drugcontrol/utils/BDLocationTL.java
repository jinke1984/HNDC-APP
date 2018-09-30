package cn.com.jinke.wh_drugcontrol.utils;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import cn.com.jinke.wh_drugcontrol.booter.ProjectApplication;

public class BDLocationTL {
    private static BDLocationTL instance;
    private LocationClient mLocationClient = null;

    private void BDLocationTL() {
    }

    public static BDLocationTL getInstance() {
        if (instance == null) {
            synchronized (BDLocationTL.class) {
                if (instance == null) {
                    instance = new BDLocationTL();
                }
            }
        }
        return instance;
    }

    public void startLocation(final MyLocationLisenner locationLisenner) {

        Context context = ProjectApplication.getContext();
        mLocationClient = new LocationClient(context);
        mLocationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
                        || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                    //定位成功
                    locationLisenner.onLocationSuccess(bdLocation);
                } else {
                    //定位失败
                    locationLisenner.onLocationFailed(bdLocation);
                }
            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {

            }
        });

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setIsNeedLocationDescribe(true);
        option.setIgnoreKillProcess(false);

        mLocationClient.setLocOption(option);
        mLocationClient.start();

    }

    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
    }

    public interface MyLocationLisenner {

        /**
         * 定位成功
         * @param bdLocation
         */
        void onLocationSuccess(BDLocation bdLocation);

        /**
         * 定位失败
         * @param bdLocation
         */
        void onLocationFailed(BDLocation bdLocation);

    }
}
