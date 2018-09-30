package cn.com.jinke.wh_drugcontrol.manager;

import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;

/**
 * Created by xkr on 2017/12/7.
 */

public class AppUpdateTimeManager extends UrlSetting {
    static AppUpdateTimeManager instance = new AppUpdateTimeManager();
    private AppUpdateTimeManager(){}
    public static AppUpdateTimeManager getInstance(){
        if (instance == null) {
            synchronized (AppUpdateTimeManager.class) {
                if (instance == null){
                    instance = new AppUpdateTimeManager();
                }
            }
        }
        return instance;
    }

    /**
     * 上传app使用状态
     * @param useStatu
     */
    public void updateAppUseStatu(AppUseStatu useStatu){
        UserCard userCard = MasterManager.getInstance().getUserCard();
        if(userCard == null){
            return;
        }
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(UPDATE_APP_USE_TIME);
        builder.putParams("idCard","");
        builder.putParams("mobileNo","");
        builder.putParams("controlId",userCard.getUserId());
        builder.putParams("lastState",useStatu.value());
        x.http().post(builder.build(),new CallbackWrapper<>());
    }

    /**
     * app 使用状态
     * 1登录，2切换后台，3退出登录
     */
    public enum  AppUseStatu{
        LOGIN(1),CHANGE2BG(2),LOGOUT(3);
        int useStatus;
        AppUseStatu(int status){
            useStatus = status;
        }
        public  int value(){
            return useStatus;
        }
    }
}
