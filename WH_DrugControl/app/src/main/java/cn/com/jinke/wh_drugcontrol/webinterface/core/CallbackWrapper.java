package cn.com.jinke.wh_drugcontrol.webinterface.core;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.JsonConstans;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;

public class CallbackWrapper<T> implements CommonCallback<String>, CodeConstants, JsonConstans,MsgKey {
    public int mMsgCode;
    public int mFlag;
    private HttpResult mHttpResult;

    public CallbackWrapper() {

    }

    public CallbackWrapper(int msgCode, int flag) {
        this.mMsgCode = msgCode;
        this.mFlag = flag;

    }
    public CallbackWrapper(int flag) {
        this.mFlag = flag;
    }

    @Override
    public void onCancelled(CancelledException cex) {
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        AppLogger.e("http error: " + ex.toString());
        if (mMsgCode != 0) {
            int code = 0;
            String errorMsg = "网络连接错误";
            String errorStr = ex.toString();
            if (!TextUtils.isEmpty(errorStr) && errorStr.contains("errorCode")) {
                if (errorStr.contains("403")) {
                    code = 403;
                } else if (errorStr.contains("404")) {
                    code = 404;
                } else if (errorStr.contains("500")) {
                    code = 500;
                } else if (errorStr.contains("503")) {
                    code = 503;
                } else if (errorStr.contains("505")) {
                    code = 505;
                }
                if (code != 0) {
                    errorMsg = String.format("网络请求错误，code: %s", code);
                }
            }
            MessageProxy.sendEmptyMessage(ACCESS_NET_FAILED);
        }

    }

    @Override
    public void onFinished() {
    }


    @Override
    public void onSuccess(String result) {
        AppLogger.e(result);
        if(mFlag == 1){
            result = result.replace("\\", "");
            result = result.substring(1, result.length() - 1);
        }

        try {
//		this.mHttpResult = result;
//		int state = result.getState();
//		String msg = result.getMsg();
//		String requestUri = result.getRequestUri();
//		int extraData = result.getExtraData();
//		String data = result.getData();
            int code = 0;
            JSONObject aResult = new JSONObject(result);
            if(aResult.has("flag")){
                boolean suc = aResult.optBoolean(SUC);
                if(suc){
                    code = 1;
                }else{
                    code = 0;
                }
            }else{
                code = aResult.optInt(SUC);
            }

            String msg = aResult.optString(RES_CASE);
            int total = 0 ;
            if(aResult.has(RES_EXTRA_DATA)){
                total = aResult.optInt(RES_EXTRA_DATA);
             }

            if (!TextUtils.isEmpty(result)) {
                Type type = getClass().getGenericSuperclass();
                Type[] generics = ((ParameterizedType) type).getActualTypeArguments();

                String typeStr = generics[0].toString().replace("class", "").trim();
                if (typeStr.equals("java.lang.Void")) {
                    onSuccess(code, msg, null, total);
                } else {

                    String dataArrStr=aResult.optString(RES_DATA);
                    if (TextUtils.isEmpty(dataArrStr) && mFlag != 3){
                        onSuccess(FAIL, msg, null, total);
                        return ;
                    }

                    if(mFlag == 3){
                        dataArrStr = aResult.toString();
                    }
                    //这里的特殊处理
                    if("[]".equals(dataArrStr)){
                        //如果 不设置成错误信息  那在每个 onSuccess 中处理时就可能会出现 强转异常
                        //onError(new Throwable("没有数据"),false);
                        onSuccess(FAIL, "没有数据", null, total);
                        return;
                    }

                    T object = new Gson().fromJson(dataArrStr, generics[0]);
                    onSuccess(code, msg, object, total);
                }
            } else {
                onSuccess(FAIL, msg, null, total);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            onSuccess(FAIL, result, null, TOTAL);

        }
    }

	public void onSuccess(int state, String msg, T object, String requestUri) {
		if (this.mHttpResult != null) {
			this.onSuccess(state, msg, object, requestUri);
		}
	}

    /**
     * @param state 状态 code
     * @param msg  消息
     * @param object 传递对象
     * @param total   总数
     */
    public void onSuccess(int state, String msg, T object, int total) {
        if (mMsgCode != 0) {
//            if(mMsgCode != GET_FLOW_LIST && mMsgCode != GET_IP){
//                MessageProxy.sendMessage(mMsgCode,  state, total, state == SUCCESS  &&  object != null ? object : msg);
//            }
        }
    }

    public HttpResult getHttpResult() {
        return this.mHttpResult;
    }

}
