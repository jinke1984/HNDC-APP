package cn.com.jinke.wh_drugcontrol.input.manager;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.cloudwalk.FaceInterface;
import cn.cloudwalk.libproject.Bulider;
import cn.cloudwalk.libproject.LiveStartActivity;
import cn.cloudwalk.libproject.callback.LivessCallBack;
import cn.cloudwalk.libproject.callback.ResultCallBack;
import cn.cloudwalk.libproject.net.ApacheHttpUtil;
import cn.cloudwalk.libproject.net.HttpManager;
import cn.cloudwalk.libproject.util.Base64Util;
import cn.com.jinke.wh_drugcontrol.BuildConfig;
import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectApplication;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.StorageUtil;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;

/**
 * @author jinke
 * @date 2018/1/4
 * @description  人脸对比和活体检测相关工具类
 */

public class FaceManager extends UrlSetting implements CodeConstants, MsgKey {

    private static FaceManager instance;

    private BackUIResultCallBack mBackUIResultCallBack;

    private FaceManager() {
    }

    public static FaceManager getInstance() {
        if (instance == null) {
            synchronized (FaceManager.class) {
                if (instance == null) {
                    instance = new FaceManager();
                }
            }
        }
        return instance;
    }

    /**
     * 人脸比对的方法
     * @param drugId
     * @param urlA
     * @param dataCallBack
     */
    public void compare(final String drugId, final String urlA, final DataCallBack dataCallBack){
        List<BasicNameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair(DRUGID, drugId));
        pairs.add(new BasicNameValuePair(URLA, urlA));
        postAsync(RequestHelper.getInstance().OUT_BASE_URL+FACE_COMPARE, pairs, dataCallBack);
    }


    /**
     * 活体检测的方法
     * @param aActivity
     */
    public void startLive(Activity aActivity){

        //TODO 根据2018-4-19日张克利沟通的情况，活体检测的步骤修改为张嘴
        ArrayList<Integer> liveList = new ArrayList<>();
//        liveList.add(FaceInterface.LivessType.LIVESS_MOUTH);
//        liveList.add(FaceInterface.LivessType.LIVESS_HEAD_LEFT);
//        liveList.add(FaceInterface.LivessType.LIVESS_HEAD_RIGHT);

        liveList.add(FaceInterface.LivessType.LIVESS_MOUTH);

        AppLogger.e("CLOUDWALK_KEY="+BuildConfig.CLOUDWALK_KEY);
        final Bulider bulider = new Bulider();
        bulider.setLicence(BuildConfig.CLOUDWALK_KEY).setLivessFace(new LivessCallBack(){
            @Override
            public void OnLivessSerResult(byte[] bestface, String bestInfo, byte[] nextface, String nextInfo,
                                          byte[] clipedBestFaceImgData, boolean isUpload) {
                if (bestface != null && bestInfo != null && !TextUtils.isEmpty(bestInfo)
                        && nextface != null && nextInfo != null && !TextUtils.isEmpty(nextInfo)){
                    String imgBestBase64 = Base64Util.encode(bestface);
                    String imgNextBase64 = Base64Util.encode(nextface);
                    //TODO:strFaceInfo为后端所需的活体信息
                    String strFaceInfo = imgBestBase64 + "," + bestInfo + "_" + imgNextBase64 + "," + nextInfo;
                    if (isUpload){
                        HttpManager.cwFaceSerLivess(faceserver, faceappid, faceappser, strFaceInfo, new HttpManager.DataCallBack() {
                            @Override
                            public void requestFailure(String errorMsg) {
                                bulider.setFaceLiveResult(ProjectApplication.getContext(), Bulider.FACE_LIVE_FAIL, Bulider.FACE_LIVE_FAIL);
                            }

                            @Override
                            public void requestSucess(JSONObject jb) {
                                AppLogger.e(jb.toString());
                                int extInfo = jb.optInt("extInfo");
                                bulider.setFaceLiveResult(ProjectApplication.getContext(), Bulider.FACE_LIVE_PASS, extInfo);
                            }
                        });
                    }
                }
            }
        }).isServerLive(true)
                .isResultPage(true)
                .setPublicFilePath(StorageUtil.CLOUDWALK_PATH)
                .setLives(liveList, liveCount, true, true, liveLevel)
                .setResultCallBack(resultCallBack)
                .setLiveTime(liveTime)
                .startActivity(aActivity, LiveStartActivity.class);
    }


    ResultCallBack resultCallBack = new ResultCallBack() {
        @Override
        public void result(boolean isLivePass, boolean isVerfyPass, String faceSessionId, double face_score,
                           int resultType, byte[] bestFaceImgData, byte[] clipedBestFaceImgData, HashMap<Integer, byte[]> liveImgDatas) {
            mBackUIResultCallBack.onBackUIResult(isLivePass, isVerfyPass, resultType, bestFaceImgData);
        }
    };


    public static void postAsync(final String url, final List<BasicNameValuePair> pairs,
                                 final DataCallBack dataCallBack) {
        new AsyncTask<Object, Object, String>() {
            @Override
            protected String doInBackground(Object... param) {
                String result = null;
                try {
                    result = ApacheHttpUtil.post(url, pairs);
                    pairs.clear();
                } catch (OutOfMemoryError e) {

                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {

                if(result != null){
                    try {
                        JSONObject jb = new JSONObject(result);
                        if (jb.optInt(RESULT) == 0) {
                            dataCallBack.requestSucess(jb);
                        } else {
                            String errorMsg = jb.optString(INFO);
                            dataCallBack.requestFailure(ProjectApplication.getContext()
                                    .getString(R.string.cwm) + jb.optInt(RESULT) + ProjectApplication.getContext()
                                    .getString(R.string.cwxx) + errorMsg);
                        }
                    } catch (Exception e) {
                        dataCallBack.requestFailure(ProjectApplication.getContext()
                                .getString(R.string.common_network_unavailable));
                        e.printStackTrace();

                    }
                }else{
                    dataCallBack.requestFailure(ProjectApplication.getContext()
                            .getString(R.string.common_network_unavailable));
                }
            }
        }.execute("");
    }

    public interface DataCallBack {

        /**
         * 失败返回
         * @param errorMsg
         */
        void requestFailure(String errorMsg);

        /**
         * 成功返回
         * @param jb
         */
        void requestSucess(JSONObject jb);
    }


    public interface BackUIResultCallBack {

        /**
         * 
         * @param isLivePass
         * @param isVerfyPass
         * @param resultType
         * @param bestFaceImgData
         */
        void onBackUIResult(boolean isLivePass, boolean isVerfyPass,int resultType, byte[] bestFaceImgData);
    }


    public void setOnBackUIResultCallBack(BackUIResultCallBack aCallBack){
        this.mBackUIResultCallBack = aCallBack;
    }
}
