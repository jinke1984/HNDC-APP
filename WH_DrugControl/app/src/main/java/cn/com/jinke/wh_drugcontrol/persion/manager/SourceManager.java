package cn.com.jinke.wh_drugcontrol.persion.manager;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.persion.model.Source;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;

/**
 * @author jinke
 * @date 2017/10/26
 * @description
 */

public class SourceManager extends UrlSetting implements CodeConstants, MsgKey{

    private static SourceManager instance;
    private final List<Source> mSourceList = new ArrayList<>();

    private final static int PAGE_SIZE = 10;

    /**
     * 上次刷新时间
     */
    public static long sLastRefreshTime;
    private static int sPageStart;
    private static int sTotalCount;
    private static int mPage = 0;

    private SourceManager() {

    }

    public static SourceManager getInstance(){
        if (instance == null) {
            synchronized (SourceManager.class) {
                if (instance == null) {
                    instance = new SourceManager();
                }
            }
        }
        return instance;
    }

    public void getSourceDetail(boolean isRefresh, String idCard){

        if (isRefresh) {
            sPageStart = 0;
            mPage = 0;
            mSourceList.clear();
            sLastRefreshTime = System.currentTimeMillis();
        }

        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(SOURCE_DETAIL_URL);
        builder.putParams(IDNO, idCard);
        builder.putParams(PAGENO, isRefresh ? 0 : sPageStart);
        builder.putParams(PAGESIZE, PAGE_SIZE);
        x.http().post(builder.build(), new CallbackWrapper<List<Source>>(SOURCE_SUC, 2){

            @Override
            public void onSuccess(int state, String msg, List<Source> object, int total) {
                if(state == SUCCESS){
                    if(object != null){
                        mSourceList.addAll(object);
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
                MessageProxy.sendMessage(mMsgCode, state, mSourceList);
            }
        });
    }

    public boolean isFinish() {
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
