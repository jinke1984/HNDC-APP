package cn.com.jinke.wh_drugcontrol.me.manager;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.booter.ProjectApplication;
import cn.com.jinke.wh_drugcontrol.manager.PackageHelper;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;

/**
 * @author jinke
 * @date 2018/9/10
 * @description
 */
public class SignInManager extends UrlSetting implements CodeConstants, MsgKey {

    private static SignInManager instance;

    private final List<String> mSigninList = new ArrayList<>();

    private final static int PAGE_SIZE = 10;

    /**
     * 上次刷新时间
     */
    public static long sLastRefreshTime;
    private static int sPageStart;
    private static int sTotalCount;
    private static int mPage = 0;

    private SignInManager() {

    }

    public static SignInManager getInstance(){
        if (instance == null) {
            synchronized (SignInManager.class) {
                if (instance == null) {
                    instance = new SignInManager();
                }
            }
        }
        return instance;
    }

    public void addSignIn(double latitude, double longitude, String address, String city){
        UserCard userCard = MasterManager.getInstance().getUserCard();
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(ADDSIGNIN);
        builder.putParams(IDNO, "");
        builder.putParams(USER_ID, userCard.getUserId());
        builder.putParams(LATITUDE, String.valueOf(latitude));
        builder.putParams(LONGITUDE, String.valueOf(longitude));
        builder.putParams(B_ADDRESS, address);
        builder.putParams(CITY, city);
        builder.putParams(APPVERSION, PackageHelper.getVersionCode(ProjectApplication.getContext()));
        builder.putParams(PERSIONTYPE, "2");
        x.http().post(builder.build(), new CallbackWrapper<Void>(SIGN_IN_SUC, 2){

            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

    public void querySignin(boolean isRefresh, String startDate, String endDate){

        if (isRefresh) {
            sPageStart = 0;
            mPage = 0;
            mSigninList.clear();
            sLastRefreshTime = System.currentTimeMillis();
        }

        UserCard userCard = MasterManager.getInstance().getUserCard();
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(QUERYSIGNIN);
        builder.putParams(PERSIONTYPE, "2");
        builder.putParams(USER_ID, userCard.getUserId());
        builder.putParams(STARTDATE, startDate);
        builder.putParams(ENDDATE, endDate);
        x.http().post(builder.build(), new CallbackWrapper<List<String>>(SIGN_LIST_SUC, 2){

            @Override
            public void onSuccess(int state, String msg, List<String> object, int total) {
                if(state == SUCCESS){
                    if(object != null){
                        mSigninList.addAll(object);
                    }

                    if (sTotalCount >= 0) {
                        mPage = mPage + 1;
                        sTotalCount = total;
                        int pageCount = sTotalCount/PAGE_SIZE;
                        if(pageCount >= mPage){
                            sPageStart = sPageStart + PAGE_SIZE;
                        }
                    }
                }
                MessageProxy.sendMessage(mMsgCode, state, mSigninList);
            }
        });

    }

    public static boolean isFinish() {
        boolean result;
        if (sTotalCount == 0) {
            return true;
        }
        else {
            int pageCount = sTotalCount/PAGE_SIZE;
            if(pageCount >= mPage){
                result = false;
            }else{
                result = true;
            }
        }
        return  result;
    }

}
