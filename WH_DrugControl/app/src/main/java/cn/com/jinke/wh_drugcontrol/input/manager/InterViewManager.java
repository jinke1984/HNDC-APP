package cn.com.jinke.wh_drugcontrol.input.manager;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.input.model.Talk;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by jinke on 2017/8/7.
 */

public class InterViewManager extends UrlSetting implements CodeConstants, MsgKey {

    private static InterViewManager instance;

    private final List<Talk> mTalkList = new ArrayList<>();
    private final static int PAGE_SIZE = 10;

    /**
     *  上次刷新时间
     */
    public static long sLastRefreshTime;
    private static int sPageStart;
    private static int sTotalCount;
    private static int mPage = 0;

    private InterViewManager() {

    }

    public static InterViewManager getInstance() {
        if (instance == null) {
            synchronized (InterViewManager.class) {
                if (instance == null) {
                    instance = new InterViewManager();
                }
            }
        }
        return instance;
    }

    public void getTalkList(String drug_id, boolean isRefresh) {

        if (isRefresh) {
            sPageStart = 0;
            mPage = 0;
            mTalkList.clear();
            sLastRefreshTime = System.currentTimeMillis();
        }

        ProjectParams params = new ProjectParams(TALK_URL)
                .with(DRUGID, drug_id)
                .with(PAGESIZE, PAGE_SIZE)
                .with(PAGENO, isRefresh ? 0 : sPageStart);
        
        x.http().post(params.build(), new CallbackWrapper<List<Talk>>(TALK_MSG, 2) {

            @Override
            public void onSuccess(int state, String msg, List<Talk> object, int total) {
                if (state == SUCCESS) {

                    if (object != null) {
                        mTalkList.addAll(object);
                    }
                    if (sTotalCount >= 0) {
                        mPage = mPage + 1;
                        sTotalCount = total;
                        int pageCount = sTotalCount / PAGE_SIZE;
                        if (pageCount >= mPage) {
                            sPageStart = sPageStart + PAGE_SIZE;
                        }
                    }

                }
                MessageProxy.sendMessage(mMsgCode, state, mTalkList);
            }
        });

    }

    public boolean isFinish() {
        boolean result;
        if (sTotalCount == 0) {
            return true;
        } else {
            int pageCount = sTotalCount / PAGE_SIZE;
            if (pageCount >= mPage) {
                result = false;
            } else {
                result = true;
            }
        }
        return result;
    }
}
