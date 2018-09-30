package cn.com.jinke.wh_drugcontrol.me.manager;

import android.graphics.Bitmap;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.me.model.Area;
import cn.com.jinke.wh_drugcontrol.me.model.NoticeChild;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;

/**
 * Created by jinke on 2017/8/1.
 */

public class MeManager extends UrlSetting implements CodeConstants, MsgKey{

    private static MeManager instance;
    private final List<Persion> mPersionList = new ArrayList<>();
    private final List<Area> mAreaList = new ArrayList<>();
    private final static int PAGE_SIZE = 10;

    /**
     * 上次刷新时间
     */
    public static long sLastRefreshTime;
    private static int sPageStart;
    private static int sTotalCount;
    private static int mPage = 0;

    private MeManager() {

    }

    public static MeManager getInstance(){
        if (instance == null) {
            synchronized (MeManager.class) {
                if (instance == null) {
                    instance = new MeManager();
                }
            }
        }
        return instance;
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

    public void getArea(String aOrgId){
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(AREA_URL);
        builder.putParams(ORGPARENTID, aOrgId);
        x.http().post(builder.build(), new CallbackWrapper<List<Area>>(AREA_MSG, 2){

            @Override
            public void onSuccess(int state, String msg, List<Area> object, int total) {
                if(state == SUCCESS){

                    if(mAreaList.size() > 0){
                        mAreaList.clear();
                    }

                    if(object != null){
                        mAreaList.addAll(object);
                    }
                }
                MessageProxy.sendMessage(mMsgCode, state, mAreaList);
            }
        });
    }

    public Bitmap createQRBitmap(String desPhone, String desId){
        Bitmap bitmap = CommUtils.getInstance().createQRBitmap(String.format("%1$s&idCard=%2$s&userId=%3$s",
                RequestHelper.getInstance().OUT_BASE_URL+DOWNLOAD_APK_PATH, desPhone, desId));
        return bitmap;
    }

    public String getJoinArticleUrl(NoticeChild child) {
        return RequestHelper.getInstance().getRequestHeader() + FINDARTICLEDEL
                + "&articleDetailId=" + child.getArticleDetailId();
    }

    /**
     * 搜索吸毒人员接口
     * @param isRefresh 是否刷新
     * @param userId  专干id
     * @param context 搜索关键字
     * @param type 用户类型 1专干2工作人员
     * @param isCollection 是否查询关注人 0否1是
     * @param orgId 所属区域id
     */
    public void search(boolean isRefresh, String userId, String context, String type, String orgId, int isCollection){

        if (isRefresh) {
            sPageStart = 0;
            mPage = 0;
            mPersionList.clear();
            sLastRefreshTime = System.currentTimeMillis();
        }

        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_URL);
        builder.putParams(USER_ID, userId);
        builder.putParams(INPUT, context);
        builder.putParams(USER_TYPE,type);
        builder.putParams(AREA_ID,orgId);
        builder.putParams(ISCOLLECTION,isCollection);
        builder.putParams(PAGENO, isRefresh ? 0 : sPageStart);
        builder.putParams(PAGESIZE, PAGE_SIZE);
        x.http().post(builder.build(), new CallbackWrapper<List<Persion>>(SEARCH_MSG, 2){

            @Override
            public void onSuccess(int state, String msg, List<Persion> object, int total) {

                if(state == SUCCESS){
                    if(object != null){
                        mPersionList.addAll(object);
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
                MessageProxy.sendMessage(mMsgCode, state, mPersionList);
            }
        });
    }
}
