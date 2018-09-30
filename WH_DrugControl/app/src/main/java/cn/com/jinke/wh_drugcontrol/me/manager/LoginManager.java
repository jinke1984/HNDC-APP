package cn.com.jinke.wh_drugcontrol.me.manager;

import android.util.Log;

import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.manager.AppUpdateTimeManager;
import cn.com.jinke.wh_drugcontrol.manager.CommonManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;

/**
 * Created by jinke on 2017/7/20.
 */

public class LoginManager extends UrlSetting implements CodeConstants, MsgKey {

    private static LoginManager instance;

    private LoginManager() {

    }

    public static LoginManager getInstance(){
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public void login(String aAccount, String aPassword){

        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(LOGIN_URL);
        builder.putParams(ACCOUNT, aAccount);
        builder.putParams(PASSWORD, aPassword);

        x.http().post(builder.build(), new CallbackWrapper<UserCard>(LOGIN_MSG, 2){

            @Override
            public void onSuccess(int state, String msg, UserCard object, int total) {
                if(state == SUCCESS && object != null){
                    MasterManager.getInstance().init(object);
                    AppUpdateTimeManager.getInstance().updateAppUseStatu(AppUpdateTimeManager.AppUseStatu.LOGIN);
                    CommonManager.getInstance().uploadPushId();
                }

                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });

    }
}
