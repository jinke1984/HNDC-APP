package cn.com.jinke.wh_drugcontrol.input.manager;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.input.model.Change;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * @author jinke
 * @date 2018/3/8
 * @description
 */

public class ChangeAddressManager extends UrlSetting implements CodeConstants, MsgKey {

    private static ChangeAddressManager instance;

    private final List<Change> mChangeList = new ArrayList<>();
    private final static int PAGE_SIZE = 10;

    /**
     *  上次刷新时间
     */
    public static long sLastRefreshTime;
    private static int sPageStart;
    private static int sTotalCount;
    private static int mPage = 0;

    private ChangeAddressManager() {}

    public static ChangeAddressManager getInstance() {
        if (instance == null) {
            synchronized (ChangeAddressManager.class) {
                if (instance == null) {
                    instance = new ChangeAddressManager();
                }
            }
        }
        return instance;
    }

    /**
     * 执行地变更记录查询
     * @param personId   吸毒人员id
     * @param isRefresh  是否刷新
     */
    public void queryChangeList(String personId, boolean isRefresh){

        if (isRefresh) {
            sPageStart = 0;
            mPage = 0;
            mChangeList.clear();
            sLastRefreshTime = System.currentTimeMillis();
        }

        ProjectParams params = new ProjectParams(CHANGELIST)
                .with(PERSONID, personId)
                .with(PAGESIZE, PAGE_SIZE)
                .with(PAGENO, isRefresh ? 0 : sPageStart);

        x.http().post(params.build(), new CallbackWrapper<List<Change>>(CHANGE_MSG, 2){

            @Override
            public void onSuccess(int state, String msg, List<Change> object, int total) {

                if(state == SUCCESS){

                    if (object != null) {
                        mChangeList.addAll(object);
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
                MessageProxy.sendMessage(mMsgCode, state, mChangeList);
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
